package modelo.level.dagger;

import java.util.ArrayList;

import modelo.gameCharacters.mummys.Mummy;
import modelo.level.Pyramid;
import util.Config;

public class DaggerStateThrowingVertical extends DaggerState
{
	private float y;

	public DaggerStateThrowingVertical(Dagger dagger)
	{
		super(dagger);
		this.dagger.setState(Dagger.ST_THROWING_UP);
		this.y = dagger.y;
	}

	@Override
	public void updateDagger(float deltaTime, Pyramid pyramid, ArrayList<Mummy> mummys)
	{
		float delta = this.dagger.getDelta();
		if (delta < 1)
		{
			dagger.incX(-Config.getInstance().getFlyingDaggerSpeed() / 24 * deltaTime);
			this.dagger.y = this.y + (delta - 3f) * (delta) * -Config.getInstance().getPlayerSpeedWalk() / 6;
			dagger.incDelta(deltaTime);
		} else
		{
			this.roundX();
			this.roundY();

			dagger.setDaggerState(new DaggerStateStucked(dagger));

		}

	}

}
