package modelo.level;

public abstract class Mechanism
{
	protected float time = 0;
	protected boolean active = true;

	public abstract void update(float deltaTime);

	public boolean isActive()
	{
		return active;
	}

	public float getTime()
	{
		return time;
	}

	protected void incTime(float delta) 
	{
		this.time+=delta;
	}
	
	protected void resetTime() 
	{
		this.time=0;
	}
	
}
