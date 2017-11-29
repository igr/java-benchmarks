package com.oblac.jmh.functional;

import jodd.mutable.MutableBoolean;
import jodd.mutable.MutableInteger;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 *
 Benchmark                Mode  Cnt   Score   Error  Units
 IfVsFnBenchmark.fnblock  avgt  100  15.114 ± 0.071  ns/op
 IfVsFnBenchmark.ifblock  avgt  100  11.143 ± 0.038  ns/op
 */
@Fork(5)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 20, time = 1, timeUnit = TimeUnit.SECONDS)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Benchmark)
public class IfVsFnBenchmark {

	public Random rnd = new Random(1234567890);

	@Benchmark
	public int ifblock() {
		if (rnd.nextInt() > 0) {
			return 1;
		}
		else {
			return 0;
		}
	}

	@Benchmark
	public int fnblock() {
		MutableInteger mi = new MutableInteger();

		If.inCase(rnd.nextInt() > 0)
			.then(() -> mi.value = 1)
			.otherwise(() -> mi.value = 0);

		return mi.value;
	}


}
