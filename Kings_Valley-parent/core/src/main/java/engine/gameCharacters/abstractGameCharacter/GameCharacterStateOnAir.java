package engine.gameCharacters.abstractGameCharacter;

import com.badlogic.gdx.math.Vector2;

import engine.level.Stair;

/**
 * Clase abstracta que representa el estado de estar en el aire. Podra
 * extenderse a GameCharacterStateJumping, o GameCharacterStateFaling
 * 
 * @author Guillermo Lazzurri
 */
public abstract class GameCharacterStateOnAir extends GameCharacterState
{

	/**
	 * Constructor de clase que llama a super(gameCharacter, state);
	 * 
	 * @param gameCharacter Caracter al cual pertenece el estado
	 * @param state         valor entero que representa el estado
	 */
	public GameCharacterStateOnAir(GameCharacter gameCharacter, int state)
	{
		super(gameCharacter, state);
		this.gameCharacter.resetAnimationDelta();
	}

	/**
	 * Se sobreescribe como metodo vacio (no hace nada)
	 */
	@Override
	protected void enterStair(Stair stair)
	{

	}

	/**
	 * Se sobreescribe como metodo vacio (no hace nada) y retorna false
	 */
	@Override
	protected boolean doJump()
	{
		return false;
	}

	/**
	 * Sobrescribe el metodo de la superclase haciendo caer al caracter
	 * 
	 */
	@Override
	protected void beforeScaling(Vector2 v, boolean b, float deltaTime)
	{
			
		this.gameCharacter.motionVector.y += this.gameCharacter.getSpeedFall() * deltaTime;

		if (this.gameCharacter.motionVector.y < this.gameCharacter.getSpeedFall())
			this.gameCharacter.motionVector.y = this.gameCharacter.getSpeedFall();
	
	
	
	}
	
	
	/**
	 * llama a this.checkLanding(escalado);
	 */
	@Override
	protected void afterScaling(Vector2 escalado)
	{
		this.checkLanding(escalado);

	}

	/**
	 * En caso de tocar el suelo pasara a estado GameCharacterStateIddle, o
	 * GameCharacterStateWalking
	 */
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
		}

	}
}
