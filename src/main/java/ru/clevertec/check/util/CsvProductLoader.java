package ru.clevertec.check.util;

import ru.clevertec.check.dto.Product;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CsvProductLoader {

    private static final Map<Integer, Product> products = new HashMap<>();

    private CsvProductLoader() {
        throw new IllegalStateException("Utility class");
    }

    public static void loadProducts(String path) {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                Integer id = Integer.parseInt(parts[0]);
                String description = parts[1];
                double price = Double.parseDouble(parts[2]);
                int quantityInStock = Integer.parseInt(parts[3]);
                boolean wholesaleProduct = Boolean.parseBoolean(parts[4]);
                Product product = new Product(id, description, price, quantityInStock, wholesaleProduct);
                products.put(id, product);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map<Integer, Product> getProducts() {
        return products;
    }
}
