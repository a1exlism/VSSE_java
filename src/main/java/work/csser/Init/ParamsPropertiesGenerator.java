package work.csser.Init;

import it.unisa.dia.gas.jpbc.PairingParameters;
import it.unisa.dia.gas.jpbc.PairingParametersGenerator;
import it.unisa.dia.gas.plaf.jpbc.pairing.a1.TypeA1CurveGenerator;
import work.csser.utils.Common;

public class ParamsPropertiesGenerator {
  /*
   * @description: generate random curve params
   */
  public static void createParams() {
    int rBits = 160;
    int qBits = 512;
    // JPBC Type A pairing generator...
    PairingParametersGenerator pg = new TypeA1CurveGenerator(rBits, qBits);
    PairingParameters params = pg.generate();
    // save pairing parameters
    String fPath = System.getProperty("user.dir") + "/src/main/java/resources";
    Common.writeFile(fPath, "type_a.properties", params.toString().getBytes());
  }
}
