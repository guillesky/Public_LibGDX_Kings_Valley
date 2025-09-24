package modelo.level;

/**
 * @author Guillermo Lazzurri Clae que representa los datos necesarios para
 *         crear una momia. Son leidos a partir de un archivo de tipo TMX. Solo
 *         usado internamente por la clase LevelReader (podria ser una inner
 *         class)
 */
public class MummyData
{
	private float x;
	private float y;
	private int type;

	/**
	 * Constructor de clase, indica las coordenadas y tipo de momia<br>
	 * <b>Pre: </b> 0 <= type <= 4
	 * @param x coordenada x de la momia
	 * @param y coordenada y de la momia
	 * @param type tipo de momia (0 <= type <= 4)
	 */
	public MummyData(float x, float y, int type)
	{
		this.x = x;
		this.y = y;
		this.type = type;
	}

	/**
	 * @return Coordenada x de la momia
	 */
	protected float getX()
	{
		return x;
	}

	/**
	 * @return Coordenada y de la momia
	 */
	protected float getY()
	{
		return y;
	}

	/**
	 * @return Tipo de la momia
	 */
	protected int getType()
	{
		return type;
	}

}
