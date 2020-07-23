package com.oblac.jmh.lang;

import jodd.util.RandomString;
import jodd.util.UnsafeUtil;
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
 ToCharArrayVsCharAtBenchmark.charAt                 16  avgt    5     7.834 ±  0.299  ns/op
 ToCharArrayVsCharAtBenchmark.charAt                 32  avgt    5    10.598 ±  0.610  ns/op
 ToCharArrayVsCharAtBenchmark.charAt                 64  avgt    5    16.851 ±  0.163  ns/op
 ToCharArrayVsCharAtBenchmark.charAt                128  avgt    5    31.583 ±  0.929  ns/op
 ToCharArrayVsCharAtBenchmark.charAt                256  avgt    5    63.483 ±  2.167  ns/op
 ToCharArrayVsCharAtBenchmark.charAt               1024  avgt    5   257.170 ±  8.375  ns/op
 ToCharArrayVsCharAtBenchmark.charAt               2048  avgt    5   510.402 ±  8.246  ns/op
 ToCharArrayVsCharAtBenchmark.charAt               4096  avgt    5  1017.116 ± 64.833  ns/op
 ToCharArrayVsCharAtBenchmark.charAt               8192  avgt    5  2018.497 ± 29.940  ns/op
 ToCharArrayVsCharAtBenchmark.toCharArray            16  avgt    5    11.488 ±  0.301  ns/op
 ToCharArrayVsCharAtBenchmark.toCharArray            32  avgt    5    14.586 ±  0.622  ns/op
 ToCharArrayVsCharAtBenchmark.toCharArray            64  avgt    5    22.502 ±  0.341  ns/op
 ToCharArrayVsCharAtBenchmark.toCharArray           128  avgt    5    37.742 ±  0.466  ns/op
 ToCharArrayVsCharAtBenchmark.toCharArray           256  avgt    5    74.798 ±  1.197  ns/op
 ToCharArrayVsCharAtBenchmark.toCharArray          1024  avgt    5   296.498 ±  8.705  ns/op
 ToCharArrayVsCharAtBenchmark.toCharArray          2048  avgt    5   657.694 ±  3.117  ns/op
 ToCharArrayVsCharAtBenchmark.toCharArray          4096  avgt    5  1466.806 ± 76.922  ns/op
 ToCharArrayVsCharAtBenchmark.toCharArray          8192  avgt    5  3105.703 ± 58.677  ns/op
 ToCharArrayVsCharAtBenchmark.toCharArrayUnsafe      16  avgt    5     8.296 ±  1.893  ns/op
 ToCharArrayVsCharAtBenchmark.toCharArrayUnsafe      32  avgt    5    13.021 ±  0.306  ns/op
 ToCharArrayVsCharAtBenchmark.toCharArrayUnsafe      64  avgt    5    22.685 ±  0.261  ns/op
 ToCharArrayVsCharAtBenchmark.toCharArrayUnsafe     128  avgt    5    29.575 ±  0.766  ns/op
 ToCharArrayVsCharAtBenchmark.toCharArrayUnsafe     256  avgt    5    61.107 ±  0.912  ns/op
 ToCharArrayVsCharAtBenchmark.toCharArrayUnsafe    1024  avgt    5   254.722 ±  4.684  ns/op
 ToCharArrayVsCharAtBenchmark.toCharArrayUnsafe    2048  avgt    5   540.702 ± 32.653  ns/op
 ToCharArrayVsCharAtBenchmark.toCharArrayUnsafe    4096  avgt    5  1091.839 ± 82.697  ns/op
 ToCharArrayVsCharAtBenchmark.toCharArrayUnsafe    8192  avgt    5  2020.387 ± 43.228  ns/op */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
@State(Scope.Thread)
public class ToCharArrayVsCharAtBenchmark {

	@Param({"16", "32", "64", "128", "256", "1024", "2048", "4096", "8192" })
	int size;

	String string;

	interface CharBuffer {
		char charAt();
		int length();
	}

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
	public int toCharArrayUnsafe() {
		char[] chars = UnsafeUtil.getChars(string);
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
