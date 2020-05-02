package work.csser;

import bloomfilter.CanGenerateHashFrom;
import bloomfilter.mutable.BloomFilter;
import work.csser.db.DBModule;
import work.csser.utils.SerializationDemonstrator;

import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author a1exlism
 * @className BloomFilter
 * @since 2020/1/4 15:53
 */
public class BloomFilterUtil {
  //  False Positive Probability
  private double falsePositiveRate = 0.01;

  /**
   * Generate BloomFilter from exist XSets in DB
   * default element numbers: 1,000,000
   *
   * @return void
   * @method bfGenerate
   * @params []
   */
  public void bfGenerate() throws SQLException {
    BloomFilter<byte[]> bf;

    long expectedElements = 1000000;

    bf = bloomfilter.mutable.BloomFilter.apply(
        expectedElements,
        this.falsePositiveRate,
        CanGenerateHashFrom.CanGenerateHashFromByteArray$.MODULE$);

    int offset = 0;
    int step = 5000;
    while (offset < expectedElements) {
      System.out.println(offset + "~" + (offset + step));
      ArrayList<String> XSets = DBModule.getXSets(offset, step);
      for (String xs : XSets) {
        bf.add(xs.getBytes(StandardCharsets.UTF_8));
      }
      offset += step;
    }
    SerializationDemonstrator.serialize(bf, "BloomFilter", "XSets.bf");
    bf.dispose();
  }

  /**
   * Generate BloomFilter with SPECIFIC number from exist XSets in DB
   *
   * @param expectedElements:
   * @return void
   * @method bfGenerate
   * @params [expectedElements]
   */
  public void bfGenerate(long expectedElements) throws SQLException {
    BloomFilter<byte[]> bf;

    bf = bloomfilter.mutable.BloomFilter.apply(
        expectedElements,
        this.falsePositiveRate,
        CanGenerateHashFrom.CanGenerateHashFromByteArray$.MODULE$);

    int offset = 0;
    int step = 5000;
    while (offset < expectedElements) {
      System.out.println(offset + "~" + (offset + step));
      ArrayList<String> XSets = DBModule.getXSets(offset, step);
      for (String xs : XSets) {
        bf.add(xs.getBytes(StandardCharsets.UTF_8));
      }
      offset += step;
    }
    SerializationDemonstrator.serialize(bf, "BloomFilter", "XSets.bf");
    bf.dispose();
  }

  /**
   * return XSet contain Probability
   *
   * @param xs: single XSet string
   * @return boolean
   * @method isInXSets
   * @params [xs]
   */
  public static boolean isInXSets(String xs) {
    BloomFilter<byte[]> bf = null;
    try {
      bf = SerializationDemonstrator.deserialize("BloomFilter", "XSets.bf");
    } catch (Error e) {
      e.printStackTrace();
      System.out.println("-- BloomFilter File read failed. --");
    }
    assert bf != null;
    return bf.mightContain(xs.getBytes(StandardCharsets.UTF_8));
  }
}
