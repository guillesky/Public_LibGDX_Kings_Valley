package modelo.gameCharacters.mummys;

import modelo.gameCharacters.abstractGameCharacter.GameCharacter;
import modelo.gameCharacters.player.Player;
import util.Config;

public class MummyStateSearchingPlayer extends MummyStateWalking
{
	public MummyStateSearchingPlayer(Mummy mummy, Player player)
	{
		super(mummy, player);

	}

	@Override
	protected void checkEndOfPlataform(Player player)
	{
		int positionMummy = mummy.CrashWallOrGiratory();
		if (positionMummy != Mummy.BLOCK_FREE)
		{
			if (positionMummy == Mummy.BLOCK_BRICK && this.mummy.makeDecisionForJump())
			{
				this.doJump = true;
			} else
			{
				this.bounces();
			}
		} else
		{
			
			if (mummy.isInBorderCliff())
			{
				if (player.y >= this.mummy.y && this.mummy.canJump() && this.mummy.makeBestDecisionProbability())
					this.doJump = true;
				else
				{
					if (this.mummy.makeDecision())
					{
						if (this.mummy.makeDecisionForJump())
							this.doJump = true;
						else
						{
							this.bounces();
						}

					}
				}
			}
		}
	}

	@Override
	public void update(float deltaTime, Player player)
	{
		this.mummy.timeWhitoutSeePlayer += deltaTime;
		super.update(deltaTime, player);

	}

	@Override
	protected void checkChangeStatus(Player player)
	{
		if (this.mummy.distanceQuadToPlayer(player) < this.mummy.rangeVision)
			this.mummy.mummyState = new MummyStateChasingPlayer(this.mummy, player);

		if (this.mummy.getTimeInState() >= this.timeToChange)
		{
			this.mummy.mummyState = new MummyStateDeciding(this.mummy);
		}
	}

	@Override
	protected void decideEnterStairs(Player player)
	{

		if (player.y - Config.getInstance().getLevelTileHeightUnits() * 2 > this.mummy.y)
			this.mummy.getDirection().y = 1;
		else if (player.y + Config.getInstance().getLevelTileHeightUnits() * 2 < this.mummy.y)
			this.mummy.getDirection().y = -1;
		else
			this.mummy.getDirection().y = 0;
	}

}
