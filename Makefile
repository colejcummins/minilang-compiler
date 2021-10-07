# Should really move to something else to manage the build.

# Provided code assumes at least Java 8
JAVAC=javac
JAR=jar
JAVA=java
ANTLRJAR=/home/akeen/public/antlr4/antlr-4.8-complete.jar
JSONJAR=/home/akeen/public/javax.json-1.0.4.jar

CLASSPATH_MOD=$(ANTLRJAR):$(JSONJAR)

FILES=MiniCompiler.java MiniToJsonVisitor.java MiniToAstDeclarationsVisitor.java MiniToAstExpressionVisitor.java MiniToAstFunctionVisitor.java MiniToAstProgramVisitor.java MiniToAstStatementVisitor.java MiniToAstTypeDeclarationVisitor.java MiniToAstTypeVisitor.java

GENERATED=MiniBaseVisitor* MiniLexer.java MiniLexer.tokens Mini.tokens MiniVisitor* MiniParser* MiniBaseListener* MiniListener*

GENERATED2='MiniBaseVisitor 2.java' 'MiniLexer 2.java' 'MiniLexer 2.tokens' 'Mini 2.tokens' 'MiniVisitor 2.java' 'MiniParser 2.java' 'MiniBaseListener 2.java' 'MiniListener 2.java'

all : MiniCompiler.class

MiniCompiler.class : antlr.generated ${FILES}
	$(JAVAC) -cp ${CLASSPATH}:$(CLASSPATH_MOD) *.java ast/*.java
	$(JAVAC) -cp ${CLASSPATH}:$(CLASSPATH_MOD) *.java arm/*.java
	$(JAVAC) -cp ${CLASSPATH}:$(CLASSPATH_MOD) *.java cfg/*.java
	$(JAVAC) -cp ${CLASSPATH}:$(CLASSPATH_MOD) *.java llvm/*.java
	$(JAVAC) -cp ${CLASSPATH}:$(CLASSPATH_MOD) *.java semantics/*.java

antlr.generated : Mini.g4
	$(JAVA) -cp ${CLASSPATH}:$(CLASSPATH_MOD) org.antlr.v4.Tool -visitor Mini.g4
	touch antlr.generated

clean:
	\rm -rf *generated* ${GENERATED} ${GENERATED2} *.class ast/*.class arm/*.class cfg/*.class llvm/*.class semantics/*.class *.jar ast/*.jar cfg/*.jar llvm/*.jar semantics/*.jar *.out
