package modelo.gameCharacters.abstractGameCharacter;

import modelo.level.Stair;

/**
 * @author Guillermo Lazzurri Clase abstracta que representa el estado de estar
 *         en el piso. Podra extenderse a GameCharacterStateIddle, o
 *         GameCharacterStateWalking
 */
public abstract class GameCharacterStateOnFloor extends GameCharacterState
{

	/**
	 * Constructor de clase que llama a super(gameCharacter, state);
	 * 
	 * @param gameCharacter Caracter al cual pertenece el estado
	 * @param state         valor entero que representa el estado
	 */
	public GameCharacterStateOnFloor(GameCharacter gameCharacter, int state)
	{
		super(gameCharacter, state);

	}

	/**
	 * cambia a estado GameCharacterStateOnStair
	 */
	@Override
	protected void enterStair(Stair stair)
	{
		this.gameCharacter.gameCharacterState = new GameCharacterStateOnStair(this.gameCharacter, stair);

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
			this.gameCharacter.gameCharacterState = new GameCharacterStateJumping(this.gameCharacter);
			r = true;
		}
		return r;
	}

}
