package engine.gameCharacters.mummys;

import engine.gameCharacters.abstractGameCharacter.GameCharacter;

/**
 * Representa el estado de una momia saltando o cayendo
 * 
 * @author Guillermo Lazzurri
 */
public class MummyStateOnAir extends MummyState
{

    /**
     * Constructor de clase (no resetea el tiempo de la momia en el presente estado,
     * al aterrizar continuara con el contador anterior)
     * 
     * @param mummy Contexto del patron State
     */
    public MummyStateOnAir(Mummy mummy)
    {
	super(mummy);
    }

    /**
     * Si la momima toca el suelo pasa a estado Walking
     */
    @Override
    public void update(float deltaTime)
    {
	this.mummy.move(this.mummy.getDirection(), false, deltaTime);
	if (this.mummy.getState() == GameCharacter.ST_WALKING || this.mummy.getState() == GameCharacter.ST_IDDLE)
	    this.mummy.mummyState = new MummyStateWalking(this.mummy);
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
     * Cambia el estado a new MummyStateDying(this.mummy, mustTeleport);
     */
    @Override
    protected void die(boolean mustTeleport)
    {

	this.mummy.mummyState = new MummyStateDying(this.mummy, mustTeleport);

    }

}
