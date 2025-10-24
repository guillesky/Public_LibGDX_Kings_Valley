package engine.gameCharacters.player;

import com.badlogic.gdx.math.Vector2;

import engine.level.GiratoryMechanism;

/**
 * 
 * Clase que representa el estado del Player (patron state)
 * 
 * @author Guillermo Lazzurri
 */
public abstract class PlayerState
{
	/**
	 * Sujeto del patron State
	 */
	protected Player player;

	/**
	 * Constructor de clase
	 * 
	 * @param player Contexto del patron state
	 * @param state  indica el tipo de estado. Puede tomar los valores:
	 *               Player.ST_DYING; Player.ST_WALKING; Player.ST_PICKING;
	 *               Player.ST_THROWING_DAGGER; GameCharacter.ST_IDDLE
	 */
	public PlayerState(Player player, int state)
	{
		this.player = player;
		player.setState(state);
		this.player.resetAnimationDelta();
	}

	/**
	 * Llamado para actualizar al player.
	 * 
	 * @param v         Indica la direccion pretendida.
	 * @param b         true si intenta realizar una accion, false en caso contrario
	 * @param deltaTime Tiempo transcurrido desde la ultima actualizacion
	 */
	public abstract void update(Vector2 v, boolean b, float deltaTime);

	/**
	 * Llamado cuando el player muere
	 */
	protected abstract void die();

	/**
	 * Llamado al pasar por una puerta giratoria
	 * 
	 * @param giratoryMechanism Puerta giratoria por la que se esta pasando
	 */
	protected abstract void passGiratoryMechanism(GiratoryMechanism giratoryMechanism);

	/**
	 * Llamado al intentar picar
	 */
	protected abstract void doPicker();

	/**
	 * Llamado al intentar lanzar la espada
	 */
	protected abstract void throwDagger();

}
