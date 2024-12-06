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
	Animation<TextureRegion> animationRightLeft;
	Animation<TextureRegion> animationLeftRight;
	
	public AnimatedGiratory2D(GiratoryMechanism giratoryMechanism, Animation<TextureRegion> animationRightLeft,Animation<TextureRegion> animationLeftRight)
	{
		super(giratoryMechanism.getLevelItem(), animationRightLeft);
		this.giratoryMechanism = giratoryMechanism;
		this.animationRightLeft=animationRightLeft;
		this.animationLeftRight=animationLeftRight;
		this.setAnimationActive();
		this.sprite = new Sprite(animation.getKeyFrame(0));
	}

	private void  setAnimationActive() 
	{
		if (giratoryMechanism.isRight())
			this.animation=this.animationRightLeft;
		else
			this.animation=this.animationLeftRight;
		
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
             this.setAnimationActive();
			}
			
		}
		super.updateElement(this.giratoryMechanism.getTime());

	}

}
