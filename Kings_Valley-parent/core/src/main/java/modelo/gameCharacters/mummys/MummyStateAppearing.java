package modelo.gameCharacters.mummys;

public class MummyStateAppearing extends MummyState
{
    public MummyStateAppearing(Mummy mummy)
    {
	super(mummy, Mummy.ST_APPEARING);
	this.timeToDecide = 2;

    }

    @Override
    public void update(float deltaTime)
    {
	
	if (this.mummy.getAnimationDelta() >= this.timeToDecide)
	{

	    this.mummy.mummyState = new MummyStateDeciding(this.mummy);
	    this.mummy = null;
	}

    }

}
