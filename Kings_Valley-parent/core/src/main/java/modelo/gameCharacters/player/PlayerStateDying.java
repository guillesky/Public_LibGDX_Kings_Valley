package modelo.gameCharacters.player;

import com.badlogic.gdx.math.Vector2;

import modelo.KVEventListener;
import modelo.game.Game;

public class PlayerStateDying extends PlayerState
{

	public PlayerStateDying(Player player)
	{
		super(player, Player.ST_DYING);
		Game.getInstance().eventFired(KVEventListener.PLAYER_DIE, this.player);

	}

	@Override
	public void update(Vector2 v, boolean b, float deltaTime)
	{
	}

	@Override
	protected void die()
	{

	}

}
