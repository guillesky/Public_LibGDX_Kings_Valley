package modelo.gameCharacters.mummys;

import util.Config;

/**
 * @author Guillermo Lazzurri
 * 
 *         Clase que representa del estado de la momia "En el Limbo"
 */
public class MummyStateLimbus extends MummyState
{
	private boolean mustTeleport = false;

	/**
	 * Contructor de clase llamado cada vez que la momia muere durante los cambios
	 * de estado, llama a this(mummy, Config.getInstance().getMummyTimeInLimbus());
	 * 
	 * @param mummy        Correspondiente al sujeto del patron state
	 * @param mustTeleport true si la momia se debera teletransoportar, flase en
	 *                     caso contrario
	 */
	public MummyStateLimbus(Mummy mummy, boolean mustTeleport)
	{
		this(mummy, Config.getInstance().getMummyTimeInLimbus());
		this.mustTeleport = mustTeleport;
		this.mummy.resetStress();
	}

	/**
	 * Constructor de clase llamado la primera vez que se crea la momia. Estado por
	 * defecto. Llama a super(mummy, Mummy.ST_LIMBUS);
	 * 
	 * @param mummy        Correspondiente al sujeto del patron state
	 * @param timeToChange Indica cuanto tiempo debe estar la momia en el limbo
	 *                     (esto es necesario porque las momias estaran mas tiempo
	 *                     en el limbo si mueren durante el juego que en su primer
	 *                     aparacion)
	 */
	public MummyStateLimbus(Mummy mummy, float timeToChange)
	{
		super(mummy, Mummy.ST_LIMBUS);
		this.timeToChange = timeToChange;

	}

	/**
	 * Al pasar el tiempo hasta his.timeToChange, se cambiara al estado new
	 * MummyStateAppearing(this.mummy);
	 */
	@Override
	public void update(float deltaTime)

	{
		if (this.mummy.getTimeInState() >= this.timeToChange)
		{
			if (this.mustTeleport)
				this.mummy.teleport();
			this.mummy.mummyState = new MummyStateAppearing(this.mummy);
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
