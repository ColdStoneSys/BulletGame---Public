package com.example.bulletgame;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

import static com.almasb.fxgl.dsl.FXGL.*;

public class EnemyFactory implements EntityFactory{

    @Spawns("Enemy")
    public Entity spawnEnemy(SpawnData data){
        return entityBuilder(data)
                .type(EntityType.ENEMY)
                .viewWithBBox(new Rectangle(40,40, Color.RED))
                .with(new CollidableComponent(true))
                .zIndex(1)
                .at(200,200)
                .buildAndAttach();
    }

    @Spawns("Boss")
    public Entity spawnBoss(SpawnData data) {
        return entityBuilder(data)
                .type(EntityType.ENEMY)
                .viewWithBBox(new Rectangle(60, 60, Color.ORANGE))
                .with(new CollidableComponent(true))
                .zIndex(1)
                .at(600, 600)
                .buildAndAttach();
    }

    @Spawns("Bullet")
    public Entity fireBullet(SpawnData data) {
        return entityBuilder(data)
                .type(EntityType.ENEMY)
                .viewWithBBox(new Circle(15, Color.LIME))
                .with(new CollidableComponent(true))
                .zIndex(1)
                .at(500, 625)
                .buildAndAttach();
    }
}
