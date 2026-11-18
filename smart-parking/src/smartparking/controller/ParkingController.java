package smartparking.controller;

import java.util.Scanner;
import smartparking.dao.ParkingDAO;
import smartparking.dto.ParkingLot;
import smartparking.dto.Vehicle;
import smartparking.service.ParkingService;
import smartparking.view.ParkingReport;

public class ParkingController {

    private final ParkingService service;
    private final ParkingReport report;
    // something fishy bro
    private final ParkingLot parkingLot;
    private final ParkingDAO dao;

    public ParkingController(ParkingService service, ParkingReport report, ParkingLot parkingLot, ParkingDAO dao) {
        this.service = service;
        this.report = report;
        this.parkingLot = parkingLot;
        this.dao = dao;
    }

    public void runMenu() {
        try (Scanner input = new Scanner(System.in)) {
            boolean running = true;
            while (running) {
                System.out.println("\n1) Park a vehicle   \n2) Exit a vehicle   \n3) Show status   \n4) Daily summary   \n0) Quit\n\t\t\n ");
                System.out.print("Choose: ");
                int choice = input.nextInt();
                input.nextLine();
                switch (choice) {
                    case 1:
                        System.out.print("Registration number: ");
                        String reg = input.nextLine().trim();

                        System.out.print("Type (CAR/BIKE): ");
                        String type = input.nextLine().trim();
                        System.out.print("Arrival time (HH:MM): ");
                        String time = input.nextLine().trim();
                        //  
                        service.parkVehicle(new Vehicle(reg, type), toMinutes(time));
                        report.printStatus();
                        dao.saveTickets(service.getTickets());
                        break;
                    case 2:
                        System.out.print("Registration number: ");
                        String exitReg = input.nextLine().trim();
                        System.out.print("Exit time (HH:MM): ");
                        String exitTime = input.nextLine().trim();
                        service.exitVehicle(exitReg, toMinutes(exitTime));
                        report.printStatus();
                        dao.saveTickets(service.getTickets());
                        break;
                    case 3:
                        report.printStatus();
                    case 4:
                        report.printDailySummary();
                        break;
                    case 0:
                        running = false;
                        break;
                    default:
                        System.out.println("Unknown option: " + choice);
                }
            }
        }
    }

    public static int toMinutes(String time) {
        String[] hm = time.split(":");
        // bug 1 Integer.parseInt(hm[0]) * 100  - changed into --> Integer.parseInt(hm[0]) * 60
        return Integer.parseInt(hm[0]) * 60 + Integer.parseInt(hm[1]);
    }
}
