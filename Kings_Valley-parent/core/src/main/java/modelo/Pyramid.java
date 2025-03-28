package modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;

import mummys.Mummy;
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
    private ArrayList<LevelItem> activators = new ArrayList<LevelItem>();
    private ArrayList<TrapMechanism> trapMechanisms = new ArrayList<TrapMechanism>();
    private ArrayList<GiratoryMechanism> giratoryMechanisms = new ArrayList<GiratoryMechanism>();

    private ArrayList<Cell> unpickableCells = new ArrayList<Cell>();

    private HashMap<LevelItem, LevelItem> hashTraps = new HashMap<LevelItem, LevelItem>();
    private HashMap<LevelItem, GiratoryMechanism> hashGiratoryMechanisms = new HashMap<LevelItem, GiratoryMechanism>();

    private int id;
    private MummyFactory mummyFactory = new MummyFactory();

    public Pyramid(TiledMap map, IGrafica interfaz, int id)
    {
	this.id = id;

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

	this.player = new Player(this.doorIn, this);
	this.corigeCeldasPicables();
    }

    private void corigeCeldasPicables()
    {
	TiledMapTileLayer layer = (TiledMapTileLayer) this.getMap().getLayers().get("front");

	for (int x = 1; x < this.mapWidthInTiles - 1; x++)
	{
	    for (int y = 2; y < this.mapHeightInTiles; y++)
	    {
		Cell cell = layer.getCell(x, y);
		if (cell != null && cell.getTile().getId() >= 220 && cell.getTile().getId() <= 239)
		{
		    if (layer.getCell(x + 1, y - 1) != null || layer.getCell(x + 1, y - 2) != null
			    || layer.getCell(x - 1, y - 1) != null || layer.getCell(x - 1, y - 2) != null)
		    {
			cell.getTile().setId(cell.getTile().getId() - 220);
		    }
		}
	    }
	    Cell cell = layer.getCell(x, 1);
	    if (cell != null && cell.getTile().getId() >= 220 && cell.getTile().getId() <= 239)
	    {
		cell.getTile().setId(cell.getTile().getId() - 220);
	    }
	}
    }

    public int getId()
    {
	return id;
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

	    int type = Constantes.stringToInteger.get(stype);

	    int p0 = Integer.parseInt(sp0);

	    float width = Config.getInstance().getLevelItemWidth();
	    float height = Config.getInstance().getLevelItemHeight();
	    if (type == Constantes.It_stairs)
	    {
		width = Config.getInstance().getStairWidth();
		height = Config.getInstance().getStairHeight();
	    } else if (type == Constantes.It_giratory)
	    {
		width = Config.getInstance().getGiratoryWidth();
		int contador = 2;
		Cell cell = this.getCell(fx, fy + Config.getInstance().getLevelTileHeightUnits() * contador);
		while (cell == null)
		{
		    contador++;
		    cell = this.getCell(fx, fy + Config.getInstance().getLevelTileHeightUnits() * contador);

		}
		height = Config.getInstance().getLevelTileHeightUnits() * contador;
		this.unpickableCells.add(cell);

	    }

	    LevelItem levelItem = new LevelItem(type, fx, fy, p0, width, height);
	    switch (type)
	    {
	    case Constantes.It_mummy:
		this.addMummy(fx, fy, p0);
		break;
	    case Constantes.It_door:
		if (levelItem.getP0() == 0)
		    this.doorIn = levelItem;
		else if (levelItem.getP0() == 1)
		    this.doorOut = levelItem;
		else
		{
		    this.doorOut = levelItem;
		    this.doorIn = levelItem;
		}
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
		GiratoryMechanism giratoryMechanism = new GiratoryMechanism(levelItem);
		this.giratoryMechanisms.add(giratoryMechanism);
		this.hashGiratoryMechanisms.put(levelItem, giratoryMechanism);
		this.unpickableCells.add(this.getCell(levelItem.getX(),
			levelItem.getY() - Config.getInstance().getLevelTileHeightUnits()));

		break;
	    case Constantes.It_wall:
		this.walls.add(levelItem);
		this.unpickableCells.add(this.getCell(levelItem.getX(), levelItem.getY()));
		break;

	    case Constantes.It_activator:
		this.activators.add(levelItem);
		break;

	    }

	}
	this.armaTrampas();
    }

    private void armaTrampas()
    {
	Iterator<LevelItem> itActivators = this.activators.iterator();
	while (itActivators.hasNext())
	{
	    LevelItem activator = itActivators.next();
	    Iterator<LevelItem> itWalls = this.walls.iterator();
	    LevelItem wall = null;
	    if (itWalls.hasNext())
		do
		{
		    wall = itWalls.next();
		} while (itWalls.hasNext() && wall.getP0() != activator.getP0());

	    if (wall.getP0() == activator.getP0())
	    {
		this.hashTraps.put(activator, wall);
	    }
	}

    }

    private void addMummy(float fx, float fy, int p0)
    {
	Mummy mummy = this.mummyFactory.getMummy(fx, fy, p0, this);
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
	levelItems.addAll(this.giratorys);

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
	this.jewels.remove(joya);

	this.removeGraphicElement(new DrawableElement(Constantes.DRAWABLE_LEVEL_ITEM, joya));

    }

    public void removePicker(LevelItem picker)
    {

	this.pickers.remove(picker);
	this.removeGraphicElement(new DrawableElement(Constantes.DRAWABLE_LEVEL_ITEM, picker));

    }

    public void activateGiratory(LevelItem giratory, float deltaTime)
    {

    }

    public ArrayList<LevelItem> getActivators()
    {
	return activators;
    }

    public void activateWall(LevelItem activator)
    {
	this.activators.remove(activator);
	LevelItem wall = this.hashTraps.get(activator);
	TrapMechanism trap = new TrapMechanism(this, wall);
	this.trapMechanisms.add(trap);
	this.addGraphicElement(new DrawableElement(Constantes.DRAWABLE_TRAP, trap));
    }

    public void updateMechanism(float deltaTime)
    {
	for (TrapMechanism trapMechanism : this.trapMechanisms)
	{
	    trapMechanism.update(deltaTime);
	    if (this.checkPlayerSmash(trapMechanism))
		this.death();
	}

	Iterator<TrapMechanism> it = this.trapMechanisms.iterator();
	while (it.hasNext())
	{
	    Mechanism mechanism = it.next();
	    if (!mechanism.isActive())
	    {
		it.remove();
		this.removeGraphicElement(new DrawableElement(Constantes.DRAWABLE_TRAP, mechanism));
	    }

	}

	for (GiratoryMechanism gMechanism : this.giratoryMechanisms)
	{
	    if (gMechanism.isActive())
		gMechanism.update(deltaTime);
	}

    }

    private void death()
    {
	// TODO Auto-generated method stub

    }

    private boolean checkPlayerSmash(TrapMechanism trapMechanism)
    {
	int px = (int) (this.player.getX() / Config.getInstance().getLevelTileWidthUnits());
	int py = (int) (this.player.getY() / Config.getInstance().getLevelTileHeightUnits());
	return (trapMechanism.getX() == px && trapMechanism.getY() == py);

    }

    public GiratoryMechanism getGiratoryMechanism(LevelItem giratory)
    {
	return this.hashGiratoryMechanisms.get(giratory);
    }

    public TiledMapTileLayer.Cell getCell(float x, float y)
    {
	TiledMapTileLayer layer = (TiledMapTileLayer) this.getMap().getLayers().get("front");
	TiledMapTileLayer.Cell cell = layer.getCell((int) (x / Config.getInstance().getLevelTileWidthUnits()),
		(int) (y / Config.getInstance().getLevelTileHeightUnits()));
	return cell;
    }

    public boolean isPickable(Cell celda)
    {
	boolean isBeginStair = (celda != null && celda.getTile().getId() >= 20 && celda.getTile().getId() < 60);

	Iterator<LevelItem> it = this.jewels.iterator();
	Cell cellWithItem = null;
	while (it.hasNext() && cellWithItem != celda)
	{
	    LevelItem item = it.next();
	    cellWithItem = this.getCell(item.getX(), item.getY() - Config.getInstance().getLevelTileHeightUnits());
	}

	it = this.daggers.iterator();

	while (it.hasNext() && cellWithItem != celda)
	{
	    LevelItem item = it.next();
	    cellWithItem = this.getCell(item.getX(), item.getY() - Config.getInstance().getLevelTileHeightUnits());
	}

	return !this.unpickableCells.contains(celda) && !isBeginStair && cellWithItem != celda;
    }

	public void updateMummys(float deltaTime)
	{
		Iterator<Mummy> it =this.mummys.iterator();
		while(it.hasNext())it.next().update(deltaTime);
		
	}
}
