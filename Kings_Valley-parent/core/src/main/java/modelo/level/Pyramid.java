package modelo.level;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;

import modelo.IGrafica;
import modelo.Juego;
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
    private LevelItem doorIn = null;
    private LevelItem doorOut = null;
    private ArrayList<LevelItem> jewels = new ArrayList<LevelItem>();
    private ArrayList<LevelItem> stairs_dr = new ArrayList<LevelItem>();
    private ArrayList<LevelItem> stairs_dl = new ArrayList<LevelItem>();
    private ArrayList<LevelItem> stairs_ul = new ArrayList<LevelItem>();
    private ArrayList<LevelItem> stairs_ur = new ArrayList<LevelItem>();
    private ArrayList<LevelItem> pickers = new ArrayList<LevelItem>();

    private ArrayList<LevelItem> giratorys = new ArrayList<LevelItem>();
    private ArrayList<LevelItem> walls = new ArrayList<LevelItem>();
    private ArrayList<LevelItem> activators = new ArrayList<LevelItem>();
    private ArrayList<TrapMechanism> trapMechanisms = new ArrayList<TrapMechanism>();
    private ArrayList<GiratoryMechanism> giratoryMechanisms = new ArrayList<GiratoryMechanism>();
    private ArrayList<Cell> unpickableCells = new ArrayList<Cell>();
    private ArrayList<Dagger> stuckedDaggers = new ArrayList<Dagger>();
    private ArrayList<Dagger> fliyingDaggers = new ArrayList<Dagger>();

    private HashMap<LevelItem, LevelItem> hashTraps = new HashMap<LevelItem, LevelItem>();
    private HashMap<LevelItem, GiratoryMechanism> hashGiratoryMechanisms = new HashMap<LevelItem, GiratoryMechanism>();
    private IGrafica interfaz = null;

    public Pyramid(TiledMap map, LevelItem doorIn, LevelItem doorOut, ArrayList<LevelItem> jewels,
	    ArrayList<LevelItem> stairs_dr, ArrayList<LevelItem> stairs_dl, ArrayList<LevelItem> stairs_ur,
	    ArrayList<LevelItem> stairs_ul, ArrayList<LevelItem> pickers, ArrayList<Dagger> stuckedDaggers,
	    ArrayList<LevelItem> giratorys, ArrayList<LevelItem> walls, ArrayList<LevelItem> activators,
	    ArrayList<TrapMechanism> trapMechanisms, ArrayList<GiratoryMechanism> giratoryMechanisms,
	    ArrayList<Cell> unpickableCells, HashMap<LevelItem, LevelItem> hashTraps,
	    HashMap<LevelItem, GiratoryMechanism> hashGiratoryMechanisms, IGrafica interfaz)
    {

	this.map = map;
	this.interfaz = interfaz;
	MapProperties properties = map.getProperties();
	tileWidth = properties.get("tilewidth", Integer.class);
	tileHeight = properties.get("tileheight", Integer.class);
	mapWidthInTiles = properties.get("width", Integer.class);
	mapHeightInTiles = properties.get("height", Integer.class);
	this.mapHeightInPixels = mapHeightInTiles * tileHeight;
	this.mapWidthInPixels = mapWidthInTiles * tileWidth;
	this.doorIn = doorIn;
	this.doorOut = doorOut;

	this.jewels = jewels;
	this.stairs_dl = stairs_dl;
	this.stairs_ul = stairs_ul;
	this.stairs_dr = stairs_dr;
	this.stairs_ur = stairs_ur;
	this.pickers = pickers;
	this.stuckedDaggers = stuckedDaggers;
	this.giratorys = giratorys;
	this.walls = walls;
	this.activators = activators;
	this.trapMechanisms = trapMechanisms;
	this.giratoryMechanisms = giratoryMechanisms;
	this.unpickableCells = unpickableCells;
	this.hashTraps = hashTraps;
	this.hashGiratoryMechanisms = hashGiratoryMechanisms;

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
	levelItems.addAll(this.stairs_dl);
	levelItems.addAll(this.stairs_dr);
	levelItems.addAll(this.stairs_ul);
	levelItems.addAll(this.stairs_ur);
	levelItems.addAll(this.walls);
	levelItems.addAll(this.stuckedDaggers);

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

    public LevelItem getDoorIn()
    {
	return doorIn;
    }

    public LevelItem getDoorOut()
    {
	return doorOut;
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

    public ArrayList<LevelItem> getGiratorys()
    {
	return giratorys;
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

    public TiledMapTileLayer.Cell getCell(float x, float y, int i, int j)
    {
	TiledMapTileLayer layer = (TiledMapTileLayer) this.getMap().getLayers().get("front");
	TiledMapTileLayer.Cell cell = layer.getCell((int) (x / Config.getInstance().getLevelTileWidthUnits()) + i,
		(int) (y / Config.getInstance().getLevelTileHeightUnits()) + j);
	return cell;
    }

    public boolean isPickable(Cell celda)
    {
	boolean isBeginStair = (celda != null && celda.getTile().getId() >= 20 && celda.getTile().getId() < 60);

	Iterator it = this.jewels.iterator();
	Cell cellWithItem = null;
	while (it.hasNext() && cellWithItem != celda)
	{
	    LevelItem item = (LevelItem) it.next();
	    cellWithItem = this.getCell(item.getX(), item.getY() - Config.getInstance().getLevelTileHeightUnits());
	}

	it = this.stuckedDaggers.iterator();

	while (it.hasNext() && cellWithItem != celda)
	{
	    LevelItem item = (LevelItem) it.next();
	    cellWithItem = this.getCell(item.getX(), item.getY() - Config.getInstance().getLevelTileHeightUnits());
	}

	return !this.unpickableCells.contains(celda) && !isBeginStair && cellWithItem != celda;
    }

    public void removeStuckedDagger(Dagger dagger)
    {
	this.stuckedDaggers.remove(dagger);
	this.removeGraphicElement(new DrawableElement(Constantes.DRAWABLE_LEVEL_ITEM, dagger));


    }

    public void addStuckedDagger(Dagger dagger)
    {
	this.stuckedDaggers.add(dagger);
	this.addGraphicElement(new DrawableElement(Constantes.DRAWABLE_LEVEL_ITEM, dagger));
    }

    
    protected ArrayList<LevelItem> getWalls()
    {
	return walls;
    }

    protected ArrayList<TrapMechanism> getTrapMechanisms()
    {
	return trapMechanisms;
    }

    protected ArrayList<GiratoryMechanism> getGiratoryMechanisms()
    {
	return giratoryMechanisms;
    }

    protected ArrayList<Cell> getUnpickableCells()
    {
	return unpickableCells;
    }

    public ArrayList<Dagger> getStuckedDaggers()
    {
	return stuckedDaggers;
    }

    protected HashMap<LevelItem, LevelItem> getHashTraps()
    {
	return hashTraps;
    }

    protected HashMap<LevelItem, GiratoryMechanism> getHashGiratoryMechanisms()
    {
	return hashGiratoryMechanisms;
    }

    public void removePicker(LevelItem picker)
    {
	this.pickers.remove(picker);
	this.removeGraphicElement(new DrawableElement(Constantes.DRAWABLE_LEVEL_ITEM, picker));

    }

    public void removeJewel(LevelItem joya)
    {
	this.jewels.remove(joya);
	this.removeGraphicElement(new DrawableElement(Constantes.DRAWABLE_LEVEL_ITEM, joya));

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
    public void updateFlyingDagger(float deltaTime)
    {
	Iterator<Dagger> it = this.fliyingDaggers.iterator();
	while (it.hasNext())
	    it.next().incX(Config.getInstance().getFlyingDaggerSpeed() * deltaTime);

    }
    
    public void addFlyingDagger(Dagger dagger)
    {
	this.fliyingDaggers.add(dagger);
	this.addGraphicElement(new DrawableElement(Constantes.DRAWABLE_FLYING_DAGGER, dagger));
    }

    public void removeFlyingDagger(Dagger dagger)
    {
	this.fliyingDaggers.remove(dagger);
	this.removeGraphicElement(new DrawableElement(Constantes.DRAWABLE_FLYING_DAGGER, dagger));
    }

    
}
