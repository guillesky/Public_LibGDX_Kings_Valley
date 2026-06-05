package engine.level.door;

import engine.gameCharacters.player.Player;

/**
 * Representa un estado de la puerta dentro del patron State.
 *
 * <p>
 * Encapsula el comportamiento dinamico de la puerta, incluyendo su
 * actualizacion temporal, su interaccion con el jugador y su estado de
 * renderizado.
 * </p>
 *
 * <p>
 * Cada implementacion concreta define una etapa del ciclo de vida de la puerta
 * (oculta, abierta, cerrandose, abriendose, etc.), controlando tanto su logica
 * como su representacion visual.
 * </p>
 *
 * <p>
 * El estado opera sobre una instancia de Door, modificando su comportamiento
 * segun la logica interna de cada subestado.
 * </p>
 *
 * @author Guillermo Lazzurri
 */
public abstract class DoorState
{
	/**
	 * Contexto asociado al patron State.
	 */
	protected Door door;

	/**
	 * Construye un estado de puerta asociado a un contexto Door.
	 *
	 * <p>
	 * Al crearse el estado, se reinicia el temporizador interno de la puerta, ya
	 * que cada estado define su propia duracion o ciclo de vida.
	 * </p>
	 *
	 * @param door contexto del patron State
	 */
	public DoorState(Door door)
	{

		this.door = door;
		this.door.resetTime();

	}

	/**
	 * Actualiza la logica interna del estado de la puerta.
	 *
	 * <p>
	 * Este metodo controla la evolucion temporal del estado, incluyendo
	 * transiciones a otros estados cuando corresponda.
	 * </p>
	 *
	 * @param deltaTime tiempo transcurrido desde la ultima actualizacion
	 */
	public abstract void update(float deltaTime);

	/**
	 * Evalua la interaccion del jugador con la palanca de la puerta.
	 *
	 * <p>
	 * Cada estado define si la interaccion es valida y como reacciona la puerta
	 * ante dicha accion.
	 * </p>
	 *
	 * @param player jugador que intenta activar la palanca
	 */
	public abstract void checkPushLever(Player player);

	/**
	 * Evalua si el jugador ingreso al pasaje de la puerta.
	 *
	 * <p>
	 * El resultado depende del estado actual de la puerta, ya que no todos los
	 * estados permiten el paso.
	 * </p>
	 *
	 * @param player jugador a evaluar
	 * @return true si el jugador ingreso al pasaje, false en caso contrario
	 */

	public abstract boolean checkEnterPassage(Player player);

	/**
	 * Define la visibilidad de la puerta en este estado.
	 *
	 * <p>
	 * Cada estado decide si la puerta debe ser visible o no en el mundo del juego.
	 * </p>
	 */
	protected abstract void setVisible();

	/**
	 * Retorna el modo de renderizado asociado al estado actual de la puerta.
	 *
	 * <p>
	 * Este valor determina como se dibuja la puerta en pantalla y refleja su estado
	 * logico (oculta, abierta, cerrandose, etc.).Puede tomar los valores:<br>
	 * HIDE = 0;<br>
	 * CLOSED = 1<br>
	 * OPEN = 2<br>
	 * CLOSING = 3<br>
	 * OPENING = 4<br>
	 * </p>
	 *
	 * @return codigo de render asociado al estado actual
	 */

	public abstract int getRenderMode();
}
