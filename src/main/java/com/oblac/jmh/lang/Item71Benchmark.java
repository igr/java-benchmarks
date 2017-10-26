package com.oblac.jmh.lang;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

import java.util.concurrent.TimeUnit;

/**
 * In Effective Java 2nd edition there is the following note in Item #71 regarding of storing volatile in local first:
 *
 * "On my machine, the method above is about 25 percent faster then the obvious version without a local variable"
 *
 * This sounds unrealistic. And it is:
 *
 * (not commented)
 * Item71Benchmark.withLocalVariable     avgt   25  33.173 ± 1.429  ns/op
 * Item71Benchmark.withoutLocalVariable  avgt   25  31.357 ± 0.442  ns/op
 *
 * (commented)
 * Benchmark                             Mode  Cnt  Score   Error  Units
 * Item71Benchmark.withLocalVariable     avgt   25  2.902 ± 0.021  ns/op
 * Item71Benchmark.withoutLocalVariable  avgt   25  2.905 ± 0.046  ns/op
 */
@Fork(5)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Benchmark)
public class Item71Benchmark {

	private volatile Object value;

	@Benchmark
	public Object withLocalVariable() {
		//value = null;

		Object local = value;
		if (local == null) {
			synchronized (this) {
				local = value;
				if (local == null) {
					local = value = new Object();
				}
			}
		}
		return local;
	}

	@Benchmark
	public Object withoutLocalVariable() {
		//value = null;

		if (value == null) {
			synchronized (this) {
				if (value == null) {
					value = new Object();
				}
			}
		}
		return value;
	}

}
