package com.michaelolech;

import com.michaelolech.core.EngineManager;
import com.michaelolech.core.WindowManager;
import com.michaelolech.utils.Constant;

public class Launcher {
    private static WindowManager window;
    private static EngineManager engine;

    public static void main(String[] args) {
        window = new WindowManager(true, Constant.TITLE, 800, 600);
        engine = new EngineManager();

        try {
            engine.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static WindowManager getWindow() {
        return window;
    }
}