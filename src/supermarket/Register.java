import java.util.Queue;
import java.util.LinkedList;
public class Register {
    private int id;
    private Queue<Customer> line;
    private double totalSales;
    private int customersServed;

    public Register(int id){
        this.id = id;
        this.line =new LinkedList<>();
        this.totalSales = 0;
        this.customersServed = 0;
    }

    public int getId(){
        return id;
    }

    public void joinLine(Customer customer){
        line.add(customer);
    }

    public Customer checkOut(){
        return line.poll();
    }

    public int getLineSize(){
        return line.size();
    }

    public double getTotalSales(){
        return totalSales;
    }

    public int getCustomersServed(){
        return customersServed;
    }

    public void processCustomer(Customer customer){
        double total = customer.calculateCartTotal();
        totalSales += total;
        customersServed++;
    }

    public boolean hasCustomers(){
        return !line.isEmpty();
    }
}
