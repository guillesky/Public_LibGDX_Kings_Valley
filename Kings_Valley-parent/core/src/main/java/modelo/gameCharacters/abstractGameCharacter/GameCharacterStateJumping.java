package modelo.gameCharacters.abstractGameCharacter;

import com.badlogic.gdx.math.Vector2;

import modelo.gameCharacters.mummys.Mummy;

public class GameCharacterStateJumping extends GameCharacterState
{
	private float initialMotionX;

	public GameCharacterStateJumping(GameCharacter gameCharacter)
	{
		super(gameCharacter, GameCharacter.ST_JUMPING);
		this.gameCharacter.resetAnimationDelta();
		this.gameCharacter.motionVector.y = this.gameCharacter.speedJump;
		this.initialMotionX = this.gameCharacter.motionVector.x;
		
	}

	@Override
	protected void moveFirstStep(Vector2 v, boolean b, float deltaTime)
	{
		if (this.hasBlocked()&& this.gameCharacter.motionVector.y>0)
			this.tryUnblock();
		this.gameCharacter.motionVector.y += this.gameCharacter.speedFall * deltaTime;

		if (this.gameCharacter.motionVector.y < this.gameCharacter.speedFall)
			this.gameCharacter.motionVector.y = this.gameCharacter.speedFall;

	}

	@Override
	protected void moveSecondStep(Vector2 escalado)
	{
		this.checkLanding(escalado);
		this.colision(escalado);

	}

	private void tryUnblock()
	{
		float coordX = this.gameCharacter.x + this.initialMotionX;
		if (this.initialMotionX > 0)
			coordX += this.gameCharacter.width;
		if (!this.isCellBlocked(coordX, this.gameCharacter.y + this.gameCharacter.motionVector.y))
			this.gameCharacter.motionVector.x = this.initialMotionX;
	}

	private boolean hasBlocked()
	{
		return this.initialMotionX != 0 && this.gameCharacter.motionVector.x == 0;
	}
}
