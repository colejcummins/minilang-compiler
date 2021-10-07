package ast;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import ast.Symbol.DeclarationLocation;
import cfg.*;
import llvm.CFGtoLLVMVisitor;
import semantics.SemanticsVisitor;
import semantics.Visitor;

public class Program
{
   private final List<TypeDeclaration> types;
   private final List<Declaration> decls;
   private final List<Function> funcs;
   private List<FunctionWrapper> cfgFuncs;

   public Program(List<TypeDeclaration> types, List<Declaration> decls,
      List<Function> funcs)
   {
      this.types = types;
      this.decls = decls;
      this.funcs = funcs;
      this.cfgFuncs = null;
   }

   public String toString() {
      String sp = "  ";
      String out = "Structs ->\n";
      for (TypeDeclaration t : this.types) {
         out += sp + t.toString(sp) + "\n";
      }
      out += "Declarations ->\n";
      for (Declaration d : this.decls) {
         out += d.toString(sp) + "\n";
      }
      out += "\nFunctions ->\n";
      for (Function f : this.funcs) {
         out += sp + f.toString(sp) + "\n";
      }
      return out;
   }

   public void accept(SemanticsVisitor v) {
      v.getSymbols().put("__location__", new Symbol(new VoidType(), DeclarationLocation.PUBLIC));
      for (TypeDeclaration t : this.types) {
         t.accept(v);
      }

      for (Declaration d : this.decls) {
         d.accept(v);
      }

      for (Function f : this.funcs) {
         if (v.getFunctions().containsKey(f.getName())) {
            System.out.printf("ERROR: Function %s already exists, cannot redeclare%n", f.getName());
            v.addError(1);
         }

         List params = new ArrayList<Type>();
         for (Declaration d : f.getParams()) {
            params.add(d.getType());
         }
         if (f.getRetType() instanceof StructType) {
            v.addFunction(f.getName(), new FunctionType(f.getName(), params, v.getStructs().get(((StructType)f.getRetType()).getName())));
         } else {
            v.addFunction(f.getName(), new FunctionType(f.getName(), params, f.getRetType()));
         }

         Map<String, Symbol> syms = new HashMap<String, Symbol>();
         Map<String, FunctionType> funcs = new HashMap<String, FunctionType>();
         Map<String, StructType> structs = new HashMap<String, StructType>();
         syms.putAll(v.getSymbols());
         funcs.putAll(v.getFunctions());
         structs.putAll(v.getStructs());
         f.accept(new SemanticsVisitor(syms, funcs, structs));
      }
   }

   public void accept(ASTtoCFGVisitor v) {
      cfgFuncs = new ArrayList<FunctionWrapper>();
      for (Function f : this.funcs) {
         ASTtoCFGVisitor visitor = new ASTtoCFGVisitor(v.getCount());
         f.accept(visitor);
         cfgFuncs.add(new FunctionWrapper(f, visitor.getCFG()));
         v.setCount(visitor.getCount() + (10 - visitor.getCount()%10));
      }
   }

   public void accept(CFGtoLLVMVisitor v) {
      v.visit(this);
   }

   public List<FunctionWrapper> getCFGFuncs() {
      return this.cfgFuncs;
   }

   public List<TypeDeclaration> getTypes() {
      return this.types;
   }

   public List<Declaration> getDeclarations() {
      return this.decls;
   }
}
