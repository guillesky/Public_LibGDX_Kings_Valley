package modelo.gameCharacters.mummys;

import modelo.gameCharacters.player.Player;

public class MummyStateDeciding extends MummyState
{
	public MummyStateDeciding(Mummy mummy)
	{
		super(mummy, Mummy.ST_IDDLE);
		this.timeToChange = this.mummy.getTimeDeciding();
		;

	}

	@Override
	public void update(float deltaTime, Player player)
	{
		if (this.mummy.getAnimationDelta() >= this.timeToChange)
		{
			this.mummy.mummyState = new MummyStateWalk(this.mummy, player);
			this.mummy = null;
		}

	}
	
	
	@Override
	protected boolean isDanger()
	{
	return true;
	}
	
	

}
