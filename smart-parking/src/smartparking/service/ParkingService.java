package smartparking.service;

import java.util.ArrayList;
import smartparking.dto.ParkingLot;
import smartparking.dto.ParkingSlot;
import smartparking.dto.ParkingTicket;
import smartparking.dto.Vehicle;
import smartparking.view.ParkingReport;

public class ParkingService {

    private final ParkingLot parkingLot;// added final to not allow parkingLot to be reassigned
    private  ArrayList<ParkingTicket> tickets = new ArrayList<>();// added final to not allow tickets to be reassigned

    private int nextTicketNumber = 1001;

    public ParkingService(ParkingLot parkingLot, ArrayList<ParkingTicket> history) {
        this.parkingLot = parkingLot;
        tickets.addAll(history);
        restoreTicketCounter();
        restoreOccupiedSlots();
    }

    //-------------------------------------------------------------------
    private void restoreTicketCounter() {
        for (ParkingTicket ticket : tickets) {
            int number = Integer.parseInt(ticket.getTicketId().substring(1));
            if (number >= nextTicketNumber) {
                nextTicketNumber = number + 1;
            }
        }
    }

    //-------------------------------------------------------------------------------
    private void restoreOccupiedSlots() {
        for (ParkingTicket ticket : tickets) {
            // i changed !ticket.isClosed(). to  this --> ticket.isClosed()
            if (!ticket.isClosed()) {
                ParkingSlot slot = parkingLot.getSlot(ticket.getSlotNumber());
                if (slot != null) {
                    slot.assignVehicle(ticket.getVehicle());
                }
            }
        }
    }

    //---------------------------------------------------------------------------------
    public ParkingTicket parkVehicle(Vehicle vehicle, int time) {
        String reg = vehicle.getRegistrationNumber();
        String ticketId = "T" + nextTicketNumber;
        nextTicketNumber++;
        System.out.println("\n>> " + vehicle + " arrives at the entrance at " + ParkingReport.formatTime(time));

        if (findActiveTicket(reg) != null) {
            System.out.println("   " + reg + " is already parked inside. Request refused.");
            return null;
        }

        ParkingSlot slot = findAvailableSlot();
        if (slot == null) {
            System.out.println("   Sorry, the parking lot is FULL. " + reg + " cannot enter.");
            return null;
        }

        slot.assignVehicle(vehicle);
        ParkingTicket ticket = new ParkingTicket(ticketId, vehicle, slot.getSlotNumber(), time);
        tickets.add(ticket);

        System.out.println("   Slot " + slot.getSlotNumber() + " assigned. Ticket " + ticket.   getTicketId() + " issued.");
        System.out.println("   Gate opens. " + reg + " parks in Slot " + slot.getSlotNumber() + ".");
        return ticket;
    }

    //--------------------------------------------------------------------------------
    public ParkingTicket exitVehicle(String registrationNumber, int time) {
        String reg = registrationNumber.toUpperCase();
        System.out.println("\n<< " + reg + " requests exit at " + ParkingReport.formatTime(time));

        ParkingTicket ticket = findActiveTicket(reg);
        if (ticket == null) {
            System.out.println("   No active ticket for " + reg + ".");
            return null;
        }

        //---------------------------------------------------------------------------------
        ParkingSlot slot = parkingLot.getSlot(ticket.getSlotNumber());
        if (slot == null) {
            System.out.println("   Could not find Slot " + ticket.getSlotNumber() + " for " + reg + ".");
            return null;
        }

        //------------------------------------------------------------------------
        ticket.close(time);
        System.out.println("   Ticket " + ticket.getTicketId() + ": parked " + ticket.getDurationMinutes()
                + " min, charged " + ticket.getChargeableHours() + " h x Rs." + ticket.getVehicle().getHourlyRate()
                + " = Rs." + ticket.getFee());

        slot.release();
        System.out.println("   Slot " + slot.getSlotNumber() + " is now free. Goodbye " + reg + "!");
        return ticket;
    }
    //------------------------------------------------------------------------------------------------
// I changed some thing in this place I  hope it work  and I don't forgot the change I made
    private ParkingSlot findAvailableSlot() {
    ParkingSlot freeSlot = null;

    // freeSlot = parkingLot.getSlots().get(0); no need of this line bro
        for (ParkingSlot slot : parkingLot.getSlots()) {
            if (!slot.isOccupied()) {
                freeSlot = slot;
                break;
            }

        }
        return freeSlot;
    }

    private ParkingTicket findActiveTicket(String registrationNumber) {
        for (ParkingTicket ticket : tickets) {
            // bug fixed bro 
            if (!ticket.isClosed() && ticket.getVehicle().getRegistrationNumber().equals(registrationNumber)) { // added check for active ticket
                System.out.println(ticket);
                return ticket;
                
            }
        }
        return null;
    }

    public int getTicketCount() {
        return tickets.size();
    }

    public ArrayList<ParkingTicket> getTickets() {
        return tickets;
    }
}
