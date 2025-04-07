package modelo.level;

public class Dagger extends LevelObject
{
    public static final int ST_STUCKED = 0;
    public static final int ST_THROWING_HORIZONTAL = 1;
    public static final int ST_FALLING = 2;
    public static final int ST_THROWING_UP = 3;
    public static final int ST_BOUNCING =4;
    private int state;
    private float delta;
    private boolean isRight;

    public Dagger(int type, float x, float y, int p0, float width, float height)
    {
	super(type, x, y, p0, width, height);
	this.state = Dagger.ST_STUCKED;
	this.delta = 0;
    }

    public void incX(float delta)
    {
	if (this.isRight)
	    this.x += delta;
	else
	    this.x -= delta;

    }

    public int getState()
    {
	return state;
    }

    public boolean isRight()
    {
	return isRight;
    }

   
    public void crash()
    {
	this.state=Dagger.ST_BOUNCING;
    }

    public void incDelta(float inc) 
    {
	this.delta+=inc;
    }
    
    public void resetDelta() 
    {
	this.delta=0;
    }

    public void throwHorizontal(boolean isRight)
    {
        this.isRight = isRight;
        
        this.state=Dagger.ST_THROWING_HORIZONTAL;
    }
    
    
}
