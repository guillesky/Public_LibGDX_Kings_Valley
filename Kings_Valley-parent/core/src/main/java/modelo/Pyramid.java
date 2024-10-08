package modelo;

import java.util.ArrayList;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import util.Config;
import util.Constantes;

public class Pyramid implements IGrafica
{
    private TiledMap map;
    private int tileWidth;
    private int tileHeight;
    private int mapWidthInTiles;
    private int mapHeightInTiles;
    private int mapWidthInPixels;
    private int mapHeightInPixels;
    private LevelItem[] levelItems;
    private IGrafica interfaz = null;
    private Player player = null;
    private LevelItem doorIn = null;
    private LevelItem doorOut = null;
    private ArrayList<Mummy> mummys = new ArrayList<Mummy>();

    public Pyramid(TiledMap map, IGrafica interfaz)
    {
	this.map = map;
	MapProperties properties = map.getProperties();
	tileWidth = properties.get("tilewidth", Integer.class);
	tileHeight = properties.get("tileheight", Integer.class);
	mapWidthInTiles = properties.get("width", Integer.class);
	mapHeightInTiles = properties.get("height", Integer.class);
	this.mapHeightInPixels = mapHeightInTiles * tileHeight;
	this.mapWidthInPixels = mapWidthInTiles * tileWidth;
	this.interfaz = interfaz;
	this.readLevelItem();
	LevelItem puerta = this.doorIn;
	if (puerta == null)
	    puerta = this.doorOut;
	this.player = new Player(puerta, (TiledMapTileLayer) this.map.getLayers().get("front"));
    }

    private void readLevelItem()
    {
	MapLayers mapLayers = map.getLayers();
	MapLayer layerObject = mapLayers.get("items");
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

	    int p0 = Integer.parseInt(sp0);
	    int p1 = Integer.parseInt(sp1);
	    float width = (float) mp.get("width");
	    float height = (float) mp.get("height");
	    LevelItem levelItem;
	    if (type == Constantes.It_mummy)
	    {
		int speedFall = Config.getInstance().getCharacterSpeedFall(), speedWalk = 0, speedWalkStairs = 0,
			speedJump = 0;
		switch (p0)
		{
		case 0:
		{
		    speedJump = Config.getInstance().getMummyWhiteSpeedJump();
		    speedWalk = Config.getInstance().getMummyWhiteSpeedWalk();
		    speedWalkStairs = Config.getInstance().getMummyWhiteSpeedWalkStairs();
		    break;
		}

		case 1:
		{
		    speedJump = Config.getInstance().getMummyBlueSpeedJump();
		    speedWalk = Config.getInstance().getMummyBlueSpeedWalk();
		    speedWalkStairs = Config.getInstance().getMummyBlueSpeedWalkStairs();
		    break;
		}
		case 2:
		{
		    speedJump = Config.getInstance().getMummyOrangeSpeedJump();
		    speedWalk = Config.getInstance().getMummyOrangeSpeedWalk();
		    speedWalkStairs = Config.getInstance().getMummyOrangeSpeedWalkStairs();
		    break;
		}
		case 3:
		{
		    speedJump = Config.getInstance().getMummyRedSpeedJump();
		    speedWalk = Config.getInstance().getMummyRedSpeedWalk();
		    speedWalkStairs = Config.getInstance().getMummyRedSpeedWalkStairs();
		    break;
		}

		}
		levelItem = new Mummy(fx, fy, p0, (TiledMapTileLayer) this.map.getLayers().get("front"), speedFall,
			speedWalk, speedWalkStairs, speedJump);
		this.mummys.add((Mummy) levelItem);
	    } else
		levelItem = new LevelItem(type, fx, fy, p0, p1, width, height);
	    this.levelItems[i] = levelItem;
	    if (levelItem.getType() == Constantes.It_door)
	    {
		if (levelItem.getP0() == 0)
		    this.doorIn = levelItem;
		else
		    this.doorOut = levelItem;
	    }
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

    @Override
    public void addGraphicElement(Object element)
    {
	// TODO Auto-generated method stub

    }

    @Override
    public void removeGraphicElement(Object element)
    {
	// TODO Auto-generated method stub

    }

    public Player getPlayer()
    {
	return player;
    }

    public LevelItem getDoorIn()
    {
	return doorIn;
    }

    public LevelItem getDoorOut()
    {
	return doorOut;
    }

}
