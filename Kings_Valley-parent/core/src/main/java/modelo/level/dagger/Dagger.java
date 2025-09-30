package modelo.level.dagger;

import java.util.ArrayList;

import modelo.gameCharacters.mummys.Mummy;
import modelo.level.LevelObject;
import modelo.level.Pyramid;
import util.Constants;

/**
 * @author Guillermo Lazzurri
 * 
 *         Clase que representa una daga. Aplica el patron State
 */
@SuppressWarnings("serial")
public class Dagger extends LevelObject
{

	private DaggerState daggerState = null;

	private boolean isRight;

	/**
	 * Constructor de clase. Llama a super(Constantes.It_dagger, x, y, p0, width,
	 * height);
	 * 
	 * @param x      Coordenada x inicial de la Daga
	 * @param y      Coordenada y inicial de la Daga
	 * @param p0     Parametro extra leido dese de tileMap (no usado)
	 * @param width  Ancho del objeto leido desde el tileMap
	 * @param height Alto del objeto leido desde el tileMap
	 */
	public Dagger(float x, float y, int p0, float width, float height)
	{
		super(Constants.IT_DAGGER, x, y, p0, width, height);
		this.daggerState = new DaggerStateStucked(this);

	}

	/**
	 * Retorna un codigo numerido indicando el estado de la espada (no confundir con
	 * el patron state). puede tomar los valores: DaggerState.ST_STUCKED = 0;
	 * DaggerState.ST_THROWING_HORIZONTAL = 1; DaggerState.ST_FALLING = 2;
	 * DaggerState.ST_THROWING_UP = 3; DaggerState.ST_BOUNCING = 4;
	 * DaggerState.ST_PICKUPED = 5;
	 * 
	 * @return el valor del atributo this.daggerState.getState();
	 */
	public int getState()
	{
		return this.daggerState.getState();
	}

	/**
	 * Indica si la orientacion de la espada es hacia la derecha
	 * 
	 * @return true si esta orientada a la derecha, false si esta orientada hacia la
	 *         izquierda
	 */
	public boolean isRight()
	{
		return isRight;
	}

	/**
	 * Llamado para lanzar la daga Horizontalmente. Delega en el atributo daggerState (patron state)
	 * @param isRight true si la espada es lanzada hacia la derecha, false si es lanzada hacia la izquierda
	 */
	public void throwHorizontal(boolean isRight)
	{
		this.isRight = isRight;
		this.daggerState.throwHorizontal();
	}

	/**
	 * Llamado para actualizar la daga. Aplica delegacion sobre su atributo
	 * daggerState (patron state). Llama a this.daggerState.updateDagger(deltaTime,
	 * pyramid, mummys);
	 * 
	 * @param deltaTime Indica el tiempo transcurrido desde la ultima actualizacion
	 * @param pyramid   Indica la piramide actual
	 * @param mummys    Contiene un arrayList de momias (para calcular colisiones)
	 */
	public void updateDagger(float deltaTime, Pyramid pyramid, ArrayList<Mummy> mummys)
	{

		this.daggerState.updateDagger(deltaTime, pyramid, mummys);

	}

	/**
	 * Setea el estado de la daga (solo deberia usarse mediante patron state)
	 * @param daggerState nuevo estado de la daga.
	 */
	protected void setDaggerState(DaggerState daggerState)
	{
		this.daggerState = daggerState;
	}
	/**
	 * Llamado para recoger la daga. Delega en el atributo daggerState (patron state)
	 */
	
	public void hasPickuped()
	{
		this.daggerState.hasPickuped();
	
	}
	/**
	 * Llamado para lanzar la daga Verticalmente (hacia arriba). Delega en el atributo daggerState (patron state)
	 * @param isRight true si la espada es lanzada hacia la derecha, false si es lanzada hacia la izquierda
	 */
	
	public void throwVertical(boolean isRight)
	{
		this.isRight = isRight;
		this.daggerState.throwVertical();
	}

}
