package arm;

public class Register implements Operand {
  private String name;

  public Register(String name) {
    this.name = name;
  }

  public String toString() {
    return name;
  }
}
