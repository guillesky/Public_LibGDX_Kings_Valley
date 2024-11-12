package modelo;

import com.badlogic.gdx.math.Rectangle;

import util.Constantes;

public class GiratoryDoor extends LevelItem
{

	private boolean right;
	private float delta;
	

	public GiratoryDoor(int type, float x, float y, int p0, float width, float height,boolean right)
	{
		super(type, x, y, p0, width, height);
		this.right=right;	
	}
	
}
