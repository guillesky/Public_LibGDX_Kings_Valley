package modelo.gameCharacters.mummys;

import modelo.gameCharacters.abstractGameCharacter.GameCharacter;
import modelo.level.LevelObject;
import modelo.level.Stair;

public class MummyStateSearchingStair extends MummyStateWalking
{
	private Stair stair;
	private LevelObject footStair;
	

	public MummyStateSearchingStair(Mummy mummy, Stair stair, int directionX, int whereIsPlayer)
	{
		super(mummy,directionX,whereIsPlayer);
		this.whereIsPlayer=whereIsPlayer;
		this.stair = stair;
		
		if (whereIsPlayer == MummyState.PLAYER_IS_UP)
			this.footStair = stair.getDownStair();
		else
			this.footStair = stair.getUpStair();
	
	}

	@Override
	public void update(float deltaTime)
	{
		super.update(deltaTime);
		if (!this.mummy.isInStair())
		{

			if (this.mummy.isFeetColision(this.footStair))
			{
				this.mummy.enterStair(stair);
				if (this.whereIsPlayer ==MummyState.PLAYER_IS_UP)
					this.mummy.getDirection().x = this.stair.directionUp();
				else
					this.mummy.getDirection().x = this.stair.directionDown();

			}
			
		}
		
	}

	@Override
	protected boolean isDanger()
	{
		// TODO Auto-generated method stub
		return false;
	}

}
