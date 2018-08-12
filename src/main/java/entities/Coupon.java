package entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Coupon {
  private Integer id;
  private String name;
  private Item item;
  //private List<CouponItem> couponItems=new ArrayList<>();

  public Coupon() {

  }
  public Coupon(Integer id, String name, Item item) {
    this.id=id;
    this.name=name;
    this.item=item;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Item getItem() {
    return item;
  }

  public void setItemId(Integer itemId) {
    this.item = item;
  }

/*  public List<CouponItem> getCouponItems() {
    List<CouponItem> couponItems=CouponItem.findByCouponId(this.id);
    return couponItems;
  }

  public void setCouponItems(List<CouponItem> couponItems) {
    this.couponItems = couponItems;
  }
*/

  //service methods
  public static List<Coupon> findByItemId(Integer itemId) {
    List<Coupon> coupons=Coupon.all();
    List<Coupon> couponsTemp=new ArrayList<>();
    for (Coupon coupon : coupons) {
      if (coupon.getItem().getId()==itemId) couponsTemp.add(coupon);
    }
    return couponsTemp;
  }

  public static List<Coupon> all() {
    List<Coupon> coupons=new ArrayList();

    Coupon coupon=new Coupon(2,"Buy 2 items get 1 at %50 off.", Item.findById(3)); //soup
    coupons.add(coupon);
    coupon=new Coupon(3,"Buy 1 get 1 free.", Item.findById(4)); //spring rolls
    coupons.add(coupon);
    coupon=new Coupon(4,"3 for $5.00", Item.findById(5)); //turtle wax
    coupons.add(coupon);
    coupon=new Coupon(5,"buy 2 get 1 free, limit 6", Item.findById(6)); // coke products
    coupons.add(coupon);
    coupon=new Coupon(6,"Buy 2, get 3 of equal or lesser value for %20 off", Item.findById(7)); //sirloin
    coupons.add(coupon);
    return coupons;
  }

  public static Boolean isCouponed(Item item) {
    if (Coupon.findByItemId(item.getId()).size()>0) return true;
    return false;
  }

  public static Boolean qualifies(Coupon coupon, List<Item> items) {
    return false;
  }

}
