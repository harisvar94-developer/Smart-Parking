import smartparking.controller.ParkingController;
import smartparking.dao.ParkingDAO;
import smartparking.dto.ParkingLot;
import smartparking.service.ParkingService;
import smartparking.view.ParkingReport;

public class Main {

    public static void main(String[] args) {
        // main creating object(lot) with  ParkingLot
        ParkingLot lot = new ParkingLot("SMART PARKING", 5);

        // main creating object(dao) with ParkingDAO
        ParkingDAO dao = new ParkingDAO();

        // main creating object(service) with ParkingService uses  --> lot(obj) and dao
        ParkingService service = new ParkingService(lot, dao.loadTickets());

        // main creating  object (report) with Parking report uses --> lot(obj) and service()
        ParkingReport report = new ParkingReport(lot, service);

        ParkingController controller = new ParkingController(service, report, lot, dao);
        System.out.println("=== SMART PARKING ===  (" + lot.getTotalSlots() + " slots)");
        controller.runMenu();

    }
}


/*
Main.java
 * controller/ ParkingController    - menu and input handling
 * service/ ParkingService      - parking and exit operations
 * view/ ParkingReport     - status board and daily summary
 * dao/ ParkingDAO     - reads and writes data/tickets.json
 * dto/.   pojo classes bro --->  ParkingLot, ParkingSlot, ParkingTicket, Vehicle
 * run.sh compiles to out/ and runs Main
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 */
