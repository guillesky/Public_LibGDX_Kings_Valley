package unused_yet_not_implemented;

import java.util.TreeMap;

import engine.game.Game;

public class ProbabilisticSelector
{
    private TreeMap<Double, Object> map = new TreeMap<Double, Object>();
    private double total = 0;

    public void add(double probability, Object value)
    {
	if (probability > 0)
	{
	    total += probability;
	    map.put(total, value);
	}
    }

    public Object getValue()
    {
	double value = Game.random.nextDouble() * total;
	return map.higherEntry(value).getValue();
    }

    @Override
    public String toString()
    {
	String prob = "";
	double previus = 0;
	for (Double k : map.keySet())
	{
	    double valorActual = (k-previus) / total * 100;
	    prob += this.map.get(k) + " " +(valorActual) + "%  -  ";
	    previus=k;
	}
	return "ProbabilisticSelector [map=" + map + ", total=" + total + "]" + "\n" + prob;
    }

}
