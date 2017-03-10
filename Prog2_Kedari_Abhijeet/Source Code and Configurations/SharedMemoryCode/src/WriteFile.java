import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class WriteFile extends Thread {
	
	public int filename;
	String inputfileName = "input.txt";
	String line = null;
	long linePerFiles;
	ArrayList<String> str;;
	Map<String, ArrayList<String>> map;
	
	public WriteFile(int i,long linePerFile) 
	{
		this.filename=i;
		this.linePerFiles=linePerFile;
		map=new TreeMap<String, ArrayList<String>>();
	}
	
	public void run()
	{
			//System.out.println("Inside WriteFile run() filename:"+filename);
			long linesPerFile= linePerFiles;
			long cnt=filename*linesPerFile;
            try {           	
            	File input = new File(inputfileName);
                File output = new File("./output/"+filename);
                
                Scanner sc = new Scanner(input);
                PrintWriter printer = new PrintWriter(output);
                
                for (int i = 0; i < cnt; i++) {
                	if(sc.hasNext())
                		sc.nextLine();
                	else
                		break;
				}
                
                for(int j = 0; j < linesPerFile; j++) {
                	if(sc.hasNext())
                	{
                		String line = sc.nextLine();
                		
                		String key=getKey(line);
        				map.put(key,getValue(line,key));
                		//printer.write(s+"\n");
                	}
                	else
                		break;
                }
                
                for(String key 	: map.keySet())
                {
                	ArrayList<String> tempValue =  map.get(key);
                	for (String string : tempValue) {
                		printer.write(key+string+"\n");	
					}
                }
                
                printer.flush();
                
			} catch (FileNotFoundException e) {
				System.out.println("Error writing file '" + filename + "'");
			} 
            //System.out.println("finished Inside WriteFile run() filename:"+filename);
            
	}
	
	private ArrayList<String> getValue(String line,String key) 
	{
		String temp= line.substring(11);
		ArrayList<String> str =	new ArrayList<String>();
		
		if(map.containsKey(key))
		{
			str=map.get(key);
		}
		str.add(temp);
		
		return str;
    }
	
	private static String getKey(String line) 
	{
    	return line.substring(0, 11);//extract value you want to sort on
    }

}
