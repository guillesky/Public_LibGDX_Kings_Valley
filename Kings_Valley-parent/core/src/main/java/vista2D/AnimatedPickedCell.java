package vista2D;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import modelo.level.LevelObject;
import util.Config;

public class AnimatedPickedCell extends AnimatedEntity2D
{
    private float stateTime;

    public void resetTime(float delta)
    {
	this.stateTime = delta;
    }

    public AnimatedPickedCell(LevelObject levelObject, Animation<TextureRegion> animation)
    {
	super(levelObject, animation,Config.getInstance().getLevelTileWidthUnits(),Config.getInstance().getLevelTileHeightUnits());

    }

    @Override
    public void updateElement(float deltaTime)
    {
	super.updateElement(deltaTime - this.stateTime);

	
    }

}
