# JMH Settings

Short overview of the most important JMH annotations.

## `BenchmarkMode` - type of the test

|Name|Description|
|---|---|
|`Mode.Throughput`|Calculate number of operations in a time unit.|
|`Mode.AverageTime`|Calculate an average running time.|
|`Mode.SampleTime`|Calculate how long does it take for a method to run (including percentiles).|
|`Mode.SingleShotTime`|Just run a method once (useful for cold-testing mode). |
| Any set of these modes| You can specify any set of these modes – the test will be run several times (depending on number of requested modes).|
|`Mode.All`	| All these modes one after another.|

## `State` - scope of the class

|Name|Description|
|---|---|
|`Scope.Thread`|This is a default state. An instance will be allocated for each thread running the given test.|
|`Scope.Benchmark`|An instance will be shared across all threads running the same test. Could be used to test multithreaded performance of a state object (or just mark your benchmark with this scope).|
|`Scope.Group`|An instance will be allocated per thread group.|

## `Forks`

By default JHM forks a new java process for each trial (set of iterations).