package modelo.level.dagger;

import java.util.ArrayList;

import modelo.gameCharacters.mummys.Mummy;
import modelo.level.Pyramid;
import util.Config;

public class DaggerStateFalling extends DaggerState
{

	public DaggerStateFalling(Dagger dagger)
	{
		super(dagger);
		dagger.setState(Dagger.ST_FALLING);
	}

	@Override
	public void updateDagger(float deltaTime, Pyramid pyramid, ArrayList<Mummy> mummys)
	{
		dagger.incY(Config.getInstance().getFlyingDaggerSpeedFall() * deltaTime);
		if (pyramid.getCell(dagger.x, dagger.y) != null)
		{
			this.roundY();
			dagger.setDaggerState(new DaggerStateStucked(dagger));
			
		}
	}

}
