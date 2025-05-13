package modelo.gameCharacters.player;

import com.badlogic.gdx.math.Vector2;

import modelo.gameCharacters.abstractGameCharacter.GameCharacter;
import modelo.level.GiratoryMechanism;

public class PlayerStatePassingGiratory extends PlayerState
{
    private GiratoryMechanism passingGiratory;

    public PlayerStatePassingGiratory(Player player, GiratoryMechanism passingGiratory)
    {
	super(player, Player.ST_WALKING);
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
		int state;
		if(v.x==0)
			state=GameCharacter.ST_IDDLE;
		else
			state=GameCharacter.ST_WALKING;
	   this.player.setPlayerState(new PlayerStateWalking(this.player,state));
	}

    }

}
