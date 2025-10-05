package vista2D;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import engine.level.door.Door;
import util.GameRules;

/**
 * @author Guillermo Lazzurri Clase que representa graficamente una puerta de
 *         entrada / salida de la piramide.
 */
public abstract class AbstractAnimatedDoor2D implements IGraphicRenderer
{
	protected Door door;
	private Sprite lever;
	private Sprite doorSingleLeft;
	private Sprite doorSingleRight;
	private Sprite doorPassage;
	protected Animation<TextureRegion> leverAnimation;

	/**
	 * Constructor de clase
	 * 
	 * @param door           Puerta que debe ser representada
	 * @param texturePassage Textura correspondiente al pasaje
	 * @param textureLeft    Textura de la hoja izquierda
	 * @param textureRight   Textura de la hoja derecha
	 * @param leverAnimation Animación de la palanca
	 */
	public AbstractAnimatedDoor2D(Door door, Texture texturePassage, Texture textureLeft, Texture textureRight,
			Animation<TextureRegion> leverAnimation)
	{
		float tileWidth = GameRules.getInstance().getLevelTileWidthUnits();
		this.door = door;
		this.leverAnimation = leverAnimation;

		this.doorPassage = new Sprite(texturePassage);
		this.doorSingleLeft = new Sprite(textureLeft);
		this.doorSingleRight = new Sprite(textureRight);
		this.doorPassage.setPosition(this.door.getPassage().x - tileWidth, this.door.getPassage().y);
		this.doorSingleLeft.setPosition(this.door.getPassage().x - tileWidth, this.door.getPassage().y);
		this.doorSingleRight.setPosition(this.door.getPassage().x, this.door.getPassage().y);
		this.lever = new Sprite(leverAnimation.getKeyFrame(0));
		this.lever.setPosition(this.door.getLever().x, this.door.getLever().y);

	}

	/**
	 * Posiciona las hojas de la puerta de acuerdo al deltaTime pasado por parametro
	 * 
	 * @param deltaTime Indica el tiempo transcurrido desde del inicio de la
	 *                  animacion
	 */
	protected void animateElements(float deltaTime)
	{
		if (deltaTime < 0)
			deltaTime = 0;
		float xLeft;
		float xRight;
		float tileWidth = GameRules.getInstance().getLevelTileWidthUnits();

		xLeft = this.door.getPassage().x - tileWidth * (1 + deltaTime);
		xRight = this.door.getPassage().x + tileWidth * deltaTime;

		this.lever.setRegion(this.leverAnimation.getKeyFrame(deltaTime, false));
		float y = this.doorSingleLeft.getY();
		this.doorSingleLeft.setPosition(xLeft, y);
		this.doorSingleRight.setPosition(xRight, y);
	}

	/**
	 * si la puerta es visible, llama primero a drawBack y luego a drawFront.
	 * 
	 * @param batch El batch encargado del renderizado
	 */
	public void draw(Batch batch)
	{
		if (this.door.isVisible())
		{
			this.drawBack(batch);
			this.drawFront(batch);

		}

	}

	/**
	 * Dibuja la parte trasera de la puerta (el passage y la palanca)
	 * 
	 * @param batch El batch encargado del renderizado
	 */
	protected void drawBack(Batch batch)
	{
		this.doorPassage.draw(batch);
		this.lever.draw(batch);
	}

	/**
	 * Dibuja la parte delantera de la puerta (las hojas)
	 * 
	 * @param batch El batch encargado del renderizado
	 */
	protected void drawFront(Batch batch)
	{
		this.doorSingleLeft.draw(batch);
		this.doorSingleRight.draw(batch);
	}

}
