package engine.gameCharacters.player;

import com.badlogic.gdx.math.Vector2;

import engine.KVEventListener;
import engine.game.Game;
import engine.gameCharacters.abstractGameCharacter.GameCharacter;
import engine.level.GiratoryMechanism;

/**
 * Representa el estado "Pasando por puerta giratoria"
 * 
 * @author Guillermo Lazzurri
 */
public class PlayerStatePassingGiratory extends PlayerState
{
	private GiratoryMechanism passingGiratory;

	/**
	 * Constructor de clase, llama a super(player, Player.ST_WALKING);
	 * 
	 * @param player          Corresponde al sujeto del patron state
	 * @param passingGiratory Indica la puerta giratoria por la que se esta pasando
	 */
	public PlayerStatePassingGiratory(Player player, GiratoryMechanism passingGiratory)
	{
		super(player, Player.ST_WALKING);
		this.passingGiratory = passingGiratory;
	}

	/**
	 * Al terminar de pasar por la girtoria se realiza el cambio de estado mediante
	 * this.player.setPlayerState(new PlayerStateMoving(this.player,state));
	 * 
	 */
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
			if (v.x == 0)
				state = GameCharacter.ST_IDDLE;
			else
				state = GameCharacter.ST_WALKING;
			Game.getInstance().eventFired(KVEventListener.EXIT_GIRATORY, this.passingGiratory);

			this.player.setPlayerState(new PlayerStateMoving(this.player, state));
		}

	}

	/**
	 * Se realiza el cambio de estado mediante this.player.setPlayerState(new
	 * PlayerStateDying(this.player));
	 * 
	 */
	@Override
	protected void die()
	{
		this.player.setPlayerState(new PlayerStateDying(this.player));

	}

	/**
	 * Se sobreescribe como metodo vacio (no hace nada)
	 */
	@Override
	protected void passGiratoryMechanism(GiratoryMechanism giratoryMechanism)
	{

	}

	/**
	 * Se sobreescribe como metodo vacio (no hace nada)
	 */
	@Override
	protected void doPicker()
	{

	}

	/**
	 * Se sobreescribe como metodo vacio (no hace nada)
	 */
	@Override
	protected void throwDagger()
	{

	}

}
