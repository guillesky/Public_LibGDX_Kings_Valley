package modelo.level;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import util.Config;

public abstract class Mechanism
{
	protected float time = 0;
	protected boolean active = true;

	public abstract void update(float deltaTime);

	public boolean isActive()
	{
		return active;
	}

	public float getTime()
	{
		return time;
	}
	
}
