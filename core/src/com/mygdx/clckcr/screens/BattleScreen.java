package com.mygdx.clckcr.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.clckcr.gameplay.CommonEnemy;
import com.mygdx.clckcr.gameplay.player.Player;

import java.util.Random;


public class BattleScreen implements Screen {

    private Camera camera;
    private Viewport viewport;
    private SpriteBatch batch;
    private MainScreen mainScreen;
    private Game game;
    private Array<CommonEnemy> enemies;
    private Stage stage;
    private CommonEnemy enemy;
    private Skin textSkin;
    private Label enemyHpLabel;
    private Label playerHpLabel;

    private boolean isShieldReady = true;
    private boolean isPotionReady = true;
    private boolean isFireSwordReady = true;


    private ImageButton heavenShieldButton;
    private ImageButton healingPotionButton;
    private ImageButton fireSwordButton;

    private int enemyDamage;

    private double heavenShieldCastPeriod;
    private double heavenShieldCooldown = 60f;
    private double healingPotionCooldown = 60f;
    private double fireSwordCastPeriod = 10f;
    private double fireSwordCooldown = 60f;


    private Table menuTable;

    private int enemyOrder = 0;
    private Texture background;

    private double timeSecondsShield = 0f;
    private double timeSeconsPotion = 0f;
    private double timeSecondsFireSword = 0f;
    private double timeSeconds = 0f;
    private double period;

    private Player player;
    private String difficulty;

    private final int WORLD_WIDTH = 400;
    private final int WORLD_HEIGHT = 800;


    public BattleScreen(Array<CommonEnemy> enemies, final Player player, Game game, String difficulty, Texture newBackground){
        this.player = player;
        this.game = game;
        this.enemies = enemies;
        this.difficulty = difficulty;
        viewport = new ScreenViewport();
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);
        background = newBackground;
        textSkin = new Skin(Gdx.files.internal("skins/skill_row/skillTextSkin.json"));
        enemy = enemies.get(0);
        period = enemy.getTimeBetweenAttack();
        enemyHpLabel = new Label("Enemy hp: " + enemy.getHp(), textSkin, "skillText");
        playerHpLabel = new Label("Player hp: " + player.getHp(), textSkin, "skillText");

        heavenShieldCastPeriod = player.getHeavenShieldLvl();
        enemyDamage = (int) enemy.getDamage();

        enemyHpLabel.setPosition(5, viewport.getScreenHeight() - 20);
        playerHpLabel.setPosition(viewport.getScreenWidth() - 120, viewport.getScreenHeight() - 20);
        prepareStage();
        addEnemyListener();
        System.out.println("Screen Generated");
        camera = new OrthographicCamera();

        batch = new SpriteBatch();




    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        timeSeconds += Gdx.graphics.getDeltaTime();
        if (timeSeconds > period){
            timeSeconds -= period;
            enemyHitHandler();
        }
        if (!isShieldReady){
            timeSecondsShield += Gdx.graphics.getDeltaTime();
            if (timeSecondsShield > heavenShieldCastPeriod){
                enemy.setDamage(enemyDamage);
            }
            if (timeSecondsShield > heavenShieldCooldown){
                timeSecondsShield -= heavenShieldCooldown;
                heavenShieldButton.setDisabled(false);
                isShieldReady = true;
            }

        }

        if (!isPotionReady){
            timeSeconsPotion += Gdx.graphics.getDeltaTime();
            if (timeSeconsPotion > healingPotionCooldown){
                timeSeconsPotion -= healingPotionCooldown;
                healingPotionButton.setDisabled(false);
                isPotionReady = true;
            }
        }

        if (!isFireSwordReady){
            timeSecondsFireSword += Gdx.graphics.getDeltaTime();
            if (timeSecondsFireSword > fireSwordCastPeriod){
                player.setCritChance(player.getCritChanceLvl());
            }
        }

        stage.act(delta);
        stage.getBatch().begin();
        stage.getBatch().draw(background, 0, 0, WORLD_WIDTH, WORLD_HEIGHT);
        stage.getBatch().end();
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        batch.setProjectionMatrix(camera.combined);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void show() {

    }



    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    public void addEnemyListener(){
        enemy.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                int hitResult;
                if (isCrit(player.getCritChance())){
                    hitResult = enemy.getHit(player.getTotalDamage() + player.getCritDamage());
                    System.out.println("Crit!");
                }
                else{
                    hitResult = enemy.getHit(player.getTotalDamage());
                }

                if(hitResult == 0){
                    enemyOrder++;
                    if (enemyOrder >= enemies.size){
                        player.setHp(player.getHpLvl());
                        if (difficulty == "Easy"){
                            player.giveCoins(100 * player.getEasyQuestStage() + 3 * 220);
                            player.setEasyQuestStage(player.getEasyQuestStage() + 1);

                        }
                        else if (difficulty == "Medium"){
                            player.giveCoins(200 * player.getMediumQuestStage() + 6 * 330);
                            player.setMediumQuestStage(player.getMediumQuestStage() + 1);
                        }
                        else if (difficulty == "Hard"){
                            player.giveCoins(300 * player.getHardQuestStage() + 9 * 440);
                            player.setHardQuestStage(player.getHardQuestStage() + 1);
                        }
                        game.setScreen(new MainScreen(game, player));
                        return;
                    }
                    enemy = enemies.get(enemyOrder);
                    enemyHpLabel.setText("Enemy hp:" + enemy.getHp());
                    addEnemyListener();
                    prepareStage();
                }
                else {
                    enemyHpLabel.setText("Enemy hp:" + enemy.getHp());
                }

            }
        });
    }

    public void prepareStage(){

        Skin skillSkin = new Skin(Gdx.files.internal("skins/battle_skin/battle_skin_skills.json"));


        Table mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.add(enemy).expandY().center();
        mainTable.row();

        heavenShieldButton = new ImageButton(skillSkin, "heavenShield");
        healingPotionButton = new ImageButton(skillSkin, "healingPotion");
        fireSwordButton = new ImageButton(skillSkin, "fireSword");

        menuTable = new Table();

        menuTable.setBackground(new TextureRegionDrawable(new Texture(Gdx.files.internal("backgrounds/skill_row.png"))));

        menuTable.add(playerHpLabel).expandX();
        menuTable.add();
        menuTable.add(enemyHpLabel).expandX();

        menuTable.row();

        menuTable.add(heavenShieldButton);
        menuTable.add(healingPotionButton);
        menuTable.add(fireSwordButton);

        mainTable.add(menuTable).bottom().padBottom(50).expandX().fillX();
        menuTable.bottom().center();
        stage.clear();
        stage.addActor(mainTable);
//        stage.addActor(enemyHpLabel);
//        stage.addActor(playerHpLabel);
//        stage.addActor(enemy);

//        stage.addActor(newTabel);

        initializeListeners();

    }

    public void enemyHitHandler(){
        player.takeDamage(enemy.calculateHit());
        if (player.getHp() <= 0) {
            player.setHp(player.getHpLvl());
            game.setScreen(new MainScreen(game, player));
        }
        playerHpLabel.setText("Player hp: " + player.getHp());
    }

    public boolean  isCrit(double probability) {
        Random random = new Random();
        if (random.nextInt(100) <= probability) {
            return true;
        } else {
            return false;
        }
    }

    public void initializeListeners(){
        heavenShieldButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                heavenShieldButton.setDisabled(true);
                enemy.setDamage(0);
                isShieldReady = false;
            }
        });

        healingPotionButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                healingPotionButton.setDisabled(true);
                player.heal(player.getHealingPotionLvl() * 2);
                isPotionReady = false;
            }
        });

        fireSwordButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                fireSwordButton.setDisabled(true);
                player.giveCritChance(player.getCritChanceLvl() * 1.25);
                isFireSwordReady = false;
            }
        });

    }

}
