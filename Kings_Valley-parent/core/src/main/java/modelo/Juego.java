package modelo;

import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;

import modelo.gameCharacters.player.Player;
import modelo.level.Level;
import modelo.level.LevelReader;

public class Juego
{
    private static Juego instance = new Juego();
    private Controles controles = new Controles();
    private HashMap<Integer, TiledMap> maps = new HashMap<Integer, TiledMap>();
    private HashMap<Integer, Boolean> completedLevels = new HashMap<Integer, Boolean>();

    private Level level = null;
    private int currentLevel = 1;
    private int dificult = 0;

    private float delta = 0;
    private IGrafica interfaz = null;
    private LevelReader levelReader;

    public IGrafica getInterfaz()
    {
	return interfaz;
    }

    public void setInterfaz(IGrafica interfaz)
    {
	this.interfaz = interfaz;
    }

    private Juego()
    {
	this.levelReader = new LevelReader();

    }

    public static Juego getInstance()
    {
	return instance;
    }

    public void addMap(Integer id, TiledMap map)
    {
	this.maps.put(id, map);
	this.completedLevels.put(id, false);
    }

    public void actualizaframe(float deltaTime)
    {
	this.delta += deltaTime;
	Player player = this.getCurrentLevel().getPlayer();
	player.update(this.controles.getNuevoRumbo(), this.controles.getShot(), deltaTime);
	if (this.controles.isNextKey())
	    this.finishLevel();
	this.getCurrentLevel().updateMechanism(deltaTime);
	this.getCurrentLevel().updateMummys(deltaTime);
	this.getCurrentLevel().updateFlyingDagger(deltaTime);
    }

    private void finishLevel()
    {
	this.level.finishLevel();

    }

    public Controles getControles()
    {
	return controles;
    }

    public void setControles(Controles controles)
    {
	this.controles = controles;
    }

    public Level getCurrentLevel()
    {
	return this.level;
    }

    public void dispose()
    {
	level.getPyramid().getMap().dispose();
    }

    public float getDelta()
    {
	return delta;
    }

    public void start()
    {
	this.countTiles();

	this.level = this.levelReader.getLevel(this.maps.get(this.currentLevel), dificult,
		this.completedLevels.get(this.currentLevel), interfaz);

    }

    private void countTiles()
    {
	TreeMap<Integer, Integer> tileCounter = new TreeMap<Integer, Integer>();

	Iterator<TiledMap> it = this.maps.values().iterator();

	while (it.hasNext())

	{
	    TiledMap map = it.next();
	    TiledMapTileLayer layer1 = (TiledMapTileLayer) map.getLayers().get("back");
	    TiledMapTileLayer layer2 = (TiledMapTileLayer) map.getLayers().get("front");
	    TiledMapTileLayer layer3 = (TiledMapTileLayer) map.getLayers().get("stairs");

	    int mapWidthInTiles = map.getProperties().get("width", Integer.class);
	    int mapHeightInTiles = map.getProperties().get("height", Integer.class);
	    this.searchInLayer(mapWidthInTiles, mapHeightInTiles, layer1, tileCounter);
	    this.searchInLayer(mapWidthInTiles, mapHeightInTiles, layer2, tileCounter);
	    this.searchInLayer(mapWidthInTiles, mapHeightInTiles, layer3, tileCounter);

	}
	System.out.println(tileCounter);
	System.out.println("Total de tiles: "+tileCounter.size());
    }

    private void searchInLayer(int mapWidthInTiles, int mapHeightInTiles, TiledMapTileLayer layer1,
	    TreeMap<Integer, Integer> tileCounter)
    {
	Cell cell;
	for (int i = 0; i < mapHeightInTiles; i++)
	    for (int j = 0; j < mapWidthInTiles; j++)
	    {
		cell = layer1.getCell(i, j);
		if (cell != null)
		{
		    int value = cell.getTile().getId();
		    if (tileCounter.get(value) == null)
		    {
			tileCounter.put(value, 1);
		    } else
		    {
			int count = tileCounter.get(value) + 1;
			tileCounter.put(value, count);
		    }
		}
	    }
    }

}
