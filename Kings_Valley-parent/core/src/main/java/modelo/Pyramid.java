package modelo;

import java.util.ArrayList;
import java.util.Iterator;

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
	private IGrafica interfaz = null;
	private Player player = null;
	private LevelItem doorIn = null;
	private LevelItem doorOut = null;
	private ArrayList<Mummy> mummys = new ArrayList<Mummy>();
	private ArrayList<LevelItem> jewels = new ArrayList<LevelItem>();
	private ArrayList<LevelItem> stairs_dr = new ArrayList<LevelItem>();
	private ArrayList<LevelItem> stairs_dl = new ArrayList<LevelItem>();
	private ArrayList<LevelItem> stairs_ul = new ArrayList<LevelItem>();
	private ArrayList<LevelItem> stairs_ur = new ArrayList<LevelItem>();
	private ArrayList<LevelItem> pickers = new ArrayList<LevelItem>();
	private ArrayList<LevelItem> daggers = new ArrayList<LevelItem>();
	private ArrayList<LevelItem> giratorys = new ArrayList<LevelItem>();
	private ArrayList<LevelItem> walls = new ArrayList<LevelItem>();

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
		this.player = new Player(puerta, this);
		for (int i = 0; i < this.walls.size(); i++)
			System.out.println("WALL: " + this.walls.get(i).toString() +"  "+this.walls.get(i).getType() );
	}

	private void readLevelItem()
	{
		MapLayers mapLayers = map.getLayers();
		MapLayer layerObject = mapLayers.get("items");
		// this.levelItems = new LevelItem[layerObject.getObjects().getCount()];
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
			LevelItem levelItem = new LevelItem(type, fx, fy, p0, p1, width, height);
			switch (type)
			{
			case Constantes.It_mummy:
				this.addMummy(fx, fy, p0);
				break;
			case Constantes.It_door:
				if (levelItem.getP0() == 0)
					this.doorIn = levelItem;
				else
					this.doorOut = levelItem;
				break;
			case Constantes.It_jewel:
				this.jewels.add(levelItem);
				break;
			case Constantes.It_picker:
				this.pickers.add(levelItem);
				break;

			case Constantes.It_dagger:
				this.daggers.add(levelItem);
				break;
			case Constantes.It_stairs:
				switch (p0)
				{
				case Constantes.STAIR_DL:
					this.stairs_dl.add(levelItem);
					break;
				case Constantes.STAIR_DR:
					this.stairs_dr.add(levelItem);
					break;
				case Constantes.STAIR_UL:
					this.stairs_ul.add(levelItem);
					break;
				case Constantes.STAIR_UR:
					this.stairs_ur.add(levelItem);
					break;
				}

				break;
			case Constantes.It_giratory:
				this.giratorys.add(levelItem);
				break;
			case Constantes.It_wall:
				this.walls.add(levelItem);
				break;

			}

		}
	}

	private void addMummy(float fx, float fy, int p0)
	{
		float speedFall = Config.getInstance().getCharacterSpeedFall(), speedWalk = 0, speedWalkStairs = 0,
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
		Mummy mummy = new Mummy(fx, fy, p0, speedFall, speedWalk, speedWalkStairs, speedJump, this);
		this.mummys.add(mummy);

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

	public Iterator<LevelItem> getLevelItems()
	{
		ArrayList<LevelItem> levelItems = new ArrayList<LevelItem>();
		levelItems.addAll(this.pickers);
		levelItems.addAll(this.jewels);
		levelItems.addAll(this.daggers);
		levelItems.addAll(this.stairs_dl);
		levelItems.addAll(this.stairs_dr);
		levelItems.addAll(this.stairs_ul);
		levelItems.addAll(this.stairs_ur);
		levelItems.addAll(this.walls);
		return levelItems.iterator();
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
		this.interfaz.addGraphicElement(element);

	}

	@Override
	public void removeGraphicElement(Object element)
	{
		LevelItem joya = (LevelItem) element;
		this.jewels.remove(joya);
		this.interfaz.removeGraphicElement(element);
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

	public IGrafica getInterfaz()
	{
		return interfaz;
	}

	public ArrayList<Mummy> getMummys()
	{
		return mummys;
	}

	public ArrayList<LevelItem> getJewels()
	{
		return jewels;
	}

	public ArrayList<LevelItem> getStairs_dr()
	{
		return stairs_dr;
	}

	public ArrayList<LevelItem> getStairs_dl()
	{
		return stairs_dl;
	}

	public ArrayList<LevelItem> getStairs_ul()
	{
		return stairs_ul;
	}

	public ArrayList<LevelItem> getStairs_ur()
	{
		return stairs_ur;
	}

	public ArrayList<LevelItem> getPickers()
	{
		return pickers;
	}

	public ArrayList<LevelItem> getDaggers()
	{
		return daggers;
	}

	public ArrayList<LevelItem> getGiratorys()
	{
		return giratorys;
	}

	public void removeJewel(LevelItem joya)
	{
		this.removeGraphicElement(joya);

	}

}
