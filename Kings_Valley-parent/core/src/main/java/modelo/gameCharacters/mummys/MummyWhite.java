package modelo.gameCharacters.mummys;

import modelo.level.Pyramid;
import util.Config;
@SuppressWarnings("serial")
public class MummyWhite extends Mummy
{

    public MummyWhite(float x, float y, Pyramid pyramid)
    {
	super(MummyFactory.WHITE_MUMMY, x, y,Config.getInstance().getMummyWhiteParameters(),	pyramid);

    }

	@Override
	protected boolean canJump()
	{
		return false;
	}

	@Override
	protected boolean makeDecisionForJump()
	{
		return false;
		
	}
	
	
    

}
