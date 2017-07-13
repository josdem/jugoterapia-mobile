import org.junit.Test;
import static org.junit.Assert.assertEquals;

import com.jugoterapia.josdem.util.BeverageSplitter;

public class BeverageSplitterTest {

  @Test
  public void shouldSplitBeverages(){
    String ingredients = "2 Zanahorias,1 Pepino,1 Rama de apio";
    String result = BeverageSplitter.split(ingredients);
    assertEquals(result, "2 Zanahorias" + '\n' + "1 Pepino" + '\n' + "1 Rama de apio" + '\n');
  }

}
