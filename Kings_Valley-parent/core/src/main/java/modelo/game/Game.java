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
import modelo.level.door.Door;

public class Game
{
	public static final int ST_GAME_PLAYING=0;
	public static final int ST_GAME_ENTERING=1;
	public static final int ST_GAME_EXITING=2;
	
	
    private static Game instance = new Game();
    private Controls controles = new Controls();
    private HashMap<Integer, TiledMap> maps = new HashMap<Integer, TiledMap>();
    private HashMap<Integer, Boolean> completedLevels = new HashMap<Integer, Boolean>();
    private boolean paused = false;

    private Level level = null;

    protected int idCurrentLevel = 1;
    private int dificult = 0;

    private float delta = 0;
    private IGrafica interfaz = null;
    private LevelReader levelReader;
    protected GameState stateGame;
    protected int state;
    private float timeToTransicion=2f;

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

	}
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

	this.level = this.levelReader.getLevel(idCurrentLevel, this.maps.get(this.idCurrentLevel), dificult,
		this.completedLevels.get(this.idCurrentLevel), interfaz);
	this.stateGame = new GameStateEntering();
	this.interfaz.reset();

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

    public void nextLevel()
    {
	this.idCurrentLevel++;
	this.start();
    }

    public void priorLevel()
    {
	// TODO Auto-generated method stub

    }

    protected void resetDelta() 
    {
    	this.delta=0;
    }

	public float getTimeToTransicion()
	{
		return timeToTransicion;
	}

	public void setTimeToTransicion(float timeToTransicion)
	{
		this.timeToTransicion = timeToTransicion;
	}

	protected HashMap<Integer, Boolean> getCompletedLevels()
	{
		return completedLevels;
	}

	
	protected void goToLevel(Door door)
    {
	this.completedLevels.put(idCurrentLevel, true);

	if (door.getLevelConnected() == Door.TO_NEXT || door.getLevelConnected() == Door.UNIQUE)
	    this.idCurrentLevel++;
	else if (door.getLevelConnected() == Door.TO_PREVIUS)
	    this.idCurrentLevel--;
	else
	    this.idCurrentLevel = door.getLevelConnected();

	this.level = this.levelReader.getLevel(idCurrentLevel, this.maps.get(this.idCurrentLevel), dificult,
		this.completedLevels.get(this.idCurrentLevel), door, interfaz);
	

    }

	public int getState()
	{
		return state;
	}
	
	
	

}
