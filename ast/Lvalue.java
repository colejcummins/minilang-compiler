package ast;

import semantics.Visitor;

public interface Lvalue
{
  public void accept(Visitor v);
  public String getId();
}
