/**
 * ================================================================
 * MAIN CLASS - UseCase9ErrorHandlingValidation
 * ================================================================
 *
 * Use Case 9: Error Handling & Validation
 *
 * Description:
 * Demonstrates how user input is validated
 * before booking is processed.
 *
 * @version 9.0
 */

import java.util.*;

// -------------------- CUSTOM EXCEPTION --------------------
class InvalidBookingException extends Exception {

    public InvalidBookingException(String message) {
        super(message);
    }
}

// -------------------- INVENTORY --------------------
class RoomInventory {

    private Set<String> validRoomTypes;

    public RoomInventory() {
        validRoomTypes = new HashSet<>();
        validRoomTypes.add("Single");
        validRoomTypes.add("Double");
        validRoomTypes.add("Suite");
    }

    public boolean isValidRoomType(String type) {
        return validRoomTypes.contains(type);
    }
}

// -------------------- VALIDATOR --------------------
class ReservationValidator {

    public void validate(
            String guestName,
            String roomType,
            RoomInventory inventory
    ) throws InvalidBookingException {

        // Validate guest name
        if (guestName == null || guestName.trim().isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty.");
        }

        // Normalize input (case handling)
        String normalizedRoomType =
                roomType.substring(0, 1).toUpperCase() +
                roomType.substring(1).toLowerCase();

        // Validate room type
        if (!inventory.isValidRoomType(normalizedRoomType)) {
            throw new InvalidBookingException("Invalid room type selected.");
        }
    }
}

// -------------------- BOOKING QUEUE --------------------
class BookingRequestQueue {
    private Queue<String> queue = new LinkedList<>();

    public void addRequest(String request) {
        queue.offer(request);
    }
}

// -------------------- MAIN CLASS --------------------
public class UseCase9ErrorHandlingValidation {

    public static void main(String[] args) {

        // Header
        System.out.println("Booking Validation");

        Scanner scanner = new Scanner(System.in);

        RoomInventory inventory = new RoomInventory();
        ReservationValidator validator = new ReservationValidator();
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        try {

            // Input
            System.out.print("Enter guest name: ");
            String guestName = scanner.nextLine();

            System.out.print("Enter room type (Single/Double/Suite): ");
            String roomType = scanner.nextLine();

            // Validate
            validator.validate(guestName, roomType, inventory);

            // If valid, add to queue
            bookingQueue.addRequest(guestName + " - " + roomType);

            System.out.println("Booking request accepted.");

        } catch (InvalidBookingException e) {

            System.out.println("Booking failed: " + e.getMessage());

        } finally {
            scanner.close();
        }
    }
}
