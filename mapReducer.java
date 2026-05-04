import java.io.*;
import java.util.*;

import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.*;

import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.*;
import org.apache.hadoop.mapreduce.lib.output.*;

public class WordCount {

    public static class Map extends Mapper<Object, Text, Text, IntWritable> {
            // hello my naame is deepak , deepak is my name
            // ["hello" , "my" , "name" , "is" , "deepak" , "deepak" , "is" , "my" , "name"]
        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {

            StringTokenizer tokenizer = new StringTokenizer(value.toString());
            while (tokenizer.hasMoreTokens()) {
                context.write(new Text(tokenizer.nextToken()), new IntWritable(1));
            }
            // hello 1
            // my 1
            // name 1
            // is 1
            // deepak 1
            // deepak 1
            // is 1
            // my 1
            // name 1

        }
    }

    public static class Reduce extends Reducer<Text, IntWritable, Text, IntWritable> {

        // int max = Integer.MIN_VALUE;
        // String maxKey = "";
        // // hello = [1 , 1]
        public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {

            int sum = 0;

            for (IntWritable val : values) {
                sum += val.get();
            }

            context.write(key, new IntWritable(sum)); // hello 2

            // if (sum > max) {
            //     max = sum;
            //     maxKey = key.toString();
            // }

        }
        // @Override 
        // protected void cleanup(Context context) throws IOException, InterruptedException {
        //     context.write(new Text("max-> "  + maxKey), new IntWritable(max));
        // }
    }

    public static void main(String[] args) throws Exception {

        Job job = Job.getInstance(new Configuration());

        job.setJarByClass(WordCount.class);
        job.setMapperClass(Map.class);
        job.setReducerClass(Reduce.class);
        job.setNumReduceTasks(1);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        job.waitForCompletion(true);
    }
}