package engine.gameCharacters.player;

import com.badlogic.gdx.math.Vector2;

import engine.level.GiratoryMechanism;

/**
 * Clase abstracta que representa el estado del Player (patron State).
 *
 * <p>
 * Esta jerarquia encapsula los distintos comportamientos posibles del personaje
 * controlado por el jugador, tales como movimiento, salto, uso de herramientas
 * y combate.
 * </p>
 *
 * <p>
 * A diferencia de la IA de los enemigos (MummyState), que toma decisiones
 * autonomas basadas en el entorno, PlayerState traduce el input del usuario en
 * acciones concretas dentro del mundo del juego.
 * </p>
 *
 * <p>
 * Esta clase define una maquina de estados reactiva que controla el flujo de
 * comportamiento del jugador, delegando en cada estado concreto la
 * interpretacion del input y la ejecucion de acciones asociadas.
 * </p>
 *
 * <p>
 * Desde el punto de vista arquitectonico, PlayerState actua como capa de
 * control entre el input del usuario y la logica de GameCharacter.
 * </p>
 *
 * @author Guillermo Lazzurri
 */
public abstract class PlayerState
{
	/**
	 * Contexto asociado al patron State.
	 */
	protected Player player;

	/**
	 * Constructor de clase.
	 *
	 * <p>
	 * Inicializa el estado del Player y configura el modo de renderizacion asociado
	 * al estado concreto.
	 * </p>
	 *
	 * @param player contexto asociado al patron State
	 * @param state  codigo de estado visual asociado al comportamiento actual.Puede
	 *               tomar los valores:<br>
	 *               Player.ST_DYING;<br>
	 *               Player.ST_WALKING;<br>
	 *               Player.ST_PICKING;<br>
	 *               Player.ST_THROWING_DAGGER;<br>
	 *               GameCharacter.ST_IDDLE<br>
	 */
	public PlayerState(Player player, int state)
	{
		this.player = player;
		player.setRenderMode(state);
		this.player.resetAnimationDelta();
	}

	/**
	 *
	 * Actualiza el comportamiento del Player en funcion del input del usuario.
	 *
	 * <p>
	 * Cada estado concreto interpreta la entrada de movimiento y accion para
	 * generar el comportamiento correspondiente dentro del juego.
	 * </p>
	 *
	 * @param movementDirection direccion de movimiento solicitada por el usuario
	 * @param action            indica si el usuario esta ejecutando una accion
	 * @param deltaTime         tiempo transcurrido desde la ultima actualizacion
	 */

	public abstract void update(Vector2 movementDirection, boolean action, float deltaTime);

	/**
	 * Gestiona la transicion del Player al estado de muerte.
	 */
	protected abstract void die();

	/**
	 * Gestiona la interaccion del Player con mecanismos giratorios del escenario.
	 *
	 * @param giratoryMechanism mecanismo con el que interactua el jugador
	 */
	protected abstract void passGiratoryMechanism(GiratoryMechanism giratoryMechanism);

	/**
	 * Ejecuta la accion de picar, si el estado actual lo permite.
	 */
	protected abstract void doPicker();

	/**
	 * Ejecuta el lanzamiento de la daga, si el estado actual lo permite.
	 */
	protected abstract void throwDagger();

}
