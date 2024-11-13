package modelo;

import util.Constantes;

public class LevelItem
{
	private int type;
	private int x;
	private int y;
	private int p0;
	private int p1;
	
	
	
	
	public LevelItem(int type, int x, int y, int p0, int p1)
	{
		super();
		this.type = type;
		this.x = x;
		this.y = y;
		this.p0 = p0;
		this.p1 = p1;
	}
	public int getType()
	{
		return type;
	}
	public int getX()
	{
		return x;
	}
	public int getY()
	{
		return y;
	}
	public int getP0()
	{
		return p0;
	}
	public int getP1()
	{
		return p1;
	}
	
	
	
	public void setX(int x)
	{
	    this.x = x;
	}
	public void setY(int y)
	{
	    this.y = y;
	}
	@Override
	public String toString()
	{
		return "LevelItem [type=" + Constantes.identificacion.get(type) + ", x=" + x + ", y=" + y + ", p0=" + p0 + ", p1=" + p1 + "]";
	}
	public void setP0(int p0)
	{
	    this.p0 = p0;
	}
	public void setP1(int p1)
	{
	    this.p1 = p1;
	}
	
	

}
