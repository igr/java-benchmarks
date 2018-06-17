package com.oblac.jmh.reflect;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;

/**
 *
 Benchmark                                                  Mode  Cnt   Score   Error  Units

 b.FieldBenchmark.normal                                    avgt   20   2,558 ± 0,031  ns/op
 b.FieldBenchmark.primitive                                 avgt   20   2,116 ± 0,092  ns/op
 b.FieldBenchmark.privateNormal                             avgt   20   2,514 ± 0,016  ns/op

 b.FieldBenchmark.reflection                                avgt   20   5,855 ± 0,164  ns/op
 b.FieldBenchmark.reflectionAccessible                      avgt   20   5,515 ± 0,013  ns/op
 b.FieldBenchmark.reflectionAccessiblePrimitive             avgt   20   5,742 ± 0,033  ns/op
 b.FieldBenchmark.reflectionAccessiblePrivate               avgt   20   5,484 ± 0,089  ns/op
 b.FieldBenchmark.reflectionAccessibleSpecializedPrimitive  avgt   20   5,850 ± 0,050  ns/op
 b.FieldBenchmark.reflectionPrimitive                       avgt   20   5,783 ± 0,131  ns/op
 b.FieldBenchmark.reflectionSpecializedPrimitive            avgt   20   5,765 ± 0,030  ns/op

 b.FieldBenchmark.handle                                    avgt   20   6,367 ± 0,167  ns/op
 b.FieldBenchmark.handleExact                               avgt   20   7,154 ± 0,128  ns/op
 b.FieldBenchmark.handleInline                              avgt   20   5,289 ± 0,143  ns/op
 b.FieldBenchmark.handleExactInline                         avgt   20   2,523 ± 0,015  ns/op

 b.FieldBenchmark.handlePrimitive                           avgt   20   5,545 ± 0,061  ns/op
 b.FieldBenchmark.handleExactPrimitive                      avgt   20   5,558 ± 0,043  ns/op
 b.FieldBenchmark.handlePrimitiveInline                     avgt   20   2,043 ± 0,055  ns/op
 b.FieldBenchmark.handleExactPrimitiveInline                avgt   20   2,023 ± 0,029  ns/op

 b.FieldBenchmark.handleUnreflected                         avgt   20   6,151 ± 0,040  ns/op
 b.FieldBenchmark.handleUnreflectedExact                    avgt   20   7,097 ± 0,025  ns/op
 b.FieldBenchmark.handleUnreflectedInline                   avgt   20   5,314 ± 0,067  ns/op
 b.FieldBenchmark.handleUnreflectedExactInline              avgt   20   2,513 ± 0,005  ns/op

 b.FieldBenchmark.handleUnreflectedPrimitive                avgt   20   5,643 ± 0,047  ns/op
 b.FieldBenchmark.handleUnreflectedExactPrimitive           avgt   20   5,544 ± 0,035  ns/op
 b.FieldBenchmark.handleUnreflectedPrimitiveInline          avgt   20   2,047 ± 0,026  ns/op
 b.FieldBenchmark.handleUnreflectedExactPrimitiveInline     avgt   20   2,013 ± 0,015  ns/op

 b.FieldBenchmark.handleUnreflectedPrivate                  avgt   20   7,249 ± 0,053  ns/op
 b.FieldBenchmark.handleUnreflectedPrivateInline            avgt   20   2,558 ± 0,032  ns/op
 */
@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class FieldBenchmark {

	private String value = "foo";

	private int primitiveValue = 42;

	enum Access {

		INSTANCE;

		private String value = "bar";
	}

	private Field reflective, reflectiveAccessible, reflectivePrimitive, reflectiveAccessiblePrimitive, reflectiveAccessiblePrivate;

	private MethodHandle methodHandle, methodHandleUnreflected, methodHandlePrimitive, methodHandleUnreflectedPrimitive, methodHandleUnreflectedPrivate;

	private static final MethodHandle METHOD_HANDLE_INLINE, METHOD_HANDLE_UNREFLECTED_INLINE, METHOD_HANDLE_PRIMITIVE_INLINE, METHOD_HANDLE_UNREFLECTED_PRIMITIVE, METHOD_HANDLE_UNREFLECTED_PRIVATE;

	static {
		try {

			METHOD_HANDLE_INLINE = MethodHandles.lookup().findGetter(FieldBenchmark.class, "value", String.class);
			METHOD_HANDLE_UNREFLECTED_INLINE = MethodHandles.lookup().unreflectGetter(FieldBenchmark.class.getDeclaredField("value"));
			METHOD_HANDLE_PRIMITIVE_INLINE = MethodHandles.lookup().findGetter(FieldBenchmark.class, "primitiveValue", int.class);
			METHOD_HANDLE_UNREFLECTED_PRIMITIVE = MethodHandles.lookup().unreflectGetter(FieldBenchmark.class.getDeclaredField("primitiveValue"));
			Field reflectiveAccessiblePrivate = Access.class.getDeclaredField("value");
			reflectiveAccessiblePrivate.setAccessible(true);
			METHOD_HANDLE_UNREFLECTED_PRIVATE = MethodHandles.lookup().unreflectGetter(reflectiveAccessiblePrivate);
		} catch (Exception e) {
			throw new AssertionError();
		}
	}

	@Setup
	public void setup() throws Exception {
		reflective = FieldBenchmark.class.getDeclaredField("value");
		reflectiveAccessible = FieldBenchmark.class.getDeclaredField("value");
		reflectiveAccessible.setAccessible(true);
		reflectivePrimitive = FieldBenchmark.class.getDeclaredField("primitiveValue");
		reflectiveAccessiblePrimitive = FieldBenchmark.class.getDeclaredField("primitiveValue");
		reflectiveAccessiblePrimitive.setAccessible(true);
		methodHandle = MethodHandles.lookup().findGetter(FieldBenchmark.class, "value", String.class);
		methodHandleUnreflected = MethodHandles.lookup().unreflectGetter(reflective);
		methodHandlePrimitive = MethodHandles.lookup().findGetter(FieldBenchmark.class, "primitiveValue", int.class);
		methodHandleUnreflectedPrimitive = MethodHandles.lookup().unreflectGetter(reflectivePrimitive);
		reflectiveAccessiblePrivate = Access.class.getDeclaredField("value");
		reflectiveAccessiblePrivate.setAccessible(true);
		methodHandleUnreflectedPrivate = MethodHandles.lookup().unreflectGetter(reflectiveAccessiblePrivate);
	}

	@Benchmark
	public Object normal() {
		return value;
	}

	@Benchmark
	public Object reflection() throws IllegalAccessException {
		return reflective.get(this);
	}

	@Benchmark
	public Object reflectionAccessible() throws IllegalAccessException {
		return reflectiveAccessible.get(this);
	}

	@Benchmark
	public Object handle() throws Throwable {
		return methodHandle.invoke(this);
	}

	@Benchmark
	public Object handleExact() throws Throwable {
		return (String) methodHandle.invokeExact(this);
	}

	@Benchmark
	public Object handleUnreflected() throws Throwable {
		return methodHandleUnreflected.invoke(this);
	}

	@Benchmark
	public Object handleUnreflectedExact() throws Throwable {
		return (String) methodHandleUnreflected.invokeExact(this);
	}

	@Benchmark
	public int primitive() {
		return primitiveValue;
	}

	@Benchmark
	public int reflectionPrimitive() throws IllegalAccessException {
		return (int) reflectivePrimitive.get(this);
	}

	@Benchmark
	public int reflectionAccessiblePrimitive() throws IllegalAccessException {
		return (int) reflectiveAccessiblePrimitive.get(this);
	}

	@Benchmark
	public int reflectionSpecializedPrimitive() throws IllegalAccessException {
		return reflectivePrimitive.getInt(this);
	}

	@Benchmark
	public int reflectionAccessibleSpecializedPrimitive() throws IllegalAccessException {
		return reflectiveAccessiblePrimitive.getInt(this);
	}

	@Benchmark
	public int handlePrimitive() throws Throwable {
		return (int) methodHandlePrimitive.invoke(this);
	}

	@Benchmark
	public int handleExactPrimitive() throws Throwable {
		return (int) methodHandlePrimitive.invokeExact(this);
	}

	@Benchmark
	public int handleUnreflectedPrimitive() throws Throwable {
		return (int) methodHandleUnreflectedPrimitive.invoke(this);
	}

	@Benchmark
	public int handleUnreflectedExactPrimitive() throws Throwable {
		return (int) methodHandleUnreflectedPrimitive.invokeExact(this);
	}

	@Benchmark
	public String privateNormal() {
		return Access.INSTANCE.value; // accessor method
	}

	@Benchmark
	public Object reflectionAccessiblePrivate() throws Exception {
		return reflectiveAccessiblePrivate.get(Access.INSTANCE);
	}

	@Benchmark
	public String handleUnreflectedPrivate() throws Throwable {
		return (String) methodHandleUnreflectedPrivate.invokeExact((Access) Access.INSTANCE);
	}

	@Benchmark
	public Object handleInline() throws Throwable {
		return METHOD_HANDLE_INLINE.invoke(this);
	}

	@Benchmark
	public Object handleExactInline() throws Throwable {
		return (String) METHOD_HANDLE_INLINE.invokeExact(this);
	}

	@Benchmark
	public Object handleUnreflectedInline() throws Throwable {
		return METHOD_HANDLE_UNREFLECTED_INLINE.invoke(this);
	}

	@Benchmark
	public Object handleUnreflectedExactInline() throws Throwable {
		return (String) METHOD_HANDLE_UNREFLECTED_INLINE.invokeExact(this);
	}

	@Benchmark
	public int handlePrimitiveInline() throws Throwable {
		return (int) METHOD_HANDLE_PRIMITIVE_INLINE.invoke(this);
	}

	@Benchmark
	public int handleExactPrimitiveInline() throws Throwable {
		return (int) METHOD_HANDLE_PRIMITIVE_INLINE.invokeExact(this);
	}

	@Benchmark
	public int handleUnreflectedPrimitiveInline() throws Throwable {
		return (int) METHOD_HANDLE_UNREFLECTED_PRIMITIVE.invoke(this);
	}

	@Benchmark
	public int handleUnreflectedExactPrimitiveInline() throws Throwable {
		return (int) METHOD_HANDLE_UNREFLECTED_PRIMITIVE.invokeExact(this);
	}

	@Benchmark
	public String handleUnreflectedPrivateInline() throws Throwable {
		return (String) METHOD_HANDLE_UNREFLECTED_PRIVATE.invokeExact((Access) Access.INSTANCE);
	}
}
