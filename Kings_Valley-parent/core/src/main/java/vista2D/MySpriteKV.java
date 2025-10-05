package vista2D;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import engine.level.LevelObject;

/**
 * @author Guillermo Lazzurri Renderiza un Sprite de King Valley. Solo se usa en
 *         tiempo de debug para visualizar elementos invisibles, como comienzos
 *         y finales de escaleras, muros trampa y activadores.
 */
public class MySpriteKV extends Sprite implements IGraphicRenderer
{
	LevelObject levelObject;

	/**
	 * Constructor de clases, llama a super(texture);
	 * @param texture Texture que debe usar el sprite
	 * @param levelObject Objecto del juego asociado
	 */
	public MySpriteKV(Texture texture, LevelObject levelObject)
	{
		super(texture);
		this.levelObject = levelObject;

	}

	/**
	 * Constructor de clases, llama a super(region);
	 * @param region TextureRegion que debe usar el sprite
	 * @param levelObject Objecto del juego asociado
	 */
	public MySpriteKV(TextureRegion region, LevelObject levelObject)
	{
		super(region);
		this.levelObject = levelObject;

	}

	@Override
	public void updateElement(Object element)
	{
		float x = this.levelObject.getX();
		float y = this.levelObject.getY();
		x = this.levelObject.getX() + (this.levelObject.getWidth() - this.getWidth()) / 2;

		this.setPosition(x, y);

	}

	
}
