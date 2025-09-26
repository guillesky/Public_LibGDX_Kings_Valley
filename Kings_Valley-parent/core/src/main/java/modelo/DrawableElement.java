package modelo;

/**
 * @author Guillermo Lazzurri
 * 
 * 
 *         Representa un elemento dibujable generico. Permite a la interfaz
 *         grafica discrimar el tipom de dibujable correspondiente para poder
 *         realizar el casting y las acciones pertinentes.
 */
public class DrawableElement
{
	private int type;
	private Object drawable;

	public int getType()
	{
		return type;
	}

	public Object getDrawable()
	{
		return drawable;
	}

	/**
	 * Constructor de clase
	 * 
	 * @param type     Indica el tipo de elemento grafico. Debe ser un tipo conocido
	 *                 por la interfaz grafica. Puede tomar los valores<br>
	 *                 Constantes.DRAWABLE_EXIT_DOOR = 2000;<br>
	 *                 Constantes.DRAWABLE_LEVEL_ITEM = 3000;<br>
	 *                 Constantes.DRAWABLE_TRAP = 3001;<br>
	 *                 Constantes.DRAWABLE_GYRATORY = 3002;<br>
	 *                 Constantes.DRAWABLE_PICKING_CELL = 4000;<br>
	 *                 Constantes.DRAWABLE_FLYING_DAGGER = 5000;<br>
	 * 
	 * @param drawable Objeto que debe dibujarse. Debera castearse al tipo
	 *                 correspondiente especificado por type.
	 */
	public DrawableElement(int type, Object drawable)
	{
		super();
		this.type = type;
		this.drawable = drawable;
	}

}
