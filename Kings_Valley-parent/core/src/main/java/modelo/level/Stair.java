package modelo.level;

public class Stair
{
private LevelObject downStair;
private LevelObject upStair;



public Stair(LevelObject downStair, LevelObject upStair)
{
	this.downStair = downStair;
	this.upStair = upStair;
}


public LevelObject getDownStair()
{
	return downStair;
}
public LevelObject getUpStair()
{
	return upStair;
}

}
