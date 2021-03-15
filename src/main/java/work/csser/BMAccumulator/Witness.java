package work.csser.BMAccumulator;

import work.csser.utils.SerializableElement;

import java.io.Serializable;

/**
 * @author a1exlism
 * @description membership verification;
 * any member x of set X has a `unique` corresponding membership witness
 * @className Witness
 * @since 2020/4/21 17:41
 */
public class Witness implements Serializable {
  private static final long serialVersionUID = 199491344334407685L;
  private SerializableElement Uy;
  private SerializableElement Wy;

  public Witness(SerializableElement Wy, SerializableElement Uy) {
    this.Uy = Uy;
    this.Wy = Wy;
  }

  public SerializableElement getUy() {
    return Uy;
  }

  public SerializableElement getWy() {
    return Wy;
  }
}
