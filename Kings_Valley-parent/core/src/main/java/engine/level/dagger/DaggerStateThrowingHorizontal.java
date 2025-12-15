package engine.level.dagger;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;

import engine.KVEventListener;
import engine.game.Game;
import engine.gameCharacters.mummys.Mummy;
import engine.level.LevelObject;
import engine.level.Pyramid;
import util.GameRules;

/**
 * Representa el estado "Lanzada horizontalmente"
 * 
 * @author Guillermo Lazzurri
 */
public class DaggerStateThrowingHorizontal extends DaggerState
{

	/**
	 * Constructor de clase. Llama a super(dagger);
	 * 
	 * @param dagger Contexto del patron state
	 */
	public DaggerStateThrowingHorizontal(Dagger dagger)
	{
		super(dagger);

	}

	/**
	 * Si la daga golpea la pared cambia el estado a dagger.setDaggerState(new
	 * DaggerStateBouncing(dagger)); <br>
	 * Si la daga golpea una momia, la elimina, cambia el estado a
	 * dagger.setDaggerState(new DaggerStateBouncing(dagger)); y dispara los
	 * eventos: Game.getInstance().eventFired(KVEventListener.MUMMY_KILLED_BY_SWORD,
	 * mummy); y Game.getInstance().eventFired(KVEventListener.SWORD_CLASH_FLESH,
	 * this.dagger);
	 */
	@Override
	public void updateDagger(float deltaTime, Pyramid pyramid, ArrayList<Mummy> mummys)
	{
		Cell cell = null;
		this.updateX(GameRules.getInstance().getFlyingDaggerSpeed() * deltaTime);
		if (dagger.isRight())
			cell = pyramid.getCell(dagger.x + dagger.width, dagger.y);
		else
			cell = pyramid.getCell(dagger.x, dagger.y);
		if (cell != null)
		{
			dagger.setDaggerState(new DaggerStateBouncing(dagger));
			Game.getInstance().eventFired(KVEventListener.SWORD_CLASH, dagger);

		}
		Iterator<Mummy> itMummy = mummys.iterator();
		Mummy mummy = null;
		do
		{
			if (itMummy.hasNext())
				mummy = itMummy.next();

		} while (itMummy.hasNext() && !dagger.isColision(mummy));
		if (mummy.getRenderMode() != Mummy.ST_APPEARING && mummy.getRenderMode() != Mummy.ST_DYING
				&& mummy.getRenderMode() != Mummy.ST_LIMBUS && dagger.isColision(mummy))
		{
			dagger.setDaggerState(new DaggerStateBouncing(dagger));
			Game.getInstance().eventFired(KVEventListener.MUMMY_KILLED_BY_SWORD, mummy);
			Game.getInstance().eventFired(KVEventListener.SWORD_CLASH_FLESH, this.dagger);

			mummy.die(false);

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
		
		return  DaggerState.ST_THROWING_HORIZONTAL;
	}

}
