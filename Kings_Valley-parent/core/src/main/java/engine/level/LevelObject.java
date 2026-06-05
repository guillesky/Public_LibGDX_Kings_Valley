package engine.level;

import java.util.Collection;
import java.util.Objects;

import com.badlogic.gdx.math.Rectangle;

import util.Constants;
import util.GameRules;
import util.CollisionDetector;

/**
 * Representa un objeto del nivel con posicion, dimensiones y propiedades
 * basicas de identificacion.
 *
 * <p>
 * Esta clase constituye la unidad base de representacion espacial dentro del
 * mundo del juego, siendo utilizada para modelar elementos como items,
 * enemigos, mecanismos del escenario y otros objetos interactuables.
 * </p>
 *
 * <p>
 * LevelObject extiende Rectangle para proporcionar soporte directo de
 * colisiones y posicionamiento en el espacio del nivel.
 * </p>
 *
 * <p>
 * Ademas de su representacion geometrica, cada instancia posee un identificador
 * unico y un tipo logico utilizado por el motor de juego para interpretar su
 * comportamiento.
 * </p>
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
	 * Crea un nuevo objeto del nivel con tipo, posicion, dimensiones y parametro
	 * adicional.
	 *
	 * @param type   tipo logico del objeto
	 * @param x      posicion horizontal en el mundo
	 * @param y      posicion vertical en el mundo
	 * @param p0     parametro extra para configuracion especifica del objeto
	 * @param width  ancho del objeto
	 * @param height alto del objeto
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
	 * Retorna el tipo logico del objeto, utilizado principalmente para
	 * interpretacion por parte del motor del juego.
	 *
	 * @return tipo logico del objeto (solo deberia usarse para renderizar)
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
				+ ", p0=" + p0 + " width=" + width + " height=" + height + "]";
	}

	/**
	 * Determina si este objeto colisiona con otro rectangulo.
	 *
	 * @param another rectangulo contra el cual se verifica la colision
	 * @return true si existe interseccion geometrica, false en caso contrario
	 */
	public boolean isColision(Rectangle another)
	{
		return CollisionDetector.rectangleColision(this, another);
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
	 * Busca el primer objeto de una coleccion que colisiona con el rectangulo dado.
	 *
	 * <p>
	 * Este metodo es utilizado para detectar interacciones entre el jugador y
	 * objetos del nivel como items, activadores o enemigos.
	 * </p>
	 *
	 * @param collectionOfRectangles conjunto de posibles colisiones
	 * @return el primer objeto colisionante o null si no hay colision
	 */
	@SuppressWarnings("rawtypes")
	public LevelObject checkRectangleColision(Collection collectionOfRectangles)
	{
		LevelObject respuesta = (LevelObject) CollisionDetector.checkRectangleColision(this, collectionOfRectangles);
		return respuesta;
	}

}
