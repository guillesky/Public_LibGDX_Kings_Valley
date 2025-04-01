package vista2D;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import modelo.LevelItem;

public class AnimatedEntity2D
{
	protected LevelItem levelItem;
	protected Animation<TextureRegion> animation;

	protected Sprite sprite;

	public AnimatedEntity2D(LevelItem levelItem, Animation<TextureRegion> animation)
	{
		this.animation = animation;
		this.sprite = new Sprite(animation.getKeyFrame(0));

		this.levelItem = levelItem;
	}

	public void updateElement(float deltaTime)
	{
		
			sprite.setRegion(animation.getKeyFrame(deltaTime, true));
			float x = this.levelItem.getX() + (this.levelItem.getWidth() - this.sprite.getWidth()) / 2;
			float y = this.levelItem.getY();
			this.sprite.setPosition(x, y);
		
	}

	public void render(SpriteBatch batch)
	{
		
			sprite.draw(batch);
	}

	public LevelItem getLevelItem()
	{
		return levelItem;
	}

}
