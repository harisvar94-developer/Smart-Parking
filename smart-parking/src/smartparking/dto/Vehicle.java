package smartparking.dto;

public class Vehicle {


    /*
     * This class get registrationNumber , vehicle type from parking controller and
     * give getRegistrationNumber(),getType(),getHourlyRate() to 
     */

    private final String registrationNumber;
    private final String type;

    public Vehicle(String registrationNumber, String type) {
        this.registrationNumber = registrationNumber.toUpperCase();
        this.type = type.toUpperCase();
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public String getType() {
        return type;
    }

    public int getHourlyRate() {
        if (type.equals("BIKE")) {
            return 20;
        }
        return 40;
    }

    @Override
    public String toString() {
        return registrationNumber + " (" + type + ")";
    }
}
