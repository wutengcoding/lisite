package com.bruce.hadoop;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.*;


import java.io.IOException;
import java.util.Iterator;
import java.util.StringTokenizer;

/**
 * Created by wuteng1 on 2016/5/17.
 */
public class ProcessUnits {
    //Mapper class
    public static class E_EMapper extends MapReduceBase implements
            Mapper<LongWritable, Text, Text, IntWritable>{
        //map function
        public void map(LongWritable key, Text value, OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException {
            String line = value.toString();
            String lasttoken = null;
            StringTokenizer s = new StringTokenizer(line, "\t");
            String year = s.nextToken();
            while(s.hasMoreTokens()){
                lasttoken = s.nextToken();
            }
            int avgprice = Integer.parseInt(lasttoken);
            output.collect(new Text(year), new IntWritable(avgprice));


        }


    }

    //Reducer class
    public static class E_EReduce extends MapReduceBase implements Reducer<Text, IntWritable, Text, IntWritable>{
        //Reduce function
        public void reduce(Text key, Iterator<IntWritable> values, OutputCollector<Text, IntWritable> outputCollector, Reporter reporter) throws IOException {
            int maxavg = 30;
            int val = Integer.MIN_VALUE;

            while(values.hasNext()){
                if((val = values.next().get()) > maxavg){
                    outputCollector.collect(key, new IntWritable(val));
                }
            }
        }
    }

    //Main function
    public static void main(String[] args) throws IOException {
        JobConf conf = new JobConf(ProcessUnits.class);

        conf.setJobName("max_electricityunits");
        conf.setOutputKeyClass(Text.class);
        conf.setOutputValueClass(IntWritable.class);
        conf.setMapperClass(E_EMapper.class);
        conf.setReducerClass(E_EReduce.class);
        conf.setCombinerClass(E_EReduce.class);
        conf.setInputFormat(TextInputFormat.class);
        conf.setOutputFormat(TextOutputFormat.class);

        FileInputFormat.setInputPaths(conf, new Path(args[0]));
        FileOutputFormat.setOutputPath(conf, new Path(args[1]));

        JobClient.runJob(conf);

    }
}
