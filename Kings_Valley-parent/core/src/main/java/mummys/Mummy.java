package mummys;

import java.util.Random;

import com.badlogic.gdx.math.Vector2;

import modelo.GameCharacter;
import modelo.GiratoryMechanism;
import modelo.Player;
import modelo.Pyramid;
import util.Config;

public abstract class Mummy extends GameCharacter
{
    public static final int WHITE_MUMMY = 0;
    public static final int BLUE_MUMMY = 1;
    public static final int YELLOW_MUMMY = 2;
    public static final int ORANGE_MUMMY = 3;
    public static final int RED_MUMMY = 4;

    protected static final int ST_WAITING = 100;
    protected static final int ST_LIMBUS = 101;

    protected static final int ST_APPEARING = 102;
    protected static final int ST_DISAPPEARING = 103;
    protected static final int ST_TELEPORTING = 104;
    protected static final Random random = new Random();

    protected float decisionFactor;
    private float timer = 0;
    private float minTimeToDecide;
    private float maxTimeToDecide;
    private float timeToDecide = 0;
    private Vector2 direction = new Vector2();
    private boolean colisionEnabled = false;

    public Mummy(int type, float x, float y, float speedWalk, float speedWalkStairs, float decisionFactor,
	    float minTimeToDecide, float maxTimeToDecide, Pyramid pyramid)
    {
	super(type, x, y, speedWalk, speedWalkStairs, pyramid);
	this.decisionFactor = decisionFactor;
	this.minTimeToDecide = minTimeToDecide;
	this.maxTimeToDecide = maxTimeToDecide;
	
    }

    @Override
    protected void doAction()
    {
	this.doJump();
    }

    private void doAfterMove()
    {
	if (this.state == GameCharacter.ST_WALK)
	{
	    if (this.isLockedLeft())
		this.doJump();
	    else
	    {

	    }
	}

	// REVISA ESTO
	/*
	 * if state == st_walk: if is_on_wall(): input_vector.y = 0 if
	 * pyramid.is_locked(self): do_jump() else: if is_flip(): if ( color !=
	 * COLOR.WHITE && pyramid.can_jump_left(self) && make_decision_for_jump() ):
	 * do_jump() return else: if ( color != COLOR.WHITE &&
	 * pyramid.can_jump_right(self) && make_decision_for_jump() ): do_jump() return
	 * input_vector.x = -input_vector.x else: if not pyramid.is_floor_down(self): if
	 * vick.position.y > self.position.y || make_decision_for_continue(): return
	 * elif color != COLOR.WHITE && make_decision_for_jump(): do_jump() else:
	 * input_vector.x = -input_vector.x
	 */

	// TODO Auto-generated method stub

    }

    protected boolean canJump()
    {

	boolean respuesta = false;

	if (this.isLookRight())
	{
	    respuesta = this.getColPosition() < this.pyramid.getMapWidthInTiles() - 2
		    && this.pyramid.getCell(x + Config.getInstance().getLevelTileWidthUnits(),
			    this.y + Config.getInstance().getLevelTileHeightUnits()) != null
		    &&

		    this.pyramid.getCell(x + Config.getInstance().getLevelTileWidthUnits(),
			    this.y + Config.getInstance().getLevelTileHeightUnits() * 2) != null;

	} else
	{

	    respuesta = this.getColPosition() > 1
		    && this.pyramid.getCell(x - Config.getInstance().getLevelTileWidthUnits(),
			    this.y + Config.getInstance().getLevelTileHeightUnits()) != null
		    &&

		    this.pyramid.getCell(x - Config.getInstance().getLevelTileWidthUnits(),
			    this.y + Config.getInstance().getLevelTileHeightUnits() * 2) != null;

	}
	return respuesta;
    }

    private void setState(int state)
    {
	this.state = state;
	this.timer = 0;
	switch (this.state)
	{
	case Mummy.ST_APPEARING:
	case Mummy.ST_WAITING:
	    this.direction.setZero();
	    Player player = this.pyramid.getPlayer();
	    if (player.getX() < this.x)
		this.direction.x = -1;
	    else
		this.direction.x = 1;
	    this.timeToDecide = random.nextFloat(1.5f, 2.5f);
	    this.colisionEnabled = true;

	    break;
	case Mummy.ST_DISAPPEARING:
	    this.timeToDecide = 5;
	    this.colisionEnabled = false;
	    break;

	case Mummy.ST_WALK:
	    this.timeToDecide = random.nextFloat(this.minTimeToDecide, this.maxTimeToDecide);
	    break;

	}
    }

    /*
     * func set_state(st): self.state = st timer.stop() match self.state: st_init:
     * set_state(st_appearing)
     * 
     * st_appearing: Globals.play_sound(Globals.snd_mummyappear)
     * set_mode(MODE.APPEAR) motion = Vector2.ZERO input_vector = Vector2.ZERO
     * input_vector.x = -1 if vick.position.x < self.position.x else 1
     * animator.play("appear") self.visible = true yield(animator,
     * "animation_finished") set_state(st_waiting)
     * 
     * st_disappearing: set_mode(MODE.DISAPPEAR) motion = Vector2.ZERO input_vector
     * = Vector2.ZERO animator.play("disappear") yield(animator,
     * "animation_finished") #Programamos la aparición después de 5 segundos
     * timer.start(5)
     * 
     * st_teleporting: set_mode(MODE.DISAPPEAR) motion = Vector2.ZERO input_vector =
     * Vector2.ZERO animator.play("disappear") yield(animator, "animation_finished")
     * pyramid.teleport(self) #Programamos la aparición en medio segundo
     * timer.start(0.5)
     * 
     * st_waiting: set_mode(MODE.MUMMY) animator.play("waiting")
     * timer.start(rand_range(1.5, 2.5))
     * 
     * st_walk: #Establecemos un tiempo tras el cual, volvemos a decidir que hacer
     * match color: COLOR.WHITE: timer.start(rand_range(5, 10)) COLOR.BLUE:
     * timer.start(rand_range(5, 8)) COLOR.YELLOW: timer.start(rand_range(5, 6))
     * COLOR.ORANGE: timer.start(5) COLOR.RED: timer.start(rand_range(3, 5))
     */

    private boolean makeDecision()
    {
	return Mummy.random.nextDouble(1) <= this.decisionFactor;
    }

    private boolean makeDecisionForJump()
    {
	return Mummy.random.nextDouble(1) <= this.decisionFactor / 2;
    }

    private boolean makeDecisionForContinue()
    {
	return Mummy.random.nextDouble(1) <= 0.6;
    }

    @Override
    protected boolean canPassGiratoryMechanism(GiratoryMechanism giratoryMechanism)
    {
	return false;
    }

    @Override
    protected void passGiratoryMechanism(GiratoryMechanism giratoryMechanism)
    {

    }

    public void update(float deltaTime)
    {
	this.timer += deltaTime;
	this.direction.x = 1;
	this.move(this.direction, false, deltaTime);
	

    }

}
