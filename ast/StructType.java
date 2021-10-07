package ast;

import java.sql.Struct;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class StructType
   implements Type
{
   private final int lineNum;
   private final String name;
   private final Map<String, Type> fields;

   public StructType(int lineNum, String name)
   {
      this(lineNum, name, new HashMap<String, Type>());
   }

   public StructType(int lineNum, String name, Map<String, Type> fields) {
      this.lineNum = lineNum;
      this.name = name;
      this.fields = fields;
   }

   public String toString() {
      String out = "StructType " + this.name + "\n";
      for (Entry<String, Type> e : this.fields.entrySet()) {
         out += String.format("%s -> %s%n", e.getKey(), e.getValue());
      }
      return out;
   }

   public Map<String, Type> getFields() {
      return this.fields;
   }

   public String getName() {
      return this.name;
   }

   public boolean structEquals(Object obj) {
      if (obj instanceof StructType) {
         if ((this.name.equals(((StructType)obj).getName())))
            return true;
      }
      if (obj instanceof VoidType) {
         return true;
      }
      return false;
   }

}
