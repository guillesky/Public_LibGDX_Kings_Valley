package vista2D;

import com.badlogic.gdx.graphics.g2d.Sprite;

import modelo.level.TrapMechanism;

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
		
		
		this.sprite.setPosition(this.trapMech.getX(), this.trapMech.getY());
		
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
