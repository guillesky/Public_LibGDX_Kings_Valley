package modelo.level;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import util.Config;

public class GiratoryMechanism extends Mechanism
{
	LevelItem levelItem;
	private boolean right;
	private boolean triplex;

	public GiratoryMechanism(LevelItem levelItem)
	{
		this.levelItem = levelItem;
		this.triplex=levelItem.getHeight() == Config.getInstance().getLevelTileHeightUnits() * 3.0f;
		this.right = (levelItem.getP0() == 0);
		this.active = false;
	}

	@Override
	public void update(float deltaTime)
	{
		this.time += deltaTime;
		if (this.time >= 1)
		{
			this.end();
		}
	}

	private void end()
	{
		this.time = 0;
		this.right = !this.right;
		this.active = false;
	}

	public void activate()
	{
		this.active = true;
	}

	public LevelItem getLevelItem()
	{
		return levelItem;
	}

	public boolean isRight()
	{
		return right;
	}

	public boolean isTriplex()
	{
		return triplex;
	}

	
}
