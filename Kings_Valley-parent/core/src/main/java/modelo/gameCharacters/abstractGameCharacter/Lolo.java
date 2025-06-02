package modelo.gameCharacters.abstractGameCharacter;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.math.Vector2;

import modelo.gameCharacters.mummys.EndPlatform;
import modelo.gameCharacters.mummys.Mummy;
import modelo.gameCharacters.mummys.NearStairResult;
import modelo.level.LevelObject;
import modelo.level.Pyramid;
import modelo.level.Stair;
import util.Config;

public class Lolo

{

	protected static final int NONE = 0;
	protected static final int RIGHT = 1;
	protected static final int LEFT = -1;

	protected static final int PLAYER_IS_SOME_LEVEL = 0;
	protected static final int PLAYER_IS_UP = 1;
	protected static final int PLAYER_IS_DOWN = -1;
	private static final int BLOCK_FREE = 0;
	private static final int BLOCK_BRICK = 1;
	private static final int BLOCK_GIRATORY = 12;

	private GameCharacter gameCharacter;

	public Lolo(GameCharacter gameCharacter)
	{
		super();
		this.gameCharacter = gameCharacter;
	}

	public Stair nearStair(boolean toUp, boolean toRight)
	{
		Stair r = null;
		Pyramid pyramid = gameCharacter.getPyramid();
		int count = this.endPlatform(toRight).getCount();
		ArrayList<Stair> candidatesStairs = new ArrayList<Stair>();
		LevelObject footStair;

		Iterator<Stair> it = pyramid.getAllStairs().iterator();
		float tileWidth = Config.getInstance().getLevelTileWidthUnits();
		float posX = gameCharacter.x + gameCharacter.width * 0.5f;
		while (it.hasNext())
		{
			Stair stair = it.next();
			if (toUp)
				footStair = stair.getDownStair();
			else
				footStair = stair.getUpStair();
			if (footStair.y == gameCharacter.y && (toRight && footStair.x >= posX || !toRight && footStair.x <= posX))
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
//System.out.println("MIN: "+min+" COUNT: "+count);
			if (min <= count)
				r = minStair;
		}

		return r;
	}

	public NearStairResult getNearStair(boolean toUp)
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

				if (footStairRight.x - gameCharacter.x < gameCharacter.x - footStairLeft.x)
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

	public EndPlatform endPlatform(boolean toRight)
	{
		int inc;
		int acum = 0;
		int type;
		int count;
		float xAux;
		Pyramid pyramid = gameCharacter.getPyramid();
		xAux = gameCharacter.x;
		if (toRight)
		{
			inc = 1;
			xAux += gameCharacter.width;
		} else

		{
			inc = -1;

		}

		while (pyramid.getCell(xAux, gameCharacter.y, acum, 0) == null
				&& pyramid.getCell(xAux, gameCharacter.y, acum, 1) == null
				&& pyramid.getCell(xAux, gameCharacter.y, acum, -1) != null && this.isColDesplaInMap(acum))
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

	public void correctGiratoryEndPlatform(EndPlatform r, boolean toRight)
	{
		Iterator<LevelObject> it = gameCharacter.getPyramid().getGiratories().iterator();
		boolean condicion = false;
		float posX = gameCharacter.x;

		if (toRight)
			posX += gameCharacter.width;

		while (it.hasNext() && !condicion)
		{
			LevelObject giratoria = it.next();
			if (giratoria.y == gameCharacter.y && (toRight && giratoria.x >= posX || !toRight && giratoria.x <= posX))
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

	public boolean isColDesplaInMap(int col)
	{

		return gameCharacter.getColPosition() + col > 1
				&& gameCharacter.getColPosition() + col < gameCharacter.getPyramid().getMapWidthInTiles() - 1;
	}

	public int typeEndPlatform(float positionX, int iDeltaX)
	{
		int type;
		Pyramid pyramid = gameCharacter.getPyramid();
		if (pyramid.getCell(positionX, gameCharacter.y, iDeltaX, 0) == null
				&& pyramid.getCell(positionX, gameCharacter.y, iDeltaX, 1) == null
				&& pyramid.getCell(positionX, gameCharacter.y, iDeltaX, -1) == null)
			type = EndPlatform.END_CLIFF;
		else if ((pyramid.getCell(positionX, gameCharacter.y, iDeltaX, 1) != null
				&& pyramid.getCell(positionX, gameCharacter.y, iDeltaX, 2) == null
				&& pyramid.getCell(positionX, gameCharacter.y, iDeltaX, 3) == null)
				|| (pyramid.getCell(positionX, gameCharacter.y, iDeltaX, 0) != null
						&& pyramid.getCell(positionX, gameCharacter.y, iDeltaX, 1) == null
						&& pyramid.getCell(positionX, gameCharacter.y, iDeltaX, 2) == null))
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

	private void doInBorderCliff()
	{
		System.out.println("BORDEEEE!!!!!!");
	}

	public void doInCrashToWallOrGiratory(int crashStatus, int type)
	{
		System.out.println("CRASH: " + crashStatus + "    Type: " + type);

	}

	public int crashWallOrGiratory()
	{
		boolean condicion = false;

		int respuesta = BLOCK_FREE;
		float epsilon = Config.getInstance().getLevelTileWidthUnits() * 0.1f;
		if (gameCharacter.isLookRight())
		{
			condicion = gameCharacter.getColPosition() >= gameCharacter.pyramid.getMapWidthInTiles() - 2
					|| gameCharacter.pyramid.getCell(gameCharacter.x + Config.getInstance().getLevelTileWidthUnits(),
							gameCharacter.y + Config.getInstance().getLevelTileHeightUnits()) != null
					||

					gameCharacter.pyramid.getCell(gameCharacter.x + Config.getInstance().getLevelTileWidthUnits(),
							gameCharacter.y) != null;

		} else
		{

			condicion = gameCharacter.getColPosition() <= 1 || gameCharacter.pyramid.getCell(gameCharacter.x - epsilon,
					gameCharacter.y + Config.getInstance().getLevelTileHeightUnits()) != null ||

					gameCharacter.pyramid.getCell(gameCharacter.x - epsilon, gameCharacter.y) != null;

		}
		if (condicion)
			respuesta = BLOCK_BRICK;
		else if (gameCharacter.checkGiratory())
			respuesta = BLOCK_GIRATORY;

		return respuesta;
	}

	public void update()
	{

		if (this.gameCharacter.state == GameCharacter.ST_WALKING)
		{
			float x = this.gameCharacter.x;

			if (this.gameCharacter.isLookRight())
				x = this.gameCharacter.x + this.gameCharacter.width;
			int type = this.typeEndPlatform(x, 0);

			this.checkEndOfPlataform(type);
		}

	}

}
