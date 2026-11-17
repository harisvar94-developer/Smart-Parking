# Smart Parking

A console-based parking lot manager written in Java. Vehicles arrive at the
entrance, take a numbered slot, and pay an hourly fee when they leave. The lot
has 5 slots. Cars pay Rs.40 per started hour and bikes Rs.20, with a minimum
charge of one hour.

## Features

- Park a vehicle: assigns the first free slot and issues a ticket
- Exit a vehicle: calculates the fee and frees the slot
- Live status board: which slots are free and who is parked where
- Daily summary: every closed ticket and the total money collected
- Tickets are saved to `data/tickets.json` and loaded back on the next
  start, so the lot remembers parked vehicles across restarts

## Project structure

```
src/
  Main.java
  smartparking/
    controller/   ParkingController - menu and input handling
    service/      ParkingService    - parking and exit operations
    view/         ParkingReport     - status board and daily summary
    dao/          ParkingDAO        - reads and writes data/tickets.json
    dto/          ParkingLot, ParkingSlot, ParkingTicket, Vehicle
run.sh            compiles to out/ and runs Main
```

## How to run

Open the project in your IDE and run `Main.java`, or from the project root:

```
./run.sh
```

The menu:

```
1) Park a vehicle   2) Exit a vehicle   3) Show status   4) Daily summary   0) Quit
```

Times are entered as HH:MM (for example 08:00 or 17:45).

## Current behavior

A fresh start right now:

```
=== SMART PARKING ===  (5 slots)

1) Park a vehicle   2) Exit a vehicle   3) Show status   4) Daily summary   0) Quit
Choose: 3
   +---------------- SMART PARKING ----------------+
   | Slot 1  FREE     -                   |
   | Slot 2  FREE     -                   |
   | Slot 3  FREE     -                   |
   | Slot 4  FREE     -                   |
   | Slot 5  FREE     -                   |
   | Available: 5   Occupied: 0                |
   +--------------------------------------------+

===== DAILY SUMMARY =====
  Total collected: Rs.0

1) Park a vehicle   2) Exit a vehicle   3) Show status   4) Daily summary   0) Quit
Choose: 1
Registration number: Type (CAR/BIKE): TN01AA0001
Arrival time (HH:MM): CAR
Exception in thread "main" java.lang.NumberFormatException: For input string: "CAR"
	at java.base/java.lang.NumberFormatException.forInputString(NumberFormatException.java:65)
	at java.base/java.lang.Integer.parseInt(Integer.java:652)
	at java.base/java.lang.Integer.parseInt(Integer.java:770)
	at smartparking.controller.ParkingController.toMinutes(ParkingController.java:68)
	at smartparking.controller.ParkingController.runMenu(ParkingController.java:39)
	at Main.main(Main.java:16)
```

## Expected behavior

The same application should behave like this:

```
=== SMART PARKING ===  (5 slots)

1) Park a vehicle   2) Exit a vehicle   3) Show status   4) Daily summary   0) Quit
Choose: 1
Registration number: TN01AA0001
Type (CAR/BIKE): CAR
Arrival time (HH:MM): 08:00

>> TN01AA0001 (CAR) arrives at the entrance at 08:00
   Slot 1 assigned. Ticket T1001 issued.
   Gate opens. TN01AA0001 parks in Slot 1.
   +---------------- SMART PARKING ----------------+
   | Slot 1  OCCUPIED TN01AA0001          |
   | Slot 2  FREE     -                   |
   | Slot 3  FREE     -                   |
   | Slot 4  FREE     -                   |
   | Slot 5  FREE     -                   |
   | Available: 4   Occupied: 1                |
   +--------------------------------------------+

1) Park a vehicle   2) Exit a vehicle   3) Show status   4) Daily summary   0) Quit
Choose: 1
Registration number: TN02BB0002
Type (CAR/BIKE): BIKE
Arrival time (HH:MM): 08:05

>> TN02BB0002 (BIKE) arrives at the entrance at 08:05
   Slot 2 assigned. Ticket T1002 issued.
   Gate opens. TN02BB0002 parks in Slot 2.
   +---------------- SMART PARKING ----------------+
   | Slot 1  OCCUPIED TN01AA0001          |
   | Slot 2  OCCUPIED TN02BB0002          |
   | Slot 3  FREE     -                   |
   | Slot 4  FREE     -                   |
   | Slot 5  FREE     -                   |
   | Available: 3   Occupied: 2                |
   +--------------------------------------------+

1) Park a vehicle   2) Exit a vehicle   3) Show status   4) Daily summary   0) Quit
Choose: 3
   +---------------- SMART PARKING ----------------+
   | Slot 1  OCCUPIED TN01AA0001          |
   | Slot 2  OCCUPIED TN02BB0002          |
   | Slot 3  FREE     -                   |
   | Slot 4  FREE     -                   |
   | Slot 5  FREE     -                   |
   | Available: 3   Occupied: 2                |
   +--------------------------------------------+

1) Park a vehicle   2) Exit a vehicle   3) Show status   4) Daily summary   0) Quit
Choose: 2
Registration number: TN01AA0001
Exit time (HH:MM): 09:30

<< TN01AA0001 requests exit at 09:30
   Ticket T1001: parked 90 min, charged 2 h x Rs.40 = Rs.80
   Slot 1 is now free. Goodbye TN01AA0001!
   +---------------- SMART PARKING ----------------+
   | Slot 1  FREE     -                   |
   | Slot 2  OCCUPIED TN02BB0002          |
   | Slot 3  FREE     -                   |
   | Slot 4  FREE     -                   |
   | Slot 5  FREE     -                   |
   | Available: 4   Occupied: 1                |
   +--------------------------------------------+

1) Park a vehicle   2) Exit a vehicle   3) Show status   4) Daily summary   0) Quit
Choose: 4

===== DAILY SUMMARY =====
  T1001  TN01AA0001   slot 1  08:00 -> 09:30  Rs.80
  Total collected: Rs.80

1) Park a vehicle   2) Exit a vehicle   3) Show status   4) Daily summary   0) Quit
Choose: 0
```
