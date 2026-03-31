package com.example.bulletgame;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;

public interface Bullets {
    public Entity fireBullet();

    @Spawns("Bullet")
    Entity fireBullet(SpawnData data);
}
