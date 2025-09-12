package modelo.gameCharacters.mummys;

/**
 * @author Guillermo Lazzurri
 * 
 * Representa el final de una plataforma. Se usa para que la momia tome decisiones al respecto
 */
public class EndPlatform
{
	/**
	 * Representa una plataforma bloqueada (por pared o giratoria)
	 */
	public static final int END_BLOCK = 0;
	/**
	 * Representa una cornisa por la que se puede caer
	 */
	public static final int END_CLIFF = 1;
	/**
	 * Representa un escalon que se podria saltar
	 */
	public static final int END_STEP = 2;
	
	private int type;
	private int count;
	/**
	 * Constructo de clase, indicara el tipo de final de plataforma, y a que distancia de la momia se encuentra el final.
	 * @param type Indica el tipo de final de plataforma. Puede tomar unicamente los valores END_BLOCK; END_CLIFF; END_STEP 
	 * @param count Indica la distancia en tiles desde la momia al fin de plataforma
	 */
	public EndPlatform(int type, int count)
	{
		
		this.type = type;
		this.count = count;
	}
	/**
	 * @return tipo de plataforma
	 */
	protected int getType()
	{
		return type;
	}
	/**
	 * @return distancia en tiles
	 */
	public int getCount()
	{
		return count;
	}
	/**
	 * @param type tipo de plataforma
	 */
	public void setType(int type)
	{
		this.type = type;
	}
	/**
	 * @param count distancia en tiles
	 */
	public void setCount(int count)
	{
		this.count = count;
	}
	/**
	 *Representacion como String (para debug)
	 */
	@Override
	public String toString()
	{
		return "EndPlatform [type=" + type + ", count=" + count + "]";
	}
	
	
	
	
}
