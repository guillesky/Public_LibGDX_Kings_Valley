package modelo.gameCharacters.mummys;

import modelo.gameCharacters.abstractGameCharacter.GameCharacter;
import modelo.gameCharacters.player.Player;
import util.Config;

public class MummyStateWalking extends MummyState
{
	protected boolean doJump = false;

	public MummyStateWalking(Mummy mummy, int direction)
	{
		super(mummy, GameCharacter.ST_WALKING);
		if (direction == 0)
			this.setDirection();
		else
			this.mummy.getDirection().x = direction;

		this.timeToChange = this.mummy.getTimeToDecide();

	}

	protected void setDirection()
	{
		if (mummy.getX() < this.mummy.player.getX())
			this.mummy.getDirection().x = 1;
		else
			this.mummy.getDirection().x = -1;

	}

	@Override
	public void update(float deltaTime)
	{
		Player player = this.mummy.player;
		if (this.mummy.getState() == GameCharacter.ST_WALKING && !this.mummy.isInStair())
			this.checkEndOfPlataform(player);
		if (this.mummy.getStressLevel() >= 9)
		{ // muere por estres o por no encontrar al player
			this.mummy.die(true);
		}

		if (this.mummy.getStressLevel() > 0)
			this.mummy.calmStress(deltaTime / 3);
		if (!this.mummy.isInStair())
			this.decideEnterStairs(player);
		this.mummy.move(this.mummy.getDirection(), doJump, deltaTime);
		this.checkChangeStatus(player);
		this.doJump = false;
	}

	protected void decideEnterStairs(Player player)
	{
	}

	protected void checkEndOfPlataform(Player player)
	{
		int crashStatus = mummy.CrashWallOrGiratory();
		if (crashStatus != Mummy.BLOCK_FREE) // si choca contra un ladrillo o una giratoria
		{
			this.doInCrashToWallOrGiratory(crashStatus, player);
		} else
		{

			if (mummy.isInBorderCliff()) // Si esta al borde del acantilado
			{
				this.doInBorderCliff(player);
			}
		}
	}

	@Override
	protected boolean isDanger()
	{
		return true;
	}

	protected void checkChangeStatus(Player player)
	{
	}

	protected void bounces()
	{
		this.mummy.getDirection().x *= -1;
		this.mummy.stressing();
	}

	protected boolean playerIsUp(Player player)
	{
		return player.y - Config.getInstance().getLevelTileHeightUnits() * 2 > this.mummy.y;
	}

	protected boolean playerIsDown(Player player)
	{
		return player.y + Config.getInstance().getLevelTileHeightUnits() * 2 < this.mummy.y;
	}

	protected void doInCrashToWallOrGiratory(int crashStatus, Player player)
	{
		if (crashStatus == Mummy.BLOCK_BRICK && this.mummy.makeDecisionForJump() && this.mummy.canJump()) // si es
																											// ladrillo
																											// y decide
		// saltar lo hace
		{
			this.doJump = true;
		} else // sino, rebota
		{
			this.bounces();
		}
	}

	protected void doInBorderCliff(Player player)
	{
		if (!this.mummy.makeDecisionForFall())
		{
			if (this.mummy.makeDecisionForJump() && this.mummy.canJump())
				this.doJump = true;
			else
				this.bounces();
		}
	}

}
