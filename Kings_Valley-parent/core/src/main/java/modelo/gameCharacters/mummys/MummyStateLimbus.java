package modelo.gameCharacters.mummys;

import modelo.gameCharacters.player.Player;

public class MummyStateLimbus extends MummyState
{
    public MummyStateLimbus(Mummy mummy)
    {
	super(mummy, Mummy.ST_LIMBUS);
	this.timeToChange = 5;
    }

    public MummyStateLimbus(Mummy mummy, int timeToDecide)
    {
	super(mummy, Mummy.ST_LIMBUS);
	this.timeToChange = timeToDecide;
	this.mummy.teleport();
    }

    @Override
    public void update(float deltaTime,Player player)

    {
	if (this.mummy.getAnimationDelta() >= this.timeToChange)
	{

	    this.mummy.mummyState = new MummyStateAppearing(this.mummy);
	    this.mummy = null;
	}
    }

    
    
    @Override
	protected boolean isDanger()
	{
	return false;
	}
}
