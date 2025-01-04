package mummys;

import modelo.Pyramid;
import util.Config;

public class MummyOrange extends Mummy
{

    public MummyOrange(float x, float y, Pyramid pyramid)
    {
	super(Mummy.ORANGE_MUMMY, x, y, Config.getInstance().getMummyOrangeSpeedWalk(),
		Config.getInstance().getMummyOrangeSpeedWalkStairs(), Config.getInstance().getMummyOrangeDecisionFactor(),
		Config.getInstance().getMummyOrangeMinTimeToDecide(), Config.getInstance().getMummyOrangeMaxTimeToDecide(),
		pyramid);

    }

}
