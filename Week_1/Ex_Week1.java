import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.NoSuchElementException;


class ProductNotFoundException extends RuntimeException {

    // Default constructor
    public ProductNotFoundException() {
        super();
    }

    // Constructor that accepts a custom error message
    public ProductNotFoundException(String message) {
        super(message);
    }
}
class InsufficientStockException extends RuntimeException {
    InsufficientStockException(String sku, int requested, int available) {
        super("Not enough stock for " + sku + ": requested " + requested + ", available " + available);
    }
}
class Product {
    String sku;
    String name;
    double price;
    int stockQty;

    Product(String sku, String name, double price, int stockQty){
        this.sku = sku;
        this.name = name;
        this.price = price;
        this.stockQty = stockQty;
    }

    @Override
    public boolean equals(Object o){
        if(this == o){
            return  true;
        }else if(o==null || getClass() != o.getClass()){
            return false;
        }
        Product p = (Product) o;
        return Objects.equals(sku, p.sku);
    }
    @Override
    public int hashCode(){
        return Objects.hash(sku, name, price, stockQty);
    }
}

class Inventory{
    private Map<String, Product> map = new HashMap<>();
    Product getProduct(String sku) {
        return map.get(sku);
    }
    void addProduct(Product p){
        map.put(p.sku, p);
    }
    void restock(String sku, int qty) throws ProductNotFoundException{

            Product pp = map.get(sku);
            if (pp == null){
                throw new ProductNotFoundException(sku);
            }
            pp.stockQty += qty;
    }
}
class Order {
    Map<String, Integer> items;
    LocalDateTime timestamp;

    Order(Map<String, Integer> items) {
        this.items = items;
        this.timestamp = LocalDateTime.now();
    }
}

class OrderService {
    Inventory inv ;
    OrderService(Inventory inv){
        this.inv = inv;
    }
    Order placeOrder(Map<String, Integer> skuToQty) {

        for(Map.Entry<String, Integer> entry : skuToQty.entrySet()) {
            String sku = entry.getKey();
            int qty = entry.getValue();
            Product p = inv.getProduct(sku);


            if (p == null) {
                throw new NoSuchElementException("Unknown SKU: " + sku);
                // note: different exception than restock's — this is a lookup
                // failure during a read-heavy operation, arguably unchecked too
            }
            if (p.stockQty < qty) {
                throw new InsufficientStockException(sku, qty, p.stockQty);
            }
        }
        for (Map.Entry<String, Integer> entry : skuToQty.entrySet()){
            Product p = inv.getProduct(entry.getKey());
            p.stockQty -= entry.getValue();
        }
        return new Order(skuToQty);
    }
}


public class Ex_Week1 {
    public static void main(String[] args) {
        // --- Setup: stock the warehouse ---
        Inventory inventory = new Inventory();
        inventory.addProduct(new Product("A1", "Widget", 9.99, 5));
        inventory.addProduct(new Product("B2", "Gadget", 19.99, 10));

        OrderService orderService = new OrderService(inventory);

        // --- Test 1: valid order ---
        System.out.println("=== Test 1: valid order ===");
        Map<String, Integer> order1 = new HashMap<>();
        order1.put("A1", 3);
        order1.put("B2", 4);

        Order result1 = orderService.placeOrder(order1);
        System.out.println("Order placed at: " + result1.timestamp);
        System.out.println("Items: " + result1.items);
        System.out.println("A1 stock now: " + inventory.getProduct("A1").stockQty); // expect 2
        System.out.println("B2 stock now: " + inventory.getProduct("B2").stockQty); // expect 6

        // --- Test 2: insufficient stock ---
        System.out.println("\n=== Test 2: insufficient stock ===");
        Map<String, Integer> order2 = new HashMap<>();
        order2.put("A1", 100); // way more than available

        try {
            orderService.placeOrder(order2);
            System.out.println("ERROR: should have thrown!");
        } catch (InsufficientStockException e) {
            System.out.println("Caught expected exception: " + e.getMessage());
        }
        System.out.println("A1 stock unchanged? " + inventory.getProduct("A1").stockQty); // expect still 2

        // --- Test 3: unknown SKU ---
        System.out.println("\n=== Test 3: unknown SKU ===");
        Map<String, Integer> order3 = new HashMap<>();
        order3.put("Z9", 1); // doesn't exist

        try {
            orderService.placeOrder(order3);
            System.out.println("ERROR: should have thrown!");
        } catch (NoSuchElementException e) {
            System.out.println("Caught expected exception: " + e.getMessage());
        }

        // --- Test 4: restock, then check ProductNotFoundException ---
        System.out.println("\n=== Test 4: restock ===");
        inventory.restock("A1", 10);
        System.out.println("A1 stock after restock: " + inventory.getProduct("A1").stockQty); // expect 12

        try {
            inventory.restock("Z9", 5); // doesn't exist
            System.out.println("ERROR: should have thrown!");
        } catch (ProductNotFoundException e) {
            System.out.println("Caught expected exception: " + e.getMessage());
        }
    }
}
