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

import java.util.concurrent.TimeUnit;

/**
 Benchmark                                 (size)  Mode  Cnt    Score    Error  Units
 ToCharArrayVsCharAtBenchmark.charAt           16  avgt    5    9.616 ±  0.805  ns/op
 ToCharArrayVsCharAtBenchmark.charAt           32  avgt    5   13.510 ±  0.342  ns/op
 ToCharArrayVsCharAtBenchmark.charAt           64  avgt    5   22.794 ±  0.509  ns/op
 ToCharArrayVsCharAtBenchmark.charAt          128  avgt    5   40.882 ±  1.121  ns/op
 ToCharArrayVsCharAtBenchmark.charAt          256  avgt    5   82.424 ±  3.868  ns/op
 ToCharArrayVsCharAtBenchmark.charAt         1024  avgt    5  327.584 ± 23.769  ns/op
 ToCharArrayVsCharAtBenchmark.charAt         2048  avgt    5  655.071 ± 46.908  ns/op
 ToCharArrayVsCharAtBenchmark.toCharArray      16  avgt    5   16.258 ±  0.983  ns/op
 ToCharArrayVsCharAtBenchmark.toCharArray      32  avgt    5   19.824 ±  0.762  ns/op
 ToCharArrayVsCharAtBenchmark.toCharArray      64  avgt    5   28.982 ±  2.473  ns/op
 ToCharArrayVsCharAtBenchmark.toCharArray     128  avgt    5   50.030 ±  2.205  ns/op
 ToCharArrayVsCharAtBenchmark.toCharArray     256  avgt    5   98.766 ±  8.456  ns/op
 ToCharArrayVsCharAtBenchmark.toCharArray    1024  avgt    5  398.665 ± 25.635  ns/op
 ToCharArrayVsCharAtBenchmark.toCharArray    2048  avgt    5  895.199 ± 58.095  ns/op
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
@State(Scope.Thread)
public class ToCharArrayVsCharAtBenchmark {

	@Param({"16", "32", "64", "128", "256", "1024", "2048" })
	int size;

	String string;

	@Setup
	public void prepare() {
		string = new RandomString().randomAlphaNumeric(size);
	}

	@Benchmark
	public int toCharArray() {
		char[] chars = string.toCharArray();
		int sum = 0;
		for (char aChar : chars) {
			sum += aChar;
		}
		return sum;
	}

	@Benchmark
	public int charAt() {
		int sum = 0;
		int length = string.length();
		for (int i = 0; i < length; i++) {
			sum += string.charAt(i);
		}
		return sum;
	}

}
