package engine.gameCharacters.abstractGameCharacter;

import engine.KVEventListener;
import engine.game.Game;

/**
 * Clase que representa del estado del caracter "cayendo"
 * 
 * @author Guillermo Lazzurri
 */
public class GameCharacterStateFalling extends GameCharacterStateOnAir
{

	/**
	 * Constructor encadenado. Llama a <br>
	 * super(gameCharacter, GameCharacter.ST_FALLING); <br>
	 * this.gameCharacter.motionVector.x=0;<br>
	 * 
	 * Dispara el evento:
	 * Game.getInstance().eventFired(KVEventListener.CHARACTER_BEGIN_FALL,
	 * this.gameCharacter);
	 * 
	 * @param gameCharacter correspondiente al contexto del patron state.
	 */
	public GameCharacterStateFalling(GameCharacter gameCharacter)
	{
		super(gameCharacter, GameCharacter.ST_FALLING);
		this.gameCharacter.motionVector.x = 0;
		Game.getInstance().eventFired(KVEventListener.CHARACTER_BEGIN_FALL, this.gameCharacter);

	}

	/**
	 * Si hay un cambio de estado, Dispara el evento:
	 * Game.getInstance().eventFired(KVEventListener.CHARACTER_END_FALL,
	 * this.gameCharacter); <br>
	 * Llama a super.checkChangeStatus();
	 * 
	 */
	@Override
	protected void checkChangeStatus()
	{
		if (this.nextState != GameCharacter.ST_NO_CHANGE)
			Game.getInstance().eventFired(KVEventListener.CHARACTER_END_FALL, this.gameCharacter);

		super.checkChangeStatus();
	}

}
