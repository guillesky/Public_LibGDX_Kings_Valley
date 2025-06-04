package modelo.gameCharacters.mummys;

import java.util.ArrayList;
import java.util.Iterator;

import modelo.level.LevelObject;
import modelo.level.Pyramid;
import modelo.level.Stair;
import util.Config;

public abstract class MummyState extends Lolo
{
	protected static final int NONE = 0;
	protected static final int RIGHT = 1;
	protected static final int LEFT = -1;

	protected static final int PLAYER_IS_SOME_LEVEL = 0;
	protected static final int PLAYER_IS_UP = 1;
	protected static final int PLAYER_IS_DOWN = -1;

	protected float timeToChange;

	public MummyState(Mummy mummy, int state)
	{
		super(mummy);
		mummy.setState(state);
		mummy.resetAnimationDelta();
		mummy.resetTimeInState();

	}

	public abstract void update(float deltaTime);

	@Override
	protected void doInBorderCliff()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doInCrashToWallOrGiratory(int crashStatus, int type)
	{
		// TODO Auto-generated method stub
		
	}

	protected abstract boolean isDanger();

	protected abstract void die(boolean mustTeleport);
}
