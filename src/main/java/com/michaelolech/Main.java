package com.michaelolech;

import com.michaelolech.core.WindowManager;

import java.nio.*;

public class Main {

    public static void main(String[] args) {
        WindowManager window = new WindowManager(true, "Villager", 800, 600);

        window.init();

        while (!window.windowShouldClose()) {
            window.update();
        }

        window.cleanup();
    }
}