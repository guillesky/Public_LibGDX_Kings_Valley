package engine.level.dagger;

import java.util.ArrayList;

import engine.gameCharacters.mummys.Mummy;
import engine.level.Pyramid;

/**
 * Representa el estado "portada por el player"
 * 
 * @author Guillermo Lazzurri
 */
public class DaggerStatePickuped extends DaggerState
{

	/**
	 * Constructor de clase. Llama a super(dagger, DaggerState.ST_PICKUPED);
	 * 
	 * @param dagger Contexto del patron state
	 * 
	 */
	public DaggerStatePickuped(Dagger dagger)
	{
		super(dagger, DaggerState.ST_PICKUPED);

	}

	/**
	 * Se sobreescribe como metodo vacio (no hace nada)
	 */
	@Override
	public void updateDagger(float deltaTime, Pyramid pyramid, ArrayList<Mummy> mummys)
	{
	}

	/**
	 * Cambie el estado a this.dagger.setDaggerState(new
	 * DaggerStateThrowingHorizontal(this.dagger));
	 * 
	 */
	@Override
	protected void throwHorizontal()
	{
		this.dagger.setDaggerState(new DaggerStateThrowingHorizontal(this.dagger));
	}

	/**
	 * Se sobreescribe como metodo vacio (no hace nada)
	 */
	@Override
	protected void hasPickuped()
	{

	}

	/**
	 * Cambia el estado a this.dagger.setDaggerState(new
	 * DaggerStateThrowingVertical(this.dagger));
	 * 
	 */
	@Override
	protected void throwVertical()
	{
		this.dagger.setDaggerState(new DaggerStateThrowingVertical(this.dagger));

	}

}
