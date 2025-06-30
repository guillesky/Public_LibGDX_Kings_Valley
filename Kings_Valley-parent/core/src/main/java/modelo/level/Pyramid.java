package modelo.level;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;

import modelo.IGrafica;
import modelo.KVEventListener;
import modelo.game.Game;
import modelo.gameCharacters.player.PairInt;
import modelo.level.dagger.Dagger;
import modelo.level.door.Door;
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
	private ArrayList<Door> doors;
	private ArrayList<LevelObject> jewels = new ArrayList<LevelObject>();
	private ArrayList<Stair> positiveStairs;
	private ArrayList<Stair> negativeStairs;
	private ArrayList<Stair> allStairs;
	private ArrayList<LevelObject> pickers = new ArrayList<LevelObject>();

	private ArrayList<LevelObject> giratories = new ArrayList<LevelObject>();
	private ArrayList<LevelObject> walls = new ArrayList<LevelObject>();
	private ArrayList<LevelObject> activators = new ArrayList<LevelObject>();
	private ArrayList<TrapMechanism> trapMechanisms = new ArrayList<TrapMechanism>();
	private ArrayList<GiratoryMechanism> giratoryMechanisms = new ArrayList<GiratoryMechanism>();
	private ArrayList<Cell> unpickableCells = new ArrayList<Cell>();
	private ArrayList<Dagger> stuckedDaggers = new ArrayList<Dagger>();

	private HashMap<LevelObject, LevelObject> hashTraps = new HashMap<LevelObject, LevelObject>();
	private HashMap<LevelObject, GiratoryMechanism> hashGiratoryMechanisms = new HashMap<LevelObject, GiratoryMechanism>();
	private IGrafica interfaz = null;

	public Pyramid(TiledMap map, ArrayList<Door> doors, ArrayList<LevelObject> jewels, ArrayList<Stair> positiveStairs,
			ArrayList<Stair> negativeStairs, ArrayList<LevelObject> pickers, ArrayList<Dagger> stuckedDaggers,
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

		this.doors = doors;
		this.jewels = jewels;

		this.positiveStairs = positiveStairs;
		this.negativeStairs = negativeStairs;

		this.pickers = pickers;
		this.stuckedDaggers = stuckedDaggers;
		this.giratories = giratorys;
		this.walls = walls;
		this.activators = activators;
		this.trapMechanisms = trapMechanisms;
		this.giratoryMechanisms = giratoryMechanisms;
		this.unpickableCells = unpickableCells;
		this.hashTraps = hashTraps;
		this.hashGiratoryMechanisms = hashGiratoryMechanisms;
		this.allStairs=new ArrayList<Stair>();
		allStairs.addAll(this.positiveStairs);
		allStairs.addAll(this.negativeStairs);
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

		levelObjects.addAll(this.walls);
		levelObjects.addAll(this.stuckedDaggers);

		levelObjects.addAll(this.giratories);

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

	public ArrayList<LevelObject> getJewels()
	{
		return jewels;
	}

	public ArrayList<LevelObject> getPickers()
	{
		return pickers;
	}

	public ArrayList<LevelObject> getGiratories()
	{
		return giratories;
	}

	public ArrayList<LevelObject> getActivators()
	{
		return activators;
	}

	public void activateWall(LevelObject activator)
	{
		this.activators.remove(activator);
		LevelObject wall = this.hashTraps.get(activator);
		TrapMechanism trap = new TrapMechanism(this, wall,Config.getInstance().getTimeToEndTrapMechanism());
		this.trapMechanisms.add(trap);
		this.addGraphicElement(new DrawableElement(Constantes.DRAWABLE_TRAP, trap));
		Game.getInstance().eventFired(KVEventListener.ACTIVATE_TRAP, trap);
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

	public TiledMapTileLayer.Cell getCellInTiledCoord(int x, int y)
	{
		TiledMapTileLayer layer = (TiledMapTileLayer) this.getMap().getLayers().get("front");
		TiledMapTileLayer.Cell cell = layer.getCell(x, y);
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
		boolean isBeginStair = (celda != null && (Constantes.tilesPositiveStairs.contains(celda.getTile().getId())
				|| Constantes.tilesNegativeStairs.contains(celda.getTile().getId())
				|| Constantes.tilesPreviusToStairs.contains(celda.getTile().getId())));

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

	private void removeGiratories()
	{
		Iterator<GiratoryMechanism> it = this.giratoryMechanisms.iterator();
		while (it.hasNext())
			this.removeGraphicElement(new DrawableElement(Constantes.DRAWABLE_GYRATORY, it.next().getLevelObject()));
		this.giratoryMechanisms.clear();
		this.giratories.clear();
	}

	public ArrayList<Stair> getPositiveStairs()
	{
		return positiveStairs;
	}

	public ArrayList<Stair> getNegativeStairs()
	{
		return negativeStairs;
	}

	public void endPicking(PairInt pi)
	{
		this.removeGraphicElement(new DrawableElement(Constantes.END_PICKING, pi));

	}

	public ArrayList<Door> getDoors()
	{
		return doors;
	}

	@Override
	public void reset()
	{

	}

	public void prepareToExit()
	{
		this.removeGiratories();
		Iterator<Door> it = this.doors.iterator();
		while (it.hasNext())
		{
			Door door = it.next();
			door.setVisible();
		}
		Game.getInstance().eventFired(KVEventListener.PICKUP_ALL_JEWEL, this);
	}

	public void dispose()
	{
		this.map.dispose();
	}

	@Override
	public float getTimeToExitLevel()
	{
		return this.interfaz.getTimeToExitLevel();
	}

	@Override
	public float getTimeToEnterLevel()
	{

		return this.interfaz.getTimeToEnterLevel();
	}

	@Override
	public float getTimeDying()
	{
		return this.interfaz.getTimeDying();
	}

	public ArrayList<Stair> getAllStairs()
	{
		return allStairs;
	}

	@Override
	public float getTimeToEndGame()
	{
	    
	    return this.interfaz.getTimeToEndGame();
	}
	
	

}
