package vista2D;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import modelo.gameCharacters.GameCharacter;
import modelo.gameCharacters.player.Player;

public class GameCharacterAnimated2D extends AnimatedEntity2D
{
	protected Animation<TextureRegion> characterAnimationIddle;
	protected Animation<TextureRegion> characterAnimationWalk;
	protected Animation<TextureRegion> characterAnimationStair;
	protected Animation<TextureRegion> characterAnimationJump;
	protected Animation<TextureRegion> characterAnimationFall;
	protected Animation<TextureRegion> characterAnimationDeath;

	
	
	
	
	
	


	public GameCharacterAnimated2D(GameCharacter character, Animation<TextureRegion>[] animation)
	{
	    super(character,animation[TileMapGrafica2D.IDDLE]);
	    this.characterAnimationIddle = animation[TileMapGrafica2D.IDDLE];
		this.characterAnimationWalk = animation[TileMapGrafica2D.WALK];
		this.characterAnimationStair = animation[TileMapGrafica2D.STAIR];
		this.characterAnimationJump = animation[TileMapGrafica2D.JUMP];
		this.characterAnimationFall = animation[TileMapGrafica2D.FALL];
		this.characterAnimationDeath = animation[TileMapGrafica2D.DEATH];
	}


	@Override
	public void updateElement(float deltaTime)
	{
	    GameCharacter character=(GameCharacter) this.levelItem;
		if (character.getState() == GameCharacter.ST_WALK)
			this.animation = this.characterAnimationWalk;
		else if (character.getState() == GameCharacter.ST_ONSTAIRS_NEGATIVE
				|| character.getState() == GameCharacter.ST_ONSTAIRS_POSITIVE)
			this.animation = this.characterAnimationStair;
		else if (character.getState() == GameCharacter.ST_JUMP_TOP)
			this.animation = this.characterAnimationJump;
		else if (character.getState() == GameCharacter.ST_FALLING)
			this.animation = this.characterAnimationFall;
		else if (character.getState() == GameCharacter.ST_IDDLE)
			this.animation = this.characterAnimationIddle;
		else if (character.getState() == GameCharacter.ST_DYING)
			this.animation = this.characterAnimationDeath;
		super.updateElement(character.getAnimationDelta());
		this.sprite.setFlip(!character.isLookRight(), false);
	}

	public Animation<TextureRegion> getAnimationWalk()
	{
		return characterAnimationWalk;
	}

	public void setAnimationWalk(Animation<TextureRegion> animationWalk)
	{
		this.characterAnimationWalk = animationWalk;
	}

	public Animation<TextureRegion> getAnimationJump()
	{
		return characterAnimationJump;
	}

	public void setAnimationJump(Animation<TextureRegion> animationJump)
	{
		this.characterAnimationJump = animationJump;
	}

	public Animation<TextureRegion> getAnimationIddle()
	{
		return characterAnimationIddle;
	}

	public void setAnimationIddle(Animation<TextureRegion> animationIddle)
	{
		this.characterAnimationIddle = animationIddle;
	}

	public Animation<TextureRegion> getAnimationStair()
	{
		return characterAnimationStair;
	}

	public void setAnimationStair(Animation<TextureRegion> animationStair)
	{
		this.characterAnimationStair = animationStair;
	}

	public Animation<TextureRegion> getAnimationDeath()
	{
		return characterAnimationDeath;
	}

	public void setAnimationDeath(Animation<TextureRegion> animationDeath)
	{
		this.characterAnimationDeath = animationDeath;
	}

	public Animation<TextureRegion> getAnimationFall()
	{
		return characterAnimationFall;
	}

	public void setAnimationFall(Animation<TextureRegion> animationFall)
	{
		this.characterAnimationFall = animationFall;
	}
	
	

}
