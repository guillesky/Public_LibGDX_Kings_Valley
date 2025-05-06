package modelo.gameCharacters.player;

import com.badlogic.gdx.math.Vector2;

public class PlayerStateDying extends PlayerState
{
    
    public PlayerStateDying(Player player)
    {
	super(player, Player.ST_DYING);
	
    }

    @Override
    public void update(Vector2 v, boolean b, float deltaTime)
    {
	
    }

}
