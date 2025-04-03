package modelo.gameCharacters.mummys;

import modelo.Pyramid;

public class MummyFactory
{

	public Mummy getMummy(float x, float y, int mummyType, Pyramid pyramid)
	{
		Mummy respuesta = null;
		

		switch (mummyType)
		{

		case Mummy.WHITE_MUMMY:
			respuesta = new MummyWhite(x, y, pyramid);
			break;

		case Mummy.BLUE_MUMMY:
			respuesta = new MummyBlue(x, y, pyramid);
			break;
		case Mummy.YELLOW_MUMMY:
			respuesta = new MummyYellow(x, y, pyramid);
			break;
		case Mummy.ORANGE_MUMMY:
			respuesta = new MummyOrange(x, y, pyramid);
			break;

		case Mummy.RED_MUMMY:
			respuesta = new MummyRed(x, y, pyramid);
			break;

		}
		return respuesta;
	}
}
