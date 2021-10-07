package arm;

public class Subtract implements ARMInstruction {
  public final Register target;
  public final Register left;
  public final Operand right;

  public Subtract(Register target, Register left, Operand right) {
    this.target = target;
    this.left = left;
    this.right = right;
  }

  public String toString() {
    return String.format("sub %s, %s, %s", this.target, this.left, this.right);
  }
}
