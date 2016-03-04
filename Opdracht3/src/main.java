import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


public class Main {

    public static void main(String[] args) {

        //exercise1();
        //exercise2();
        //exercise3();
        exercise4();

    }

    public static void exercise1() {

        PageRank pr = new PageRank();

        //pr.importData("C:\\Users\\TU Delf SID\\Documents\\GitHub\\MineTheShit\\Opdracht3\\data\\example.txt");
        pr.importData("data/example.txt");
        System.out.println(pr.calculatePageRank(10));

    }

    public static void exercise2() {

        PageRank pr = new PageRank();

        //pr.importData("C:\\Users\\TU Delf SID\\Documents\\GitHub\\MineTheShit\\Opdracht3\\data\\example2.txt");
        pr.importData("data/example2.txt");
        System.out.println(pr.calculatePageRank(12));

    }

    public static void exercise3() {

        TaxationPageRank tpr = new TaxationPageRank(0.8);

        //tpr.importData("C:\\Users\\TU Delf SID\\Documents\\GitHub\\MineTheShit\\Opdracht3\\data\\example2.txt");
        tpr.importData("data/example2.txt");
        System.out.println(tpr.calculatePageRank(20));

    }

    public static void exercise4() {

        TaxationPageRank tpr = new TaxationPageRank(0.8);
        //tpr.importData("C:\\Users\\TU Delf SID\\Documents\\GitHub\\MineTheShit\\Opdracht3\\data\\flight_data.txt");
        tpr.importData("data/flight_data.txt");

        Iterator<Entry<String, Double>> iter = sortByValues(tpr.calculatePageRank(20)).entrySet().iterator();
        Entry<String, Double> current = iter.next();

        System.out.println(current); //first element in sortedList

    }

    /*
     * Java method to sort Map in Java by value e.g. HashMap or Hashtable
     * throw NullPointerException if Map contains null values
     * It also sort values even if they are duplicates
     */
    public static <K extends Comparable, V extends Comparable> Map<K, V> sortByValues(Map<K, V> map) {
        List<Map.Entry<K, V>> entries = new LinkedList<Map.Entry<K, V>>(map.entrySet());

        Collections.sort(entries, new Comparator<Map.Entry<K, V>>() {

            @Override
            public int compare(Entry<K, V> o1, Entry<K, V> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });

        //LinkedHashMap will keep the keys in the order they are inserted
        //which is currently sorted on natural ordering
        Map<K, V> sortedMap = new LinkedHashMap<K, V>();

        for (Map.Entry<K, V> entry : entries) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }

}
