package com.michaelolech.core;

import com.michaelolech.core.entity.Model;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

public class ObjectLoader {
    private List<Integer> vertexArrayObjects = new ArrayList<>();
    private List<Integer> vertexBufferObjects = new ArrayList<>();
    private List<Integer> textures = new ArrayList<>();

    public Model loadObject(float[] vertices, float[] textureCoordinates, int[] indices) {
        int id = createVertexArrayObject();

        storeIndicesBuffer(indices);
        storeDataInAttributeList(0, 3, vertices);
        storeDataInAttributeList(1, 2, textureCoordinates);
        unbind();

        return new Model(id, indices.length);
    }

    public int loadTexture(String filename) throws Exception {
        int width, height;
        ByteBuffer buffer;

        try(MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer bufferWidth = stack.mallocInt(1);
            IntBuffer bufferHeight = stack.mallocInt(1);
            IntBuffer numberOfChannels = stack.mallocInt(1);

            buffer = STBImage.stbi_load(filename, bufferWidth, bufferHeight, numberOfChannels, 4);

            if (buffer == null) {
                throw new Exception("Failed to load texture file: " + filename + " STBI reason: " + STBImage.stbi_failure_reason());
            }

            width = bufferWidth.get();
            height = bufferHeight.get();
        }

        int id = GL11.glGenTextures();
        textures.add(id);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
        GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
        GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
        STBImage.stbi_image_free(buffer);

        return id;
    }

    private int createVertexArrayObject() {
        int id = GL30.glGenVertexArrays();

        vertexArrayObjects.add(id);
        GL30.glBindVertexArray(id);

        return id;
    }

    private void storeIndicesBuffer(int[] indices) {
        int vectorBufferObject = GL15.glGenBuffers();

        vertexBufferObjects.add(vectorBufferObject);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vectorBufferObject);

        IntBuffer buffer = Utils.storeDataInIntBuffer(indices);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
    }

    private void storeDataInAttributeList(int attributeNumber, int vertexCount, float[] data) {
        int vertexBufferObject = GL15.glGenBuffers();

        vertexBufferObjects.add(vertexBufferObject);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vertexBufferObject);

        FloatBuffer buffer = Utils.storeDataInFloatBuffer(data);

        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(attributeNumber, vertexCount, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }

    private void unbind() {
        GL30.glBindVertexArray(0);
    }

    public void cleanup() {
        for(int vertex : vertexArrayObjects) {
            GL30.glDeleteVertexArrays(vertex);
        }
        for(int vertex : vertexBufferObjects) {
            GL30.glDeleteBuffers(vertex);
        }
        for(int texture : textures) {
            GL30.glDeleteTextures(texture);
        }
    }
}
