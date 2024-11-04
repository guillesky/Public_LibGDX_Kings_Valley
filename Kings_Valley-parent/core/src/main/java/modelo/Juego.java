package modelo;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class Juego
{
	private static Juego instance = new Juego();
	private Controles controles = new Controles();
	private ArrayList<Pyramid> pyramids = new ArrayList<Pyramid>();
	private int currentPyramid = 0;
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
		Player player = this.getCurrentPyramid().getPlayer();
		player.move(this.controles.getNuevoRumbo(), this.controles.getShot(), deltaTime);
		this.getCurrentPyramid().CheckJewelPickup();

	}

	public Controles getControles()
	{
		return controles;
	}

	public void setControles(Controles controles)
	{
		this.controles = controles;
	}

	public void addPyramid(Pyramid pyramid)
	{
		this.pyramids.add(pyramid);
	}

	public Pyramid getCurrentPyramid()
	{
		return this.pyramids.get(currentPyramid);
	}

	public void dispose()
	{
		Iterator<Pyramid> it = this.pyramids.iterator();
		while (it.hasNext())
			it.next().getMap().dispose();
	}

	public float getDelta()
	{
		return delta;
	}
	
}
