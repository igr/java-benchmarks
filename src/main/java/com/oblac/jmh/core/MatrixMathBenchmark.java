package com.oblac.jmh.core;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;

import java.util.concurrent.TimeUnit;

/**
 * Java 2D arrays perform better with <em>row-major</em> access patterns. It matters!
 * Benchmark                   (size)   Mode  Cnt      Score     Error  Units
 * MatrixMathPerf.baseline        256  thrpt   50  18817.379 ± 268.756  ops/s
 * MatrixMathPerf.baseline       1024  thrpt   50   1076.577 ±  12.828  ops/s
 * MatrixMathPerf.baseline      20480  thrpt   50      3.171 ±   0.045  ops/s
 * MatrixMathPerf.columnMajor     256  thrpt   50  30938.842 ± 294.760  ops/s
 * MatrixMathPerf.columnMajor    1024  thrpt   50    164.639 ±   4.610  ops/s
 * MatrixMathPerf.columnMajor   20480  thrpt   50      0.090 ±   0.002  ops/s
 * MatrixMathPerf.rowMajor        256  thrpt   50  35414.640 ± 446.914  ops/s
 * MatrixMathPerf.rowMajor       1024  thrpt   50   1851.282 ±  20.052  ops/s
 * MatrixMathPerf.rowMajor      20480  thrpt   50      5.295 ±   0.072  ops/s
 */
@State(Scope.Thread)
@OutputTimeUnit(TimeUnit.SECONDS)
@Fork(jvmArgsPrepend = {"-Xmx8g", "-XX:+UseG1GC"})
@Threads(1)
public class MatrixMathBenchmark {

	@Param({"256", "1024", "20480"})
	private int size;

	private MatrixMath mm;

	@Setup
	public void setUp() {
		mm = new MatrixMath(size);
	}

	@Benchmark
	public double baseline() {
		return mm.sumUpperTriangle();
	}

	@Benchmark
	public double columnMajor() {
		return mm.sumUpperTriangleCMA();
	}

	@Benchmark
	public double rowMajor() {
		return mm.sumUpperTriangleRMA();
	}
}

