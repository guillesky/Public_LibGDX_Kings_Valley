package modelo.gameCharacters.mummys;

import modelo.level.Pyramid;
import util.Config;

@SuppressWarnings("serial")
public class MummyRed extends Mummy
{

    public MummyRed(float x, float y, Pyramid pyramid)
    {
	super(Mummy.RED_MUMMY, x, y, Config.getInstance().getMummyRedSpeedWalk(),
		Config.getInstance().getMummyRedSpeedWalkStairs(), Config.getInstance().getMummyRedDecisionFactor(),
		Config.getInstance().getMummyRedMinTimeToDecide(), Config.getInstance().getMummyRedMaxTimeToDecide(),
		pyramid);

    }

}
