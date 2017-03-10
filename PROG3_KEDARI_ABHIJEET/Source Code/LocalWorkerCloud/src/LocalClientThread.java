import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;


public class LocalClientThread extends Thread {
	String fileName;
	ConcurrentLinkedQueue<String> queue;
	int count;
	
	public LocalClientThread(String fileName, ConcurrentLinkedQueue<String> queue,int inputCount) {
		super();
		this.fileName = fileName;
		this.queue=queue;
		this.count=inputCount;
	}


@Override
public void run() {
	File file = new File(fileName);
	BufferedReader br = null;
	String message;
	int taskid =0;
	String temp;
	
	try {
		br = new BufferedReader(new FileReader(file));
		while ((message = br.readLine()) != null) {
			temp = Integer.toString(taskid) + ":" +  message;
			queue.add(temp);
			taskid = taskid + 1;
			
		}
		count=taskid;
		//display();
	}

	catch (IOException e) {
		e.printStackTrace();
	}
	
}

public void display()
{
	while(!queue.isEmpty())
	{
		System.out.println(" "+ queue.poll());
	}
}


}
