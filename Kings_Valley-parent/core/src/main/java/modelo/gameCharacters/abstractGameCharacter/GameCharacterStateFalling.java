package modelo.gameCharacters.abstractGameCharacter;

import com.badlogic.gdx.math.Vector2;

public class GameCharacterStateFalling extends GameCharacterState
{

    public GameCharacterStateFalling(GameCharacter gameCharacter)
    {
	super(gameCharacter, GameCharacter.ST_FALLING);
	this.gameCharacter.motionVector.x=0;
	this.gameCharacter.resetAnimationDelta();
    }

    @Override
    protected void moveFirstStep(Vector2 v, boolean b, float deltaTime)
    {
	Vector2 motionVector = this.gameCharacter.motionVector;
	motionVector.y += this.gameCharacter.speedFall * deltaTime;

	if (motionVector.y < this.gameCharacter.speedFall)
	    motionVector.y = this.gameCharacter.speedFall;

	if (this.isFloorDown())
	{
	    if (v.x == 0)
		this.gameCharacter.gameCharacterState = new GameCharacterStateIddle(this.gameCharacter);
	    else
		this.gameCharacter.gameCharacterState = new GameCharacterStateWalking(this.gameCharacter);

	}
    }

    @Override
    protected void moveSecondStep(Vector2 escalado)
    {
	this.checkLanding(escalado);
	this.colision(escalado);
	
    }

}
