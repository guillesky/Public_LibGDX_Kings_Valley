package vista2D;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import modelo.level.TrapMechanism;

/**
 * @author Guillermo Lazzurri Clase que representa graficamente un muro trampa
 */
public class AnimatedTrapKV2D implements IGraphicRenderer
{

	protected Sprite sprite;
	protected TrapMechanism trapMech;
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
	 * al mecanismo trampa y dibuja el sprite correspondiente a traves del atributo SpriteBatch. El objeto pasado por parametro es ignorado.
	 */
	@Override
	public void updateElement(Object element)
	{
		this.sprite.setPosition(this.trapMech.getX(), this.trapMech.getY());
		sprite.draw(spriteBatch);
	}

	

}
