package work.csser.Init;


import work.csser.accumulator.BAPrivateKey;
import work.csser.accumulator.BAPublicKey;

/**
 * @author a1exlism
 * @description keygen for Master/Public Keys and Bilinear Map Accumulator M/P Keys
 * @className KeysGen
 * @since 2020/4/10 14:55
 */
public class KeysGen {
  //  TODO: 弄清楚 t 和 q 分别代表什么
  public static void main(String[] args) {

//    Master and Public
    MasterKey.generateKey();
    MasterKey MK = MasterKey.readKey();
    PublicKey.generateKey(MK, 300);

    System.out.println("VEDB keys generated;");

    BAKeyGen();

    System.out.println("Accumulator keys generated. with prefix `BA_`");
  }

  //    Bilinear Map Accumulator Master and Public
  public static void BAKeyGen() {
    BAPrivateKey.generateKey();
    BAPrivateKey BAMK = BAPrivateKey.readKey();
    //  TODO: temporary set q = 100(keyword: ids num)
    BAPublicKey.generateKey(BAMK, 100);
  }
}
