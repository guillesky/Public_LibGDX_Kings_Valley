package vista2D;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import modelo.level.LevelObject;

public class AnimatedEntity2D
{
	protected LevelObject levelObject;
	protected Animation<TextureRegion> animation;

	protected Sprite sprite;

	public AnimatedEntity2D(LevelObject levelObject, Animation<TextureRegion> animation)
	{
		this.animation = animation;
		this.sprite = new Sprite(animation.getKeyFrame(0));

		this.levelObject = levelObject;
	}

	public void updateElement(float deltaTime)
	{
		
			sprite.setRegion(animation.getKeyFrame(deltaTime, true));
			float x = this.levelObject.getX() + (this.levelObject.getWidth() - this.sprite.getWidth()) / 2;
			float y = this.levelObject.getY();
			this.sprite.setPosition(x, y);
		
	}

	public void render(SpriteBatch batch)
	{
		
			sprite.draw(batch);
	}

	public LevelObject getLevelObject()
	{
		return levelObject;
	}

}
