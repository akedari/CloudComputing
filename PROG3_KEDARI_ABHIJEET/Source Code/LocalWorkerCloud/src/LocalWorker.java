import java.util.concurrent.ConcurrentLinkedQueue;


//Local work for executing task mentione din queue

public class LocalWorker extends Thread{
	ConcurrentLinkedQueue<String> queue;
	ConcurrentLinkedQueue<String> destinationqueue;

	public LocalWorker(ConcurrentLinkedQueue<String> queue,
			ConcurrentLinkedQueue<String> destinationqueue) {
		super();
		this.queue = queue;
		this.destinationqueue = destinationqueue;
	}

	// Executing the task and calculting the time taken byy this thread to execute the tasks
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		//super.run();
		/*long startTime,endtime;
		startTime = 0;
		endtime = 0;*/
		String temp;
		String regex=":";
		String data[],time[];
				
		//startTime = System.currentTimeMillis();
		try {
			while (!queue.isEmpty()) {
				temp = queue.poll();
				data = temp.split(regex);
				time = data[1].split(" ");
				destinationqueue.add(data[0]);
				sleep(Integer.parseInt(time[1]));
			}
		}

		catch (NumberFormatException e) 
		{
			e.printStackTrace();
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
/*		endtime = System.currentTimeMillis();
		double totaltime1 = (endtime - startTime);
		System.out.println("\nTotal Time=    " + totaltime1 +" milliseconds");*/
		//double totaltime=totaltime1/1000;
		//System.out.println("Total Time=    " + totaltime +" seconds");
	}
	
}
