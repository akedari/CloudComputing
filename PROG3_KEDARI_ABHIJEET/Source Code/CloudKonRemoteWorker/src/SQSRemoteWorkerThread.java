import java.util.List;

import com.amazonaws.services.sqs.model.Message;

public class SQSRemoteWorkerThread extends Thread {
	
	String queueName;
	String resultQueueName;
	String tableName;
	
	public SQSRemoteWorkerThread(String queueName, String resultQueueName, String tableName) 
	{
		this.queueName=queueName;
		this.resultQueueName=resultQueueName;
		this.tableName=tableName;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		String data[];
		String temp;
		String msg;
		String msgs[];
		int time;
/*		long startTime,endtime;
		startTime = 0;
		endtime = 0;*/
		
		SQSQueueServiceUtil sourceQueue = new SQSQueueServiceUtil();
		String queueUrl = sourceQueue.getQueueUrl(queueName);
		
		//Destination SQS URL
		SQSQueueServiceUtil resultQueue = new SQSQueueServiceUtil();
		String resultQueueUrl = resultQueue.getQueueUrl(resultQueueName);
		
		//Creating DynamoDB table
		DynamoDBServiceUtil dbServiceUtil = new DynamoDBServiceUtil();
		
		boolean flag = true; 
		/*startTime = System.currentTimeMillis();*/
		try
		{
		
		while(flag)
		{
			msg = sourceQueue.pollFromQueue(queueUrl);
			if (!(msg == null)) {
				msgs = msg.split(":");
				if (!dbServiceUtil.scan(tableName, msgs[0])) {
					//System.out.println("I am here !!");
					dbServiceUtil.insert(msgs[0], "0", tableName);
					temp = msgs[1].toString();
					data = temp.split(" ");
					time = Integer.parseInt(data[1]);
					try {
						sleep(time);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					// adding to result SQS queue
					resultQueue.sendMessagetoQueue(resultQueueUrl, msgs[0]);
				}
			}
		}
		
		}
		
		catch(Exception ex)
		{
		}
		/*endtime = System.currentTimeMillis();
		double totaltime1 = (endtime - startTime);
		System.out.println("\nTotal Time=    " + totaltime1 +" milliseconds");
		double totaltime=totaltime1/1000;
		System.out.println("Total Time=    " + totaltime +" seconds");*/
	}

}
