import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import cfg.*;
import llvm.CFGtoLLVMVisitor;
import llvm.LLVMProgram;
import arm.*;

import java.util.List;
import java.util.HashMap;
import semantics.SemanticsVisitor;

import java.io.*;
import javax.json.JsonValue;

public class MiniCompiler
{
   public static void main(String[] args)
   {
      parseParameters(args);

      CommonTokenStream tokens = new CommonTokenStream(createLexer());
      MiniParser parser = new MiniParser(tokens);
      ParseTree tree = parser.program();

      if (parser.getNumberOfSyntaxErrors() == 0)
      {
         /*
            This visitor will create a JSON representation of the AST.
            This is primarily intended to allow use of languages other
            than Java.  The parser can thusly be used to generate JSON
            and the next phase of the compiler can read the JSON to build
            a language-specific AST representation.
         */
         MiniToJsonVisitor jsonVisitor = new MiniToJsonVisitor();
         JsonValue json = jsonVisitor.visit(tree);

         /*
            This visitor will build an object representation of the AST
            in Java using the provided classes.
         */
         MiniToAstProgramVisitor programVisitor =
            new MiniToAstProgramVisitor();
         ast.Program program = programVisitor.visit(tree);

         SemanticsVisitor typeCheck = new SemanticsVisitor();
         program.accept(typeCheck);

         ASTtoCFGVisitor buildCFG = new ASTtoCFGVisitor();
         program.accept(buildCFG);

         CFGtoLLVMVisitor writeLLVM;
         HashMap<String, Boolean> argsHash = new HashMap<String, Boolean>();
         if (args.length > 1) {
            for (int i = 1; i < args.length; i++) {
               argsHash.put(args[i], false);
            }
            writeLLVM = new CFGtoLLVMVisitor(argsHash);
         } else {
            writeLLVM = new CFGtoLLVMVisitor();
         }
         program.accept(writeLLVM);

         LLVMProgram llvmProg = writeLLVM.getProgram();

         if (argsHash.containsKey("-llvm")) {
            System.out.println(llvmProg.toString());
         } else {
            LLVMtoARMVisitor writeARM = new LLVMtoARMVisitor();
            writeARM.visit(llvmProg);
            for (ARMFunction func : writeARM.getFunctions()) {
               System.out.println(func.toString());
            }
         }
      }
   }

   private static String _inputFile = null;

   private static void parseParameters(String [] args)
   {
      _inputFile = args[0];
   }

   private static void error(String msg)
   {
      System.err.println(msg);
      System.exit(1);
   }

   private static MiniLexer createLexer()
   {
      try
      {
         CharStream input;
         if (_inputFile == null)
         {
            input = CharStreams.fromStream(System.in);
         }
         else
         {
            input = CharStreams.fromFileName(_inputFile);
         }
         return new MiniLexer(input);
      }
      catch (java.io.IOException e)
      {
         System.err.println("file not found: " + _inputFile);
         System.exit(1);
         return null;
      }
   }
}
