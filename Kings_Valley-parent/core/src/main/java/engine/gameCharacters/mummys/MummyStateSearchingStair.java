package engine.gameCharacters.mummys;

import engine.level.LevelObject;
import engine.level.Stair;

/**
 * Clase que representa del estado de la momia "Buscando escalera"
 * 
 * @author Guillermo Lazzurri
 */
public class MummyStateSearchingStair extends MummyStateWalking
{
    private Stair stair;
    private LevelObject beginStair;
    boolean enterToStair = false;

    /**
     * Constructor de clase, llama a super(mummy, nearStairResult.getDirectionX(),
     * whereIsPlayer);
     * 
     * @param mummy                  Contexto del patron state
     * @param platformAnalysisResult Analisis de la plataforma donde estan los
     *                               limites de la misma
     * @param stair                  Indica la escalera mas cercana
     */
    public MummyStateSearchingStair(Mummy mummy, PlatformAnalysisResult platformAnalysisResult, Stair stair)
    {

	super(mummy, platformAnalysisResult, decideDirection(mummy, stair));
	this.stair = stair;

	if (stair.getDownStair().y == this.mummy.getLastFloorCoordinate())
	    this.beginStair = stair.getDownStair();
	else
	    this.beginStair = stair.getUpStair();

    }

    /**
     * Decide la direccion a la cual debe dirigirse la momia de acuerdo a su
     * posicion y a la escalera (si sube o baja)
     * 
     * @param mummy Momia que busca al escalera
     * @param stair Escalera buscada
     * @return la direccion de la direccion a la que se dirigue la momia: RIGHT=1 o LEFT=-1
     */
    private static int decideDirection(Mummy mummy, Stair stair)
    {
	LevelObject beginStair;
	if (stair.getDownStair().y == mummy.getLastFloorCoordinate())
	    beginStair = stair.getDownStair();
	else
	    beginStair = stair.getUpStair();

	int direccion = (int) Math.signum(beginStair.x - mummy.x);
	if (direccion == 0)
	    direccion = RIGHT;
	return direccion;
    }

    /**
     * llama a super.update(deltaTime); y luego decide la direccion de la momia de
     * acuerdo al tipo de escalera y ubicacion del player
     */
    @Override
    public void update(float deltaTime)
    {

	if (!this.mummy.isInStair())
	{
	    if (this.enterToStair)
	    {
		this.enterToStair = false;
		this.platformAnalysisResult = new PlatformAnalysisResult(this.mummy);
	    }
	    if (this.mummy.isFeetColision(this.beginStair))
	    {
		this.enterToStair = true;
		this.mummy.enterStair(stair);
		if (this.beginStair == this.stair.getDownStair())
		    this.mummy.getDirection().x = this.stair.directionUp();
		else
		    this.mummy.getDirection().x = this.stair.directionDown();

	    }

	}
	super.update(deltaTime);

    }

}
