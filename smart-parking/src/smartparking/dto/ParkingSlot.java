package smartparking.dto;

public class ParkingSlot {

    private final int slotNumber;
    private  boolean occupied;
    private Vehicle vehicle;

    public ParkingSlot(int slotNumber) {
        this.slotNumber = slotNumber;
        this.occupied = false;
        this.vehicle = null;
    }

    public void assignVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
        // something fishy bro ✅
        // bug bro
        occupied = true;
    }   

    public void release() {
        // another one bug 
        this.occupied = false;
        this.vehicle = null;
    }

    public int getSlotNumber() {
        return slotNumber;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }
}
