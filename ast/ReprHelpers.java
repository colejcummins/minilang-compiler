package ast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReprHelpers<T> {
  public String tToString(List<T> list, String sp) {
    String out = "";
    for (T t : list) {
      out += sp + "  " + t.toString() + "\n";
    }
    return out;
  }

  public static void main(String[] args) {
    List<BoolType> bools = new ArrayList<BoolType>(Arrays.asList(new BoolType(), new BoolType()));
    ReprHelpers<BoolType> n = new ReprHelpers<BoolType>();
    System.out.println(n.tToString(bools, " "));
  }
}
