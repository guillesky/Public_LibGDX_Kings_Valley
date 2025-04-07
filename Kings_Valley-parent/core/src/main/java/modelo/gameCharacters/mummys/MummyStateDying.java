package modelo.gameCharacters.mummys;

import modelo.gameCharacters.GameCharacter;
import modelo.gameCharacters.player.Player;

public class MummyStateDying extends MummyState
{
    public MummyStateDying(Mummy mummy)
    {
	super(mummy, GameCharacter.ST_DYING);

	this.timeToDecide = 1;

    }

    @Override
    public void update(float deltaTime,Player player)
    {
	if (this.mummy.getAnimationDelta() >= this.timeToDecide)
	{

	    this.mummy.mummyState = new MummyStateLimbus(this.mummy);
	    this.mummy = null;
	}

    }

}
