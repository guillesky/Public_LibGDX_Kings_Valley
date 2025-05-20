package modelo.gameCharacters.mummys;

import com.badlogic.gdx.math.Vector2;

import modelo.gameCharacters.player.Player;

public class MummyStateDeciding extends MummyState
{
	public MummyStateDeciding(Mummy mummy)
	{
		super(mummy, Mummy.ST_IDDLE);
		this.timeToChange = this.mummy.getTimeDeciding();
	this.update(0);

	}

	@Override
	public void update(float deltaTime)
	{
		if (this.mummy.getTimeInState() >= this.timeToChange)
		{
				this.mummy.mummyState = new MummyStateWalking(this.mummy);
		}
		this.mummy.move(new Vector2(), false, deltaTime);
		
	}

	@Override
	protected boolean isDanger()
	{
		return true;
	}

}
