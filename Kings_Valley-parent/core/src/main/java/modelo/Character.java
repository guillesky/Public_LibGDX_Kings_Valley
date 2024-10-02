package modelo;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

import util.Constantes;

public class Character extends LevelItem
{

    protected static final int st_init = 0; // Inicializando
    protected static final int st_walk = 1; // Andando
    protected static final int st_onstairs = 2; // En una escalera
    protected static final int st_falling = 3; // Cayendo
    protected static final int st_jump_left = 4; // Saltando izquierda
    protected static final int st_jump_top = 5; // Saltando arriba
    protected static final int st_jump_right = 6; // Saltando derecha
    protected static final int st_dying = 7; // Muriendo

    protected int state = Character.st_init;
    protected Vector2 inputVector = new Vector2();
    protected Vector2 motionVector = new Vector2();
    protected int speedFall = -600;
    protected int speedWalk = 60;
    protected int speedWalkStairs = 60;
    protected int speedJump = 160;
    protected int stairInit = Constantes.It_none;
    protected TiledMapTileLayer layer;
    protected float speedY = 0;

    public Character(int type, float x, float y, int p0, int p1, float width, float height, TiledMapTileLayer layer)
    {
	super(type, x, y, p0, p1, width, height);
	this.layer = layer;
	// TODO Auto-generated constructor stub
    }

    public void move(Vector2 v, boolean b, float deltaTime)
    {
	this.motionVector.x=v.x * this.speedWalk;
	 
	if (!this.isFloorDown())
	{
	    this.state=Character.st_falling;
	    this.motionVector.y += this.speedFall * deltaTime;
	    if (this.motionVector.y < this.speedFall)
		this.motionVector.y = speedFall;
	}

	else
	{
	    this.state=Character.st_walk;
	    this.motionVector.y = 0;
	    if (b) this.motionVector.y=this.speedJump;
	}
	
	Vector2 escalado = this.motionVector.cpy().scl(deltaTime);
	this.x+=escalado.x;
	this.y+=escalado.y;
	
    }

    public int getState()
    {
	return state;
    }

    public void setState(int state)
    {
	this.state = state;
    }

    public boolean isFloorDown()
    {
	int x1 = (int) ((this.getX() + this.getWidth() / 2) / 10);
	int x2 = (int) ((this.getX() - this.getWidth() / 2) / 10);
	int y = (int) ((this.getY() - 1) / 10);
	return (layer.getCell(x1, y) != null || layer.getCell(x2, y) != null);
    }
}
