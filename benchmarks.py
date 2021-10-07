import sys
import os
import time
import subprocess

def main():
  resources = '../benchmarks'
  compilation_failures = {}
  files = []
  run_files = []
  failed_files = {}
  run_passed = [0]

  print("---BENCHMARKS---\nRemaking all classfiles....\n")
  os.system('make clean')
  status = os.system('make')

  compiler_flags = []
  testing_flags = []
  for arg in sys.argv[1:]:
    if arg in ['-u', '-p', '-c', '-llvm']:
      compiler_flags += [arg]
    if arg in ['-o', '-t', '-cfiles']:
      testing_flags += [arg]

  if (status == 0):
    write_llvm_files(resources, compilation_failures, files, compiler_flags)

    if (len(compilation_failures.items()) > 0):
      print("\n---COMPILATION FAILURES---\nThe following benchmarks failed to compile")
      for k, v in compilation_failures.items():
        print(f"{k}\n")

    check_llvm_files(files, run_files)
    run_llvm_files(run_files, failed_files, run_passed, testing_flags)

    if '-cfiles' in testing_flags:
      c_file_timings(files)

    output = f"\n---RESULTS---\nCOMPILATION: {len(files)} / 20\n\nLLVM SYNTAX: {len(run_files)} / 20\n\nCORRECT OUTPUT: {run_passed[0]} / 20\n\n"
    print(output)


#
# Generates llvm files from MiniCompiler
#
def write_llvm_files(res_dir, failures, files, args):
  for folder_name in os.listdir(res_dir):

    if folder_name[0] != '.':
      cur_dir = res_dir + '/' + folder_name

      for file_name in os.listdir(cur_dir):

        if len(file_name) > 4 and file_name[-4:] == 'mini':
          write_file_name = cur_dir + '/' + file_name[:-5] + '.ll'
          print("# WRITING TO -> " + write_file_name + "\n")
          write_file = open(write_file_name, 'w')
          cmd = ['java', 'MiniCompiler', cur_dir + '/' + file_name, *args]
          print(cmd)
          out = subprocess.call(cmd, stdout=write_file)

          if (out != 0):
            failures[cur_dir + '/' + file_name] = 1

          else:
            files += [(cur_dir, write_file_name)]

          write_file.close()


#
# Checks llvm files for syntax errors
#
def check_llvm_files(files, run_files):
  print("\n---CHECKING FILES---")
  failures = {}

  for file in files:
    print('# CHECKING -> ' + file[1] + '\n')
    out = subprocess.call(['clang', file[1]])

    if (out != 0):
      failures[file[1]] = 1

    else:
      run_files += [file]

  if (len(failures.items()) > 0):
    print("\n---CLANG FAILURES---\nThe following benchmarks have llvm syntax errors")
    for k, _ in failures.items():
      print(f"{k}\n")


def sort_by(e):
  return e[1].split('/')[-1].lower()


#
# Runs llvm files and diffs expected output with actual output
#
def run_llvm_files(run_files, failed_files, run_passed, args):
  print("\n---RUNNING EXECUTIBLES---")
  run_files.sort(key=sort_by)

  for file in run_files:

    if '-t' not in args:
      print('\n# RUNNING -> ' + file[1])

    subprocess.call(['clang', file[1]])
    input_file = open(file[0] + '/input')
    output_file = open(file[0] + '/output.actual', 'w')

    if '-t' in args:
      time_sum = 0

      start = time.perf_counter()
      subprocess.call(['./a.out'], stdin=input_file, stdout=output_file)
      end = time.perf_counter()
      time_sum += end - start
      print(file[1].split('/')[-1] + '  {:.5f} sec'.format(time_sum))

    else:
      subprocess.call(['./a.out'], stdin=input_file, stdout=output_file)

    out = subprocess.run(['diff', '-w', file[0] + '/output.expected', file[0] + '/output.actual'], stdout=subprocess.PIPE)
    output = out.stdout.decode('unicode_escape')

    if (len(output) > 0):
      failed_files[file[1]] = output
      if '-o' in args:
        print(output)

    else:
      run_passed[0] += 1

      if '-t' not in args:
        print("PASSED!")


#
# Runs C files for default timings
#
def c_file_timings(files):
  files.sort(key=sort_by)

  for file in files:
    subprocess.call(['gcc', file[1][:-2] + 'c', '-O0'], stdout=subprocess.DEVNULL, stderr=subprocess.DEVNULL)

    input_file = open(file[0] + '/input')

    time_sum = 0
    start = time.perf_counter()
    subprocess.call(['./a.out'], stdin=input_file, stdout=subprocess.DEVNULL)
    end = time.perf_counter()
    time_sum += end - start
    print(file[1].split('/')[-1] + '  {:.5f} sec'.format(time_sum))


if __name__ == '__main__':
  main()