package modelo.game;

import java.util.HashMap;
import java.util.TreeMap;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;

import modelo.IGrafica;
import modelo.KVEventListener;
import modelo.control.Controls;
import modelo.level.Level;
import modelo.level.LevelReader;
import modelo.level.door.Door;
import util.Constantes;
import util.GameConfig;

public class Game implements KVEventListener
{
	public static final int ST_GAME_PLAYING = 0;
	public static final int ST_GAME_ENTERING = 1;
	public static final int ST_GAME_EXITING = 2;
	public static final int ST_GAME_DYING = 3;
	private static Game instance = new Game();
	private Controls controles = new Controls();
	private HashMap<Integer, Boolean> completedLevels = new HashMap<Integer, Boolean>();
	private boolean paused = false;
	private Level level = null;
	protected int idCurrentLevel = 1;
	private int dificultLevel = 0;
	private float delta = 0;
	private IGrafica interfaz = null;
	protected GameState stateGame;
	protected int state;
	private int score = 0;
	protected int lives;
	private float maxDeltaTimeRegistered = 0;
	private GameConfig gameConfig;
	
	
	
	public void setGameConfig(GameConfig gameConfig)
	{
		this.gameConfig = gameConfig;
	}

	public void incScore(int cant)
	{
		this.score += cant;
	}

	public int getLives()
	{
		return lives;
	}

	public int getScore()
	{
		return score;
	}

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
		this.initNewGame();
	}

	private void initNewGame()
	{
		this.resetCompletedLevels();
		this.lives = 3;
	}

	private void resetCompletedLevels()
	{
		for (int i = 1; i <= 15; i++)
		{
			this.completedLevels.put(i, false);
		}
	}

	public static Game getInstance()
	{
		return instance;
	}

	public void updateframe(float deltaTime)
	{
		/*
		 * if (deltaTime > this.maxDeltaTimeRegistered) { this.maxDeltaTimeRegistered =
		 * deltaTime; System.out.println(deltaTime); }
		 */

		if (deltaTime > 0.015f)
			deltaTime = 0.015f;

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

	public void start(Door door)
	{
		// this.countTiles();

		LevelReader levelReader = new LevelReader();
		if (this.level != null)
			this.level.dispose();
		if (Constantes.levelFileName.get(idCurrentLevel) == null)
		{
			this.eventFired(KVEventListener.FINISH_ALL_LEVELS, null);

		}
		this.level = levelReader.getLevel(idCurrentLevel, Constantes.levelFileName.get(idCurrentLevel), dificultLevel,
				this.completedLevels.get(this.idCurrentLevel), door, interfaz);
		this.stateGame = new GameStateEntering();
		this.interfaz.reset();

	}

	/*
	 * private void countTiles() { TreeMap<Integer, Integer> tileCounter = new
	 * TreeMap<Integer, Integer>();
	 * 
	 * Iterator<TiledMap> it = this.maps.values().iterator(); int i = 0; while
	 * (it.hasNext())
	 * 
	 * { i++; TiledMap map = it.next(); TiledMapTileLayer layer1 =
	 * (TiledMapTileLayer) map.getLayers().get("back"); TiledMapTileLayer layer2 =
	 * (TiledMapTileLayer) map.getLayers().get("front"); TiledMapTileLayer layer3 =
	 * (TiledMapTileLayer) map.getLayers().get("stairs");
	 * 
	 * int mapWidthInTiles = map.getProperties().get("width", Integer.class); int
	 * mapHeightInTiles = map.getProperties().get("height", Integer.class);
	 * this.searchInLayer(mapWidthInTiles, mapHeightInTiles, layer1, tileCounter);
	 * this.searchInLayer(mapWidthInTiles, mapHeightInTiles, layer2, tileCounter);
	 * this.searchInLayer(mapWidthInTiles, mapHeightInTiles, layer3, tileCounter);
	 * if (tileCounter.get(33) != tileCounter.get(34)) System.out
	 * .println("ERROR en MAPA: " + i + "     " + tileCounter.get(33) + "    " +
	 * tileCounter.get(34)); } System.out.println(tileCounter);
	 * System.out.println("Total de tiles: " + tileCounter.size()); }
	 */
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

	
	protected void resetDelta()
	{
		this.delta = 0;
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
		this.start(door);

	}

	public int getState()
	{
		return state;
	}

	public void start()
	{
		this.start(null);
	}

	public void dying()
	{
		this.stateGame = new GameStateDying();
		this.level.getPlayer().die();

		this.eventFired(KVEventListener.PLAYER_DIE, null);
	}

	@Override
	public void eventFired(int eventCode, Object param)
	{
		switch (eventCode)
		{
		case KVEventListener.FINISH_ALL_LEVELS:
			this.idCurrentLevel = 1;
			this.dificultLevel++;
			this.resetCompletedLevels();
			break;

		case KVEventListener.MUMMY_KILLED_BY_SWORD:
			this.score += Constantes.MUMMY_KILLED_BY_SWORD_SCORE;
			break;

		case KVEventListener.PICKUP_JEWEL:
			this.score += Constantes.PICKUP_JEWEL_SCORE;
			break;

		case KVEventListener.FINISH_CURRENT_LEVEL:
			this.score += Constantes.FINISH_CURRENT_LEVEL_SCORE;
			break;

		}

	}

	public void showPlayer()
	{
/*
		Player player = this.level.getPlayer();
		this.lolo = new Lolo(player);

		System.out.println("IZQUIERDA: " + lolo.endPlatform(false));

		System.out.println("Derecha: " + lolo.endPlatform(true));

		System.out.println("Arriba: " + lolo.getNearStair(true));

		System.out.println("Abajo: " + lolo.getNearStair(false));
*/
		
		
		
		/*
		 * System.out.println(this.level.getPlayer());/* Iterator it =
		 * this.level.getMummys().iterator(); while (it.hasNext())
		 * System.out.println(it.next().toString());
		 */

	}

	public int getDificultLevel()
	{
		return dificultLevel;
	}

	public void setDificultLevel(int dificultLevel)
	{
		this.dificultLevel = dificultLevel;
	}

	
	
	
	
}
