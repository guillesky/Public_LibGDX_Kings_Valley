package engine.level;

/**
 * Clase que representa las coordenadas matriciales de una celda (usualmente
 * usado para las funciones de picar)
 * 
 * @author Guillermo Lazzurri
 * 
 */
public class PairInt
{
	private int x;
	private int y;

	/**
	 * Constructor de clase
	 * 
	 * @param x coordenada matricial x
	 * @param y coordenada matricial y
	 */
	public PairInt(int x, int y)
	{
		super();
		this.x = x;
		this.y = y;
	}

	/**
	 * @return coordenada matricial x
	 */
	public int getX()
	{
		return x;
	}

	/**
	 * @return coordenada matricial y
	 */
	public int getY()
	{
		return y;
	}

	/**
	 * Devuelve un String por ej: [x=4 , y=3]
	 */
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("[x=");
		builder.append(x);
		builder.append(" , y=");
		builder.append(y);
		builder.append("]");
		return builder.toString();
	}

}
