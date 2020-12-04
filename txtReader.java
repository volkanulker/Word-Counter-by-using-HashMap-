// Java Program to illustrate reading from FileReader
// using BufferedReader

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Scanner;

public class txtReader
{
    public void readAndAddToMap(String pathname, Map<String, Integer>  mapToAdd) throws FileNotFoundException {
        File file = new File(pathname);
        Scanner input = new Scanner(file);

        while (input.hasNext()) {
            String word  = input.next();
            mapToAdd.put(word.toLowerCase(Locale.ENGLISH),1,0);

        }

    }
    public void readAndGet(String pathname,Map<String, Integer>  mapToSearch) throws FileNotFoundException {
        File file = new File(pathname);
        Scanner input = new Scanner(file);

        long startSearch;
        long endSearch;
        long searchTime;
        while (input.hasNext()) {
            // read a word
            String word  = input.next();
            // Measure search time
            startSearch = System.currentTimeMillis();
            System.out.println(mapToSearch.get(word.toLowerCase()));
            endSearch = System.currentTimeMillis();
            searchTime = endSearch - startSearch;

            // Check for minimum search time
            if(searchTime<mapToSearch.getMinSearchTime()){
                mapToSearch.setMinSearchTime(searchTime);
            }
            if(searchTime>mapToSearch.getMaxSearchTime()){
                mapToSearch.setMaxSearchTime(searchTime);
            }
        }
    }

}
