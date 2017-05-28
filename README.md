Java Benchmarks
===============

We've seen many Java code snippets that suppose to improve some performance.
In many cases, there was _NO_ real improvement with the additional code.
Instead, the code gets bigger for no real reason.

With this project we will try to bust or prove some common performance myths.

## :crystal_ball: How do we measure?

It is not easy to write micro-benchmark code. Fortunately (and finally), we
have an excellent tool for that: [JMH](http://openjdk.java.net/projects/code-tools/jmh/).

## :notebook: How the project is organized?

Each benchmark suite is stored in a single class with a name that _must_
end with `Benchmark`. Every benchmark class explains in java docs what the
goal is, i.e. what is going to be measured and why. Each benchmark suite
should have at least two individual benchmark methods.

Moreover, each java doc will also contain the actual benchmark results.

## :rocket: How to run the benchmarks?

Easy :) The project is using [Gradle](http://www.gradle.org/).
Benchmarks may be run just by passing the bechmark class name. Since class name
must end with `Benchmark`, Gradle will recognize it and run it. For example:

    gradlew clean FooBenchmark

Running `clean` task first is optional.

When running benchmarks, try not to use your computer for anything else.
Try to minimize the number of background tasks.

## :watch: What does the values means?

Values of individual benchmark are **not** relevant! What matter is the
_relationship_ between the individual benchmarks and the standard deviation
of the values. Only by comparing the benchmark values we can say if one
has better performance then the other.

## :hotsprings: Use it with caution!

Don't take results as written in a stone. Think! Sometimes the input values
(_volume_ and the _content_) dictates the performance results.

## :hearts: Contribute!

Feel free to add your own benchmarks. Let us know if you want us to measure
something.

**Java benchmarks - the answers on common performance concerns!**
