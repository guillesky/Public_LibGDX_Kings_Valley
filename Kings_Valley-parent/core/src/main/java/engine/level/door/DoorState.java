package engine.level.door;

import engine.gameCharacters.player.Player;

/**
 * Clase que representa el estado de la puerta (patron state)
 * 
 * @author Guillermo Lazzurri
 */
public abstract class DoorState
{
	/**
	 * Corresponde al sujeto del patron state
	 */
	protected Door door;

	/**
	 * Constructor de clase
	 * 
	 * @param door  Contexto del patron state
	  */
	public DoorState(Door door)
	{

		this.door = door;
		this.door.resetTime();

	}

	/**
	 * Llamado para actualizar la puerta
	 * 
	 * @param deltaTime tiempo transcurrido desde la ultima llamada
	 */
	public abstract void update(float deltaTime);

	/**
	 * Llamado para verificar si el player activo la palanca
	 * 
	 * @param player representa al player
	 */
	public abstract void checkPushLever(Player player);

	/**
	 * Llamado para indicar si el player entro al pasage
	 * 
	 * @param player representa al player
	 * @return true si entro al pasaje, false en caso contrario
	 */
	public abstract boolean checkEnterPassage(Player player);

	/**
	 * Llamado para poner visible la puerta
	 */
	protected abstract void setVisible();
	
	/**
	 * Retorna un codigo numerico que indica como sera la renderizacion de acuerdo
	 * al estado de la puerta.<br>
	 * 
	 * Puede tomar los valores:<br>
	 * HIDE = 0;<br>
	 * CLOSED = 1<br>
	 * OPEN = 2<br>
	 * CLOSING = 3<br>
	 * OPENING = 4<br>
	 * 
	 * @return un codigo numerico que indica como sera la renderizacion de acuerdo
	 *         al estado de la puerta.
	 */
	public abstract int getRenderMode();
}
