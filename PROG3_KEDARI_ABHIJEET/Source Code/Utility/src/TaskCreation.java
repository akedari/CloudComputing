
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class TaskCreation {

	public static void main(String[] args) {
		int tasks = Integer.parseInt(args[0]);
		int time = Integer.parseInt(args[1]);
		int i=1;
		FileWriter writer = null;
		File file = null;
		try {
			file = new File("/home/ubuntu/CloudKonClient/workload");
			writer = new FileWriter(file);

			while(i<=tasks)
			{				
				writer.write("sleep "+ time + "\n");
				i++;
			}
			
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		finally 
		{
			try 
			{
				writer.close();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
	}
}
