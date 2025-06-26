package modelo.level.dagger;

import java.util.ArrayList;

import modelo.KVEventListener;
import modelo.game.Game;
import modelo.gameCharacters.mummys.Mummy;
import modelo.level.Pyramid;
import util.Config;

public class DaggerStateFalling extends DaggerState
{

	public DaggerStateFalling(Dagger dagger)
	{
		super(dagger,DaggerState.ST_FALLING);
		
	}

	@Override
	public void updateDagger(float deltaTime, Pyramid pyramid, ArrayList<Mummy> mummys)
	{
		dagger.incY(Config.getInstance().getFlyingDaggerSpeedFall() * deltaTime);
		if (pyramid.getCell(dagger.x, dagger.y) != null)
		{
			this.roundY();
			dagger.setDaggerState(new DaggerStateStucked(dagger));
			Game.getInstance().eventFired(KVEventListener.SWORD_STUCK, this.dagger);
			
		}
	}

}
