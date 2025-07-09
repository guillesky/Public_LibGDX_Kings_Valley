package modelo.gameCharacters.mummys;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.math.Rectangle;

import modelo.gameCharacters.abstractGameCharacter.GameCharacter;
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
	private static final int BLOCK_FREE = 0;
	private static final int BLOCK_BRICK = 1;
	private static final int BLOCK_GIRATORY = 2;
	protected float timeToChange;
	protected Mummy mummy;
	private Rectangle r;

	public MummyState(Mummy mummy, int state)
	{
		this.mummy = mummy;
		this.r = new Rectangle(this.mummy.x, this.mummy.y, this.mummy.width, this.mummy.height);
		mummy.setState(state);
		mummy.resetAnimationDelta();
		mummy.resetTimeInState();

	}
	
	
	
	
	
	public abstract void update(float deltaTime);
	protected abstract boolean isDanger();

	protected abstract void die(boolean mustTeleport);
	private Stair nearStair(boolean toUp, boolean toRight)
	{
		Stair r = null;
		Pyramid pyramid = mummy.getPyramid();
		int count = this.endPlatform(toRight).getCount();
		ArrayList<Stair> candidatesStairs = new ArrayList<Stair>();
		LevelObject footStair;

		Iterator<Stair> it = pyramid.getAllStairs().iterator();
		float tileWidth = Config.getInstance().getLevelTileWidthUnits();
		float posX = mummy.x + mummy.width * 0.5f;
		while (it.hasNext())
		{
			Stair stair = it.next();
			if (toUp)
				footStair = stair.getDownStair();
			else
				footStair = stair.getUpStair();
			if (footStair.y == mummy.y && (toRight && footStair.x >= posX || !toRight && footStair.x <= posX))
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

			float aux = posX - (footStair.x + footStair.width * 0.5f);
			if (aux < 0)
				aux *= -1;
			int min = (int) (aux / (float) tileWidth);
			while (it.hasNext())
			{
				stair = it.next();
				if (toUp)
					footStair = stair.getDownStair();
				else
					footStair = stair.getUpStair();

				aux = posX - (footStair.x + footStair.width * 0.5f);
				if (aux < 0)
					aux *= -1;
				int dist = (int) (aux / (float) tileWidth);
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

				if (footStairRight.x - mummy.x < mummy.x - footStairLeft.x)
					stair = toRight;
				else
					stair = toLeft;

			}
		}
		NearStairResult r = null;
		if (stair != null)
		{
			if (stair == toRight)
				r = new NearStairResult(stair, RIGHT);
			else
				r = new NearStairResult(stair, LEFT);
		}
		return r;
	}

	protected EndPlatform endPlatform(boolean toRight)
	{
		int inc;
		int acum = 0;
		int type;
		int count;
		float x;
		Pyramid pyramid = mummy.getPyramid();
		x = mummy.x;
		if (toRight)
		{
			inc = 1;
			x += mummy.width;
		} else

		{
			inc = -1;

		}

		while (pyramid.getCell(x, mummy.y, acum, 0) == null && pyramid.getCell(x, mummy.y, acum, 1) == null
				&& pyramid.getCell(x, mummy.y, acum, -1) != null && this.isColDesplaInMap(acum))
		{
			acum += inc;

		}
		type = this.typeEndPlatform(x, acum);
		if (acum < 0)
			acum *= -1;
		count = acum;
		EndPlatform r = new EndPlatform(type, count);
		this.correctGiratoryEndPlatform(r, toRight);

		return r;

	}

	private void correctGiratoryEndPlatform(EndPlatform r, boolean toRight)
	{
		Iterator<LevelObject> it = mummy.getPyramid().getGiratories().iterator();
		boolean condicion = false;
		float posX = mummy.x;

		if (toRight)
			posX += mummy.width;

		while (it.hasNext() && !condicion)
		{
			LevelObject giratoria = it.next();
			if (giratoria.y == mummy.y && (toRight && giratoria.x >= posX || !toRight && giratoria.x <= posX))
			{
				float aux = posX - (giratoria.x + giratoria.width * 0.5f);
				if (aux < 0)
					aux *= -1;
				int dist = (int) (aux / (float) Config.getInstance().getLevelTileWidthUnits());

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

		return mummy.getColPosition() + col > 1
				&& mummy.getColPosition() + col < mummy.getPyramid().getMapWidthInTiles() - 1;
	}

	protected int typeEndPlatform(float positionX, int iDeltaX)
	{
		int type = -1;
		Pyramid pyramid = mummy.getPyramid();
		if (pyramid.getCell(positionX, mummy.y, iDeltaX, 0) == null
				&& pyramid.getCell(positionX, mummy.y, iDeltaX, 1) == null
				&& pyramid.getCell(positionX, mummy.y, iDeltaX, -1) == null)
			type = EndPlatform.END_CLIFF;
		else if ((pyramid.getCell(positionX, mummy.y, iDeltaX, 1) != null
				&& pyramid.getCell(positionX, mummy.y, iDeltaX, 2) == null
				&& pyramid.getCell(positionX, mummy.y, iDeltaX, 3) == null)
				|| (pyramid.getCell(positionX, mummy.y, iDeltaX, 0) != null
						&& pyramid.getCell(positionX, mummy.y, iDeltaX, 1) == null
						&& pyramid.getCell(positionX, mummy.y, iDeltaX, 2) == null))
			type = EndPlatform.END_STEP;
		else
			type = EndPlatform.END_BLOCK;
		return type;
	}

	public void checkEndOfPlataform(int type)
	{
		int crashStatus = this.crashWallOrGiratory();
		if (crashStatus != BLOCK_FREE) // si choca contra un ladrillo o una giratoria
		{
			this.doInCrashToWallOrGiratory(crashStatus, type);
		} else
		{

			if (type == EndPlatform.END_CLIFF) // Si esta al borde del acantilado
			{
				this.doInBorderCliff();
			}

		}
	}

	protected abstract void doInBorderCliff();

	public abstract void doInCrashToWallOrGiratory(int crashStatus, int type);

	private int crashWallOrGiratory()
	{
		boolean condicion = false;
		Pyramid pyramid = this.mummy.getPyramid();
		int respuesta = BLOCK_FREE;
		float epsilon = Config.getInstance().getLevelTileWidthUnits() * 0.1f;
		if (mummy.isLookRight())
		{
			condicion = mummy.getColPosition() >= pyramid.getMapWidthInTiles() - 2
					|| pyramid.getCell(mummy.x + Config.getInstance().getLevelTileWidthUnits(),
							mummy.y + Config.getInstance().getLevelTileHeightUnits()) != null
					||

					pyramid.getCell(mummy.x + Config.getInstance().getLevelTileWidthUnits(), mummy.y) != null;

		} else
		{

			condicion = mummy.getColPosition() <= 1 || pyramid.getCell(mummy.x - epsilon,
					mummy.y + Config.getInstance().getLevelTileHeightUnits()) != null ||

					pyramid.getCell(mummy.x - epsilon, mummy.y) != null;

		}
		if (condicion)
			respuesta = BLOCK_BRICK;
		else if (this.checkGiratory())
			respuesta = BLOCK_GIRATORY;

		return respuesta;
	}

	private boolean checkGiratory()
	{
		float epsilon = Config.getInstance().getLevelTileWidthUnits() * 0.1f;
		if (this.mummy.isLookRight())
			this.r.x = this.mummy.x + epsilon;
		else
			this.r.x = this.mummy.x - epsilon;
		this.r.y = this.mummy.y;

		return this.checkRectangleColision(this.mummy.getPyramid().getGiratories());
	}

	public void update()
	{

		if (this.mummy.getState() == GameCharacter.ST_WALKING)
		{
			float x = this.mummy.x;

			if (!this.mummy.isLookRight())
				x = this.mummy.x + this.mummy.width;
			int type = this.typeEndPlatform(x, 0);

			this.checkEndOfPlataform(type);
		}

	}

	public boolean checkRectangleColision(ArrayList levelObjects)
	{

		Iterator<LevelObject> it = levelObjects.iterator();
		LevelObject item = null;
		if (it.hasNext())
			do
			{
				item = it.next();
			} while (it.hasNext() && !LevelObject.rectangleColision(r, item));

		return (LevelObject.rectangleColision(r, item));

	}

}
