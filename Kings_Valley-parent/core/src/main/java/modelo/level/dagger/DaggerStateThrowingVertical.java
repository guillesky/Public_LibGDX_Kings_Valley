package modelo.level.dagger;

import java.util.ArrayList;

import modelo.gameCharacters.mummys.Mummy;
import modelo.level.Pyramid;
import util.Config;

public class DaggerStateThrowingVertical extends DaggerState
{
    private float originalY;

    private float x;
    private float y;

    public DaggerStateThrowingVertical(Dagger dagger)
    {
	super(dagger);
	this.dagger.setState(Dagger.ST_THROWING_UP);
	this.originalY = dagger.y;
	this.roundX();
	this.roundY();
	this.y = dagger.y;
	this.x = dagger.x;
	this.y += Config.getInstance().getLevelTileHeightUnits();
	if (dagger.isRight())
	    this.x -= Config.getInstance().getLevelTileWidthUnits();
	else
	    this.x += Config.getInstance().getLevelTileWidthUnits();
    }

    @Override
    public void updateDagger(float deltaTime, Pyramid pyramid, ArrayList<Mummy> mummys)
    {
	float delta = this.dagger.getDelta();
	if (delta < 1)
	{
	    dagger.incX(-Config.getInstance().getFlyingDaggerSpeed() / 20 * deltaTime);
	    this.dagger.y = this.originalY + (delta - 1f) * (delta) * -Config.getInstance().getFlyingDaggerSpeed() / 3f;
	    dagger.incDelta(deltaTime);
	} else
	{
	    dagger.x = this.x;
	    dagger.y = this.y;

	    dagger.setDaggerState(new DaggerStateStucked(dagger));

	}

    }

}
