package entities;

import java.util.ArrayList;
import java.util.List;

public class CouponItem {
/*  private Integer id;
  private Integer couponId;
  private Integer itemId; //must be unique

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getCouponId() {
    return couponId;
  }

  public void setCouponId(Integer couponId) {
    this.couponId = couponId;
  }

  public Integer getItemId() {
    return itemId;
  }

  public void setItemId(Integer itemId) {
    this.itemId = itemId;
  }

  //services
  public static List<CouponItem> findByCouponId(Integer couponId) {
    List<CouponItem> couponItems=CouponItem.all();
    List<CouponItem> couponItemsTemp=new ArrayList<>();
    for (CouponItem couponItem : couponItems) {
      if (couponItem.couponId==couponId) couponItemsTemp.add(couponItem);
    }
    return couponItemsTemp;
  }

  public static List<CouponItem> all() {
    List<CouponItem> couponItems=new ArrayList<>();
    CouponItem couponItem=new CouponItem();
    couponItem.setId(2);
    couponItem.setCouponId(2);//Buy 2 items get 1 at %50 off.
    couponItem.setItemId(3);//soup
    return couponItems;
  }
*/
}
