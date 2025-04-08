package vista2D;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import modelo.level.dagger.Dagger;

public class AnimatedDagger2D extends AnimatedEntity2D
{
	protected Animation<TextureRegion> daggerAnimationStucked;
	protected Animation<TextureRegion> daggerAnimationFlying;
	private int oldState;

	public AnimatedDagger2D(Dagger dagger, Animation<TextureRegion> animationStucked,
			Animation<TextureRegion> animationFlying)
	{
		super(dagger, animationStucked);
		this.daggerAnimationStucked = animationStucked;
		this.daggerAnimationFlying = animationFlying;
		this.oldState = Dagger.ST_STUCKED;
	}

	@Override
	public void updateElement(float deltaTime)
	{

		Dagger dagger = (Dagger) this.levelObject;
		if (dagger.getState() != this.oldState)
			this.changeAnimation();

		super.updateElement(deltaTime);
		this.sprite.setFlip(!dagger.isRight(), false);

	}

	private void changeAnimation()
	{
		Dagger dagger = (Dagger) this.levelObject;
		this.oldState = dagger.getState();
		if (dagger.getState() == Dagger.ST_STUCKED)
			this.animation = this.daggerAnimationStucked;

		else
			this.animation = this.daggerAnimationFlying;
		this.sprite = new Sprite(animation.getKeyFrame(0));

	}

	public Animation<TextureRegion> getAnimationFlying()
	{
		return daggerAnimationFlying;
	}

	public void setAnimationFlying(Animation<TextureRegion> animationFlying)
	{
		this.daggerAnimationFlying = animationFlying;
	}

	public Animation<TextureRegion> getAnimationStucked()
	{
		return daggerAnimationStucked;
	}

	public void setAnimationStucked(Animation<TextureRegion> animationStucked)
	{
		this.daggerAnimationStucked = animationStucked;
	}

	@Override
	public void render(SpriteBatch batch)
	{

		Dagger dagger = (Dagger) this.getLevelObject();
		if (dagger.getState() != Dagger.ST_PICKUPED)
			super.render(batch);

	}

}
