package modelo.gameCharacters.player;

import com.badlogic.gdx.math.Vector2;

public class PlayerStateThrowingDagger extends PlayerState
{

    public PlayerStateThrowingDagger(Player player)
    {
	super(player, Player.ST_THROWING_DAGGER);

    }

    @Override
    public void update(Vector2 v, boolean b, float deltaTime)
    {

	
	if (this.player.getAnimationDelta() >= 0.2f) // termino de LANZAR LA DAGA
	{
	    this.player.setPlayerState(new PlayerStateWalking(this.player));
	}
    }

}
