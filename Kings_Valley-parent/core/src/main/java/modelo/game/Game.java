package modelo.game;

import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;

import modelo.IGrafica;
import modelo.control.Controls;
import modelo.level.Level;
import modelo.level.LevelReader;

public class Game
{
    private static Game instance = new Game();
    private Controls controles = new Controls();
    private HashMap<Integer, TiledMap> maps = new HashMap<Integer, TiledMap>();
    private HashMap<Integer, Boolean> completedLevels = new HashMap<Integer, Boolean>();
    private boolean paused = false;

    private Level level = null;
    private boolean readyToExit = false;
    private int currentLevel = 11;
    private int dificult = 0;

    private float delta = 0;
    private IGrafica interfaz = null;
    private LevelReader levelReader;
    protected GameState stateGame;

    public IGrafica getInterfaz()
    {
	return interfaz;
    }

    public void setInterfaz(IGrafica interfaz)
    {
	this.interfaz = interfaz;
    }

    private Game()
    {
	this.levelReader = new LevelReader();

    }

    public static Game getInstance()
    {
	return instance;
    }

    public void addMap(Integer id, TiledMap map)
    {
	this.maps.put(id, map);
	this.completedLevels.put(id, false);
    }

    public void updateframe(float deltaTime)
    {
	if (controles.getShot(Input.Keys.P))
	{
	    this.paused = !this.paused;
	}

	if (!this.paused)
	{
	    this.stateGame.updateframe(deltaTime);
	    if (this.level.isReadyToExit() && !this.readyToExit)
	    {
		this.level.removeGiratories();
		this.readyToExit = true;
	    }
	}
    }

    protected void finishLevel()
    {
	this.level.removeGiratories();
    }

    public Controls getControles()
    {
	return controles;
    }

    public void setControles(Controls controles)
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
	// this.countTiles();

	this.level = this.levelReader.getLevel(this.maps.get(this.currentLevel), dificult,
		this.completedLevels.get(this.currentLevel), interfaz);
	this.stateGame = new GameStateEntering();

    }

    private void countTiles()
    {
	TreeMap<Integer, Integer> tileCounter = new TreeMap<Integer, Integer>();

	Iterator<TiledMap> it = this.maps.values().iterator();
	int i = 0;
	while (it.hasNext())

	{
	    i++;
	    TiledMap map = it.next();
	    TiledMapTileLayer layer1 = (TiledMapTileLayer) map.getLayers().get("back");
	    TiledMapTileLayer layer2 = (TiledMapTileLayer) map.getLayers().get("front");
	    TiledMapTileLayer layer3 = (TiledMapTileLayer) map.getLayers().get("stairs");

	    int mapWidthInTiles = map.getProperties().get("width", Integer.class);
	    int mapHeightInTiles = map.getProperties().get("height", Integer.class);
	    this.searchInLayer(mapWidthInTiles, mapHeightInTiles, layer1, tileCounter);
	    this.searchInLayer(mapWidthInTiles, mapHeightInTiles, layer2, tileCounter);
	    this.searchInLayer(mapWidthInTiles, mapHeightInTiles, layer3, tileCounter);
	    if (tileCounter.get(33) != tileCounter.get(34))
		System.out
			.println("ERROR en MAPA: " + i + "     " + tileCounter.get(33) + "    " + tileCounter.get(34));
	}
	System.out.println(tileCounter);
	System.out.println("Total de tiles: " + tileCounter.size());
    }

    private void searchInLayer(int mapWidthInTiles, int mapHeightInTiles, TiledMapTileLayer layer,
	    TreeMap<Integer, Integer> tileCounter)
    {
	Cell cell;
	for (int i = 0; i < mapHeightInTiles; i++)
	    for (int j = 0; j < mapWidthInTiles; j++)
	    {
		cell = layer.getCell(j, i);
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

    public boolean isPaused()
    {
	return paused;
    }

    protected void incDelta(float delta)
    {
	this.delta += delta;
    }



}
