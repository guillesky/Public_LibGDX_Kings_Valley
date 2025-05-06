package modelo.level;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import util.Config;

public class TrapMechanism extends Mechanism
{

	private TiledMapTileLayer layer;

	private TiledMapTile tile;
	private int x;
	private int y;

	public TrapMechanism(Pyramid pyramid, LevelObject wall)
	{
		this.layer = (TiledMapTileLayer) pyramid.getMap().getLayers().get("front");

		this.x = wall.getColPosition();
		this.y = wall.getRowPosition();
		this.tile = this.layer.getCell(x, y).getTile();

	}

	@Override
	public void update(float deltaTime)
	{
		this.incTime(deltaTime);
		if (this.time >= 1)
		{
			this.nextCell();
		}
	}

	private void nextCell()
	{
		this.resetTime();
		y--;
		TiledMapTileLayer.Cell newCell = new TiledMapTileLayer.Cell();
		newCell.setTile(tile);
		this.layer.setCell(x, y, newCell);

		if (this.layer.getCell(x, y - 1) != null)
			this.active = false;
	}

	public TiledMapTile getTile()
	{
		return tile;
	}

	public float getX()
	{
		float auxX = x;
		float r = auxX * Config.getInstance().getLevelObjectHeight();
		return r;
	}

	public float getY()
	{
		float auxY = y;
		float r = (auxY - this.time) * Config.getInstance().getLevelObjectHeight();
		return r;
	}

}
