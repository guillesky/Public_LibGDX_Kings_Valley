package engine.game;

import engine.KVEventListener;
import engine.level.LevelReader;
import engine.level.door.Door;
import i18n.Messages;
import util.Constants;

/**
 * Clase abstracta que representa un estado del juego (patron State)
 * 
 * @author Guillermo Lazzurri
 */
public abstract class GameState
{
    /**
     * Sujeto del patron state
     */
    protected Game game;

    /**
     * Actualiza un fram del juego.
     * 
     * @param deltaTime indica cuanto tiempo transcurrio desde la ultima
     *                  actualizacion medido en segundos
     */
    public void updateframe(float deltaTime)
    {
	this.game.incDelta(deltaTime);
    }

    /**
     * Constructor de clase. Gestiona la doble referencia del patron state. Resetea
     * el delta del juego.
     */
    public GameState()
    {
	this.game = Game.getInstance();
	this.game.resetDelta();
    }

    /**
     * Retorna un codigo numerico que indica como sera la renderizacion de acuerdo
     * al estado del juego <br>
     * ST_GAME_PLAYING = 0 <br>
     * ST_GAME_ENTERING = 1<br>
     * ST_GAME_EXITING = 2<br>
     * ST_GAME_DYING = 3<br>
     * ST_NOT_IN_GAME = 100<br>
     * ST_ENDING = 99<br>
     * 
     * @return codigo numerico que indica como sera la renderizacion de acuerdo al
     *         estado del juego<br>
     *         ST_GAME_PLAYING = 0 <br>
     *         ST_GAME_ENTERING = 1<br>
     *         ST_GAME_EXITING = 2<br>
     *         ST_GAME_DYING = 3<br>
     *         ST_NOT_IN_GAME = 100<br>
     *         ST_ENDING = 99<br>
     */
    public abstract int getRenderMode();

    /**
     * Llamado al iniciar un nuevo juego
     * 
     */
    public abstract void startNewGame();

    /**
     * Llamado al terminar el juego
     */
    public void endGame()
    {

	this.game.stateGame = new GameStateEndingGame();
    }

    /**
     * Metodo llamado para iniciar un nivel
     * 
     * @param door      Indica la puerta de origen, es decir, la puerta por la que
     *                  el player salio del nivel que termino. En caso de no venir
     *                  de un nivel anterior o de morir, este paremtro podria ser
     *                  null.
     * @param fromDeath Indica true si el player acaba de morir y esta reiniciando
     *                  el nivel, false en caso contrario
     */
    protected void startNewLevel(Door door, boolean fromDeath)
    {

	StringBuilder sb = new StringBuilder();
	String entering_next = Messages.ENTERING_PYRAMID.getValue();
	String entering_prior = Messages.GOING_BACK_PYRAMID.getValue();
	LevelReader levelReader = new LevelReader();
	if (this.game.level != null)
	    this.game.level.dispose();

	if (this.game.levelFileName.get(this.game.getIdCurrentLevel()) == null)
	{
	    this.game.eventFired(KVEventListener.FINISH_ALL_LEVELS, null);
	    sb.append(Messages.FINISH_GAME.getValue());
	    sb.append("\n");
	}

	switch (this.game.getGameType())
	{
	case Game.GAME_TYPE_CLASSIC:
	    if (this.game.getIdCurrentLevel() == Constants.LAST_CLASSIC_LEVEL + 1)
	    {
		sb.append(Messages.ENTER_GOAL_LEVEL.getValue());
		sb.append("\n");
	    }
	    break;
	case Game.GAME_TYPE_EXTENDED:
	    if (door != null && (this.game.getIdCurrentLevel() == 16 || this.game.getIdCurrentLevel() == 31
		    || this.game.getIdCurrentLevel() == 46))
	    {
		this.game.eventFired(KVEventListener.FINISH_EPISODE, (int) (this.game.getIdCurrentLevel() / 15));
		sb.append(Messages.FINISH_EPISODE.getValue());
		sb.append("\n");

	    } else if (this.game.getIdCurrentLevel() == Constants.LAST_EXTENDED_LEVEL + 1)
	    {
		sb.append(Messages.ENTER_GOAL_LEVEL.getValue());
		sb.append("\n");
	    }
	    break;
	case Game.GAME_TYPE_GREAT_TEMPLE:
	    entering_next = Messages.ENTERING_GREAT_TEMPLE.getValue();
	    entering_prior = Messages.GOING_BACK_GREAT_TEMPLE.getValue();
	    if (this.game.getIdCurrentLevel() == Constants.LAST_GREAT_TEMPLE_LEVEL + 1)
	    {
		sb.append(Messages.ENTER_GOAL_LEVEL.getValue());
		sb.append("\n");
	    } else if (door != null)
	    {
		this.game.eventFired(KVEventListener.FINISH_GREAT_TEMPLE, door.getIdLevel());

	    }

	}

	this.game.level = levelReader.getLevel(this.game.getIdCurrentLevel(),
		this.game.levelFileName.get(this.game.getIdCurrentLevel()), this.game.getDificultLevel(),
		this.game.completedLevels.get(this.game.getIdCurrentLevel()), door, fromDeath, this.game.getInterfaz());
	this.game.stateGame = new GameStateEntering();
	this.game.getInterfaz().inicialize();

	if ((door != null && door.getLevelConnected() == Door.TO_PREVIUS))
	    sb.append(entering_prior);
	else
	    sb.append(entering_next);
	sb.append(this.game.getCurrentLevel().getId());

	this.game.setTextToEnterLevel(sb.toString());

    }

    /**
     * Llamado al morir el player
     */
    protected abstract void dying();

}
