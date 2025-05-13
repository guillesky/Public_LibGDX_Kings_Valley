package modelo.gameCharacters.player;

import com.badlogic.gdx.math.Vector2;

import modelo.gameCharacters.abstractGameCharacter.GameCharacter;

public class PlayerStateWalking extends PlayerState
{

	public PlayerStateWalking(Player player, int state)
	{
			super(player, state);
	}

	@Override
	public void update(Vector2 v, boolean b, float deltaTime)
	{
		this.player.move(v, b, deltaTime);

	}

}
