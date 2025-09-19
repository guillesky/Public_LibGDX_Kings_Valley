package modelo.level.dagger;

import java.util.ArrayList;

import modelo.KVEventListener;
import modelo.game.Game;
import modelo.gameCharacters.mummys.Mummy;
import modelo.level.Pyramid;
import util.Config;

/**
 * @author Guillermo Lazzurri
 * 
 *         Representa el estado "cayendo"
 */
public class DaggerStateFalling extends DaggerState
{

	/**
	 * Constructor de clase. Llama a super(dagger, DaggerState.ST_FALLING);
	 * 
	 * @param dagger Corresponde al sujeto del patron state
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
		this.dagger.y += (Config.getInstance().getFlyingDaggerSpeedFall() * deltaTime);

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
