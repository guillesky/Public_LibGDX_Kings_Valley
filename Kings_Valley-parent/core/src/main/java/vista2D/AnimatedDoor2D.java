package vista2D;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import modelo.level.door.Door;

public class AnimatedDoor2D extends AbstractAnimatedDoor2D
{

	public AnimatedDoor2D(Door door, Texture texturePassage, Texture textureLeft, Texture textureRight,
			Animation<TextureRegion> leverAnimation)
	{
		super(door, texturePassage, textureLeft, textureRight, leverAnimation);

	}

	@Override
	public void updateElement(Object element)
	{
		
		if (this.door.isVisible() &&  (this.door.getState() == Door.CLOSING || this.door.getState() == Door.OPENING))
		{
			float deltaTime = this.door.getTime();
			if (this.door.getState() == Door.CLOSING)
			{
				deltaTime = 1 - deltaTime;
			}
			this.animateElements(deltaTime);
		}
	}

	@Override
	public void draw(Batch batch)
	{
		if (this.door.isVisible())
		{
			this.drawBack(batch);
			this.drawFront(batch);

		}

	}

}
