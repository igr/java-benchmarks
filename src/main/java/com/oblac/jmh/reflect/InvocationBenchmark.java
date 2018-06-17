package com.oblac.jmh.reflect;

import org.openjdk.jmh.annotations.*;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 *
As per: https://gist.github.com/raphw/881e1745996f9d314ab0

 b.InvocationBenchmark.normal                                 avgt   20  28,364 ± 1,378  ns/op
 b.InvocationBenchmark.primitive                              avgt   20   2,469 ± 0,011  ns/op
 b.InvocationBenchmark.privateNormal                          avgt   20  27,122 ± 0,158  ns/op

 b.InvocationBenchmark.reflection                             avgt   20  31,026 ± 0,213  ns/op
 b.InvocationBenchmark.reflectionPrimitive                    avgt   20  29,537 ± 2,152  ns/op
 b.InvocationBenchmark.reflectionAccessible                   avgt   20  30,420 ± 0,216  ns/op
 b.InvocationBenchmark.reflectionAccessiblePrimitive          avgt   20  28,582 ± 1,160  ns/op
 b.InvocationBenchmark.reflectionAccessiblePrivate            avgt   20  33,566 ± 3,674  ns/op

 b.InvocationBenchmark.handle                                 avgt   20  32,195 ± 2,448  ns/op
 b.InvocationBenchmark.handleExact                            avgt   20  36,736 ± 0,908  ns/op
 b.InvocationBenchmark.handleInline                           avgt   20  33,549 ± 3,397  ns/op
 b.InvocationBenchmark.handleExactInline                      avgt   20  27,241 ± 0,114  ns/op

 b.InvocationBenchmark.handlePrimitive                        avgt   20   8,525 ± 0,055  ns/op
 b.InvocationBenchmark.handlePrimitiveBoxed                   avgt   20  10,443 ± 0,370  ns/op
 b.InvocationBenchmark.handlePrimitiveExact                   avgt   20   8,237 ± 0,025  ns/op
 b.InvocationBenchmark.handlePrimitiveInline                  avgt   20   2,467 ± 0,006  ns/op
 b.InvocationBenchmark.handlePrimitiveBoxedInline             avgt   20   9,966 ± 0,036  ns/op
 b.InvocationBenchmark.handlePrimitiveExactInline             avgt   20   2,462 ± 0,007  ns/op

 b.InvocationBenchmark.handleUnreflectedExact                 avgt   20  30,176 ± 0,239  ns/op
 b.InvocationBenchmark.handleUnreflectedExactPrivate          avgt   20  29,882 ± 0,152  ns/op
 b.InvocationBenchmark.handleUnreflectedPrimitiveExact        avgt   20   8,765 ± 0,250  ns/op
 b.InvocationBenchmark.handleUnreflectedExactInline           avgt   20  28,594 ± 3,065  ns/op
 b.InvocationBenchmark.handleUnreflectedExactPrivateInline    avgt   20  39,853 ± 6,530  ns/op
 b.InvocationBenchmark.handleUnreflectedPrimitiveExactInline  avgt   20   2,539 ± 0,035  ns/op
 */
@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class InvocationBenchmark {

	private String s1 = "foo", s2 = "bar", s3 = "qux", s4 = "baz";

	private String method(String a, String b, String c, String d) {
		return a + b + c + d;
	}

	private int i1 = 1, i2 = 2, i3 = 3, i4 = 4;

	private int methodPrimitive(int a, int b, int c, int d) {
		return a + b + c + d;
	}

	enum Access {

		INSTANCE;

		private String method(String a, String b, String c, String d) {
			return a + b + c + d;
		}
	}

	private Method method, methodAccessible, methodPrimitive, methodAccessiblePrimitive, methodAccessiblePrivate;

	private MethodHandle methodHandle, methodHandleUnreflected, methodHandlePrimitive, methodHandleUnreflectedPrimitive, methodHandleUnreflectedPrivate;

	private static final MethodHandle METHOD_HANDLE_INLINE, METHOD_HANDLE_UNREFLECTED_INLINE, METHOD_HANDLE_PRIMITIVE_INLINE, METHOD_HANDLE_UNREFLECTED_PRIMITIVE_INLINE, METHOD_HANDLE_UNREFLECTED_PRIVATE_INLINE;

	static {
		try {
			Method methodAccessible = InvocationBenchmark.class.getDeclaredMethod("method", String.class, String.class, String.class, String.class);
			methodAccessible.setAccessible(true);
			METHOD_HANDLE_INLINE = MethodHandles.lookup().findVirtual(InvocationBenchmark.class, "method",
				MethodType.methodType(String.class, String.class, String.class, String.class, String.class));
			METHOD_HANDLE_UNREFLECTED_INLINE = MethodHandles.lookup().unreflect(methodAccessible);
			Method methodAccessiblePrimitive = InvocationBenchmark.class.getDeclaredMethod("methodPrimitive", int.class, int.class, int.class, int.class);
			methodAccessiblePrimitive.setAccessible(true);
			METHOD_HANDLE_PRIMITIVE_INLINE = MethodHandles.lookup().findVirtual(InvocationBenchmark.class, "methodPrimitive",
				MethodType.methodType(int.class, int.class, int.class, int.class, int.class));
			METHOD_HANDLE_UNREFLECTED_PRIMITIVE_INLINE = MethodHandles.lookup().unreflect(methodAccessiblePrimitive);
			Method methodAccessiblePrivate = Access.class.getDeclaredMethod("method", String.class, String.class, String.class, String.class);
			methodAccessiblePrivate.setAccessible(true);
			METHOD_HANDLE_UNREFLECTED_PRIVATE_INLINE = MethodHandles.lookup().unreflect(methodAccessiblePrivate);
		} catch (Exception e) {
			throw new AssertionError();
		}
	}

	@Setup
	public void setUp() throws Exception {
		method = InvocationBenchmark.class.getDeclaredMethod("method", String.class, String.class, String.class, String.class);
		methodAccessible = InvocationBenchmark.class.getDeclaredMethod("method", String.class, String.class, String.class, String.class);
		methodAccessible.setAccessible(true);
		methodHandle = MethodHandles.lookup().findVirtual(InvocationBenchmark.class, "method",
			MethodType.methodType(String.class, String.class, String.class, String.class, String.class));
		methodHandleUnreflected = MethodHandles.lookup().unreflect(methodAccessible);
		methodPrimitive = InvocationBenchmark.class.getDeclaredMethod("methodPrimitive", int.class, int.class, int.class, int.class);
		methodAccessiblePrimitive = InvocationBenchmark.class.getDeclaredMethod("methodPrimitive", int.class, int.class, int.class, int.class);
		methodAccessiblePrimitive.setAccessible(true);
		methodHandlePrimitive = MethodHandles.lookup().findVirtual(InvocationBenchmark.class, "methodPrimitive",
			MethodType.methodType(int.class, int.class, int.class, int.class, int.class));
		methodHandleUnreflectedPrimitive = MethodHandles.lookup().unreflect(methodAccessiblePrimitive);
		methodAccessiblePrivate = Access.class.getDeclaredMethod("method", String.class, String.class, String.class, String.class);
		methodAccessiblePrivate.setAccessible(true);
		methodHandleUnreflectedPrivate = MethodHandles.lookup().unreflect(methodAccessiblePrivate);
	}

	@Benchmark
	public Object normal() throws Exception {
		return method(s1, s2, s3, s4);
	}

	@Benchmark
	public Object reflection() throws Exception {
		return method.invoke(this, s1, s2, s3, s4);
	}

	@Benchmark
	public Object reflectionAccessible() throws Exception {
		return methodAccessible.invoke(this, s1, s2, s3, s4);
	}

	@Benchmark
	public Object handle() throws Throwable {
		return methodHandle.invoke(this, s1, s2, s3, s4);
	}

	@Benchmark
	public Object handleExact() throws Throwable {
		return (String) methodHandle.invokeExact(this, s1, s2, s3, s4);
	}

	@Benchmark
	public Object handleUnreflectedExact() throws Throwable {
		return (String) methodHandleUnreflected.invokeExact(this, s1, s2, s3, s4);
	}

	@Benchmark
	public int primitive() {
		return methodPrimitive(i1, i2, i3, i4);
	}

	@Benchmark
	public int reflectionPrimitive() throws Throwable {
		return (int) methodPrimitive.invoke(this, i1, i2, i3, i4);
	}

	@Benchmark
	public int reflectionAccessiblePrimitive() throws Throwable {
		return (int) methodAccessiblePrimitive.invoke(this, i1, i2, i3, i4);
	}

	@Benchmark
	public int handlePrimitive() throws Throwable {
		return (int) methodHandlePrimitive.invoke(this, i1, i2, i3, i4);
	}

	@Benchmark
	public int handlePrimitiveBoxed() throws Throwable {
		return (Integer) methodHandlePrimitive.invoke(this, Integer.valueOf(i1), Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4));
	}

	@Benchmark
	public int handlePrimitiveExact() throws Throwable {
		return (int) methodHandlePrimitive.invokeExact(this, i1, i2, i3, i4);
	}

	@Benchmark
	public Object handleUnreflectedPrimitiveExact() throws Throwable {
		return (int) methodHandleUnreflectedPrimitive.invokeExact(this, i1, i2, i3, i4);
	}

	@Benchmark
	public Object privateNormal() throws Exception {
		return Access.INSTANCE.method(s1, s2, s3, s4); // accessor method indirection
	}

	@Benchmark
	public Object reflectionAccessiblePrivate() throws Exception {
		return methodAccessiblePrivate.invoke(Access.INSTANCE, s1, s2, s3, s4);
	}

	@Benchmark
	public Object handleUnreflectedExactPrivate() throws Throwable {
		return (String) methodHandleUnreflectedPrivate.invokeExact(Access.INSTANCE, s1, s2, s3, s4);
	}

	@Benchmark
	public Object handleInline() throws Throwable {
		return METHOD_HANDLE_INLINE.invoke(this, s1, s2, s3, s4);
	}

	@Benchmark
	public Object handleExactInline() throws Throwable {
		return (String) METHOD_HANDLE_INLINE.invokeExact(this, s1, s2, s3, s4);
	}

	@Benchmark
	public Object handleUnreflectedExactInline() throws Throwable {
		return (String) METHOD_HANDLE_UNREFLECTED_INLINE.invokeExact(this, s1, s2, s3, s4);
	}

	@Benchmark
	public int handlePrimitiveInline() throws Throwable {
		return (int) METHOD_HANDLE_PRIMITIVE_INLINE.invoke(this, i1, i2, i3, i4);
	}

	@Benchmark
	public int handlePrimitiveBoxedInline() throws Throwable {
		return (Integer) METHOD_HANDLE_PRIMITIVE_INLINE.invoke(this, Integer.valueOf(i1), Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4));
	}

	@Benchmark
	public int handlePrimitiveExactInline() throws Throwable {
		return (int) METHOD_HANDLE_UNREFLECTED_PRIMITIVE_INLINE.invokeExact(this, i1, i2, i3, i4);
	}

	@Benchmark
	public int handleUnreflectedPrimitiveExactInline() throws Throwable {
		return (int) METHOD_HANDLE_UNREFLECTED_PRIMITIVE_INLINE.invokeExact(this, i1, i2, i3, i4);
	}

	@Benchmark
	public Object handleUnreflectedExactPrivateInline() throws Throwable {
		return (String) METHOD_HANDLE_UNREFLECTED_PRIVATE_INLINE.invokeExact(Access.INSTANCE, s1, s2, s3, s4);
	}
}
