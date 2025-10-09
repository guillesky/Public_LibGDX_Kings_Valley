package vista2D;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import engine.level.dagger.Dagger;
import engine.level.dagger.DaggerState;

/**
 * Clase encargada de renderizar Dagas
 * 
 * @author Guillermo Lazzurri
 */
public class AnimatedDagger2D extends AnimatedEntity2D
{
    /**
     * Animacion correspondiente a la daga cuando esta clavada en el piso
     */
    protected Animation<TextureRegion> daggerAnimationStucked;
    /**
     * Animacion correspondiente a la daga cuando esta volando
     */
    protected Animation<TextureRegion> daggerAnimationFlying;
    private int oldState;

    /**
     * Constructor de clase, llama a super(dagger, animationStucked);
     * 
     * @param dagger           Daga a ser renderizada
     * @param animationStucked Animacion correspondiente a la daga cuando esta
     *                         clavada en el piso
     * @param animationFlying  Animacion correspondiente a la daga cuando esta
     *                         volando
     */
    public AnimatedDagger2D(Dagger dagger, Animation<TextureRegion> animationStucked,
	    Animation<TextureRegion> animationFlying)
    {
	super(dagger, animationStucked);
	this.daggerAnimationStucked = animationStucked;
	this.daggerAnimationFlying = animationFlying;
	this.oldState = DaggerState.ST_STUCKED;
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

    /**
     * Llamado internamente cuando la daga cambia de estado<br>
     * if (dagger.getState() == DaggerState.ST_STUCKED) this.animation =
     * this.daggerAnimationStucked;
     * 
     * else this.animation = this.daggerAnimationFlying;
     */
    private void changeAnimation()
    {
	Dagger dagger = (Dagger) this.levelObject;
	this.oldState = dagger.getState();
	if (dagger.getState() == DaggerState.ST_STUCKED)
	    this.animation = this.daggerAnimationStucked;

	else
	    this.animation = this.daggerAnimationFlying;
	this.sprite = new Sprite(animation.getKeyFrame(0));
    }

    @Override
    public void render(SpriteBatch batch)
    {

	Dagger dagger = (Dagger) this.getLevelObject();
	if (dagger.getState() != DaggerState.ST_PICKUPED)
	    super.render(batch);

    }

}
