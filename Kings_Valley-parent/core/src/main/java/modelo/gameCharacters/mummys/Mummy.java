package modelo.gameCharacters.mummys;

import java.util.Random;

import com.badlogic.gdx.math.Vector2;

import modelo.gameCharacters.abstractGameCharacter.GameCharacter;
import modelo.gameCharacters.player.Player;
import modelo.level.GiratoryMechanism;
import modelo.level.Pyramid;
import modelo.level.Stair;
import util.Config;

@SuppressWarnings("serial")
public abstract class Mummy extends GameCharacter
{

    public static final int ST_LIMBUS = 101;

    public static final int ST_APPEARING = 102;
    public static final int ST_TELEPORTING = 104;
    protected static final int BLOCK_FREE = 0;
    protected static final int BLOCK_BRICK = 1;
    protected static final int BLOCK_GIRATORY = 2;

    protected static final Random random = new Random();

    protected float decisionFactorForFall;
    protected float decisionFactorForJump;
    private float timeToDecide;
    private float timeDeciding;
    private float timeInState = 0;

    private Vector2 direction = new Vector2();
    protected MummyState mummyState;
    private float stressLevel = 0;
    protected Player player;

    protected float getTimeInState()
    {
	return timeInState;
    }

    protected void resetTimeInState()
    {
	this.timeInState = 0;
    }

    protected void incTimeInState(float delta)
    {
	this.timeInState += delta;
    }

    public Mummy(int type, float x, float y, float[] parameters, Pyramid pyramid, Player player)
    {
	super(type, x, y, parameters[Config.INDEX_SPEED_WALK], parameters[Config.INDEX_SPEED_STAIR], pyramid);
	this.decisionFactorForFall = parameters[Config.INDEX_DECICION_FACTOR_FALL];
	this.decisionFactorForJump = parameters[Config.INDEX_DECICION_FACTOR_JUMP];
	this.timeToDecide = parameters[Config.INDEX_TIME_TO_DECIDE];
	this.timeDeciding = parameters[Config.INDEX_TIME_DECIDING];

	this.mummyState = new MummyStateLimbus(this, 1);
	this.player = player;
    }

    @Override
    protected void doAction()
    {
	this.doJump();
    }

    protected boolean isInBorderCliff()
    {
	boolean condicion = false;

	float probableX;
	if (this.isLookRight())
	    probableX = x + width * .5f;
	else
	    probableX = x;
	condicion = this.pyramid.getCell(probableX, this.y - Config.getInstance().getLevelTileHeightUnits()) == null
		&& this.pyramid.getCell(probableX, this.y) == null
		&& this.pyramid.getCell(probableX, this.y + Config.getInstance().getLevelTileHeightUnits()) == null;
	return condicion;

    }

    protected float getTimeToDecide()
    {
	return this.timeToDecide;
    }

    protected float getTimeDeciding()
    {
	return this.timeDeciding;
    }

    protected boolean makeDecisionForJump()
    {
	return Mummy.random.nextDouble() <= this.decisionFactorForJump;
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
	this.incAnimationDelta(deltaTime);
	this.incTimeInState(deltaTime);
	this.mummyState.update(deltaTime);

    }

    @Override
    protected void move(Vector2 v, boolean b, float deltaTime)
    {
	super.move(v, b, deltaTime);

    }

    public Vector2 getDirection()
    {
	return direction;
    }

    protected void setState(int state)
    {
	this.state = state;

    }

    protected void stressing()
    {
	this.stressLevel++;
    }

    protected void calmStress(float cant)
    {
	this.stressLevel -= cant;
    }

    protected float getStressLevel()
    {
	return stressLevel;
    }

    protected void resetStress()
    {
	this.stressLevel = 0;

    }

    protected float distanceQuadToPlayer(Player player)
    {

	return this.distanceQuadToPlayer(this.x, this.y, player);
    }

    private float distanceQuadToPlayer(float paramX, float paramY, Player player)
    {
	float deltaX = paramX - player.getX();
	float deltaY = paramY - player.getY();

	return deltaX * deltaX + deltaY * deltaY;

    }

    @Override
    protected void resetAnimationDelta()
    {
	super.resetAnimationDelta();
    }

    public void die(boolean mustTeleport)
    {

	this.mummyState.die(mustTeleport);
    }

    public boolean isDanger()
    {
	return this.mummyState.isDanger();
    }

    public void teleport()
    {
	float[] coords;
	do
	{
	    coords = this.getRandomCellInFloor();

	} while (this.distanceQuadToPlayer(coords[0], coords[1], player) < Config.getInstance()
		.getMinMummySpawnDistanceToPlayer());
	this.x = coords[0];
	this.y = coords[1];

    }

    private float[] getRandomCellInFloor()
    {
	int i;
	int j;
	do
	{
	    i = random.nextInt(this.pyramid.getMapHeightInTiles() - 2) + 1;
	    j = random.nextInt(this.pyramid.getMapWidthInTiles() - 2) + 1;

	} while (this.pyramid.getCellInTiledCoord(j, i) != null || this.pyramid.getCellInTiledCoord(j, i + 1) != null);

	while (this.pyramid.getCellInTiledCoord(j, i - 1) == null)
	    i--;
	float[] r =
	{ j * Config.getInstance().getLevelTileWidthUnits(), i * Config.getInstance().getLevelTileHeightUnits() };
	return r;
    }

    @Override
    protected Stair checkStairsFeetColision(boolean positiveStairs, boolean isUpping)
    {

	return super.checkStairsFeetColision(positiveStairs, isUpping);
    }

    @Override
    protected void enterStair(Stair stair)
    {

	super.enterStair(stair);
    }

    protected boolean canJump()
    {
	float posX;
	int offset;
	if (this.lookRight)
	{
	    posX = this.x;
	    offset = 1;
	} else
	{
	    posX = this.x + this.width;
	    offset = -1;
	}
	return this.pyramid.getCell(posX, this.y, offset, 2) == null;

    }

}
