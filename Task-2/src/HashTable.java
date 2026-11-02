import java.util.HashMap;
import java.util.Locale;

public class HashTable {
    static class Entry { String key; int value; Entry next; Entry(String k,int v){key=k;value=v;} }
    static class ChainedHashTable {
        Entry[] buckets;
        ChainedHashTable(int capacity){buckets=new Entry[capacity];}
        int index(String key){return Math.floorMod(key.hashCode(), buckets.length);}
        void put(String key,int value){int i=index(key); for(Entry e=buckets[i];e!=null;e=e.next) if(e.key.equals(key)){e.value=value;return;} Entry n=new Entry(key,value); n.next=buckets[i]; buckets[i]=n;}
        Integer get(String key){for(Entry e=buckets[index(key)];e!=null;e=e.next) if(e.key.equals(key)) return e.value; return null;}
        int chainLength(String key){int n=0;for(Entry e=buckets[index(key)];e!=null;e=e.next)n++;return n;}
    }
    public static void main(String[] args) {
        ChainedHashTable demo = new ChainedHashTable(101);
        demo.put("Aa", 1); demo.put("BB", 2); // Java String hash collision: both hashCode values are 2112.
        System.out.println("Collision demo: hash(Aa)="+"Aa".hashCode()+", hash(BB)="+"BB".hashCode());
        System.out.println("Chain length after inserting both keys: "+demo.chainLength("Aa"));
        System.out.println("Lookup BB: "+demo.get("BB"));

        int count=100_000; ChainedHashTable custom=new ChainedHashTable(131101); HashMap<String,Integer> map=new HashMap<>(count*2); String[] keys=new String[count];
        for(int i=0;i<count;i++){keys[i]=String.format(Locale.ROOT,"SKU-%06d",i);custom.put(keys[i],i);map.put(keys[i],i);}
        long checksum=0;
        for(int r=0;r<2;r++) for(String k:keys){checksum+=custom.get(k);checksum+=map.get(k);}
        long t1=System.nanoTime(); for(String k:keys) checksum+=custom.get(k); long customTime=System.nanoTime()-t1;
        long t2=System.nanoTime(); for(String k:keys) checksum+=map.get(k); long mapTime=System.nanoTime()-t2;
        System.out.printf(Locale.ROOT,"Custom chained table lookup: %.3f ms%n",customTime/1_000_000.0);
        System.out.printf(Locale.ROOT,"Built-in HashMap lookup: %.3f ms%n",mapTime/1_000_000.0);
        System.out.println("Checksum: "+checksum);
        System.out.println("Note: timings vary by machine and JVM; this is a simple demonstration, not a formal benchmark.");
    }
}
