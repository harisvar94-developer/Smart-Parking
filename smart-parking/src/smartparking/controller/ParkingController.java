package smartparking.controller;

import java.util.Scanner;
import smartparking.dao.ParkingDAO;
import smartparking.dto.ParkingLot;
import smartparking.dto.Vehicle;
import smartparking.service.ParkingService;
import smartparking.view.ParkingReport;

public class ParkingController {

    private ParkingService service;
    private ParkingReport report;
    private ParkingLot parkingLot;
    private ParkingDAO dao;

    public ParkingController(ParkingService service, ParkingReport report, ParkingLot parkingLot, ParkingDAO dao) {
        this.service = service;
        this.report = report;
        this.parkingLot = parkingLot;
        this.dao = dao;
    }

    public void runMenu() {
        Scanner input = new Scanner(System.in);
        boolean running = true;
        while (running) {
            System.out.println("\n1) Park a vehicle   2) Exit a vehicle   3) Show status   4) Daily summary   0) Quit");
            System.out.print("Choose: ");
            int choice = input.nextInt();
            switch (choice) {
                case 1:
                    System.out.print("Registration number: ");
                    String reg = input.nextLine().trim();
                    System.out.print("Type (CAR/BIKE): ");
                    String type = input.nextLine().trim();
                    System.out.print("Arrival time (HH:MM): ");
                    String time = input.nextLine().trim();
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

    public static int toMinutes(String time) {
        String[] hm = time.split(":");
        return Integer.parseInt(hm[0]) * 100 + Integer.parseInt(hm[1]);
    }
}
