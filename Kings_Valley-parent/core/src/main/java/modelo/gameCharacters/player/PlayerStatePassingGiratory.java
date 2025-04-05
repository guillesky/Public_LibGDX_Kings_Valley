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

public class PlayerStatePassingGiratory extends PlayerState
{
    private GiratoryMechanism passingGiratory;

    public PlayerStatePassingGiratory(Player player, GiratoryMechanism passingGiratory)
    {
	super(player, Player.ST_WALK);
	this.passingGiratory = passingGiratory;
    }

    @Override
    public void update(Vector2 v, boolean b, float deltaTime)
    {
	float direction;
	float speedWalkStairs = this.player.getSpeedWalkStairs();
	if (this.passingGiratory.isRight())
	    direction = 1;
	else
	    direction = -1;

	this.player.incX(direction * speedWalkStairs * 0.5f * deltaTime);
	if (!this.player.isColision(this.passingGiratory.getLevelItem()))
	{
	   this.player.setPlayerState(new PlayerStateWalking(this.player));
	}

    }

}
