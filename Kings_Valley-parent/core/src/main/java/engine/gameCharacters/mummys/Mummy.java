package engine.gameCharacters.mummys;

import com.badlogic.gdx.math.Vector2;

import engine.KVEventListener;
import engine.game.Game;
import engine.gameCharacters.abstractGameCharacter.GameCharacter;
import engine.gameCharacters.player.Player;
import engine.level.GiratoryMechanism;
import engine.level.Pyramid;
import engine.level.Stair;
import util.GameRules;

/**
 * Clase abstracta que representa un momia
 * 
 * @author Guillermo Lazzurri
 */
@SuppressWarnings("serial")
public abstract class Mummy extends GameCharacter
{

	/**
	 * Codigo que indica Player al mismo nivel que la momia
	 */
	protected static final int PLAYER_IS_SOME_LEVEL = 0;
	/**
	 * Codigo que indica Player a esta arriba de la momia
	 */

	protected static final int PLAYER_IS_UP = 1;

	/**
	 * Codigo que indica Player a esta abajo de la momia
	 */
	protected static final int PLAYER_IS_DOWN = -1;

	/**
	 * Codigo de estado En el limbo
	 */
	public static final int ST_LIMBUS = 101;

	/**
	 * Codigo de estado Apareciendo
	 */
	public static final int ST_APPEARING = 102;
	/**
	 * Codigo de estado Teletransportandose
	 */
	public static final int ST_TELEPORTING = 104;

	/**
	 * Tiempo que tarda en tomar la proxima decicion
	 */
	private float timeToDecide;
	/**
	 * Tiempo que tarda en decidir cuando esta en modo decidiendo
	 */
	private float timeDeciding;
	/**
	 * Contador de tiempo en el presente estado
	 */
	private float timeInState = 0;

	/**
	 * Indica la direccion pretendida de la momia
	 */
	private Vector2 direction = new Vector2();
	/**
	 * Estado de la momia (patron state)
	 */
	protected MummyState mummyState;
	/**
	 * Nivel de stress de la momia (si es muy alto la momia muere)
	 */
	private float stressLevel = 0;
	/**
	 * Referencia al player a perseguir
	 */
	protected Player player;

	/**
	 * Constructor de clase, llama a <br>
	 * super(type, x,
	 * y,GameRules.getInstance().getCharacterWidth(),GameRules.getInstance().getMummyHeight(),
	 * parameters[GameRules.INDEX_SPEED_WALK],
	 * parameters[GameRules.INDEX_SPEED_STAIR], pyramid);
	 * 
	 * 
	 * @param type       Indica el tipo de momia (solo deberia utilizarse para
	 *                   elegir modelos de visualizacion)
	 * @param x          Coordenada x
	 * @param y          Coordenada y
	 * @param parameters array de float que indica los parametros de la momia
	 * @param pyramid    piramide en la que esta ubicada la momia
	 * @param player     referencia al player que la momia persigue
	 */
	public Mummy(int type, float x, float y, float[] parameters, Pyramid pyramid, Player player)
	{
		super(type, x, y, GameRules.getInstance().getCharacterWidth(), GameRules.getInstance().getMummyHeight(),
				parameters[GameRules.INDEX_SPEED_WALK], parameters[GameRules.INDEX_SPEED_STAIR], pyramid);
		this.timeToDecide = parameters[GameRules.INDEX_TIME_TO_DECIDE];
		this.timeDeciding = parameters[GameRules.INDEX_TIME_DECIDING];

		this.mummyState = new MummyStateLimbus(this, 1);
		this.player = player;
	}

	/**
	 * Llama a this.doJump(); (las momias solo pueden saltar) y dispara el evento
	 * Game.getInstance().eventFired(KVEventListener.MUMMY_JUMP, this);
	 * 
	 */
	@Override
	protected void doAction()
	{
		this.doJump();
		Game.getInstance().eventFired(KVEventListener.MUMMY_JUMP, this);

	}

	/**
	 * retorna el tiempo que tarda la momia en tomar la proxima decision
	 * 
	 * @return tiempo que tarda la momia en tomar la proxima decision
	 */
	protected float getTimeToDecide()
	{
		return this.timeToDecide;
	}

	/**
	 * Retorna el tiempo que tarda la momia en dudar, para tomar la decision
	 * 
	 * @return tiempo que tarda la momia en dudar, para tomar la decision
	 */
	protected float getTimeDeciding()
	{
		return this.timeDeciding;
	}

	/**
	 * Siempre retorna false (las momias no pueden utilizar giratorias)
	 */
	@Override
	protected boolean canPassGiratoryMechanism(GiratoryMechanism giratoryMechanism)
	{
		return false;
	}

	/**
	 * Se sobreescribe como metodo vacio (no hace nada)
	 */
	@Override
	protected void passGiratoryMechanism(GiratoryMechanism giratoryMechanism)
	{

	}

	/**
	 * Llamado para actualizar la momia
	 * 
	 * @param deltaTime tiempo desde la ultima llamada
	 */
	public void update(float deltaTime)
	{
		this.incAnimationDelta(deltaTime);
		this.incTimeInState(deltaTime);
		this.mummyState.update(deltaTime);

	}

	/**
	 * llama a super.move(v, b, deltaTime);
	 * 
	 */
	@Override
	protected void move(Vector2 v, boolean b, float deltaTime)
	{
		super.move(v, b, deltaTime);

	}

	/**
	 * Retorna la direccion hacia la que se dirige la momia
	 * 
	 * @return La direccion hacia la que se dirige la momia
	 */
	public Vector2 getDirection()
	{
		return direction;
	}

	@Override
	protected void setRenderMode(int renderMode)
	{
		super.setRenderMode(renderMode);
	}

	/**
	 * Incrementa el nivel de stress de la momia (si es muy alto la momia muere)
	 */
	protected void stressing()
	{
		this.stressLevel++;
	}

	/**
	 * Calma el nivel de stress de la momia
	 * 
	 * @param cant cantidad de stress que debe restar
	 */
	protected void calmStress(float cant)
	{
		this.stressLevel -= cant;
	}

	/**
	 * Retorna el nivel de stress de la momia
	 * 
	 * @return nivel de stress de la momia
	 */
	protected float getStressLevel()
	{
		return stressLevel;
	}

	/**
	 * pone el nivel de stress en cero
	 */
	protected void resetStress()
	{
		this.stressLevel = 0;

	}

	/**
	 * llama a super.resetAnimationDelta(); (su unico objetivo es hacer visible el
	 * metodo dentro del paquete)
	 */
	@Override
	protected void resetAnimationDelta()
	{
		super.resetAnimationDelta();
	}

	/**
	 * Delega en el metodo this.mummyState.die(mustTeleport); (patron state)
	 * 
	 * @param mustTeleport true si la momia debe teletransportarse al renacer, false
	 *                     en caso contrario.
	 */
	public void die(boolean mustTeleport)
	{

		this.mummyState.die(mustTeleport);
	}

	/**
	 * Delega en el metodo this.mummyState.isDanger(); (patron state)
	 * 
	 * @return true si la momia es peligrosa, false en caso contrario (esta en el
	 *         limbo, apareciendo o muriendo)
	 * 
	 */
	public boolean isDanger()
	{
		return this.mummyState.isDanger();
	}

	/**
	 * Llama super.enterStair(stair); (su unico objetivo es hacer visible el metodo
	 * dentro del paquete)
	 */
	@Override
	protected void enterStair(Stair stair)
	{

		super.enterStair(stair);
	}

	/**
	 * Retorna true si la momia puede saltar sin chocarse arriba, false en caso
	 * contrario
	 * 
	 * @return true si la momia puede saltar sin chocarse arriba, false en caso
	 *         contrario
	 */
	protected boolean canJump()
	{
		float posX;
		int offset;
		if (this.lookRight)
		{
			posX = this.x;
			offset = 1;
		} else
		{
			posX = this.x + this.width;
			offset = -1;
		}
		return (this.pyramid.getCell(posX, this.y, offset, 2) == null && this.isFreeUp());

	}

	/**
	 * Retorna el tiempo que permacio en el estado actual
	 * 
	 * @return El tiempo que permacio en el estado actual
	 */
	protected float getTimeInState()
	{
		return timeInState;
	}

	/**
	 * Pone en cero el tiempo en el estado actual
	 */
	protected void resetTimeInState()
	{
		this.timeInState = 0;
	}

	/**
	 * Incrementa el tiempo en el estaco actual
	 * 
	 * @param delta tiempo de incremento
	 */
	protected void incTimeInState(float delta)
	{
		this.timeInState += delta;
	}

	/**
	 * Retorna la posicion relativa del player a la momia actual
	 * 
	 * @return codigo numerico indicando la posicion relativa del player a la momia.
	 *         Puede tomar los valores: <br>
	 *         Mummy.PLAYER_IS_UP;<br>
	 *         Mummy.PLAYER_IS_DOWN;<br>
	 *         Mummy.PLAYER_IS_SOME_LEVEL;<br>
	 */
	protected int whereIsPlayer()
	{
		int whereIsPlayer;
		if (this.player.getLastFloorCoordinate() > this.getLastFloorCoordinate())// player esta arriba
		{
			whereIsPlayer = Mummy.PLAYER_IS_UP;

		} else if (this.player.getLastFloorCoordinate() < this.getLastFloorCoordinate())// player esta
		// abajo
		{
			whereIsPlayer = Mummy.PLAYER_IS_DOWN;
		} else

			whereIsPlayer = Mummy.PLAYER_IS_SOME_LEVEL; // Player esta a la misma altura.
		return whereIsPlayer;
	}

}
