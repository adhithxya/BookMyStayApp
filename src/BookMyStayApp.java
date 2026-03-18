import java.util.*;


class Booking {
    String reservationId;
    String roomType;
    boolean isActive;

    Booking(String reservationId, String roomType) {
        this.reservationId = reservationId;
        this.roomType = roomType;
        this.isActive = true;
    }
}


class CancellationService {

    // Inventory for room types
    private Map<String, Integer> inventory = new HashMap<>();

    // Active bookings
    private Map<String, Booking> bookings = new HashMap<>();

    
    private Stack<String> rollbackStack = new Stack<>();

    public CancellationService() {
        
        inventory.put("Single", 5);
    }

    
    public void createBooking(String reservationId, String roomType) {
        if (inventory.get(roomType) > 0) {
            inventory.put(roomType, inventory.get(roomType) - 1);
            bookings.put(reservationId, new Booking(reservationId, roomType));
        }
    }

    
    public void cancelBooking(String reservationId) {

        System.out.println("Booking Cancellation");

        
        if (!bookings.containsKey(reservationId)) {
            System.out.println("Invalid cancellation. Reservation does not exist.");
            return;
        }

        Booking booking = bookings.get(reservationId);

        if (!booking.isActive) {
            System.out.println("Booking already cancelled.");
            return;
        }

        
        rollbackStack.push(reservationId);

        
        String roomType = booking.roomType;
        inventory.put(roomType, inventory.get(roomType) + 1);

        
        booking.isActive = false;

      
        System.out.println("Booking cancelled successfully. Inventory restored for room type: " + roomType);

        
        System.out.println("\nRollback History (Most Recent First):");
        for (int i = rollbackStack.size() - 1; i >= 0; i--) {
            System.out.println("Released Reservation ID: " + rollbackStack.get(i));
        }

        
        System.out.println("\nUpdated " + roomType + " Room Availability: " + inventory.get(roomType));
    }
}


public class BookMyStayApp {

    public static void main(String[] args) {

        CancellationService service = new CancellationService();

        
        service.createBooking("Single-1", "Single");

        
        service.cancelBooking("Single-1");
    }
}
