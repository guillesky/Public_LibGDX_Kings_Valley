package vista2D;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import modelo.level.LevelItem;

public class AnimatedPickedCell extends AnimatedEntity2D
{
    private float stateTime;

    public void resetTime(float delta)
    {
	this.stateTime = delta;
    }

    public AnimatedPickedCell(LevelItem levelItem, Animation<TextureRegion> animation)
    {
	super(levelItem, animation);

    }

    @Override
    public void updateElement(float deltaTime)
    {
	super.updateElement(deltaTime - this.stateTime);
    }

}
