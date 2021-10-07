package arm;

public class Add implements ARMInstruction {
  public final Register target;
  public final Register left;
  public final Operand right;

  public Add(Register target, Register left, Operand right) {
    this.target = target;
    this.left = left;
    this.right = right;
  }

  public String toString() {
    return String.format("add %s, %s, %s", this.target, this.left, this.right);
  }
}
