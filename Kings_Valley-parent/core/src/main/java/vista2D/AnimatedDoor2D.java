package vista2D;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import engine.level.door.Door;

/**
 *
 * Dibuja la puerta del nivel durante el juego.
 * 
 * @author Guillermo Lazzurri
 */
public class AnimatedDoor2D extends AbstractAnimatedDoor2D
{

    /**
     * Constructor de clase. LLama a super(door, texturePassage, textureLeft,
     * textureRight, leverAnimation);
     * 
     * 
     * @param door           Puerta que debe ser representada
     * @param texturePassage Textura correspondiente al pasaje
     * @param textureLeft    Textura de la hoja izquierda
     * @param textureRight   Textura de la hoja derecha
     * @param leverAnimation Animacion de la palanca
     */
    public AnimatedDoor2D(Door door, Texture texturePassage, Texture textureLeft, Texture textureRight,
	    Animation<TextureRegion> leverAnimation)
    {
	super(door, texturePassage, textureLeft, textureRight, leverAnimation);

    }

    @Override
    public void updateElement()
    {

	if (this.door.isVisible() && (this.door.getState() == Door.CLOSING || this.door.getState() == Door.OPENING))
	{
	    float deltaTime = this.door.getTime();
	    if (this.door.getState() == Door.CLOSING)
	    {
		deltaTime = 1 - deltaTime;
	    }
	    this.animateElements(deltaTime);
	}
    }

}
