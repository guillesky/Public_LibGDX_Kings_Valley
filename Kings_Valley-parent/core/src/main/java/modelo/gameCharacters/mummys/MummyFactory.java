package modelo.gameCharacters.mummys;

import modelo.level.Pyramid;

public class MummyFactory
{
    public static final int WHITE_MUMMY = 0;
    public static final int BLUE_MUMMY = 1;
    public static final int YELLOW_MUMMY = 2;
    public static final int ORANGE_MUMMY = 3;
    public static final int RED_MUMMY = 4;

    public Mummy getMummy(float x, float y, int mummyType, Pyramid pyramid)
    {
	Mummy respuesta = null;

	switch (mummyType)
	{

	case WHITE_MUMMY:
	    respuesta = new MummyWhite(x, y, pyramid);
	    break;

	case BLUE_MUMMY:
	    respuesta = new MummyBlue(x, y, pyramid);
	    break;
	case YELLOW_MUMMY:
	    respuesta = new MummyYellow(x, y, pyramid);
	    break;
	case ORANGE_MUMMY:
	    respuesta = new MummyOrange(x, y, pyramid);
	    break;

	case RED_MUMMY:
	    respuesta = new MummyRed(x, y, pyramid);
	    break;

	}
	return respuesta;
    }
}
