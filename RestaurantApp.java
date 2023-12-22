import java.util.*;

class Person {
    String name;
    String gender;
    String address;

    public Person(String name, String gender, String address) {
        this.name = name;
        this.gender = gender;
        this.address = address;
    }
}

class Customer extends Person {
    double saldo;
    List<Order> orders;

    public Customer(String name, String gender, String address) {
        super(name, gender, address);
        this.saldo = 0;
        this.orders = new ArrayList<>();
    }
}

class Admin extends Person {
    public Admin(String name, String gender, String address) {
        super(name, gender, address);
    }
}

class Menu {
    String itemName;
    double price;
    String code;

    public Menu(String itemName, double price, String code) {
        this.itemName = itemName;
        this.price = price;
        this.code = code;
    }
}

class Order {
    Menu menu;
    int quantity;

    public Order(Menu menu, int quantity) {
        this.menu = menu;
        this.quantity = quantity;
    }
}

public class RestaurantApp {
    static Scanner scanner = new Scanner(System.in);
    static List<Customer> customers = new ArrayList<>();
    static List<Menu> menus = new ArrayList<>();
    static List<Admin> admins = new ArrayList<>(); // Added to store admin data

    public static void main(String[] args) {
        initializeMenus();

        while (true) {
            System.out.println("1. Login as a customer");
            System.out.println("2. Login as an admin");
            System.out.println("3. Stop program");
            System.out.print("Insert your role (1/2/3): ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    loginAsCustomer();
                    break;
                case 2:
                    loginAsAdmin();
                    break;
                case 3:
                    stopProgram();
                    break;
                default:
                    System.out.println("Invalid choice. Please enter 1, 2, or 3.");
            }
        }
    }

    private static boolean isNameAlreadyExists(String name) {
            for (Customer existingCustomer : customers) {
                if (existingCustomer.name.equals(name)) {
                    return true;
                }
            }
            return false;
        }

    // Existing methods remain unchanged.
    private static void loginAsCustomer() {
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        System.out.print("Enter your gender: ");
        String gender = scanner.nextLine();
        System.out.print("Enter your address: ");
        String address = scanner.nextLine();

        if (isNameAlreadyExists(name)) {
            System.out.println("Name already exists. Please enter a different name.");
            return;
        }

        Customer customer = new Customer(name, gender, address);
        customers.add(customer);

        System.out.println("Hello " + name + ", welcome to Edbert's Kitchen!!");
        while (true) {
            System.out.println("1. Show restaurant menu");
            System.out.println("2. Add balance");
            System.out.println("3. Place an order");
            System.out.println("4. Back to the main menu");
            System.out.print("Choose an option (1/2/3/4): ");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (option) {
                case 1:
                    showMenu();
                    break;
                case 2:
                    addBalance(customer);
                    break;
                case 3:
                    placeOrder(customer);
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid option. Please enter 1, 2, 3, or 4.");
            }
        }
    }

    private static void showMenu() {
        System.out.println("=================================================");
        System.out.printf("| %-2s| %-25s| %-19s| %-5s|\n", "No", "Nama Makanan/Minuman", "Harga", "Kode");
        System.out.println("=================================================");
        int number = 1;
        for (Menu menu : menus) {
            System.out.printf("| %-2d| %-25s| %-19s| %-5s|\n", number, menu.itemName, "Rp. " + menu.price, menu.code);
            number++;
        }
        System.out.println("=================================================");
    }

    private static void addBalance(Customer customer) {
        System.out.println("Your current balance: Rp. " + customer.saldo);
        System.out.print("Enter the amount to add: Rp. ");
        double amount = scanner.nextDouble();
        customer.saldo += amount;
        System.out.println("Your balance after adding: Rp. " + customer.saldo);
    }

    private static void placeOrder(Customer customer) {
        showMenu();
        System.out.print("Enter the food/drink code to order: ");
        String orderCode = scanner.next();
        Menu selectedMenu = findMenuByCode(orderCode);

        if (selectedMenu == null) {
            System.out.println("Invalid menu code. Please try again.");
            return;
        }

        System.out.print("Enter the quantity: ");
        int quantity = scanner.nextInt();

        double totalCost = selectedMenu.price * quantity;

        if (totalCost > customer.saldo) {
            System.out.println("Insufficient balance. Your current balance is Rp. " + customer.saldo);
            System.out.print("Do you want to add more balance? (yes/no): ");
            String addBalanceChoice = scanner.next().toLowerCase();
    
            if (addBalanceChoice.equals("yes")) {
                addBalance(customer);
            } else {
                System.out.println("Order canceled. Please add more balance to place the order.");
            }
        } else {
            customer.orders.add(new Order(selectedMenu, quantity));
            customer.saldo -= totalCost;
            System.out.println("Order placed successfully. Remaining balance: Rp. " + customer.saldo);
        }
    }

    private static Menu findMenuByCode(String code) {
        for (Menu menu : menus) {
            if (menu.code.equals(code)) {
                return menu;
            }
        }
        return null;
    }

    private static void loginAsAdmin() {
        System.out.println("Hello mas admin, welcome to restaurant's setting:");
        while (true) {
            System.out.println("1. Edit restaurant menu");
            System.out.println("2. View customer data");
            System.out.println("3. Back to the main menu");
            System.out.print("Choose an option (1/2/3): ");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (option) {
                case 1:
                    editMenu();
                    break;
                case 2:
                    viewCustomerData();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid option. Please enter 1, 2, or 3.");
            }
        }
    }

    private static void editMenu() {
        while (true) {
            showMenu();
            System.out.println("1. Add new menu");
            System.out.println("2. Delete menu");
            System.out.println("3. Back to admin menu");
            System.out.print("Choose an option (1/2/3): ");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (option) {
                case 1:
                    addNewMenu();
                    break;
                case 2:
                    deleteMenu();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid option. Please enter 1, 2, or 3.");
            }
        }
    }

    private static void addNewMenu() {
        System.out.print("Enter the name of the new menu: ");
        String itemName = scanner.nextLine();

        double price = 0;
        while (true) {
            System.out.print("Enter the price of the new menu: Rp. ");
            try {
                price = Double.parseDouble(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }

        String code;
        while (true) {
            System.out.print("Enter the code of the new menu: ");
            code = scanner.next();
            if (isCodeUnique(code)) {
                break;
            } else {
                System.out.println("Menu with the entered code already exists. Please enter a unique code.");
            }
        }

        Menu newMenu = new Menu(itemName, price, code);
        menus.add(newMenu);

        System.out.println("New menu added successfully!");
    }

    private static boolean isCodeUnique(String code) {
        for (Menu menu : menus) {
            if (menu.code.equals(code)) {
                return false;
            }
        }
        return true;
    }

    private static void deleteMenu() {
        System.out.print("Enter the code of the menu to delete: ");
        String code = scanner.next();

        Menu menuToDelete = findMenuByCode(code);

        if (menuToDelete == null) {
            System.out.println("Menu with code " + code + " not found.");
        } else {
            menus.remove(menuToDelete);
            System.out.println("Menu deleted successfully!");
        }
    }

    private static void viewCustomerData() {
        System.out.println("Customer Data:");
        for (Customer customer : customers) {
            System.out.println("Name: " + customer.name);
            System.out.println("Gender: " + customer.gender);
            System.out.println("Address: " + customer.address);

            if (!customer.orders.isEmpty()) {
                System.out.println("Orders:");
                for (Order order : customer.orders) {
                    System.out.println("- " + order.quantity + "x " + order.menu.itemName + " (Rp. " + order.menu.price + " each)");
                }
            } else {
                System.out.println("No orders placed by this customer.");
            }

            System.out.println("Remaining Balance: Rp. " + customer.saldo);
            System.out.println("========================================");
        }
    }

    private static void stopProgram() {
        customers.clear();
        menus.clear();
        System.out.println("Program stopped. All data has been deleted.");
        System.exit(0);
    }

    private static void initializeMenus() {
        menus.add(new Menu("Ayam goreng", 10000, "001"));
        menus.add(new Menu("Pizza", 12000, "002"));
        menus.add(new Menu("Steak", 15000, "003"));
        menus.add(new Menu("Spaghetti", 16000, "004"));
    }
}
