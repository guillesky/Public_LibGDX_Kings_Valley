package modelo.level;

public class Stair
{
    private LevelObject downStair;
    private LevelObject upStair;

    public Stair(LevelObject downStair, LevelObject upStair)
    {
	this.downStair = downStair;
	this.upStair = upStair;
    }

    public LevelObject getDownStair()
    {
	return downStair;
    }

    public LevelObject getUpStair()
    {
	return upStair;
    }

    public boolean isPositive()
    {
	return this.downStair.x < this.upStair.x;
    }
    
    public float directionUp() 
    {
    	return Math.signum(this.upStair.x-this.downStair.x);
    }

    public float directionDown() 
    {
    	return Math.signum(this.downStair.x-this.upStair.x);
    }

    
    @Override
    public String toString()
    {
	StringBuilder builder = new StringBuilder();
	builder.append("Stair [downStair=");
	builder.append(downStair);
	builder.append(", upStair=");
	builder.append(upStair);
	builder.append(", isPositive()=");
	builder.append(isPositive());
	builder.append("]");
	return builder.toString();
    }
    
    

}
