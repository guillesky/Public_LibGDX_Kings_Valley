package util;

import java.util.Collection;
import java.util.Iterator;

import com.badlogic.gdx.math.Rectangle;

/**
 * Contiene metodos estaticos utilizados para calculos de deteccion
 * de colisiones dentro del juego.
 *
 * <p>
 * Esta clase agrupa operaciones independientes de las entidades del dominio,
 * permitiendo reutilizar logica geometrica comun sin acoplarla a clases como
 * LevelObject o GameCharacter.
 * </p>
 *
 * @author Guillermo Lazzurri
 */

public class CollisionDetector
{

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
	@SuppressWarnings({ "rawtypes", "unchecked" })
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

	/**
	 * Determina si dos rectangulos colisionan mediante interseccion de area.
	 *
	 * <p>
	 * Este metodo es utilizado como base para todos los sistemas de deteccion de
	 * colisiones del juego.
	 * </p>
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
	 * Constructor de clase privado. Esta clase no deberia instanciarse.
	 */
	private CollisionDetector()
	{
	}

}
