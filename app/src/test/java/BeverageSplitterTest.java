/*
  Copyright 2015 José Luis De la Cruz Morales <joseluis.delacruz@gmail.com>

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
*/

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
