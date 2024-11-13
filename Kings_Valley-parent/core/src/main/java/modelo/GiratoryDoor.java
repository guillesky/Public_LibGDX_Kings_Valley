package modelo;

import com.badlogic.gdx.math.Rectangle;

import util.Constantes;

public class GiratoryDoor extends LevelItem
{

	private boolean right;
	private float delta;
	

	public GiratoryDoor(int type, float x, float y, int p0, float width, float height)
	{
		super(type, x, y, p0, width, height);
		this.right=(p0==0);	
	}


	public GiratoryDoor(LevelItem levelItem)
	{
		this(levelItem.getType(), levelItem.getX(), levelItem.getY(),levelItem.getP0(), levelItem.width, levelItem.height);
	}


	public boolean isRight()
	{
		return right;
	}


	public float getDelta()
	{
		return delta;
	}
	
	
	
}
