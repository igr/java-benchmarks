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
 * Benchmark                      (size)  Mode  Cnt      Score     Error  Units
 * IsoEncodeBenchmark.withClass       32  avgt   25     87.113 ±   2.353  ns/op
 * IsoEncodeBenchmark.withClass      128  avgt   25    188.925 ±   1.020  ns/op
 * IsoEncodeBenchmark.withClass     1024  avgt   25   1553.195 ±   8.505  ns/op
 * IsoEncodeBenchmark.withClass    32768  avgt   25  48765.192 ± 576.917  ns/op
 * IsoEncodeBenchmark.withMethod      32  avgt   25     88.350 ±   3.025  ns/op
 * IsoEncodeBenchmark.withMethod     128  avgt   25    189.265 ±   2.427  ns/op
 * IsoEncodeBenchmark.withMethod    1024  avgt   25   1571.427 ±  23.476  ns/op
 * IsoEncodeBenchmark.withMethod   32768  avgt   25  48565.216 ± 198.854  ns/op
 */
@Fork(5)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Benchmark)
public class IsoEncodeBenchmark {

	private static String encodeToIso88591(String str) {
		final StringBuilder encodedStr = new StringBuilder();

		for (char c : str.toCharArray()) {
			if (c > 255) {
				throw new IllegalArgumentException("Character " + c + " cannot be encoded in ISO-8859-1");
			}

			encodedStr.append(c);
		}
		return encodedStr.toString();
	}

	private static String decodeFromIso88591(String encodedStr) {
		final StringBuilder decodedStr = new StringBuilder();

		for (char c : encodedStr.toCharArray()) {
			decodedStr.append(c);
		}

		return decodedStr.toString();
	}

	public static class Iso88591String {

		private final String str;

		public Iso88591String(String str) {
			this.str = str;
		}

		@Override
		public String toString() {
			final StringBuilder encodedStr = new StringBuilder();

			for (char c : str.toCharArray()) {
				if (c > 255) {
					throw new IllegalArgumentException("Character " + c + " cannot be encoded in ISO-8859-1");
				}

				encodedStr.append(c); // Add the character to the result string
			}

			return encodedStr.toString();
		}

		public String getDecoded() {
			final StringBuilder decodedStr = new StringBuilder();

			for (char c : str.toCharArray()) {
				decodedStr.append(c); // Add the character to the result string
			}

			return decodedStr.toString();
		}

	}

	// benchmarks

	@Param({"32", "128", "1024", "32768" })
	int size;

	String value;
	int from, to;

	@Setup
	public void prepare() {
		value = new RandomString().randomAlphaNumeric(size);
	}

	@Benchmark
	public String withMethod() {
		return encodeToIso88591(value);
	}

	@Benchmark
	public String withClass() {
		return new Iso88591String(value).toString();
	}

}
