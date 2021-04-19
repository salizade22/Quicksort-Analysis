public class Quicksort2 {
    static long comparisonCounter = 0; // counter
    static int[] ns = {1000, 10000, 20000, 50000, 100000, 300000, 400000, 800000, 1600000, 3200000, 10000000}; // number of elements in array to use
    static int numberOfReps = 5; // number of reps
    static boolean median = false; // make middle element be the partitioning element

    public static void main(String[] args){

        Generate test = new Generate();

        System.out.println("not random");
        calcRT(test, "rand", false, numberOfReps);
        calcRT(test, "part", false, numberOfReps);
        calcRT(test, "mostly", false, numberOfReps);

        System.out.println("\n  random");
        calcRT(test, "rand", true, numberOfReps);
        calcRT(test, "part", true, numberOfReps);
        calcRT(test, "mostly", true, numberOfReps);

    }

    public static void calcRT(Generate test, String typeOfArray, boolean randomOrNot, int numberOfReps) {
        for (int j = 0; j < ns.length; j++ ){
            median = false;
            long cumulativeComparisons = 0;
            long[] comparisonArray = new long[numberOfReps];


            for (int i = 0; i < numberOfReps; i++ ){

                if (typeOfArray.equals("rand")) {
                    int[] input = test.generateRandomInput(ns[j]); //randomly generated input
                    Quicksort(input, 0, input.length-1, randomOrNot);
                    assert (sorted(input));
                } else if (typeOfArray.equals("part")) {
                    int[] input = test.generatePartiallySortedInput(ns[j]); //partially sorted input
                    Quicksort(input, 0, input.length-1, randomOrNot);
                    assert (sorted(input));
                } else {
                    int[] input = test.generateMostlySortedInput(ns[j]); // mostly sorted input
                    Quicksort(input, 0, input.length-1, randomOrNot);
                    assert (sorted(input));
                }
                cumulativeComparisons += comparisonCounter;
                comparisonArray[i] = comparisonCounter;
                comparisonCounter = 0;
            }
            long meanComparisons = cumulativeComparisons/numberOfReps;
            float var = 0.f;
            float normVar = 0.f;
            for (long oneResult : comparisonArray){
                
                var += Math.pow(oneResult-meanComparisons, 2);
                
            }
            var = var/(numberOfReps-1);
            normVar = var/(meanComparisons^2);

            System.out.println("type: " + typeOfArray +  ", n: " + ns[j] + " " + cumulativeComparisons/numberOfReps);
            System.out.println("var and normVar: " + var + ", " + normVar); 
        }

    } 


    public static void swap(int[] input, int f, int s){
        int t = input[f];
        input[f] = input[s];
        input[s] = t;
    }
    public static void Quicksort(int[] input, int lo, int hi, boolean rand){

        if(input.length==0 || lo >= hi){
            return;
        }
        int ind = lo + (hi-lo)/2;

        if(rand) {
            ind = (int)Math.floor(Math.random()*(hi - lo + 1) + lo);
        }
        if(median){
            ind = (hi+lo)/2;
        }
        int p = partition(input, lo, hi, ind);
        Quicksort(input, lo, p-1, rand);
        Quicksort(input, p+1, hi, rand);

    }

    public static int partition(int[] input, int lo, int hi, int ind){
        swap(input, ind, hi);
        int pivot = input[hi];
        int i = lo;
        int j = hi-1;
        while(i<=j) {
            if (input[i] <= pivot) {
                i++;
                //comparisonCounter++;
            }
            else{
                swap(input, i, j);
                j--;
            }
            comparisonCounter += 1;
        }
        swap(input, hi, j+1);
        return j+1;
    }

    static boolean sorted(int[] input){
        int n = input.length;
        for(int i = 1; i<n;i++){
            if(input[i]<input[i-1]){
                return false;
            }
        }
        return true;
    }

}
