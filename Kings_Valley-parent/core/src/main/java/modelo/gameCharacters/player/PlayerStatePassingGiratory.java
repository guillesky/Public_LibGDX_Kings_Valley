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
	if (!this.player.isColision(this.passingGiratory.getLevelObject()))
	{
	   this.player.setPlayerState(new PlayerStateWalking(this.player));
	}

    }

}
