package anneau.tp3;

import java.util.Calendar;
import java.util.Date;

/**
 * Util
 */
public class Util {

  public static String timestamp() {
    Calendar cal = Calendar.getInstance();
    Date d = new Date();
    cal.setTime(d);
    return cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE) + ":" + cal.get(Calendar.SECOND);
  }
}