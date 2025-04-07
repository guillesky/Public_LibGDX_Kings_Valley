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
import util.Config;
import util.Constantes;

public class LevelReader 
{
    private TiledMap map;

    private IGrafica interfaz = null;
    private Player player = null;
    private LevelItem doorIn = null;
    private LevelItem doorOut = null;
    private ArrayList<LevelItem> jewels = new ArrayList<LevelItem>();
    private ArrayList<LevelItem> stairs_dr = new ArrayList<LevelItem>();
    private ArrayList<LevelItem> stairs_dl = new ArrayList<LevelItem>();
    private ArrayList<LevelItem> stairs_ul = new ArrayList<LevelItem>();
    private ArrayList<LevelItem> stairs_ur = new ArrayList<LevelItem>();
    private ArrayList<LevelItem> pickers = new ArrayList<LevelItem>();
    private ArrayList<Dagger> stuckedDaggers = new ArrayList<Dagger>();
    private ArrayList<Dagger> fliyingDaggers = new ArrayList<Dagger>();
    private ArrayList<LevelItem> giratorys = new ArrayList<LevelItem>();
    private ArrayList<LevelItem> walls = new ArrayList<LevelItem>();
    private ArrayList<LevelItem> activators = new ArrayList<LevelItem>();
    private ArrayList<TrapMechanism> trapMechanisms = new ArrayList<TrapMechanism>();
    private ArrayList<GiratoryMechanism> giratoryMechanisms = new ArrayList<GiratoryMechanism>();
    private ArrayList<Cell> unpickableCells = new ArrayList<Cell>();
    private HashMap<LevelItem, LevelItem> hashTraps = new HashMap<LevelItem, LevelItem>();
    private HashMap<LevelItem, GiratoryMechanism> hashGiratoryMechanisms = new HashMap<LevelItem, GiratoryMechanism>();
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
	this.readLevelItem(dificultLevel);
	this.corrigeCeldasPicables();
	this.pyramid = new Pyramid(map, doorIn, doorIn, jewels, stairs_dr, stairs_dl, stairs_ur, stairs_ul, pickers,
		stuckedDaggers, giratorys, walls, activators, trapMechanisms, giratoryMechanisms, unpickableCells,
		hashTraps, hashGiratoryMechanisms,interfaz);
	this.player = new Player(this.doorIn, this.pyramid);
	
	this.generateMummys(dificultLevel);
	Level level = new Level(id, interfaz, pyramid, mummys, player);
	return level;

    }

    private void resetAll()
    {
	this.player = null;
	this.doorIn = null;
	this.doorOut = null;
	this.jewels = new ArrayList<LevelItem>();
	this.stairs_dr = new ArrayList<LevelItem>();
	this.stairs_dl = new ArrayList<LevelItem>();
	this.stairs_ul = new ArrayList<LevelItem>();
	this.stairs_ur = new ArrayList<LevelItem>();
	this.pickers = new ArrayList<LevelItem>();
	this.stuckedDaggers = new ArrayList<Dagger>();
	this.fliyingDaggers = new ArrayList<Dagger>();
	this.giratorys = new ArrayList<LevelItem>();
	this.walls = new ArrayList<LevelItem>();
	this.activators = new ArrayList<LevelItem>();
	this.trapMechanisms = new ArrayList<TrapMechanism>();
	this.giratoryMechanisms = new ArrayList<GiratoryMechanism>();
	this.unpickableCells = new ArrayList<Cell>();
	this.hashTraps = new HashMap<LevelItem, LevelItem>();
	this.hashGiratoryMechanisms = new HashMap<LevelItem, GiratoryMechanism>();
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

    private void readLevelItem(int dificultLevel)
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

	    LevelItem levelItem;
	    switch (type)
	    {
	    case Constantes.It_mummy:
		this.mummyDatas.add(new MummyData(fx, fy, p0));

		break;
	    case Constantes.It_door:
		levelItem = new LevelItem(type, fx, fy, p0, width, height);
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
		levelItem = new LevelItem(type, fx, fy, p0, width, height);
		this.jewels.add(levelItem);
		break;
	    case Constantes.It_picker:
		levelItem = new LevelItem(type, fx, fy, p0, width, height);
		this.pickers.add(levelItem);
		break;

	    case Constantes.It_dagger:

		this.stuckedDaggers.add(new Dagger(type, fx, fy, p0, width, height));
		break;
	    case Constantes.It_stairs:
		levelItem = new LevelItem(type, fx, fy, p0, width, height);

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
		levelItem = new LevelItem(type, fx, fy, p0, width, height);

		this.giratorys.add(levelItem);
		GiratoryMechanism giratoryMechanism = new GiratoryMechanism(levelItem);
		this.giratoryMechanisms.add(giratoryMechanism);
		this.hashGiratoryMechanisms.put(levelItem, giratoryMechanism);
		this.unpickableCells.add(this.getCell(levelItem.getX(),
			levelItem.getY() - Config.getInstance().getLevelTileHeightUnits()));

		break;
	    case Constantes.It_wall:
		levelItem = new LevelItem(type, fx, fy, p0, width, height);

		this.walls.add(levelItem);
		this.unpickableCells.add(this.getCell(levelItem.getX(), levelItem.getY()));
		break;

	    case Constantes.It_activator:
		levelItem = new LevelItem(type, fx, fy, p0, width, height);

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

    public TiledMap getMap()
    {
	return map;
    }

    public Iterator<LevelItem> getLevelItems()
    {
	ArrayList<LevelItem> levelItems = new ArrayList<LevelItem>();
	levelItems.addAll(this.pickers);
	levelItems.addAll(this.jewels);
	levelItems.addAll(this.stuckedDaggers);
	levelItems.addAll(this.stairs_dl);
	levelItems.addAll(this.stairs_dr);
	levelItems.addAll(this.stairs_ul);
	levelItems.addAll(this.stairs_ur);
	levelItems.addAll(this.walls);
	levelItems.addAll(this.giratorys);

	return levelItems.iterator();
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
