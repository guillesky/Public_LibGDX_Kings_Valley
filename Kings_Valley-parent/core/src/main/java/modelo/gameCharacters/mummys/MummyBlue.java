package modelo.gameCharacters.mummys;

import modelo.level.Pyramid;
import util.Config;

@SuppressWarnings("serial")
public class MummyBlue extends Mummy
{

    public MummyBlue(float x, float y, Pyramid pyramid)
    {
	super(MummyFactory.BLUE_MUMMY, x, y, Config.getInstance().getMummyBlueParameters(),	pyramid);

    }

}
