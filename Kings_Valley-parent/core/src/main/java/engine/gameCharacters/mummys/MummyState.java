package engine.gameCharacters.mummys;

/**
 * Clase abstracta que representa el estado de la momia (patron state)
 * 
 * @author Guillermo Lazzurri
 */
public abstract class MummyState

{

	/**
	 * Codigo que indica direccion derecha
	 */

	protected static final int RIGHT = 1;
	/**
	 * Codigo que indica direccion izquierda
	 */
	protected static final int LEFT = -1;

	/**
	 * Codigo indicando que hay un bloqueo por ladrillos
	 */
	protected static final int BLOCK_BRICK = 1;
	/**
	 * Inica cuanto tiempo debe permanecer en este estado antes de cambiar
	 */
	protected float timeToChange;
	/**
	 * Corresponde al sujeto del patron state
	 */
	protected Mummy mummy;

	/**
	 * Contructor de clase
	 * 
	 * @param mummy Momia a la que pertences el estado (patron state)
	 * @param state tipo de estado
	 */
	public MummyState(Mummy mummy, int state)
	{
		this.mummy = mummy;
		mummy.setRenderMode(state);
		mummy.resetAnimationDelta();
	}

	/**
	 * Contructor de clase
	 * 
	 * @param mummy Contexto del patron state
	 */
	public MummyState(Mummy mummy)
	{
		this.mummy = mummy;
		mummy.resetAnimationDelta();
	}

	/**
	 * Metodo llamado al actualizar la momia
	 * 
	 * @param deltaTime tiempo transcurrido desde la ultima actualizacion
	 */
	public abstract void update(float deltaTime);

	/**
	 * Retorna true si la momia esta esta activa (es peligrosa o vulnerable a las espadas), false en caso contrario
	 * 
	 * @return true si la momia esta activa, false en caso contrario
	 */
	protected abstract boolean isActive();

	/**
	 * Evento que podria pasar a modo muerto.
	 * 
	 * @param mustTeleport true si la momia debe teletransportarse al reaparecer,
	 *                     false en caso contrarioF
	 */
	protected abstract void die(boolean mustTeleport);

	
}
