package modelo.gameCharacters.abstractGameCharacter;

import com.badlogic.gdx.math.Vector2;

public class GameCharacterStateJumping extends GameCharacterState
{

    public GameCharacterStateJumping(GameCharacter gameCharacter)
    {
	super(gameCharacter, GameCharacter.ST_JUMPING);
	this.gameCharacter.resetAnimationDelta();
	this.gameCharacter.motionVector.y = this.gameCharacter.speedJump;
    }

    @Override
    protected void moveFirstStep(Vector2 v, boolean b, float deltaTime)
    {
	this.gameCharacter.motionVector.y += this.gameCharacter.speedFall * deltaTime;

	if (this.gameCharacter.motionVector.y < this.gameCharacter.speedFall)
	    this.gameCharacter.motionVector.y =  this.gameCharacter.speedFall;

    }

    @Override
    protected void moveSecondStep(Vector2 escalado)
    {
	this.checkLanding(escalado);
	this.colision(escalado);
    }

}
