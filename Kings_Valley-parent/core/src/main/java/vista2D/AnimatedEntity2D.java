package vista2D;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import engine.level.LevelObject;

/**
 * Clase que representa graficamente una entidad del juego con una animacion
 * asociada
 * 
 * @author Guillermo Lazzurri
 */
public class AnimatedEntity2D
{
    /**
     * Representa la entidad del juego que debe dibujarse
     */
    protected LevelObject levelObject;
    /**
     * Animacion correspondiente a la entidad del juego
     */
    protected Animation<TextureRegion> animation;

    /**
     * Sprite actual a dibujarse. Se actualiza a partir de la animacion
     */
    protected Sprite sprite;

    /**
     * Constructor de clase
     * 
     * @param levelObject Representa la entidad del juego que debe dibujarse
     * @param animation   Animacion correspondiente a la entidad del juego
     */
    public AnimatedEntity2D(LevelObject levelObject, Animation<TextureRegion> animation)
    {
	this.animation = animation;

	this.levelObject = levelObject;

	this.sprite = new Sprite(animation.getKeyFrame(0));

    }

    /**
     * Actualiza la animacion de acuerdo al tiempo de animacion pasado por parametro
     * 
     * @param deltaTime Tiempo de la animacion
     */
    public void updateElement(float deltaTime)
    {

	sprite.setRegion(animation.getKeyFrame(deltaTime));
	float x = this.levelObject.getX() + (this.levelObject.getWidth() - this.sprite.getWidth()) / 2;
	float y = this.levelObject.getY();
	this.sprite.setPosition(x, y);

    }

    /**
     * Dibuja el sprite actual en el batch pasado por parametro
     * 
     * @param batch El batch encargado del renderizado
     */
    public void render(SpriteBatch batch)
    {

	sprite.draw(batch);
    }

    /**
     * @return La entidad del juego asociada
     */
    public LevelObject getLevelObject()
    {
	return levelObject;
    }

}
