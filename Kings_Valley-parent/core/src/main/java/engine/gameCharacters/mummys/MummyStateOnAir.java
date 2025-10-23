package engine.gameCharacters.mummys;

import engine.gameCharacters.abstractGameCharacter.GameCharacter;

public class MummyStateOnAir extends MummyState
{

	public MummyStateOnAir(Mummy mummy)
	{
		super(mummy);
	}

	@Override
	public void update(float deltaTime)
	{
		this.mummy.move(this.mummy.getDirection(), false, deltaTime);
		if (this.mummy.getState() == GameCharacter.ST_WALKING
				|| this.mummy.getState() == GameCharacter.ST_IDDLE)
			this.mummy.mummyState=new MummyStateWalking(this.mummy);
	}

	@Override
	protected boolean isDanger()
	{
		return true;
	}

	/**
	 * Cambia el estado a new MummyStateDying(this.mummy, mustTeleport);
	 */
	@Override
	protected void die(boolean mustTeleport)
	{

		this.mummy.mummyState = new MummyStateDying(this.mummy, mustTeleport);

	}


}
