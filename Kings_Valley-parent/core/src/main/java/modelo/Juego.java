package modelo;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import modelo.gameCharacters.player.Player;
import modelo.level.Level;

public class Juego
{
	private static Juego instance = new Juego();
	private Controles controles = new Controles();
	private ArrayList<Level> levels = new ArrayList<Level>();
	private int currentLevel =11;
	private float delta = 0;

	private Juego()
	{
	}

	public static Juego getInstance()
	{
		return instance;
	}

	public void actualizaframe(float deltaTime)
	{
		this.delta += deltaTime;
		Player player = this.getCurrentLevel().getPlayer();
		player.update(this.controles.getNuevoRumbo(), this.controles.getShot(), deltaTime);
		
		this.getCurrentLevel().updateMechanism(deltaTime);
		this.getCurrentLevel().updateMummys(deltaTime);
		this.getCurrentLevel().updateFlyingDagger(deltaTime);
	}

	public Controles getControles()
	{
		return controles;
	}

	public void setControles(Controles controles)
	{
		this.controles = controles;
	}

	public void addLevel(Level level)
	{
		this.levels.add(level);
	}

	public Level getCurrentLevel()
	{
		return this.levels.get(currentLevel);
	}

	public void dispose()
	{
		Iterator<Level> it = this.levels.iterator();
		while (it.hasNext())
			it.next().getPyramid().getMap().dispose();
	}

	public float getDelta()
	{
		return delta;
	}

}
