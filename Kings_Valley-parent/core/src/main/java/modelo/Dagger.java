package modelo;

public class Dagger extends LevelItem
{

    public Dagger(int type, float x, float y, int p0, float width, float height)
    {
	super(type, x, y, p0, width, height);
	
    }
    
    public void incX(float value) 
    {
	this.x+=value;
    }

}
