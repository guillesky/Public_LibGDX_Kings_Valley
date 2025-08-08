package modelo.gameCharacters.abstractGameCharacter;

import modelo.level.Stair;

public abstract  class GameCharacterStateOnFloor extends GameCharacterState
{

	public GameCharacterStateOnFloor(GameCharacter gameCharacter, int state)
	{
		super(gameCharacter, state);
		
	}
	
	@Override
	protected void enterStair(Stair stair)
	{
		this.gameCharacter.gameCharacterState = new GameCharacterStateOnStair(this.gameCharacter, stair);

	}

	@Override
	protected boolean doJump()
	{
		boolean r = false;
		if (this.gameCharacter.isFreeUp())
		{
			this.gameCharacter.gameCharacterState = new GameCharacterStateJumping(this.gameCharacter);
			r = true;
		}
		return r;
	}

}
