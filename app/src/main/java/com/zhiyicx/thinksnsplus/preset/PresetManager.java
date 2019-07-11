package com.zhiyicx.thinksnsplus.preset;

import com.zhiyicx.thinksnsplus.data.beans.GameInfoBean;

public class PresetManager {
    private static PresetManager instance;

    public GameInfoBean gameInfoBean;
    private PresetManager() {

    }

    public static PresetManager getInstance() {
        if (instance == null) {
            synchronized (PresetManager.class) {
                if (instance == null) {
                    instance = new PresetManager();
                }

            }
        }
        return instance;
    }


}
