package com.example.bulletgame;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.core.collection.PropertyMap;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.texture.Texture;
import com.almasb.fxgl.time.TimerAction;
import static com.almasb.fxgl.dsl.FXGL.*;

import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.Map;
import java.util.Random;


public class BasicGameApp extends GameApplication {
    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(800);
        settings.setHeight(800);
        settings.setTitle("Basic Game App");
        settings.setVersion("0.1");
    }

    private Entity BG;
    private Entity player;

    @Override
    protected void initGame(){

        getGameWorld().addEntityFactory(new EnemyFactory());
        getGameWorld().addEntityFactory(new PlayerFactory());
        getGameWorld().addEntityFactory(new EnvironmentFactory());

        // create a simple BG layer, nothing should spawn before this ever.
        // can be replaced with an image later.
         BG = FXGL.entityBuilder()
                .at(0, 0)
                .zIndex(0)
                .view("VolcanoEnv_NoLava.png")
                .buildAndAttach();


        spawn("Obstacle");


        player = spawn("Player");

        spawn("Enemy"); // debug only

        spawn("Boss"); // debug only

        spawn("Bullet"); //debug only

        TimerAction timerAction = FXGL.getGameTimer().runAtInterval(
                this::generateCoin
                ,Duration.seconds(5)
        );

    }


    @Override
    protected void initPhysics() {
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.PLAYER, EntityType.COIN) {

            // order of types is the same as passed into the constructor
            @Override
            protected void onCollisionBegin(Entity player, Entity coin) {
                coin.removeFromWorld();
                FXGL.inc("coinsCollected", +1);
            }
        });

        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.PLAYER, EntityType.ENEMY) {

            @Override
            protected void onCollisionBegin(Entity player, Entity enemy){
                PropertyMap playerProperties = FXGL.getWorldProperties();

                if(playerProperties.getInt("health") > 1){
                    FXGL.inc("health", -1);
                    System.out.println("Ouch!");
                } else {
                    System.out.println("You died!!");
                    System.exit(0);
                    // You died, you lose, game over
                }

            }

        });
    }

    private void initBackground(){
        getGameScene().setBackgroundRepeat("VolcanoEnv_NoLava.png");
    }


    @Override
    protected void initInput(){
        Input input = FXGL.getInput();

        FXGL.onKey(KeyCode.W, () -> {
            player.translateY(-5);
            FXGL.inc("pixelsMoved", +5);
        });
        FXGL.onKey(KeyCode.A, () -> {
            player.translateX(-5);
            FXGL.inc("pixelsMoved", +5);
        });
        FXGL.onKey(KeyCode.S, () -> {
            player.translateY(5);
            FXGL.inc("pixelsMoved", +5);
        });
        FXGL.onKey(KeyCode.D, () -> {
            player.translateX(5);
            FXGL.inc("pixelsMoved", +5);
        });

        FXGL.onKey(KeyCode.SPACE, () ->{ // DEBUG ONLY
            BG = FXGL.entityBuilder()
                    .at(0, 0)
                    .zIndex(0)
                    .view("VolcanoEnv.png")
                    .buildAndAttach();
        });

    }

    @Override
    protected void initUI(){
        Text myText = new Text();
        myText.setTranslateX(50);
        myText.setTranslateY(50);

        Text numCoins = new Text();
        numCoins.setTranslateX(400);
        numCoins.setTranslateY(50);

        Text playerHealth = new Text();
        playerHealth.setTranslateX(50);
        playerHealth.setTranslateY(750);

        FXGL.getGameScene().addUINodes(numCoins, myText, playerHealth);

        myText.textProperty().bind(FXGL.getWorldProperties()
                .intProperty("pixelsMoved").asString());

        numCoins.textProperty().bind(FXGL.getWorldProperties()
                .intProperty("coinsCollected").asString());

        playerHealth.textProperty().bind(FXGL.getWorldProperties()
                .intProperty("health").asString());
    }

    @Override
    protected void initGameVars(Map<String, Object> vars){
        vars.put("pixelsMoved", 0);
        vars.put("coinsCollected",0);
        vars.put("health", 10);
    }

    protected void generateCoin(){
        Random rand = new Random();

        int randCoordinate1 =  rand.nextInt(795);
        int randCoordinate2 = rand.nextInt(795);

        Entity coin = FXGL.entityBuilder()
                .type(EntityType.COIN)
                .at(randCoordinate1, randCoordinate2)
                .viewWithBBox(new Circle(15, 15, 15, Color.YELLOW))
                .with(new CollidableComponent(true))
                .buildAndAttach();

        despawnWithDelay(coin, Duration.seconds(10));
    }



    public static void main(String[] args) {
        launch(args);
    }
}