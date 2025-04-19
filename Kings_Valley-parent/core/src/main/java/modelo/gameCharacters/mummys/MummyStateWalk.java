package modelo.gameCharacters.mummys;

import modelo.gameCharacters.abstractGameCharacter.GameCharacter;
import modelo.gameCharacters.player.Player;
import util.Config;

public class MummyStateWalk extends MummyState
{
    private boolean doJump = false;
    private float maxDistanceToPlayer;

    public MummyStateWalk(Mummy mummy, Player player)
    {
	super(mummy, GameCharacter.ST_WALKING);
	if (mummy.getX() < player.getX())
	    this.mummy.getDirection().x = 1;
	else
	    this.mummy.getDirection().x = -1;

	this.timeToDecide = this.mummy.getTimeToDecide();

	this.maxDistanceToPlayer = (64 * Config.getInstance().getLevelTileHeightUnits()
		* Config.getInstance().getLevelTileHeightUnits());
    }

    @Override
    public void update(float deltaTime, Player player)
    {

	if (this.mummy.getState() == GameCharacter.ST_WALKING)
	    this.checkCrash();

	if (this.mummy.getStressLevel() >= 9)
	{
	    this.mummy.die();

	} else
	{
	    this.mummy.move(this.mummy.getDirection(), doJump, deltaTime);

	    if (this.mummy.getStressLevel() > 0)
		this.mummy.calmStress(deltaTime / 3);

	    if (this.mummy.distanceQuadToPlayer(player) > this.maxDistanceToPlayer
		    && this.mummy.getAnimationDelta() >= this.timeToDecide)
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
