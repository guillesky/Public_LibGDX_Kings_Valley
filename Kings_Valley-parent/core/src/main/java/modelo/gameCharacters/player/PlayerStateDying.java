package modelo.gameCharacters.player;

import com.badlogic.gdx.math.Vector2;

import modelo.KVEventListener;
import modelo.game.Game;
import modelo.level.GiratoryMechanism;

/**
 * @author Guillermo Lazzurri
 * 
 *         Representa el estado del player "muriendo"
 */
public class PlayerStateDying extends PlayerState
{

	/**
	 * Constructor de clase. Llama a super(player, Player.ST_DYING);<br>
	 * Dispara el evento Game.getInstance().eventFired(KVEventListener.PLAYER_DIE,
	 * this.player);
	 * 
	 * @param player Corresponde al sujeto del patron state
	 */
	public PlayerStateDying(Player player)
	{
		super(player, Player.ST_DYING);
		Game.getInstance().eventFired(KVEventListener.PLAYER_DIE, this.player);

	}

	/**
	 * Se sobreescribe como metodo vacio (no hace nada)
	 */
	@Override
	public void update(Vector2 v, boolean b, float deltaTime)
	{
	}

	/**
	 * Se sobreescribe como metodo vacio (no hace nada)
	 */
	@Override
	protected void die()
	{

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
