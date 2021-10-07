package arm;

public class Immediate implements Operand {
  private final int value;

  public Immediate(int value) {
    this.value = value;
  }

  public String toString() {
    return String.format("#%d", this.value);
  }

  public int getValue() {
    return this.value;
  }
}
