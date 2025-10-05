package vista2D;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import engine.level.LevelObject;

/**
 * @author Guillermo Lazzurri Clase encargada de dibujar una celda que esta
 *         siendo picada
 */
public class AnimatedPickedCell extends AnimatedEntity2D
{
	private float stateTime;

	/**
	 * Indica el tiempo de inicio de la animacion
	 * 
	 * @param delta Tiempo de inicio de la nimacion
	 */
	public void setInitialTime(float delta)
	{
		this.stateTime = delta;
	}

	/**
	 * Constructor de clase, llama a super(levelObject, animation);
	 * 
	 * @param levelObject Celda que esta siendo picada
	 * @param animation Animacion correspondiente a la celda picada
	 */
	public AnimatedPickedCell(LevelObject levelObject, Animation<TextureRegion> animation)
	{
		super(levelObject, animation);

	}

	@Override
	public void updateElement(float deltaTime)
	{
		if (deltaTime - this.stateTime > 0)
			super.updateElement(deltaTime - this.stateTime);

	}

}
