package vista2D;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import modelo.IGraphicRenderer;
import modelo.gameCharacters.GameCharacter;
import modelo.gameCharacters.player.Player;
import modelo.level.LevelObject;

public class MySpriteKV extends Sprite implements IGraphicRenderer
{
	LevelObject levelObject;

	public MySpriteKV(Texture texture, LevelObject levelObject)
	{
		super(texture);
		this.levelObject = levelObject;

	}

	public MySpriteKV(TextureRegion region, LevelObject levelObject)
	{
		super(region);
		this.levelObject = levelObject;

	}

	@Override
	public void updateElement(Object element)
	{
		float x = this.levelObject.getX();
		float y = this.levelObject.getY();
		x = this.levelObject.getX() + (this.levelObject.getWidth() - this.getWidth()) / 2;
		
		this.setPosition(x, y);
		

	}

	public LevelObject getLevelObject()
	{
		return levelObject;
	}

}
