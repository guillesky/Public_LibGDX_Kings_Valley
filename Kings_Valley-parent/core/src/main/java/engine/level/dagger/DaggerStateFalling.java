package engine.level.dagger;

import java.util.ArrayList;

import engine.KVEventListener;
import engine.game.Game;
import engine.gameCharacters.mummys.Mummy;
import engine.level.Pyramid;
import util.GameRules;

/**
 * Representa el estado "cayendo"
 * 
 * @author Guillermo Lazzurri
 */
public class DaggerStateFalling extends DaggerState
{

	/**
	 * Constructor de clase. Llama a super(dagger, DaggerState.ST_FALLING);
	 * 
	 * @param dagger Contexto del patron state
	 */
	public DaggerStateFalling(Dagger dagger)
	{
		super(dagger, DaggerState.ST_FALLING);

	}

	/**
	 * Si la daga llega al piso, se cambia al estado dagger.setDaggerState(new
	 * DaggerStateStucked(dagger)); y se dispara el evento
	 * Game.getInstance().eventFired(KVEventListener.SWORD_STUCK, this.dagger);
	 * 
	 */
	@Override
	public void updateDagger(float deltaTime, Pyramid pyramid, ArrayList<Mummy> mummys)
	{
		this.dagger.y += (GameRules.getInstance().getFlyingDaggerSpeedFall() * deltaTime);

		if (pyramid.getCell(dagger.x, dagger.y) != null)
		{
			this.roundY();
			dagger.setDaggerState(new DaggerStateStucked(dagger));
			Game.getInstance().eventFired(KVEventListener.SWORD_STUCK, this.dagger);

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

}
