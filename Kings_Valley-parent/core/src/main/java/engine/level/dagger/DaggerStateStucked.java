package engine.level.dagger;

import java.util.ArrayList;

import engine.gameCharacters.mummys.Mummy;
import engine.level.Pyramid;

/**
 * Representa el estado "Clavada en el piso"
 * 
 * @author Guillermo Lazzurri
 */
public class DaggerStateStucked extends DaggerState
{

	/**
	 * COnstructor de clase. Llama a super(dagger);
	 * 
	 * @param dagger Contexto del patron state
	 */
	public DaggerStateStucked(Dagger dagger)
	{
		super(dagger);
	}

	/**
	 * Se sobreescribe como metodo vacio (no hace nada)
	 */
	@Override
	public void updateDagger(float deltaTime, Pyramid pyramid, ArrayList<Mummy> mummys)
	{
	}

	/**
	 * Se sobreescribe como metodo vacio (no hace nada)
	 */
	@Override
	protected void throwHorizontal()
	{

	}

	/**
	 * Cambia el estado a this.dagger.setDaggerState(new
	 * DaggerStatePickuped(this.dagger));
	 */
	@Override
	protected void hasPickuped()
	{
		this.dagger.setDaggerState(new DaggerStatePickuped(this.dagger));

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
		
		return  DaggerState.ST_STUCKED;
	}
}
