package util;

import java.util.Random;
import java.util.TreeMap;

/**
 * Devuelve un resultado al azar a partir de un conjunto de posibles resultados
 * con su corespondiente factor de probabilidad relativo
 * 
 * @author Guillermo Lazzurri
 */
public class ProbabilisticSelector
{
	private TreeMap<Double, Object> map = new TreeMap<Double, Object>();
	private double total = 0;
	private Random random;

	/**
	 * Constructor de clase
	 * 
	 * @param random Objeto de tipo Random que se utilizara para obtener el
	 *               resultado
	 */
	public ProbabilisticSelector(Random random)
	{
		this.random = random;
	}

	/**
	 * Constructor de clase. Llama a this(new Random());
	 */
	public ProbabilisticSelector()
	{
		this(new Random());
	}

	/**
	 * Agrega un nuevo objeto posible a ser elegido
	 * 
	 * @param probability Probabilidad relativa (el 100% se corresponde con la
	 *                    sumatoria de los valores de todos los objetos)
	 * @param value       Valor probable a ser devuelto.
	 */
	public void add(double probability, Object value)
	{
		if (probability > 0)
		{
			total += probability;
			map.put(total, value);
		}
	}

	/**
	 * Retorna un objeto del conjunto al azar de acuerdo a su correspondiente
	 * probabilidad relativa
	 * Si el mapa esta vacion retorna null
	 * 
	 * @return El obejto del conjunto seleccionado
	 */
	public Object getValue()
	{
		Object r;
		double value = this.random.nextDouble() * total;
		if (map.isEmpty())
			r = null;
		else
			r = map.higherEntry(value).getValue();
		return r;
	}


	@Override
	public String toString()
	{
		String prob = "";
		double previus = 0;
		for (Double k : map.keySet())
		{
			double valorActual = (k - previus) / total * 100;
			prob += this.map.get(k) + " " + (valorActual) + "%  -  ";
			previus = k;
		}
		return "ProbabilisticSelector [map=" + map + ", total=" + total + "]" + "\n" + prob;
	}

}
