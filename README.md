## compiler-project
### Cole Cummins

This is a minilang compiler using SSA for CSC 431 at Cal Poly
Implemented Milestones 1-5

#### Running the Compiler 

##### `make clean`

Removes all .class files .out files and ANTLR generated files

##### `make`

Makes all ANTLR files and java class files

##### `java MiniCompiler <file_name> [<flags>]`

Takes a .mini file as input with file name, runs static type and return equivalence checking on given file, then compiles to llvm code and prints to standard out

Accepted flags and associated optimizations for the MiniCompiler are:

* `-c` - CFG compaction
* `-p` - constant propagation
* `-u` - useless code removal

#### Testing the Compiler

##### `python benchmarks.py [<flags>]`

Testing script that cleans and remakes all files, compiles llvm files for every benchmark, runs llvm static syntax checking on every file, and finally runs every ./a.out and diffs output.expected with output.actual. 

All benchmark folders must be in the folder `../benchmarks` in relation to python benchmarks.py

Accepted flags:

* `-t`: Timing of executibles to stdout
* `-o`: Output the result of all diffs 
* `-cfiles`: Run and time the c files in every benchmark folder, gcc flags must be changed manually
* `-c` - CFG compaction
* `-p` - constant propagation
* `-u` - useless code removal


