import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.amazonaws.services.sqs.model.GetQueueUrlRequest;
import com.amazonaws.services.sqs.model.ListQueuesResult;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;


public class SQSQueueServiceUtil {

	
	private BasicAWSCredentials credentials;
    private AmazonSQS sqs;
    private String simpleQueue = null;
    private String resultQueue = null;
	private String accessKey=null;
	private String secretKey=null;
	private String endpoint = null;
    
	// Constructor to initialize the required properties
	
    public SQSQueueServiceUtil() {
		// TODO Auto-generated constructor stub
    	Properties prop = new Properties();
    	try 
    	{
    		File file =  new File("AwsCredentials.properties");
			prop.load(new FileInputStream(file));
			this.accessKey = prop.getProperty("accessKey");
	    	this.secretKey = prop.getProperty("secretKey");
	    	this.endpoint = prop.getProperty("endpoint");
	    	this.simpleQueue = prop.getProperty("queuename");
	    	this.resultQueue = prop.getProperty("resultsqs");
	    	
	    	credentials = new BasicAWSCredentials(accessKey, secretKey);
	    	sqs = new AmazonSQSClient(credentials);
	    	sqs.setEndpoint(endpoint);
    	} 
    	catch (FileNotFoundException e) 
    	{
			e.printStackTrace();
		} 
    	
    	catch (IOException e) 
    	{
			e.printStackTrace();
		}
    	
	}
    
    // Return the AmazonSQS
    public AmazonSQS getSQSClient()
    {
    	return this.sqs;
    }
    
    //	Return the Source QueueName
    public String getQueueName()
    {
    	return this.simpleQueue;
    }
    
    // 	Return the Result QueueName
    public String getResultQueueName()
    {
    	return this.resultQueue;
    }
    
    // Create a new SQS queuee and Return newly created Queue URL
    public String createQueue(String queueName)
    {
    	CreateQueueRequest createQueueRequest = new CreateQueueRequest(queueName);
    	String queueUrl = this.sqs.createQueue(createQueueRequest).getQueueUrl();
    	return queueUrl;
    }
        
    //Return newly Queue URL from Name given as a parameter
    public String getQueueUrl(String queueName)
    {
    	GetQueueUrlRequest getQueueUrlRequest = new GetQueueUrlRequest(queueName);
    	return this.sqs.getQueueUrl(getQueueUrlRequest).getQueueUrl();
    }
    
    //Return ListQueuesResult
    public ListQueuesResult listQueue()
    {
    	return this.sqs.listQueues();
    }
    
   //Adding the record to queue; parameters are queue URl and message to add in queue 
    public void sendMessagetoQueue(String queueUrl, String message)
    {
    	SendMessageRequest sendMessageRequest = new SendMessageRequest(queueUrl,message);
    	SendMessageResult messageResult = this.sqs.sendMessage(sendMessageRequest);
    	//System.out.println(messageResult.toString());
    }
    
    //return the list of message from queue; parameter provided are queue URl
    public List<Message> getMessageFromQueue(String queueUrl)
    {
    	ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(queueUrl);
    	receiveMessageRequest.setMaxNumberOfMessages(1);
    	receiveMessageRequest.setVisibilityTimeout(0);
    	receiveMessageRequest.setWaitTimeSeconds(1);
    	List<Message> messages = sqs.receiveMessage(receiveMessageRequest).getMessages();
    	return messages;
    }
    
    //poll the message or record from queue; parameter provided is URL for queue
    public String pollFromQueue(String queueUrl)
    {
    	String msg = null;
    	List<Message> messages=null;
    	messages = this.getMessageFromQueue(queueUrl);
    	if(!(messages.size()<=0 || messages == null))
    	{
    		msg = messages.get(0).getBody().toString();
    		
    		String messageReceiptHandle = messages.get(0).getReceiptHandle();
    		DeleteMessageRequest deleteMessageRequest = new DeleteMessageRequest(queueUrl,messageReceiptHandle);
    		sqs.deleteMessage(deleteMessageRequest);
    	}
		return msg;
    }
    
    // Delete message from queue; parameters provided are queue URL and message to delete 
    public void deleteMessageFromQueue(String queueUrl, Message message)
    {
    	String messageReceiptHandle = message.getReceiptHandle();
    	DeleteMessageRequest deleteMessageRequest = new DeleteMessageRequest(queueUrl,messageReceiptHandle);
    	sqs.deleteMessage(deleteMessageRequest);
    }

	public boolean isExist(String queueName) {
		// TODO Auto-generated method stub
		String queueUrl = null;
		try {
			queueUrl = this.getQueueUrl(queueName);
		}

		catch (Exception ex) {
			return false;
		}		
		return true;
	}
}
