package modelo;

import com.badlogic.gdx.math.Vector2;

public class Controles
{
	private boolean actionKey;
	private Vector2 nuevoRumbo;
	private boolean shotEnabled = true;
	private boolean nextEnabled = true;
	private boolean nextKey = false;

	public Vector2 getNuevoRumbo()
	{
		return nuevoRumbo;
	}

	public void setNuevoRumbo(Vector2 nuevoRumbo)
	{
		this.nuevoRumbo = nuevoRumbo;
	}

	public boolean getShot()
	{
		boolean r = actionKey;
		this.actionKey = false;
		return r;
	}

	public void shot()
	{
		actionKey = true;
		this.shotEnabled = false;
	}

	public boolean isShotEnabled()
	{
		return shotEnabled;
	}

	public void enableShotEnabled()
	{
		this.shotEnabled = true;
	}

	public void next()
	{
		nextKey = true;
		this.nextEnabled = false;
	}

	public void enableNexttEnabled()
	{
		this.nextEnabled = true;
	}

	public boolean isNextKey()
	{
		boolean r = nextKey;
		this.nextKey = false;
		return r;

	}

	public boolean isNextEnabled()
	{
		return nextEnabled;
	}

}
