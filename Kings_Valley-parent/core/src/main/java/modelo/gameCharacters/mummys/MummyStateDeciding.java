package modelo.gameCharacters.mummys;

import com.badlogic.gdx.math.Vector2;

import modelo.level.LevelObject;
import modelo.level.Stair;

public class MummyStateDeciding extends MummyState
{

	private static final int NONE = 0;
	private static final int RIGHT = 1;
	private static final int LEFT = -1;

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
			this.changeStatus();
		}
		this.mummy.move(new Vector2(), false, deltaTime);

	}

	private void changeStatus()
	{
		if (this.mummy.player.y > this.mummy.y)// player esta arriba
		{
			Stair stair=this.mummy.getNearStair(true);
			LevelObject footStair = stair.getDownStair();
			if (footStair != null)
			{
				this.mummy.mummyState = new MummyStateSearchingStair(this.mummy,stair,1);
			} else
			{
				int direction=this.searchEndPlatform(Mummy.END_STEP);
				if(direction==NONE)
				direction=	this.searchEndPlatform(Mummy.END_CLIFF);
				if(direction==NONE)
					this.mummy.die(true); //Muere por estar encerrada
				else this.mummy.mummyState=new MummyStateWalking(this.mummy,direction);
			}

		} else if (this.mummy.player.y < this.mummy.y)// player esta abajo
		{
			Stair stair=this.mummy.getNearStair(false);
			
			LevelObject footStair = stair.getUpStair();
			
			if (stair != null)
			{
				this.mummy.mummyState = new MummyStateSearchingStair(this.mummy, stair,-1);
			}
			else 
			{
				int direccion=this.searchEndPlatform(Mummy.END_CLIFF);
				if(direccion==NONE)
				direccion=	this.searchEndPlatform(Mummy.END_STEP);
				if(direccion==NONE)
					this.mummy.die(true); //Muere por estar encerrada
				else this.mummy.mummyState=new MummyStateWalking(this.mummy,direccion);
			
				
			}
			
			
		} else
		{
			this.mummy.mummyState=new MummyStateWalking(this.mummy,0);
			
		} // player esta al mismo nivel
		

	}

	@Override
	protected boolean isDanger()
	{
		return true;
	}

	private int searchEndPlatform(int typeEnd)
	{
		int r = NONE;
		int[] endToRight = this.mummy.endPlatform(true);
		int[] endToLeft = this.mummy.endPlatform(false);
		if (endToRight[0] == typeEnd || endToLeft[0] == typeEnd)
		{
			if (endToRight[0] != typeEnd)
				r = LEFT;
			else if (endToLeft[0] != typeEnd)
				r = RIGHT;
			else
			{
				if (endToRight[1] < endToLeft[1])
					r = RIGHT;
				else
					r = LEFT;
			}
		}

		return r;
	}

}
