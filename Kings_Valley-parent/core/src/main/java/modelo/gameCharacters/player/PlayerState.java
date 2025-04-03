package modelo.gameCharacters.player;

import com.badlogic.gdx.math.Vector2;

public abstract class PlayerState
{
	protected Player player;

	public PlayerState(Player player, int state)
	{
		this.player = player;
		player.setState(state);
		this.player.resetAnimationDelta();
	}
	
	public abstract void update(Vector2 v, boolean b, float deltaTime);
    
}
