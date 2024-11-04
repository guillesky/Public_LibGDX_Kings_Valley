package vista2D;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import modelo.GameCharacter;

public class GameCharacterAnimated2D extends AnimatedEntity2D
{
	protected Animation<TextureRegion> animationIddle;
	protected Animation<TextureRegion> animationWalk;
	protected Animation<TextureRegion> animationStair;
	protected Animation<TextureRegion> animationJump;
	protected Animation<TextureRegion> animationFall;
	protected Animation<TextureRegion> animationDeath;
	
	
	
	
	
	
	public GameCharacterAnimated2D(GameCharacter character,
			Animation<TextureRegion> animationIddle, Animation<TextureRegion> animationWalk,
			Animation<TextureRegion> animationStair, Animation<TextureRegion> animationJump,
			Animation<TextureRegion> animationFall, Animation<TextureRegion> animationDeath)
	{
		super(character,animationIddle);
		this.animationIddle = animationIddle;
		this.animationWalk = animationWalk;
		this.animationStair = animationStair;
		this.animationJump = animationJump;
		this.animationFall = animationFall;
		this.animationDeath = animationDeath;
	}


	@Override
	public void update(float deltaTime)
	{
	    GameCharacter character=(GameCharacter) this.levelItem;
		if (character.getState() == GameCharacter.ST_WALK_RIGHT
				|| character.getState() == GameCharacter.ST_WALK_LEFT)
			this.animation = this.animationWalk;
		else if (character.getState() == GameCharacter.ST_ONSTAIRS_NEGATIVE
				|| character.getState() == GameCharacter.ST_ONSTAIRS_POSITIVE)
			this.animation = this.animationStair;
		else if (character.getState() == GameCharacter.ST_JUMP_TOP)
			this.animation = this.animationJump;
		else if (character.getState() == GameCharacter.ST_FALLING)
			this.animation = this.animationFall;
		else if (character.getState() == GameCharacter.ST_IDDLE)
			this.animation = this.animationIddle;
		super.update(character.getAnimationDelta());
		this.sprite.setFlip(!character.isLookRight(), false);
	}

	public Animation<TextureRegion> getAnimationWalk()
	{
		return animationWalk;
	}

	public void setAnimationWalk(Animation<TextureRegion> animationWalk)
	{
		this.animationWalk = animationWalk;
	}

	public Animation<TextureRegion> getAnimationJump()
	{
		return animationJump;
	}

	public void setAnimationJump(Animation<TextureRegion> animationJump)
	{
		this.animationJump = animationJump;
	}

	public Animation<TextureRegion> getAnimationIddle()
	{
		return animationIddle;
	}

	public void setAnimationIddle(Animation<TextureRegion> animationIddle)
	{
		this.animationIddle = animationIddle;
	}

	public Animation<TextureRegion> getAnimationStair()
	{
		return animationStair;
	}

	public void setAnimationStair(Animation<TextureRegion> animationStair)
	{
		this.animationStair = animationStair;
	}

	public Animation<TextureRegion> getAnimationDeath()
	{
		return animationDeath;
	}

	public void setAnimationDeath(Animation<TextureRegion> animationDeath)
	{
		this.animationDeath = animationDeath;
	}

	public Animation<TextureRegion> getAnimationFall()
	{
		return animationFall;
	}

	public void setAnimationFall(Animation<TextureRegion> animationFall)
	{
		this.animationFall = animationFall;
	}
	
	

}
