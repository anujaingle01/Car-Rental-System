import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Car{
    private String carID;
    private String brand;
    private String model;
    private double basePricePerDay;
    private boolean isAvailable;        // true = Yes ans false = No

    public Car(String carID,String brand,String model,double basePricePerDay){
        this.carID = carID;
        this.brand = brand;
        this.model = model;
        this.basePricePerDay = basePricePerDay;
        this.isAvailable = true;
    }

    public String getCarID(){
        return carID;
    }

    public String getBrand(){
        return brand;
    }

    public String getModel(){
        return model;
    }

    public double calculatePrice(int rentalDays){
        return basePricePerDay * rentalDays;            // 100rs * 5days = 500rs
    }

    public boolean isAvailable(){
        return isAvailable;
    }

    public void rent(){
        isAvailable = false;
    }

    public void returnCar(){
        isAvailable = true;
    }
}

class Customer{
    private String customerID ;
    private String name;

    public Customer(String customerID,String name){
        this.customerID = customerID;
        this.name = name;
    }

    public String getCustomerID(){
        return customerID;
    }

    public String getName(){
        return name;
    }
}

class Rental{
    private Car car;
    private Customer customer;
    private int days;

    public Rental(Car car,Customer customer,int days){
        this.car = car;
        this.customer = customer;
        this.days = days;
    }

    public Car getCar() {
        return car;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getDays() {
        return days;
    }
}

class CarRentalSystem {
    // ArrayList Address Creation
    private List<Car> cars;
    private List<Customer> customers;
    private List<Rental> rentals;

    // ArrayList Object Creation
    public CarRentalSystem() {
        cars = new ArrayList<>();
        customers = new ArrayList<>();
        rentals = new ArrayList<>();
    }

    public void addCar(Car car) {
        cars.add(car);
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public void rentCar(Car car, Customer customer, int days) {
        if (car.isAvailable()) {        // T = Yes Car is Available
            car.rent();                // F = Car is not Available Now
            // Rented Car is Listed in Rental Car List
            rentals.add(new Rental(car, customer, days));
        } else {
            System.out.println("Car is not Available for Rent");
        }
    }

    public void returnCar(Car car) {
        car.returnCar();                    // Making available for Rent
        Rental rentalToRemove = null;       // Rental List Object is Empty
        for (Rental rental : rentals) {
            if (rental.getCar() == car) {
                rentalToRemove = rental;    // Removed Car from Rental List
                break;
            }
        }
        if (rentalToRemove != null) {
            rentals.remove(rentalToRemove); // Rental List Object is Deleted
        } else {
            System.out.println("Car was not Rented");
        }
    }

    public void menu() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("====== Car Rental System ======");
            System.out.println("1.  Rent a Car");
            System.out.println("2.  Return a Car");
            System.out.println("3.  Exit");

            System.out.print("Enter your Choice = ");
            int choice = sc.nextInt();
            sc.nextLine();

            if (choice == 1) {
                System.out.println("----- Rent a Car -----");
                System.out.print("Enter your Name : ");
                String customerName = sc.nextLine();

                System.out.println("== Available Cars ==");
                for (Car car : cars) {   // loop on cars list
                    if (car.isAvailable()) {
                        System.out.println(car.getCarID() + " - " + car.getBrand() + " " + car.getModel());
                    }
                }

                System.out.print("Enter the Car-ID you want to rent : ");
                String carID = sc.nextLine();

                System.out.print("Enter the no: of days for Rental : ");
                int rentalDays = sc.nextInt();
                sc.nextLine();

                Customer newCustomer = new Customer("CUS" + (customers.size() + 1), customerName);
                addCustomer(newCustomer);

                Car selectedCar = null;     // Car Class Empty Object
                for (Car car : cars) {
                    if (car.getCarID().equals(carID) && car.isAvailable()) {
                        selectedCar = car;    // Car Class Object is Filled with CarID
                        break;
                    }
                }

                if (selectedCar != null) {
                    double totalPrice = selectedCar.calculatePrice(rentalDays);

                    System.out.println();
                    System.out.println("----- Rental Information -----");
                    System.out.println("CustomerID : " + newCustomer.getCustomerID());
                    System.out.println("Customer Name : " + newCustomer.getName());
                    System.out.println("Car : " + selectedCar.getBrand() + " " + selectedCar.getModel());
                    System.out.println("Rental Days : " + rentalDays);
                    System.out.printf("Total Price : $%.2f%n", +totalPrice);

                    System.out.println();
                    System.out.print("Confirm Rental (Y/N) : ");
                    String confirm = sc.nextLine();
                    System.out.println();

                    if (confirm.equalsIgnoreCase("Y")) {     // Y or y Both Allowed
                        rentCar(selectedCar, newCustomer, rentalDays);
                        System.out.println("Car rented Successfully ...");
                    } else {
                        System.out.println("Rental canceled");
                    }
                }
                    else {
                        System.out.println("Invalid car selection or car not available for rent. ");
                    }


                } else if (choice == 2) {
                    System.out.println("----- Return a Car -----");
                    System.out.print("Enter the Car-ID you want to return : ");
                    String carID = sc.nextLine();

                    Car carToReturn = null;
                    for (Car car : cars) {
                        if (car.getCarID().equals(carID) && !car.isAvailable()) {
                            carToReturn = car;
                            break;
                        }
                    }

                    if (carToReturn != null) {
                        Customer customer = null;
                        for (Rental rental : rentals) {
                            if (rental.getCar() == carToReturn) {
                                customer = rental.getCustomer();
                                break;
                            }
                        }

                        if (customer != null) {
                            returnCar(carToReturn);
                            System.out.println("Car returned Successfully by - " + customer.getName());
                        } else {
                            System.out.println("Car was not Rented or Rental Information is missing");
                        }
                    } else {
                        System.out.println("Invalid Car-ID or car is not Rented");
                    }
                } else if (choice == 3) {
                    break;
                } else {
                    System.out.println("Invalid Choice : Please enter a valid option.");
                }
            }
            System.out.println("THANK YOU... For using Car Rental System !");
        }
    }


public class Main{
    public static void main(String[] args) {
        CarRentalSystem crs = new CarRentalSystem();

        Car c1 = new Car("C001","Toyota","Camry",60.0);
        Car c2 = new Car("C002","Honda","Accord",70.0);
        Car c3 = new Car("C003","Mahindra","Thar",150.0);

        crs.addCar(c1);
        crs.addCar(c2);
        crs.addCar(c3);

        crs.menu();
    }
}
