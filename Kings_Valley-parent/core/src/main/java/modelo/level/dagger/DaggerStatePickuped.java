package modelo.level.dagger;

import java.util.ArrayList;

import modelo.gameCharacters.mummys.Mummy;
import modelo.level.Pyramid;

public class DaggerStatePickuped extends DaggerState
{

	public DaggerStatePickuped(Dagger dagger)
	{
		super(dagger, DaggerState.ST_PICKUPED);

	}

	@Override
	public void updateDagger(float deltaTime, Pyramid pyramid, ArrayList<Mummy> mummys)
	{
	}

	@Override
	protected void throwHorizontal()
	{
		this.dagger.setDaggerState(new DaggerStateThrowingHorizontal(this.dagger));

	}

	@Override
	protected void hasPickuped()
	{
		// TODO Auto-generated method stub

	}

	@Override
	protected void throwVertical()
	{
		this.dagger.setDaggerState(new DaggerStateThrowingVertical(this.dagger));

	}

}
