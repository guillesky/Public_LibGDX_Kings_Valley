package modelo.level.dagger;

import java.util.ArrayList;

import modelo.gameCharacters.mummys.Mummy;
import modelo.level.Pyramid;

/**
 * @author Guillermo Lazzurri
 * 
 * Representa el estado "Clavada en el piso"
 */
public class DaggerStateStucked extends DaggerState
{

	/**
	 * COnstructor de clase. Llama a super(dagger,DaggerState.ST_STUCKED);
	 * @param dagger Coresponde al sujeto del patron state
	 */
	public DaggerStateStucked(Dagger dagger)
	{
		super(dagger,DaggerState.ST_STUCKED);
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
	 *Cambia el estado a this.dagger.setDaggerState(new DaggerStatePickuped(this.dagger));
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
}
