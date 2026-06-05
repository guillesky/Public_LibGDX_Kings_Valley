package engine.gameCharacters.mummys;

/**
 * Clase base para los distintos estados de comportamiento de una momia.
 *
 * <p>
 * Esta clase implementa el patron State para encapsular las diferentes
 * conductas que puede adoptar una momia durante el juego, tales como perseguir
 * al jugador, reaparecer, permanecer temporalmente inactiva o gestionar su
 * destruccion.
 * </p>
 *
 * <p>
 * A diferencia de GameCharacterState, que modela los distintos modos de
 * movimiento de un personaje (caminar, saltar, caer, subir escaleras, etc.),
 * MummyState representa el comportamiento especifico de la IA de las momias y
 * las reglas asociadas a su ciclo de vida.
 * </p>
 *
 * <p>
 * Cada estado concreto define sus propias reglas de actualizacion, transiciones
 * y condiciones de activacion, permitiendo desacoplar los distintos
 * comportamientos de las momias en componentes independientes.
 * </p>
 * <p>
 ** Las decisiones de movimiento concreto continúan siendo responsabilidad de los
 * estados derivados de GameCharacterState. Esta jerarquia modela exclusivamente
 * el comportamiento general y las decisiones propias de las momias.
 * </p>
 * <p>
 * El contexto asociado al patron State es la clase Mummy, que delega en el
 * estado actual la responsabilidad de actualizar su comportamiento.
 * </p>
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
	 * Tiempo restante, expresado en segundos, antes de efectuar una transicion a
	 * otro estado.
	 */

	protected float timeToChange;
	/**
	 * Contexto asociado al patron State.
	 */
	protected Mummy mummy;

	/**
	 * Constructor de la clase.
	 *
	 * @param mummy contexto asociado al patron State.
	 * @param state codigo de renderizacion asociado al estado concreto.
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
	 * @param mummy Contexto asociado al patron State
	 */
	public MummyState(Mummy mummy)
	{
		this.mummy = mummy;
		mummy.resetAnimationDelta();
	}

	/**
	 * Actualiza el comportamiento correspondiente al estado actual.
	 *
	 * @param deltaTime tiempo transcurrido, expresado en segundos, desde la ultima
	 *                  actualizacion.
	 */
	public abstract void update(float deltaTime);

	/**
	 * Indica si la momia se encuentra activa dentro del juego.
	 *
	 * <p>
	 * Una momia activa puede interactuar normalmente con el entorno, representar
	 * una amenaza para el jugador y resultar vulnerable a determinados ataques.
	 * </p>
	 *
	 * @return true si la momia esta activa, false en caso contrario.
	 */
	protected abstract boolean isActive();

	/**
	 * Gestiona la transicion al estado asociado a la destruccion o desactivacion de
	 * la momia.
	 *
	 * @param mustTeleport indica si la momia debera reaparecer mediante
	 *                     teletransportacion o utilizando el mecanismo normal de
	 *                     reaparicion.
	 */
	protected abstract void die(boolean mustTeleport);

}
