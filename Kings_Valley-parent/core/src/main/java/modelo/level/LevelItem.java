package modelo.level;

import java.util.Objects;

import com.badlogic.gdx.math.Rectangle;

import modelo.IGraphicRenderer;
import util.Config;
import util.Constantes;

public class LevelItem extends Rectangle implements IGraphicRenderer
{
    private static int count = 0;
    private int id;
    private int type;
    private int p0;
    private IGraphicRenderer graphicRenderer;

    public LevelItem(int type, float x, float y, int p0, float width, float height)
    {
	super(x, y, width, height);
	this.type = type;
	this.p0 = p0;
	count++;
	this.id = count;
    }

    public int getType()
    {
	return type;
    }

    public int getP0()
    {
	return p0;
    }

    @Override
    public String toString()
    {
	return "LevelItem [id= "+this.id+" type=" + Constantes.identificacion.get(type) + ", x=" + x + ", y=" + y + ", p0=" + p0 + "]";
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
	return LevelItem.rectangleColision(this, another);
    }

    protected static boolean rectangleColision(Rectangle rectangleA, Rectangle rectangleB)
    {
	boolean respuesta = false;
	if (rectangleA != null && rectangleB != null)
	{
	    float overlapX = Math.min(rectangleA.x + rectangleA.width, rectangleB.x + rectangleB.width)
		    - Math.max(rectangleA.x, rectangleB.x);
	    float overlapY = Math.min(rectangleA.y + rectangleA.height, rectangleB.y + rectangleB.height)
		    - Math.max(rectangleA.y, rectangleB.y);
	    respuesta = (overlapX > 0 && overlapY > 0);
	}
	return respuesta;
    }

    public int getColPosition()
    {
	return (int) (this.x / Config.getInstance().getLevelTileWidthUnits());
    }

    public int getRowPosition()
    {
	return (int) (this.y / Config.getInstance().getLevelTileHeightUnits());
    }

    @Override
    public int hashCode()
    {
	final int prime = 31;
	int result = super.hashCode();
	result = prime * result + Objects.hash(id);
	return result;
    }

    @Override
    public boolean equals(Object obj)
    {
	if (this == obj)
	    return true;
	if (!super.equals(obj))
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	LevelItem other = (LevelItem) obj;
	return id == other.id;
    }
    
    

}
