package engine.gameCharacters.mummys;

public class MummyStateOnAir extends MummyState
{

	public MummyStateOnAir(Mummy mummy)
	{
		super(mummy, mummy.getState());
	}

	@Override
	public void update(float deltaTime)
	{
	
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
