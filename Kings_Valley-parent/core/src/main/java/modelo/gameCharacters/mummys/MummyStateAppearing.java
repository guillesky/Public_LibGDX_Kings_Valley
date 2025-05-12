package modelo.gameCharacters.mummys;

import modelo.gameCharacters.player.Player;
import util.Config;

public class MummyStateAppearing extends MummyState
{
    public MummyStateAppearing(Mummy mummy)
    {
	super(mummy, Mummy.ST_APPEARING);
	this.timeToChange = Config.getInstance().getMummyTimeAppearing();
	this.mummy.timeWhitoutSeePlayer=0;

    }

    @Override
    public void update(float deltaTime,Player player)
    {
	
	if (this.mummy.getTimeInState() >= this.timeToChange)
	{

	    this.mummy.mummyState = new MummyStateDeciding(this.mummy);
	    this.mummy = null;
	}

    }

	@Override
	protected boolean isDanger()
	{
	return false;
	}

}
