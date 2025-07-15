import java.util.*;
public class Supermarket {
    private List<Register> registers;
    private SupermarketStatistics stats;
    private Random random;

    public Supermarket(){
        this.registers = new ArrayList<>();
        this.stats = new SupermarketStatistics();
        this.random = new Random();
    }

    private List<String> generateItemNames(){
        return Arrays.asList("Milk", "Bread", "Eggs", "Cheese", "Chicken", "Beef", "Apples", "Bananas", "Rice", "Pasta");
    }

    private Customer generateCustomer(int id, List<String> itemNames){
        Customer customer = new Customer(id);
        int itemCount = random.nextInt(26) + 5; 
        for (int i = 0; i < itemCount; i++) {
            String itemName = itemNames.get(random.nextInt(itemNames.size()));
            double price = 0.5 + (100 - 0.5) * random.nextDouble();
            customer.addToCart(new Item(itemName, price));
        } 
        return customer;
    }

    private Register getShortestLine(){
        /*
        Register shortest = registers.get(0);
        for (Register reg : registers) {
            if (reg.getLineSize() < shortest.getLineSize()) {
                shortest = reg;
            }
        }
        return shortest;
        */
        return registers.stream()
        .min(Comparator.comparingInt(Register::getLineSize))
        .orElse(registers.get(0));
    }

    public void run() throws InterruptedException{
        int registerCount = random.nextInt(11) + 5; // 5 to 15
        int customerCount = random.nextInt(451) + 50; // 50 to 500
        List<String> itemNames = generateItemNames();

        for (int i = 0; i < registerCount; i++) {
            registers.add(new Register(i + 1));
        }
        System.out.println("Opened " + registerCount + " registers.");
        stats.customersArrived = customerCount;

        List<Customer> customers = new ArrayList<>();
        for (int i = 1; i < customerCount; i++) {
            Customer customer = generateCustomer(i, itemNames);
            customers.add(customer);
        }

        for (Customer c : customers) {
            Register reg = getShortestLine();
            reg.joinLine(c);
            stats.longestLine = Math.max(stats.longestLine, reg.getLineSize());
        }

        boolean registersNotEmpty = true;
        while (registersNotEmpty) {
            registersNotEmpty = false;

            for (Register reg : registers) {
                if (reg.hasCustomers()) {
                    Customer c = reg.checkOut();
                    if (c != null) {
                        double total = c.calculateCartTotal();
                        reg.processCustomer(c);

                        stats.totalSales += total;
                        stats.customersServed++;
                        stats.minTotal = Math.min(stats.minTotal, total);
                        stats.maxTotal = Math.max(stats.maxTotal, total);
                    }
                }
                if (reg.hasCustomers()) {
                    registersNotEmpty = true;
                }
            }
            Thread.sleep(random.nextInt(301) + 200); // 200-500 ms
        }
        stats.avgTotal = stats.totalSales / stats.customersServed;
        printSummary();
    }
    private void printSummary(){
        System.out.println("\nSimulation Complete!");
        System.out.println("Customers Arrived: " + stats.customersArrived);
        System.out.println("Customers Served: " + stats.customersServed);
        System.out.println("Longest Line: " + stats.longestLine);
        System.out.println("Total Sales: $" + String.format("%.2f", stats.totalSales));
        System.out.println("Average Customer Total: $" + String.format("%.2f", stats.avgTotal));
        System.out.println("Min Customer Total: $" + String.format("%.2f", stats.minTotal));
        System.out.println("Max Customer Total: $" + String.format("%.2f", stats.maxTotal));
    }
}

