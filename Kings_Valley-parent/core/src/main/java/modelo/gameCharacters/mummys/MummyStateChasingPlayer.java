package modelo.gameCharacters.mummys;

import modelo.gameCharacters.player.Player;
import modelo.level.Stair;
import util.Config;

public class MummyStateChasingPlayer extends MummyStateWalking
{
	public MummyStateChasingPlayer(Mummy mummy, Player player)
	{
		super(mummy, player);

	}

	@Override
	protected void checkEndOfPlataform(Player player)
	{
		int positionMummy = mummy.CrashWallOrGiratory();
		if (positionMummy != Mummy.BLOCK_FREE)
		{

			if (positionMummy == Mummy.BLOCK_BRICK  && player.y >= this.mummy.y
					&& this.mummy.makeBestDecisionProbability())
			{
				this.doJump = true;
			} else
			{
				this.mummy.getDirection().x *= -1;
				this.mummy.stressing();
			}
		}
		if (mummy.isInBorderCliff())
		{
			if (player.y >= this.mummy.y && this.mummy.makeBestDecisionProbability())
				this.doJump = true;
			else if (this.mummy.makeDecision())
				this.mummy.getDirection().x *= -1;
		}
	}

	@Override
	protected void decideEnterStairs(Player player)
	{
		Stair stair = null;
		this.mummy.getDirection().y = 0;
		
		if (player.y-Config.getInstance().getLevelTileHeightUnits()*2 > this.mummy.y)
		{
			stair = this.mummy.checkStairsFeetColision(true, true);
			if (stair == null)
				stair = this.mummy.checkStairsFeetColision(false, true);
			if (stair != null && this.mummy.makeBestDecisionProbability())
			{
				this.mummy.getDirection().x = stair.directionUp();
				this.mummy.getDirection().y = 1;
			}
		} else if (player.y +Config.getInstance().getLevelTileHeightUnits()*2< this.mummy.y)
		{
			stair = this.mummy.checkStairsFeetColision(true, false);
			if (stair == null)
				stair = this.mummy.checkStairsFeetColision(false, false);
			if (stair != null && this.mummy.makeBestDecisionProbability())
			{
				this.mummy.getDirection().x = stair.directionDown();
				this.mummy.getDirection().y = -1;
			}
		}

	}

	@Override
	protected void checkChangeStatus(Player player)
	{
		if (this.mummy.getTimeInState() >= this.timeToChange)
		{
			if (this.mummy.distanceQuadToPlayer(player) > this.mummy.rangeVision)
				this.mummy.mummyState = new MummyStateDeciding(this.mummy);
			else
			{
				this.setDirection(player);
				this.mummy.resetTimeInState();
			}
		}

	}

}
