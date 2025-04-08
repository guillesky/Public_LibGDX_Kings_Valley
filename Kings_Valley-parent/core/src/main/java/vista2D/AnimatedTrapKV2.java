package vista2D;

import com.badlogic.gdx.graphics.g2d.Sprite;

import modelo.IGraphicRenderer;
import modelo.level.TrapMechanism;
import util.Config;

public class AnimatedTrapKV2 implements IGraphicRenderer
{

	protected Sprite sprite;
	protected TrapMechanism trapMech;

	public AnimatedTrapKV2(TrapMechanism trapMech)
	{
		this.trapMech = trapMech;
		this.sprite = new Sprite(trapMech.getTile().getTextureRegion());
	}

	@Override
	public void updateElement(Object element)
	{
		float delta = this.trapMech.getTime();

		float x = this.trapMech.getX() * Config.getInstance().getLevelObjectWidth();
		float y = (this.trapMech.getY() - delta) * Config.getInstance().getLevelObjectHeight();
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
