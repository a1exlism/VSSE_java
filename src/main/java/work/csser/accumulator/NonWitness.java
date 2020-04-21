package work.csser.accumulator;

import work.csser.utils.SerializableElement;

import java.io.Serializable;

/**
 * @author a1exlism
 * @className NonWitness
 * @since 2020/4/21 17:37
 */
public class NonWitness implements Serializable {

  private static final long serialVersionUID = 199491344334407685L;
  private SerializableElement Uy;
  private SerializableElement Wy;

  public NonWitness(SerializableElement Uy, SerializableElement Wy) {
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
