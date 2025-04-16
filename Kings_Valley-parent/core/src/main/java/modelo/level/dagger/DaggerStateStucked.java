package modelo.level.dagger;

import java.util.ArrayList;

import modelo.gameCharacters.mummys.Mummy;
import modelo.level.Pyramid;

public class DaggerStateStucked extends DaggerState
{

	public DaggerStateStucked(Dagger dagger)
	{
		super(dagger,DaggerState.ST_STUCKED);
		

	}

	@Override
	public void updateDagger(float deltaTime, Pyramid pyramid, ArrayList<Mummy> mummys)
	{
	}
}
