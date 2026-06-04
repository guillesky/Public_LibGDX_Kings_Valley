package engine.gameCharacters.mummys;

import engine.gameCharacters.player.Player;
import engine.level.Pyramid;

/**
 * Fabrica responsable de la creacion de instancias de momias.
 *
 * <p>
 * Esta clase encapsula la logica necesaria para construir enemigos del juego,
 * centralizando el proceso de inicializacion y configuracion de las distintas
 * variantes de momias disponibles en el sistema.
 * </p>
 *
 * <p>
 * La utilizacion de una fabrica permite desacoplar la creacion de objetos de
 * los componentes que los utilizan, evitando que otras clases deban conocer
 * detalles concretos sobre la construccion e inicializacion de los enemigos.
 * </p>
 *
 * <p>
 * Desde el punto de vista arquitectonico, esta clase implementa el patron
 * Factory, delegando en un componente especializado la responsabilidad de crear
 * y configurar las instancias correspondientes.
 * </p>
 *
 * <p>
 * La informacion necesaria para construir cada enemigo es recibida como
 * parametro, permitiendo que la logica de creacion permanezca separada de los
 * mecanismos encargados de obtener o interpretar los datos del nivel.
 * </p>
 *
 * <p>
 * Esta estrategia facilita la incorporacion de nuevos tipos de enemigos o
 * variantes de comportamiento sin afectar a los componentes que consumen dichas
 * instancias.
 * </p>
 *
 * @author Guillermo Lazzurri
 */
public class MummyFactory
{
    /**
     * Codigo de momia blanca
     */
    public static final int WHITE_MUMMY = 0;
    /**
     * Codigo de momia rosa
     */

    public static final int PINK_MUMMY = 1;

    /**
     * Codigo de momia amarilla
     */
    public static final int YELLOW_MUMMY = 2;
    /**
     * Codigo de momia azul
     */
    public static final int BLUE_MUMMY = 3;
    /**
     * Codigo de momia roja
     */
    public static final int RED_MUMMY = 4;

    /**
     * Constructor de clase.
     */
    public MummyFactory()
    {
    }

    /**
     * Metodo que retornara una momia de acuerdo a si tipo
     * 
     * @param x         coordenada x
     * @param y         coordenada x
     * @param mummyType tipo de momia que debe crearse. Puede tomar los valores:
     *                  WHITE_MUMMY; PINK_MUMMY; YELLOW_MUMMY; BLUE_MUMMY; RED_MUMMY
     * @param pyramid   Piramide a la que pertenece la momia
     * @param player    Player que debera perseguir la momia
     * @return Una momia concreta.
     */
    public Mummy getMummy(float x, float y, int mummyType, Pyramid pyramid, Player player)
    {
	Mummy respuesta = null;

	switch (mummyType)
	{

	case WHITE_MUMMY:
	    respuesta = new MummyWhite(x, y, pyramid, player);
	    break;

	case BLUE_MUMMY:
	    respuesta = new MummyBlue(x, y, pyramid, player);
	    break;
	case YELLOW_MUMMY:
	    respuesta = new MummyYellow(x, y, pyramid, player);
	    break;
	case PINK_MUMMY:
	    respuesta = new MummyPink(x, y, pyramid, player);
	    break;

	case RED_MUMMY:
	    respuesta = new MummyRed(x, y, pyramid, player);
	    break;

	}
	return respuesta;
    }
}
