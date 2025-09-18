package modelo.level.dagger;

import java.util.ArrayList;

import modelo.gameCharacters.mummys.Mummy;
import modelo.level.Pyramid;
import util.Config;

public abstract class DaggerState
{
	public static final int ST_STUCKED = 0;
	public static final int ST_THROWING_HORIZONTAL = 1;
	public static final int ST_FALLING = 2;
	public static final int ST_THROWING_UP = 3;
	public static final int ST_BOUNCING = 4;
	public static final int ST_PICKUPED = 5;
	
	protected Dagger dagger;
	private int state;
	protected float delta;
	
	public DaggerState(Dagger dagger, int state)
	{
		this.dagger = dagger;
		this.state = state;
		this.delta=0;
	}

	public abstract void updateDagger(float deltaTime, Pyramid pyramid, ArrayList<Mummy> mummys);

	private float roundCoord(float value, float factor)
	{
		float r;
		float i = (int) ((value + factor / 2) / factor);
		r = i * factor;
		return r;
	}

	protected void roundX()
	{

		dagger.x = this.roundCoord(dagger.x, Config.getInstance().getLevelTileWidthUnits());

	}

	protected void roundY()
	{

		dagger.y = this.roundCoord(dagger.y, Config.getInstance().getLevelTileHeightUnits());

	}

	public int getState()
	{
		return state;
	}
	
	
	public void incX(float delta)
	{
		if (this.dagger.isRight())
			this.dagger.x += delta;
		else
			this.dagger.x -= delta;

	}

	public void incY(float delta)
	{
		this.dagger.y += delta;

	}
	public void incDelta(float inc)
	{
		this.delta += inc;
	}

	protected abstract void throwHorizontal();

	protected abstract void hasPickuped();

	protected abstract void throwVertical();

	


}
