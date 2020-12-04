class Entry<K, V>
{
    final K key;
    int value;

    int dib;

    public Entry(K key, int value,int dib, Entry<K, V> next) {
        this.key = key;
        this.value = value;

    }

    public K getKey() {
        return key;
    }
    public int getValue() {
        return value;
    }


}
