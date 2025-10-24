package engine.gameCharacters.abstractGameCharacter;

import engine.level.LevelObject;
import engine.level.Stair;

/**
 * Clase abstracta que representa el estado de estar en el piso. Podra
 * extenderse a GameCharacterStateIddle, o GameCharacterStateWalking
 * 
 * @author Guillermo Lazzurri
 */
public abstract class GameCharacterStateOnFloor extends GameCharacterState
{
    /**
     * Indica a escalera a la que se entrara
     */
    protected Stair stairToEnter = null;
    private float initialMotionX;

    /**
     * Constructor de clase que llama a:<br>
     * super(gameCharacter, state);<br>
     * this.gameCharacter.motionVector.y = 0;<br>
     * this.gameCharacter.lastFloorCoordinate = this.gameCharacter.y; Este estado
     * actualiza el atributo this.gameCharacter.lastFloorCoordinate
     * 
     * @param gameCharacter Contexto del patron state
     * @param state         valor entero que representa el estado
     */
    public GameCharacterStateOnFloor(GameCharacter gameCharacter, int state)
    {
	super(gameCharacter, state);
	this.gameCharacter.motionVector.y = 0;
	this.gameCharacter.lastFloorCoordinate = this.gameCharacter.y;
    }

    /**
     * Marca el siguiente cambia de estado a GameCharacterStateOnStair
     */
    @Override
    protected void enterStair(Stair stair)
    {
	this.stairToEnter = stair;
	this.nextState = GameCharacter.ST_ONSTAIRS;
	LevelObject beginStair;
	if (this.gameCharacter.lastFloorCoordinate == stair.getDownStair().y)
	    beginStair = stair.getDownStair();
	else
	    beginStair = stair.getUpStair();
	float middleBeginStair=beginStair.x+beginStair.width*.5f;
	this.gameCharacter.x=middleBeginStair-this.gameCharacter.width*.5f;

    }

    /**
     * Si el salto se puede realizar se cambia el estado a GameCharacterStateJumping
     */
    @Override
    protected boolean doJump()
    {
	boolean r = false;
	if (this.gameCharacter.isFreeUp())
	{
	    this.nextState = GameCharacter.ST_JUMPING;
	    this.initialMotionX = this.gameCharacter.motionVector.x;

	    r = true;
	}
	return r;
    }

    protected void checkChangeStatus()
    {
	switch (this.nextState)
	{
	case GameCharacter.ST_IDDLE:
	    this.gameCharacter.gameCharacterState = new GameCharacterStateIddle(this.gameCharacter);
	    break;
	case GameCharacter.ST_WALKING:
	    this.gameCharacter.gameCharacterState = new GameCharacterStateWalking(this.gameCharacter);
	    break;
	case GameCharacter.ST_FALLING:
	    this.gameCharacter.gameCharacterState = new GameCharacterStateFalling(this.gameCharacter);
	    break;
	case GameCharacter.ST_JUMPING:
	    this.gameCharacter.gameCharacterState = new GameCharacterStateJumping(this.gameCharacter,
		    this.initialMotionX);
	    break;

	case GameCharacter.ST_ONSTAIRS:

	    this.gameCharacter.gameCharacterState = new GameCharacterStateOnStair(this.gameCharacter,
		    this.stairToEnter);
	    break;

	}

    }

}
