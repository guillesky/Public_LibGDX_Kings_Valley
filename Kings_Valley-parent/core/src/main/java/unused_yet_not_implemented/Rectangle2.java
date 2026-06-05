package unused_yet_not_implemented;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;

public class Rectangle2 extends Rectangle
{
    private static final long serialVersionUID = 1L;
    public Color color;

    public Rectangle2(float x, float y, float width, float height,Color color)
    {
	super(x, y, width, height);
	this.color=color;
	
    }

}
