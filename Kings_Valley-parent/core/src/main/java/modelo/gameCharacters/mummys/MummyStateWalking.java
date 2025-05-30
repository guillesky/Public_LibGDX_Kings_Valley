package modelo.gameCharacters.mummys;

import modelo.gameCharacters.abstractGameCharacter.GameCharacter;
import modelo.gameCharacters.player.Player;
import util.Config;

public class MummyStateWalking extends MummyState
{
	protected boolean doJump = false;
	protected int whereIsPlayer;

	public MummyStateWalking(Mummy mummy, int directionX, int whereIsPlayer)
	{
		super(mummy, GameCharacter.ST_WALKING);
		this.whereIsPlayer = whereIsPlayer;

		if (directionX == MummyState.NONE)
			this.setDirection();
		else
			this.mummy.getDirection().x = directionX;

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

		if ((this.mummy.getState() == GameCharacter.ST_WALKING || this.mummy.getState() == GameCharacter.ST_IDDLE)
				&& !this.mummy.isInStair())
		{
			this.checkChangeStatus();
			float x = this.mummy.x;

			if (this.mummy.isLookRight())
				x = this.mummy.x + this.mummy.width;
			int type = this.typeEndPlatform(x, 0);
			
			this.checkEndOfPlataform(type);
		}
		if (this.mummy.getStressLevel() >= 9)
		{ // muere por estres
			this.mummy.die(true);
		}

		if (this.mummy.getStressLevel() > 0)
			this.mummy.calmStress(deltaTime / 6);

		this.mummy.move(this.mummy.getDirection(), doJump, deltaTime);
		if (this.mummy.getDirection().x == 0)
			
		this.doJump = false;
		
		
	}

	protected void checkEndOfPlataform(int type)
	{
		int crashStatus = mummy.crashWallOrGiratory();
		if (crashStatus != Mummy.BLOCK_FREE) // si choca contra un ladrillo o una giratoria
		{
			this.doInCrashToWallOrGiratory(crashStatus, type);
		} else
		{
			;
/*
			if (type == EndPlatform.END_CLIFF) // Si esta al borde del acantilado
			{
				this.doInBorderCliff();
			}*/
		}
	}

	@Override
	protected boolean isDanger()
	{
		return true;
	}

	protected void checkChangeStatus()
	{
		if (this.mummy.getTimeInState() >= this.timeToChange)
			this.mummy.mummyState = new MummyStateDeciding(this.mummy);
	}

	protected void bounces()
	{
		this.mummy.getDirection().x *= -1;
		this.mummy.stressing();
	}

	protected void doInCrashToWallOrGiratory(int crashStatus, int type)
	{
		if (crashStatus == Mummy.BLOCK_BRICK)

		{

			if (type == EndPlatform.END_STEP && this.whereIsPlayer == MummyState.PLAYER_IS_UP
					|| this.whereIsPlayer == MummyState.PLAYER_IS_SOME_LEVEL)

				this.doJump = true;
		} else // rebota pues choco contra giratoria
		{
			this.bounces();
		}
	}

	protected void doInBorderCliff()
	{
		if (this.whereIsPlayer == MummyState.PLAYER_IS_UP || this.whereIsPlayer == MummyState.PLAYER_IS_SOME_LEVEL)
			if(this.mummy.makeDecisionForJump())this.doJump = true;
			else this.bounces();
		
	}

}
