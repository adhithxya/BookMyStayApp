/**
 * ================================================================
 * MAIN CLASS - UseCase7AddOnServiceSelection
 * ================================================================
 *
 * Use Case 7: Add-On Service Selection
 *
 * Description:
 * Demonstrates how optional services can be attached
 * to a confirmed reservation.
 *
 * @version 7.0
 */

import java.util.*;

// -------------------- SERVICE --------------------
/**
 * Represents an optional add-on service.
 */
class Service {

    private String serviceName;
    private double cost;

    public Service(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getCost() {
        return cost;
    }
}

// -------------------- SERVICE MANAGER --------------------
/**
 * Manages services linked to reservations.
 */
class AddOnServiceManager {

    private Map<String, List<Service>> servicesByReservation;

    public AddOnServiceManager() {
        servicesByReservation = new HashMap<>();
    }

    public void addService(String reservationId, Service service) {

        servicesByReservation
                .computeIfAbsent(reservationId, k -> new ArrayList<>())
                .add(service);
    }

    public double calculateTotalServiceCost(String reservationId) {

        List<Service> services = servicesByReservation.get(reservationId);

        double total = 0.0;

        if (services != null) {
            for (Service s : services) {
                total += s.getCost();
            }
        }

        return total;
    }
}

// -------------------- MAIN CLASS --------------------
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("Add-On Service Selection");

        // Example reservation ID
        String reservationId = "Single-1";

        // Initialize service manager
        AddOnServiceManager manager = new AddOnServiceManager();

        // Create services
        Service breakfast = new Service("Breakfast", 500.0);
        Service spa = new Service("Spa", 1000.0);

        // Add services to reservation
        manager.addService(reservationId, breakfast);
        manager.addService(reservationId, spa);

        // Calculate total cost
        double totalCost = manager.calculateTotalServiceCost(reservationId);

        // Output
        System.out.println("Reservation ID: " + reservationId);
        System.out.println("Total Add-On Cost: " + totalCost);
    }
}
