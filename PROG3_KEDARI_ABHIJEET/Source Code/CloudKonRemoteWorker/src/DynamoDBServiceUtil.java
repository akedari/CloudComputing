import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.DescribeTableRequest;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.PutItemResult;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.amazonaws.services.dynamodbv2.model.TableDescription;
import com.amazonaws.services.dynamodbv2.util.Tables;

public class DynamoDBServiceUtil {

	private BasicAWSCredentials credentials;
    private String simpleQueue = null;
    private String resultQueue = null;
	private String accessKey=null;
	private String secretKey=null;
	private String tableName = null;
	static AmazonDynamoDBClient client;
	private DynamoDBMapper mapper;
	private DynamoDB db;
	private AmazonDynamoDB amazonDynamoDB;
	
	// Constructor to initialize the required properties
	
	public DynamoDBServiceUtil() {
		
		Properties prop = new Properties();
		
		try 
    	{
    		File file =  new File("AwsCredentials.properties");
			prop.load(new FileInputStream(file));
			this.accessKey = prop.getProperty("accessKey");
	    	this.secretKey = prop.getProperty("secretKey");
	    	this.simpleQueue = prop.getProperty("queuename");
	    	this.resultQueue = prop.getProperty("resultsqs");
	    	
	    	credentials = new BasicAWSCredentials(accessKey, secretKey);
	    	client = new AmazonDynamoDBClient(credentials);
	    	Region USEastNV = Region.getRegion(Regions.US_EAST_1);
	    	client.setRegion(USEastNV); 	
	    	
	    	amazonDynamoDB = new AmazonDynamoDBClient(credentials);
	    	mapper = new DynamoDBMapper(amazonDynamoDB);
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
	
	// Creating a DynamoDB table for storing taskID and their status, so that we can verify the atomic execution of the task
	// redundant task will simply get discarded.
	
	public void createTable(String tableName) 
	{
		try
		{
			if (Tables.doesTableExist(client, tableName) == true) 
			{
                System.out.println("Table " + tableName + " is already ACTIVE");
            } 
			else 
			{
				this.tableName = tableName;
				CreateTableRequest createTableRequest = new CreateTableRequest().withTableName(tableName);
				
				KeySchemaElement keySchema = new KeySchemaElement().withAttributeName("id").withKeyType(KeyType.HASH);
				createTableRequest.withKeySchema(keySchema);
				
				AttributeDefinition attributeDefinition = new AttributeDefinition().withAttributeName("id").withAttributeType(ScalarAttributeType.S);
				createTableRequest.withAttributeDefinitions(attributeDefinition);
				
				ProvisionedThroughput provisionedThroughput = new ProvisionedThroughput().withReadCapacityUnits(100L).withWriteCapacityUnits(100L);
				createTableRequest.withProvisionedThroughput(provisionedThroughput);
				
				TableDescription createdTableDescription = client.createTable(createTableRequest).getTableDescription();
				
				try 
				{
					Tables.awaitTableToBecomeActive(client, tableName);
				} 
				catch (InterruptedException e) 
				{
					e.printStackTrace();
				}
				System.out.println("Table Created SUCCESSFULLY !!!");
			}
		}	
		catch (AmazonServiceException e) 
		{
			e.printStackTrace();
		}
		catch (AmazonClientException e) 
		{
			e.printStackTrace();
		}
		  // Describe new table
        DescribeTableRequest describeTableRequest = new DescribeTableRequest().withTableName(tableName);
        TableDescription tableDescription = client.describeTable(describeTableRequest).getTable();
	}
	
	//Inserting record in DynamoDB table
	public void insert(String id,String status,String tableName)
	{
		 Map<String, AttributeValue> item = newItem(id, status);
         PutItemRequest putItemRequest = new PutItemRequest(tableName, item);
         PutItemResult putItemResult = client.putItem(putItemRequest);
	}
	
	//updating record in DynamoDB table
	public void update(String id,String status)
	{
		CloudKonDB record = new CloudKonDB();
		record.setId(id);
		mapper.save(record);
	}
	
	//Deleting record in DynamoDB table
	public void delete(String id,String status)
	{
		CloudKonDB record = new CloudKonDB();
		record.setId(id);
		mapper.delete(record);
	}
	
	//Scanning through record in DynamoDB table, and return false if no record for specified filter (Or Condition)
	public boolean scan(String tableName, String msgID)
	{
		HashMap<String, Condition> scanFilter = new HashMap<String, Condition>();
        Condition condition = new Condition()
            .withComparisonOperator(ComparisonOperator.EQ.toString())
            .withAttributeValueList(new AttributeValue().withS(msgID));
        scanFilter.put("id", condition);
        ScanRequest scanRequest = new ScanRequest(tableName).withScanFilter(scanFilter);
        ScanResult scanResult = client.scan(scanRequest);
        if(scanResult.getCount()==0)
        {
        	return false;
        }
        else
        {
        	return true;
        }
	}
	
	//Table ColumnMapper
	@DynamoDBTable(tableName="CloudKonDB")
 	public class CloudKonDB {
		private String Id;

		@DynamoDBHashKey(attributeName = "Id")
		public String getID(){
			return Id;
		}
		public void setId(String Id){
			this.Id = Id;
		}
	}
	
	//Setting Column attribute type 
	private static Map<String, AttributeValue> newItem(String id, String status) {
		Map<String, AttributeValue> item = new HashMap<String, AttributeValue>();
        item.put("id", new AttributeValue(id));
        item.put("status", new AttributeValue().withS(status));
        return item;
    }
}
