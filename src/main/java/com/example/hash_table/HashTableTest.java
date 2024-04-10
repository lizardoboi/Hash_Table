package com.example.hash_table;
import java.util.Random;

public class HashTableTest {
    // Метод для оценки качества хеширования методом квадрата отклонения
    private static double evaluateHashQuality(HashTable<Integer, String> hashTable) {
        double deviation = 0;
        int dataSize = hashTable.size();

        for (int i = 0; i < dataSize; i++) {
            int key = i;
            String value = "Value" + i;
            hashTable.put(key, value);
        }

        deviation = calculateDeviation(hashTable);

        hashTable.clear();

        return deviation;
    }

    // Метод для оценки средней трудоемкости операции вставки для заданного коэффициента заполнения
    private static void evaluateInsertionComplexity(HashTable<Integer, String> hashTable, int dataSize, double loadFactor) {
        Random random = new Random();
        long totalTime = 0;

        // Определение размера таблицы в зависимости от коэффициента заполнения
        int tableSize = (int) (dataSize / loadFactor);
        hashTable = new HashTable<>(); // Создание новой таблицы с измененным размером
        hashTable.setCollisionResolution(HashTable.CollisionResolution.CHAINING); // Или установка типа разрешения коллизий, если это необходимо

        for (int i = 0; i < dataSize; i++) {
            int key = random.nextInt(dataSize);
            String value = "Value" + i;

            long startTime = System.nanoTime();
            hashTable.put(key, value);
            long endTime = System.nanoTime();

            totalTime += (endTime - startTime);
        }

        long averageTime = totalTime / dataSize;
        System.out.println("Среднее время вставки для коэффициента заполнения " + loadFactor + ": " + averageTime + " наносекунд");
    }

    // Метод для вычисления квадрата отклонения
    private static double calculateDeviation(HashTable<Integer, String> hashTable) {
        int[] distribution = new int[hashTable.getTableSize()];
        int dataSize = hashTable.size();
        double deviation = 0;

        for (int i = 0; i < dataSize; i++) {
            int key = i;
            int index = hashTable.hash(key);
            distribution[index]++;
        }

        int idealSize = dataSize / hashTable.getTableSize();

        for (int i = 0; i < hashTable.getTableSize(); i++) {
            deviation += Math.pow(distribution[i] - idealSize, 2);
        }

        return deviation;
    }
    public static void main(String[] args) {
        // Создание хеш-таблицы с цепочками коллизий
        HashTable<Integer, String> chainingHashTable = new HashTable<>();
        chainingHashTable.setCollisionResolution(HashTable.CollisionResolution.CHAINING);

        // Создание хеш-таблицы с открытой адресацией
        HashTable<Integer, String> openAddressingHashTable = new HashTable<>();
        openAddressingHashTable.setCollisionResolution(HashTable.CollisionResolution.OPEN_ADDRESSING);

        // Генерация случайных данных для заполнения таблиц
        Random random = new Random();
        int dataSize = 100; // Размер данных для вставки
        for (int i = 0; i < dataSize; i++) {
            int key = random.nextInt(dataSize); // Случайный ключ
            String value = "Value" + i; // Значение для вставки
            chainingHashTable.put(key, value);
            openAddressingHashTable.put(key, value);
        }

        // Поиск элементов (для оценки трудоемкости поиска)
        int searchOperations = 1000; // Количество операций поиска для оценки
        for (int i = 0; i < searchOperations; i++) {
            int keyToSearch = random.nextInt(dataSize); // Случайный ключ для поиска
            chainingHashTable.get(keyToSearch);
            openAddressingHashTable.get(keyToSearch);
        }

        // Удаление элементов (для оценки трудоемкости удаления)
        int removeOperations = 1000; // Количество операций удаления для оценки
        for (int i = 0; i < removeOperations; i++) {
            int keyToRemove = random.nextInt(dataSize); // Случайный ключ для удаления
            chainingHashTable.remove(keyToRemove);
            openAddressingHashTable.remove(keyToRemove);
        }

        // Оценка средней трудоемкости операции вставки (можно добавить аналогично)
        int insertOperations = 1000; // Количество операций вставки для оценки
        long chainingInsertTime = 0; // Время вставки в хеш-таблицу с цепочками коллизий
        long openAddressingInsertTime = 0; // Время вставки в хеш-таблицу с открытой адресацией
        for (int i = 0; i < insertOperations; i++) {
            int keyToInsert = random.nextInt(dataSize); // Случайный ключ для вставки
            String valueToInsert = "Value" + i; // Значение для вставки

            long startTime = System.nanoTime();
            chainingHashTable.put(keyToInsert, valueToInsert);
            long endTime = System.nanoTime();
            chainingInsertTime += (endTime - startTime);

            startTime = System.nanoTime();
            openAddressingHashTable.put(keyToInsert, valueToInsert);
            endTime = System.nanoTime();
            openAddressingInsertTime += (endTime - startTime);
        }

        // Вывод среднего времени вставки для каждой таблицы
        System.out.println("Среднее время вставки в хеш-таблицу с цепочками коллизий: " + (chainingInsertTime / insertOperations) + " наносекунд");
        System.out.println("Среднее время вставки в хеш-таблицу с открытой адресацией: " + (openAddressingInsertTime / insertOperations) + " наносекунд");

        // Оценка средней трудоемкости операции поиска
        long chainingSearchTime = 0; // Время поиска в хеш-таблице с цепочками коллизий
        long openAddressingSearchTime = 0; // Время поиска в хеш-таблице с открытой адресацией
        for (int i = 0; i < searchOperations; i++) {
            int keyToSearch = random.nextInt(dataSize); // Случайный ключ для поиска

            long startTime = System.nanoTime();
            chainingHashTable.search(keyToSearch);
            long endTime = System.nanoTime();
            chainingSearchTime += (endTime - startTime);

            startTime = System.nanoTime();
            openAddressingHashTable.search(keyToSearch);
            endTime = System.nanoTime();
            openAddressingSearchTime += (endTime - startTime);
        }

        // Вывод среднего времени поиска для каждой таблицы
        System.out.println("Среднее время поиска в хеш-таблице с цепочками коллизий: " + (chainingSearchTime / searchOperations) + " наносекунд");
        System.out.println("Среднее время поиска в хеш-таблице с открытой адресацией: " + (openAddressingSearchTime / searchOperations) + " наносекунд");

        // Оценка средней трудоемкости операции удаления
        long chainingRemoveTime = 0; // Время удаления в хеш-таблице с цепочками коллизий
        long openAddressingRemoveTime = 0; // Время удаления в хеш-таблице с открытой адресацией
        for (int i = 0; i < removeOperations; i++) {
            int keyToRemove = random.nextInt(dataSize); // Случайный ключ для удаления

            long startTime = System.nanoTime();
            chainingHashTable.remove(keyToRemove);
            long endTime = System.nanoTime();
            chainingRemoveTime += (endTime - startTime);

            startTime = System.nanoTime();
            openAddressingHashTable.remove(keyToRemove);
            endTime = System.nanoTime();
            openAddressingRemoveTime += (endTime - startTime);
        }

        // Вывод среднего времени удаления для каждой таблицы
        System.out.println("Среднее время удаления в хеш-таблице с цепочками коллизий: " + (chainingRemoveTime / removeOperations) + " наносекунд");
        System.out.println("Среднее время удаления в хеш-таблице с открытой адресацией: " + (openAddressingRemoveTime / removeOperations) + " наносекунд");

        // Оценка качества хеширования методом квадрата отклонения
        double chainingDeviation = evaluateHashQuality(chainingHashTable);
        double openAddressingDeviation = evaluateHashQuality(openAddressingHashTable);
        System.out.println("Квадрат отклонения для хеш-таблицы с цепочками коллизий: " + chainingDeviation);
        System.out.println("Квадрат отклонения для хеш-таблицы с открытой адресацией: " + openAddressingDeviation);

        // Оценка средней трудоемкости операции вставки для разных коэффициентов заполнения таблиц
        for (double loadFactor = 0.1; loadFactor <= 1.0; loadFactor += 0.1) {
            // Оценка средней трудоемкости операции вставки
            System.out.println("Оценка средней трудоемкости операции вставки для коэффициента заполнения " + loadFactor);
            evaluateInsertionComplexity(chainingHashTable, dataSize, loadFactor);
            evaluateInsertionComplexity(openAddressingHashTable, dataSize, loadFactor);
        }

    }

}
