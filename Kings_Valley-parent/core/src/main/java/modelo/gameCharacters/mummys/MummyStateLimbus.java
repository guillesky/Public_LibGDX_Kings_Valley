package modelo.gameCharacters.mummys;

import modelo.gameCharacters.player.Player;
import util.Config;

public class MummyStateLimbus extends MummyState
{
	private boolean mustTeleport = false;

	public MummyStateLimbus(Mummy mummy, boolean mustTeleport)
	{
		this(mummy, Config.getInstance().getMummyTimeInLimbus());
		this.mustTeleport = mustTeleport;
		this.mummy.resetStress();
	}

	public MummyStateLimbus(Mummy mummy, float timeToChange)
	{
		super(mummy, Mummy.ST_LIMBUS);
		this.timeToChange = timeToChange;

	}

	@Override
	public void update(float deltaTime)

	{
		if (this.mummy.getTimeInState() >= this.timeToChange)
		{
			if (this.mustTeleport)
				this.mummy.teleport();
			this.mummy.mummyState = new MummyStateAppearing(this.mummy);
			this.mummy = null;
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

	@Override
	protected void doInBorderCliff()
	{
	    // TODO Auto-generated method stub
	    
	}

	@Override
	public void doInCrashToWallOrGiratory(int crashStatus, int type)
	{
	    // TODO Auto-generated method stub
	    
	}
}
