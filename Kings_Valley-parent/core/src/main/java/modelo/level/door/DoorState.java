package modelo.level.door;

import modelo.gameCharacters.player.Player;

/**
 * @author Guillermo Lazzurri
 * 
 * Clase que representa el estado de la puerta (patron state)
 */
public abstract class DoorState
{
	protected Door door;

	/**
	 * Constructor de clase
	 * @param door Corresponde al sujeto del patron state
	 * @param state codigo numerico para representar el estado
	 */
	public DoorState(Door door, int state)
	{

		this.door = door;
		this.door.state=state;
		this.door.resetTime();
		
	}

	/**
	 * Llamado para actualizar la puerta
	 * @param deltaTime tiempo transcurrido desde la ultima llamada
	 */
	public abstract void update(float deltaTime);

	/**
	 * Llamado para verificar si el player activo la palanca
	 * @param player representa al player
	 */
	public abstract void checkPushLever(Player player);

	/**
	 * Llamado para indicar si el player entro al pasage
	 * @param player representa al player
	 * @return true si entro al pasaje, false en caso contrario
	 */
	public abstract boolean checkEnterPassage(Player player);
	
	/**
	 * Llamado para poner visible la puerta
	 */
	protected abstract void setVisible();

}
