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
    protected int stairInit = Constantes.It_none;
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
	deltaTime *= Config.getInstance().getSpeedGame();
	this.gameCharacterState.moveFirstStep(v, b, deltaTime);
	Vector2 escalado = this.motionVector.cpy().scl(deltaTime);
	this.gameCharacterState.moveSecondStep(escalado);
	this.x += escalado.x;
	this.y += escalado.y;
	this.checkOutLevel();

    }

    protected abstract void doAction();

    protected void doJump()
    {
	if (this.isFreeUp())
	    this.gameCharacterState = new GameCharacterStateJumping(this);
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

    private void checkOutLevel()
    {
	float epsilon = .1f * Config.getInstance().getLevelTileWidthUnits();

	if (this.x < Config.getInstance().getLevelTileWidthUnits())
	{
	    this.x = Config.getInstance().getLevelTileWidthUnits();
	} else

	{
	    if (this.x + epsilon + this.width > (this.pyramid.getMapWidthInTiles() - 1)
		    * Config.getInstance().getLevelTileWidthUnits())

		this.x = (this.pyramid.getMapWidthInTiles() - 1) * Config.getInstance().getLevelTileWidthUnits()
			- (this.width + epsilon);
	}

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

    protected boolean checkGiratory(Vector2 v)
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

    protected boolean isLocked()
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

    protected Pyramid getPyramid()
    {
	return pyramid;
    }

    public boolean isInStair()
    {
	return this.gameCharacterState.isInStair();

    }

}
