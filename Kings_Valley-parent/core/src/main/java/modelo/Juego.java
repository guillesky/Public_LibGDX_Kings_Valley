package modelo;

import java.util.ArrayList;
import java.util.Iterator;

public class Juego implements IGrafica
{
	private static Juego instance = null;
	private Controles controles = new Controles();
	private IGrafica interfaz = null;
	private ArrayList<Pyramid> pyramids = new ArrayList<Pyramid>();
	private int currentPyramid = 0;

	

	private Juego()
	{
	}

	@Override
	public void addGraphicElement(Object element)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void removeGraphicElement(Object element)
	{
		// TODO Auto-generated method stub

	}

	public static Juego getInstance()
	{
		if (instance == null)
			instance = new Juego();
		return instance;
	}

	public void actualizaframe(float deltaTime)
	{
		// TODO Auto-generated method stub

	}

	public Controles getControles()
	{
		// TODO Auto-generated method stub
		return null;
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
		Iterator<Pyramid> it=this.pyramids.iterator();
		while(it.hasNext())
			it.next().getMap().dispose();
	}
}
