package modelo.gameCharacters.mummys;

import modelo.gameCharacters.player.Player;

public class MummyStateAppearing extends MummyState
{
    public MummyStateAppearing(Mummy mummy)
    {
	super(mummy, Mummy.ST_APPEARING);
	this.timeToDecide = 2;

    }

    @Override
    public void update(float deltaTime,Player player)
    {
	
	if (this.mummy.getAnimationDelta() >= this.timeToDecide)
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
