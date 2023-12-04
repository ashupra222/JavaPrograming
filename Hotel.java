import java.util.Scanner;
import java.time.LocalDate;

class Food{
    String type;
    int price;
    LocalDate buyOn;
    Food(String type){
        this.type = type;
        buyOn = LocalDate.now();
        if(type == "breakfast") price = 50;
        else if(type == "lunch") price = 100;
        else if(type == "dinner") price = 200;
        else {
            System.out.println("Not a valid food type.");
            price = 0;
        }
    }
    void generateBill(){
        System.out.println(type + " on " + buyOn + " price: "+ price);
    }
}

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
    Food[] foodlist = new Food[2];
    int foodlistsize = 0;

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
        int foodTotal = 0;
        for(int i=0; i<foodlistsize;i++){
            foodTotal += foodlist[i].price;
        }
        System.out.println("--------------------------------------------");
        System.out.println("Room No. : "+roomNo);
        System.out.println("Booked by: "+ name);
        System.out.println("check-in date: "+ checkinDate);
        System.out.println("check-out date: "+checkoutDate);
        System.out.println("Rent per day : "+rateperday);
        for(int i=0; i<foodlistsize;i++){
            foodlist[i].generateBill();
        }
        System.out.println("Total food amount: " + foodTotal);
        System.out.println("Total Amount: "+ ((bookedForDays * rateperday)+ foodTotal));
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
    void addFood(String type){
        if (foodlistsize == foodlist.length){
            Food[] newfoodlist = new Food[foodlist.length + 2];
            for(int i=0; i<foodlistsize; i++){
                newfoodlist[i] = foodlist[i];
            }
            foodlist = newfoodlist;
        }
        foodlist[foodlistsize] = new Food(type);
        foodlistsize++;
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
                        System.out.print("Amount to pay is "+((rooms[i].rateperday * days) - amount)+"Rs. : ");
                        amount1 = sc1.nextInt();
                        if (amount1 ==0) break;
                        else amount += amount1; 
                        if (amount < (rooms[i].rateperday * days)){
                            System.out.println(
                                "amount not sufficient. Add left money or add 0 to cancel payment.");
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
        boolean flag = false;
        for(int i=0; i<totalRooms; i++){
            if(rooms[i].roomNo == roomNo){
                flag = true;
                if(rooms[i].available){
                    System.out.println("--------------------------------------------");
                    System.out.println("Room is not booked by anyone.");
                    System.out.println("--------------------------------------------");
                }
                else
                rooms[i].generateBill();
            }
        }
        if(flag == false)
            System.out.println("room not found!");
    }
    void generalCheck(){
        for(int i=0; i<totalRooms; i++){
            rooms[i].generalCheck();
        }
    }
    void bookFood(int roomNo, String typeOfFood){
        for(int i=0; i<totalRooms; i++){
            rooms[i].addFood(typeOfFood);
        }
    }
}


class Hotel {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of rooms in your hotel: ");
        int totalRooms= sc.nextInt();

        RoomManager manager= new RoomManager(totalRooms);
        for(int i=1; i<=totalRooms; i++){
            System.out.print("Enter true if room no. " +i+ " have Ac otherwise false: ");
            boolean ac = sc.nextBoolean();
            System.out.print("Enter true if room no. " +i+ " have double bed otherwise false: ");
            boolean doubleBed = sc.nextBoolean();
            System.out.print("Enter bed count for room no. " +i+": ");
            int bedCount = sc.nextInt();
            System.out.print("Enter rent per day for room no. " +i+": ");
            int rateperday = sc.nextInt();
            manager.setRoomDetails(i, rateperday, ac, doubleBed, bedCount);
            System.out.println("--------------------------------------------");
        }

        int choose = 0;
        int roomNo;
        do {
            manager.generalCheck();
            System.out.println("1 for show all available rooms."); 
            System.out.println("2 for find room with custom filter"); 
            System.out.println("3 for book room by room no.");
            System.out.println("4 for free room by room no.");
            System.out.println("5 for generate bill by room no.");
            System.out.println("6 to book food for room no. ");
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
                case 6:
                    System.out.print("Enter room No. to book food: ");
                    roomNo = sc.nextInt();
                    int choose3;
                    System.out.println("--------------------------------------------");
                    System.out.println("1 for Breakfast 50Rs.");
                    System.out.println("2 for Lunch 100Rs.");
                    System.out.println("3 for Dinner 200Rs.");
                    choose3 = sc.nextInt();
                    switch (choose3) {
                        case 1:
                            manager.bookFood(roomNo, "breakfast");
                            System.out.println("Breakfast booked for room no. "+ roomNo);
                            break;
                        case 2:
                            manager.bookFood(roomNo, "lunch");
                            System.out.println("Lunch booked for room no. "+ roomNo);
                            break;
                        case 3:
                            manager.bookFood(roomNo, "dinner");
                            System.out.println("Dinner booked for room no. "+ roomNo);
                            break;
                        default:
                            System.out.println("Not a valid food type. Food not booked.");
                            break;
                    }

                default:
                    System.out.println("--------------------------------------------");
                    break;
            }
        } while (choose != 9);  
        sc.close(); 
    }
}