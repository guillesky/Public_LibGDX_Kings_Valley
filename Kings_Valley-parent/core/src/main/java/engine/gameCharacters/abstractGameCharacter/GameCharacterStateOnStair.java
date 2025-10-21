package engine.gameCharacters.abstractGameCharacter;

import com.badlogic.gdx.math.Vector2;

import engine.KVEventListener;
import engine.game.Game;
import engine.level.LevelObject;
import engine.level.Stair;

/**
 * Clase que representa del estado del caracter "En escalera"
 * 
 * @author Guillermo Lazzurri
 */
public class GameCharacterStateOnStair extends GameCharacterState
{
	/**
	 * Indica la escalera en la que esta el caracter
	 */
	private Stair stair;

	/**
	 * Llama a super(gameCharacter, GameCharacter.ST_WALKING);<br>
	 * this.stair = stair;<br>
	 * Game.getInstance().eventFired(KVEventListener.CHARACTER_ENTER_STAIR,
	 * this.gameCharacter);
	 * 
	 * 
	 * @param gameCharacter correspondiente al sujeto del patron state.
	 * @param stair         indica la escalera en la que esta el caracter (debe ser
	 *                      distinto de null)
	 */
	public GameCharacterStateOnStair(GameCharacter gameCharacter, Stair stair)
	{
		super(gameCharacter, GameCharacter.ST_WALKING);
		this.stair = stair;
		Game.getInstance().eventFired(KVEventListener.CHARACTER_ENTER_STAIR, this.gameCharacter);

	}

	/**
	 * Movera el caracter por la escalera dependiendo de su direccion horizontal
	 */
	@Override
	protected void moveFirstStep(Vector2 v, boolean b, float deltaTime)
	{
		this.gameCharacter.motionVector.x = v.x * this.gameCharacter.getSpeedWalkStairs();
		int sign;
		if (this.stair.isPositive())
		{
			sign = 1;

		} else
		{
			sign = -1;
		}
		this.gameCharacter.motionVector.y = v.x * this.gameCharacter.getSpeedWalkStairs() * sign;
		if (v.x != 0)
		{

			this.checkExitStair(v);
			this.gameCharacter.lookRight = v.x > 0;
			if (this.gameCharacter.state != GameCharacter.ST_WALKING)
			{
				this.gameCharacter.resetAnimationDelta();
				this.gameCharacter.state = GameCharacter.ST_WALKING;
			}
		} else
		{
			if (this.gameCharacter.state != GameCharacter.ST_IDDLE)
			{
				this.gameCharacter.resetAnimationDelta();
				this.gameCharacter.state = GameCharacter.ST_IDDLE;
			}
		}

	}

	/**
	 * Verifica si debe salir de la escalera y en ese caso llama a exitStair
	 * 
	 * @param v indica la direcion pretendida
	 */
	private void checkExitStair(Vector2 v)
	{
		if (this.stair.isPositive())
		{
			if (v.x > 0)
			{
				if (this.gameCharacter.y >= this.stair.getUpStair().y)
					this.exitStair(this.stair.getUpStair());

			} else
			{
				if (this.gameCharacter.y <= this.stair.getDownStair().y + this.stair.getDownStair().getHeight())
					this.exitStair(this.stair.getDownStair());
			}
		} else
		{
			if (v.x > 0)
			{
				if (this.gameCharacter.y <= this.stair.getDownStair().y + this.stair.getDownStair().getHeight())
					this.exitStair(this.stair.getDownStair());
			} else
			{
				if (this.gameCharacter.y >= this.stair.getUpStair().y)
					this.exitStair(this.stair.getUpStair());

			}

		}

	}

	/**
	 * Llamado al salir de la escalera. Marca el siguiente cambio de estado mediante
	 * this.nextState = GameCharacter.ST_WALKING;
	 * 
	 * @param endStair indica el pie o cabecera de la escalera por la que el
	 *                 caracter sale de la misma. Sirve para setear la posicion y
	 *                 del caracter
	 */
	private void exitStair(LevelObject endStair)
	{
		this.gameCharacter.y = endStair.y;
		this.gameCharacter.motionVector.y = 0;
		this.nextState = GameCharacter.ST_WALKING;
		this.stair = null;
	}

	/**
	 * Se sobreescribe como metodo vacio (no hace nada)
	 */

	@Override
	protected void moveSecondStep(Vector2 escalado)
	{

	}

	/**
	 * Retorna la escalera sobre la que esta el caracter. En este caso no puede ser
	 * null (si lo puede ser en otro tipo de estado)
	 */
	@Override
	protected Stair getStair()
	{
		return this.stair;
	}

	/**
	 * Se sobreescribe como metodo vacio (no hace nada)
	 */
	@Override
	protected void enterStair(Stair stair)
	{

	}

	/**
	 * Retorna false (no se puede saltar en la escalera)
	 */
	@Override
	protected boolean doJump()
	{
		return false;
	}

	/**
	 * Si hay un cambio de estado, Dispara el evento:
	 * Game.getInstance().eventFired(KVEventListener.CHARACTER_EXIT_STAIR,
	 * this.gameCharacter); <br>
	 * Llama a this.gameCharacter.gameCharacterState = new
	 * GameCharacterStateWalking(this.gameCharacter); (AL salir de una escalera solo
	 * se puede pasar a modo caminata)
	 * 
	 */
	@Override
	protected void checkChangeStatus()
	{
		if (this.nextState == GameCharacter.ST_WALKING)
		{
			Game.getInstance().eventFired(KVEventListener.CHARACTER_EXIT_STAIR, this.gameCharacter);
			this.gameCharacter.gameCharacterState = new GameCharacterStateWalking(this.gameCharacter);

		}

	}

}
