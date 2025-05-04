package modelo.level.dagger;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;

import modelo.gameCharacters.mummys.Mummy;
import modelo.level.LevelObject;
import modelo.level.Pyramid;
import util.Config;

public class DaggerStateThrowingHorizontal extends DaggerState
{

	public DaggerStateThrowingHorizontal(Dagger dagger)
	{
		super(dagger,DaggerState.ST_THROWING_HORIZONTAL);
		

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

		LevelObject giratory = null;
		Iterator<LevelObject> itgiratorys = pyramid.getGiratories().iterator();
		do
		{
			if (itgiratorys.hasNext())
				giratory = itgiratorys.next();

		} while (itgiratorys.hasNext() && !dagger.isColision(giratory));

		if (dagger.isColision(giratory))
		{
			dagger.setDaggerState(new DaggerStateBouncing(dagger));
		}
	}

}
