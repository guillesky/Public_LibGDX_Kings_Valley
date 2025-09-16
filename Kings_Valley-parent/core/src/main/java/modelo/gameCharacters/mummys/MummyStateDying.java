package modelo.gameCharacters.mummys;

import modelo.KVEventListener;
import modelo.game.Game;
import modelo.gameCharacters.abstractGameCharacter.GameCharacter;
import util.Config;


/**
 * @author Guillermo Lazzurri
 * 
 *         Clase que representa del estado de la momia "Muriendo"
 */
public class MummyStateDying extends MummyState
{
	private boolean mustTeleport;

	/**
	 * Constructor de clase. Llama a super(mummy, GameCharacter.ST_DYING); y dispara
	 * el evento Game.getInstance().eventFired(KVEventListener.MUMMY_DIE, this);
	 * 
	 * @param mummy        Correspondiente al sujeto del patron state
	 * @param mustTeleport true si la momia debera teletransoportarse al reaparecer,
	 *                     false en caso contrario
	 */
	public MummyStateDying(Mummy mummy, boolean mustTeleport)
	{
		super(mummy, GameCharacter.ST_DYING);
		this.mustTeleport = mustTeleport;
		this.timeToChange = Config.getInstance().getMummyTimeDying();
		Game.getInstance().eventFired(KVEventListener.MUMMY_DIE, this);

	}

	/**
	 *Al pasar el tiempo indicado en this.timeToChange, se cambiara al estado new MummyStateLimbus(this.mummy, this.mustTeleport);
	 */
	@Override
	public void update(float deltaTime)
	{

		if (this.mummy.getTimeInState() >= this.timeToChange)
		{
			this.mummy.mummyState = new MummyStateLimbus(this.mummy, this.mustTeleport);
			this.mummy = null;
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
