package modelo.level.dagger;

import java.util.ArrayList;

import modelo.gameCharacters.mummys.Mummy;
import modelo.level.Pyramid;
import util.Config;

/**
 * @author Guillermo Lazzurri Clase que representa los estados de la daga
 *         (patron state)
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

	protected Dagger dagger;
	private int state;
	protected float delta;

	/**
	 * Constructor de clase.
	 * 
	 * @param dagger Corresponde al sujeto del patron state
	 * @param state  codigo numerico que indica el estado. Puede tomar los valores:
	 *               ST_STUCKED; ST_THROWING_HORIZONTAL; ST_FALLING; ST_THROWING_UP;
	 *               ST_BOUNCING; ST_PICKUPED
	 */
	public DaggerState(Dagger dagger, int state)
	{
		this.dagger = dagger;
		this.state = state;
		this.delta = 0;
	}

	/**
	 * Llamado para actualizar la daga
	 * 
	 * @param deltaTime Tiempo transcurrido desde la ultima llamada
	 * @param pyramid   Piramide donde esta la daga
	 * @param mummys    coleccion de momias para calcular colisiones
	 */
	public abstract void updateDagger(float deltaTime, Pyramid pyramid, ArrayList<Mummy> mummys);

	/**
	 * Utilizado para calcular donde debe clavarse la espada
	 * 
	 * @param value  coordenada original
	 * @param factor Factor de redondeo. Puede ser el ancho o el alto del tile
	 * @return retorna el valor en el medio del tile
	 */
	private float roundCoord(float value, float factor)
	{
		float r;
		float i = (int) ((value + factor / 2) / factor);
		r = i * factor;
		return r;
	}

	/**
	 * reubica la coordenada x de la espada
	 */
	protected void roundX()
	{

		dagger.x = this.roundCoord(dagger.x, Config.getInstance().getLevelTileWidthUnits());

	}

	/**
	 * reubica la coordenada y de la espada
	 */
	protected void roundY()
	{

		dagger.y = this.roundCoord(dagger.y, Config.getInstance().getLevelTileHeightUnits());

	}

	/**
	 * @return el codigo numerico del estado (no confundir con el patron state)
	 *         Puede tomar los valores: ST_STUCKED; ST_THROWING_HORIZONTAL;
	 *         ST_FALLING; ST_THROWING_UP; ST_BOUNCING; ST_PICKUPED
	 */
	public int getState()
	{
		return state;
	}

	/**
	 * desplaza la coordenada x de acuerdo al parametro delta y a la direccion de la
	 * espada
	 * 
	 * @param delta desplazamiento
	 */
	protected void updateX(float delta)
	{
		if (this.dagger.isRight())
			this.dagger.x += delta;
		else
			this.dagger.x -= delta;

	}

	/**
	 * Incrementa el tiempo que la daga esta en el presente estado
	 * 
	 * @param inc incremento.
	 */
	protected void incDelta(float inc)
	{
		this.delta += inc;
	}

	/**
	 * Llamado para cambiar al estado DaggerStateThrowingHorizontal
	 */
	protected abstract void throwHorizontal();

	/**
	 * Llamado para cambiar al estado DaggerStatePickuped
	 */
	protected abstract void hasPickuped();

	/**
	 * Llamado para cambiar al estado DaggerStateThrowingVertical
	 */
	protected abstract void throwVertical();

}
