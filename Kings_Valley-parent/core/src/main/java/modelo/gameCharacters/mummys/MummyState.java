package modelo.gameCharacters.mummys;

import modelo.gameCharacters.player.Player;
import util.Config;

public abstract class MummyState
{
	protected Mummy mummy;
	protected float timeToChange;

	protected static float getSecureDistanceToPlayer()
	{
		return (64 * Config.getInstance().getLevelTileHeightUnits()

				* Config.getInstance().getLevelTileHeightUnits());
	}

	public MummyState(Mummy mummy, int state)
	{
		this.mummy = mummy;
		this.mummy.setState(state);
		this.mummy.resetAnimationDelta();
		this.mummy.resetTimeInState();
		this.mummy.resetStress();
	}

	public abstract void update(float deltaTime, Player player);

	protected abstract boolean isDanger();

}
