package engine.gameCharacters.mummys;

import java.util.Iterator;

import com.badlogic.gdx.math.Vector2;

import engine.KVEventListener;
import engine.game.Game;
import engine.gameCharacters.abstractGameCharacter.GameCharacter;
import engine.level.LevelObject;
import engine.level.Pyramid;
import engine.level.Stair;
import util.GameRules;
import util.ProbabilisticSelector;

/**
 * Clase que representa del estado de la momia "Decidiendo"
 * 
 * @author Guillermo Lazzurri
 */
public class MummyStateDeciding extends MummyState
{
    /**
     * Codigo que indica direccion derecha
     */

    private static final int RIGHT = 1;
    /**
     * Codigo que indica direccion izquierda
     */
    private static final int LEFT = -1;

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
     * @param mummy Correspondiente al sujeto del patron state
     */
    public MummyStateDeciding(Mummy mummy)
    {
	super(mummy, Mummy.ST_IDDLE);
	this.timeToChange = this.mummy.getTimeDeciding();

	this.update(0);

    }

    /**
     * Si pasa el tiempo corresponediente a this.timeToChange entonces llama a
     * this.changeStatus();
     */
    @Override
    public void update(float deltaTime)
    {
	this.mummy.move(new Vector2(), false, deltaTime);

	if (this.mummy.getTimeInState() >= this.timeToChange && (this.mummy.getState() == GameCharacter.ST_WALKING
		|| this.mummy.getState() == GameCharacter.ST_IDDLE))
	{
	    this.changeStatus();
	}

    }

    /**
     * De acuerdo a la posicion del player puede pasar a los estados
     * MummyStateSearchingStair o MummyStateWalking
     */
    private void changeStatus()
    {
	PlatformAnalysisResult result = new PlatformAnalysisResult(mummy);
	Stair playerStair = this.mummy.player.getStair();
	boolean takedDecision = false;
	// Si el player esta en una escalera a la que puede acceder la momia desde esta
	// plataforma, va hacia la escalera
	if (result.getDownStairsInPlatform().contains(playerStair)
		|| result.getUpStairsInPlatform().contains(playerStair))
	{
	    this.mummy.mummyState = new MummyStateSearchingStair(this.mummy, playerStair);
	    takedDecision = true;
	} else // El player no esta en una escalera accesible desde la plataforma
	{

	    ProbabilisticSelector selector = new ProbabilisticSelector(Game.random);
	    int whereIsPlayer;
	    if (this.mummy.player.getLastFloorCoordinate() > this.mummy.getLastFloorCoordinate())// player esta arriba
	    {
		whereIsPlayer = MummyState.PLAYER_IS_UP;

	    } else if (this.mummy.player.getLastFloorCoordinate() < this.mummy.getLastFloorCoordinate())// player esta
	    // abajo
	    {
		whereIsPlayer = MummyState.PLAYER_IS_DOWN;
	    } else
	    {
		whereIsPlayer = MummyState.PLAYER_IS_SOME_LEVEL; // Player esta a la misma altura.
		if (result.isCharacterReachable(mummy.player)) // Si el player es alcanzable en la plataforma seguro
							       // tomare una decision deterministica
		{
		    takedDecision = true;
		    if (this.mummy.x > this.mummy.player.x) // Si el player esta a la izquierda voy hacia alli
			this.mummy.mummyState = new MummyStateWalking(this.mummy, LEFT,
				MummyState.PLAYER_IS_SOME_LEVEL);
		    else // Sino voy hacia la derecha
			this.mummy.mummyState = new MummyStateWalking(this.mummy, RIGHT,
				MummyState.PLAYER_IS_SOME_LEVEL);

		}

	    }

	    this.addLeftAndRightProbability(result, selector, whereIsPlayer);

	    if (result.getNearestUpStair() != null) // Si hay una escalera cercana que suba

	    {
		switch (whereIsPlayer)
		{
		case PLAYER_IS_UP:
		    if (this.mummy.player.getLastFloorCoordinate() >= result.getNearestUpStair().getUpStair().y)
		    {
			// Si el player esta mas arriba o a la misma altura que la parte superior de la
			// escalera, busco la escalera.
			this.mummy.mummyState = new MummyStateSearchingStair(this.mummy, result.getNearestUpStair());
			takedDecision = true;
		    } else // Si no, entonces considero la posibilidad de tomar la escalera
		    {
			selector.add(0.5, GO_UP_STAIR);
		    }
		    break;
		case PLAYER_IS_DOWN:
		    selector.add(0.15, GO_UP_STAIR);
		    break;
		case PLAYER_IS_SOME_LEVEL:
		    selector.add(0.25, GO_UP_STAIR);
		    break;
		}
	    }

	    if (result.getNearestDownStair() != null) // Si hay una escalera cercana que baje

	    {
		switch (whereIsPlayer)
		{
		case PLAYER_IS_DOWN:
		    if (this.mummy.player.getLastFloorCoordinate() <= result.getNearestDownStair().getDownStair().y)
		    {
			// Si el player esta mas abajo o a la misma altura que la parte superior de la
			// escalera, busco la escalera.
			this.mummy.mummyState = new MummyStateSearchingStair(this.mummy, result.getNearestDownStair());
			takedDecision = true;
		    } else // Si no, entonces considero la posibilidad de tomar la escalera
		    {
			selector.add(0.5, GO_DOWN_STAIR);
		    }
		    break;
		case PLAYER_IS_UP:
		    selector.add(0.15, GO_DOWN_STAIR);
		    break;
		case PLAYER_IS_SOME_LEVEL:
		    selector.add(0.25, GO_DOWN_STAIR);
		    break;
		}
	    }
	    if (!takedDecision) // Si no pude tomar una decision con seguridad voy a elegir una opcion al azar
	    // de entre las posibles
	    {
		Integer decision = (Integer) selector.getValue();
		if (decision != null) // Si hay alguna opcion, es decir, no estoy encerrado con el player fuera de mi
				      // alcance
		{
		    switch (decision)
		    {
		    case GO_UP_STAIR:
			this.mummy.mummyState = new MummyStateSearchingStair(this.mummy, result.getNearestUpStair());
			break;
		    case GO_DOWN_STAIR:
			this.mummy.mummyState = new MummyStateSearchingStair(this.mummy, result.getNearestDownStair());
			break;
		    case LEFT:
			this.mummy.mummyState = new MummyStateWalking(this.mummy, LEFT,
				MummyState.PLAYER_IS_SOME_LEVEL);

			break;
		    case RIGHT:
			this.mummy.mummyState = new MummyStateWalking(this.mummy, RIGHT,
				MummyState.PLAYER_IS_SOME_LEVEL);

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

    /**
     * Retorna true
     */
    @Override
    protected boolean isDanger()
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
	case MummyState.PLAYER_IS_UP:
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

	case MummyState.PLAYER_IS_DOWN:
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

	case MummyState.PLAYER_IS_SOME_LEVEL:
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
