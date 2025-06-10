public class Item {
    private String name;
    private double price;

    public Item(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }
}

public interface DiscountService {
    double applyDiscount(double totalAmount);
}

public class PercentageDiscount implements DiscountService {
    private double percentage;

    public PercentageDiscount(double percentage) {
        this.percentage = percentage;
    }

    @Override
    public double applyDiscount(double totalAmount) {
        return totalAmount - (totalAmount * percentage / 100);
    }
}

public interface TaxService {
    double applyTax(double amount);
}

public class GSTService implements TaxService {
    private double taxRate;

    public GSTService(double taxRate) {
        this.taxRate = taxRate;
    }

    @Override
    public double applyTax(double amount) {
        return amount + (amount * taxRate / 100);
    }
}

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<Item> items;
    private DiscountService discountService;
    private TaxService taxService;

    public Cart(DiscountService discountService, TaxService taxService) {
        this.items = new ArrayList<>();
        this.discountService = discountService;
        this.taxService = taxService;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public double calculateFinalTotal() {
        double subtotal = 0;
        for (Item item : items) {
            subtotal += item.getPrice();
        }

        double discounted = discountService.applyDiscount(subtotal);
        double finalTotal = taxService.applyTax(discounted);

        return finalTotal;
    }

    public void displayCartDetails() {
        System.out.println("Items in cart:");
        for (Item item : items) {
            System.out.println("- " + item.getName() + " : ₹" + item.getPrice());
        }

        System.out.println("Final Total (with discount & tax): ₹" + calculateFinalTotal());
    }
}

public class Main {
    public static void main(String[] args) {
        DiscountService discount = new PercentageDiscount(10); // 10% off
        TaxService tax = new GSTService(18);                   // 18% GST

        Cart cart = new Cart(discount, tax);
        cart.addItem(new Item("Laptop", 50000));
        cart.addItem(new Item("Headphones", 2000));

        cart.displayCartDetails();
    }
}
