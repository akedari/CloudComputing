import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
//import org.mortbay.log.Log;

          
public class TeraSort {
	
	public static class Map extends Mapper<LongWritable, Text, Text, Text> {
	       
	       public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
	    	  String val= value.toString();
	          String keyten = val.substring(0, 10);
	          String line=val.substring(10);
	          
	          Text t1 = new Text(keyten);
	          Text t2 = new Text(line);
	          
	          context.write(t1,t2);
      
	       }
	    } 
	           
	    public static class Reduce extends Reducer<Text, Text, Text, Text> {
	   
	       public void reduce(Text key, Iterable<Text> values, Context context) 
	         throws IOException, InterruptedException {

	    	   for (Text sum : values) {
	               context.write(key,sum);
	           }
	       }
	    }
	
	/**
	 * @param args
	 */
	    public static void main(String[] args) throws Exception {
	        Configuration conf = new Configuration();
	        //conf.set("mapreduce.input.keyvaluelinerecordreader.key.value.separator", " ");
	        //conf.setInt("mapreduce.input.fixedlengthinputformat.record.length",502);
	        //conf.setInt("mapreduce.input.fixedlengthinputformat.record.key.length", 10);
	        long startTime,endtime;
			startTime = 0;
			endtime = 0;
			startTime = System.currentTimeMillis();
	        
	        Job job = new Job(conf, "TeraSort_AbhijeetKedari");
	        job.setJarByClass(TeraSort.class);
	        job.setOutputKeyClass(Text.class);
	        job.setOutputValueClass(Text.class);
	            
	        job.setMapperClass(Map.class);
	        job.setReducerClass(Reduce.class);
	            
	        job.setInputFormatClass(TextInputFormat.class);
	        job.setOutputFormatClass(TextOutputFormat.class);
	            
	        FileInputFormat.addInputPath(job, new Path(args[0]));
	        FileOutputFormat.setOutputPath(job, new Path(args[1]));
	            
	        job.waitForCompletion(true);
	        
	        
	        endtime = System.currentTimeMillis();
			double totaltime1 = (endtime - startTime);
			System.out.println("\n\n\nTotal Time=    " + totaltime1 +" milliseconds");
			double totaltime=totaltime1/1000;
			System.out.println("Total Time=    " + totaltime +" seconds");
	     }

}
