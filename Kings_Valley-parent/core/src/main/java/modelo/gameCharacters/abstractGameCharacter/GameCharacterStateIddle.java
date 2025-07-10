package modelo.gameCharacters.abstractGameCharacter;

import com.badlogic.gdx.math.Vector2;

public class GameCharacterStateIddle extends GameCharacterState
{

    public GameCharacterStateIddle(GameCharacter gameCharacter)
    {
	super(gameCharacter, GameCharacter.ST_IDDLE);
	this.gameCharacter.resetAnimationDelta();
	this.gameCharacter.motionVector.y = 0;
    }

    @Override
    protected void moveFirstStep(Vector2 v, boolean b, float deltaTime)
    {

	if (b)
	{
	    if (v.x != 0)
		this.gameCharacter.motionVector.x = v.x * this.gameCharacter.speedWalk;
	    this.gameCharacter.doAction();

	} else if (v.x != 0)
	    this.gameCharacter.gameCharacterState = new GameCharacterStateWalking(this.gameCharacter);
    }

    @Override
    protected void moveSecondStep(Vector2 escalado)
    {

    }

}
