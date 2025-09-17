package modelo.level;

/**
 * @author Guillermo Lazzurri
 * Clase que representa 
 * 
 */
public class PairInt
{
private int x;
private int y;




public PairInt(int x, int y)
{
    super();
    this.x = x;
    this.y = y;
}

public int getX()
{
    return x;
}
public int getY()
{
    return y;
}

@Override
public String toString()
{
    StringBuilder builder = new StringBuilder();
    builder.append("[x=");
    builder.append(x);
    builder.append(", y=");
    builder.append(y);
    builder.append("]");
    return builder.toString();
}


}
