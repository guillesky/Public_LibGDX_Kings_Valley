package modelo.gameCharacters.mummys;

import modelo.level.Pyramid;
import util.Config;

@SuppressWarnings("serial")
public class MummyOrange extends Mummy
{

    public MummyOrange(float x, float y, Pyramid pyramid)
    {
	super(MummyFactory.ORANGE_MUMMY, x, y, Config.getInstance().getMummyOrangeSpeedWalk(),
		Config.getInstance().getMummyOrangeSpeedWalkStairs(), Config.getInstance().getMummyOrangeDecisionFactor(),
		Config.getInstance().getMummyOrangeMinTimeToDecide(), Config.getInstance().getMummyOrangeMaxTimeToDecide(),
		pyramid);

    }

   

}
