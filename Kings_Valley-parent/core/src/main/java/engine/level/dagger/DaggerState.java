package engine.level.dagger;

import java.util.ArrayList;

import engine.gameCharacters.mummys.Mummy;
import engine.level.Pyramid;
import util.GameRules;

/**
 * Representa la clase base de los estados de comportamiento de una daga.
 *
 * <p>
 * Esta clase implementa el patron State para encapsular los distintos
 * comportamientos dinamicos de una daga dentro del juego, incluyendo su
 * movimiento, interaccion con el entorno, colisiones y transiciones de estado.
 * </p>
 *
 * <p>
 * Cada estado concreto define reglas especificas de actualizacion y puede
 * modificar el comportamiento de la daga de acuerdo a su situacion actual
 * (clavada, lanzada, en caida, rebotando o recolectada).
 * </p>
 *
 * <p>
 * El estado tambien define el modo de renderizado asociado a cada
 * comportamiento, permitiendo que la representacion visual sea consistente con
 * la logica interna.
 * </p>
 *
 * @author Guillermo Lazzurri
 */
public abstract class DaggerState
{
	/**
	 * Daga clavada en el piso
	 */
	public static final int ST_STUCKED = 0;
	/**
	 * Daga Lanzada horizontalmente
	 */
	public static final int ST_THROWING_HORIZONTAL = 1;
	/**
	 * Daga Cayendo libremente
	 */

	public static final int ST_FALLING = 2;
	/**
	 * Daga lanzada hacia arriba
	 */

	public static final int ST_THROWING_UP = 3;
	/**
	 * Daga rebotando
	 */

	public static final int ST_BOUNCING = 4;
	/**
	 * Daga siendo portada por el player
	 */

	public static final int ST_PICKUPED = 5;

	/**
	 * Contexto asociado al patron State.
	 */
	protected Dagger dagger;

	/**
	 * tiempo desde que comenzo el estado
	 */
	protected float delta;

	/**
	 * Constructor de clase.
	 * 
	 * @param dagger Contexto del patron state
	 * 
	 */
	public DaggerState(Dagger dagger)
	{
		this.dagger = dagger;

		this.delta = 0;
	}

	/**
	 * Actualiza el comportamiento de la daga en su estado actual.
	 *
	 * <p>
	 * Este metodo es invocado en cada frame y es responsable de ejecutar la logica
	 * especifica del estado, incluyendo movimiento, colisiones y transiciones.
	 * </p>
	 *
	 * @param deltaTime tiempo transcurrido desde la ultima actualizacion
	 * @param pyramid   estructura del nivel
	 * @param mummys    lista de momias para deteccion de colisiones
	 */
	public abstract void updateDagger(float deltaTime, Pyramid pyramid, ArrayList<Mummy> mummys);

	/**
	 * Ajusta una coordenada al centro del tile mas cercano.
	 *
	 * <p>
	 * Se utiliza para alinear la daga a la grilla del nivel cuando se clava en una
	 * posicion valida.
	 * </p>
	 */
	private float roundCoord(float value, float factor)
	{
		float r;
		float i = (int) ((value + factor / 2) / factor);
		r = i * factor;
		return r;
	}

	/**
	 * Alinea la posicion X de la daga al sistema de grilla del nivel.
	 */
	protected void roundX()
	{

		dagger.x = this.roundCoord(dagger.x, GameRules.getInstance().getLevelTileWidthUnits());

	}

	/**
	 * Alinea la posicion Y de la daga al sistema de grilla del nivel.
	 */
	protected void roundY()
	{

		dagger.y = this.roundCoord(dagger.y, GameRules.getInstance().getLevelTileHeightUnits());

	}

	/**
	 * Retorna un codigo numerico que indica como sera la renderizacion de acuerdo
	 * al estado de la espada.<br>
	 * DaggerState.ST_STUCKED = 0; <br>
	 * DaggerState.ST_THROWING_HORIZONTAL = 1;<br>
	 * DaggerState.ST_FALLING = 2;<br>
	 * DaggerState.ST_THROWING_UP = 3;<br>
	 * DaggerState.ST_BOUNCING = 4; <br>
	 * DaggerState.ST_PICKUPED = 5;<br>
	 * 
	 * @return el codigo numerico que indica como sera la renderizacion de acuerdo
	 *         al estado de la espada.
	 */
	public abstract int getRenderMode();

	/**
	 * Actualiza la posicion horizontal de la daga segun su direccion actual.
	 *
	 * @param delta desplazamiento a aplicar en el eje X
	 */

	protected void updateX(float delta)
	{
		if (this.dagger.isRight())
			this.dagger.x += delta;
		else
			this.dagger.x -= delta;

	}

	/**
	 * Incrementa el tiempo acumulado en el estado actual.
	 *
	 * @param inc tiempo a sumar al estado actual
	 */
	protected void incDelta(float inc)
	{
		this.delta += inc;
	}

	/**
	 * Cambia al estado de lanzamiento horizontal.
	 */
	protected abstract void throwHorizontal();

	/**
	 * Cambia al estado de recoleccion de la daga.
	 */
	protected abstract void hasPickuped();

	/**
	 * Cambia al estado de lanzamiento vertical.
	 */
	protected abstract void throwVertical();

}
