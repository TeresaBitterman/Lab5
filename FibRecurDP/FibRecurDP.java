import java.io.*;

public class FibRecurDP
{
  // Define constants 
      static int MININPUT = 0;
      static int MAXINPUT = 128;
      static long numberOfTrials = 500;
      static String ResultsFolderPath = "/home/teresa/Results/"; // pathname to results folder
      static FileWriter resultsFile;
      static PrintWriter resultsWriter;
      
      public static void main(String[] args)
        {
          runFullExperiment("FibRecurDP-Exp1.txt");
          runFullExperiment("FibRecurDP-Exp2.txt");
          runFullExperiment("FibRecurDP-Exp3.txt");
        }
      
      public static long fibRecurDP(int x, long[] table)
        {
       
          if(x <= 1)
          {
             return x;
          }

          if(table[x] != 0)     
          {
            return table[x];  
          }
        else  
         {
        
           table[x] = fibRecurDP(x - 1, table) + fibRecurDP(x - 2, table);

          return table[x];
         }
       }

        static void runFullExperiment(String resultsFileName)
        {
        try
        {
        resultsFile = new FileWriter(ResultsFolderPath + resultsFileName);
        resultsWriter = new PrintWriter(resultsFile);
        }
        catch(Exception e)
        {
        System.out.println("There was a problem opening the results file: " + ResultsFolderPath + resultsFileName);
        return; // not very foolproof... but we do expect to be able to create/open the file...
        }

        ThreadCpuStopWatch BatchStopwatch = new ThreadCpuStopWatch(); // for timing an entire set of trials

        resultsWriter.println();
        resultsWriter.println("#X(Value)   N(Size)   AverageTime"); // # marks a comment in gnuplot data
        resultsWriter.flush();

        // for each size input we want to test: in this case starting small and doubling the size each time
        for(int x = MININPUT; x <= MAXINPUT; x++)
        {
        // progress message...
        System.out.println("Running test for input: " + x);

        // repeat for desired number of trials (for a specific size of input)...
        long batchElapsedTime = 0;

        // tabel for storage
        long[] table = new long[x + 1];

        System.out.print("    Running trial batch...");

        // force garbage collection before each batch of trials run so it is not included in the time
        System.gc();

        // instead of timing each individual trial, we will time the entire set of trials (for a given input size)
        // and divide by the number of trials -- this reduces the impact of the amount of time it takes to call the
        // stopwatch methods themselves
        BatchStopwatch.start(); // comment this line if timing trials individually

        // run the trials
        for (long trial = 0; trial < numberOfTrials; trial++)
        {
        // run the function we're testing on the trial input
        fibRecurDP(x, table);
        }

        // comment this line if timing trials individually
        batchElapsedTime = BatchStopwatch.elapsedTime();

        // calculate the average time per trial in this batch
        double averageTimePerTrialInBatch = (double) batchElapsedTime / (double) numberOfTrials;

        // print data for this size of input
        resultsWriter.printf("%7d %7d %15.2f \n", x, Long.toBinaryString(x).length(), averageTimePerTrialInBatch);
        resultsWriter.flush();
        System.out.println(" ....done.");
        }
     }
 }

