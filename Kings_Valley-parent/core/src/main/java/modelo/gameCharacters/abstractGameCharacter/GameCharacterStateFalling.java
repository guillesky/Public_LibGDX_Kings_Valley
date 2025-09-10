package modelo.gameCharacters.abstractGameCharacter;

import com.badlogic.gdx.math.Vector2;

import modelo.level.Stair;

/**
 * @author Guillermo Lazzurri
 * 
 *         Clase que representa del estado del caracter "cayendo"
 */
public class GameCharacterStateFalling extends GameCharacterState
{

	/**
	 * Constructor encadenado. Llama a super(gameCharacter,
	 * GameCharacter.ST_FALLING); this.gameCharacter.motionVector.x=0;
	 * this.gameCharacter.resetAnimationDelta();
	 * 
	 * @param gameCharacter correspondiente al sujeto del patron state.
	 */
	public GameCharacterStateFalling(GameCharacter gameCharacter)
	{
		super(gameCharacter, GameCharacter.ST_FALLING);
		this.gameCharacter.motionVector.x = 0;
		this.gameCharacter.resetAnimationDelta();
	}

	/**
	 * Sobrescribe el metodo de la superclase haciendo caer al caracter, en caso de
	 * tocar el suelo pasara a estado GameCharacterStateIddle, o
	 * GameCharacterStateWalking
	 */
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

	/**
	 * llama a this.checkLanding(escalado);
	 */
	@Override
	protected void moveSecondStep(Vector2 escalado)
	{
		this.checkLanding(escalado);

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
	 */@Override
	protected boolean doJump()
	{
		return false;
	}

}
