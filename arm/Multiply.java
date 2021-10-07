package arm;

public class Multiply implements ARMInstruction {
  public final Register target;
  public final Register left;
  public final Register right;

  public Multiply(Register target, Register left, Register right) {
    this.target = target;
    this.left = left;
    this.right = right;
  }

  public String toString() {
    return String.format("mul %s, %s, %s", this.target, this.left, this.right);
  }
}
