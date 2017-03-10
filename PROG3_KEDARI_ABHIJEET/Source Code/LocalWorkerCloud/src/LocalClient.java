import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;

/*
 * This class is for submitting the task to queue (in memory communication is required).
 * Multithreading approch for creating Local workers they will access in memory queue and will
 * perform the required task.
*/
public class LocalClient {

	
	static ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
	static ConcurrentLinkedQueue<String> destinationqueue = new ConcurrentLinkedQueue<>();
	static int inputCount=0;
	static int outputCount=0;
	
	
	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		String inputArgs[] = null;
		int i=0,taskid =0,count=0;;
		String fileName=null,threads=null,message=null,temp=null;
		boolean working = true;
		BufferedReader br = null;
		
		// Accepting the inputs from user; this is command line tool
		
		//System.out.println("Local Client is running enter Client -s LOCAL -t <Number_Of_Threads> -w <Workload_File_Name>");
		
		//inputArgs = scanner.nextLine().split(" ");

		while (i < args.length) {
			if (args[i].equals("-t")) {
				i = i + 1;
				threads = args[i];
			}

			if (args[i].equals("-w")) {
				i = i + 1;
				fileName = args[i];
			}
			i++;
		}
		
		// Initialized the queue with content form file
		System.out.println("*************************Local Worker***************************");
		System.out.println("Local worker Experiment for  Threds:"+threads);
		long startTime,endtime;
		startTime = 0;
		endtime = 0;
		startTime = System.currentTimeMillis();
		File file = new File(fileName);
		try {
			br = new BufferedReader(new FileReader(file));
			while ((message = br.readLine()) != null) {
				temp = Integer.toString(taskid) + ":" +  message;
				queue.add(temp);
				taskid = taskid + 1;
			}
		}

		catch (IOException e) {
			e.printStackTrace();
		}
		
		int limit = Integer.parseInt(threads);
		for(i=0;i<limit;i++)
		{
			LocalWorker localWorker =  new LocalWorker(queue,destinationqueue);
			localWorker.start();
		}

		while(working)
		{
			if(!destinationqueue.isEmpty())
			{
				destinationqueue.poll();
				count++;
			}
			
			if(count==taskid)
			{
				working = false;
				break;
			}
		}
		
		endtime = System.currentTimeMillis();
		double totaltime1 = (endtime - startTime);
		System.out.println("Total Time=    " + totaltime1 +" milliseconds");
		System.out.println("\n");
		
	}

}
