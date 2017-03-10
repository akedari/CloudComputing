import java.util.List;
import java.util.Scanner;

import com.amazonaws.services.sqs.model.Message;

public class SQSRemoteWorker {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		/*System.out
				.println("Remote Worker is running enter <Client -s <SourceQueueName> -t <Number Of threads>");
		Scanner scanner = new Scanner(System.in);*/
		String inputArgs[] = null;
		String threads = null, queueName = null;
		int i = 0, j = 0;
		boolean working = true;
		List<Message> messages = null;
		//inputArgs = scanner.nextLine().split(" ");

		while (i < args.length) {
			if (args[i].equals("-s")) {
				i = i + 1;
				queueName = args[i];
			}

			if (args[i].equals("-t")) {
				i = i + 1;
				threads = args[i];
			}
			i++;
		}

		// check whether we have SQS queue created if not create the queue
		SQSQueueServiceUtil sourceQueue = new SQSQueueServiceUtil();
		if (!sourceQueue.isExist(queueName)) {
			String queueUrl = sourceQueue.createQueue(queueName);
		}

		// Creating Result SQS
		SQSQueueServiceUtil resultQueue = new SQSQueueServiceUtil();
		String resultQueueName = resultQueue.getResultQueueName();
		if (!resultQueue.isExist(resultQueueName)) {
			String resultQueueUrl = resultQueue.createQueue(resultQueueName);
		}

		// Creating DynamoDB table
		String tableName = "CloudKonDB";
		DynamoDBServiceUtil dbServiceUtil = new DynamoDBServiceUtil();
		dbServiceUtil.createTable(tableName);

		int numberofthreads = Integer.parseInt(threads);

		for (j = 0; j < numberofthreads; j++) {
			SQSRemoteWorkerThread remoteWorkerThread = new SQSRemoteWorkerThread(
					queueName, resultQueueName, tableName);
			remoteWorkerThread.start();
		}
		//scanner.close();
	}
}

