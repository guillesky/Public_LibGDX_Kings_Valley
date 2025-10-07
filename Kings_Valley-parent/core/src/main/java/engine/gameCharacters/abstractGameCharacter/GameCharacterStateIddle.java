package engine.gameCharacters.abstractGameCharacter;

import com.badlogic.gdx.math.Vector2;

/**
 * @author Guillermo Lazzurri
 * Clase que representa del estado del caracter "quieto en el piso"
 */
public class GameCharacterStateIddle extends GameCharacterStateOnFloor
{

	/**
	 * Constructor de clase que llama a super(gameCharacter, GameCharacter.ST_IDDLE);
	 * 
	 * @param gameCharacter Caracter al cual pertenece el estado
	 */
    public GameCharacterStateIddle(GameCharacter gameCharacter)
    {
	super(gameCharacter, GameCharacter.ST_IDDLE);
	this.gameCharacter.resetAnimationDelta();
	this.gameCharacter.motionVector.y = 0;
    }

    /**
     * Realiza los calculos y eventualemente realiza cambios de estado.
     */
    @Override
    protected void moveFirstStep(Vector2 v, boolean b, float deltaTime)
    {

	if (b)
	{
	    if (v.x != 0)
		this.gameCharacter.motionVector.x = v.x * this.gameCharacter.getSpeedWalk();
	    this.gameCharacter.doAction();

	} else if (v.x != 0)
	    this.gameCharacter.gameCharacterState = new GameCharacterStateWalking(this.gameCharacter);
    }

    /**
	 * Se sobreescribe como metodo vacio (no hace nada)
	 */
    @Override
    protected void moveSecondStep(Vector2 escalado)
    {

    }


}
