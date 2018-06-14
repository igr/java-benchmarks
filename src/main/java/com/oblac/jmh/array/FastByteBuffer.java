package com.oblac.jmh.array;

// Copyright (c) 2003-present, Jodd Team (http://jodd.org)
// All rights reserved.

import java.util.Arrays;

/**
 * Fast, fast <code>byte</code> buffer.
 * This buffer implementation does not store all data
 * in single array, but in array of chunks.
 */
public class FastByteBuffer {

	private byte[][] buffers = new byte[16][];
	private byte[] currentBuffer;
	private int currentBufferIndex;
	private int blength;
	private int offset;

	public FastByteBuffer() {
		currentBuffer = new byte[64];
		buffers[0] = currentBuffer;
	}

	/**
	 * Appends single <code>byte</code> to buffer.
	 */
	public FastByteBuffer append(final byte element) {
		if (offset == currentBuffer.length) {
			growFor(1);
		}

		currentBuffer[offset++] = element;

		return this;
	}

	private void growFor(final int delta) {
		int newBufferSize = currentBuffer.length << 1;

		if (newBufferSize < delta) {
			newBufferSize = delta + 512;
		}

		blength += currentBuffer.length;
		offset = 0;
		currentBuffer = new byte[newBufferSize];

		// add buffer
		currentBufferIndex++;
		if (currentBufferIndex >= buffers.length) {
			buffers = Arrays.copyOf(buffers, buffers.length << 1);
		}
		buffers[currentBufferIndex] = currentBuffer;
	}

	public byte[] toArray() {
		if (currentBufferIndex == 0) {
			return Arrays.copyOf(currentBuffer, offset);
		}

		int pos = 0;
		byte[] array = new byte[blength + offset];

		for (int i = 0; i < currentBufferIndex; i++) {
			int len = buffers[i].length;
			System.arraycopy(buffers[i], 0, array, pos, len);
			pos += len;
		}

		System.arraycopy(buffers[currentBufferIndex], 0, array, pos, offset);

		return array;
	}

}