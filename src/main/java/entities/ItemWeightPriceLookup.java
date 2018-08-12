package entities;

public class ItemWeightPriceLookup {
  public static String getItemPricePerOunce(Item item) throws Exception {
    if (item.getName().equals("ground beef")) return "1";
    if (item.getName().equals("sirloin")) return "2";
    throw new Exception("error.. unknown price");
  }
}
