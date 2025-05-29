package modelo.gameCharacters.mummys;

import java.util.ArrayList;
import java.util.Iterator;

import modelo.level.LevelObject;
import modelo.level.Pyramid;
import modelo.level.Stair;
import util.Config;

public abstract class MummyState
{
	protected static final int NONE = 0;
	protected static final int RIGHT = 1;
	protected static final int LEFT = -1;

	protected static final int PLAYER_IS_SOME_LEVEL = 0;
	protected static final int PLAYER_IS_UP = 1;
	protected static final int PLAYER_IS_DOWN = -1;

	protected Mummy mummy;
	protected float timeToChange;

	protected static float getSecureDistanceToPlayer()
	{
		return (64 * Config.getInstance().getLevelTileHeightUnits()

				* Config.getInstance().getLevelTileHeightUnits());
	}

	public MummyState(Mummy mummy, int state)
	{
		this.mummy = mummy;
		this.mummy.setState(state);
		this.mummy.resetAnimationDelta();
		this.mummy.resetTimeInState();
		this.mummy.resetStress();

	}

	public abstract void update(float deltaTime);

	protected abstract boolean isDanger();

	private Stair nearStair(boolean toUp, boolean toRight)
	{
		Stair r = null;
		Pyramid pyramid = this.mummy.getPyramid();
		int count = this.mummy.endPlatform(toRight).getCount();
		ArrayList<Stair> candidatesStairs = new ArrayList<Stair>();
		LevelObject footStair;

		Iterator<Stair> it = pyramid.getAllStairs().iterator();
		float tileWidth = Config.getInstance().getLevelTileWidthUnits();
		float posX = this.mummy.x + this.mummy.width * 0.5f;
		while (it.hasNext())
		{
			Stair stair = it.next();
			if (toUp)
				footStair = stair.getDownStair();
			else
				footStair = stair.getUpStair();
			if (footStair.y == this.mummy.y && (toRight && footStair.x >= posX || !toRight && footStair.x <= posX))
				candidatesStairs.add(stair);
		}

		if (!candidatesStairs.isEmpty())
		{
			it = candidatesStairs.iterator();
			Stair stair = it.next();
			if (toUp)
				footStair = stair.getDownStair();
			else
				footStair = stair.getUpStair();

			Stair minStair = stair;

			double aux = posX - (footStair.x + footStair.width * 0.5);
			if (aux < 0)
				aux *= -1;
			int min = (int) (aux / tileWidth);
			while (it.hasNext())
			{
				stair = it.next();
				if (toUp)
					footStair = stair.getDownStair();
				else
					footStair = stair.getUpStair();

				aux = posX - (footStair.x + footStair.width * 0.5);
				if (aux < 0)
					aux = -1;
				int dist = (int) (aux / tileWidth);
				if (dist < min)
				{
					min = dist;

					minStair = stair;
				}
			}

			if (min <= count)
				r = minStair;
		}

		return r;
	}

	protected NearStairResult getNearStair(boolean toUp)
	{
		Stair stair = null;
		Stair toRight = this.nearStair(toUp, true);
		Stair toLeft = this.nearStair(toUp, false);

		if (toRight == null)
			stair = toLeft;
		else
		{
			if (toLeft == null)
				stair = toRight;
			else
			{

				LevelObject footStairRight;
				LevelObject footStairLeft;

				if (toUp)
				{
					footStairRight = toRight.getDownStair();
					footStairLeft = toLeft.getDownStair();
				} else
				{
					footStairRight = toRight.getUpStair();
					footStairLeft = toLeft.getUpStair();
				}

				if (footStairRight.x - this.mummy.x < this.mummy.x - footStairLeft.x)
					stair = toRight;
				else
					stair = toLeft;

			}
		}
		NearStairResult r = null;
		if (stair != null)
		{
			if (stair == toRight)
				r = new NearStairResult(stair, MummyState.RIGHT);
			else
				r = new NearStairResult(stair, MummyState.LEFT);
		}
		return r;
	}

}
