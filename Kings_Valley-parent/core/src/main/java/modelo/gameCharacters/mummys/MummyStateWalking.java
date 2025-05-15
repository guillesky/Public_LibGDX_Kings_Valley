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
		if (!this.mummy.isInStair())
			this.decideEnterStairs(player);
		this.mummy.move(this.mummy.getDirection(), doJump, deltaTime);
		this.checkChangeStatus(player);
		this.doJump = false;
	}

	protected abstract void decideEnterStairs(Player player);

	protected void checkEndOfPlataform(Player player)
	{
		int statusPositionMummy = mummy.CrashWallOrGiratory();
		if (statusPositionMummy != Mummy.BLOCK_FREE) // si choca contra un ladrillo o una giratoria
		{
			if (statusPositionMummy == Mummy.BLOCK_BRICK && this.mummy.makeDecisionForJump()) // si es ladrillo y decide
																								// saltar lo hace
			{
				this.doJump = true;
			} else // sino, rebota
			{
				this.bounces();
			}
		} else
		{

			if (mummy.isInBorderCliff()) // Si esta al borde del acantilado
			{
				if (this.playerIsUp(player)) // Si el player esta arriba
				{
					if (this.mummy.makeBestDecisionProbability()) // Si toma la mejor decision posible rebota
						this.bounces();
					else // Si no
					{
						if (!this.mummy.makeDecisionForFall() && this.mummy.canJump()) // Si no decide caer y puede
																						// saltar lo hace
							this.doJump = true;
					}
				} // sino se dejara caer

				else if (this.playerIsDown(player) && !this.mummy.makeBestDecisionProbability())
				// Si el player esta abajo y no toma la mejor decision que seria caer
				{
					if (this.mummy.makeDecisionForJump() && this.mummy.canJump()) // Si puede y decide saltar lo hace
						this.doJump = true;
					else
						this.bounces(); // sino, rebota

				}

				else // Si el player esta casi a la misma altura
				{
					if (this.mummy.makeBestDecisionProbability() && this.mummy.canJump())
						this.doJump = true;
					else
					{
						if (!this.mummy.makeDecisionForFall())
							this.bounces();
					}
				}
			}
		}
	}


	@Override
	protected boolean isDanger()
	{
		return true;
	}

	
	
	
	protected abstract void checkChangeStatus(Player player);

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

}
