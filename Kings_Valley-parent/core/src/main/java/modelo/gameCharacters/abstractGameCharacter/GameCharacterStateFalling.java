package modelo.gameCharacters.abstractGameCharacter;

import com.badlogic.gdx.math.Vector2;

import modelo.KVEventListener;
import modelo.game.Game;
import modelo.level.Stair;

/**
 * @author Guillermo Lazzurri
 * 
 *         Clase que representa del estado del caracter "cayendo"
 */
public class GameCharacterStateFalling extends GameCharacterState
{

	/**
	 * Constructor encadenado. Llama a <br>
	 * super(gameCharacter, GameCharacter.ST_FALLING); <br>
	 * this.gameCharacter.motionVector.x=0;<br>
	 * this.gameCharacter.resetAnimationDelta();<br>
	 * Dispara el evento:
	 * Game.getInstance().eventFired(KVEventListener.CHARACTER_BEGIN_FALL,
	 * this.gameCharacter);
	 * 
	 * @param gameCharacter correspondiente al sujeto del patron state.
	 */
	public GameCharacterStateFalling(GameCharacter gameCharacter)
	{
		super(gameCharacter, GameCharacter.ST_FALLING);
		this.gameCharacter.motionVector.x = 0;
		this.gameCharacter.resetAnimationDelta();
		Game.getInstance().eventFired(KVEventListener.CHARACTER_BEGIN_FALL, this.gameCharacter);

	}

	/**
	 * Sobrescribe el metodo de la superclase haciendo caer al caracter, en caso de
	 * tocar el suelo pasara a estado GameCharacterStateIddle, o
	 * GameCharacterStateWalking y dispara el evento
	 * Game.getInstance().eventFired(KVEventListener.CHARACTER_LANDING,
	 * this.gameCharacter);
	 * 
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
			Game.getInstance().eventFired(KVEventListener.CHARACTER_END_FALL, this.gameCharacter);
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
	 */
	@Override
	protected boolean doJump()
	{
		return false;
	}

}
