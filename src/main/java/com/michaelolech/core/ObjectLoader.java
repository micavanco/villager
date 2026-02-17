package com.michaelolech.core;

import com.michaelolech.core.entity.Model;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

public class ObjectLoader {
    private List<Integer> vertexArrayObjects = new ArrayList<>();
    private List<Integer> vertexBufferObjects = new ArrayList<>();

    public Model loadObject(float[] vertices, int[] indices) {
        int id = createVertexArrayObject();

        storeIndicesBuffer(indices);
        storeDataInAttributeList(0, 3, vertices);
        unbind();

        return new Model(id, vertices.length / 3);
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
    }
}
