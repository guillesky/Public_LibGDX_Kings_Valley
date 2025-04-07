package modelo.level;

public class MummyData
{
private float x;
private float y;
private int type;



public MummyData(float x, float y, int type)
{
    this.x = x;
    this.y = y;
    this.type = type;
}
protected float getX()
{
    return x;
}
protected float getY()
{
    return y;
}
protected int getType()
{
    return type;
}


}
