package modelo.gameCharacters.mummys;

import modelo.gameCharacters.abstractGameCharacter.GameCharacter;
import modelo.level.LevelObject;
import modelo.level.Stair;

public class MummyStateSearchingStair extends MummyStateWalking
{
	private Stair stair;
	private LevelObject footStair;

	public MummyStateSearchingStair(Mummy mummy, Stair stair, int directionY)
	{
		super(mummy, GameCharacter.ST_WALKING);
		this.stair = stair;
		this.mummy.getDirection().y = directionY;

		if (directionY == 1)
			this.footStair = stair.getDownStair();
		else
			this.footStair = stair.getUpStair();
		if (footStair.x < this.mummy.x)
			this.mummy.getDirection().x = -1;
		else
			this.mummy.getDirection().x = 1;
	}

	@Override
	public void update(float deltaTime)
	{
		this.mummy.move(this.mummy.getDirection(), false, deltaTime);

		if (!this.mummy.isInStair())
		{

			if (this.mummy.isFeetColision(this.footStair))
			{
				this.mummy.enterStair(stair);
				if (this.mummy.getDirection().y > 1)
					this.mummy.getDirection().x = this.stair.directionUp();
				else
					this.mummy.getDirection().x = this.stair.directionDown();

			}
			if (this.mummy.getTimeInState() >= this.timeToChange)
				this.mummy.mummyState = new MummyStateDeciding(this.mummy);
		}
	}

	@Override
	protected boolean isDanger()
	{
		// TODO Auto-generated method stub
		return false;
	}

}
