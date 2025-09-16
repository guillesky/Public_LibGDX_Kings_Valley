package modelo.gameCharacters.mummys;

import modelo.KVEventListener;
import modelo.game.Game;
import util.Config;

/**
 * @author Guillermo Lazzurri
 * 
 *         Clase que representa del estado de la momia "Apareciendo"
 */
public class MummyStateAppearing extends MummyState
{
	/**
	 * Constructor de clase. Llama a:<br>
	 * super(mummy, Mummy.ST_APPEARING);<br>
	 * Game.getInstance().eventFired(KVEventListener.MUMMY_APPEAR, this.mummy);
	 * 
	 * @param mummy Correspondiente al sujeto del patron state
	 */
	public MummyStateAppearing(Mummy mummy)
	{
		super(mummy, Mummy.ST_APPEARING);
		this.timeToChange = Config.getInstance().getMummyTimeAppearing();
		Game.getInstance().eventFired(KVEventListener.MUMMY_APPEAR, this.mummy);

	}

	/**
	 * Al pasar el tiempo correspondiente a this.timeToChange, pasa al estado
	 * MummyStateDeciding
	 */
	@Override
	public void update(float deltaTime)
	{

		if (this.mummy.getTimeInState() >= this.timeToChange)
		{

			this.mummy.mummyState = new MummyStateDeciding(this.mummy);

		}

	}

	/**
	 * Retorna false
	 */
	@Override
	protected boolean isDanger()
	{
		return false;
	}

	/**
	 * Se sobreescribe como metodo vacio (no hace nada)
	 */
	@Override
	protected void die(boolean mustTeleport)
	{
	}

	/**
	 * Se sobreescribe como metodo vacio (no hace nada)
	 */
	@Override
	protected void doInBorderCliff()
	{
	}

	/**
	 * Se sobreescribe como metodo vacio (no hace nada)
	 */
	@Override
	public void doInCrashToWallOrGiratory(int crashStatus, int type)
	{
	}

}
