package engine.level;

import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;

import com.badlogic.gdx.math.Rectangle;

import util.Constants;
import util.GameRules;

/**
 * Clase que representa un objeto del nivel que tenga una posicion y un tamano.
 * 
 * @author Guillermo Lazzurri
 */
@SuppressWarnings("serial")

public class LevelObject extends Rectangle
{
	/**
	 * Usado para identificar univocamente cada LevelObject con el siguiente entero
	 */
	private static int count = 0;
	/**
	 * Identificador del objecto
	 */
	private int id;
	/**
	 * Tipo de objeto de nivel (Daga, Pico, gema, momia, etc.)
	 */
	private int type;
	/**
	 * Parametro extra de ser necesario (Para guardar coherencia con el programa
	 * tiled)
	 */
	private int p0;

	/**
	 * Constructor de clase
	 * 
	 * @param type   entero que indica el tipo de objeto
	 * @param x      (posicion x en el mapa)
	 * @param y      (posicion y en el mapa)
	 * @param p0     (parametro extra en caso de ser necesario)
	 * @param width  (ancho del objeto)
	 * @param height (alto del objeto)
	 */
	public LevelObject(int type, float x, float y, int p0, float width, float height)
	{
		super(x, y, width, height);
		this.type = type;
		this.p0 = p0;
		count++;
		this.id = count;
	}

	/**
	 * Retorna el tipo del objeto (solo deberia usarse para renderizar)
	 * 
	 * @return tipo del objeto (solo deberia usarse para renderizar)
	 */
	public int getType()
	{
		return type;
	}

	/**
	 * Retorna el parametro extra (en caso de ser necesario)
	 * 
	 * @return parametro extra en caso de ser necesario
	 */
	public int getP0()
	{
		return p0;
	}

	@Override
	public String toString()
	{
		return "LevelObject [id= " + this.id + " type=" + Constants.identificacion.get(type) + ", x=" + x + ", y=" + y
				+ ", p0=" + p0 + "]";
	}

	/**
	 * Calcula si existe colision rectangular entre este objeto y otro rectangulo
	 * 
	 * @param another Rectangulo contra el que quiero verificar colision
	 * @return true si hay colision, false en caso contrario
	 */
	public boolean isColision(Rectangle another)
	{
		return LevelObject.rectangleColision(this, another);
	}

	/**
	 * Indica si existe colision entre dos rectangulos
	 * 
	 * @param rectangleA primer rectangulo
	 * @param rectangleB segundo rectangulo
	 * @return true si el prmer rectangulo colisiona con el segundo, false en caso
	 *         contrario
	 */
	public static boolean rectangleColision(Rectangle rectangleA, Rectangle rectangleB)
	{
		boolean respuesta = false;
		if (rectangleA != null && rectangleB != null)
		{
			float overlapX = Math.min(rectangleA.x + rectangleA.width, rectangleB.x + rectangleB.width)
					- Math.max(rectangleA.x, rectangleB.x);
			float overlapY = Math.min(rectangleA.y + rectangleA.height, rectangleB.y + rectangleB.height)
					- Math.max(rectangleA.y, rectangleB.y);
			respuesta = (overlapX > 0 && overlapY > 0);
		}
		return respuesta;
	}

	/**
	 * retorna la coordenada matricial de la columna del tile en el que se encuentra
	 * el objeto
	 * 
	 * @return la coordenada matricial de la columna del tile en el que se encuentra
	 *         el objeto
	 */
	public int getColPosition()
	{
		return (int) (this.x / GameRules.getInstance().getLevelTileWidthUnits());
	}

	/**
	 * Retorna la coordenada matricial de la fila del tile en el que se encuentra el
	 * objeto
	 * 
	 * @return la coordenada matricial de la fila del tile en el que se encuentra el
	 *         objeto
	 */
	public int getRowPosition()
	{
		return (int) (this.y / GameRules.getInstance().getLevelTileHeightUnits());
	}

	/**
	 * Se sobreescribe utilizando un id autoincremental
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(id);
		return result;
	}

	/**
	 * Se sobreescribe utilizando un id autoincremental
	 */

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		LevelObject other = (LevelObject) obj;
		return id == other.id;
	}

	/**
	 * Retorna el identificador del objeto dentro del nivel.
	 * 
	 * @return El identificador del objeto dentro del nivel.
	 */
	public int getId()
	{
		return id;
	}

	
	/**
	 * Devuelve el objeto de tipo de LevelObjet con el cual el LevelObjet colisiona
	 * desde una lista pasada por parametro. Si no colisiona con ninguno, retorna
	 * null
	 * 
	 * @param collectionOfRectangles Coleccion de rectangulos a verificar
	 * @return El objeto de la lista con el cual el caracter colisiona, null si no
	 *         hay colision.
	 */
	public LevelObject checkRectangleColision(Collection collectionOfRectangles)
	{
		LevelObject respuesta = (LevelObject) checkRectangleColision(this, collectionOfRectangles);
		return respuesta;
	}

	/**
	 * Metodo estatico que retorna un el rectangulo de la coleccion que colisiona
	 * con el rectangulo pasado como primer parametro. Si ningun rectangulo de la
	 * coleccio colisiona con el primero, se devuelve null.
	 * 
	 * @param rectangle              Rectangulo a analizar contra cada uno de los
	 *                               rectangulos de la coleccion.
	 * @param collectionOfRectangles Coleccion de rectangulos que se debe comparar
	 *                               con el primero.
	 * @return El rectangulo de la coleccion que colisiona con el rectangulo pasado
	 *         como primer parametro. Si ningun rectangulo de la coleccio colisiona
	 *         con el primero, se devuelve null.
	 */
	public static Rectangle checkRectangleColision(Rectangle rectangle, Collection collectionOfRectangles)
	{

		Iterator<Rectangle> it = collectionOfRectangles.iterator();
		Rectangle respuesta = null;
		Rectangle item = null;
		if (it.hasNext())
			do
			{
				item = it.next();
			} while (it.hasNext() && !rectangleColision(rectangle, item));

		if (rectangleColision(rectangle, item))
		{
			respuesta = item;
		}

		return respuesta;
	}

}
