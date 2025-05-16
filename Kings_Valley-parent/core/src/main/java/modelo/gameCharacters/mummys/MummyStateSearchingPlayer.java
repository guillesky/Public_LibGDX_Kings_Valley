package modelo.gameCharacters.mummys;

import modelo.gameCharacters.abstractGameCharacter.GameCharacter;
import modelo.gameCharacters.player.Player;
import util.Config;

public class MummyStateSearchingPlayer extends MummyStateWalking
{
	public MummyStateSearchingPlayer(Mummy mummy, Player player)
	{
		super(mummy, player);

	}

	@Override
	public void update(float deltaTime, Player player)
	{
		this.mummy.timeWhitoutSeePlayer += deltaTime;
		super.update(deltaTime, player);

	}

	@Override
	protected void checkChangeStatus(Player player)
	{
		if (this.mummy.distanceQuadToPlayer(player) < this.mummy.rangeVision)
			this.mummy.mummyState = new MummyStateChasingPlayer(this.mummy, player);

		if (this.mummy.getTimeInState() >= this.timeToChange)
		{
			this.mummy.mummyState = new MummyStateDeciding(this.mummy);
		}
	}

	@Override
	protected void decideEnterStairs(Player player)
	{

		if (this.playerIsUp(player))
			this.mummy.getDirection().y = 1;
		else if (this.playerIsDown(player))
			this.mummy.getDirection().y = -1;
		else
			this.mummy.getDirection().y = 0;
	}

	

}
