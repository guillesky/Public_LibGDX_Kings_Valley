package prueba;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import modelo.LevelConverter;

public class Prueba
{

	public static void main(String[] args)
	{
		for (int i=1;i<16;i++) 
		{
			String archi="assets/levels/level_";
			
			if(i<10)
				archi+="0";
			archi+=i;
			
			convert(archi);
		}
		
	}

	public static void convert(String filename) 
	{
		File file = new File(filename);
		byte[] fileData = new byte[(int) file.length()];
		DataInputStream dis;
		try
		{
			dis = new DataInputStream(new FileInputStream(file));
			dis.readFully(fileData);
			dis.close();
		} catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LevelConverter l1 = new LevelConverter();
		l1.setFromArray(fileData);
		//System.out.println(l1.getObjectLayer());
		Object[] li = l1.getLevelitems();
		//for (int i = 0; i < li.length; i++)
	//		System.out.println(li[i]);
		try
		{
			PrintWriter out = new PrintWriter(filename+".tmx");
			out.println(l1.getTMX());
			out.close();
		} catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
