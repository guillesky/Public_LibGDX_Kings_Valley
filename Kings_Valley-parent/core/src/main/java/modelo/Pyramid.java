package modelo;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;

import util.Constantes;

public class Pyramid
{
	private TiledMap map;
	private int tileWidth;
	private int tileHeight;
	private int mapWidthInTiles;
	private int mapHeightInTiles;
	private int mapWidthInPixels;
	private int mapHeightInPixels;
	private LevelItem[] levelItems;

	public Pyramid(TiledMap map)
	{

		this.map = map;
		MapProperties properties = map.getProperties();
		tileWidth = properties.get("tilewidth", Integer.class);
		tileHeight = properties.get("tileheight", Integer.class);
		mapWidthInTiles = properties.get("width", Integer.class);
		mapHeightInTiles = properties.get("height", Integer.class);
		this.mapHeightInPixels=mapHeightInTiles*tileHeight;
		this.mapWidthInPixels=mapWidthInTiles*tileWidth;
		this.readLevelItem();
	}

	private void readLevelItem()
	{
		MapLayers mapLayers = map.getLayers();
		MapLayer layerObject = mapLayers.get(2);
		this.levelItems = new LevelItem[layerObject.getObjects().getCount()];
		for (int i = 0; i < layerObject.getObjects().getCount(); i++)
		{
			MapObject objeto = layerObject.getObjects().get(i);

			MapProperties mp = objeto.getProperties();
			String stype = (String) mp.get("type");
			float fx = (float) mp.get("x");
			float fy = (float) mp.get("y");
			String sp0 = (String) mp.get("p0");
			String sp1 = (String) mp.get("p1");
			int type = Constantes.stringToInteger.get(stype);
			int x = (int) (fx / 10);
			int y = (int) (fy / 10);
			int p0 = Integer.parseInt(sp0);
			int p1 = Integer.parseInt(sp1);
			LevelItem levelItem = new LevelItem(type, x, y, p0, p1);
			this.levelItems[i] = levelItem;
			System.out.println(levelItem);
			System.out.println("******************************");
		}
	}

	public TiledMap getMap()
	{
		return map;
	}

	public int getTileWidth()
	{
		return tileWidth;
	}

	public int getTileHeight()
	{
		return tileHeight;
	}

	public int getMapWidthInTiles()
	{
		return mapWidthInTiles;
	}

	public int getMapHeightInTiles()
	{
		return mapHeightInTiles;
	}

	public LevelItem[] getLevelItems()
	{
		return levelItems;
	}

	public int getMapWidthInPixels()
	{
		return mapWidthInPixels;
	}

	public int getMapHeightInPixels()
	{
		return mapHeightInPixels;
	}

	
	
}
