package modelo;

import com.badlogic.gdx.math.Vector2;

public class Controles
{
	private boolean[] teclas = new boolean[5];
	private Vector2 nuevoRumbo;
	private boolean shotEnabled=true;

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
	    boolean r=this.teclas[0];
	    this.teclas[0]=false;
		return r;
	}

	public void shot()
	{
		this.teclas[0] = true;
		this.shotEnabled=false;
	}

	public boolean getSpawnKamikaze()
	{
		return this.teclas[1];
	}

	public void setSpawnKamikaze(boolean param)
	{
		this.teclas[1] = param;
	}

	public boolean getSpawnAcechador()
	{
		return this.teclas[2];
	}

	public boolean isShotEnabled()
	{
	    return shotEnabled;
	}

	public void enableShotEnabled()
	{
	    this.shotEnabled = true;
	}

	public void setSpawnAcechador(boolean param)
	{
		this.teclas[2] = param;
	}

	public boolean getSpawnAcorazado()
	{
		return this.teclas[3];
	}

	public void setSpawnAcorazado(boolean param)
	{
		this.teclas[3] = param;
	}

	public void resetAll()
	{
		for (int i = 0; i < this.teclas.length; i++)
			this.teclas[i] = false;
	}

}
