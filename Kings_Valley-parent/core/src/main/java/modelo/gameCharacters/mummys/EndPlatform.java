package modelo.gameCharacters.mummys;

public class EndPlatform
{
	public static final int END_BLOCK = 0;
	public static final int END_CLIFF = 1;
	public static final int END_STEP = 2;
	
	private int type;
	private int count;
	public EndPlatform(int type, int count)
	{
		
		this.type = type;
		this.count = count;
	}
	protected int getType()
	{
		return type;
	}
	protected int getCount()
	{
		return count;
	}
	protected void setType(int type)
	{
		this.type = type;
	}
	protected void setCount(int count)
	{
		this.count = count;
	}
	
	
	
	
}
