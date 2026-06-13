package engine.gameCharacters.mummys;

import com.badlogic.gdx.math.Vector2;

import engine.DrawableElement;
import engine.KVEventListener;
import engine.game.Game;
import engine.gameCharacters.abstractGameCharacter.GameCharacter;
import engine.level.Stair;
import util.Constants;
import util.GameRules;
import util.ProbabilisticSelector;

/**
 * Estado de la momia encargado de la evaluación del entorno y la seleccion del
 * proximo comportamiento dentro de la maquina de estados.
 *
 * <p>
 * Durante este estado, la momia permanece temporalmente quieta mientras acumula
 * tiempo de decision. Una vez alcanzado el umbral definido, se analiza el
 * entorno del jugador y la estructura de la plataforma actual para determinar
 * el siguiente estado de la IA.
 * </p>
 *
 * 
 * La decision puede basarse en:
 * <ul>
 * <li>Posicion relativa del jugador</li>
 * <li>Accesibilidad del jugador dentro de la plataforma actual</li>
 * <li>Presencia de escaleras accesibles (ascenso o descenso)</li>
 * <li>Seleccion probabilística cuando no existe una decision determinista
 * clara</li>
 * </ul>
 * 
 *
 * <p>
 * Este estado actua como un punto de transicion dentro de la FSM, pudiendo
 * derivar en estados de movimiento (Walking), busqueda de escaleras
 * (SearchingStair) o destruccion (Dying).
 * </p>
 * 
 * @author Guillermo Lazzurri
 * 
 */
public class MummyStateDeciding extends MummyState
{

	/**
	 * Codigo que indica subir escalera
	 */
	private static final int GO_UP_STAIR = 3;
	/**
	 * Codigo que indica bajar escalera
	 */

	private static final int GO_DOWN_STAIR = 4;

	/**
	 * Constructor de clase, llama a super(mummy, Mummy.ST_IDDLE);
	 * 
	 * @param mummy Contexto del patron state
	 */
	public MummyStateDeciding(Mummy mummy)
	{
		super(mummy, Mummy.ST_IDDLE);
		mummy.resetTimeInState();
		this.timeToChange = this.mummy.getTimeDeciding();

	}

	/**
	 * Actualiza el estado de decision de la momia.
	 *
	 * <p>
	 * Durante la fase activa del estado, la momia permanece sin desplazamiento
	 * mientras acumula tiempo de analisis. Una vez superado el tiempo definido, se
	 * evalua el entorno y se selecciona el siguiente estado de comportamiento.
	 * </p>
	 */
	@Override
	public void update(float deltaTime)
	{
		this.mummy.move(new Vector2(), false, deltaTime);

		if (this.mummy.getTimeInState() >= this.timeToChange && (this.mummy.getRenderMode() == GameCharacter.ST_WALKING
				|| this.mummy.getRenderMode() == GameCharacter.ST_IDDLE))
		{
			this.changeStatus();
		}

	}

	/**
	 * Evalua el entorno de la momia y determina el proximo estado de la maquina de
	 * estados.
	 *
	 * <p>
	 * El proceso combina logica determinista y probabilistica para seleccionar el
	 * comportamiento mas adecuado, considerando la posicion del jugador, la
	 * accesibilidad de escaleras y la configuracion de la plataforma actual.
	 * </p>
	 *
	 * <p>
	 * En caso de no existir ninguna accion posible, la momia se considera encerrada
	 * y se dispara su destruccion.
	 * </p>
	 */
	private void changeStatus()
	{
		PlatformAnalysisResult result = new PlatformAnalysisResult(mummy);
		if (GameRules.getInstance().isDebugMode())
			Game.getInstance().getIGraphic()
					.addGraphicElement(new DrawableElement(Constants.DRAWABLE_PLATFORM_ANALYSIS_RESULT, result));
		Stair playerStair = this.mummy.player.getStair();
		boolean takedDecision = false;
		// Si el player esta en una escalera a la que puede acceder la momia desde esta
		// plataforma, va hacia la escalera
		if (playerStair != null && (result.getDownStairsInPlatform().contains(playerStair)
				|| result.getUpStairsInPlatform().contains(playerStair)))
		{
			this.mummy.mummyState = new MummyStateSearchingStair(this.mummy, result, playerStair);
			takedDecision = true;
		} else // El player no esta en una escalera accesible desde la plataforma
		{

			ProbabilisticSelector selector = new ProbabilisticSelector(Game.random);
			int whereIsPlayer = this.mummy.whereIsPlayer();
			if (whereIsPlayer == Mummy.PLAYER_IS_SOME_LEVEL && result.isCharacterReachable(mummy.player))
			// Si el player es alcanzable en la plataforma seguro tomare una decision
			// deterministica
			{
				takedDecision = true;
				if (this.mummy.x > this.mummy.player.x) // Si el player esta a la izquierda voy hacia alli
					this.mummy.mummyState = new MummyStateWalking(this.mummy, result, LEFT);
				else // Sino voy hacia la derecha
					this.mummy.mummyState = new MummyStateWalking(this.mummy, result, RIGHT);

			}

			else
			{

				this.addLeftAndRightProbability(result, selector, whereIsPlayer);

				if (result.getNearestUpStair() != null) // Si hay una escalera cercana que suba

				{
					switch (whereIsPlayer)
					{
					case Mummy.PLAYER_IS_UP:
						if (this.mummy.player.getLastFloorCoordinate() >= result.getNearestUpStair().getUpStair().y)
						{
							// Si el player esta mas arriba o a la misma altura que la parte superior de la
							// escalera, busco la escalera.
							this.mummy.mummyState = new MummyStateSearchingStair(this.mummy, result,
									result.getNearestUpStair());
							takedDecision = true;
						} else // Si no, entonces considero la posibilidad de tomar la escalera
						{
							selector.add(0.5, GO_UP_STAIR);
						}
						break;
					case Mummy.PLAYER_IS_DOWN:
						selector.add(0.15, GO_UP_STAIR);
						break;
					case Mummy.PLAYER_IS_SOME_LEVEL:
						selector.add(0.25, GO_UP_STAIR);
						break;
					}
				}

				if (result.getNearestDownStair() != null) // Si hay una escalera cercana que baje

				{
					switch (whereIsPlayer)
					{
					case Mummy.PLAYER_IS_DOWN:
						if (this.mummy.player.getLastFloorCoordinate() <= result.getNearestDownStair().getDownStair().y)
						{
							// Si el player esta mas abajo o a la misma altura que la parte superior de la
							// escalera, busco la escalera.
							this.mummy.mummyState = new MummyStateSearchingStair(this.mummy, result,
									result.getNearestDownStair());
							takedDecision = true;
						} else // Si no, entonces considero la posibilidad de tomar la escalera
						{
							selector.add(0.5, GO_DOWN_STAIR);
						}
						break;
					case Mummy.PLAYER_IS_UP:
						selector.add(0.15, GO_DOWN_STAIR);
						break;
					case Mummy.PLAYER_IS_SOME_LEVEL:
						selector.add(0.25, GO_DOWN_STAIR);
						break;
					}
				}
				if (!takedDecision) // Si no pude tomar una decision con seguridad voy a elegir una opcion al azar
				// de entre las posibles
				{
					Integer decision = (Integer) selector.getValue();
					if (decision != null) // Si hay alguna opcion, es decir, no estoy encerrado con el player fuera de
					// mi
					// alcance
					{
						switch (decision)
						{
						case GO_UP_STAIR:
							this.mummy.mummyState = new MummyStateSearchingStair(this.mummy, result,
									result.getNearestUpStair());

							break;
						case GO_DOWN_STAIR:
							this.mummy.mummyState = new MummyStateSearchingStair(this.mummy, result,
									result.getNearestDownStair());
							break;
						case LEFT:
							this.mummy.mummyState = new MummyStateWalking(this.mummy, result, LEFT);

							break;
						case RIGHT:
							this.mummy.mummyState = new MummyStateWalking(this.mummy, result, RIGHT);

							break;
						}

					} else // estoy encerrado con el player fuera de mi alcance, la momia muere y se
					// teletransporta
					{
						this.mummy.die(true);
					}

				}

			}
		}

	}

	/**
	 * Retorna true
	 */
	@Override
	protected boolean isActive()
	{
		return true;
	}

	/**
	 * Llamado cuando la momia muere. Cambia el estado a new
	 * MummyStateDying(this.mummy, mustTeleport); Ademas dispara el evento:
	 * Game.getInstance().eventFired(KVEventListener.MUMMY_DIE, this);
	 */
	@Override
	protected void die(boolean mustTeleport)
	{
		this.mummy.mummyState = new MummyStateDying(this.mummy, mustTeleport);
		Game.getInstance().eventFired(KVEventListener.MUMMY_DIE, this);
	}

	private double getProbabilityOfEndPlatform(EndPlatform endPlatform, int whereIsPlayer)
	{
		double r = 0;
		switch (whereIsPlayer)
		{
		case Mummy.PLAYER_IS_UP:
			switch (endPlatform.getType())
			{
			case EndPlatform.END_STEP:
				r = 0.5;
				break;
			case EndPlatform.END_CLIFF:
				r = 0.25;
				break;
			}
			break;

		case Mummy.PLAYER_IS_DOWN:
			switch (endPlatform.getType())
			{
			case EndPlatform.END_STEP:
				r = 0.25;
				break;
			case EndPlatform.END_CLIFF:
				r = 0.50;
				break;
			}
			break;

		case Mummy.PLAYER_IS_SOME_LEVEL:
			switch (endPlatform.getType())
			{
			case EndPlatform.END_STEP:
				r = 0.50;
				break;
			case EndPlatform.END_CLIFF:
				r = 0.50;
				break;
			}
			break;

		}
		return r;
	}

	/**
	 * Agrega al selector las probabilidades de ir hacia la derecha o hacia la
	 * izquierda. Si la momia esta encerrada en una plataforma sin salida, no se
	 * agrega ninguna probabilidad.
	 * 
	 * @param result        Resultado del analisis de la plataforma
	 * @param selector      Selector donde se agregarian las probabilidadades
	 * @param whereIsPLayer Codigo que indica posicion relativa vertical del player
	 */
	private void addLeftAndRightProbability(PlatformAnalysisResult result, ProbabilisticSelector selector,
			int whereIsPLayer)
	{
		double leftProbability = this.getProbabilityOfEndPlatform(result.getEndPlatformLeft(), whereIsPLayer);
		double rightProbability = this.getProbabilityOfEndPlatform(result.getEndPlatformRight(), whereIsPLayer);
		if (leftProbability == rightProbability)
		{
			if (mummy.x > mummy.player.x)
				leftProbability *= 2;
			else
				rightProbability *= 2;
		}
		if (leftProbability > 0)
			selector.add(leftProbability, LEFT);
		if (rightProbability > 0)
			selector.add(rightProbability, RIGHT);

	}

}
