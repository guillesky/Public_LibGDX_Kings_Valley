package engine.gameCharacters.abstractGameCharacter;

import com.badlogic.gdx.math.Vector2;

import engine.KVEventListener;
import engine.game.Game;
import engine.level.Stair;
import util.GameRules;

/**
 * @author Guillermo Lazzurri
 * 
 *         Clase que representa del estado del caracter "Saltando" (ya sea
 *         subiendo o bajando)
 */
public class GameCharacterStateJumping extends GameCharacterState
{
	private float initialMotionX;

	/**
	 * Constructor encadenado. Llama a <br>
	 * super(gameCharacter, GameCharacter.ST_JUMPING);<br>
	 * this.gameCharacter.resetAnimationDelta();<br>
	 * this.gameCharacter.motionVector.y = this.gameCharacter.speedJump;<br>
	 * this.initialMotionX = this.gameCharacter.motionVector.x;<br>
	 * 
	 * 
	 * @param gameCharacter correspondiente al sujeto del patron state.
	 */
	public GameCharacterStateJumping(GameCharacter gameCharacter)
	{
		super(gameCharacter, GameCharacter.ST_JUMPING);
		this.gameCharacter.resetAnimationDelta();
		this.gameCharacter.motionVector.y = this.gameCharacter.getSpeedJump();
		this.gameCharacter.motionVector.x=Math.signum(this.gameCharacter.motionVector.x)*GameRules.getInstance().getCharacterHorizontalSpeedJump();
		this.initialMotionX = this.gameCharacter.motionVector.x;
		Game.getInstance().eventFired(KVEventListener.CHARACTER_JUMP, gameCharacter);
	}

	/**
	 *Calcula las colisione durante el salto. En caso de que el player se desbloquee horizontalmente durante el salto podra desplazarse para el costado para salir de un pozo.
	 */
	@Override
	protected void moveFirstStep(Vector2 v, boolean b, float deltaTime)
	{
		if (this.hasBlocked() && this.gameCharacter.motionVector.y > 0)
			this.tryUnblock();
		this.gameCharacter.motionVector.y += this.gameCharacter.getSpeedFall() * deltaTime;

		if (this.gameCharacter.motionVector.y < this.gameCharacter.getSpeedFall())
			this.gameCharacter.motionVector.y = this.gameCharacter.getSpeedFall();

	}

	/**
	 *Llama a checkLanding para verificar si llego al suelo
	 */
	@Override
	protected void moveSecondStep(Vector2 escalado)
	{
		this.checkLanding(escalado);
		this.colision(escalado);

	}

	/**
	 * Intenta desplazarse hacia el costado en caso de ser necesario
	 */
	private void tryUnblock()
	{
		float coordX = this.gameCharacter.x + this.initialMotionX;
		if (this.initialMotionX > 0)
			coordX += this.gameCharacter.width;
		if (!this.isCellBlocked(coordX, this.gameCharacter.y + this.gameCharacter.motionVector.y))
			this.gameCharacter.motionVector.x = this.initialMotionX;
	}

	/**
	 * Indica si el caracter esta bloqueado para desplazamiento horizontal
	 * @return true si esta bloqueado horizontalemente, false en caso contrario
	 */
	private boolean hasBlocked()
	{
		return this.initialMotionX != 0 && this.gameCharacter.motionVector.x == 0;
	}

	/**
	 *
	 * Se sobreescribe como metodo vacio (no hace nada)
	 */

	@Override
	protected void enterStair(Stair stair)
	{

	}

	/**
	 * Siempre retorna false (ya esta saltado)
	 */
	@Override
	protected boolean doJump()
	{
		return false;
	}
}
