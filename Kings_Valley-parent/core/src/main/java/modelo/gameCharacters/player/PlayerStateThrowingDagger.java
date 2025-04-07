package modelo.gameCharacters.player;

import java.util.ArrayList;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

import modelo.Juego;
import modelo.gameCharacters.GameCharacter;
import modelo.level.DrawableElement;
import modelo.level.GiratoryMechanism;
import modelo.level.Pyramid;
import util.Constantes;

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
