package com.michaelolech;

import com.michaelolech.core.EngineManager;
import com.michaelolech.core.ILogic;
import com.michaelolech.core.WindowManager;
import com.michaelolech.utils.Constant;

public class Launcher {
    private static WindowManager window;
    private static EngineManager engine;
    private static TestGame testGame;

    public static void main(String[] args) {
        window = new WindowManager(true, Constant.TITLE, 800, 600);
        testGame = new TestGame();
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

    public static ILogic getGame() {
        return testGame;
    }
}