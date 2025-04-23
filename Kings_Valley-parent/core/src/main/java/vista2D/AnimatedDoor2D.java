package vista2D;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import modelo.IGraphicRenderer;
import modelo.level.LevelObject;
import modelo.level.door.Door;
import util.Config;

public class AnimatedDoor2D implements IGraphicRenderer
{
	private Door door;
	private Sprite lever;
	private Sprite doorSingleLeft;
	private Sprite doorSingleRight;
	private Sprite doorPassage;
	protected Animation<TextureRegion> leverAnimation;

	public AnimatedDoor2D(Door door, Texture texturePassage, Texture textureLeft, Texture textureRight,
			Animation<TextureRegion> leverAnimation)
	{
		this.door = door;
		this.leverAnimation = leverAnimation;

		this.doorPassage = new Sprite(texturePassage);
		this.doorSingleLeft = new Sprite(textureLeft);
		this.doorSingleRight = new Sprite(textureRight);
		this.doorPassage.setPosition(this.door.getPassage().x, this.door.getPassage().y);
		this.doorSingleLeft.setPosition(this.door.getPassage().x, this.door.getPassage().y);
		this.doorSingleRight.setPosition(this.door.getPassage().x + Config.getInstance().getLevelTileWidthUnits(),
				this.door.getPassage().y);
		this.lever = new Sprite(leverAnimation.getKeyFrame(0));
		this.lever.setPosition(this.door.getLever().x, this.door.getLever().y);

	}

	@Override
	public void updateElement(Object element)
	{

	}

	public void draw(Batch batch)
	{
		if (this.door.isVisible())
		{
			this.drawBack(batch);
			this.drawFront(batch);
		}

	}

	public void drawBack(Batch batch)
	{
		this.doorPassage.draw(batch);
		this.lever.draw(batch);
	}

	public void drawFront(Batch batch)
	{
		this.doorSingleLeft.draw(batch);
		this.doorSingleRight.draw(batch);
	}

}
