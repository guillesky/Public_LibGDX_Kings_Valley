package modelo.gameCharacters.mummys;

import modelo.gameCharacters.player.Player;
import modelo.level.Pyramid;

public class MummyFactory
{
	public static final int WHITE_MUMMY = 0;
	public static final int YELLOW_MUMMY = 1;
	public static final int PINK_MUMMY = 2;
	public static final int BLUE_MUMMY = 3;
	public static final int RED_MUMMY = 4;

	public Mummy getMummy(float x, float y, int mummyType, Pyramid pyramid, Player player)
	{
		Mummy respuesta = null;

		switch (mummyType)
		{

		case WHITE_MUMMY:
			respuesta = new MummyWhite(x, y, pyramid,player);
			break;

		case BLUE_MUMMY:
			respuesta = new MummyBlue(x, y, pyramid,player);
			break;
		case YELLOW_MUMMY:
			respuesta = new MummyYellow(x, y, pyramid,player);
			break;
		case PINK_MUMMY:
			respuesta = new MummyPink(x, y, pyramid,player);
			break;

		case RED_MUMMY:
			respuesta = new MummyRed(x, y, pyramid,player);
			break;

		}
		return respuesta;
	}
}
