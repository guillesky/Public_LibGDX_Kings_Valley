package vista2D;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import modelo.level.GiratoryMechanism;
import util.Config;

public class AnimatedGiratory2D extends AnimatedEntity2D
{
	protected GiratoryMechanism giratoryMechanism;
	private boolean spinning = false;
	
	public AnimatedGiratory2D(GiratoryMechanism giratoryMechanism, Animation<TextureRegion> animation)
	{
		super(giratoryMechanism.getLevelObject(), animation,Config.getInstance().getLevelTileWidthUnits(),Config.getInstance().getLevelTileHeightUnits());
		this.giratoryMechanism = giratoryMechanism;
		this.setAnimationPlayMode();
		this.sprite = new Sprite(animation.getKeyFrame(0));
	}

	private void  setAnimationPlayMode() 
	{
		if (giratoryMechanism.isRight())
			this.animation.setPlayMode(PlayMode.NORMAL);
		else
			this.animation.setPlayMode(PlayMode.REVERSED);
		
	}
	public void updateElement(float deltaTime)
	{
		if (this.giratoryMechanism.isActive())
		{
			this.spinning = true;
			
		} else
		{
			if (this.spinning)
			{
             this.spinning=false;
             this.setAnimationPlayMode();
			}
			
		}
		super.updateElement(this.giratoryMechanism.getTime());

	}

}
