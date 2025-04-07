package modelo.gameCharacters.mummys;

import modelo.level.Pyramid;
import util.Config;

public class MummyYellow extends Mummy
{

    public MummyYellow(float x, float y, Pyramid pyramid)
    {
	super(Mummy.YELLOW_MUMMY, x, y, Config.getInstance().getMummyYellowSpeedWalk(),
		Config.getInstance().getMummyYellowSpeedWalkStairs(), Config.getInstance().getMummyYellowDecisionFactor(),
		Config.getInstance().getMummyYellowMinTimeToDecide(), Config.getInstance().getMummyYellowMaxTimeToDecide(),
		pyramid);

    }

}
