package ast;

import java.util.List;

public class FunctionType implements Type {

  private final String name;
  private final List<Type> params;
  private final Type returnType;

  public FunctionType(String name, List<Type> params, Type returnType) {
    this.name = name;
    this.params = params;
    this.returnType = returnType;
  }

  public String toString(String sp) {
    String out =  String.format("FunctionType %s -> %s%n%s(", this.name, this.returnType.toString(), sp);
    for (Type t : this.params) {
      out += t.toString() + " ";
    }
    return out + ")\n";
  }

  public String getName() {
    return this.name;
  }

  public List<Type> getParams() {
    return this.params;
  }

  public Type getReturnType() {
    return this.returnType;
  }
}
