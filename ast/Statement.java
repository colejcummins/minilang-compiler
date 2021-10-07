package ast;

import semantics.Visitor;

public interface Statement
{
  public String toString(String sp);
  public void accept(Visitor v);
}
