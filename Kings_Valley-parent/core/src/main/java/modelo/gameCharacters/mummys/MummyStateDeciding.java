package modelo.gameCharacters.mummys;

import com.badlogic.gdx.math.Vector2;

import modelo.gameCharacters.player.Player;

public class MummyStateDeciding extends MummyState
{
	public MummyStateDeciding(Mummy mummy)
	{
		super(mummy, Mummy.ST_IDDLE);
		this.timeToChange = this.mummy.getTimeDeciding();
		;

	}

	@Override
	public void update(float deltaTime, Player player)
	{
		if (this.mummy.getTimeInState() >= this.timeToChange)
		{
			if (this.mummy.distanceQuadToPlayer(player) < this.mummy.rangeVision)
				this.mummy.mummyState = new MummyStateChasingPlayer(this.mummy, player);
			else
				this.mummy.mummyState = new MummyStateSearchingPlayer(this.mummy, player);

		}
		this.mummy.move(new Vector2(), false, deltaTime);
		
	}

	@Override
	protected boolean isDanger()
	{
		return true;
	}

}
