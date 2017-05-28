package com.oblac.jmh.math;

import com.oblac.jmh.math.impl.SineLookup;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;

import java.util.concurrent.TimeUnit;

/**
 * Benchmark of various sine implementations.
 * Following implementations are available:
 * <ul>
 *		<li>Default - using math directly.</li>
 *		<li>Using lookup table - lookup table is created for e.g. each degree
 *			and method returns aprox. values. It is very fast, but consumes
 *			memory. Precision can be improved by increasing the numbers of
 *			lookup table elements. Also, consider minimizing the table using
 *			only 0-90 degrees.
 *		</li>
 * </ul>
 * <pre>

 # VM invoker: /Library/Java/JavaVirtualMachines/jdk1.8.0_20.jdk/Contents/Home/jre/bin/java
 # VM options: -Dfile.encoding=UTF-8 -Duser.country=US -Duser.language=en -Duser.variant

 Benchmark                             Mode  Samples      Score     Error   Units
 c.o.j.m.SineBenchmark.lookupTable    thrpt       20  71645.755 ± 723.554  ops/ms
 c.o.j.m.SineBenchmark.math           thrpt       20   5236.416 ± 662.827  ops/ms

 * </pre>
 * <p>
 * Conclusion: lookup table is much faster, but less precise.
 */
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 10, time = 1, timeUnit = TimeUnit.MILLISECONDS)
@Threads(2)
@Fork(2)
@State(Scope.Thread)
@SuppressWarnings("unused")
public class SineBenchmark {

	private SineLookup sineLookup;
	private double[] inputs = new double[] {
			0.0, 4.5, 15.3, 24.6, 45.7, 55.0, 61.8, 84.4, 92.04, 100.23
	};

	@Setup
	public void setup() {
		sineLookup = new SineLookup();
	}

	@Benchmark
	public double math() {
		double sum = 0;

		for (double input : inputs) {
			sum += Math.sin(Math.toRadians(input));
		}

		return sum;
	}

	@Benchmark
	public double lookupTable() {
		double sum = 0;

		for (double input : inputs) {
			sum += sineLookup.sin(input);
		}

		return sum;
	}

}