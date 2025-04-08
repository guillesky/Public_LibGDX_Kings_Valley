package modelo.level.dagger;

import java.util.ArrayList;

import modelo.gameCharacters.mummys.Mummy;
import modelo.level.LevelObject;
import modelo.level.Pyramid;

public class Dagger extends LevelObject
{
	public static final int ST_STUCKED = 0;
	public static final int ST_THROWING_HORIZONTAL = 1;
	public static final int ST_FALLING = 2;
	public static final int ST_THROWING_UP = 3;
	public static final int ST_BOUNCING = 4;
	public static final int ST_PICKUPED = 5;
	private int state;
	private DaggerState daggerState = null;
	private float delta;
	private boolean isRight;

	public Dagger(int type, float x, float y, int p0, float width, float height)
	{
		super(type, x, y, p0, width, height);
		this.daggerState = new DaggerStateStucked(this);
		this.delta = 0;
	}

	public void incX(float delta)
	{
		if (this.isRight)
			this.x += delta;
		else
			this.x -= delta;

	}

	public void incY(float delta)
	{
		this.y += delta;

	}

	public int getState()
	{
		return state;
	}

	public boolean isRight()
	{
		return isRight;
	}

	public void incDelta(float inc)
	{
		this.delta += inc;
	}

	public void resetDelta()
	{
		this.delta = 0;
	}

	public void throwHorizontal(boolean isRight)
	{
		this.isRight = isRight;

		this.daggerState = new DaggerStateThrowingHorizontal(this);
	}

	public void updateDagger(float deltaTime, Pyramid pyramid, ArrayList<Mummy> mummys)
	{
		
			this.daggerState.updateDagger(deltaTime, pyramid, mummys);
		
	}

	protected void setState(int state)
	{
		this.state = state;
	}

	protected void setDaggerState(DaggerState daggerState)
	{
		this.daggerState = daggerState;
	}

	protected float getDelta()
	{
		return delta;
	}

	public void hasPickuped()
	{
		this.daggerState=new DaggerStatePickuped(this);
		
	}

	public void throwVertical(boolean isRight)
	{
		this.isRight = isRight;

		this.daggerState = new DaggerStateThrowingVertical(this);
	}

	

}
