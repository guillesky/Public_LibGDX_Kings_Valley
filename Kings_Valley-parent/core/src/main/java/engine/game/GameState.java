package engine.game;

import engine.KVEventListener;
import engine.level.LevelReader;
import engine.level.door.Door;
import i18n.Messages;
import util.Constants;

/**
 * Clase base para los distintos estados globales del juego.
 *
 * <p>
 * Esta clase forma parte de la implementacion del patron State utilizado para
 * modelar las distintas etapas por las que puede atravesar una partida, tales
 * como ingreso a un nivel, juego activo, muerte del jugador, salida de un nivel
 * o finalizacion del juego.
 * </p>
 *
 * <p>
 * Cada subclase representa un estado concreto y encapsula el comportamiento
 * especifico asociado a dicha situacion, permitiendo evitar estructuras
 * complejas basadas en condicionales para gestionar el flujo general del juego.
 * </p>
 *
 * <p>
 * Ademas de definir el comportamiento comun a todos los estados, esta clase
 * mantiene una referencia al objeto Game, que actua como contexto del patron
 * State y almacena la informacion global de la partida.
 * </p>
 *
 * <p>
 * Las transiciones entre estados se realizan mediante la sustitucion de la
 * instancia actual por otra subclase de GameState, delegando en cada estado la
 * responsabilidad de determinar la evolucion del ciclo de vida del juego.
 * </p>
 *
 * <p>
 * Esta clase tambien proporciona implementaciones comunes utilizadas por
 * multiples estados concretos, incluyendo la gestion de niveles y ciertas
 * transiciones del flujo general de la partida.
 * </p>
 *
 * <p>
 * Desde el punto de vista arquitectonico, esta clase contribuye a separar las
 * reglas asociadas a cada etapa de la partida, favoreciendo una implementacion
 * modular, extensible y facil de mantener.
 * </p>
 *
 * @author Guillermo Lazzurri
 */

public abstract class GameState
{
    /**
     * Contexto asociado al patron State.
     */
    protected Game game;

    /**
     * Actualiza la logica asociada al estado actual del juego.
     *
     * @param deltaTime tiempo transcurrido, expresado en segundos, desde la ultima
     *                  actualizacion.
     */
    public void updateframe(float deltaTime)
    {
	this.game.incDelta(deltaTime);
    }

    /**
     * Constructor de la clase.
     *
     * <p>
     * Inicializa la referencia al contexto del patron State y reinicia el
     * acumulador de tiempo utilizado por el juego.
     * </p>
     */
    public GameState()
    {
	this.game = Game.getInstance();
	this.game.resetDelta();
    }

    /**
     * Retorna el modo de renderizacion asociado al estado actual.
     *
     * <p>
     * El valor devuelto determina la forma en que la capa grafica debe representar
     * la transicion o situacion actual del juego.
     * </p>
     *
     * @return codigo de renderizacion definido por Game.
     */
    public abstract int getRenderMode();

    /**
     * Inicializa el comportamiento asociado al comienzo de una nueva partida.
     */
    public abstract void startNewGame();

    /**
     * Finaliza la partida y realiza la transicion al estado de cierre del juego.
     */
    public void endGame()
    {

	this.game.stateGame = new GameStateEndingGame();
    }

    /**
     * Inicializa y carga un nuevo nivel del juego.
     *
     * <p>
     * Este metodo se encarga de liberar el nivel actual, cargar la nueva
     * instancia correspondiente, generar los eventos asociados a la
     * transicion y configurar el estado inicial de ingreso al nivel.
     * </p>
     *
     * @param door puerta utilizada para acceder al nuevo nivel. Puede ser
     *             null cuando no existe un nivel previo o cuando el jugador
     *             reaparece luego de morir.
     * @param fromDeath indica si la carga del nivel se produce como
     *                  consecuencia de la muerte del jugador.
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
     * Gestiona el comportamiento asociado a la muerte del jugador dentro
     * del estado actual.
     */
    protected abstract void dying();

}
