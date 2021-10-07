package ast;

import semantics.Visitor;

public interface Expression
{
  public String toString(String sp);
  public void accept(Visitor v);
}
