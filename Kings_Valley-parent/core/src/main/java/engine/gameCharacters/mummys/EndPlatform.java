package engine.gameCharacters.mummys;

import com.badlogic.gdx.math.Rectangle;

/**
 * Representa el final de una plataforma. Se usa para que la momia tome
 * decisiones al respecto
 * 
 * @author Guillermo Lazzurri
 */
public class EndPlatform
{
	/**
	 * Representa una plataforma bloqueada (por pared o giratoria)
	 */
	public static final int END_BLOCK = 0;
	/**
	 * Representa una cornisa por la que se puede caer
	 */
	public static final int END_CLIFF = 1;
	/**
	 * Representa un escalon que se podria saltar
	 */
	public static final int END_STEP = 2;

	private int type;
	private Rectangle rectangle;

	/**
	 * Constructor de clase.
	 * 
	 * @param type      Codigo numerico para indicar el tipo de final de plataforma.
	 *                  Puede tomar los valores:<br>
	 *                  END_BLOCK = 0 (final de plataforma bloqueado (por pared o
	 *                  giratoria)<br>
	 *                  END_CLIFF = 1 (Representa una cornisa por la que se puede
	 *                  caer)<br>
	 *                  END_STEP = 2;(Representa un escalon que se podria
	 *                  saltar)<br>
	 * 
	 * @param rectangle Rectangulo del final de plataforma. Necesario para calcular
	 *                  colisiones.
	 */
	public EndPlatform(int type, Rectangle rectangle)
	{

		this.type = type;
		this.rectangle = rectangle;
	}

	/**
	 * Retorna del tipo de final de plataforma. Puede tomar los valores:<br>
	 * END_BLOCK = 0 (final de plataforma bloqueado (por pared o giratoria)<br>
	 * END_CLIFF = 1 (Representa una cornisa por la que se puede caer)<br>
	 * END_STEP = 2;(Representa un escalon que se podria saltar)<br>
	 * 
	 * @return tipo de plataforma Codigo numerico que indica el final de plataforma
	 */
	public int getType()
	{
		return type;
	}

	/**
	 * Retorna el rectangulo donde esta el final de plaforma. Necesario para
	 * calcular colisiones
	 * 
	 * @return El rectangulo donde esta el final de plaforma. Necesario para
	 *         calcular colisiones
	 */
	public Rectangle getRectangle()
	{
		return rectangle;
	}

	@Override
	public String toString()
	{
		return "EndPlatform2 [type=" + type + ", rectangle=" + rectangle + "]";
	}

}
