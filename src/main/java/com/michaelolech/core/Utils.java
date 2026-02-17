package com.michaelolech.core;

import org.lwjgl.system.MemoryUtil;

import java.io.InputStream;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Utils {
    public static FloatBuffer storeDataInFloatBuffer(float[] data) {
        FloatBuffer buffer = MemoryUtil.memAllocFloat(data.length);

        buffer.put(data).flip();

        return buffer;
    }

    public static IntBuffer storeDataInIntBuffer(int[] data) {
        IntBuffer buffer = MemoryUtil.memAllocInt(data.length);

        buffer.put(data).flip();

        return buffer;
    }

    public static String loadResource(String path) throws Exception {
        String result;

        try (
                InputStream inputStream = Utils.class.getResourceAsStream(path);
                Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8);
        ) {
            result = scanner.useDelimiter("\\A").next();
        }

        return result;
    }
}
