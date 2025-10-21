package engine.gameCharacters.abstractGameCharacter;

import com.badlogic.gdx.math.Vector2;

import engine.level.Stair;

/**
 * Clase que representa del estado del caracter "caminando"
 * 
 * @author Guillermo Lazzurri
 */
public class GameCharacterStateWalking extends GameCharacterStateOnFloor
{

	/**
	 * Constructor encadenado. Llama a <br>
	 * super(gameCharacter, GameCharacter.ST_WALKING); <br>
	 * this.gameCharacter.resetAnimationDelta(); <br>
	 * 
	 * @param gameCharacter correspondiente al sujeto del patron state.
	 */
	public GameCharacterStateWalking(GameCharacter gameCharacter)
	{
		super(gameCharacter, GameCharacter.ST_WALKING);
		this.gameCharacter.resetAnimationDelta();
	}

	/**
	 * Verifica colisiones laterales y puede marcar los siguientes cambios de estado
	 */
	@Override
	protected void moveFirstStep(Vector2 v, boolean b, float deltaTime)
	{

		this.gameCharacter.motionVector.x = v.x * this.gameCharacter.getSpeedWalk();
		if (v.x != 0)
			this.gameCharacter.lookRight = v.x > 0;

		if (b)
			this.gameCharacter.doAction();
		else
		{

			if (v.x == 0)
				this.nextState = GameCharacter.ST_IDDLE;
			
			if (v.y != 0 && v.x != 0)
				this.checkEnterStair(v);
			if (!this.isFloorDown())
				this.nextState = GameCharacter.ST_FALLING;
		}
	}

	/**
	 * Verifica si el caracter esta entrando a una escalera. De ser asi, llama a
	 * this.gameCharacter.enterStair(stair);
	 * 
	 * @param v Indica la direccion pretendida
	 */
	protected void checkEnterStair(Vector2 v)
	{
		Stair stair = null;
		if (v.y > 0)
		{
			if (v.x > 0)
			{
				stair = this.gameCharacter.checkStairsFeetColision(true, true);

			} else
			{
				stair = this.gameCharacter.checkStairsFeetColision(false, true);
			}
		} else
		{
			if (v.x > 0)
			{
				stair = this.gameCharacter.checkStairsFeetColision(false, false);

			} else
			{
				stair = this.gameCharacter.checkStairsFeetColision(true, false);

			}
		}
		if (stair != null)
			this.gameCharacter.enterStair(stair);

	}

	/**
	 * llama a this.colisionForWalk(escalado);
	 */
	@Override
	protected void moveSecondStep(Vector2 escalado)
	{
		this.colisionForWalk(escalado);
	}

	

}
