package modelo.gameCharacters.mummys;

public class MummyStateDeciding extends MummyState
{
    public MummyStateDeciding(Mummy mummy)
    {
	super(mummy, Mummy.ST_IDDLE);
	this.timeToDecide = Mummy.random.nextFloat(1.5f, 2.5f);
	;

    }

    @Override
    public void update(float deltaTime)
    {
	if (this.mummy.getAnimationDelta() >= this.timeToDecide)
	{
	    this.mummy.mummyState = new MummyStateWalk(this.mummy);
	    this.mummy = null;
	}

    }

}
