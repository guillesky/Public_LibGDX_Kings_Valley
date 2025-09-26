package modelo.level;

/**
 * @author Guillermo Lazzurri Representa una escalera en la piramide
 */
public class Stair
{
	private LevelObject downStair;
	private LevelObject upStair;

	/**
	 * Constructor de clase.
	 * 
	 * @param downStair Representa el pie de la escalera (limite inferior)
	 * @param upStair   Representa la cabecera de la escalera (limite superior)
	 */
	public Stair(LevelObject downStair, LevelObject upStair)
	{
		this.downStair = downStair;
		this.upStair = upStair;
	}

	/**
	 * @return El pie de la escalera (limite inferior)
	 */
	public LevelObject getDownStair()
	{
		return downStair;
	}

	/**
	 * @return La cabecera de la escalera (limite superior)
	 */
	public LevelObject getUpStair()
	{
		return upStair;
	}

	/**
	 * @return true si la pendiente es positiva (sube hacia la derecha).<br>
	 *         false si la pendiente es negativa (sube hacia la izquierda).
	 */
	public boolean isPositive()
	{
		return this.downStair.x < this.upStair.x;
	}

	/**
	 * @return La direccion horizontal que se debe tomar para subir (1 si es
	 *         positiva, -1 si es negativa)
	 */
	public float directionUp()
	{
		return Math.signum(this.upStair.x - this.downStair.x);
	}

	/**
	 * @return La direccion horizontal que se debe tomar para bajar (-1 si es
	 *         positiva, 1 si es negativa)
	 */
	public float directionDown()
	{
		return Math.signum(this.downStair.x - this.upStair.x);
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("Stair [downStair=");
		builder.append(downStair);
		builder.append(", upStair=");
		builder.append(upStair);
		builder.append(", isPositive()=");
		builder.append(isPositive());
		builder.append("]");
		return builder.toString();
	}

}
