/**
 * ================================================================
 * MAIN CLASS - UseCase12DataPersistenceRecovery
 * ================================================================
 *
 * Use Case 12: Data Persistence & System Recovery
 *
 * @version 12.0
 */

import java.io.*;
import java.util.*;

// -------------------- INVENTORY --------------------
class RoomInventory {

    private Map<String, Integer> availability;

    public RoomInventory() {
        availability = new HashMap<>();
        availability.put("Single", 5);
        availability.put("Double", 3);
        availability.put("Suite", 2);
    }

    public Map<String, Integer> getAll() {
        return availability;
    }

    public void updateAvailability(String type, int count) {
        availability.put(type, count);
    }
}

// -------------------- PERSISTENCE SERVICE --------------------
class FilePersistenceService {

    // Save inventory to file
    public void saveInventory(RoomInventory inventory, String filePath) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {

            for (Map.Entry<String, Integer> entry : inventory.getAll().entrySet()) {
                writer.write(entry.getKey() + "=" + entry.getValue());
                writer.newLine();
            }

            System.out.println("Inventory saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving inventory.");
        }
    }

    // Load inventory from file
    public void loadInventory(RoomInventory inventory, String filePath) {

        File file = new File(filePath);

        if (!file.exists()) {
            System.out.println("No valid inventory data found. Starting fresh.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            String line;

            while ((line = reader.readLine()) != null) {

                String[] parts = line.split("=");

                if (parts.length == 2) {
                    String type = parts[0];
                    int count = Integer.parseInt(parts[1]);

                    inventory.updateAvailability(type, count);
                }
            }

            System.out.println("Inventory loaded successfully.");

        } catch (Exception e) {
            System.out.println("Error loading inventory. Starting fresh.");
        }
    }
}

// -------------------- MAIN --------------------
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("System Recovery");

        String filePath = "inventory.txt";

        RoomInventory inventory = new RoomInventory();
        FilePersistenceService persistence = new FilePersistenceService();

        // Load previous state
        persistence.loadInventory(inventory, filePath);

        // Display current inventory
        System.out.println();
        System.out.println("Current Inventory:");

        for (Map.Entry<String, Integer> entry : inventory.getAll().entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        // Save state
        persistence.saveInventory(inventory, filePath);
    }
}
