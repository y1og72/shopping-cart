package entities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Item {
  private Integer id;
  private String name;
  private String price;
  private String weightInOunces;
  private Boolean isWeighted=false;
  private BigDecimal priceAdjusted;

  public Item() {

  }

  public Item(Integer id, String name,String price, String weightInOunces, Boolean isWeighted) {
    this.id=id;
    this.name=name;
    this.price=price;
    this.weightInOunces=weightInOunces;
    this.isWeighted=isWeighted;
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

  public String getPrice() {
    return price;
  }

  public void setPrice(String price) {
    this.price = price;
  }

  public String getWeightInOunces() {
    return weightInOunces;
  }

  public void setWeightInOunces(String weightInOunces) {
    this.weightInOunces = weightInOunces;
  }

  public Boolean getWeighted() {
    return isWeighted;
  }

  public void setWeighted(Boolean weighted) {
    isWeighted = weighted;
  }

  public BigDecimal getPriceAdjusted() {
    return priceAdjusted;
  }

  public void setPriceAdjusted(BigDecimal priceAdjusted) {
    this.priceAdjusted = priceAdjusted;
  }

  //methods that are screaming for its own service class
  public static Item findById(Integer id) {
    Item item=null;
    for (Item itemSearch : Item.all()) {
      if (itemSearch.getId()==id) item=itemSearch;
    }
    return item;
  }

  public static List<Item> all() {
    List<Item> items=new ArrayList<>();
    Item item = new Item();
    item.setId(2);
    item.setName("ground beef");
    item.setWeightInOunces("8");
    item.setWeighted(true);
    items.add(item);
    item = new Item();
    item.setId(3);
    item.setName("soup");
    item.setPrice("3");
    items.add(item);
    items.add(new Item(4,"Spring rolls","3",null,false));
    items.add(new Item(5,"Turtle Wax","4",null,false));
    items.add(new Item(6,"2 Liter of Coke-Cola","1.89",null,false));
    items.add(new Item(7,"Sirloin","2.29","8.92",true));
    return items;
  }

  public static Item findByName(String name) {
    Item item=null;
    List<Item> items=Item.all();
    for (Item itemSearch : items) if (itemSearch.getName().equals(name)) item=itemSearch;
    return item;
  }

}
