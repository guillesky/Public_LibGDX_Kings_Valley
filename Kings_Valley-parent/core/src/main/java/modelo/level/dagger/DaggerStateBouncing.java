package modelo.level.dagger;

import java.util.ArrayList;

import modelo.KVEventListener;
import modelo.game.Game;
import modelo.gameCharacters.mummys.Mummy;
import modelo.level.Pyramid;
import util.Config;

public class DaggerStateBouncing extends DaggerState
{
    private float y;

    public DaggerStateBouncing(Dagger dagger)
    {
	super(dagger, DaggerState.ST_BOUNCING);

	this.y = dagger.y;
    }

    @Override
    public void updateDagger(float deltaTime, Pyramid pyramid, ArrayList<Mummy> mummys)
    {
	if (delta < 1)
	{
	    this.incX(-Config.getInstance().getPlayerSpeedWalk() / 6 * deltaTime);
	    this.dagger.y = this.y + (delta - 1f) * (delta) * -Config.getInstance().getPlayerSpeedWalk() / 6;
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

	@Override
	protected void throwHorizontal()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void hasPickuped()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void throwVertical()
	{
		// TODO Auto-generated method stub
		
	}

}
