<!-- vscode-markdown-toc -->
* 1. [Testing stand](#Testingstand)
* 2. [Comparison table](#Comparisontable)
* 3. [Raw data output](#Rawdataoutput)
	* 3.1. [GC1](#GC1)
	* 3.2. [CMS](#CMS)
	* 3.3. [ParallelGC](#ParallelGC)
	* 3.4. [SerialGC](#SerialGC)

<!-- vscode-markdown-toc-config
	numbering=true
	autoSave=true
	/vscode-markdown-toc-config -->
<!-- /vscode-markdown-toc -->

##  1. <a name='Testingstand'></a>Testing stand

Java version "1.8.0_201"

List of GC's: GC1, CMS, ParallelGC, SerialGC

Processor:	Intel(R) Core(TM) i7-6820HQ CPU @ 2.70GHz

Memory:	16314MB

Operating System:	Ubuntu 18.04.1 LTS

Memory VM args: -Xms512m -Xmx512m -XX:MaxMetaspaceSize=512m


##  2. <a name='Comparisontable'></a>Comparison table

|GC         |Collection Time   |Collection Count   |List size till crash   |Time till crash   |
|-----------|------------------|-------------------|-----------------------|------------------|
|GC1        |1702              |43                 |6905283                |294sec            |
|CMS        |603               |8                  |6153400                |254sec            |
|ParallelGC |252               |5                  |6153400                |259sec            |
|SerialGC   |474               |6                  |6153400                |252sec            |

**GC1 helped to collect more items to list. GC1 executed garbage collection more times and took more time for garbage collection.**


##  3. <a name='Rawdataoutput'></a>Raw data output

###  3.1. <a name='GC1'></a>GC1
~~~
=======================GC Stats========================
name of memory manager:G1 Young Generation
Collection Time:1702
Collection Count:43
G1 Eden Space
G1 Eden Space
name of memory manager:G1 Old Generation
Collection Time:10257
Collection Count:13
G1 Eden Space
G1 Eden Space
G1 Eden Space
====================Memory Pools Stats=====================
:: Pool name ::                     .:Usage:.
Code Cache                           1924 Kb
Metaspace                            4668 Kb
Compressed Class Space                506 Kb
G1 Eden Space                           0 Kb
G1 Survivor Space                       0 Kb
G1 Old Gen                            874 Kb
Number of list items that were added before the crash: 6905283
Time taken: 294sec
~~~

###  3.2. <a name='CMS'></a>CMS

~~~
=======================GC Stats========================
name of memory manager:ParNew
Collection Time:603
Collection Count:8
Par Eden Space
Par Eden Space
name of memory manager:ConcurrentMarkSweep
Collection Time:4702
Collection Count:34
Par Eden Space
Par Eden Space
Par Eden Space
====================Memory Pools Stats=====================
:: Pool name ::                     .:Usage:.
Code Cache                           1902 Kb
Metaspace                            4659 Kb
Compressed Class Space                506 Kb
Par Eden Space                     110009 Kb
Par Survivor Space                      0 Kb
CMS Old Gen                        349567 Kb
Number of list items that were added before the crash: 6153400
Time taken: 254sec
~~~

###  3.3. <a name='ParallelGC'></a>ParallelGC

~~~
=======================GC Stats========================
name of memory manager:PS Scavenge
Collection Time:252
Collection Count:5
PS Eden Space
PS Eden Space
name of memory manager:PS MarkSweep
Collection Time:9457
Collection Count:6
PS Eden Space
PS Eden Space
PS Eden Space
====================Memory Pools Stats=====================
:: Pool name ::                     .:Usage:.
Code Cache                           1909 Kb
Metaspace                            4663 Kb
Compressed Class Space                506 Kb
PS Eden Space                      109705 Kb
PS Survivor Space                       0 Kb
PS Old Gen                         349416 Kb
Number of list items that were added before the crash: 6153400
Time taken: 259sec
~~~

###  3.4. <a name='SerialGC'></a>SerialGC

~~~
=======================GC Stats========================
name of memory manager:Copy
Collection Time:474
Collection Count:6
Eden Space
Eden Space
name of memory manager:MarkSweepCompact
Collection Time:2086
Collection Count:4
Eden Space
Eden Space
Eden Space
====================Memory Pools Stats=====================
:: Pool name ::                     .:Usage:.
Code Cache                           1938 Kb
Metaspace                            4667 Kb
Compressed Class Space                506 Kb
Eden Space                         110000 Kb
Survivor Space                          0 Kb
Tenured Gen                        349567 Kb
Number of list items that were added before the crash: 6153400
Time taken: 252sec
~~~