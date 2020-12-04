import java.math.BigInteger;
import java.util.Random;

public class Map<K, V> {
    // Array variables
    private Entry[] buckets;
    private int capacity; // 16
    private int size = 0;

    // Variables that are took from user
    private double lf;
    private String hashMethod;

    // Variables for performance monitoring
    private long minSearchTime;
    private long maxSearchTime;
    private long avgSearchTime;
    private int totalCollision;

    // Constructor
    public Map(double lf,String hashMethod) {
        this.capacity =997;
        this.buckets = new Entry[this.capacity];
        this.totalCollision=0;
        this.lf=lf;
        this.minSearchTime=99999;
        this.maxSearchTime=0;
        this.avgSearchTime=-1;
        this.totalCollision=0;
        this.hashMethod=hashMethod;
    }
    // Getter Setter Methods
    public int getTotalCollision(){
        return totalCollision;
    }
    public long getMaxSearchTime(){ return maxSearchTime; }

    public long getMinSearchTime(){ return minSearchTime; }

    public void setMinSearchTime(long val){ this.minSearchTime=val; }

    public void setMaxSearchTime(long val){ this.maxSearchTime=val; }

    public long getAvgSearchTime(){ return (this.maxSearchTime-this.minSearchTime)/2; }


    public void put(K key, int value,int dib) {
        if (size == lf * capacity) {
            reSize();
        }

        Entry<K, V> entry = new Entry<>(key, value,dib, null);
        Entry<K, V> entryToAddAgain;
        //Entry<K,V> entryToAddAgain;
        int bucket = Math.abs(getHash(key) % getBucketSize());


        Entry existing = buckets[bucket];
        if (existing == null) {
            buckets[bucket] = entry;
            size++;
        }
        else {
            // Assume that there is a collision here
            totalCollision++;
            // compare the keys see if key already exists
            while (existing!= null)
            {
                // Check key for counting
                if (existing.key.equals(key)) {
                    existing.value+=value;
                    // We assume that collision happened
                    // but actually same word inserted again
                    // so decrease total collision
                    totalCollision--;
                    return;
                }

                // Check dib values
                if(entry.dib>existing.dib){
                    entryToAddAgain=existing;
                    buckets[bucket]=entry;
                    put(entryToAddAgain.key,entryToAddAgain.value,entryToAddAgain.dib);
                    return;
                }
                // increase size for preventing index out of bounds error
                if(bucket+1==buckets.length){
                    reSize();
                    // Word is tried to add again
                    put(key,value,dib);
                    return;
                }
                // jump to other bucket
                bucket++;
                existing = buckets[bucket];
                // increase dib value for entry
                entry.dib++;


            }
            // We reach null node so put new entry to this bucket
            buckets[bucket]=entry;
            size++;

        }
    }

// Set size of array using by laod factor
    public void reSize(){
            // rehash
            Entry[] old = buckets;

            capacity *= 2;
            size = 0;
            buckets = new Entry[capacity];

            // traverse all old entries and put them into new array
            for (Entry<K,V> e: old) {
                if(e != null){
                    put(e.key,e.value,e.dib);
                }

            }

    }

    public String get(K key) {
        int index= Math.abs(getHash(key) % getBucketSize());
        Entry bucket = buckets[index];

        while (bucket != null) {
            if (key.toString().equals(bucket.key)) {
                return "--------------------------------\n"
                        +
                        "Search: "+bucket.key+"\nKey: "+getHash(key) +"\nCount: "+ bucket.value+"\nindex: "+index;
            }
            bucket=buckets[index++];
        }
        return null;
    }

    public int size() {
        return size;
    }

    private int getBucketSize() {
        return buckets.length;
    }

    public  int getHash(K key)
    {
        if(hashMethod.equals("PAF"))
        {

            String convertedKey = key.toString();
            int hashVal = 0;
            for (int j = 0; j < convertedKey.length(); j++) {

                int letter = convertedKey.charAt(j) - 96;
                hashVal = hashVal * 33 + letter;

            }
            return hashVal;
        }
        else{ return myHashFunction(key); }

    }

    // Hash function that I used
    public int hash32(final byte[] data, int length, int seed) {
        // 'm' and 'r' are mixing constants generated offline.
        // They're not really 'magic', they just happen to work well.
        final int m = 0x5bd1e995;
        final int r = 24;

        // Initialize the hash to a random value
        int h = seed^length;
        int length4 = length/4;

        for (int i=0; i<length4; i++) {
            final int i4 = i*4;
            int k = (data[i4+0]&0xff) +((data[i4+1]&0xff)<<8)
                    +((data[i4+2]&0xff)<<16) +((data[i4+3]&0xff)<<24);
            k *= m;
            k ^= k >>> r;
            k *= m;
            h *= m;
            h ^= k;
        }

        // Handle the last few bytes of the input array
        switch (length%4) {
            case 3: h ^= (data[(length&~3) +2]&0xff) << 16;
            case 2: h ^= (data[(length&~3) +1]&0xff) << 8;
            case 1: h ^= (data[length&~3]&0xff);
                h *= m;
        }

        h ^= h >>> 13;
        h *= m;
        h ^= h >>> 15;

        return h;
    }

    public  int myHashFunction(final K key) {
        String text = key.toString();
        final byte[] bytes = text.getBytes();
        return hash32(bytes, bytes.length,3);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Entry entry : buckets) {

            sb.append("[");
            if(entry != null){
                sb.append(entry);
                sb.append(" dib:"+entry.dib);
            }


            sb.append("]");
        }
        return "{" + sb.toString() + "}";
    }

}