package engine.gameCharacters.abstractGameCharacter;

import com.badlogic.gdx.math.Vector2;

import engine.KVEventListener;
import engine.game.Game;
import util.GameRules;

/**
 * Clase que representa del estado del caracter "Saltando" (ya sea subiendo o
 * bajando)
 * 
 * @author Guillermo Lazzurri
 */
public class GameCharacterStateJumping extends GameCharacterStateOnAir
{
	private float initialMotionX;

	/**
	 * Constructor encadenado. Llama a <br>
	 * super(gameCharacter, GameCharacter.ST_JUMPING);<br>
	 * this.gameCharacter.motionVector.y = this.gameCharacter.speedJump;<br>
	 * this.initialMotionX = this.gameCharacter.motionVector.x;<br>
	 * * Dispara el
	 * evento:Game.getInstance().eventFired(KVEventListener.CHARACTER_JUMP,
	 * gameCharacter);
	 * 
	 * @param gameCharacter  correspondiente al contexto del patron state.
	 * @param initialMotionX indica la direccion de movimiento horizontal pretendida
	 *                       (Si salta hacia el costado estando bloqueado)
	 */
	public GameCharacterStateJumping(GameCharacter gameCharacter, float initialMotionX)
	{
		super(gameCharacter, GameCharacter.ST_JUMPING);
		this.gameCharacter.lookRight = initialMotionX > 0;
		this.gameCharacter.motionVector.y = this.gameCharacter.getSpeedJump();
		this.gameCharacter.motionVector.x = Math.signum(initialMotionX)
				* GameRules.getInstance().getCharacterHorizontalSpeedJump();
		this.initialMotionX = this.gameCharacter.motionVector.x;
		Game.getInstance().eventFired(KVEventListener.CHARACTER_JUMP, gameCharacter);

	}

	/**
	 * Calcula las colisione durante el salto. En caso de que el player se
	 * desbloquee horizontalmente durante el salto podra desplazarse para el costado
	 * para salir de un pozo.
	 */
	@Override
	protected void beforeScaling(Vector2 v, boolean b, float deltaTime)
	{

		if (this.hasBlocked() && this.gameCharacter.motionVector.y > 0)
			this.tryUnblock(deltaTime);
		super.beforeScaling(v, b, deltaTime);
	}

	/**
	 * Llama a super para verificar si llego al suelo y ademas calcula posibles
	 * colisiones laterales
	 */
	@Override
	protected void afterScaling(Vector2 escalado)
	{

		super.afterScaling(escalado);
		this.colision(escalado);
	}

	/**
	 * Intenta desplazarse hacia el costado en caso de ser necesario
	 */

	private void tryUnblock(float deltaTime)
	{
		int offset;
		if (this.initialMotionX > 0)
			offset = 1;
		else
			offset = -1;

		if (this.gameCharacter.pyramid.getCell(this.gameCharacter.x,
				this.gameCharacter.y + this.gameCharacter.motionVector.y * deltaTime, offset, 0) == null)
			this.gameCharacter.motionVector.x = this.initialMotionX;
	}

	/**
	 * Indica si el caracter esta bloqueado para desplazamiento horizontal
	 * 
	 * @return true si esta bloqueado horizontalemente, false en caso contrario
	 */
	private boolean hasBlocked()
	{
		return this.initialMotionX != 0 && this.gameCharacter.motionVector.x == 0;
	}

	/**
	 * Si hay un cambio de estado, Dispara el evento:
	 * Game.getInstance().eventFired(KVEventListener.CHARACTER_END_JUMP,
	 * this.gameCharacter); <br>
	 * Llama a super.checkChangeStatus();
	 * 
	 */
	@Override
	protected void checkChangeStatus()
	{
		if (this.nextState != GameCharacter.ST_NO_CHANGE)
			Game.getInstance().eventFired(KVEventListener.CHARACTER_END_JUMP, this.gameCharacter);

		super.checkChangeStatus();
	}

}
