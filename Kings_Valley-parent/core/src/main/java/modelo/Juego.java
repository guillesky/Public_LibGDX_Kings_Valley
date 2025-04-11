package modelo;

import java.util.HashMap;

import com.badlogic.gdx.maps.tiled.TiledMap;

import modelo.gameCharacters.player.Player;
import modelo.level.Level;
import modelo.level.LevelReader;

public class Juego
{
	private static Juego instance = new Juego();
	private Controles controles = new Controles();
	private HashMap<Integer,TiledMap> maps = new HashMap<Integer,TiledMap>();
	private HashMap<Integer,Boolean> completedLevels = new HashMap<Integer,Boolean>();
	
	private Level level=null;
	private int currentLevel = 1;
	private int dificult = 0;
	
	private float delta = 0;
	private IGrafica interfaz = null;
	private LevelReader levelReader ;
	
	public IGrafica getInterfaz()
	{
		return interfaz;
	}

	public void setInterfaz(IGrafica interfaz)
	{
		this.interfaz = interfaz;
	}

	private Juego()
	{		this.levelReader= new LevelReader();
		
	}

	public static Juego getInstance()
	{
		return instance;
	}

	public void addMap(Integer id,TiledMap map) 
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
		this.level=this.levelReader.getLevel(this.maps.get(this.currentLevel), dificult,  this.completedLevels.get(this.currentLevel), interfaz);
	}

}
