package render;

import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.Cursor;
import application.App;

public class CursorUtil {
  public static Cursor cursor;

  public static void setCursorBlank() {
    BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
    setCursor(cursorImg);
  }
  public static void setCursor(BufferedImage img){
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
		    img, new java.awt.Point(0, 0), "blank cursor");

		App.app.getContentPane().setCursor(blankCursor);
  }
}
