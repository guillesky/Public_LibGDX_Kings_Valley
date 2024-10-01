package modelo;

import util.Constantes;

public class LevelItem implements IGraphicRenderer
{
    private int type;
    protected float x;
    protected float y;
    private int p0;
    private int p1;
    private IGraphicRenderer graphicRenderer;
    private float width;
    private float height;

    public LevelItem(int type, float x, float y, int p0, int p1,float width,float height)
    {
	super();
	this.type = type;
	this.x = x;
	this.y = y;
	this.p0 = p0;
	this.p1 = p1;
	this.width=width;
	this.height=height;
	
    }

    public int getType()
    {
	return type;
    }

    public float getX()
    {
	return x;
    }

    public float getY()
    {
	return y;
    }

    public int getP0()
    {
	return p0;
    }

    public int getP1()
    {
	return p1;
    }

    @Override
    public String toString()
    {
	return "LevelItem [type=" + Constantes.identificacion.get(type) + ", x=" + x + ", y=" + y + ", p0=" + p0
		+ ", p1=" + p1 + "]";
    }

    public IGraphicRenderer getGraphicRenderer()
    {
	return graphicRenderer;
    }

    public void setGraphicRenderer(IGraphicRenderer graphicRenderer)
    {
	this.graphicRenderer = graphicRenderer;
    }

    @Override
    public void updateElement(Object element)
    {
	if (this.graphicRenderer != null)
	    this.graphicRenderer.updateElement(element);

    }

    public float getWidth()
    {
        return width;
    }

    public float getHeight()
    {
        return height;
    }
    
}
