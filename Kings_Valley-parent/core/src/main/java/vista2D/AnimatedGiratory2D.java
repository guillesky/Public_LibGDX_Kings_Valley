package vista2D;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;

import engine.level.GiratoryMechanism;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Clase que representa graficamente una puerta giratoria
 * 
 * @author Guillermo Lazzurri
 */
public class AnimatedGiratory2D extends AnimatedEntity2D
{
    /**
     * Mecanismo giratorio asociado quedebera ser dibujado
     */
    protected GiratoryMechanism giratoryMechanism;
    private boolean spinning = false;

    /**
     * Constructor de clase. LLama a super(giratoryMechanism.getLevelObject(),
     * animation);
     * 
     * @param giratoryMechanism Mecanismo giratorio asociado quedebera ser dibujado
     * @param animation         Animacion correspondiente a un mecanismo giratorio
     */
    public AnimatedGiratory2D(GiratoryMechanism giratoryMechanism, Animation<TextureRegion> animation)
    {
	super(giratoryMechanism.getLevelObject(), animation);
	this.giratoryMechanism = giratoryMechanism;
	this.setAnimationPlayMode();
	this.sprite = new Sprite(animation.getKeyFrame(0));
    }

    /**
     * Llmado internamente para invertir la animacion de acuerdo a posicion del
     * mecanismo giratorio
     */
    private void setAnimationPlayMode()
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
		this.spinning = false;
		this.setAnimationPlayMode();
	    }

	}
	super.updateElement(this.giratoryMechanism.getTime());

    }

}
