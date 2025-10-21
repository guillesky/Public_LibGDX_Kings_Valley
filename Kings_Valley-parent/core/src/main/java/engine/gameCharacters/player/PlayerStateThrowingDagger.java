package engine.gameCharacters.player;

import com.badlogic.gdx.math.Vector2;

import engine.KVEventListener;
import engine.game.Game;
import engine.gameCharacters.abstractGameCharacter.GameCharacter;
import engine.level.GiratoryMechanism;
import util.GameRules;

/**
 * Representa el estado "Lanzando daga"
 * 
 * @author Guillermo Lazzurri
 */
public class PlayerStateThrowingDagger extends PlayerState
{

	/**
	 * Contructor de clase, llama a super(player, Player.ST_THROWING_DAGGER);<br>
	 * dispara el evento Game.getInstance().eventFired(KVEventListener.THROW_DAGGER,
	 * this.player.item);
	 * 
	 * 
	 * @param player Corresponde al sujeto del patron state
	 */
	public PlayerStateThrowingDagger(Player player)
	{
		super(player, Player.ST_THROWING_DAGGER);

		Game.getInstance().eventFired(KVEventListener.THROW_DAGGER, this.player.item);
		this.player.item = null;

	}

	/**
	 * Si pasa el tiempo correspondiente a
	 * Config.getInstance().getTimeToEndThrowDagger() se realiza el cambio de estado
	 * mediante this.player.setPlayerState(new PlayerStateMoving(this.player,
	 * state));
	 * 
	 */
	@Override
	public void update(Vector2 v, boolean b, float deltaTime)
	{

		if (this.player.getAnimationDelta() >= GameRules.getInstance().getTimeToEndThrowDagger()) // termino de LANZAR
																									// LA
																									// DAGA
		{
			int state;
			if (v.x == 0)
				state = GameCharacter.ST_IDDLE;
			else
				state = GameCharacter.ST_WALKING;
			this.player.setPlayerState(new PlayerStateMoving(this.player, state));
		}
	}

	/**
	 * Se realiza el cambio de estado mediante this.player.setPlayerState(new
	 * PlayerStateDying(this.player));
	 * 
	 */
	@Override
	protected void die()
	{
		this.player.setPlayerState(new PlayerStateDying(this.player));

	}

	/**
	 * Se sobreescribe como metodo vacio (no hace nada)
	 */
	@Override
	protected void passGiratoryMechanism(GiratoryMechanism giratoryMechanism)
	{

	}

	/**
	 * Se sobreescribe como metodo vacio (no hace nada)
	 */
	@Override
	protected void doPicker()
	{

	}

	/**
	 * Se sobreescribe como metodo vacio (no hace nada)
	 */
	@Override
	protected void throwDagger()
	{

	}
}
