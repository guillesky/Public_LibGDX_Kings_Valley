package modelo.level;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;

import modelo.IGrafica;
import modelo.level.dagger.Dagger;
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
	private LevelObject doorIn = null;
	private LevelObject doorOut = null;
	private ArrayList<LevelObject> jewels = new ArrayList<LevelObject>();
	private ArrayList<LevelObject> stairs_dr = new ArrayList<LevelObject>();
	private ArrayList<LevelObject> stairs_dl = new ArrayList<LevelObject>();
	private ArrayList<LevelObject> stairs_ul = new ArrayList<LevelObject>();
	private ArrayList<LevelObject> stairs_ur = new ArrayList<LevelObject>();
	private ArrayList<LevelObject> pickers = new ArrayList<LevelObject>();

	private ArrayList<LevelObject> giratorys = new ArrayList<LevelObject>();
	private ArrayList<LevelObject> walls = new ArrayList<LevelObject>();
	private ArrayList<LevelObject> activators = new ArrayList<LevelObject>();
	private ArrayList<TrapMechanism> trapMechanisms = new ArrayList<TrapMechanism>();
	private ArrayList<GiratoryMechanism> giratoryMechanisms = new ArrayList<GiratoryMechanism>();
	private ArrayList<Cell> unpickableCells = new ArrayList<Cell>();
	private ArrayList<Dagger> stuckedDaggers = new ArrayList<Dagger>();

	private HashMap<LevelObject, LevelObject> hashTraps = new HashMap<LevelObject, LevelObject>();
	private HashMap<LevelObject, GiratoryMechanism> hashGiratoryMechanisms = new HashMap<LevelObject, GiratoryMechanism>();
	private IGrafica interfaz = null;

	public Pyramid(TiledMap map, LevelObject doorIn, LevelObject doorOut, ArrayList<LevelObject> jewels,
			ArrayList<LevelObject> stairs_dr, ArrayList<LevelObject> stairs_dl, ArrayList<LevelObject> stairs_ur,
			ArrayList<LevelObject> stairs_ul, ArrayList<LevelObject> pickers, ArrayList<Dagger> stuckedDaggers,
			ArrayList<LevelObject> giratorys, ArrayList<LevelObject> walls, ArrayList<LevelObject> activators,
			ArrayList<TrapMechanism> trapMechanisms, ArrayList<GiratoryMechanism> giratoryMechanisms,
			ArrayList<Cell> unpickableCells, HashMap<LevelObject, LevelObject> hashTraps,
			HashMap<LevelObject, GiratoryMechanism> hashGiratoryMechanisms, IGrafica interfaz)
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

	public Iterator<LevelObject> getLevelObjects()
	{
		ArrayList<LevelObject> levelObjects = new ArrayList<LevelObject>();
		levelObjects.addAll(this.pickers);
		levelObjects.addAll(this.jewels);
		levelObjects.addAll(this.stairs_dl);
		levelObjects.addAll(this.stairs_dr);
		levelObjects.addAll(this.stairs_ul);
		levelObjects.addAll(this.stairs_ur);
		levelObjects.addAll(this.walls);
		levelObjects.addAll(this.stuckedDaggers);

		levelObjects.addAll(this.giratorys);

		return levelObjects.iterator();
	}

	public int getMapWidthInPixels()
	{
		return mapWidthInPixels;
	}

	public int getMapHeightInPixels()
	{
		return mapHeightInPixels;
	}

	public LevelObject getDoorIn()
	{
		return doorIn;
	}

	public LevelObject getDoorOut()
	{
		return doorOut;
	}

	public ArrayList<LevelObject> getJewels()
	{
		return jewels;
	}

	public ArrayList<LevelObject> getStairs_dr()
	{
		return stairs_dr;
	}

	public ArrayList<LevelObject> getStairs_dl()
	{
		return stairs_dl;
	}

	public ArrayList<LevelObject> getStairs_ul()
	{
		return stairs_ul;
	}

	public ArrayList<LevelObject> getStairs_ur()
	{
		return stairs_ur;
	}

	public ArrayList<LevelObject> getPickers()
	{
		return pickers;
	}

	public ArrayList<LevelObject> getGiratorys()
	{
		return giratorys;
	}

	public ArrayList<LevelObject> getActivators()
	{
		return activators;
	}

	public void activateWall(LevelObject activator)
	{
		this.activators.remove(activator);
		LevelObject wall = this.hashTraps.get(activator);
		TrapMechanism trap = new TrapMechanism(this, wall);
		this.trapMechanisms.add(trap);
		this.addGraphicElement(new DrawableElement(Constantes.DRAWABLE_TRAP, trap));
	}

	public GiratoryMechanism getGiratoryMechanism(LevelObject giratory)
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

		Iterator<LevelObject> itJewels = this.jewels.iterator();
		Cell cellWithItem = null;
		while (itJewels.hasNext() && cellWithItem != celda)
		{
			LevelObject item = (LevelObject) itJewels.next();
			cellWithItem = this.getCell(item.getX(), item.getY() - Config.getInstance().getLevelTileHeightUnits());
		}

		Iterator<Dagger> itDaggers = this.stuckedDaggers.iterator();

		while (itDaggers.hasNext() && cellWithItem != celda)
		{
			LevelObject item = (LevelObject) itDaggers.next();
			cellWithItem = this.getCell(item.getX(), item.getY() - Config.getInstance().getLevelTileHeightUnits());
		}

		return !this.unpickableCells.contains(celda) && !isBeginStair && cellWithItem != celda;
	}

	

	protected ArrayList<LevelObject> getWalls()
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

	protected HashMap<LevelObject, LevelObject> getHashTraps()
	{
		return hashTraps;
	}

	protected HashMap<LevelObject, GiratoryMechanism> getHashGiratoryMechanisms()
	{
		return hashGiratoryMechanisms;
	}

	public void removePicker(LevelObject picker)
	{
		this.pickers.remove(picker);
		this.removeGraphicElement(new DrawableElement(Constantes.DRAWABLE_LEVEL_ITEM, picker));

	}

	public void removeJewel(LevelObject joya)
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


}
