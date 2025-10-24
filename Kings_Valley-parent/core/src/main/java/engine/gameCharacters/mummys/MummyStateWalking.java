package engine.gameCharacters.mummys;

import java.util.Collection;
import java.util.Iterator;

import com.badlogic.gdx.math.Rectangle;

import engine.game.Game;
import engine.gameCharacters.abstractGameCharacter.GameCharacter;
import engine.level.LevelObject;
import engine.level.Pyramid;
import util.GameRules;
import util.ProbabilisticSelector;

/**
 * Clase que representa del estado de la momia "Caminando"
 * 
 * @author Guillermo Lazzurri
 */
public class MummyStateWalking extends MummyState
{
    /**
     * Indica el estado al que debe pasar
     */
    protected int nextState;

    protected PlatformAnalysisResult platformAnalysisResult;
    private boolean doJump = false;
    private boolean mustDecide = true;

    private static final int DO_JUMP = 100;
    private static final int DO_FALL = 101;
    private static final int DO_BOUNCE = 102;
    private static final int ST_ON_AIR = 1000;

    /**
     * indica donde esta el player con respecto a la momia. Puede tomar los
     * valores:<br>
     * MummyState.PLAYER_IS_UP; <br>
     * MummyState.PLAYER_IS_DOWN;<br>
     * MummyState.PLAYER_IS_SOME_LEVEL
     */
    protected int whereIsPlayer;

    /**
     * Constructor de clase. LLama a super(mummy, GameCharacter.ST_WALKING);
     * 
     * @param mummy         Correspondiente al sujeto del patron state
     * @param directionX    indica la direccion hacia donde debe caminar la momia.
     *                      Puede tomar los valores: MummyState.NONE;
     *                      MummyState.RIGHT o MummyState.LEFT
     * @param whereIsPlayer indica donde esta el player con respecto a la momia.
     *                      Puede tomar los valores: MummyState.PLAYER_IS_UP;
     *                      MummyState.PLAYER_IS_DOWN;
     *                      MummyState.PLAYER_IS_SOME_LEVEL
     */
    public MummyStateWalking(Mummy mummy, PlatformAnalysisResult platformAnalysisResult, int directionX,
	    int whereIsPlayer)
    {
	super(mummy, GameCharacter.ST_WALKING);
	mummy.resetTimeInState();
	this.whereIsPlayer = whereIsPlayer;
	this.platformAnalysisResult = platformAnalysisResult;
	this.mummy.getDirection().x = directionX;
	this.timeToChange = this.mummy.getTimeToDecide();
	this.nextState = Mummy.ST_NO_CHANGE;

    }

    /**
     * Constructor de clase. Es Llamado cuando la momia pasa a estado caminata luego
     * de haber saltado o caido, debe recalcular el platformAnalysisResult y no debe
     * resetear el tiempo en el estado actual
     * 
     * @param mummy Correspondiente al sujeto del patron state
     */
    public MummyStateWalking(Mummy mummy)
    {
	super(mummy, GameCharacter.ST_WALKING);
	this.whereIsPlayer = this.mummy.whereIsPlayer();
	this.platformAnalysisResult = new PlatformAnalysisResult(this.mummy);
	this.timeToChange = this.mummy.getTimeToDecide();
	this.nextState = Mummy.ST_NO_CHANGE;

    }

    /**
     * Dedice la direccion de la momia de acuerdo a la posicion relativa del player
     */
    protected void setDirection()
    {
	if (mummy.getX() < this.mummy.player.getX())
	    this.mummy.getDirection().x = 1;
	else
	    this.mummy.getDirection().x = -1;

    }

    @Override
    public void update(float deltaTime)
    {

	if (this.mummy.getState() == GameCharacter.ST_WALKING || this.mummy.getState() == GameCharacter.ST_IDDLE)

	{
	    if (!this.mummy.isInStair())
	    {
		if (this.mummy.getTimeInState() >= this.timeToChange)
		    this.nextState = Mummy.ST_IDDLE;

		if (this.mustDecide)
		    this.checkEndOfPlataform();

		if (this.mummy.getStressLevel() >= 9)

		    this.nextState = Mummy.ST_DYING;

		if (this.mummy.getStressLevel() > 0)
		    this.mummy.calmStress(deltaTime / 6);
	    }
	    this.mummy.move(this.mummy.getDirection(), doJump, deltaTime);
	    this.doJump = false; //Si esta vez decidio saltar, no deberia volver a saltar el proximo ciclo

	} else
	{
	    this.nextState = ST_ON_AIR;
	}
	this.checkChangeStatus();

    }

    /**
     * Retorna true
     */
    @Override
    protected boolean isDanger()
    {
	return true;
    }

    /**
     * Si pasa el tiempo correspondiente a this.timeToChange, entonces el estado
     * cambia a new MummyStateDeciding(this.mummy)
     */
    private void checkChangeStatus()
    {
	if (this.nextState != Mummy.ST_NO_CHANGE)
	    switch (this.nextState)
	    {
	    case Mummy.ST_IDDLE:
		this.mummy.mummyState = new MummyStateDeciding(this.mummy);
		break;
	    case Mummy.ST_DYING:
		this.die(true);
		break;
	    case ST_ON_AIR:
		this.mummy.mummyState = new MummyStateOnAir(this.mummy);
		break;
	    }

    }

    /**
     * Llamado para que la momia rebote contra la pared e incrememnte su nivel de stress (si es mu alto la momia muere)
     */
    private void bounces()
    {
	this.mummy.getDirection().x *= -1;
	this.mummy.stressing();
    }

    /**
     * Cambia el estado a new MummyStateDying(this.mummy, mustTeleport);
     */
    @Override
    protected void die(boolean mustTeleport)
    {

	this.mummy.mummyState = new MummyStateDying(this.mummy, mustTeleport);

    }

    /**
     * Chequea si llega al final de la plataforma y actua en consecuencia. Puede
     * llamar a this.doInCrashToWallOrGiratory, o a this.doInBorderCliff()
     */
    private void checkEndOfPlataform()
    {
	if (this.mummy.isColision(this.platformAnalysisResult.getEndPlatformLeft().getRectangle())
		&& this.mummy.getDirection().x == MummyState.LEFT)
	    this.analizeEndPlatform(this.platformAnalysisResult.getEndPlatformLeft());
	else if (this.mummy.isColision(this.platformAnalysisResult.getEndPlatformRight().getRectangle())
		&& this.mummy.getDirection().x == MummyState.RIGHT)
	    this.analizeEndPlatform(this.platformAnalysisResult.getEndPlatformRight());

    }

    private void analizeEndPlatform(EndPlatform endPlatform)
    {

	if (endPlatform.getType() == EndPlatform.END_BLOCK) // Si hay un bloqueo
	    this.bounces(); // Hay que rebotar (no queda otra)
	else
	{
	    ProbabilisticSelector selector = new ProbabilisticSelector(Game.random);

	    boolean canJump = this.mummy.canJump();
	    selector.add(0.1, DO_BOUNCE); // agrega la posibilidad de rebotar
	    if (endPlatform.getType() == EndPlatform.END_STEP) // Si es un bloque escalon, es decir se puede saltar
	    {
		if (canJump) // Si la momia puede saltar
		    selector.add(0.5, DO_JUMP); // agrega la posibilidad de saltar

	    } else // La unica opcion que queda es que sea una cornisa
	    {
		if (whereIsPlayer == Mummy.PLAYER_IS_DOWN)
		{
		    if (canJump) // Si la momia puede saltar
			selector.add(0.25, DO_JUMP); // agrega la posibilidad de saltar
		    selector.add(0.5, DO_FALL);
		} else
		{
		    if (canJump) // Si la momia puede saltar
			selector.add(0.5, DO_JUMP); // agrega la posibilidad de saltar
		    selector.add(0.25, DO_FALL);
		}

	    }
	    int valor = (int) selector.getValue();
	    switch (valor)
	    {
	    case DO_JUMP:
		this.doJump = true;
		break;
	    case DO_BOUNCE:
		this.bounces();
		break;
	    case DO_FALL:
		this.mustDecide = false; // Si la momia decide caer, no debe volver a detectar la colision con el borde
					 // porque podria tomar decision, lo cual disminuirira las probabilidades de
					 // caer de forma exponencial
		break;

	    }
	}

    }
}
