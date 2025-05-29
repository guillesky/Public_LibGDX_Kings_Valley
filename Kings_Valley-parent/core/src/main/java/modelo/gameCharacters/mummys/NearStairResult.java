package modelo.gameCharacters.mummys;

import modelo.level.Stair;

public class NearStairResult
{
private Stair stair;
private int directionX;
public NearStairResult(Stair stair, int directionX)
{
	super();
	this.stair = stair;
	this.directionX = directionX;
}
protected Stair getStair()
{
	return stair;
}
protected int getDirectionX()
{
	return directionX;
}

}
