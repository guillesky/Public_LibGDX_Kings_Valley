package vista2D;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import modelo.gameCharacters.abstractGameCharacter.GameCharacter;

/**
 * @author Guillermo Lazzurri Clase que renderiza un GameCharacter (momia o
 *         player)
 */
public class GameCharacterAnimated2D extends AnimatedEntity2D
{
	protected Animation<TextureRegion> characterAnimationIddle;
	protected Animation<TextureRegion> characterAnimationWalk;
	protected Animation<TextureRegion> characterAnimationStair;
	protected Animation<TextureRegion> characterAnimationJump;
	protected Animation<TextureRegion> characterAnimationFall;
	protected Animation<TextureRegion> characterAnimationDeath;

	/**
	 * Constructor de clase, llama a super(character,
	 * animation[TileMapGrafica2D.IDDLE]);
	 * 
	 * @param character GameCharacter a ser dibujado
	 * @param animation array con las animaciones correspondientes a los estados del
	 *                  caracter. Debe contener 5 animaciones correspondientes a<br>
	 *                  TileMapGrafica2D.IDDLE = 0;<br>
	 *                  TileMapGrafica2D.FALL = 1;<br>
	 *                  TileMapGrafica2D.WALK = 2;<br>
	 *                  TileMapGrafica2D.DEATH = 3;<br>
	 *                  TileMapGrafica2D.JUMP = 4;<br>
	 * 
	 */
	public GameCharacterAnimated2D(GameCharacter character, Animation<TextureRegion>[] animation)
	{
		super(character, animation[TileMapGrafica2D.IDDLE]);
		this.characterAnimationIddle = animation[TileMapGrafica2D.IDDLE];
		this.characterAnimationFall = animation[TileMapGrafica2D.FALL];
		this.characterAnimationWalk = animation[TileMapGrafica2D.WALK];

		this.characterAnimationDeath = animation[TileMapGrafica2D.DEATH];
		this.characterAnimationJump = animation[TileMapGrafica2D.JUMP];

	}

	@Override
	public void updateElement(float deltaTime)
	{
		GameCharacter character = (GameCharacter) this.levelObject;
		if (character.getState() == GameCharacter.ST_WALKING)
			this.animation = this.characterAnimationWalk;
		else if (character.getState() == GameCharacter.ST_JUMPING)
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

	

}
