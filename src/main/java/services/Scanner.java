package services;

import entities.Coupon;
import entities.CouponItem;
import entities.Item;
import exceptions.PlaceOnScaleException;

import java.math.BigDecimal;
import java.util.*;

public class Scanner {
  private String signal;
  private Double total;
  private ArrayList<Item> itemsScanned;

  public String getSignal() {
    return signal;
  }

  public void setSignal(String signal) {
    this.signal = signal;
  }

  public Double getTotal() {
    //make a copy
    //List<Item> itemsScannedOrderedByPriceAsc = (List<Item>)itemsScanned.clone();

    //iterate through the coupons
    List<Coupon> coupons = Coupon.all();
    for (Coupon coupon : coupons) {
      for (Item item : itemsScanned) {
        List<Item> itemsAffectedByCoupon = findItemsScannedByItemId(coupon.getItem().getId());
        int incr=0;
        for (Item itemAffectedByCoupon : itemsAffectedByCoupon) {
          incr++;
          if (coupon.getName().equals("Buy 2 items get 1 at %50 off.")) {
            if (incr%3==0) itemAffectedByCoupon.setPriceAdjusted(new BigDecimal((Double.parseDouble(item.getPrice()))*.5));
          } else if (coupon.getName().equals("Buy 1 get 1 free.")) {
            if (incr%2==0) itemAffectedByCoupon.setPriceAdjusted(new BigDecimal(0d));
          } else if (coupon.getName().equals("3 for $5.00")) {
            //set in groups of three
            if (incr%3==0) {
              itemAffectedByCoupon.setPriceAdjusted(new BigDecimal(1.67d));
              itemsAffectedByCoupon.get(incr-1-1).setPriceAdjusted(new BigDecimal(1.66d));
              itemsAffectedByCoupon.get(incr-2-1).setPriceAdjusted(new BigDecimal(1.67d));
            }
          } else if (coupon.getName().equals("buy 2 get 1 free, limit 6")) {
            if (incr%3==0&&incr<=6) itemAffectedByCoupon.setPriceAdjusted(new BigDecimal(0d));
          } else if (coupon.getName().equals("Buy 2, get 3rd of equal or lesser value for %20 off")) {
            //list is sorted in price desc so this should work
            if (incr%3==0) itemAffectedByCoupon.setPriceAdjusted(new BigDecimal(Double.parseDouble(item.getPrice())*.8));
          }
        }
      }
    }

    this.total=0d;
    for (Item item : itemsScanned) {
      if (item.getPriceAdjusted()!=null) total+=item.getPriceAdjusted().doubleValue();
      else total+=Double.parseDouble(item.getPrice());
    }
    return total;
  }

  public void setTotal(Double total) {
    this.total = total;
  }

  public ArrayList<Item> getItemsScanned() {
    return itemsScanned;
  }

  public void setItemsScanned(ArrayList<Item> itemsScanned) {
    this.itemsScanned = itemsScanned;
  }

  public void beginTransactionSession() {
    this.signal="Scanner loading up";
    this.total=0d;
    itemsScanned=new ArrayList<>();
  }

  public void endTransactionSession() {
    this.signal="Scanner shutting down... 3..2..1.. power off";
  }

  public void scan(Item item, Boolean weighedAction) throws Exception {
    if (item.getWeighted()==true&&weighedAction==false) {
      this.signal="please place item on scale";
      return;
    }
    total=total+Double.parseDouble(item.getPrice());
    this.signal=item.getName()+ " ($"+item.getPrice()+")";
    this.itemsScanned.add(item);
    System.out.println("scanned "+item.getName()+" for $"+item.getPrice()+" - total "+this.getTotal());
  }

  public void scan(Item item) throws Exception {
    scan(item, false);
  }

  public void weigh(Item item, String pricePerOunce) throws Exception {
    Double priceCalulated=(Double.parseDouble(item.getWeightInOunces())*Double.parseDouble(pricePerOunce));
    item.setPrice(String.valueOf(priceCalulated));
    this.scan(item, true);
  }

  public void remove(Item item) {
    total=total-Double.parseDouble(item.getPrice());
    this.itemsScanned.remove(item);
  }

  public List<Item> findItemsScannedByItemId(Integer id) {
    List<Item> items=new ArrayList<>();
    for (Item item : getItemsScanned()) {
      if (item.getId()==id) items.add(item);
    }
    Item[] itemsSortedByPrice = items.toArray(new Item[items.size()]);
    Arrays.sort(itemsSortedByPrice, new Comparator<Item>() {
      public int compare(Item o1, Item o2) {
        if (o1.getPrice()==null&&o2.getPrice()!=null) return -1;
        if (o1.getPrice()==null&&o2.getPrice()==null) return 0;
        if (o1.getPrice()!=null&&o2.getPrice()==null) return 1;
        if (Double.parseDouble(o1.getPrice())<=Double.parseDouble(o2.getPrice())) return -1;
        else return 1;
      }
    });
    items=Arrays.asList(itemsSortedByPrice);
    Collections.reverse(items);
    return items;
  }
}
