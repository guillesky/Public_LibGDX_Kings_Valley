package modelo;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector2;

import util.Config;
import util.Constantes;

public class Player extends GameCharacter
{
    public static final int ST_GIRATORY_RIGHT = 20;
    public static final int ST_GIRATORY_LEFT = 21;
    public static final int ST_PICKING = 22;

    private GiratoryMechanism passingGiratory = null;
    private int item = Constantes.It_none;
    private ArrayList<PairInt> coordToPick = new ArrayList<PairInt>();
    private float timePicking = 0;

    public Player(LevelItem door, Pyramid pyramid)
    {
	super(Constantes.PLAYER, door.getX(), door.getY(), Config.getInstance().getPlayerSpeedWalk(),
		Config.getInstance().getPlayerSpeedWalkStairs(), pyramid);

    }

    @Override
    public void move(Vector2 v, boolean b, float deltaTime)
    {
	if (this.coordToPick.isEmpty())
	{
	    if (this.passingGiratory != null)
	    {
		float direction;
		if (this.passingGiratory.isRight())
		    direction = 1;
		else
		    direction = -1;
		this.motionVector.x = direction * this.speedWalkStairs * 0.5f;
		Vector2 escalado = this.motionVector.cpy().scl(deltaTime);
		this.x += escalado.x;

		if (!this.isColision(this.passingGiratory.getLevelItem()))
		{
		    this.passingGiratory = null;
		    this.state = GameCharacter.ST_IDDLE;

		}
	    }

	    else
	    {
		super.move(v, b, deltaTime);

		LevelItem joya = this.checkItemFeetColision(this.pyramid.getJewels());
		if (joya != null)
		{
		    this.pyramid.removeJewel(joya);

		}

		if (this.item == Constantes.It_none)
		{
		    LevelItem picker = this.checkRectangleColision(this.pyramid.getPickers());
		    if (picker != null)
		    {
			this.item = Constantes.It_picker;
			this.pyramid.removePicker(picker);

		    }

		}

		LevelItem activator = this.checkRectangleColision(this.pyramid.getActivators());
		if (activator != null)
		    this.pyramid.activateWall(activator);
		this.checkGiratory(v);
	    }
	} else
	{
	    PairInt pi = this.coordToPick.get(0);
	    if (this.timePicking == 0)// Recien comienza a picar
		this.pyramid.addGraphicElement(new DrawableElement(Constantes.DRAWABLE_PICKING_CELL, pi));

	    this.timePicking += deltaTime;
	    this.animationDelta += deltaTime;

	    if (this.timePicking >= 1.f) // termino de picar una celda
	    {

		TiledMapTileLayer layer = (TiledMapTileLayer) this.pyramid.getMap().getLayers().get("front");
		layer.setCell(pi.getX(), pi.getY(), null);
		this.coordToPick.remove(0);
		this.pyramid.removeGraphicElement(new DrawableElement(Constantes.DRAWABLE_PICKING_CELL, pi));
		if (this.coordToPick.isEmpty())
		    this.state = Player.ST_IDDLE;
		this.timePicking = 0;
	    }
	}
    }

    public int getItem()
    {
	return item;
    }

    @Override
    protected void doAction()
    {
	if (this.item == Constantes.It_none)
	    this.doJump();
	else if (this.item == Constantes.It_picker)
	    this.doPicker();
	else if (this.item == Constantes.It_dagger)
	    this.throwDagger();

    }

    private void throwDagger()
    {
	// TODO Auto-generated method stub
	this.item = Constantes.It_none;
    }

    private void doPicker()
    {
	// TODO Auto-generated method stub
	float px, py;

	if (this.isLocked())
	{
	    if (this.isLookRight())
	    {
		px = this.x + Config.getInstance().getLevelTileWidthUnits();
	    } else
	    {
		px = this.x - Config.getInstance().getLevelTileWidthUnits();
	    }
	    py = this.y + Config.getInstance().getLevelTileHeightUnits();
	    if (this.pyramid.getCell(px, py) != null)
	    {
		int tileY = (int) (py / Config.getInstance().getLevelTileHeightUnits());
		int tileX = (int) (px / Config.getInstance().getLevelTileWidthUnits());
		if (this.picking(tileX, tileY, 2, true))
		    this.item = Constantes.It_none;

	    } else
	    {
		int tileY = (int) (this.y / Config.getInstance().getLevelTileHeightUnits());
		int tileX = (int) (px / Config.getInstance().getLevelTileWidthUnits());
		if (this.picking(tileX, tileY, 1, true))
		    this.item = Constantes.It_none;
	    }
	} else

	{

	    if (this.isLookRight())
	    {
		px = this.x + Config.getInstance().getLevelTileWidthUnits();
		py = this.y - Config.getInstance().getLevelTileHeightUnits();

	    } else
	    {
		px = this.x + this.width - Config.getInstance().getLevelTileWidthUnits();
		py = this.y - Config.getInstance().getLevelTileHeightUnits();

	    }

	    int tileY = (int) (py / Config.getInstance().getLevelTileHeightUnits());
	    int tileX = (int) (px / Config.getInstance().getLevelTileWidthUnits());

	    if (this.picking(tileX, tileY, 2, false))
		this.item = Constantes.It_none;
	}
    }

    

    public boolean picking(int x, int y, int cont, boolean locked)
    {
	boolean respuesta = false;

	TiledMapTileLayer layer = (TiledMapTileLayer) this.pyramid.getMap().getLayers().get("front");
	Cell cell = layer.getCell(x, y);
	Cell celdaArriba = layer.getCell(x, y + 1);

	if (x != 0 && x != this.pyramid.getMapWidthInTiles() - 1 && y != 0 && this.pyramid.isPickable(cell)
		&& cell != null && (celdaArriba == null || locked))
	{
	    PairInt pairInt = new PairInt(x, y);
	    this.coordToPick.add(pairInt);
	    this.state = Player.ST_PICKING;
	    respuesta = true;
	    if (cont == 2)
		this.picking(x, y - 1, 1, true);
	}
	return respuesta;
    }

    public PairInt getCoordPicking()
    {
	PairInt respuesta = null;
	if (!this.coordToPick.isEmpty())
	    respuesta = this.coordToPick.get(0);
	return respuesta;
    }

    @Override
    protected boolean canPassGiratoryMechanism(GiratoryMechanism giratoryMechanism)
    {

	return (this.state == GameCharacter.ST_WALK_RIGHT && giratoryMechanism.isRight())
		|| (this.state == GameCharacter.ST_WALK_LEFT && !giratoryMechanism.isRight());
    }

    @Override
    protected void passGiratoryMechanism(GiratoryMechanism giratoryMechanism)
    {
	this.passingGiratory = giratoryMechanism;
	giratoryMechanism.activate();

    }

}
