package engine.gameCharacters.mummys;

import java.util.Iterator;

import engine.level.LevelObject;
import engine.level.Pyramid;
import util.GameRules;

/**
 * Clase abstracta que representa el estado de la momia (patron state)
 * 
 * @author Guillermo Lazzurri
 */
public abstract class MummyState

{
    
    
    /**
     * Codigo que indica direccion derecha
     */

    protected static final int RIGHT = 1;
    /**
     * Codigo que indica direccion izquierda
     */
    protected static final int LEFT = -1;

  

    /**
     * Codigo indicando que hay un bloqueo por ladrillos
     */
    protected static final int BLOCK_BRICK = 1;
    /**
     * Inica cuanto tiempo debe permanecer en este estado antes de cambiar
     */
    protected float timeToChange;
    /**
     * Corresponde al sujeto del patron state
     */
    protected Mummy mummy;

    /**
     * Contructor de clase
     * 
     * @param mummy Momia a la que pertences el estado (patron state)
     * @param state tipo de estado
     */
    public MummyState(Mummy mummy, int state)
    {
	this.mummy = mummy;
	mummy.setState(state);
	mummy.resetAnimationDelta();
	

    }

    /**
     * Metodo llamado al actualizar la momia
     * 
     * @param deltaTime tiempo transcurrido desde la ultima actualizacion
     */
    public abstract void update(float deltaTime);

    /**
     * Retorna true si la momia es peligrosa, false en caso contrario
     * 
     * @return true si la momia es peligrosa, false en caso contrario
     */
    protected abstract boolean isDanger();

    /**
     * Evento que podria pasar a modo muerto.
     * 
     * @param mustTeleport true si la momia debe teletransportarse al reaparecer,
     *                     false en caso contrarioF
     */
    protected abstract void die(boolean mustTeleport);

    /**
     * Indica el tipo de fin de plataforma que existe de acuerdo a la posicionX y al
     * desplazamiento en x pasado por parametro
     * 
     * @param positionX coordenada X pretendida
     * @param iDeltaX   Desplazamiento en x
     * @return valor entero que representa el tipo de final de plataforma. Puede
     *         tomar los valores: EndPlatform.END_CLIFF; EndPlatform.END_STEP;
     *         EndPlatform.END_BLOCK
     */
    protected int typeEndPlatform(float positionX, int iDeltaX)
    {
	int type = -1;
	Pyramid pyramid = mummy.getPyramid();
	if (pyramid.getCell(positionX, mummy.y, iDeltaX, 0) == null
		&& pyramid.getCell(positionX, mummy.y, iDeltaX, 1) == null
		&& pyramid.getCell(positionX, mummy.y, iDeltaX, -1) == null)
	    type = EndPlatformOLD.END_CLIFF;
	else if ((pyramid.getCell(positionX, mummy.y, iDeltaX, 1) != null
		&& pyramid.getCell(positionX, mummy.y, iDeltaX, 2) == null
		&& pyramid.getCell(positionX, mummy.y, iDeltaX, 3) == null)
		|| (pyramid.getCell(positionX, mummy.y, iDeltaX, 0) != null
			&& pyramid.getCell(positionX, mummy.y, iDeltaX, 1) == null
			&& pyramid.getCell(positionX, mummy.y, iDeltaX, 2) == null))
	    type = EndPlatformOLD.END_STEP;
	else
	    type = EndPlatformOLD.END_BLOCK;
	return type;
    }

}
