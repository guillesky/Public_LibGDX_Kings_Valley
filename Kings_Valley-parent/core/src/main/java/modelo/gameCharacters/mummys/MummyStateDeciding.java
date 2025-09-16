package modelo.gameCharacters.mummys;

import com.badlogic.gdx.math.Vector2;

import modelo.KVEventListener;
import modelo.game.Game;

/**
 * @author Guillermo Lazzurri
 * 
 *         Clase que representa del estado de la momia "Decidiendo"
 */
public class MummyStateDeciding extends MummyState
{

	/**
	 * Constructor de clase, llama a super(mummy, Mummy.ST_IDDLE);
	 * 
	 * @param mummy Correspondiente al sujeto del patron state
	 */
	public MummyStateDeciding(Mummy mummy)
	{
		super(mummy, Mummy.ST_IDDLE);
		this.timeToChange = this.mummy.getTimeDeciding();

		this.update(0);

	}

	/**
	 * Si pasa el tiempo corresponediente a this.timeToChange entonces llama a
	 * this.changeStatus();
	 */
	@Override
	public void update(float deltaTime)
	{
		if (this.mummy.getTimeInState() >= this.timeToChange)
		{
			this.changeStatus();
		}
		this.mummy.move(new Vector2(), false, deltaTime);

	}

	/**
	 * De acuerdo a la posicion del player puede pasar a los estados
	 * MummyStateSearchingStair o MummyStateWalking
	 */
	private void changeStatus()
	{
		if (this.mummy.player.y > this.mummy.y)// player esta arriba
		{
			NearStairResult nearStairResult = this.getNearStair(true);

			if (nearStairResult != null)
			{
				this.mummy.mummyState = new MummyStateSearchingStair(this.mummy, nearStairResult,
						MummyState.PLAYER_IS_UP);
			} else
			{
				int directionX = this.searchEndPlatform(EndPlatform.END_STEP);
				if (directionX == NONE)
					directionX = this.searchEndPlatform(EndPlatform.END_CLIFF);
				this.mummy.mummyState = new MummyStateWalking(this.mummy, directionX, MummyState.PLAYER_IS_UP);
			}

		} else if (this.mummy.player.y < this.mummy.y)// player esta abajo
		{
			NearStairResult nearStairResult = this.getNearStair(false);
			if (nearStairResult != null)
			{
				this.mummy.mummyState = new MummyStateSearchingStair(this.mummy, nearStairResult,
						MummyState.PLAYER_IS_DOWN);

			} else
			{
				int direccion = this.searchEndPlatform(EndPlatform.END_CLIFF);
				if (direccion == NONE)
					direccion = this.searchEndPlatform(EndPlatform.END_STEP);
				this.mummy.mummyState = new MummyStateWalking(this.mummy, direccion, MummyState.PLAYER_IS_DOWN);
			}

		} else
		{
			this.mummy.mummyState = new MummyStateWalking(this.mummy, MummyState.NONE, MummyState.PLAYER_IS_SOME_LEVEL);

		} // player esta al mismo nivel

	}

	/**
	 * Retorna true
	 */
	@Override
	protected boolean isDanger()
	{
		return true;
	}

	/**
	 * Llamado internamente por changeStatus. Indica hacia donde debe ir la momia dependiendo del tipo de fin de plataforma.
	 * @param typeEnd indica el tipo de fin de plataforma, tomara los valores: EndPlatform.END_CLIFF o EndPlatform.END_STEP 
	 * @return valor numerico que tomara los valores NONE, LEFT o RIGHT
	 */
	private int searchEndPlatform(int typeEnd)
	{
		int r = NONE;
		EndPlatform endToRight = this.endPlatform(true);
		EndPlatform endToLeft = this.endPlatform(false);
		if (endToRight.getType() == typeEnd || endToLeft.getType() == typeEnd)
		{
			if (endToRight.getType() != typeEnd)
				r = LEFT;
			else if (endToLeft.getType() != typeEnd)
				r = RIGHT;
			else
			{

				if (this.mummy.x < this.mummy.player.x)

					r = RIGHT;
				else
					r = LEFT;
			}
		}

		return r;
	}

	/**
	 *Llamado cuando la momia muere. Cambia el estado a new MummyStateDying(this.mummy, mustTeleport); Ademas dispara el evento: Game.getInstance().eventFired(KVEventListener.MUMMY_DIE, this); 
	 */
	@Override
	protected void die(boolean mustTeleport)
	{
		this.mummy.mummyState = new MummyStateDying(this.mummy, mustTeleport);
		Game.getInstance().eventFired(KVEventListener.MUMMY_DIE, this);
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
