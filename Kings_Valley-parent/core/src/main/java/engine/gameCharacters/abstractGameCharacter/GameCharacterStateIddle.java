package engine.gameCharacters.abstractGameCharacter;

import com.badlogic.gdx.math.Vector2;

/**
 * Clase que representa del estado del caracter "quieto en el piso"
 * 
 * @author Guillermo Lazzurri
 */
public class GameCharacterStateIddle extends GameCharacterStateOnFloor
{

	/**
	 * Constructor de clase que llama a super(gameCharacter,
	 * GameCharacter.ST_IDDLE);
	 * 
	 * @param gameCharacter Caracter al cual pertenece el estado
	 */
	public GameCharacterStateIddle(GameCharacter gameCharacter)
	{
		super(gameCharacter, GameCharacter.ST_IDDLE);
		this.gameCharacter.resetAnimationDelta();

	}

	/**
	 * Realiza los calculos y eventualemente maraca el siguiente cambio de estado.
	 */
	@Override
	protected void moveFirstStep(Vector2 v, boolean b, float deltaTime)
	{

		if (b)
		{
			if (v.x != 0)
				this.gameCharacter.motionVector.x = v.x * this.gameCharacter.getSpeedWalk();
			this.gameCharacter.doAction();

		} else if (v.x != 0)
			this.nextState = GameCharacter.ST_WALKING;
	}

	/**
	 * Se sobreescribe como metodo vacio (no hace nada)
	 */
	@Override
	protected void moveSecondStep(Vector2 escalado)
	{

	}

	

}
