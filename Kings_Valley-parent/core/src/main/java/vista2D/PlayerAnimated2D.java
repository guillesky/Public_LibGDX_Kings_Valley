package vista2D;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import modelo.gameCharacters.abstractGameCharacter.GameCharacter;
import modelo.gameCharacters.player.Player;
import modelo.level.LevelObject;
import util.Constantes;

public class PlayerAnimated2D extends GameCharacterAnimated2D
{
    protected Animation<TextureRegion>[] animation_Picker;
    protected Animation<TextureRegion>[] animation_Dagger;
    protected Animation<TextureRegion>[] animation_Nothing;
    private LevelObject item = null;

    int specialWidth;
    int specialHeight;
    int normalWidth;
    int normalHeight;
    private boolean specialSize = false;

    public PlayerAnimated2D(GameCharacter character, Animation<TextureRegion>[] animation,
	    Animation<TextureRegion>[] animation_Picker, Animation<TextureRegion>[] animation_Dagger)
    {
	super(character, animation);
	this.animation_Dagger = animation_Dagger;
	this.animation_Picker = animation_Picker;
	this.animation_Nothing = animation;
	this.specialWidth = animation_Dagger[TileMapGrafica2D.THROW_DAGGER].getKeyFrame(0).getRegionWidth();
	this.specialHeight = animation_Dagger[TileMapGrafica2D.THROW_DAGGER].getKeyFrame(0).getRegionHeight();
	this.normalWidth = animation[TileMapGrafica2D.IDDLE].getKeyFrame(0).getRegionWidth();
	this.normalHeight = animation[TileMapGrafica2D.IDDLE].getKeyFrame(0).getRegionHeight();

	Player p = (Player) character;
	this.item = p.getItem();
	
    }

    @Override
    public void updateElement(float deltaTime)
    {
	Player player = (Player) this.levelObject;
	if (player.getItem() != this.item)
	{
	    this.item = player.getItem();
	    this.changeAnimations();

	}
	if (player.getState() == Player.ST_PICKING)

	{
	    this.animation = this.animation_Picker[TileMapGrafica2D.PICKING];
	    this.sprite.setSize(this.specialWidth, this.specialWidth);
	    this.specialSize = true;
	    
	}

	if (player.getState() == Player.ST_THROWING_DAGGER)
	{
	    this.animation = this.animation_Dagger[TileMapGrafica2D.THROW_DAGGER];
	    this.sprite.setSize(this.specialWidth, this.specialHeight);
	    this.specialSize = true;
	   
	}

	if (this.specialSize
		&& (player.getState() != Player.ST_THROWING_DAGGER && player.getState() != Player.ST_PICKING))
	{
	    this.sprite.setSize(this.normalWidth, this.normalHeight);
	    this.specialSize = false;
	   
	}

	super.updateElement(deltaTime);
    }

    private void changeAnimations()
    {
	if (this.item == null)

	    this.changeArrayAnimation(this.animation_Nothing);

	else

	if (this.item.getType() == Constantes.It_picker)

	    this.changeArrayAnimation(this.animation_Picker);
	else
	    this.changeArrayAnimation(this.animation_Dagger);

    }

    private void changeArrayAnimation(Animation<TextureRegion>[] arrayAnimation)
    {
	this.characterAnimationDeath = arrayAnimation[TileMapGrafica2D.DEATH];
	this.characterAnimationFall = arrayAnimation[TileMapGrafica2D.FALL];
	this.characterAnimationWalk = arrayAnimation[TileMapGrafica2D.WALK];

	this.characterAnimationIddle = arrayAnimation[TileMapGrafica2D.IDDLE];
	this.characterAnimationJump = arrayAnimation[TileMapGrafica2D.JUMP];
    }

    
   
}
