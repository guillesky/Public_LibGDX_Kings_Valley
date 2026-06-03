package engine.gameCharacters.abstractGameCharacter;

import com.badlogic.gdx.math.Vector2;

import engine.level.Stair;

/**
 * Clase que representa del estado del caracter "caminando"
 * 
 * @author Guillermo Lazzurri
 */
public class GameCharacterStateWalking extends GameCharacterStateOnFloor
{

    /**
     * Constructor encadenado. Llama a <br>
     * super(gameCharacter, GameCharacter.ST_WALKING); <br>
     * this.gameCharacter.resetAnimationDelta(); <br>
     * 
     * @param gameCharacter Contexto del patron state
     */
    public GameCharacterStateWalking(GameCharacter gameCharacter)
    {
	super(gameCharacter, GameCharacter.ST_WALKING);
	this.gameCharacter.resetAnimationDelta();
    }

    /**
     * Verifica colisiones laterales y puede marcar los siguientes cambios de estado
     */
    @Override
    protected void beforeScaling(Vector2 movementDirection, boolean action, float deltaTime)
    {

	this.gameCharacter.motionVector.x = movementDirection.x * this.gameCharacter.getSpeedWalk();
	if (movementDirection.x != 0)
	    this.gameCharacter.lookRight = movementDirection.x > 0;

	if (action)
	    this.gameCharacter.doAction();
	else
	{

	    if (movementDirection.x == 0)
		this.nextState = GameCharacter.ST_IDDLE;

	    if (movementDirection.y != 0 && movementDirection.x != 0)
		this.checkEnterStair(movementDirection);
	    if (!this.isFloorDown())
		this.nextState = GameCharacter.ST_FALLING;
	}
    }

    /**
     * Verifica si el caracter esta entrando a una escalera. De ser asi, llama a
     * this.gameCharacter.enterStair(stair);
     * 
     * @param movementDirection Indica la direccion pretendida
     */
    protected void checkEnterStair(Vector2 movementDirection)
    {
	Stair stair = null;
	if (movementDirection.y > 0)
	{
	    if (movementDirection.x > 0)
	    {
		stair = this.gameCharacter.checkStairsFeetColision(true, true);

	    } else
	    {
		stair = this.gameCharacter.checkStairsFeetColision(false, true);
	    }
	} else
	{
	    if (movementDirection.x > 0)
	    {
		stair = this.gameCharacter.checkStairsFeetColision(false, false);

	    } else
	    {
		stair = this.gameCharacter.checkStairsFeetColision(true, false);

	    }
	}
	if (stair != null)
	    this.gameCharacter.enterStair(stair);

    }

    /**
     * llama a this.colisionForWalk(escalado);
     */
    @Override
    protected void afterScaling(Vector2 escalado)
    {
	this.colisionForWalk(escalado);
    }

}
