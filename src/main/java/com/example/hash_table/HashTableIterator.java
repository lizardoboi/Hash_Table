package com.example.hash_table;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class HashTableIterator<K, V> implements Iterator<V> {
    private final HashTable<K, V> hashTable;
    private int currentIndex;
    private Iterator<HashTable.Node<K, V>> currentChainIterator;

    public HashTableIterator(HashTable<K, V> hashTable) {
        this.hashTable = hashTable;
        this.currentIndex = -1;
        this.currentChainIterator = null;
    }

    @Override
    public boolean hasNext() {
        if (currentChainIterator != null && currentChainIterator.hasNext()) {
            return true;
        }

        for (int i = currentIndex + 1; i < hashTable.getTable().length; i++) {
            if (hashTable.getTable()[i] != null && !hashTable.getTable()[i].isEmpty()) {
                return true;
            }
        }

        return false;
    }

    @Override
    public V next() {
        if (!hasNext()) {
            throw new NoSuchElementException("No more elements in the hash table");
        }

        if (currentChainIterator == null || !currentChainIterator.hasNext()) {
            while (hashTable.getTable()[++currentIndex] == null || hashTable.getTable()[currentIndex].isEmpty()) ;
            currentChainIterator = hashTable.getTable()[currentIndex].iterator();
        }

        return currentChainIterator.next().getValue();
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Remove operation is not supported by HashTableIterator");
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (this == obj) return true;
        if (getClass() != obj.getClass()) return false;

        HashTableIterator<?, ?> other = (HashTableIterator<?, ?>) obj;
        return this.currentIndex == other.currentIndex && this.hashTable == other.hashTable;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + currentIndex;
        result = 31 * result + (hashTable == null ? 0 : hashTable.hashCode());
        return result;
    }
}
