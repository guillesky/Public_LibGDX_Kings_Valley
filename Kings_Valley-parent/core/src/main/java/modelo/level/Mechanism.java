package modelo.level;

public abstract class Mechanism
{
	protected float time = 0;
	protected float timeToEnd ;
	protected boolean active = true;

	public abstract void update(float deltaTime);

	
	
	
	public Mechanism(float timeToEnd)
	{
	  
	    this.timeToEnd = timeToEnd;
	}




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
