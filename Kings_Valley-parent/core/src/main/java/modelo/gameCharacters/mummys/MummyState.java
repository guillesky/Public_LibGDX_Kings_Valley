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

	

	public MummyState(Mummy mummy, int state)
	{
		this.mummy = mummy;
		this.mummy.setState(state);
		this.mummy.resetAnimationDelta();
		this.mummy.resetTimeInState();
		

	}

	public abstract void update(float deltaTime);

	protected abstract boolean isDanger();

	private Stair nearStair(boolean toUp, boolean toRight)
	{
		Stair r = null;
		Pyramid pyramid = this.mummy.getPyramid();
		int count = this.endPlatform(toRight).getCount();
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

	protected EndPlatform endPlatform(boolean toRight)
	{
		int inc;
		int acum = 0;
		int type;
		int count;
		float xAux;
		Pyramid pyramid = this.mummy.getPyramid();
		xAux = this.mummy.x + this.mummy.width * .5f;
		if (toRight)
			inc = 1;
		else
			inc = -1;

		while (pyramid.getCell(xAux, this.mummy.y, acum, 0) == null
				&& pyramid.getCell(xAux, this.mummy.y, acum, 1) == null
				&& pyramid.getCell(xAux, this.mummy.y, acum, -1) != null && this.isColDesplaInMap(acum))
		{
			acum += inc;
		}
		type = this.typeEndPlatform(xAux, acum);
		if (acum < 0)
			acum *= -1;
		count = acum;
		EndPlatform r = new EndPlatform(type, count);
		this.correctGiratoryEndPlatform(r, toRight);

		return r;

	}

	private void correctGiratoryEndPlatform(EndPlatform r, boolean toRight)
	{
		Iterator<LevelObject> it = this.mummy.getPyramid().getGiratories().iterator();
		boolean condicion = false;
		float posX = this.mummy.x + this.mummy.width * 0.5f;
		while (it.hasNext() && !condicion)
		{
			LevelObject giratoria = it.next();
			if (giratoria.y == this.mummy.y && (toRight && giratoria.x >= posX || !toRight && giratoria.x <= posX))
			{
				float aux = posX - (giratoria.x + giratoria.width * 0.5f);
				if (aux < 0)
					aux = -1;
				int dist = (int) (aux / Config.getInstance().getLevelTileWidthUnits());
				if (dist < r.getCount())
				{
					r.setCount(dist);
					r.setType(EndPlatform.END_BLOCK);
					condicion = true;
				}
			}
		}

	}

	private boolean isColDesplaInMap(int col)
	{

		return this.mummy.getColPosition() + col > 1
				&& this.mummy.getColPosition() + col < this.mummy.getPyramid().getMapWidthInTiles() - 1;
	}

	protected int typeEndPlatform(float positionX, int iDeltaX)
	{
		int type;
		Pyramid pyramid = this.mummy.getPyramid();
		if (pyramid.getCell(positionX, this.mummy.y, iDeltaX, 0) == null
				&& pyramid.getCell(positionX, this.mummy.y, iDeltaX, 1) == null
				&& pyramid.getCell(positionX, this.mummy.y, iDeltaX, -1) == null)
			type = EndPlatform.END_CLIFF;
		else if ((pyramid.getCell(positionX, this.mummy.y, iDeltaX, 1) != null
				&& pyramid.getCell(positionX, this.mummy.y, iDeltaX, 2) == null
				&& pyramid.getCell(positionX, this.mummy.y, iDeltaX, 3) == null)
				|| (pyramid.getCell(positionX, this.mummy.y, iDeltaX, 0) != null
						&& pyramid.getCell(positionX, this.mummy.y, iDeltaX, 1) == null
						&& pyramid.getCell(positionX, this.mummy.y, iDeltaX, 2) == null))
			type = EndPlatform.END_STEP;
		else
			type = EndPlatform.END_BLOCK;
		return type;
	}

	protected abstract void die(boolean mustTeleport);
}
