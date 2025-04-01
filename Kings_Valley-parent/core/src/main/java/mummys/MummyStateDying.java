package mummys;

import modelo.GameCharacter;

public class MummyStateDying extends MummyState
{
	public MummyStateDying(Mummy mummy)
	{
		super(mummy, GameCharacter.ST_DYING);

		this.timeToDecide = 1;

	}

	@Override
	public void update(float deltaTime)
	{
		if (this.mummy.getAnimationDelta() >= this.timeToDecide)
		{

			this.mummy.mummyState = new MummyStateLimbus(this.mummy);
			this.mummy = null;
		}

	}

}
