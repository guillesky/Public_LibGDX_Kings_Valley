package modelo.gameCharacters.mummys;

import modelo.KVEventListener;
import modelo.game.Game;
import modelo.gameCharacters.abstractGameCharacter.GameCharacter;
import modelo.gameCharacters.player.Player;
import util.Config;

public class MummyStateDying extends MummyState
{
	private boolean mustTeleport;

	public MummyStateDying(Mummy mummy, boolean mustTeleport)
	{
		super(mummy, GameCharacter.ST_DYING);
		this.mustTeleport = mustTeleport;
		this.timeToChange = Config.getInstance().getMummyTimeDying();
		Game.getInstance().eventFired(KVEventListener.MUMMY_DIE, this);

	}

	@Override
	public void update(float deltaTime)
	{
		
		if (this.mummy.getTimeInState() >= this.timeToChange)
		{
			this.mummy.mummyState = new MummyStateLimbus(this.mummy,this.mustTeleport);
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
}
