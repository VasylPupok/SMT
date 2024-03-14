package main;

import javax.sound.sampled.*;

public interface LevelScene {
    Clip getClip();
    LevelButton getLevelButton();
}
