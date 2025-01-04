package mummys;

import modelo.Pyramid;
import util.Config;

public class MummyBlue extends Mummy
{

    public MummyBlue(float x, float y, Pyramid pyramid)
    {
	super(Mummy.BLUE_MUMMY, x, y, Config.getInstance().getMummyBlueSpeedWalk(),
		Config.getInstance().getMummyBlueSpeedWalkStairs(), Config.getInstance().getMummyBlueDecisionFactor(),
		Config.getInstance().getMummyBlueMinTimeToDecide(), Config.getInstance().getMummyBlueMaxTimeToDecide(),
		pyramid);

    }

}
