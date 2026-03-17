/**
 * ================================================================
 * MAIN CLASS - UseCase6RoomAllocationService
 * ================================================================
 *
 * Use Case 6: Reservation Confirmation & Room Allocation
 *
 * Description:
 * Demonstrates how booking requests are confirmed
 * and rooms are allocated safely.
 *
 * @version 6.0
 */

import java.util.*;

// -------------------- RESERVATION --------------------
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

// -------------------- BOOKING QUEUE --------------------
class BookingRequestQueue {
    private Queue<Reservation> queue = new LinkedList<>();

    public void addRequest(Reservation r) {
        queue.offer(r);
    }

    public Reservation getNextRequest() {
        return queue.poll();
    }

    public boolean hasPendingRequests() {
        return !queue.isEmpty();
    }
}

// -------------------- INVENTORY --------------------
class RoomInventory {
    private Map<String, Integer> availability = new HashMap<>();

    public RoomInventory() {
        availability.put("Single", 5);
        availability.put("Double", 3);
        availability.put("Suite", 2);
    }

    public int getAvailableRooms(String type) {
        return availability.getOrDefault(type, 0);
    }

    public void updateAvailability(String type, int count) {
        availability.put(type, count);
    }
}

// -------------------- ALLOCATION SERVICE --------------------
/**
 * Handles room allocation and prevents double booking.
 */
class RoomAllocationService {

    private Set<String> allocatedRoomIds;
    private Map<String, Set<String>> assignedRoomsByType;

    public RoomAllocationService() {
        allocatedRoomIds = new HashSet<>();
        assignedRoomsByType = new HashMap<>();
    }

    public void allocateRoom(Reservation reservation, RoomInventory inventory) {

        String type = reservation.getRoomType();

        int available = inventory.getAvailableRooms(type);

        if (available > 0) {

            String roomId = generateRoomId(type);

            // Track allocated room
            allocatedRoomIds.add(roomId);

            assignedRoomsByType
                .computeIfAbsent(type, k -> new HashSet<>())
                .add(roomId);

            // Update inventory
            inventory.updateAvailability(type, available - 1);

            // Confirmation output
            System.out.println("Booking confirmed for Guest: "
                    + reservation.getGuestName()
                    + ", Room ID: "
                    + roomId);

        } else {
            System.out.println("Booking failed for Guest: "
                    + reservation.getGuestName()
                    + " (No rooms available)");
        }
    }

    private String generateRoomId(String roomType) {

        int count = assignedRoomsByType
                .getOrDefault(roomType, new HashSet<>())
                .size() + 1;

        return roomType + "-" + count;
    }
}

// -------------------- MAIN CLASS --------------------
public class UseCase6RoomAllocationService {

    public static void main(String[] args) {

        System.out.println("Room Allocation Processing");

        // Initialize components
        BookingRequestQueue queue = new BookingRequestQueue();
        RoomInventory inventory = new RoomInventory();
        RoomAllocationService allocationService = new RoomAllocationService();

        // Add booking requests
        queue.addRequest(new Reservation("Abhi", "Single"));
        queue.addRequest(new Reservation("Subha", "Single"));
        queue.addRequest(new Reservation("Vanmathi", "Suite"));

        // Process queue
        while (queue.hasPendingRequests()) {
            Reservation r = queue.getNextRequest();
            allocationService.allocateRoom(r, inventory);
        }
    }
}
