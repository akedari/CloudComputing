import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//This class is main class calling splitting of files and merging of files class methods.

public class SharedMemory extends Thread {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	
	static // Configuration part
			int i;
			static long inputFileSize;// = 1000;	// 1GB or 1TB
			static long chunkSize; // = 100;			// 10MB or 1GB
			static long numberOfFilestoCreate; //=inputFileSize/chunkSize;
	
	public static void main(String[] args) throws InterruptedException {

		long startTime,endtime;
		startTime = 0;
		endtime = 0;
		startTime = System.currentTimeMillis();
		
		File inputfile= new File("input");
		long totalBytes= inputfile.length();

		int chunk=100;

		//10   000 00 000   = 1GB
		inputFileSize = (totalBytes)/1000000;	// 1000MB
		chunkSize = inputFileSize/1000;						//10MB chunk
		numberOfFilestoCreate = inputFileSize/chunkSize;	// 10 files
		
		long totalline = (int) (totalBytes/chunk);
		long linePerFile = (totalline / numberOfFilestoCreate);
		
		//Diving files in to small pieces
		divideFilesintoChunks(linePerFile);
		
		//MergeFiles function;
		mergeFileChunks();
		
		
		endtime = System.currentTimeMillis();
		double totaltime1 = (endtime - startTime);
		System.out.println("\n\n\nTotal Time=    " + totaltime1 +" milliseconds");
		double totaltime=totaltime1/1000;
		System.out.println("Total Time=    " + totaltime +" seconds");
	}

//***********************************************************************************************************************************************************
	
	// This method is designed for merging of files , this method will get total number of files in specified folder.
	// Then till all the files get completely process it will put lock on folder.
	//Using ExecutorService we have limited number of threads concurrent execution
	
	private static void mergeFileChunks() {
		// TODO Auto-generated method stub
		int j,threadCount=1;
		int name=0;
		File folder= new File("./output/");
		
		while(true)
		{
			File[] files= folder.listFiles();
			if(files.length==1)
			{
				break;
			}
			
			int numberofthreads =  files.length;
			ExecutorService pool = Executors.newFixedThreadPool(threadCount);
			
			// If we have even number of files in specified folder location
			if(numberofthreads%2==0)
			{
				for(i=0; i<numberofthreads; )
				{
					j=i+1;
					Runnable worker = new MergeFiles(files[i].getName(),files[j].getName(),name++);
					pool.execute(worker);
					i=i+2;
				}				
			}
			
			// If we have odd number of files in specified folder location
			else
			{
				for(i=0; i<numberofthreads-1; )
				{
					j=i+1;
					Runnable worker = new MergeFiles(files[i].getName(),files[j].getName(),name++);
					pool.execute(worker);
					i=i+2;
				}
			}
			
			pool.shutdown();
			while (!pool.isTerminated()) 
			{
	        }
		}
	}

//*******************************************************************************************************************************************************
	
	// This method is designed for splitting the files in small chunks, we are creating 10 MB size files for all operations
	//Using ExecutorService we have limited number of threads concurrent execution
	
	private static void divideFilesintoChunks(long linePerFile) {
		// TODO Auto-generated method stub
		ExecutorService pool1 = Executors.newFixedThreadPool(1);
		System.out.println("numberOfFilestoCreate: "+ numberOfFilestoCreate);
		for (i = 0; i < numberOfFilestoCreate; i++) 
		{
			Runnable worker1 = new SortFiles(i,linePerFile);
			pool1.execute(worker1);
		}
		
		pool1.shutdown();
		while (!pool1.isTerminated()) 
		{
        }
	}

}
