package modelo;

import com.badlogic.gdx.math.Vector2;

import util.Constantes;

public class Character extends LevelItem
{
    public Character(int type, float x, float y, int p0, int p1,float width,float height)
    {
	super(type, x, y, p0, p1, width, height);
	// TODO Auto-generated constructor stub
    }

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
    protected Vector2 snapVector = new Vector2();;
    protected Vector2 motionVector = new Vector2();
    protected int speedFall = 600;
    protected int speedWalk = 60;
    protected int speedWalkStairs = 60;
    protected int speedJump = 160;
    protected int stairInit = Constantes.It_none;

    public void move(Vector2 v)
    {
	this.x += v.x * this.speedWalk;
	this.y += v.y * this.speedWalk;
	
    }

    public int getState()
    {
        return state;
    }

    public void setState(int state)
    {
        this.state = state;
    }
    

}
