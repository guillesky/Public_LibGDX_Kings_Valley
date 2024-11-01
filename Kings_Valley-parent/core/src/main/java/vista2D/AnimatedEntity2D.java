package vista2D;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimatedEntity2D
{
	protected Animation<TextureRegion> animation;
	protected float stateTime;
	protected Sprite sprite;

	public AnimatedEntity2D(Animation<TextureRegion> animation)
	{
		this.animation = animation;
		this.sprite = new Sprite(animation.getKeyFrame(0));
		this.stateTime = 0f;
	}

	public void update(float deltaTime)
	{

		sprite.setRegion(animation.getKeyFrame(deltaTime, true));
	}

	public void render(SpriteBatch batch)
	{
		sprite.draw(batch);
	}
}
