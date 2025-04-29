package vista2D;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import modelo.game.Game;
import modelo.level.door.Door;
import util.Config;

public class AnimatedEnteringDoor2D extends AbstractAnimatedDoor2D
{

	public AnimatedEnteringDoor2D(Door door, Texture texturePassage, Texture textureLeft, Texture textureRight,
			Animation<TextureRegion> leverAnimation)
	{
		super(door, texturePassage, textureLeft, textureRight, leverAnimation);

	}

	@Override
	public void updateElement(Object element)
	{
		float deltaTime = Game.getInstance().getDelta();

		if (Game.getInstance().getState() == Game.ST_GAME_ENTERING
				&& deltaTime > Game.getInstance().getTimeToTransicion() / 2)
		{
			deltaTime = Game.getInstance().getTimeToTransicion() - deltaTime;
		} else if (Game.getInstance().getState() == Game.ST_GAME_EXITING)
			deltaTime = Game.getInstance().getTimeToTransicion() / 2 - deltaTime;

		this.animateElements(deltaTime);

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
