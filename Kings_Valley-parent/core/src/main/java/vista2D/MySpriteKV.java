package vista2D;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import modelo.IGraphicRenderer;
import modelo.LevelItem;
import modelo.GameCharacter;
import modelo.Player;

public class MySpriteKV extends Sprite implements IGraphicRenderer
{
	LevelItem levelItem;

	public MySpriteKV(Texture texture, LevelItem levelItem)
	{
		super(texture);
		this.levelItem = levelItem;

	}

	public MySpriteKV(TextureRegion region, LevelItem levelItem)
	{
		super(region);
		this.levelItem = levelItem;

	}

	@Override
	public void updateElement(Object element)
	{
		float x = this.levelItem.getX();
		float y = this.levelItem.getY();
		x = this.levelItem.getX() + (this.levelItem.getWidth() - this.getWidth()) / 2;
		
		this.setPosition(x, y);
		

	}

	public LevelItem getLevelItem()
	{
		return levelItem;
	}

}
