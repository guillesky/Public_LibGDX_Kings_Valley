package engine.level.dagger;

import java.util.ArrayList;

import engine.gameCharacters.mummys.Mummy;
import engine.level.LevelObject;
import engine.level.Pyramid;
import util.Constants;

/**
 * Representa una daga dentro del juego.
 *
 * <p>
 * La daga es un objeto interactivo del nivel que puede encontrarse en estado
 * estatico (clavada), ser recogida por el jugador o ser lanzada como proyectil
 * en distintas direcciones.
 * </p>
 *
 * <p>
 * Su comportamiento se modela mediante el patron State, delegando toda la
 * logica de actualizacion, movimiento e interaccion a su estado actual.
 * </p>
 *
 * <p>
 * La daga puede interactuar con el jugador, el entorno del nivel y las momias,
 * pudiendo ser recolectada o causar efectos al colisionar.
 * </p>
 *
 * @author Guillermo Lazzurri
 */
@SuppressWarnings("serial")
public class Dagger extends LevelObject
{

	/**
	 * Estado de la daga (Patron State)
	 */
	private DaggerState daggerState = null;

	/**
	 * Indica hacia que se lanza la espada
	 */
	private boolean isRight;

	/**
	 * Crea una daga en el nivel en estado inicial clavado.
	 *
	 * @param x      posicion horizontal inicial
	 * @param y      posicion vertical inicial
	 * @param p0     parametro adicional del tilemap (no utilizado directamente)
	 * @param width  ancho del objeto
	 * @param height alto del objeto
	 */
	public Dagger(float x, float y, int p0, float width, float height)
	{
		super(Constants.IT_DAGGER, x, y, p0, width, height);
		this.daggerState = new DaggerStateStucked(this);

	}

	/**
	 * Retorna el modo de render actual de la daga segun su estado interno.
	 *
	 * <p>
	 * Este valor es utilizado por el sistema de renderizado para determinar la
	 * animacion o representacion visual correspondiente.
	 * </p>
	 *
	 * @return codigo de render segun el estado actual
	 */

	public int getRenderMode()
	{
		return this.daggerState.getRenderMode();
	}

	/**
	 * Indica la orientacion actual de la daga.
	 *
	 * @return true si la daga esta orientada hacia la derecha, false en caso
	 *         contrario
	 */
	public boolean isRight()
	{
		return isRight;
	}

	/**
	 * Llamado para lanzar la daga Horizontalmente. Delega en el atributo
	 * daggerState (patron state)
	 * 
	 * @param isRight true si la espada es lanzada hacia la derecha, false si es
	 *                lanzada hacia la izquierda
	 */
	public void throwHorizontal(boolean isRight)
	{
		this.isRight = isRight;
		this.daggerState.throwHorizontal();
	}

	/**
	 * Actualiza el estado de la daga.
	 *
	 * <p>
	 * Este metodo delega completamente la logica de actualizacion al estado
	 * actual de la daga, incluyendo movimiento, colisiones y transiciones
	 * de estado.
	 * </p>
	 *
	 * @param deltaTime tiempo desde la ultima actualizacion
	 * @param pyramid estructura del nivel
	 * @param mummys lista de momias para deteccion de colisiones
	 */
	public void updateDagger(float deltaTime, Pyramid pyramid, ArrayList<Mummy> mummys)
	{

		this.daggerState.updateDagger(deltaTime, pyramid, mummys);

	}

	/**
	 * Setea el estado de la daga (solo deberia usarse mediante patron state)
	 * 
	 * @param daggerState nuevo estado de la daga.
	 */
	protected void setDaggerState(DaggerState daggerState)
	{
		this.daggerState = daggerState;
	}

	/**
	 * Notifica a la daga que ha sido recogida.
	 *
	 * <p>
	 * Este evento es gestionado por el estado actual de la daga.
	 * </p>
	 */
	public void hasPickuped()
	{
		this.daggerState.hasPickuped();

	}

	/**
	 * Llamado para lanzar la daga Verticalmente (hacia arriba). Delega en el
	 * atributo daggerState (patron state)
	 * 
	 * @param isRight true si la espada es lanzada hacia la derecha, false si es
	 *                lanzada hacia la izquierda
	 */

	public void throwVertical(boolean isRight)
	{
		this.isRight = isRight;
		this.daggerState.throwVertical();
	}

}
