package vista2D;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import modelo.level.Dagger;
import modelo.level.LevelObject;

public class AnimatedThrowingDagger2D extends AnimatedEntity2D
{

    public AnimatedThrowingDagger2D(LevelObject levelObject, Animation<TextureRegion> animation)
    {
	super(levelObject, animation);
	
    }

    @Override
    public void updateElement(float deltaTime)
    {
	Dagger dagger = (Dagger) levelObject;
	super.updateElement(deltaTime);
	this.sprite.setFlip(!dagger.isRight(), false);
	
    }

}
