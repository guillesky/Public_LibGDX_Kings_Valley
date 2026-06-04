package util;

import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

/**
 * Permite realizar selecciones aleatorias ponderadas entre un conjunto de
 * alternativas.
 *
 * <p>
 * Cada alternativa puede asociarse a un peso o probabilidad relativa que
 * determina su posibilidad de ser seleccionada respecto de las demas.
 * </p>
 *
 * <p>
 * A diferencia de una seleccion uniforme, este mecanismo permite favorecer
 * determinadas opciones sin eliminar completamente la posibilidad de que otras
 * alternativas sean elegidas.
 * </p>
 *
 * <p>
 * Esta clase encapsula la logica necesaria para gestionar distribuciones
 * probabilisticas discretas, proporcionando una abstraccion reutilizable para
 * cualquier situacion que requiera toma de decisiones ponderada.
 * </p>
 *
 * <p>
 * Desde el punto de vista arquitectonico, la implementacion centraliza los
 * algoritmos de seleccion probabilistica, evitando su duplicacion en los
 * distintos componentes que requieran este comportamiento.
 * </p>
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
     * probabilidad relativa Si el mapa esta vacio retorna null
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
	{
	    Map.Entry<Double, Object> entry = map.higherEntry(value);

	    if (entry == null) // puede pasar si value es mayor o igual ultimo key
		entry = map.lastEntry();
	    r = entry.getValue();
	}
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
