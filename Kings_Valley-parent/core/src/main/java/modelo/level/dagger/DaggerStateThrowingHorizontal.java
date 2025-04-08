package modelo.level.dagger;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;

import modelo.gameCharacters.mummys.Mummy;
import modelo.level.Pyramid;
import util.Config;

public class DaggerStateThrowingHorizontal extends DaggerState
{

	public DaggerStateThrowingHorizontal(Dagger dagger)
	{
		super(dagger);
		this.dagger.setState(Dagger.ST_THROWING_HORIZONTAL);

	}

	@Override
	public void updateDagger(float deltaTime, Pyramid pyramid, ArrayList<Mummy> mummys)
	{
		Cell cell = null;
		dagger.incX(Config.getInstance().getFlyingDaggerSpeed() * deltaTime);
		if (dagger.isRight())
			cell = pyramid.getCell(dagger.x + dagger.width, dagger.y);
		else
			cell = pyramid.getCell(dagger.x, dagger.y);
		if (cell != null)
			dagger.setDaggerState(new DaggerStateBouncing(dagger));
		Iterator<Mummy> itMummy = mummys.iterator();
		Mummy mummy = null;
		do
		{
			if (itMummy.hasNext())
				mummy = itMummy.next();

		} while (itMummy.hasNext() && !dagger.isColision(mummy));
		if (mummy.getState() != Mummy.ST_APPEARING && mummy.getState() != Mummy.ST_DYING
				&& mummy.getState() != Mummy.ST_LIMBUS && dagger.isColision(mummy))
		{
			dagger.setDaggerState(new DaggerStateBouncing(dagger));
			mummy.die();
		}

	}

}
