import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

// This class is designed for merging of small files and sort them while merging them
public class MergeFiles extends Thread{

	String name1;
	String name2;
	int outputname;
	
	//Assign the new name to file which we are going to create, and assign input file names to variable
	public MergeFiles(String name1, String name2, int outputname) 
	{
		this.name1=name1;
		this.name2=name2;
		this.outputname=outputname;
	}


	public void run()
	{
		File file1 = new File("./output/"+name1);
		File file2 = new File("./output/"+name2);
		PrintWriter printer = null;
		
        File output = new File("./output/"+ "output"+outputname);
        try 
        {
			printer = new PrintWriter(output);
		} 
        catch (FileNotFoundException e1) 
        {
			e1.printStackTrace();
		}
        
        Scanner sc1,sc2;
        String line1 = null;
        String line2 = null;
        
        boolean flag1=false;
        boolean flag2=false;
        
		try 
		{
			sc1 = new Scanner(file1);
			sc2 = new Scanner(file2);
			
			if(file1.length() ==0)
			{
				line1 = sc2.nextLine();
				printer.write(line2+"\r\n");
			}
			
			else if(file2.length()==0)
			{
				line2 = sc1.nextLine();
				printer.write(line1+"\r\n");
			}
			
			else {

				line1 = sc1.nextLine();
				line2 = sc2.nextLine();
//scanning both the files to read and sort them accordingly
				while (sc1 != null && sc2 != null) {

					String key1 = line1.substring(0, 11);
					String Key2 = line2.substring(0, 11);

					int result = key1.compareTo(Key2);

					if (result < 0) 
					{
						printer.write(line1 + "\r\n");
						if(sc1.hasNext())
							line1 = sc1.nextLine();
						else
						{	flag1=true;
							break;
						}
					}
					
					else 
					{
						printer.write(line2 + "\r\n");
						if(sc2.hasNext())
							line2 = sc2.nextLine();
						else
						{
							flag2=true;
							break;
						}
					}
				}
// Writing down remaining contents of file
				if (flag1==true) {
					printer.write(line2 + "\r\n");
					while(sc2.hasNext())
					{
						line2=sc2.nextLine();
						printer.write(line2 + "\r\n");
					}
				}
// Writing down remaining contents of file				
				if(flag2==true){
					printer.write(line1 + "\r\n");
					while(sc1.hasNext())
					{
						line1=sc1.nextLine();
						printer.write(line1 + "\r\n");
					}
				}
			}
			printer.flush();
		}
		
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		
		// This will remove the files we have merged to avoid next batch to ignore the already merged files.
		finally
		{
			try 
			{
				Path path= Paths.get("./output/"+name1);
				Files.delete(path);
				
				Path path2= Paths.get("./output/"+name2);
				Files.delete(path2);
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
}
