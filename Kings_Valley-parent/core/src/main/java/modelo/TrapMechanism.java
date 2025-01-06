package modelo;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import util.Config;

public class TrapMechanism extends Mechanism
{

    private TiledMapTileLayer layer;

    private TiledMapTile tile;
    private int x;
    private int y;

    public TrapMechanism(Pyramid pyramid, LevelItem wall)
    {
	this.layer = (TiledMapTileLayer) pyramid.getMap().getLayers().get("front");
	
	this.x = wall.getColPosition();
	this.y = wall.getRowPosition();
	this.tile = this.layer.getCell(x, y).getTile();
	
    }

    @Override
    public void update(float deltaTime)
    {
	this.time += deltaTime;
	if (this.time >= 1)
	{
	    this.nextCell();
	}
    }

    private void nextCell()
    {
	this.time = 0;
	if (this.layer.getCell(x, y - 1) == null)
	{
	    y--;
	    TiledMapTileLayer.Cell newCell = new TiledMapTileLayer.Cell();
	    newCell.setTile(tile);
	    this.layer.setCell(x, y, newCell);
	} else
	    this.active = false;
    }

    public TiledMapTileLayer getLayer()
    {
	return layer;
    }

    public TiledMapTile getTile()
    {
	return tile;
    }

    public int getX()
    {
	return x;
    }

    public int getY()
    {
	return y;
    }

}
