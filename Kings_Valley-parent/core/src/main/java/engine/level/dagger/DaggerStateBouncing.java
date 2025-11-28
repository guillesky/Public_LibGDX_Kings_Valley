package engine.level.dagger;

import java.util.ArrayList;

import engine.KVEventListener;
import engine.game.Game;
import engine.gameCharacters.mummys.Mummy;
import engine.level.Pyramid;
import util.GameRules;

/**
 * Representa el estado "Rebotando"
 * 
 * @author Guillermo Lazzurri
 */
public class DaggerStateBouncing extends DaggerState
{
	private float y;

	/**
	 * Constructor de clase. Llama a super(dagger);<br>
	 * Dispara el evento Game.getInstance().eventFired(KVEventListener.SWORD_CLASH,
	 * dagger);
	 * 
	 * 
	 * @param dagger Contexto del patron state
	 */
	public DaggerStateBouncing(Dagger dagger)
	{
		super(dagger);

		this.y = dagger.y;
		Game.getInstance().eventFired(KVEventListener.SWORD_CLASH, dagger);
	}

	/**
	 * Luego de rebotar puede pasar a los estados: new DaggerStateStucked(dagger) o
	 * new DaggerStateFalling(dagger). Si se cambia a DaggerStateStucked, se dispara
	 * el metodo Game.getInstance().eventFired(KVEventListener.SWORD_STUCK,
	 * this.dagger);
	 * 
	 */
	@Override
	public void updateDagger(float deltaTime, Pyramid pyramid, ArrayList<Mummy> mummys)
	{
		if (delta < 1)
		{
			this.updateX(-GameRules.getInstance().getPlayerSpeedWalk() / 6 * deltaTime);
			this.dagger.y = this.y + (delta - 1f) * (delta) * -GameRules.getInstance().getPlayerSpeedWalk() / 6;
			this.incDelta(deltaTime);
		} else
		{
			this.roundX();
			if (pyramid.getCell(dagger.x, dagger.y, 0, -1) != null)
			{
				this.roundY();
				{
					dagger.setDaggerState(new DaggerStateStucked(dagger));
					Game.getInstance().eventFired(KVEventListener.SWORD_STUCK, this.dagger);
				}

			} else
				dagger.setDaggerState(new DaggerStateFalling(dagger));
		}
	}

	/**
	 * Se sobreescribe como metodo vacio (no hace nada)
	 */
	@Override
	protected void throwHorizontal()
	{

	}

	/**
	 * Se sobreescribe como metodo vacio (no hace nada)
	 */
	@Override
	protected void hasPickuped()
	{

	}

	/**
	 * Se sobreescribe como metodo vacio (no hace nada)
	 */
	@Override
	protected void throwVertical()
	{

	}

	@Override
	public int getRenderMode()
	{
		
		return  DaggerState.ST_BOUNCING;
	}

}
