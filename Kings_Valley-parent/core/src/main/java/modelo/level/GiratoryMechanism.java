package modelo.level;

import util.Config;

public class GiratoryMechanism extends Mechanism
{
    LevelObject levelObject;
    private boolean right;
    private int heightInTiles;

    public GiratoryMechanism(LevelObject levelObject, float timeToEnd)
    {
	super(timeToEnd);
	this.levelObject = levelObject;
	this.heightInTiles = (int) (levelObject.getHeight() / Config.getInstance().getLevelTileHeightUnits());
	this.right = (levelObject.getP0() == 0);
	this.active = false;

    }

    @Override
    public void update(float deltaTime)
    {
	this.incTime(deltaTime);
	if (this.time >= this.timeToEnd)
	{
	    this.end();
	}
    }

    private void end()
    {
	this.resetTime();
	this.right = !this.right;
	this.active = false;
    }

    public void activate()
    {
	this.active = true;
    }

    public LevelObject getLevelObject()
    {
	return levelObject;
    }

    public boolean isRight()
    {
	return right;
    }

    public int getHeightInTiles()
    {
	return heightInTiles;
    }

}
