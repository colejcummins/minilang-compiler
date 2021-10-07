package arm;

public class Compare implements ARMInstruction {
  private final Operand left;
  private final Operand right;

  public Compare(Operand left, Operand right) {
    this.left = left;
    this.right = right;
  }

  public String toString() {
    return String.format("cmp %s, %s", this.left.toString(), this.right.toString());
  }
}
