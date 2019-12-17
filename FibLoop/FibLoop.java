import java.io.*;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.Arrays;


public class FibLoop {
        static ThreadMXBean bean = ManagementFactory.getThreadMXBean();

        /* define constants */
        static int numberOfTrials = 50;
        static int MININPUTSIZE = 0;
        static int MAXINPUTSIZE = 128;


        static String ResultsFolderPath = "/home/teresa/Results/"; // pathname to results folder
        static FileWriter resultsFile;
        static PrintWriter resultsWriter;

        public static void main(String[] args) {

        //direct the verification test results to file
        // run the whole experiment at least twice, and expect to throw away the data from the earlier runs, before java has fully optimized
        System.out.println("Running first full experiment...");
        runFullExperiment("FibLoop-Exp1-ThrowAway.txt");
        System.out.println("Running second full experiment...");
        runFullExperiment("FibLoop-Exp2.txt");
        System.out.println("Running third full experiment...");
        runFullExperiment("FibLoop-Exp3.txt");

        }

        static void runFullExperiment(String resultsFileName) {
        try {
        resultsFile = new FileWriter(ResultsFolderPath + resultsFileName);
        resultsWriter = new PrintWriter(resultsFile);
        } catch (Exception e) {
        System.out.println("*****!!!!!  Had a problem opening the results file " + ResultsFolderPath + resultsFileName);
        return; // not very foolproof... but we do expect to be able to create/open the file...
        }

        ThreadCpuStopWatch BatchStopwatch = new ThreadCpuStopWatch(); // for timing an entire set of trials
        ThreadCpuStopWatch TrialStopwatch = new ThreadCpuStopWatch(); // for timing an individual trial

        resultsWriter.println("#X(Value)    N(Size)    T(Time)"); // # marks a comment in gnuplot data
        resultsWriter.flush();
        /* for each size of input we want to test: in this case starting small and doubling the size each time */
        for (int inputSize = MININPUTSIZE; inputSize <= MAXINPUTSIZE; inputSize++) {
        // progress message...
        System.out.println("Running test for input size " + inputSize + " ... ");

        /* repeat for desired number of trials (for a specific size of input)... */
        long batchElapsedTime = 0;
        // generate a list of randomly spaced integers in ascending sorted order to use as test input
        // In this case we're generating one list to use for the entire set of trials (of a given input size)
        // but we will randomly generate the search key for each trial

        /* force garbage collection before each batch of trials run so it is not included in the time */
        System.gc();

        TrialStopwatch.start(); // *** uncomment this line if timing trials individually
        // run the trials
        for (long trial = 0; trial < numberOfTrials; trial++) {
        long fib = FibLoop(inputSize);

        }

        batchElapsedTime = BatchStopwatch.elapsedTime(); // *** comment this line if timing trials individually
        double averageTimePerTrialInBatch = (double) batchElapsedTime / (double) numberOfTrials; // calculate the average time per trial in this batch

        /* print data for this size of input */
        resultsWriter.printf("%12d  %12d  %15.2f \n", inputSize, Long.toBinaryString(inputSize).length(), averageTimePerTrialInBatch); // might as well make the columns look nice
        resultsWriter.flush();
        System.out.println(" ....done.");
        }
        }

        public static long FibLoop(long X){
  
        long num1 = 1;
        long num2 = 1;
        long fib = 1;
        for (int i = 3; i <= X; i++) {
        fib = num1 + num2; 
        num1 = num2;
        num2 = fib;

        }
        return fib; // Fibonacci number

        }

}

