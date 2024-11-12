package vista2D;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import modelo.IGraphicRenderer;
import modelo.LevelItem;
import modelo.GameCharacter;
import modelo.Player;
import modelo.TrapMechanism;
import util.Config;

public class AnimatedTrapKV2 implements IGraphicRenderer
{

	protected Sprite sprite;
	protected TrapMechanism trapMech;

	public AnimatedTrapKV2(TrapMechanism trapMech)
	{
		this.trapMech = trapMech;
		this.sprite = new Sprite(trapMech.getTile().getTextureRegion());
		System.out.println(trapMech.getTile().getTextureRegion());
	}

	@Override
	public void updateElement(Object element)
	{
		float delta = this.trapMech.getTime();

		float x = this.trapMech.getX() * Config.getInstance().getLevelItemWidth();
		float y = (this.trapMech.getY() - delta) * Config.getInstance().getLevelItemHeight();
		this.sprite.setPosition(x, y);
		
	}

	public Sprite getSprite()
	{
		return sprite;
	}

	public TrapMechanism getTrapMech()
	{
		return trapMech;
	}

}
