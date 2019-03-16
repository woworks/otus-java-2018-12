
### Testing stand

Java version "1.8.0_201"

List of GC's: GC1, CMS, ParallelGC, SerialGC

Processor:	Intel(R) Core(TM) i7-6820HQ CPU @ 2.70GHz

Memory:	16314MB

Operating System:	Ubuntu 18.04.1 LTS

Memory VM args: -XX:+UseG1GC -Xms8m -Xmx8m


### Results

Between 4000 and 5000 cache items were Garbage Collected.

```
....
>>>>>>>>>>>>>>>: '4' will be GC'ed!
>>>>>>>>>>>>>>>: '123' will be GC'ed!
>>>>>>>>>>>>>>>: '144' will be GC'ed!
....
```

total:

```
=======================GC Stats========================
name of memory manager:G1 Young Generation
Collection Time:60
Collection Count:9
G1 Eden Space
G1 Eden Space
name of memory manager:G1 Old Generation
Collection Time:0
Collection Count:0
G1 Eden Space
G1 Eden Space
G1 Eden Space
====================Memory Pools Stats=====================
:: Pool name ::                     .:Usage:.
Code Cache                           2031 Kb
Metaspace                            4840 Kb
Compressed Class Space                537 Kb
G1 Eden Space                        1024 Kb
G1 Survivor Space                    1024 Kb
G1 Old Gen                           3533 Kb
                                                      Cache hits: 13833
                                                      Cache misses: 4167
```