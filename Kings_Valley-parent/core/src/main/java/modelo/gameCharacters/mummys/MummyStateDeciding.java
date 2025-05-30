package modelo.gameCharacters.mummys;

import com.badlogic.gdx.math.Vector2;

import modelo.level.LevelObject;
import modelo.level.Stair;

public class MummyStateDeciding extends MummyState
{

	public MummyStateDeciding(Mummy mummy)
	{
		super(mummy, Mummy.ST_IDDLE);
		this.timeToChange = this.mummy.getTimeDeciding();
		this.update(0);

	}

	@Override
	public void update(float deltaTime)
	{
		if (this.mummy.getTimeInState() >= this.timeToChange)
		{
			this.changeStatus();
		}
		this.mummy.move(new Vector2(), false, deltaTime);

	}

	private void changeStatus()
	{
		if (this.mummy.player.y > this.mummy.y)// player esta arriba
		{
			NearStairResult nearStairResult = this.getNearStair(true);

			if (nearStairResult != null)
			{
				this.mummy.mummyState = new MummyStateSearchingStair(this.mummy, nearStairResult.getStair(),
						nearStairResult.getDirectionX(), MummyState.PLAYER_IS_UP);
			} else
			{
				int directionX = this.searchEndPlatform(EndPlatform.END_STEP);
				if (directionX == NONE)
					directionX = this.searchEndPlatform(EndPlatform.END_CLIFF);
				this.mummy.mummyState = new MummyStateWalking(this.mummy, directionX, MummyState.PLAYER_IS_UP);
			}

		} else if (this.mummy.player.y < this.mummy.y)// player esta abajo
		{
			NearStairResult nearStairResult = this.getNearStair(false);
			if (nearStairResult != null)
			{
				this.mummy.mummyState = new MummyStateSearchingStair(this.mummy, nearStairResult.getStair(),
						nearStairResult.getDirectionX(), MummyState.PLAYER_IS_DOWN);

			} else
			{
				int direccion = this.searchEndPlatform(EndPlatform.END_CLIFF);
				if (direccion == NONE)
					direccion = this.searchEndPlatform(EndPlatform.END_STEP);
				this.mummy.mummyState = new MummyStateWalking(this.mummy, direccion, MummyState.PLAYER_IS_DOWN);
			}

		} else
		{
			this.mummy.mummyState = new MummyStateWalking(this.mummy, MummyState.NONE, MummyState.PLAYER_IS_SOME_LEVEL);

		} // player esta al mismo nivel

	}

	@Override
	protected boolean isDanger()
	{
		return true;
	}

	private int searchEndPlatform(int typeEnd)
	{
		int r = NONE;
		EndPlatform endToRight = this.endPlatform(true);
		EndPlatform endToLeft = this.endPlatform(false);
		if (endToRight.getType() == typeEnd || endToLeft.getType() == typeEnd)
		{
			if (endToRight.getType() != typeEnd)
				r = LEFT;
			else if (endToLeft.getType() != typeEnd)
				r = RIGHT;
			else
			{
				if (endToRight.getCount() < endToLeft.getCount())
					r = RIGHT;
				else
					r = LEFT;
			}
		}

		return r;
	}

}
