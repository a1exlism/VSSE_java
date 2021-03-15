package work.csser.Init;

import it.unisa.dia.gas.jpbc.Element;
import org.junit.Test;

/**
 * @author a1exlism
 * @className KeyGenTest
 * @description
 * @since 2019/12/8 13:16
 */
public class KeyGenTest {

  @Test
  public void GenAndRead() {
    /*
     * MasterKeyTest
     */
    //  generate
    MasterKey.generateKey();
    //  recovery
    MasterKey MK = MasterKey.readKey();
    System.out.println(MK.toString());
    /*
     * PublicKeyTest()
     */
    //  generate
    PublicKey.generateKey(MK, 30);
    //  recovery
    PublicKey PK = PublicKey.readKey();
    System.out.println(PK.toString());
  }

  @Test
  public void Immutable() {
    /*
     * TODO: move to JPBC test
     *  */
    MasterKey MK = MasterKey.readKey();
    boolean result = MK.getG1().getElement().isImmutable();
    System.out.println("Immutable: " + result);
    //    Immutable: false || Still changed

    Element g1 = MK.getG1().getElement();
    Element g2 = MK.getG2().getElement();
    Element g3 = MK.getG3().getElement();
    Element g1D = MK.getG1().getElement().duplicate();
    Element g2I = MK.getG2().getElement().getImmutable();
    System.out.println("== Before ==\n");
    System.out.printf("\tg1: %s\n\tg2: %s\n\tg3: %s\n\tg1D: %s%n \tg2I: %s%n", g1.toString(), g2.toString(), g3.toString(), g1D.toString(), g2I.toString());
    g2.mulZn(g1.duplicate());
    g1D.mulZn(g1.duplicate());
    g2I.mulZn(g1.duplicate());
    g2I.powZn(g3.invert());
    System.out.println("== After ==\n");
    System.out.printf("\tg1: %s\n\tg2: %s\n\tg3: %s\n\tg1D: %s%n \tg2I: %s%n", g1.toString(), g2.toString(), g3.toString(), g1D.toString(), g2I.toString());

    /*
     == Before ==
    	g1:  416639578597599174316228133190799352493712744036 SAME
    	g2:  127567295204633502738101395741812142179640712803
    	g3:  605479319796967899593519217369324990371918585500
	    g1D: 416639578597599174316228133190799352493712744036
 	    g2I: 127567295204633502738101395741812142179640712803 SAME
     == After ==
			g1:  416639578597599174316228133190799352493712744036 SAME
			g2:  186430045629776019353117567042097649822638721914
			g3:  213427828743057792090724773573834013406865240912
			g1D: 644528454050069616462955672657363899291678678626
 	 	 	g2I: 127567295204633502738101395741812142179640712803 SAME
    */


  }
}
