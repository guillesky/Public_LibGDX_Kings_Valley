package modelo.gameCharacters.abstractGameCharacter;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import modelo.level.GiratoryMechanism;
import modelo.level.LevelObject;
import modelo.level.Pyramid;
import modelo.level.Stair;
import util.Config;
import util.Constantes;

@SuppressWarnings("serial")
public abstract class GameCharacter extends LevelObject
{

    public static final int ST_IDDLE = 0; // Inicializando
    public static final int ST_WALKING = 11; // Andando
    public static final int ST_ONSTAIRS = 2; // En una escalera pendiente positiva

    public static final int ST_JUMPING = 5; // Saltando

    public static final int ST_FALLING = 7; // Cayendo

    public static final int ST_DYING = 8; // Muriendo

    protected int state = GameCharacter.ST_IDDLE;

    protected Vector2 motionVector = new Vector2();
    protected float speedFall;
    protected float speedWalk;
    protected float speedWalkStairs;
    protected float speedJump;
    protected float speedY = 0;
    protected Pyramid pyramid;
    protected boolean lookRight = true;
    protected float animationDelta = 0;
    private Rectangle feet;
    protected GameCharacterState gameCharacterState;

    public GameCharacter(int type, float x, float y, float speedWalk, float speedWalkStairs, Pyramid pyramid)
    {
	super(type, x, y, 0, Config.getInstance().getCharacterWidth(), Config.getInstance().getCharacterHeight());
	float feetWidth;
	float feetHeight;

	this.speedFall = Config.getInstance().getCharacterSpeedFall();
	this.speedWalk = speedWalk;
	this.speedWalkStairs = speedWalkStairs;
	this.speedJump = Config.getInstance().getCharacterSpeedJump();

	feetHeight = Config.getInstance().getCharacterFeetHeight();
	feetWidth = Config.getInstance().getCharacterFeetWidth();
	this.pyramid = pyramid;

	float mitad = this.x + this.width / 2;
	mitad -= feetWidth / 2;
	this.feet = new Rectangle(mitad, y, feetWidth, feetHeight);
	this.gameCharacterState = new GameCharacterStateIddle(this);

    }

    protected void move(Vector2 v, boolean b, float deltaTime)
    {
	this.gameCharacterState.move(v, b, deltaTime);

    }

    protected abstract void doAction();

    protected boolean doJump()
    {
	boolean r = false;
	if (this.isFreeUp())
	{
	    this.gameCharacterState = new GameCharacterStateJumping(this);
	    r = true;
	}
	return r;
    }

    public int getState()
    {
	return state;
    }

    public boolean isFeetColision(Rectangle another)
    {
	this.feet.x = this.x + (this.width - Config.getInstance().getCharacterFeetHeight()) / 2;
	this.feet.y = this.y;
	return LevelObject.rectangleColision(this.feet, another);
    }

    public float getAnimationDelta()
    {
	return animationDelta;
    }

    public boolean isLookRight()
    {
	return lookRight;
    }

    @SuppressWarnings(
    { "rawtypes", "unchecked" })
    public LevelObject checkRectangleColision(ArrayList levelObjects)
    {

	Iterator<LevelObject> it = levelObjects.iterator();
	LevelObject respuesta = null;
	LevelObject item = null;
	if (it.hasNext())
	    do
	    {
		item = it.next();
	    } while (it.hasNext() && !this.isColision(item));

	if (this.isColision(item))
	{
	    respuesta = item;
	}

	return respuesta;
    }

    protected boolean checkGiratory()
    {
	boolean r = false;
	LevelObject giratory = this.checkRectangleColision(this.pyramid.getGiratories());
	if (giratory != null)
	{
	    GiratoryMechanism gm = this.pyramid.getGiratoryMechanism(giratory);

	    if (this.canPassGiratoryMechanism(gm))
	    {
		this.passGiratoryMechanism(gm);

	    } else
	    {
		this.blockGiratory(gm);
		r = true;
	    }
	}
	return r;
    }

    public boolean isLocked()
    {

	return this.isLockedRight() && this.isLockedLeft();
    }

    protected boolean isLockedLeft()
    {
	return this.pyramid.getCell(this.x - Config.getInstance().getLevelTileWidthUnits(),
		this.y + Config.getInstance().getLevelTileHeightUnits()) != null
		|| this.pyramid.getCell(this.x - Config.getInstance().getLevelTileWidthUnits(), this.y) != null;

    }

    protected boolean isLockedRight()
    {
	return this.pyramid.getCell(this.x + Config.getInstance().getLevelTileWidthUnits(),
		this.y + Config.getInstance().getLevelTileHeightUnits()) != null
		|| this.pyramid.getCell(this.x + Config.getInstance().getLevelTileWidthUnits(), this.y) != null;

    }

    protected boolean isFreeUp()
    {
	return this.pyramid.getCell(this.x, this.y, 0, 2) == null
		&& this.pyramid.getCell(this.x + this.width, this.y, 0, 2) == null;

    }

    protected abstract boolean canPassGiratoryMechanism(GiratoryMechanism giratoryMechanism);

    protected abstract void passGiratoryMechanism(GiratoryMechanism giratoryMechanism);

    protected void blockGiratory(GiratoryMechanism gm)
    {
	LevelObject g = gm.getLevelObject();
	float lado = g.x - this.x;

	this.motionVector.x = 0;
	if (lado < 0)
	{
	    this.x = g.x + g.width;
	} else

	{
	    this.x = g.x - this.width;
	}

    }

    protected void setState(int state)
    {
	this.state = state;
    }

    protected void resetAnimationDelta()
    {
	this.animationDelta = 0;
    }

    protected void incAnimationDelta(float delta)
    {
	this.animationDelta += delta;
    }

    public Pyramid getPyramid()
    {
	return pyramid;
    }

    public boolean isInStair()
    {
	return this.gameCharacterState.getStair() != null;
    }

    public Stair getStair()
    {
	return this.gameCharacterState.getStair();
    }

    protected Stair checkStairsFeetColision(boolean positiveStairs, boolean isUpping)
    {

	Iterator<Stair> it;
	if (positiveStairs)
	    it = this.getPyramid().getPositiveStairs().iterator();
	else
	    it = this.getPyramid().getNegativeStairs().iterator();
	Stair respuesta = null;
	Stair stair = null;
	LevelObject item = null;
	if (it.hasNext())
	    do
	    {
		stair = it.next();
		if (isUpping)
		    item = stair.getDownStair();
		else
		    item = stair.getUpStair();
	    } while (it.hasNext() && !this.isFeetColision(item));

	if (this.isFeetColision(item))
	{
	    respuesta = stair;
	}

	return respuesta;
    }

    protected void enterStair(Stair stair)
    {
	this.gameCharacterState = new GameCharacterStateOnStair(this, stair);
    }

}
