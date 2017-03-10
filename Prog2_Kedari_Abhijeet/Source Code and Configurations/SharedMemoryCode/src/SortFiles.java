import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Map;
import java.util.TreeMap;

//This class is designed for creating small chunks of files

public class SortFiles extends Thread{
	
	public int filename;
	String inputfileName = "input";
	long linePerFiles;
	Map<String, String> map;
	 
	//Assign file name ,lines per file.
	public SortFiles(int i,long linePerFile) 
	{
		this.filename=i;
		this.linePerFiles=linePerFile;
		map=new TreeMap<String, String>();
	}
	
	
	// we are reading file in small chunk size then we are writing back to disk in chunk size
	//This will reduce the number of IO Operations and process will speed up
	public void run()
	{
        File output = new File("./output/"+filename);
        int offset =0;
        int len = (int)linePerFiles;
        
        int fizesize = len*100;
        byte[] buffer = new byte[fizesize] ;
        StringBuffer stringBuffer = new StringBuffer();
        
        RandomAccessFile fileStore;
		try {
			fileStore = new RandomAccessFile(inputfileName, "rw");
			BufferedWriter bwr = new BufferedWriter(new FileWriter(output));
			
			//reading from required position to allow multiple thread work simultaneously
			long position  = linePerFiles*filename*100;
			fileStore.seek(position);
			
			// reading String from RandomAccessFile
            fileStore.read(buffer,offset,fizesize);
            
            byte[] bytesarray = new byte[100];
            int newOffset=0;
            ByteArrayInputStream in = new ByteArrayInputStream(buffer);
            
            for (int i = 0; i < linePerFiles; i++) 
            {				
            	in.read(bytesarray, newOffset, 100);
            	String line = new String(bytesarray);
            	map.put(line.substring(0, 10),line.substring(10));
			}
            
            for(String key 	: map.keySet())
            {
            	String value =  map.get(key);
           		stringBuffer.append(key+value);
            }
            
            bwr.write(stringBuffer.toString());
            bwr.flush();
            bwr.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
        
	}

}
