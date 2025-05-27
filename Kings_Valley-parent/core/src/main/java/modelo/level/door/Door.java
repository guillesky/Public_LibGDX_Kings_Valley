package modelo.level.door;

import modelo.gameCharacters.player.Player;
import modelo.level.LevelObject;
import modelo.level.Mechanism;
import util.Config;
import util.Constantes;

public class Door extends Mechanism
{
    public static final int HIDE = 0;
    public static final int CLOSED = 1;
    public static final int OPEN = 2;
    public static final int CLOSING = 3;
    public static final int OPENING = 4;

    public static final int TO_PREVIUS = 0;
    public static final int TO_NEXT = -1;
    public static final int UNIQUE = -2;

    private LevelObject lever;
    private LevelObject passage;
    protected DoorState doorState;
    private int state;
    private int idLevel;
    private int levelConnected;

    public Door(LevelObject lever, int idLevel, float timeToEnd)
    {
	super(timeToEnd);
	this.lever = lever;
	float width = Config.getInstance().getLevelTileWidthUnits();
	float eight = Config.getInstance().getLevelTileHeightUnits();
	float x = this.lever.x + width * 3;
	float y = this.lever.y - eight * 2;
	this.doorState = new DoorStateHide(this);
	this.passage = new LevelObject(Constantes.It_door_passage, x, y, this.lever.getP0(), width * 0.2f, eight * 2);
	this.active = false;
	this.levelConnected = lever.getP0() * -1;
	this.idLevel = idLevel;

    }

    public LevelObject getLever()
    {
	return lever;
    }

    public LevelObject getPassage()
    {
	return passage;
    }

    @Override
    public void update(float deltaTime)
    {
	this.doorState.update(deltaTime);
    }

    public boolean isVisible()
    {
	return this.state != Door.HIDE;
    }

    public int getState()
    {
	return state;
    }

    protected void setState(int state)
    {
	this.state = state;
    }

    public void setVisible()
    {
	this.doorState = new DoorStateClosed(this);

    }

    public void checkPushLever(Player player)
    {
	this.doorState.checkPushLever(player);
    }

    @Override
    protected void incTime(float delta)
    {
	super.incTime(delta);
    }

    @Override
    protected void resetTime()
    {
	super.resetTime();
    }

    public boolean checkEnterPassage(Player player)
    {
	return this.doorState.checkEnterPassage(player);
    }

    public int getLevelConnected()
    {
	return levelConnected;
    }

    public int getIdLevel()
    {
	return idLevel;
    }

    protected float getTimeToEnd()
    {
	return this.timeToEnd;
    }

}
