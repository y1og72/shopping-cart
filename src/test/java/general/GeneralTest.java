package general;

import entities.Coupon;
import entities.CouponItem;
import entities.Item;
import entities.ItemWeightPriceLookup;
import exceptions.BabyFoundOnConveyorBeltException;
import org.junit.Before;
import org.junit.Test;
import services.Scanner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class GeneralTest {
  List<Item> shoppingCart;
  List<Coupon> coupons;

  @Before
  public void performShoppingExtravaganza() throws Exception {
    //shop till you drop
    shoppingCart = new ArrayList<>();
    Item item = new Item();
    item.setName("ground beef");
    item.setWeightInOunces("8");
    shoppingCart.add(item);
    item = new Item();
    item.setName("soup");
    item.setPrice("3");
    shoppingCart.add(item);

    shoppingCart=Item.all();
    shoppingCart.add(Item.findByName("Turtle Wax"));
    shoppingCart.add(Item.findByName("Turtle Wax"));
    shoppingCart.add(Item.findByName("Turtle Wax"));
    shoppingCart.add(Item.findByName("Turtle Wax"));
    shoppingCart.add(Item.findByName("soup"));
    shoppingCart.add(Item.findByName("Turtle Wax"));
    shoppingCart.add(Item.findByName("soup"));
    shoppingCart.add(Item.findByName("2 Liter of Coke-Cola"));
    shoppingCart.add(Item.findByName("2 Liter of Coke-Cola"));
    shoppingCart.add(Item.findByName("2 Liter of Coke-Cola"));
    shoppingCart.add(Item.findByName("2 Liter of Coke-Cola"));
    shoppingCart.add(Item.findByName("2 Liter of Coke-Cola"));
    shoppingCart.add(Item.findByName("2 Liter of Coke-Cola"));
    shoppingCart.add(Item.findByName("2 Liter of Coke-Cola"));
    shoppingCart.add(Item.findByName("2 Liter of Coke-Cola"));
    this.coupons=Coupon.all();
  }

  @Test
  public void testSomething() {
    assert(false);
  }

  @Test
  public void scanItems() {
    Scanner scanner = new Scanner();
    scanner.beginTransactionSession();
    for (Item item : shoppingCart) {
      try {
        //item.setPrice(Scanner.calculateWeightInOuncesPrice(item.getPricePerOunce(),item.getWeightInOunces()));
        scanner.scan(item);
        if (scanner.getSignal().equals("please place on scale")) {
          String pricePerOunce = ItemWeightPriceLookup.getItemPricePerOunce(item);
          scanner.weigh(item, pricePerOunce);
        }
        System.out.println(scanner.getTotal());
      }
      catch (Exception e) {
        shoppingCart.remove(item);
        System.out.println("couldn't scan item.. so hide it under the counter and give perplexed look to the customer");
      }
    }
    scanner.endTransactionSession();

  }

  @Test
  public void acceptAScannedItem() {
    Scanner scanner = new Scanner();
    scanner.beginTransactionSession();
    Item item = new Item();
    scanner.endTransactionSession();
  }

  @Test
  public void getAllCoupons() {
    for (Coupon coupon:coupons) {
      System.out.println(coupon.getId()+" - "+coupon.getName()+" - "+coupon.getItem().getName());
    }
  }

  @Test
  public void isGroupBeefCouponed() {
    Item item=shoppingCart.get(0);
    System.out.println("pulled "+item.getName()+" out of the shopping cart and scanned it..");
    Scanner scanner = new Scanner();
    scanner.beginTransactionSession();
    try {
      System.out.println(scanner.getSignal() + " - total: " + scanner.getTotal());
      scanner.scan(item);
      System.out.println(scanner.getSignal() + " - total: " + scanner.getTotal());
      if (scanner.getSignal().equals("please place item on scale"))
        scanner.weigh(item, ItemWeightPriceLookup.getItemPricePerOunce(item));
      System.out.println(scanner.getSignal() + " - total: " + scanner.getTotal());
      Boolean isCouponed = Coupon.isCouponed(item);
      if (isCouponed == true) System.out.println("presses coupon button.. yes");
      else System.out.println("presses coupon button.. no");
    }
    catch (BabyFoundOnConveyorBeltException e) {
      System.out.println("pick up baby and hand back to parent and say.. sorry, but this one is priceless");
    }
    catch (Exception e) {
      System.out.println("couldn't scan item.. so hide "+item.getName()+" under the counter and give perplexed look to the customer");
      scanner.remove(item);
    }
    scanner.endTransactionSession();
    System.out.println(scanner.getSignal() + " - total: " + scanner.getTotal());
  }

  @Test
  public void isSoupCouponed() {
    Item item=Item.findByName("soup");
    System.out.println("item: "+item.getName()+", price: "+item.getPrice());
    Scanner scanner = new Scanner();
    scanner.beginTransactionSession();
    try {
      scanner.scan(item);
      scanner.scan(item);
      scanner.scan(item);
      if (Coupon.isCouponed(item) == true) System.out.println("presses coupon button.. yes");
    }
    catch (BabyFoundOnConveyorBeltException e) {
      System.out.println("pick up baby and hand back to parent and say.. sorry, but this one is priceless");
    }
    catch (Exception e) {
      System.out.println("couldn't scan item.. so hide "+item.getName()+" under the counter and give perplexed look to the customer");
      scanner.remove(item);
    }
    scanner.endTransactionSession();
    System.out.println(scanner.getSignal() + " - total: " + scanner.getTotal());
  }

  @Test
  public void orderAnArrayList() {
    List<Item> itemsScannedOrderedByPriceAsc = Item.all();
    Item[] itemsScannedOrderedByPriceAscAr = itemsScannedOrderedByPriceAsc.toArray(new Item[itemsScannedOrderedByPriceAsc.size()]);
    Arrays.sort(itemsScannedOrderedByPriceAscAr, new Comparator<Item>() {
      public int compare(Item o1, Item o2) {
        if (o1.getPrice()==null&&o2.getPrice()!=null) return -1;
        if (o1.getPrice()!=null&&o2.getPrice()==null) return 1;
        if (o1.getPrice()==null&&o2.getPrice()==null) return 0;
        if (Double.parseDouble(o1.getPrice())<=Double.parseDouble(o2.getPrice())) return -1;
        else return 1;
      }
    });
    for (Item item : itemsScannedOrderedByPriceAscAr) {
      System.out.println("item: "+item.getName()+", price: "+item.getPrice());
    }
  }

  @Test
  public void scanTurtleWax() {
    Scanner scanner = new Scanner();
    scanner.beginTransactionSession();
    shoppingCart=new ArrayList<>();
    shoppingCart.add(Item.findByName("Turtle Wax"));
    shoppingCart.add(Item.findByName("Turtle Wax"));
    shoppingCart.add(Item.findByName("Turtle Wax"));
    for (Item item : shoppingCart) {
      try {
        scanner.scan(item);
      }
      catch (Exception e) {
        shoppingCart.remove(item);
        System.out.println("couldn't scan item.. so hide it under the counter and give perplexed look to the customer");
      }
    }
    scanner.endTransactionSession();
  }
}
