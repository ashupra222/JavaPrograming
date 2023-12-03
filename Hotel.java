import java.util.Scanner;

import java.time.LocalDate;

class Room{
    int roomNo ;
    int rateperday;
    boolean ac ;
    boolean doubleBed;
    boolean available;
    int bedCount;
    String name;
    int bookedForDays;
    LocalDate checkinDate;
    LocalDate checkoutDate;

    Room(int rmN,int rateperday, boolean ac, boolean doubleBed, int bedCount){
        this.roomNo = rmN;
        this.rateperday = rateperday;
        this.ac = ac;
        this.doubleBed = doubleBed;
        this.bedCount = bedCount;
        available = true;
        name = "";
    }
    void bookRoom(String name, int days){
        this.available = false;
        bookedForDays = days;
        checkinDate= LocalDate.now();
        checkoutDate = checkinDate.plusDays(days);
        this.name = name;
    }
    void freeRoom(){
        this.available = true;
        checkoutDate = LocalDate.now();
        this.name = "";
    }
    boolean checkAvailablility(){
        if (available == true)
            System.out.println("is available");
        else 
            System.out.println("is not available");
        return available;
    }
    void RoomDetails(){
        System.out.print("Room no. " + roomNo + " is ");
        if (available){
            System.out.print("available having ");
            if (ac){
                System.out.print("AC with " + bedCount + " bed of ");
                if (doubleBed){
                    System.out.print("double type. Rate per day = " + rateperday + "\n");
                }
                else{
                    System.out.print("single type. Rate per day = " + rateperday + "\n");
                }
            }
            else{
                System.out.print("no AC with " + bedCount + " bed of ");
                if (doubleBed){
                    System.out.print("double type. Rate per day = " + rateperday + "\n");
                }
                else{
                    System.out.print("single type. Rate per day = " + rateperday + "\n");
                }
            }
        }
        else{
            System.out.print("not available having ");
            if (ac){
                System.out.print("AC with " + bedCount + " bed of ");
                if (doubleBed){
                    System.out.print("double type. Rate per day = " + rateperday + 
                    ". Name of renter: " + name+ " \n");
                }
                else{
                    System.out.print("single type. Rate per day = " + rateperday + 
                    ". Name of renter: " + name+ " \n");
                }
            }
            else{
                System.out.print("no AC with " + bedCount + " bed of ");
                if (doubleBed){
                    System.out.print("double type. Rate per day = " + rateperday + 
                    ". Name of renter: " + name+ " \n");
                }
                else{
                    System.out.print("single type. Rate per day = " + rateperday + 
                    ". Name of renter: " + name+ " \n");
                }
            }
        }
    }
    void generateBill(){
        System.out.println("--------------------------------------------");
        System.out.println("Room No. : "+roomNo);
        System.out.println("Booked by: "+ name);
        System.out.println("check-in date: "+ checkinDate);
        System.out.println("check-out date: "+checkoutDate);
        System.out.println("Rent per day : "+rateperday);
        System.out.println("Total Amount: "+ (bookedForDays * rateperday));
        System.out.println("--------------------------------------------");
    }
    void generalCheck(){
        if (available == false){
            if ((LocalDate.now().isEqual(checkoutDate)) || (LocalDate.now().isAfter(checkoutDate))){
                System.out.println("Checkout date reached for room no. "+roomNo);
                freeRoom();
            }
        }
    }
}

class RoomManager{
    Room rooms[] ;
    int totalRooms;
    Scanner sc1 = new Scanner(System.in);

    RoomManager(int totalRooms){
        this.totalRooms = totalRooms;
        rooms =new Room[totalRooms];
        for(int i=1; i<=totalRooms; i++){
        rooms[i-1] = new Room(i, 0, false, false, 0);
        }
    }
    void setRoomDetails(int rmN, int rateperday, boolean ac, boolean doubleBed, int bedCount){
        for(int i=1; i<=totalRooms; i++){
            if (rooms[i-1].roomNo == rmN){
                rooms[i-1].rateperday = rateperday;
                rooms[i-1].ac = ac;
                rooms[i-1].doubleBed = doubleBed;
                rooms[i-1].bedCount = bedCount;

            }
        }
    }
    void RoomsAvailability(){
        boolean flag=false;
        for(int i=1; i<=totalRooms;i++){
            if (rooms[i-1].available){
                rooms[i-1].RoomDetails();
                flag = true;
            }
        }
        if (flag == false){
            System.out.println("No rooms available.");
        }
        System.out.println("--------------------------------------------");
    }
    void RoomsAvailability(boolean ac, boolean doubleBed, int bedCount){
        boolean flag=false;
        for(int i=1; i<=totalRooms;i++){
            if (
                rooms[i-1].ac == ac 
                && rooms[i-1].doubleBed == doubleBed 
                && rooms[i-1].bedCount == bedCount 
                ){
                    if (rooms[i-1].available){
                        rooms[i-1].RoomDetails();
                        flag=true;
                    }
            }
        }
        if (flag == false){
            System.out.println("No rooms available with such criteria.");
        }
        System.out.println("--------------------------------------------");
    }
    void bookRoom(int roomNo){
        boolean flag = false;
        for(int i = 0; i<totalRooms; i++){
            if ((rooms[i].roomNo==roomNo)){
                flag = true;
                if(rooms[i].available){
                    String name;
                    int amount=0, amount1, days;
                    System.out.print("Enter name of customer: ");
                    name = sc1.nextLine();
                    System.out.println("days to book for: ");
                    days = sc1.nextInt();
                    do{
                        System.out.print("Amount to pay is "+(rooms[i].rateperday * days)+"Rs. : ");
                        amount1 = sc1.nextInt();
                        if (amount1 ==0) break;
                        else amount += amount1; 
                        if (amount < (rooms[i].rateperday * days)){
                            System.out.println(
                                "amount not sufficient. Add another money.add 0 to cancel.");
                        }
                    }while(amount < (rooms[i].rateperday * days));
                    if (amount < (rooms[i].rateperday * days)){
                            System.out.println(
                                "Room not booked due to insuffisient deposit.");
                            break;
                        }
                    rooms[i].bookRoom(name, days);
                    System.out.println("Room No. " +rooms[i].roomNo+ " booked with name: "+rooms[i].name);
                }
                else{
                    System.out.println("Room is already booked.");
                }
                break;
            }
        }
        if(flag == false)System.out.println("Room no. not found.");
        System.out.println("--------------------------------------------");
    }
    void freeRoom(int roomNo){
        boolean flag = false;
        for(int i=0; i<totalRooms; i++){
            if (rooms[i].roomNo == roomNo){
                flag = true;
                if(rooms[i].available != true){
                    rooms[i].freeRoom();
                    rooms[i].generateBill();
                    System.out.println("Room no. "+rooms[i].roomNo+" is now available to book.");
                } 
                else{
                    System.out.println("Room is already free.");
                }
                break;
            }
        }
        if(flag == false) System.out.println("Room no not found.");
        System.out.println("--------------------------------------------");
    }
    void generateBill(int roomNo){
        for(int i=0; i<totalRooms; i++){
            if(rooms[i].roomNo == roomNo){
                rooms[i].generateBill();
            }
        }
    }
    void generalCheck(){
        for(int i=0; i<totalRooms; i++){
            rooms[i].generalCheck();
        }
    }
}


class Hotel {
    public static void main(String[] args) {
        RoomManager manager= new RoomManager(10);
        manager.setRoomDetails(1, 500, false, false, 1);
        manager.setRoomDetails(2, 1300, true, false, 1);
        manager.setRoomDetails(3, 1500, true, true, 1);
        manager.setRoomDetails(4, 1000, false, true, 2);
        manager.setRoomDetails(5, 700, false, true, 1);
        manager.setRoomDetails(6, 900, false, false, 2);
        manager.setRoomDetails(7, 1200, true, false, 2);
        manager.setRoomDetails(8, 1900, true, true, 2);
        manager.setRoomDetails(9, 600, false, false, 1);
        manager.setRoomDetails(10, 2100, true, true, 1);

        Scanner sc = new Scanner(System.in);
        int choose = 0;
        int roomNo;
        do {
            manager.generalCheck();
            System.out.println("1 for show all available rooms."); 
            System.out.println("2 for find room with custom filter"); 
            System.out.println("3 for book room by room no.");
            System.out.println("4 for free room by room no.");
            System.out.println("5 for generate bill by room no.");
            System.out.println("9 for exit menu.Any other value to reprint menu");

            choose = sc.nextInt();
            switch (choose) {
                case 1:
                    manager.RoomsAvailability();
                    break;
                case 2:
                    int choose2;
                    System.out.println("--------------------------------------------");
                  
                    System.out.println("1 for non-Ac rooms with 1 bed of single type.");
                    System.out.println("2 for non-Ac rooms with 2 beds of single type.");
                    System.out.println("3 for non-Ac rooms with 1 bed of double type.");
                    System.out.println("4 for non-Ac rooms with 2 beds of double type.");
                    System.out.println("5 for Ac rooms with 1 bed of single type.");
                    System.out.println("6 for Ac rooms with 2 beds of single type.");
                    System.out.println("7 for Ac rooms with 1 bed of double type.");
                    System.out.println("8 for Ac rooms with 2 beds of double type.");
                    System.out.println("9 for custom details.");

                    choose2 = sc.nextInt();
                    switch (choose2) {
                        case 1:
                            manager.RoomsAvailability(false, false, 1);
                            break;
                        case 2:
                            manager.RoomsAvailability(false, false, 2);
                            break;
                        case 3:
                            manager.RoomsAvailability(false, true, 1);
                            break;
                        case 4:
                            manager.RoomsAvailability(false, true, 2);
                            break;
                        case 5:
                            manager.RoomsAvailability(true, false, 1);
                            break;
                        case 6:
                            manager.RoomsAvailability(true, false, 2);
                            break;
                        case 7:
                            manager.RoomsAvailability(true, true, 1);
                            break;
                        case 8:
                            manager.RoomsAvailability(true, true, 2);
                            break;
                        case 9:
                            System.out.print("enter true for ac false for non Ac: ");
                            boolean ac = sc.nextBoolean();
                            System.out.print("enter true for double bed false for single bed: ");
                            boolean doubleBed = sc.nextBoolean();
                            System.out.print("enter no of beds.");
                            int bedCount = sc.nextInt();
                            manager.RoomsAvailability(ac, doubleBed, bedCount);
                            break;
                        default:
                            break;
                    }
                    
                    break;
                
                case 3:
                    System.out.print("Enter room No. to book: ");
                    roomNo= sc.nextInt();
                    manager.bookRoom(roomNo);
                    break;
                case 4:
                    System.out.print("Enter room No. to free: ");
                    roomNo = sc.nextInt();
                    manager.freeRoom(roomNo);
                    break;
                case 5:
                    System.out.print("Enter room No. to generate bill: ");
                    roomNo = sc.nextInt();
                    manager.generateBill(roomNo);
                    break;
                default:
                    System.out.println("--------------------------------------------");
                    break;
            }
        } while (choose != 9);  
        sc.close(); 
    }
}