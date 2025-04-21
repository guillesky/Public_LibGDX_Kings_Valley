package util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;

public class Rectangle2 extends Rectangle
{
    public Color color;

    public Rectangle2(float x, float y, float width, float height,Color color)
    {
	super(x, y, width, height);
	this.color=color;
	
    }

}
