package vista2D;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import modelo.GiratoryMechanism;

public class AnimatedGiratory2D extends AnimatedEntity2D
{
	protected GiratoryMechanism giratoryMechanism;
	private boolean spinning = false;

	public AnimatedGiratory2D(GiratoryMechanism giratoryMechanism, Animation<TextureRegion> animation)
	{
		super(giratoryMechanism.getLevelItem(), animation);
		this.giratoryMechanism = giratoryMechanism;
		this.setPlayMode();
		this.sprite = new Sprite(animation.getKeyFrame(0));
	}

	private void  setPlayMode() 
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
             this.setPlayMode();
			}
			
		}
		super.updateElement(this.giratoryMechanism.getTime());

	}

}
