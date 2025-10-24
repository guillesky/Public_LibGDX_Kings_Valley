package engine.level.dagger;

import java.util.ArrayList;

import engine.KVEventListener;
import engine.game.Game;
import engine.gameCharacters.mummys.Mummy;
import engine.level.Pyramid;
import util.GameRules;

/**
 * Representa el estado "Lanzada hacia arriba"
 * 
 * @author Guillermo Lazzurri
 */
public class DaggerStateThrowingVertical extends DaggerState
{
	private float originalY;

	private float x;
	private float y;

	/**
	 * Constructor de clase. Llama a super(dagger, DaggerState.ST_THROWING_UP);
	 * 
	 * @param dagger Contexto del patron state
	 */
	public DaggerStateThrowingVertical(Dagger dagger)
	{
		super(dagger, DaggerState.ST_THROWING_UP);
		this.originalY = dagger.y;
		this.roundX();
		this.roundY();
		this.y = dagger.y;
		this.x = dagger.x;
		this.y += GameRules.getInstance().getLevelTileHeightUnits();
		if (dagger.isRight())
			this.x -= GameRules.getInstance().getLevelTileWidthUnits();
		else
			this.x += GameRules.getInstance().getLevelTileWidthUnits();
	}

	/**
	 * Cuando llega a su destino cambia el estado a dagger.setDaggerState(new
	 * DaggerStateStucked(dagger)); y dispara el evento
	 * Game.getInstance().eventFired(KVEventListener.SWORD_STUCK, this.dagger);
	 * 
	 */
	@Override
	public void updateDagger(float deltaTime, Pyramid pyramid, ArrayList<Mummy> mummys)
	{
		if (delta < 1)
		{
			this.updateX(-GameRules.getInstance().getFlyingDaggerSpeed() / 20 * deltaTime);
			this.dagger.y = this.originalY
					+ (delta - 1f) * (delta) * -GameRules.getInstance().getFlyingDaggerSpeed() / 3f;
			this.incDelta(deltaTime);
		} else
		{
			dagger.x = this.x;
			dagger.y = this.y;

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
