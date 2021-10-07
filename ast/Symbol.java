package ast;

public class Symbol {
  private Type symbolType;
  private DeclarationLocation location;

  public Symbol(Type symbolType, DeclarationLocation location) {
    this.symbolType = symbolType;
    this.location = location;
  }

  public DeclarationLocation getLoc() {
    return this.location;
  }

  public Type getType() {
    return this.symbolType;
  }

  public static enum DeclarationLocation {
    PUBLIC, PARAM, LOCAL, CURRENT, RETURN, STRUCT
  }
}

