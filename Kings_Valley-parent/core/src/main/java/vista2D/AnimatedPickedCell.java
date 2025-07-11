package vista2D;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import modelo.level.LevelObject;

public class AnimatedPickedCell extends AnimatedEntity2D
{
    private float stateTime;

    public void resetTime(float delta)
    {
	this.stateTime = delta;
    }

    public AnimatedPickedCell(LevelObject levelObject, Animation<TextureRegion> animation)
    {
	super(levelObject, animation);

    }

    @Override
    public void updateElement(float deltaTime)
    {
	if (deltaTime - this.stateTime > 0)
	    super.updateElement(deltaTime - this.stateTime);

    }

}
