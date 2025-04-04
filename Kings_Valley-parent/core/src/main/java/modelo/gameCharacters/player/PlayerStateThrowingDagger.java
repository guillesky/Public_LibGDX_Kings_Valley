package modelo.gameCharacters.player;

import java.util.ArrayList;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

import modelo.DrawableElement;
import modelo.GiratoryMechanism;
import modelo.Juego;
import modelo.Pyramid;
import modelo.gameCharacters.GameCharacter;
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

	this.player.incAnimationDelta(deltaTime);

	if (this.player.getAnimationDelta() >= 0.2f) // termino de LANZAR LA DAGA
	{
	    this.player.setPlayerState(new PlayerStateWalking(this.player));
	}
    }

}
