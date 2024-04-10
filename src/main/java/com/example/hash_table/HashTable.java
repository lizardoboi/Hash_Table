package com.example.hash_table;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class HashTable<K, V> {
    private static final int INITIAL_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.75;
    private static final int MULTIPLIER = 31;

    private List<Node<K, V>>[] table;
    private int size;

    public HashTable() {
        table = new List[INITIAL_CAPACITY];
        size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }
    public List<Node<K, V>>[] getTable() {
        return table;
    }

    public void clear() {
        for (int i = 0; i < table.length; i++) {
            table[i] = null;
        }
        size = 0;
    }

    public V get(K key) {
        int index = hash(key);
        List<Node<K, V>> chain = table[index];
        if (chain != null) {
            for (Node<K, V> node : chain) {
                if (node.key.equals(key)) {
                    return node.value;
                }
            }
        }
        return null;
    }

    public void put(K key, V value) {
        int index = hash(key);
        if (collisionResolution == CollisionResolution.CHAINING) {
            if (table[index] == null) {
                table[index] = new ArrayList<>();
            }

            for (Node<K, V> node : table[index]) {
                if (node.key.equals(key)) {
                    node.value = value;
                    return;
                }
            }

            table[index].add(new Node<>(key, value));
            size++;

            if (size >= LOAD_FACTOR * table.length) {
                resize();
            }
        } else if (collisionResolution == CollisionResolution.OPEN_ADDRESSING) {
            while (table[index] != null && !table[index].get(0).key.equals(key)) {
                index = (index + 1) % table.length;
            }

            if (table[index] == null) {
                table[index] = new ArrayList<>();
            }

            table[index].add(new Node<>(key, value));
            size++;

            if (size >= LOAD_FACTOR * table.length) {
                resize();
            }
        }
    }

    public void remove(K key) {
        int index = hash(key);
        if (collisionResolution == CollisionResolution.CHAINING) {
            List<Node<K, V>> chain = table[index];
            if (chain != null) {
                Iterator<Node<K, V>> iterator = chain.iterator();
                while (iterator.hasNext()) {
                    Node<K, V> node = iterator.next();
                    if (node.key.equals(key)) {
                        iterator.remove();
                        size--;
                        return;
                    }
                }
            }
        } else if (collisionResolution == CollisionResolution.OPEN_ADDRESSING) {
            while (table[index] != null && !table[index].get(0).key.equals(key)) {
                index = (index + 1) % table.length;
            }
            if (table[index] != null && table[index].get(0).key.equals(key)) {
                table[index] = null;
                size--;
            }
        }
    }

    public void printTable() {
        for (int i = 0; i < table.length; i++) {
            System.out.print("[" + i + "]: ");
            if (table[i] != null) {
                for (Node<K, V> node : table[i]) {
                    System.out.print("(" + node.key + ", " + node.value + ") ");
                }
            }
            System.out.println();
        }
    }
    public String getTableAsString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < table.length; i++) {
            sb.append("[").append(i).append("]: ");
            if (table[i] != null) {
                for (Node<K, V> node : table[i]) {
                    sb.append("(").append(node.key).append(", ").append(node.value).append(") ");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }
    public int getTableSize() {
        return table.length;
    }

    public int getElementCount() {
        return size;
    }

    public V search(K key) {
        int index = hash(key);
        List<Node<K, V>> chain = table[index];
        if (chain != null) {
            for (Node<K, V> node : chain) {
                if (node.key.equals(key)) {
                    return node.value;
                }
            }
        }
        return null;
    }

    public int hash(K key) {
        int hash = 0;
        for (int i = 0; i < key.toString().length(); i++) {
            hash = (MULTIPLIER * hash + key.toString().charAt(i)) % table.length;
        }
        return hash;
    }

    private void resize() {
        List<Node<K, V>>[] oldTable = table;
        table = new List[2 * oldTable.length];
        size = 0;

        for (List<Node<K, V>> chain : oldTable) {
            if (chain != null) {
                for (Node<K, V> node : chain) {
                    put(node.key, node.value);
                }
            }
        }
    }
    public enum CollisionResolution {
        CHAINING,
        OPEN_ADDRESSING
    }

    private CollisionResolution collisionResolution = CollisionResolution.CHAINING;

    public void setCollisionResolution(CollisionResolution resolution) {
        this.collisionResolution = resolution;
    }

    public CollisionResolution getCollisionResolution() {
        return collisionResolution;
    }

    public Iterator<V> begin() {
        return new HashTableIterator<>(this);
    }

    public Iterator<V> end() {
        return new HashTableIterator<>(this) {
            @Override
            public boolean hasNext() {
                return false; // Всегда возвращает false, так как это "неустановленный" итератор
            }

            @Override
            public V next() {
                throw new NoSuchElementException("End iterator has no next element");
            }
        };
    }

    public static class Node<K, V> {
        private K key;
        private V value;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }
    }
}
