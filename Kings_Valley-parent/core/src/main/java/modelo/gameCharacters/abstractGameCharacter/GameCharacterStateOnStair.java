package modelo.gameCharacters.abstractGameCharacter;

import com.badlogic.gdx.math.Vector2;

import modelo.level.LevelObject;
import modelo.level.Stair;

public class GameCharacterStateOnStair extends GameCharacterState
{
    private Stair stair;

    public GameCharacterStateOnStair(GameCharacter gameCharacter, Stair stair)
    {
	super(gameCharacter, GameCharacter.ST_WALKING);
	this.stair = stair;
    }

    @Override
    protected void moveFirstStep(Vector2 v, boolean b, float deltaTime)
    {
	this.gameCharacter.motionVector.x = v.x * this.gameCharacter.speedWalkStairs;
	int sign;
	if (this.stair.isPositive())
	{
	    sign = 1;

	} else
	{
	    sign = -1;
	}
	this.gameCharacter.motionVector.y = v.x * this.gameCharacter.speedWalkStairs * sign;
	if (v.x != 0)
	{

	    this.checkExitStair(v);
	    this.gameCharacter.lookRight = v.x > 0;
	    if (this.gameCharacter.state != gameCharacter.ST_WALKING)
	    {
		this.gameCharacter.resetAnimationDelta();
		this.gameCharacter.state = gameCharacter.ST_WALKING;
	    }
	} else
	{
	    if (this.gameCharacter.state != this.gameCharacter.ST_IDDLE)
	    {
		this.gameCharacter.resetAnimationDelta();
		this.gameCharacter.state = this.gameCharacter.ST_IDDLE;
	    }
	}

    }

    private void checkExitStair(Vector2 v)
    {
	if (this.stair.isPositive())
	{
	    if (v.x > 0)
	    {
		if (this.gameCharacter.y >= this.stair.getUpStair().y)
		    this.exitStair(this.stair.getUpStair());

	    } else
	    {
		if (this.gameCharacter.y <= this.stair.getDownStair().y + this.stair.getDownStair().getHeight())
		    this.exitStair(this.stair.getDownStair());
	    }
	} else
	{
	    if (v.x > 0)
	    {
		if (this.gameCharacter.y <= this.stair.getDownStair().y + this.stair.getDownStair().getHeight())
		    this.exitStair(this.stair.getDownStair());
	    } else
	    {
		if (this.gameCharacter.y >= this.stair.getUpStair().y)
		    this.exitStair(this.stair.getUpStair());

	    }

	}



    }

    private void exitStair(LevelObject endStair)
    {
	    this.gameCharacter.y = endStair.y;
	    this.gameCharacter.motionVector.y = 0;
	    this.gameCharacter.gameCharacterState = new GameCharacterStateWalking(this.gameCharacter);
	    this.stair = null;

    }

    @Override
    protected void moveSecondStep(Vector2 escalado)
    {

    }

    @Override
    protected boolean isInStair()
    {
	return true;
    }

}
