package cfg;

import java.util.List;

import ast.Type;
import ast.Declaration;
import ast.Function;

public class FunctionWrapper {
  private final String name;
  private final Type retType;
  private final List<Declaration> params;
  private final List<Declaration> locals;
  private final BasicBlock body;

  public FunctionWrapper(Function func, BasicBlock body) {
    this(func.getName(), func.getParams(), func.getRetType(), func.getLocals(), body);
  }

  public FunctionWrapper(String name, List<Declaration> params,
  Type retType, List<Declaration> locals, BasicBlock body) {
    this.name = name;
    this.params = params;
    this.retType = retType;
    this.locals = locals;
    this.body = body;
  }

  public String toString() {
    return "func "  + this.name + "\n" + this.body.toString();
  }

  public String getName() {
    return this.name;
 }

 public Type getRetType() {
    return this.retType;
 }

 public List<Declaration> getParams() {
    return this.params;
 }

 public List<Declaration> getLocals() {
    return this.locals;
 }

 public BasicBlock getBody() {
    return this.body;
 }
}
