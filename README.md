To compile the risk report generator:
```
mvn clean compile assemble:single
```
Use the same Maven command to compile the Benchmarking for
risk report generator.

Note: to benchmark risk report generator, risk report generator should
be started first, then the benchmarking program within tests

You may be able to just use the jar files in the target directories
as they've been compiled as executable jars.

For Benchmarking code, there are 4 seperate benchmarks configured which
are enabled using booleans in the App class. If one wishes to enable/disable
benchmarks, this file should be modified and then the program will need to be
recompiled using the Maven command above.
