package com.oblac.jmh.core;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;

import java.lang.reflect.Array;
import java.util.concurrent.TimeUnit;

/**
 * Benchmark new array creation. There the following scenarios:
 * <ul>
 *     <li>When type is known in compile type, using <code>new</code>.</li>
 *     <li>When type is known in runtime, using reflection</li>
 *     <li>By cloning an empty template array (when size is constant)</li>
 * </ul>
 * <pre>

 # VM invoker: /Library/Java/JavaVirtualMachines/jdk1.8.0_20.jdk/Contents/Home/jre/bin/java
 # VM options: -Dfile.encoding=UTF-8 -Duser.country=US -Duser.language=en -Duser.variant

 Benchmark                                    Mode  Samples       Score       Error   Units
 c.o.j.c.NewArrayBenchmark.cloneKnownType    thrpt       20  253372.206 ± 62914.803  ops/ms
 c.o.j.c.NewArrayBenchmark.reflection        thrpt       20  415388.670 ±  1741.215  ops/ms
 c.o.j.c.NewArrayBenchmark.usingNew          thrpt       20  415101.470 ±  2088.599  ops/ms

 * </pre>
 * <p>
 * Conclusion: on JDK8 reflection and <code>new</code> operator behave the same.
 * One more thing: after enough warmup (10 iterations), values are significantly better
 * then with e.g. 5 warmups (410k vs 310k).
 */
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 10, time = 1, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 10, time = 1, timeUnit = TimeUnit.MILLISECONDS)
@Threads(2)
@Fork(2)
@State(Scope.Thread)
@SuppressWarnings("unused")
public class NewArrayBenchmark {

	@Benchmark
	public String[] usingNew() {
		return new String[1];
	}

	@Benchmark
	public String[] reflection() {
		return (String[]) Array.newInstance(String.class, 1);
	}

	private static final String[] ARR = new String[1];

	@Benchmark
	public String[] cloneKnownType() {
		return ARR.clone();
	}

}