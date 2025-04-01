package mummys;

public abstract class MummyState
{
protected Mummy mummy;
protected float timeToDecide ;
public MummyState(Mummy mummy,int state)
{
	this.mummy = mummy;
	this.mummy.setState(state);
	this.mummy.resetAnimationDelta();
	
}

public abstract void update(float deltaTime);


}
