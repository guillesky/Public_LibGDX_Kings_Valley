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
	public int getCount()
	{
		return count;
	}
	public void setType(int type)
	{
		this.type = type;
	}
	public void setCount(int count)
	{
		this.count = count;
	}
	@Override
	public String toString()
	{
		return "EndPlatform [type=" + type + ", count=" + count + "]";
	}
	
	
	
	
}
