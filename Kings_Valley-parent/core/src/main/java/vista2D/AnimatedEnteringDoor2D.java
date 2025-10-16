package vista2D;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import engine.game.Game;
import engine.level.door.Door;

/**
 * Dibuja la puerta durante la entrada y salida del nivel.
 * 
 * @author Guillermo Lazzurri
 */
public class AnimatedEnteringDoor2D extends AbstractAnimatedDoor2D
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
    public AnimatedEnteringDoor2D(Door door, Texture texturePassage, Texture textureLeft, Texture textureRight,
	    Animation<TextureRegion> leverAnimation)
    {
	super(door, texturePassage, textureLeft, textureRight, leverAnimation);

    }

    @Override
    public void updateElement()
    {
	float deltaTime = Game.getInstance().getDelta();

	if (Game.getInstance().getState() == Game.ST_GAME_ENTERING
		&& deltaTime > Game.getInstance().getInterfaz().getTimeToEnterLevel() / 2)
	{
	    deltaTime = Game.getInstance().getInterfaz().getTimeToEnterLevel() - deltaTime;
	} else if (Game.getInstance().getState() == Game.ST_GAME_EXITING)
	    deltaTime = Game.getInstance().getInterfaz().getTimeToExitLevel() - deltaTime;

	this.animateElements(deltaTime);

    }

}
