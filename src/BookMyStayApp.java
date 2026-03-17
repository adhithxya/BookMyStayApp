/**
 * ================================================================
 * MAIN CLASS - UseCase5BookingRequestQueue
 * ================================================================
 *
 * Use Case 5: Booking Request (First-Come-First-Served)
 *
 * Description:
 * Demonstrates how booking requests are queued
 * and processed in a fair FIFO order.
 *
 * No inventory update is performed here.
 *
 * @version 5.0
 */

import java.util.LinkedList;
import java.util.Queue;

// -------------------- RESERVATION --------------------
/**
 * Represents a booking request made by a guest.
 */
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
/**
 * Manages booking requests using FIFO queue.
 */
class BookingRequestQueue {

    private Queue<Reservation> requestQueue;

    public BookingRequestQueue() {
        requestQueue = new LinkedList<>();
    }

    public void addRequest(Reservation reservation) {
        requestQueue.offer(reservation);
    }

    public Reservation getNextRequest() {
        return requestQueue.poll();
    }

    public boolean hasPendingRequests() {
        return !requestQueue.isEmpty();
    }
}

// -------------------- MAIN CLASS --------------------
public class UseCase5BookingRequestQueue {

    public static void main(String[] args) {

        // Display header
        System.out.println("Booking Request Queue");

        // Initialize queue
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        // Create booking requests
        Reservation r1 = new Reservation("Abhi", "Single");
        Reservation r2 = new Reservation("Subha", "Double");
        Reservation r3 = new Reservation("Vanmathi", "Suite");

        // Add to queue
        bookingQueue.addRequest(r1);
        bookingQueue.addRequest(r2);
        bookingQueue.addRequest(r3);

        // Process in FIFO order
        while (bookingQueue.hasPendingRequests()) {
            Reservation current = bookingQueue.getNextRequest();

            System.out.println(
                "Processing booking for Guest: " +
                current.getGuestName() +
                ", Room Type: " +
                current.getRoomType()
            );
        }
    }
}
