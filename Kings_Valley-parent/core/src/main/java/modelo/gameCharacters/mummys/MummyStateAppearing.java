package modelo.gameCharacters.mummys;

import modelo.gameCharacters.player.Player;
import util.Config;

public class MummyStateAppearing extends MummyState
{
    public MummyStateAppearing(Mummy mummy)
    {
	super(mummy, Mummy.ST_APPEARING);
	this.timeToChange = Config.getInstance().getMummyTimeAppearing();


    }

    @Override
    public void update(float deltaTime)
    {
	
	if (this.mummy.getTimeInState() >= this.timeToChange)
	{

	    this.mummy.mummyState = new MummyStateDeciding(this.mummy);
	   
	}

    }

	@Override
	protected boolean isDanger()
	{
	return false;
	}

	@Override
	protected void die(boolean mustTeleport)
	{
	   
	    
	}

}
