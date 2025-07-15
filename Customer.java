import java.util.Stack;
class Customer{
    private int id;
    private Stack<Item> cart;

    public Customer(int id){
        this.id = id;
        this.cart = new Stack <>();
    }

    public int getId(){
        return id;
    }

    public void addToCart(Item item){
        cart.push(item);
    }

    public Item removeFromCart(){
        return cart.isEmpty() ? null : cart.pop();
    }

    public Stack<Item> getCart(){
        return cart;
    }

    public double calculateCartTotal(){
        double total = 0;
        for (Item item : cart) {
            total += item.getPrice();
        }
        return total;
    }
}