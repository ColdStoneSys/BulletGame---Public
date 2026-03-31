package com.example.bulletgame;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class EnvironmentFactory implements EntityFactory {

    @Spawns("Obstacle") // basic for debug purposes
    public Entity spawnEnemy(SpawnData data){
        return FXGL.entityBuilder(data)
                .type(EntityType.OBSTACLE)
                .viewWithBBox(new Rectangle(20,20, Color.AQUA))
                .with(new CollidableComponent(true))
                .zIndex(3) // Obstacles should be collidable and also on the highest z-layer
                .at(0,0)
                .buildAndAttach();
    }

    /**
     * Ideally we just create multiple spawners for each obstacle type when we load a level
     *
     * We would just have to modify or add more Entity types to handle different collision sizes
     * */


}
