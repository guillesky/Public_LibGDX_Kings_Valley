package modelo.gameCharacters.mummys;

import modelo.level.Pyramid;
import util.Config;
@SuppressWarnings("serial")
public class MummyYellow extends Mummy
{

    public MummyYellow(float x, float y, Pyramid pyramid)
    {
	super(MummyFactory.YELLOW_MUMMY, x, y, Config.getInstance().getMummyYellowParameters(),	pyramid);

    }

}
