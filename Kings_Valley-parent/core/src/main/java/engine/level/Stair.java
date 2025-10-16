package engine.level;

/**
 * Representa una escalera en la piramide
 * 
 * @author Guillermo Lazzurri
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
     * Retorna El pie de la escalera (limite inferior)
     * 
     * @return El pie de la escalera (limite inferior)
     */
    public LevelObject getDownStair()
    {
	return downStair;
    }

    /**
     * Retorna la cabecera de la escalera (limite superior)
     * 
     * @return La cabecera de la escalera (limite superior)
     */
    public LevelObject getUpStair()
    {
	return upStair;
    }

    /**
     * Retorna true si la pendiente es positiva (sube hacia la derecha).<br>
     * false si la pendiente es negativa (sube hacia la izquierda).
     * 
     * @return true si la pendiente es positiva (sube hacia la derecha).<br>
     *         false si la pendiente es negativa (sube hacia la izquierda).
     */
    public boolean isPositive()
    {
	return this.downStair.x < this.upStair.x;
    }

    /**
     * Retorna la direccion horizontal que se debe tomar para subir (1 si es
     * positiva, -1 si es negativa)
     * 
     * @return La direccion horizontal que se debe tomar para subir (1 si es
     *         positiva, -1 si es negativa)
     */
    public float directionUp()
    {
	return Math.signum(this.upStair.x - this.downStair.x);
    }

    /**
     * Retorna la direccion horizontal que se debe tomar para bajar (-1 si es
     * positiva, 1 si es negativa)
     * 
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
