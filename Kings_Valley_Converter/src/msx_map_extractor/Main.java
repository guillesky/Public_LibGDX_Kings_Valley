package msx_map_extractor;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main
{

	public static void main(String[] args)
	{
		String archi = "assets/DATA1";

		convert(archi);

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
		int count = 0;
		int countTotal=0;
		boolean flag = false;
		int oldIndex=0;
		for (int i = 0; i < fileData.length; i++)
		{

			if (fileData[i] != 20)
			{
				if (flag)
				{countTotal++;
					System.out.println("Total: "+countTotal+" count: "+count+" Index: "+i+ " Diferencia: "+ (i-oldIndex));
					count = 0;
	
					flag = false;
					oldIndex=i;
				}

			} else
			{
				flag = true;
				count++;
			}

		}
		Ventana v=new Ventana(fileData);
	}
	
}
