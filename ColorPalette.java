import java.util.TreeMap;
import java.awt.Color;

public class ColorPalette {
   TreeMap<String, Color> colors = new TreeMap<>();

   public ColorPalette() {
      this.colors.put("", new Color(204, 192, 179));
      this.colors.put("0", new Color(204, 192, 179));
      this.colors.put("2", new Color(238, 228, 218));
      this.colors.put("4", new Color(237, 224, 200));
      this.colors.put("8", new Color(242, 177, 121));
      this.colors.put("16", new Color(245, 149, 99));
      this.colors.put("32", new Color(246, 124, 95));
      this.colors.put("64", new Color(246, 94, 59));
      this.colors.put("128", new Color(237, 207, 114));
      this.colors.put("256", new Color(237, 204, 97));
      this.colors.put("512", new Color(237, 200, 80));
      this.colors.put("1024", new Color(237, 197, 63));
      this.colors.put("2048", new Color(237, 194, 46));
      this.colors.put("gameover", new Color(238, 228, 218));
   }

   public Color getColor(String val) {
      if (this.colors.keySet().contains(val)) {
         return this.colors.get(val);
      }
      return Color.BLACK;
   }
}
