package com.oblac.jmh.array;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

import java.io.ByteArrayOutputStream;

@Fork(1)
@Warmup(iterations = 5)
@Measurement(iterations = 3)
@State(Scope.Benchmark)
public class FastByteBufferVsBAOSBenchmark {

//	@Param({"1", "33", "64", "65", "193", "500", "1000"})
	@Param({"65"})
	public int size;

	@Benchmark
	public byte[] fastBuffer() {
		final FastByteBuffer fastBuffer = new FastByteBuffer();
		for (int i = 0; i < size; i++) {
			fastBuffer.append((byte) i);
		}
		return fastBuffer.toArray();
	}

	@Benchmark
	public byte[] outputStream() {
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		for (int i = 0; i < size; i++) {
			baos.write(i);
		}
		return baos.toByteArray();
	}
}