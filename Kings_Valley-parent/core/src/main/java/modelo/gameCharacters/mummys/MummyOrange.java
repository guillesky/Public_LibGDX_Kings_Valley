package modelo.gameCharacters.mummys;

import modelo.level.Pyramid;
import util.Config;

public class MummyOrange extends Mummy
{

    public MummyOrange(float x, float y, Pyramid pyramid)
    {
	super(Mummy.ORANGE_MUMMY, x, y, Config.getInstance().getMummyOrangeSpeedWalk(),
		Config.getInstance().getMummyOrangeSpeedWalkStairs(), Config.getInstance().getMummyOrangeDecisionFactor(),
		Config.getInstance().getMummyOrangeTimeToDecide(), Config.getInstance().getMummyOrangeTimeToDecide(),
		pyramid);

    }

    @Override
    protected float getTimeToDecide()
    {
	
	return Config.getInstance().getMummyOrangeTimeToDecide();
    }
    
    

}
