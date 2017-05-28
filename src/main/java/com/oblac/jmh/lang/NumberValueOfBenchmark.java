package com.oblac.jmh.lang;

import com.oblac.jmh.DataGenerator;
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
 * Measure difference between using <code>new</code> operator
 * for build new <code>Numbers</code> and using <code>valueOf</code>.
 * Some numbers (like <code>Integer</code>) have a cache of some numbers.
 * Some numbers (like <code>Double</code>) don't have it.
 * <pre>

 # VM invoker: /Library/Java/JavaVirtualMachines/jdk1.8.0_20.jdk/Contents/Home/jre/bin/java
 # VM options: -Dfile.encoding=UTF-8 -Duser.country=US -Duser.language=en -Duser.variant

 Benchmark                                              Mode  Samples     Score     Error   Units
 c.o.j.l.NumberValueOfBenchmark.newInstance            thrpt       20  6766.729 ±  62.546  ops/ms
 c.o.j.l.NumberValueOfBenchmark.newInstanceGaussian    thrpt       20  6740.192 ± 108.157  ops/ms
 c.o.j.l.NumberValueOfBenchmark.valueOf                thrpt       20   589.531 ±  26.161  ops/ms
 c.o.j.l.NumberValueOfBenchmark.valueOfGaussian        thrpt       20   593.561 ±  31.200  ops/ms

 * </pre>
 *
 * Conclusion: Don't use <code>new</code> for numbers, ever. Use <code>valueOf</code>.
 */
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 10, time = 1, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 10, time = 1, timeUnit = TimeUnit.MILLISECONDS)
@Threads(2)
@Fork(2)
@State(Scope.Thread)
@SuppressWarnings({"UnnecessaryBoxing", "unused"})
public class NumberValueOfBenchmark {

	private int[] arrayUniformDistribution;
	private int[] arrayNormalDistribution;

	@Setup
	public void setup() {
		arrayUniformDistribution = DataGenerator.arrayOfRandomInts(1000);
		arrayNormalDistribution = DataGenerator.arrayOfGaussiandRandomInts(1000);
	}

	@Benchmark
	public long newInstance() {
		long sum = 0;
		for (int i : arrayUniformDistribution) {
			Integer integer = new Integer(i);

			sum += integer.longValue();
		}
		return sum;
	}
	@Benchmark
	public long newInstanceGaussian() {
		long sum = 0;
		for (int i : arrayNormalDistribution) {
			Integer integer = new Integer(i);

			sum += integer.longValue();
		}
		return sum;
	}

	@Benchmark
	public long valueOf() {
		long sum = 0;
		for (int i : arrayUniformDistribution) {
			Integer integer = Integer.valueOf(i);

			sum += integer.longValue();
		}
		return sum;
	}

	@Benchmark
	public long valueOfGaussian() {
		long sum = 0;
		for (int i : arrayNormalDistribution) {
			Integer integer = Integer.valueOf(i);

			sum += integer.longValue();
		}
		return sum;
	}
}