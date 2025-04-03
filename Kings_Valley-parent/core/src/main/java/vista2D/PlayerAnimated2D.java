package vista2D;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import modelo.gameCharacters.GameCharacter;
import modelo.gameCharacters.player.Player;
import util.Constantes;

public class PlayerAnimated2D extends GameCharacterAnimated2D
{
    protected Animation<TextureRegion>[] animation_Picker;
    protected Animation<TextureRegion>[] animation_Dagger;
    protected Animation<TextureRegion>[] animation_Nothing;
    private int item;

    public PlayerAnimated2D(GameCharacter character, Animation<TextureRegion>[] animation,
	    Animation<TextureRegion>[] animation_Picker, Animation<TextureRegion>[] animation_Dagger)
    {
	super(character, animation);
	this.animation_Dagger = animation_Dagger;
	this.animation_Picker = animation_Picker;
	this.animation_Nothing = animation;

	Player p = (Player) character;
	this.item = p.getItem();
    }

    @Override
    public void updateElement(float deltaTime)
    {
	Player player = (Player) this.levelItem;
	if (player.getItem() != this.item)
	{
	    this.item = player.getItem();
	    this.changeAnimations();

	}
	if(player.getState()==Player.ST_PICKING)
		this.animation=this.animation_Picker[TileMapGrafica2D.PICKING];
	super.updateElement(deltaTime);
    }

    private void changeAnimations()
    {
	switch (this.item)
	{
	case Constantes.It_none:
	    this.changeArrayAnimation(this.animation_Nothing);

	    break;
	case Constantes.It_picker:
	    this.changeArrayAnimation(this.animation_Picker);

	    break;
	case Constantes.It_dagger:
	    this.changeArrayAnimation(this.animation_Dagger);

	    break;

	}

    }

    private void changeArrayAnimation(Animation<TextureRegion>[] arrayAnimation)
    {
	this.characterAnimationDeath = arrayAnimation[TileMapGrafica2D.DEATH];
	this.characterAnimationFall = arrayAnimation[TileMapGrafica2D.FALL];
	this.characterAnimationWalk = arrayAnimation[TileMapGrafica2D.WALK];
	this.characterAnimationStair = arrayAnimation[TileMapGrafica2D.STAIR];
	this.characterAnimationIddle = arrayAnimation[TileMapGrafica2D.IDDLE];
	this.characterAnimationJump = arrayAnimation[TileMapGrafica2D.JUMP];
    }

}
