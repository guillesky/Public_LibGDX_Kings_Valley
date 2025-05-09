package modelo.gameCharacters.mummys;

import modelo.gameCharacters.abstractGameCharacter.GameCharacter;
import modelo.gameCharacters.player.Player;
import util.Config;

public class MummyStateChasingPlayer extends MummyState
{
	private boolean doJump = false;

	public MummyStateChasingPlayer(Mummy mummy, Player player)
	{
		super(mummy, GameCharacter.ST_WALKING);
		this.mummy.timeWhitoutSeePlayer = 0;

		this.timeToChange = this.mummy.getTimeToDecide();
		this.setDirection(player);
		System.out.println("CHASING "+mummy);
	}

	private void setDirection(Player player)
	{
		if (mummy.getX() < player.getX())
			this.mummy.getDirection().x = 1;
		else
			this.mummy.getDirection().x = -1;

	}

	@Override
	public void update(float deltaTime, Player player)
	{
		this.mummy.timeWhitoutSeePlayer += deltaTime;
		if (this.mummy.getAnimationDelta() >= this.timeToChange)
		{
			if (this.mummy.distanceQuadToPlayer(player) > this.mummy.rangeVision)
				this.mummy.mummyState = new MummyStateDeciding(this.mummy);
			else
			{
				this.setDirection(player);
			}
		}

		if (this.mummy.getState() == GameCharacter.ST_WALKING)
			this.checkCrash();

		this.mummy.move(this.mummy.getDirection(), doJump, deltaTime);
	
		
		this.doJump = false;
	}

	private void checkCrash()
	{
		int blockMummy = mummy.CrashWall();
		if (blockMummy != Mummy.BLOCK_FREE)
		{
			if (blockMummy == Mummy.BLOCK_BRICK && this.mummy.makeDecisionForJump())
			{
				this.doJump = true;
			} else
			{
				this.mummy.getDirection().x *= -1;
				this.mummy.stressing();
			}
		}
	}

	@Override
	protected boolean isDanger()
	{
		return true;
	}
}
