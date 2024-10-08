package modelo;

import com.badlogic.gdx.math.Rectangle;

import util.Constantes;

public class LevelItem extends Rectangle implements IGraphicRenderer
{
    private int type;
    private int p0;
    private int p1;
    private IGraphicRenderer graphicRenderer;

    public LevelItem(int type, float x, float y, int p0, int p1, float width, float height)
    {
	super(x, y, width, height);
	this.type = type;
	this.p0 = p0;
	this.p1 = p1;
    }

    public int getType()
    {
	return type;
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

    public boolean isColision(Rectangle another)
    {
	float overlapX = Math.min(x + width, another.x + another.width) - Math.max(x, another.x);
	float overlapY = Math.min(y + height, another.y + another.height) - Math.max(y, another.y);
	return (overlapX > 0 && overlapY > 0);
    }
}
