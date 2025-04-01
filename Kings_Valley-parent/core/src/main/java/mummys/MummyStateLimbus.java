package mummys;

public class MummyStateLimbus extends MummyState
{
	public MummyStateLimbus(Mummy mummy)
	{
		super(mummy,Mummy.ST_LIMBUS);
		this.timeToDecide=5;
	}

	public MummyStateLimbus(Mummy mummy,int timeToDecide)
	{
		super(mummy,Mummy.ST_LIMBUS);
		this.timeToDecide=timeToDecide;
	}

	
	@Override
	public void update(float deltaTime)
	{	if (this.mummy.getAnimationDelta() >= this.timeToDecide)
		{
			
			this.mummy.mummyState = new MummyStateAppearing(this.mummy);
			this.mummy = null;
		}
	}

}
