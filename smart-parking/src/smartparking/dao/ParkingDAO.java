package smartparking.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import smartparking.dto.ParkingTicket;
import smartparking.dto.Vehicle;

public class ParkingDAO {

    private static final String FILE_PATH = "smart-parking/src/data/tickets.json";

    // Load tickets from the JSON file
    public ArrayList<ParkingTicket> loadTickets() {
        ArrayList<ParkingTicket> tickets = new ArrayList<>();
        try {
            try (Scanner file = new Scanner(new File(FILE_PATH))) {
                //String content = file.useDelimiter("\\A").next();
               // System.out.println("Tickets file content: " + content); // bugging line bro
            while (file.hasNextLine()) {
                String line = file.nextLine().trim();
                
                if (!line.isEmpty() && !line.equals("[") && !line.equals("]")) {
                    if (line.endsWith(",")) {
                        line = line.substring(0, line.length() - 1);
                    }

                    tickets.add(parseTicket(line));
                }
            }
            }
        } catch (FileNotFoundException e){
            System.out.println("Tickets file not found. Starting with an empty ticket list.");
            e.printStackTrace();
        }
        return tickets;
    }
    // Save tickets to the JSON file
    public void saveTickets(ArrayList<ParkingTicket> tickets) {

        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            // I added the opening and closing brackets for the JSON array to ensure proper formatting-->
                writer.write("[\n");
            for (ParkingTicket ticket : tickets) {
                writer.write(toJson(ticket) + ",\n");
            }
            writer.write("]");//--->
            // I added the closing bracket for the JSON array to ensure proper formatting
        } catch (IOException e) {
            System.out.println("Could not save tickets: " + e.getMessage());
        }
    }
// toJson method to convert a ParkingTicket object into a JSON string
    private String toJson(ParkingTicket ticket) {
        return " {\"id\":\"" + ticket.getTicketId()
                + "\",\"reg\":\"" + ticket.getVehicle().getRegistrationNumber()
                + "\",\"type\":\"" + ticket.getVehicle().getType()
                + "\",\"slot\":" + ticket.getSlotNumber()
                + ",\"entry\":" + ticket.getEntryTime()
                + ",\"exit\":" + ticket.getExitTime()
                + ",\"fee\":" + ticket.getFee()
                + ",\"closed\":" + ticket.isClosed() + "}  ";
    }
// parseTicket method to convert a JSON string back into a ParkingTicket object


    private ParkingTicket parseTicket(String line) {
        String id = jsonText(line, "id");
        String reg = jsonText(line, "reg");
        String type = jsonText(line, "type");
        int slot = jsonNumber(line, "slot");
        int entry = jsonNumber(line, "entry");
        int exit = jsonNumber(line, "exit");
        int fee = jsonNumber(line, "fee");
        boolean closed = jsonText(line, "closed").equals("true");
        return new ParkingTicket(id, new Vehicle(reg, type), slot, entry, exit, fee, closed);
    }
// jsonText method to extract a string value from a JSON line based on a given key
    private String jsonText(String line, String key) {
        String marker = "\"" + key + "\":";
       
        int start = line.indexOf(marker) + marker.length();
       // System.out.println("start : " + start);
        if (line.charAt(start) == '"') {
            int end = line.indexOf('"', start + 1);
           // System.out.println("end : "+end);
            return line.substring(start + 1, end);
        }
        //-------------------------
        int end = start;
        // something fishy  bro
        while (end < line.length() && line.charAt(end) != ',' && line.charAt(end) != '}') {
            end++;

            //System.out.println(" loop:  end : " + end);
        }
        //System.out.println(line.substring(start, end));
        return line.substring(start, end);

    }
    private int jsonNumber(String line, String key) {
        System.out.println(jsonText(line, key));
        return Integer.parseInt(jsonText(line, key));
    }
}

/*
 * A summary about each method in this class bro
 * loadTickets() method have return type in ArrayList<ParkingTicket> 
 * 
 */
