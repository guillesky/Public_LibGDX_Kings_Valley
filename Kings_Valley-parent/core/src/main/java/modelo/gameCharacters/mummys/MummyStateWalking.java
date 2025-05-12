package modelo.gameCharacters.mummys;

import modelo.gameCharacters.abstractGameCharacter.GameCharacter;
import modelo.gameCharacters.player.Player;
import util.Config;

public abstract class MummyStateWalking extends MummyState
{
	protected boolean doJump = false;

	public MummyStateWalking(Mummy mummy, Player player)
	{
		super(mummy, GameCharacter.ST_WALKING);
		this.setDirection(player);
		this.timeToChange = this.mummy.getTimeToDecide();

	}

	protected void setDirection(Player player)
	{
		if (mummy.getX() < player.getX())
			this.mummy.getDirection().x = 1;
		else
			this.mummy.getDirection().x = -1;

	}

	@Override
	public void update(float deltaTime, Player player)
	{
		if (this.mummy.getState() == GameCharacter.ST_WALKING && !this.mummy.isInStair())
			this.checkEndOfPlataform(player);
		if (this.mummy.getStressLevel() >= 9 || this.mummy.timeWhitoutSeePlayer >= 20)
		{ // muere por estres o por no encontrar al player
			this.mummy.die(true);
		}

		if (this.mummy.getStressLevel() > 0)
			this.mummy.calmStress(deltaTime / 3);

		this.decideEnterStairs(player);
		this.mummy.move(this.mummy.getDirection(), doJump, deltaTime);
		this.checkChangeStatus(player);
		this.doJump = false;
	}

	protected abstract void decideEnterStairs(Player player);

	protected abstract void checkEndOfPlataform(Player player);

	@Override
	protected boolean isDanger()
	{
		return true;
	}

	protected abstract void checkChangeStatus(Player player);

}
