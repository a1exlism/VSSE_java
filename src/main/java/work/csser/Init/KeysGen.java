package work.csser.Init;


import work.csser.BMAccumulator.BMAPrivateKey;
import work.csser.BMAccumulator.BMAPublicKey;

/**
 * @author a1exlism
 * @description keygen for Master/Public Keys and Bilinear Map Accumulator M/P Keys
 * @className KeysGen
 * @since 2020/4/10 14:55
 */
public class KeysGen {
  public static void main(String[] args) {

//    Master and Public
    MasterKey.generateKey();
    MasterKey MK = MasterKey.readKey();
    PublicKey.generateKey(MK, 300);

    System.out.println("VEDB keys generated;");

    BAKeyGen();

    System.out.println("Accumulator keys generated. with prefix `BMA_`");
  }

  //    Bilinear Map Accumulator Master and Public
  public static void BAKeyGen() {
    BMAPrivateKey.generateKey();
    BMAPrivateKey BMAMK = BMAPrivateKey.readKey();
    //  TODO: temporary set q = 100(keyword: ids num)
    BMAPublicKey.generateKey(BMAMK, 100);
  }
}
