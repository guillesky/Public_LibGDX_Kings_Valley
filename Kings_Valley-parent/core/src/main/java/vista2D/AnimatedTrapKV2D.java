package vista2D;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import engine.level.TrapMechanism;

/**
 * Clase que representa graficamente un muro trampa
 * 
 * @author Guillermo Lazzurri
 */
public class AnimatedTrapKV2D implements IGraphicRenderer
{

    /**
     * Sprite que sera dibujado. Se crea a partir del tie de origen del muro trampa
     */
    protected Sprite sprite;
    /**
     * Mecanismo del muro trampa a ser dibujado
     */
    protected TrapMechanism trapMech;
    /**
     * SpriteBatch encargado del dibujado
     */
    private SpriteBatch spriteBatch;

    /**
     * Constructor de clase
     * 
     * @param trapMech    Mecanismo del muro trampa a ser dibujado
     * @param spriteBatch SpriteBatch encargado del dibujado
     */
    public AnimatedTrapKV2D(TrapMechanism trapMech, SpriteBatch spriteBatch)
    {
	this.trapMech = trapMech;
	this.sprite = new Sprite(trapMech.getTile().getTextureRegion());
	this.spriteBatch = spriteBatch;
    }

    /**
     * Actualiza el sprite de acuerdo a los parametros del atributo corespondiente
     * al mecanismo trampa y dibuja el sprite correspondiente a traves del atributo
     * SpriteBatch. El objeto pasado por parametro es ignorado.
     */
    @Override
    public void updateElement()
    {
	this.sprite.setPosition(this.trapMech.getX(), this.trapMech.getY());
	sprite.draw(spriteBatch);
    }

}
