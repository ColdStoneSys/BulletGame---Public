package com.example.bulletgame;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

import static com.almasb.fxgl.dsl.FXGL.entityBuilder;

public class PlayerFactory implements EntityFactory {
    @Spawns ("Player")
    public Entity spawnPlayer(SpawnData data){
        return entityBuilder()
                .type(EntityType.PLAYER)
                .at(400,400)
                .zIndex(1)
                .viewWithBBox("ball.png")
                .with(new CollidableComponent(true))
                .buildAndAttach();
    }

    @Spawns("PlayerBullet")
    public Entity fireBullet(SpawnData data) {
        return entityBuilder(data)
                .type(EntityType.ENEMY)
                .viewWithBBox(new Circle(15, Color.BLUE))
                .with(new CollidableComponent(true))
                .at(500, 625)
                .buildAndAttach();
    }

}
