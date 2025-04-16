package modelo.level.dagger;

import java.util.ArrayList;

import modelo.gameCharacters.mummys.Mummy;
import modelo.level.Pyramid;

public class DaggerStatePickuped extends DaggerState
{

	public DaggerStatePickuped(Dagger dagger)
	{
		super(dagger,DaggerState.ST_PICKUPED);
		
	}

	@Override
	public void updateDagger(float deltaTime, Pyramid pyramid, ArrayList<Mummy> mummys)
	{
		}

}
