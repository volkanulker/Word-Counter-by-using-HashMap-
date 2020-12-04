import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        // TODO: 5.12.2020 Write Technical Report
        txtReader reader = new txtReader();

        double laodFactor;
        String hashFunction;

        String question1="Choose your load factor:\nEnter '1' for 0.5" +                 "\nEnter '2' for 0.7";
        String question2="Choose your hash function:\nEnter '1' for Polynomial Accumulation Function" +
                "\nEnter '2' for My Own Function";

        boolean input1;   boolean input2;

        // Take load factor from user
        input1= takeInput(question1);
        input2= takeInput(question2);


        if(input1){
            laodFactor=0.5;
        }else{
            laodFactor=0.7;
        }

        if(input2){
            hashFunction="PAF";
        }else{
            hashFunction="YHF";
        }


        Map<String, Integer> hashMap = new Map<String, Integer>(laodFactor,hashFunction);

        long startIndexing = System.currentTimeMillis();
        reader.readAndAddToMap("C:\\story.txt",hashMap);
        long endIndexing = System.currentTimeMillis();
        long indexingTime = endIndexing - startIndexing;


        reader.readAndGet("C:\\search.txt",hashMap);

        System.out.println("--------------------------------------------");
        System.out.println("Total Collision: "+hashMap.getTotalCollision()+" (includes collisions that in reSize() method)");
        System.out.println("Indexing Time: "+indexingTime+" ms");
        System.out.println("Min Search Time: "+hashMap.getMinSearchTime()+"ms");
        System.out.println("Max Search Time: "+hashMap.getMaxSearchTime()+"ms");
        System.out.println("Avg Search Time: "+hashMap.getAvgSearchTime()+"ms");


        System.out.println(hashMap.toString());

    }

    public static boolean takeInput(String question)
    {
        String option;
        Scanner scanner = new Scanner(System.in).useLocale(Locale.US);
        System.out.println(question);
        option=scanner.next();
        if(option.equals("1"))
            return true;

        else if(option.equals("2"))
            return false;

        else{
            // Print error message
            System.out.println("You entered wrong input option set '1' as default");
            return true;

        }

    }
}
