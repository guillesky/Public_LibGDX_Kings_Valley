package modelo.gameCharacters.mummys;

import modelo.Juego;
import modelo.gameCharacters.GameCharacter;
import modelo.gameCharacters.player.Player;
import util.Config;

public class MummyStateWalk extends MummyState
{
	private boolean doJump = false;
	private Player player;
	private float maxDistanceToPlayer;

	public MummyStateWalk(Mummy mummy)
	{
		super(mummy, GameCharacter.ST_WALK);
		this.player=this.mummy.getPyramid().getPlayer();
		this.timeToDecide = this.mummy.getTimeToDecide();

		if (mummy.getX() < this.player.getX())
			this.mummy.getDirection().x = 1;
		else
			this.mummy.getDirection().x = -1;
		
		this.maxDistanceToPlayer= (64*Config.getInstance().getLevelTileHeightUnits()*Config.getInstance().getLevelTileHeightUnits());
	}

	@Override
	public void update(float deltaTime)
	{
		if (this.mummy.getState() == GameCharacter.ST_WALK)
			this.checkCrash();

		if (this.mummy.getStressLevel() >= 9)
		{
			this.mummy.mummyState = new MummyStateDying(this.mummy);
			this.mummy.resetStress();
		} else
		{
			this.mummy.move(this.mummy.getDirection(), doJump, deltaTime);

			if (this.mummy.getStressLevel() > 0)
				this.mummy.calmStress(deltaTime / 3);

			if (this.mummy.distanceQuadToPlayer() > this.maxDistanceToPlayer && this.mummy.getAnimationDelta() >= this.timeToDecide)
			{
				this.mummy.mummyState = new MummyStateDeciding(this.mummy);
			}
		}
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
}
