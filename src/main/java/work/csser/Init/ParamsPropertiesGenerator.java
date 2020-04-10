package work.csser.Init;

import it.unisa.dia.gas.jpbc.PairingParameters;
import it.unisa.dia.gas.jpbc.PairingParametersGenerator;
import it.unisa.dia.gas.plaf.jpbc.pairing.a1.TypeA1CurveGenerator;
import work.csser.utils.Common;

import java.io.IOException;

/**
 * ATTENTION this may take a lot times, and it's NOT necessary.
 * Description: Generate Params for jPBC;
 * This class SHOULD run before all java and default params is a.properties;
 */
public class ParamsPropertiesGenerator {
  /**
   * generate random curve params provided by jPBC library
   * Generally this method NO need to call.
   */
  public static void createParams() throws IOException {
    int rBits = 160;
    int qBits = 512;
    // jPBC Type A pairing generator...
    PairingParametersGenerator pg = new TypeA1CurveGenerator(rBits, qBits);
    PairingParameters params = pg.generate();
    // save pairing parameters
    Common.writeFile("resources", "type_a.properties", params.toString().getBytes());
  }
}
