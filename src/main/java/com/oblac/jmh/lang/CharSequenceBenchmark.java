package com.oblac.jmh.lang;

import jodd.util.RandomString;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

import java.nio.CharBuffer;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
@State(Scope.Thread)
public class CharSequenceBenchmark {

	@Param({"32", "128", "1024", "32768" })
	int size;

	char[] chars;
	int from, to;

	@Setup
	public void prepare() {
		chars = new RandomString().randomAlphaNumeric(size).toCharArray();
		from = (int) (chars.length * 0.1);
		to = (int) (chars.length * 0.9);
	}

	@Benchmark
	public CharSequence createCharBuffer() {
		return CharBuffer.wrap(chars, from, to);
	}

	@Benchmark
	public CharSequence createString() {
		return new String(chars, from, to);
	}

}
