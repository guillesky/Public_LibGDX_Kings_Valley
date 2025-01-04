package mummys;

import modelo.Pyramid;
import util.Config;

public class MummyWhite extends Mummy
{

    public MummyWhite(float x, float y, Pyramid pyramid)
    {
	super(Mummy.WHITE_MUMMY, x, y, Config.getInstance().getMummyWhiteSpeedWalk(),
		Config.getInstance().getMummyWhiteSpeedWalkStairs(), Config.getInstance().getMummyWhiteDecisionFactor(),
		Config.getInstance().getMummyWhiteMinTimeToDecide(), Config.getInstance().getMummyWhiteMaxTimeToDecide(),
		pyramid);

    }

}
