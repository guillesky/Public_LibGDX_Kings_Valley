package modelo.level;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import modelo.IGrafica;
import modelo.gameCharacters.mummys.Mummy;
import modelo.gameCharacters.mummys.MummyFactory;
import modelo.gameCharacters.player.Player;
import modelo.level.dagger.Dagger;
import modelo.level.door.Door;
import util.Config;
import util.Constantes;

public class LevelReader
{

	
	
	private ArrayList<Door> doors = null;

	private ArrayList<LevelObject> jewels = new ArrayList<LevelObject>();
	private ArrayList<LevelObject> pickers = new ArrayList<LevelObject>();
	private ArrayList<Dagger> stuckedDaggers = new ArrayList<Dagger>();
	private ArrayList<LevelObject> giratorys = new ArrayList<LevelObject>();
	private ArrayList<LevelObject> walls = new ArrayList<LevelObject>();
	private ArrayList<LevelObject> activators = new ArrayList<LevelObject>();
	private ArrayList<TrapMechanism> trapMechanisms = new ArrayList<TrapMechanism>();
	private ArrayList<GiratoryMechanism> giratoryMechanisms = new ArrayList<GiratoryMechanism>();
	private ArrayList<Cell> unpickableCells = new ArrayList<Cell>();
	private HashMap<LevelObject, LevelObject> hashTraps = new HashMap<LevelObject, LevelObject>();
	private HashMap<LevelObject, GiratoryMechanism> hashGiratoryMechanisms = new HashMap<LevelObject, GiratoryMechanism>();
	private Pyramid pyramid;
	private ArrayList<Mummy> mummys = new ArrayList<Mummy>();
	private MummyFactory mummyFactory = new MummyFactory();
	private ArrayList<MummyData> mummyDatas = new ArrayList<MummyData>();
	private ArrayList<Stair> positiveStairs;
	private ArrayList<Stair> negativeStairs;
	private TmxMapLoader mapLoader = new TmxMapLoader();
	

	

	public Level getLevel(int id, String mapFile, int dificultLevel, boolean isCompleted, Door doorFrom,
			IGrafica interfaz)
	{
		TiledMap map = this.mapLoader.load(mapFile);
		this.resetAll();
		this.readLevelObjects(map, dificultLevel, id);
		this.readStairs(map);
		if (isCompleted)
		{
			this.jewels.clear();
			this.giratoryMechanisms.clear();
			this.hashGiratoryMechanisms.clear();
		}
		this.pyramid = new Pyramid(map, doors, jewels, positiveStairs, negativeStairs, pickers, stuckedDaggers,
				giratorys, walls, activators, trapMechanisms, giratoryMechanisms, unpickableCells, hashTraps,
				hashGiratoryMechanisms, interfaz);

		if (isCompleted)
			this.pyramid.prepareToExit();
		Door door = this.getDoorIn(id, doorFrom);
		float y = door.getPassage().y;
		float x = door.getPassage().x;
		
		Player player = new Player(x, y, this.pyramid);
		this.generateMummys(player,dificultLevel);
		
		Level level = new Level(id, pyramid, mummys, door,player);
		return level;

	}

	private Door getDoorIn(int id, Door doorFrom)
	{
		Door door = null;
		if (this.doors.size() == 1)
			door = this.doors.get(0);
		else
		{
			if (doorFrom == null || doorFrom.getLevelConnected() == Door.TO_NEXT
					|| doorFrom.getLevelConnected() == Door.UNIQUE)
				door = this.searchDoor(Door.TO_PREVIUS);

			else if (doorFrom.getLevelConnected() == Door.TO_PREVIUS)
				door = this.searchDoor(Door.TO_NEXT);
			else
				door = this.searchDoor(doorFrom.getIdLevel());
		}
		return door;
	}

	private Door searchDoor(int value)
	{

		Iterator<Door> it = this.doors.iterator();
		Door respuesta = null;
		Door door;
		do
		{
			door = it.next();
		} while (it.hasNext() && door.getLevelConnected() != value);

		if (door.getLevelConnected() == value)
		{
			respuesta = door;
		}

		return respuesta;
	}

	private void readStairs(TiledMap map)
	{

		int mapWidthInTiles = map.getProperties().get("width", Integer.class);
		int mapHeightInTiles = map.getProperties().get("height", Integer.class);
		TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get("front");
		Cell cell;
		for (int i = 0; i < mapHeightInTiles; i++)
			for (int j = 0; j < mapWidthInTiles; j++)
			{
				cell = layer.getCell(j, i);
				if (cell != null)
				{
					int value = cell.getTile().getId();
					if (Constantes.tilesPositiveStairs.contains(value))
					{
						this.generateStair(map, j, i, true);

					} else if (Constantes.tilesNegativeStairs.contains(value))
					{
						this.generateStair(map, j, i, false);

					}
				}
			}

	}

	private void generateStair(TiledMap map, int j, int i, boolean isPositive)
	{
		TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get("stairs");

		float xUpStair;
		float yUpStair;
		float xDownStair;
		float yDownStair;
		int down;
		int up;
		int sign;
		float incX = 0;

		i++;
		if (isPositive)
		{
			up = Constantes.STAIR_DL;
			down = Constantes.STAIR_UR;
			sign = -1;
		} else
		{
			up = Constantes.STAIR_DR;
			down = Constantes.STAIR_UL;
			sign = 1;
			incX = Config.getInstance().getLevelTileWidthUnits() - Config.getInstance().getStairWidth();
		}

		xUpStair = j * Config.getInstance().getLevelTileWidthUnits() + incX;
		yUpStair = i * Config.getInstance().getLevelTileHeightUnits();

		do
		{
			i--;
			j += sign;
		} while (layer.getCell(j + sign, i - 1) != null);

		xDownStair = j * Config.getInstance().getLevelTileWidthUnits() + incX;
		yDownStair = i * Config.getInstance().getLevelTileHeightUnits();

		LevelObject upStair = new LevelObject(Constantes.It_stairs, xUpStair, yUpStair, up,
				Config.getInstance().getStairWidth(), Config.getInstance().getStairHeight());
		LevelObject downStair = new LevelObject(Constantes.It_stairs, xDownStair, yDownStair, down,
				Config.getInstance().getStairWidth(), Config.getInstance().getStairHeight());
		Stair stair = new Stair(downStair, upStair);

		if (isPositive)
			this.positiveStairs.add(stair);
		else
			this.negativeStairs.add(stair);
	}

	private void resetAll()
	{
		
		this.doors = new ArrayList<Door>();
		this.jewels = new ArrayList<LevelObject>();
		this.pickers = new ArrayList<LevelObject>();
		this.stuckedDaggers = new ArrayList<Dagger>();
		this.giratorys = new ArrayList<LevelObject>();
		this.walls = new ArrayList<LevelObject>();
		this.activators = new ArrayList<LevelObject>();
		this.trapMechanisms = new ArrayList<TrapMechanism>();
		this.giratoryMechanisms = new ArrayList<GiratoryMechanism>();
		this.unpickableCells = new ArrayList<Cell>();
		this.hashTraps = new HashMap<LevelObject, LevelObject>();
		this.hashGiratoryMechanisms = new HashMap<LevelObject, GiratoryMechanism>();
		this.pyramid = null;
		this.mummys = new ArrayList<Mummy>();
		this.positiveStairs = new ArrayList<Stair>();
		this.negativeStairs = new ArrayList<Stair>();
		this.mummyDatas = new ArrayList<MummyData>();
	}


	private void readLevelObjects(TiledMap map, int dificultLevel, int id)
	{
		MapLayers mapLayers = map.getLayers();
		MapLayer layerObject = mapLayers.get("items");
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

			float width = Config.getInstance().getLevelObjectWidth();
			float height = Config.getInstance().getLevelObjectHeight();
			if (type == Constantes.It_stairs)
			{
				width = Config.getInstance().getStairWidth();
				height = Config.getInstance().getStairHeight();
			} else if (type == Constantes.It_giratory)
			{
				width = Config.getInstance().getGiratoryWidth();
				int contador = 2;
				Cell cell = this.getCell(map, fx, fy + Config.getInstance().getLevelTileHeightUnits() * contador);
				while (cell == null)
				{
					contador++;
					cell = this.getCell(map, fx, fy + Config.getInstance().getLevelTileHeightUnits() * contador);

				}
				height = Config.getInstance().getLevelTileHeightUnits() * contador;
				this.unpickableCells.add(cell);

			}

			LevelObject levelObject;
			switch (type)
			{
			case Constantes.It_mummy:
				this.mummyDatas.add(new MummyData(fx, fy, p0));

				break;
			case Constantes.It_door_lever:
				levelObject = new LevelObject(type, fx, fy, p0, width, height);
				Door door = new Door(levelObject, id);
				this.doors.add(door);

				break;
			case Constantes.It_jewel:
				levelObject = new LevelObject(type, fx, fy, p0, width, height);
				this.jewels.add(levelObject);
				break;
			case Constantes.It_picker:
				levelObject = new LevelObject(type, fx, fy, p0, width, height);
				this.pickers.add(levelObject);
				break;

			case Constantes.It_dagger:

				this.stuckedDaggers.add(new Dagger(type, fx, fy, p0, width, height));
				break;

			case Constantes.It_giratory:
				levelObject = new LevelObject(type, fx, fy, p0, width, height);

				this.giratorys.add(levelObject);
				GiratoryMechanism giratoryMechanism = new GiratoryMechanism(levelObject);
				this.giratoryMechanisms.add(giratoryMechanism);
				this.hashGiratoryMechanisms.put(levelObject, giratoryMechanism);
				this.unpickableCells.add(this.getCell(map, levelObject.getX(),
						levelObject.getY() - Config.getInstance().getLevelTileHeightUnits()));

				break;
			case Constantes.It_wall:
				levelObject = new LevelObject(type, fx, fy, p0, width, height);

				this.walls.add(levelObject);
				this.unpickableCells.add(this.getCell(map, levelObject.getX(), levelObject.getY()));
				break;

			case Constantes.It_activator:
				levelObject = new LevelObject(type, fx, fy, p0, width, height);

				this.activators.add(levelObject);
				break;

			}

		}
		this.armaTrampas();
	}

	private void armaTrampas()
	{
		Iterator<LevelObject> itActivators = this.activators.iterator();
		while (itActivators.hasNext())
		{
			LevelObject activator = itActivators.next();
			Iterator<LevelObject> itWalls = this.walls.iterator();
			LevelObject wall = null;
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

	public Iterator<LevelObject> getLevelObjects()
	{
		ArrayList<LevelObject> levelObjects = new ArrayList<LevelObject>();
		levelObjects.addAll(this.pickers);
		levelObjects.addAll(this.jewels);
		levelObjects.addAll(this.stuckedDaggers);

		levelObjects.addAll(this.walls);
		levelObjects.addAll(this.giratorys);

		return levelObjects.iterator();
	}

	public TiledMapTileLayer.Cell getCell(TiledMap map, float x, float y)
	{
		TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get("front");
		TiledMapTileLayer.Cell cell = layer.getCell((int) (x / Config.getInstance().getLevelTileWidthUnits()),
				(int) (y / Config.getInstance().getLevelTileHeightUnits()));
		return cell;
	}

	private void generateMummys(Player player, int dificultLevel)
	{
		Iterator<MummyData> it = this.mummyDatas.iterator();
		while (it.hasNext())
		{
			MummyData mummyData = it.next();
			int mummyType = dificultLevel + mummyData.getType();
			if (mummyType > MummyFactory.RED_MUMMY)
				mummyType = MummyFactory.RED_MUMMY;

			Mummy mummy = this.mummyFactory.getMummy(mummyData.getX(), mummyData.getY(), mummyType, this.pyramid,player);
			this.mummys.add(mummy);
		}
	}

}
