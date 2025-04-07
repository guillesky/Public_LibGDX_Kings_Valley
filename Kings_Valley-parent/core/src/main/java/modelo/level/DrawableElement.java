package modelo.level;

public class DrawableElement
{
private int type;
private Object drawable;
public int getType()
{
	return type;
}
public Object getDrawable()
{
	return drawable;
}
public DrawableElement(int type, Object drawable)
{
	super();
	this.type = type;
	this.drawable = drawable;
}


}
