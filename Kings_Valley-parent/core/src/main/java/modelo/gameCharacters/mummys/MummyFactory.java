package modelo.gameCharacters.mummys;

import modelo.gameCharacters.player.Player;
import modelo.level.Pyramid;

/**
 * @author Guillermo Lazzurri
 * 
 *         Clase que crea las momias a partir de su tipo Patron Factory
 */
public class MummyFactory
{
	public static final int WHITE_MUMMY = 0;
	public static final int PINK_MUMMY = 1;
	public static final int YELLOW_MUMMY = 2;
	public static final int BLUE_MUMMY = 3;
	public static final int RED_MUMMY = 4;

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
