package modelo.level;

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

import modelo.IGrafica;
import modelo.gameCharacters.mummys.Mummy;
import modelo.gameCharacters.mummys.MummyFactory;
import modelo.gameCharacters.player.Player;
import modelo.level.dagger.Dagger;
import util.Config;
import util.Constantes;

public class LevelReader 
{
    private TiledMap map;

    private IGrafica interfaz = null;
    private Player player = null;
    private LevelObject doorIn = null;
    private LevelObject doorOut = null;
    private ArrayList<LevelObject> jewels = new ArrayList<LevelObject>();
    private ArrayList<LevelObject> stairs_dr = new ArrayList<LevelObject>();
    private ArrayList<LevelObject> stairs_dl = new ArrayList<LevelObject>();
    private ArrayList<LevelObject> stairs_ul = new ArrayList<LevelObject>();
    private ArrayList<LevelObject> stairs_ur = new ArrayList<LevelObject>();
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

    public LevelReader(IGrafica interfaz)
    {
	this.interfaz = interfaz;

    }

    public Level getLevel(TiledMap map, int id, int dificultLevel)
    {

	this.map = map;
	this.resetAll();
	this.readLevelObjects(dificultLevel);
	this.corrigeCeldasPicables();
	this.pyramid = new Pyramid(map, doorIn, doorOut, jewels, stairs_dr, stairs_dl, stairs_ur, stairs_ul, pickers,
		stuckedDaggers, giratorys, walls, activators, trapMechanisms, giratoryMechanisms, unpickableCells,
		hashTraps, hashGiratoryMechanisms,interfaz);
	this.player = new Player(this.doorIn, this.pyramid);
	
	this.generateMummys(dificultLevel);
	Level level = new Level(pyramid, mummys, player);
	return level;

    }

    private void resetAll()
    {
	this.player = null;
	this.doorIn = null;
	this.doorOut = null;
	this.jewels = new ArrayList<LevelObject>();
	this.stairs_dr = new ArrayList<LevelObject>();
	this.stairs_dl = new ArrayList<LevelObject>();
	this.stairs_ul = new ArrayList<LevelObject>();
	this.stairs_ur = new ArrayList<LevelObject>();
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

	this.mummyDatas = new ArrayList<MummyData>();
    }

    private void corrigeCeldasPicables()
    {
	TiledMapTileLayer layer = (TiledMapTileLayer) this.getMap().getLayers().get("front");
	MapProperties properties = map.getProperties();
	int mapWidthInTiles = properties.get("width", Integer.class);
	int mapHeightInTiles = properties.get("height", Integer.class);
	for (int x = 1; x < mapWidthInTiles - 1; x++)
	{
	    for (int y = 2; y < mapHeightInTiles; y++)
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

    private void readLevelObjects(int dificultLevel)
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
		Cell cell = this.getCell(fx, fy + Config.getInstance().getLevelTileHeightUnits() * contador);
		while (cell == null)
		{
		    contador++;
		    cell = this.getCell(fx, fy + Config.getInstance().getLevelTileHeightUnits() * contador);

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
	    case Constantes.It_door:
		levelObject = new LevelObject(type, fx, fy, p0, width, height);
		if (levelObject.getP0() == 0)
		    this.doorIn = levelObject;
		else if (levelObject.getP0() == 1)
		    this.doorOut = levelObject;
		else
		{
		    this.doorOut = levelObject;
		    this.doorIn = levelObject;
		}
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
	    case Constantes.It_stairs:
		levelObject = new LevelObject(type, fx, fy, p0, width, height);

		switch (p0)
		{
		case Constantes.STAIR_DL:
		    this.stairs_dl.add(levelObject);
		    break;
		case Constantes.STAIR_DR:
		    this.stairs_dr.add(levelObject);
		    break;
		case Constantes.STAIR_UL:
		    this.stairs_ul.add(levelObject);
		    break;
		case Constantes.STAIR_UR:
		    this.stairs_ur.add(levelObject);
		    break;
		}

		break;
	    case Constantes.It_giratory:
		levelObject = new LevelObject(type, fx, fy, p0, width, height);

		this.giratorys.add(levelObject);
		GiratoryMechanism giratoryMechanism = new GiratoryMechanism(levelObject);
		this.giratoryMechanisms.add(giratoryMechanism);
		this.hashGiratoryMechanisms.put(levelObject, giratoryMechanism);
		this.unpickableCells.add(this.getCell(levelObject.getX(),
			levelObject.getY() - Config.getInstance().getLevelTileHeightUnits()));

		break;
	    case Constantes.It_wall:
		levelObject = new LevelObject(type, fx, fy, p0, width, height);

		this.walls.add(levelObject);
		this.unpickableCells.add(this.getCell(levelObject.getX(), levelObject.getY()));
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

    public TiledMap getMap()
    {
	return map;
    }

    public Iterator<LevelObject> getLevelObjects()
    {
	ArrayList<LevelObject> levelObjects = new ArrayList<LevelObject>();
	levelObjects.addAll(this.pickers);
	levelObjects.addAll(this.jewels);
	levelObjects.addAll(this.stuckedDaggers);
	levelObjects.addAll(this.stairs_dl);
	levelObjects.addAll(this.stairs_dr);
	levelObjects.addAll(this.stairs_ul);
	levelObjects.addAll(this.stairs_ur);
	levelObjects.addAll(this.walls);
	levelObjects.addAll(this.giratorys);

	return levelObjects.iterator();
    }

   

   

   

   

   

   

    public TiledMapTileLayer.Cell getCell(float x, float y)
    {
	TiledMapTileLayer layer = (TiledMapTileLayer) this.getMap().getLayers().get("front");
	TiledMapTileLayer.Cell cell = layer.getCell((int) (x / Config.getInstance().getLevelTileWidthUnits()),
		(int) (y / Config.getInstance().getLevelTileHeightUnits()));
	return cell;
    }

   
  

  
  

  

    private void generateMummys(int dificultLevel)
    {
	Iterator<MummyData> it = this.mummyDatas.iterator();
	while (it.hasNext())
	{
	    MummyData mummyData = it.next();
	    int mummyType = dificultLevel + mummyData.getType();
	    if (mummyType > Mummy.RED_MUMMY)
		mummyType = Mummy.RED_MUMMY;

	    Mummy mummy = this.mummyFactory.getMummy(mummyData.getX(), mummyData.getY(), mummyType, this.pyramid);
	    this.mummys.add(mummy);
	}
    }
}
