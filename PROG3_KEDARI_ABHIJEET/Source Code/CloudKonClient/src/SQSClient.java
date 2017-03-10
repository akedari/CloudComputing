import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class SQSClient {
	public static void main(String[] args) {

		//System.out.println("Enter Command in the format <client -s <SOURCEQUEUE> -w <WORKLOAD_FILE>>");
		//Scanner scanner = new Scanner(System.in);
		String commandLineArgument[] = null;
		String fileName = null, queueName = null;
		File file = null;
		int i = 0;
		boolean taskExecuting =true;
		//commandLineArgument = scanner.nextLine().split(" ");

		while (i < args.length) {
			if (args[i].equals("-s")) {
				i = i + 1;
				queueName = args[i];
			}
			if (args[i].equals("-w")) {
				i = i + 1;
				fileName = args[i];
			}
			i++;
		}

		//Check whether user has entered correct input for 
		if (!(fileName.isEmpty() && queueName.isEmpty())) {

			file = new File(fileName);
			BufferedReader br = null;
			int count = 0, outputCount =0;
			String message,temp;

			// creating source SQS for storing input data
			SQSQueueServiceUtil sourceQueue = new SQSQueueServiceUtil();
			String queueUrl = sourceQueue.getQueueUrl(queueName);
			
			//Creating Destination SQS for storing output data
			SQSQueueServiceUtil resultQueue = new SQSQueueServiceUtil();
			String resultQueueName = resultQueue.getResultQueueName();
			String resultQueueUrl = resultQueue.getQueueUrl(resultQueueName);

			long startTime,endtime;
			startTime = 0;
			endtime = 0;
			startTime = System.currentTimeMillis();
			try {
				br = new BufferedReader(new FileReader(file));
				while ((message = br.readLine()) != null) {
					//System.out.println(message);
					temp = Integer.toString(count);
					temp = "task"+temp;
					message = temp + ":"+ message;
					sourceQueue.sendMessagetoQueue(queueUrl, message);
					count = count + 1;
				}
			}

			catch (IOException e) {
				e.printStackTrace();
			}
			
			System.out.println("filling done Successfully !!");
			System.out.println("Waiting for remote worker to complete the task !!");
			
			while(taskExecuting)
			{
				//Checking if resultant SQS queue is Empty or not
				if(!resultQueue.IsEmpty(resultQueueUrl))
				{
					//poll from destination SQS
					//Here i can improve by deleting bunch of requests and incrementing the count (outputCount) by the size of message
					// sqs get() returning.
					resultQueue.pollFromQueue(resultQueueUrl);
					outputCount ++;
				}
				
				//Comparing the numbe rof task completed, if all task are completed will delete dource and destination SQS queue 
				if(count == outputCount)
				{
					//System.out.println("Number of task completed are: "+outputCount);
					resultQueue.deleteSQS(resultQueueUrl);
					sourceQueue.deleteSQS(queueUrl);
					taskExecuting = false;
					break;
				}
			}
			endtime = System.currentTimeMillis();
			double totaltime1 = (endtime - startTime);
			System.out.println("\nTotal Time=    " + totaltime1 +" milliseconds");
/*			double totaltime=totaltime1/1000;
			System.out.println("Total Time=    " + totaltime +" seconds");*/
			
			
		} else {
			System.out
					.println(" input file is missing please check the name of file !!!!");
		}

	}

}