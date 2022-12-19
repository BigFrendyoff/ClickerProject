package com.mygdx.clckcr.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.clckcr.gameplay.CommonEnemy;
import com.mygdx.clckcr.gameplay.player.Player;

import org.w3c.dom.Text;

import java.util.Random;

import javax.swing.text.TabExpander;

public class MainScreen implements Screen {

    private Camera camera;
    private Viewport viewport;
    private SpriteBatch batch;
    private Stage stage;
    private Table menuTable;
    private Table mainTable;
    private Table skillsTable;
    private Table questsTable;
    private Table statsTable;
    private Table shownTable;
    private Stack menuTableStack;
    private BattleScreen battleScreen;
    private Game game;
    private Player player;
    private ShapeRenderer shapeRenderer;

    private TextButton swordAddButton;
    private TextButton armorAddButton;
    private TextButton critAddButton;
    private TextButton critChanceAddButton;
    private TextButton heavenShielAddButton;
    private TextButton healingPotionAddButton;
    private TextButton fireSwordAddButton;

    private Label damageSkillLabel;
    private Label armorSkillLabel;
    private Label critDamageSkillLabel;
    private Label critChanceSkillLabel;
    private Label heavenShieldSkillLabel;
    private Label healingPotionSkillLabel;
    private Label fireSwordSkillLabel;
    private Label playerCoinsLabel;

    private Texture backgroundTexture;

    private Skin skillButtonSkin;
    private Skin textSkin;

    private Skin menuButtonsSkin;
    private ImageButton questButton;
    private ImageButton skillButton;
    private ImageButton statsButton;

    private ImageButton easyStart;
    private ImageButton mediumStart;
    private ImageButton hardStart;

    Random random;

    private final int WORLD_WIDTH = 400;
    private final int WORLD_HEIGHT = 800;

    public MainScreen(final Game game, Player player) {
        camera = new OrthographicCamera();
        viewport = new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        batch = new SpriteBatch();
        this.game = game;
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        this.player = player;
        random = new Random();

        menuTable = new Table();
        mainTable = new Table();
        skillsTable = new Table();
        questsTable = new Table();
        statsTable = new Table();

        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);

        textSkin = new Skin(Gdx.files.internal("skins/skill_row/skillTextSkin.json"));

        createSkillTable();
        createQuestTable();



        mainTable.setFillParent(true);


        menuButtonsSkin = new Skin(Gdx.files.internal("skins/menu_skin/MenuButtonSkin.json"));
        questButton = new ImageButton(menuButtonsSkin, "QuestButton");
        skillButton = new ImageButton(menuButtonsSkin, "SkillsButton");
        statsButton = new ImageButton(menuButtonsSkin, "StatsButton");


        initButtonListeners();

        menuTable.add(questButton).width(64).height(64);
        menuTable.add(skillButton).width(64).height(64).padLeft(20);
        menuTable.add(statsButton).width(64).height(64).padLeft(20);

        mainTable.add(questsTable);
        mainTable.row();
        mainTable.add(menuTable).expand().bottom().fillX().height(viewport.getWorldHeight() / 5);

        stage.addActor(mainTable);

        backgroundTexture = new Texture(Gdx.files.internal("backgrounds/bottom_menu.png"));

        menuTable.setBackground(new TextureRegionDrawable(backgroundTexture));


    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();


    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }



    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public void show() {

    }

    public Table createSkillDescription(String skillName, Label skillLvl, String description) {
        Table newTable = new Table();
        newTable.add(new Label(skillName, textSkin, "skillText"));
        newTable.row();
        newTable.add(new Label(description, textSkin, "skillText"));
        newTable.add(skillLvl).padLeft(15);
        return newTable;
    }

    public Table createQuestDescription(String difficulty, String enemyRange, String stageLvl){
        Table newTable = new Table();
        newTable.add(new Label(difficulty, textSkin, "skillText"));
        newTable.row();
        newTable.add(new Label(enemyRange, textSkin, "skillText"));
        newTable.add(new Label(stageLvl, textSkin, "skillText")).padLeft(15);
        return newTable;

    }


    public void initButtonListeners(){
        questButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                mainTable.clearChildren();
                mainTable.add(questsTable);
                mainTable.row();
                mainTable.add(menuTable).expand().bottom().fillX().height(viewport.getWorldHeight() / 5);
                System.out.println("quest button clicked!");
            }
        });

        skillButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                mainTable.clearChildren();
                mainTable.add(skillsTable).expand().center();
                mainTable.row();
                mainTable.add(menuTable).expand().bottom().fillX().height(viewport.getWorldHeight() / 5);
                System.out.println("skill button clicked!");
            }
        });

        statsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                mainTable.clearChildren();
                createStatTable();
                mainTable.add(statsTable);
                mainTable.row();
                mainTable.add(menuTable).expand().bottom().fillX().height(viewport.getWorldHeight() / 5);
            }
        });

        swordAddButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                player.takeFromCoins((player.getCritDmgLvl() + 1) * 1000);
                player.setTotalDamage(player.getDamageLvl() + 1);
                playerCoinsLabel.setText("Coins: " + player.getCoins());
                damageSkillLabel.setText(player.getDamageLvl());
                setPrices();
                isEnoughCoins();

            }
        });

        armorAddButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                player.takeFromCoins((player.getHpLvl() + 1) * 1000);
                player.setHp(player.getHpLvl() + 1);
                playerCoinsLabel.setText("Coins: " + player.getCoins());
                armorSkillLabel.setText(player.getHpLvl());
                setPrices();
                isEnoughCoins();
            }
        });

        critAddButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                player.takeFromCoins((player.getCritDmgLvl() + 1) * 1000);
                player.setCritDamage(player.getCritDmgLvl() + 1);
                playerCoinsLabel.setText("Coins: " + player.getCoins());
                critDamageSkillLabel.setText(player.getCritDmgLvl());
                setPrices();
                isEnoughCoins();
            }
        });

        critChanceAddButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                player.takeFromCoins((player.getCritChanceLvl() + 1) * 1000);
                player.setCritChance(player.getCritChanceLvl() + 1);
                playerCoinsLabel.setText("Coins: " + player.getCoins());
                critChanceSkillLabel.setText(player.getCritChanceLvl());
                setPrices();
                isEnoughCoins();
            }
        });

        heavenShielAddButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                player.takeFromCoins((player.getHeavenShieldLvl() + 1) * 2000);
                player.setHeavenShieldLvl(player.getHeavenShieldLvl() + 1);
                playerCoinsLabel.setText("Coins: " + player.getCoins());
                heavenShieldSkillLabel.setText(player.getHeavenShieldLvl());
                setPrices();
                isEnoughCoins();
            }
        });

        healingPotionAddButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                player.takeFromCoins((player.getHealingPotionLvl() + 1) * 2000);
                player.setHealingPotionLvl(player.getHealingPotionLvl() + 1);
                playerCoinsLabel.setText("Coins: " + player.getCoins());
                healingPotionSkillLabel.setText(player.getHealingPotionLvl());
                setPrices();
                isEnoughCoins();

            }
        });

        fireSwordAddButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                player.takeFromCoins((player.getFireSwordLvl() + 1) * 2000);
                player.setFireSwordLvl(player.getFireSwordLvl() + 1);
                playerCoinsLabel.setText("Coins: " + player.getCoins());
                fireSwordSkillLabel.setText(player.getFireSwordLvl());
                setPrices();
                isEnoughCoins();
            }
        });

        easyStart.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                Array<CommonEnemy> createdQuest = createEnemies(3,
                        player.getEasyQuestStage() * 1000,
                        player.getEasyQuestStage(),
                        0.9);
                BattleScreen battleScreen = new BattleScreen(createdQuest, player, game,
                        "Easy",
                        new Texture(Gdx.files.internal("backgrounds/battle_backgrounds/back" + random.nextInt(4) + ".png")));
                game.setScreen(battleScreen);
            }
        });

        mediumStart.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                Array<CommonEnemy> createdQuest = createEnemies(6,
                        player.getMediumQuestStage() * 1100,
                        player.getMediumQuestStage() * 2,
                        0.6);
                BattleScreen battleScreen = new BattleScreen(createdQuest, player, game,
                        "Medium",
                        new Texture(Gdx.files.internal("backgrounds/battle_backgrounds/back" + random.nextInt(4) + ".png")));
                game.setScreen(battleScreen);
            }
        });

        hardStart.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                Array<CommonEnemy> createdQuest = createEnemies(9,
                        player.getEasyQuestStage() * 1200,
                        player.getEasyQuestStage() * 3,
                        0.3);
                BattleScreen battleScreen = new BattleScreen(createdQuest, player, game,
                        "Hard",
                        new Texture(Gdx.files.internal("backgrounds/battle_backgrounds/back" + random.nextInt(4) + ".png")));
                game.setScreen(battleScreen);
            }
        });
    }

    public void createSkillTable(){
        skillsTable.center();

        damageSkillLabel = new Label(player.getDamageLvl().toString(), textSkin, "skillText");
        armorSkillLabel = new Label(player.getHpLvl().toString(), textSkin, "skillText");
        critDamageSkillLabel = new Label(player.getCritDmgLvl().toString(), textSkin, "skillText");
        critChanceSkillLabel = new Label(player.getCritChanceLvl().toString(), textSkin, "skillText");
        heavenShieldSkillLabel = new Label(String.valueOf(player.getHeavenShieldLvl()), textSkin, "skillText");
        healingPotionSkillLabel = new Label(String.valueOf(player.getHealingPotionLvl()), textSkin, "skillText");
        fireSwordSkillLabel = new Label(String.valueOf(player.getFireSwordLvl()), textSkin, "skillText");
        playerCoinsLabel = new Label("Coins: " + player.getCoins(), textSkin, "skillText");



        skillButtonSkin = new Skin(Gdx.files.internal("skins/skill_row/skill_add/skill_add_text_button.json"));

        swordAddButton = new TextButton(String.valueOf((player.getDamageLvl() + 1) * 1000) ,skillButtonSkin, "default");

        skillsTable.setBackground(new TextureRegionDrawable(new Texture("backgrounds/menu_background_skills.png")));

        skillsTable.add(createSkillRow(null, new Table().add(playerCoinsLabel).getTable(), null));
        skillsTable.row();


        skillsTable.add(createSkillRow(new Image(new Texture(Gdx.files.internal("skills/sword_skill.png"))),
                createSkillDescription("Sword", damageSkillLabel, "Increase DMG"),
                swordAddButton)).expandX().fillX().height(64);

        skillsTable.row();

        armorAddButton = new TextButton(String.valueOf((player.getHpLvl() + 1) * 1000) ,skillButtonSkin, "default");


        skillsTable.add(createSkillRow(new Image(new Texture(Gdx.files.internal("skills/armor_skill.png"))),
                createSkillDescription("Armor", armorSkillLabel, "Increase HP"),
                armorAddButton)).expandX().fillX().height(64);


        skillsTable.row();

        critAddButton = new TextButton(String.valueOf((player.getCritDmgLvl() + 1) * 1000) ,skillButtonSkin, "default");

        skillsTable.add(createSkillRow(new Image(new Texture(Gdx.files.internal("skills/crit_skill.png")))
                , createSkillDescription("Crit", critDamageSkillLabel, "Increase CRT"),
                critAddButton)).expandX().fillX().height(64);


        skillsTable.row();

        critChanceAddButton = new TextButton(String.valueOf((player.getCritChanceLvl() + 1) * 1000) ,skillButtonSkin, "default");

        skillsTable.add(createSkillRow(new Image(new Texture(Gdx.files.internal("skills/crit_chance_skill.png"))),
                createSkillDescription("Crit chance", critChanceSkillLabel, "Increase CRT %"),
                critChanceAddButton)).expandX().fillX().height(64);

        skillsTable.row();

        heavenShielAddButton = new TextButton(String.valueOf((player.getHeavenShieldLvl() + 1) * 2000),
                skillButtonSkin, "default");

        skillsTable.add(createSkillRow(new Image(new Texture(Gdx.files.internal("skills/heaven_shield_skill.png"))),
                createSkillDescription("Heaven shield (Active)", heavenShieldSkillLabel, "Protects you"),
                heavenShielAddButton)).expandX().fillX().height(64);

        skillsTable.row();

        healingPotionAddButton = new TextButton(String.valueOf((player.getHealingPotionLvl() + 1) * 2000),
                skillButtonSkin, "default");

        skillsTable.add(createSkillRow(new Image(new Texture(Gdx.files.internal("skills/healing_potion_skill.png"))),
                createSkillDescription("Healing potion (Active)", healingPotionSkillLabel, "Healing you!"),
                healingPotionAddButton)).expandX().fillX().height(64);

        skillsTable.row();

        fireSwordAddButton = new TextButton(String.valueOf((player.getFireSwordLvl() + 1) * 2000),
                skillButtonSkin, "default");

        skillsTable.add(createSkillRow(new Image(new Texture(Gdx.files.internal("skills/fire_sword_skill.png"))),
                createSkillDescription("Fire sword (Active)", fireSwordSkillLabel, "Deals permanent damage"),
                fireSwordAddButton)).expandX().fillX().height(64);


        isEnoughCoins();

    }

    public void createStatTable(){
        statsTable.clear();
        statsTable.setBackground(new TextureRegionDrawable(new Texture(Gdx.files.internal("backgrounds/stats_background.png"))));
//        statsTable.add(new Image(new Texture(Gdx.files.internal("character.png")))).expandY();
        statsTable.add(createStatSummary());
    }

    public Table createSkillRow(Image image, Table table, TextButton textButton){
        Table newTable = new Table();
        newTable.setBackground(new TextureRegionDrawable(new Texture(Gdx.files.internal("backgrounds/skill_row.png"))));
        newTable.add(image).expandX();
        newTable.add(table).expandX();
        newTable.add(textButton).expandX();
        return newTable;
    }

    public Table createStatSummary(){
        Table newTable = new Table();
        newTable.setBackground(new TextureRegionDrawable(new Texture(Gdx.files.internal("backgrounds/stat_row.png"))));
        newTable.add(new Label("Damage: " + player.getTotalDamage(), textSkin, "skillText"));
        newTable.row();
        newTable.add(new Label("HP: " + player.getHp(), textSkin, "skillText"));
        newTable.row();
        newTable.add(new Label("Crit damage: " + player.getCritDamage(), textSkin, "skillText"));
        newTable.row();
        newTable.add(new Label("Crit chance: " + player.getCritChance(), textSkin, "skillText"));
        newTable.row();
        return newTable;
    }

    public Table createQuestRow(Image image, Table questDescription, ImageButton button){
        Table newTable = new Table();
        newTable.setBackground(new TextureRegionDrawable(new Texture(Gdx.files.internal("backgrounds/quest_row.png"))));
        newTable.add(image).expandX();
        newTable.add(questDescription).expandX();
        newTable.add(button).expandX();
        return newTable;
    }

    public void createQuestTable(){
        Skin questButtonSkin = new Skin(Gdx.files.internal("quest_button/start_button_skin.json"));

        questsTable.setBackground(new TextureRegionDrawable(
                new Texture(Gdx.files.internal("backgrounds/quest_background.png"))));

        easyStart = new ImageButton(questButtonSkin, "default");

        questsTable.add(createQuestRow(new Image(
                new Texture(Gdx.files.internal("difficulties/easy_difficulty.png"))),
                createQuestDescription("Easy", "3 mobs",
                        player.getEasyQuestStage().toString()),
                easyStart)).expandX().fillX();
//        questsTable.add(new Image(
//                new Texture(Gdx.files.internal("difficulties/easy_difficulty.png"))));
//        questsTable.add(createQuestDescription("Easy", "3 mobs",
//                player.getEasyQuestStage().toString()));
//        questsTable.add(easyStart);
        questsTable.row();

        mediumStart = new ImageButton(questButtonSkin, "default");
        questsTable.add(createQuestRow(new Image(
                new Texture(Gdx.files.internal("difficulties/medium_difficulty.png"))),
                createQuestDescription("Medium", "6 mobs",
                        player.getMediumQuestStage().toString()),
                mediumStart)).expandX().fillX();
//        questsTable.add(new Image(
//                new Texture(Gdx.files.internal("difficulties/medium_difficulty.png"))));
//        questsTable.add(createQuestDescription("Medium", "6 mobs",
//                player.getMediumQuestStage().toString()));
//        questsTable.add(mediumStart);
        questsTable.row();

        hardStart = new ImageButton(questButtonSkin, "default");
        questsTable.add(createQuestRow(new Image(
                new Texture(Gdx.files.internal("difficulties/hard_difficulty.png"))),
                createQuestDescription("Hard", "9 mobs",
                        player.getHardQuestStage().toString()),
                hardStart)).expandX().fillX();
//        questsTable.add(new Image(
//                new Texture(Gdx.files.internal("difficulties/hard_difficulty.png"))));
//        questsTable.add(createQuestDescription("Hard", "9 mobs",
//                player.getHardQuestStage().toString()));
//        questsTable.add(hardStart);




    }

    public Array<CommonEnemy> createEnemies(double enemyCount, double enemyHp,
                                            double enemyDamage, double enemyTimeBetweenAttack){
        Array<CommonEnemy> enemies = new Array<>();
        Random random = new Random();
        for (int i = 0; i < enemyCount; i++){
            enemies.add(new CommonEnemy(
                    random.nextInt(4),
                    enemyHp, enemyDamage, enemyTimeBetweenAttack));
        }
        return enemies;
    }

    public void isEnoughCoins(){
        if (player.getCoins() < (player.getDamageLvl() + 1)*1000){
            swordAddButton.setDisabled(true);
        }
        else{
            swordAddButton.setDisabled(false);
        }

        if (player.getCoins() < (player.getHpLvl() + 1)*1000){
            armorAddButton.setDisabled(true);
        }
        else{
            armorAddButton.setDisabled(false);
        }

        if (player.getCoins() < (player.getCritDmgLvl() + 1)*1000){
            critAddButton.setDisabled(true);
        }
        else{
            critAddButton.setDisabled(false);
        }

        if (player.getCoins() < (player.getCritChanceLvl() + 1)*1000){
            critChanceAddButton.setDisabled(true);
        }
        else{
            critChanceAddButton.setDisabled(false);
        }

        if (player.getCoins() < (player.getHeavenShieldLvl() + 1) * 2000){
            heavenShielAddButton.setDisabled(true);
        }
        else{
            heavenShielAddButton.setDisabled(false);
        }

        if (player.getCoins() < (player.getHealingPotionLvl() + 1) * 2000){
            healingPotionAddButton.setDisabled(true);
        }
        else{
            healingPotionAddButton.setDisabled(false);
        }

        if (player.getCoins() < (player.getFireSwordLvl() + 1) * 2000){
            fireSwordAddButton.setDisabled(true);
        }
        else{
            fireSwordAddButton.setDisabled(false);
        }

    }
    public void setPrices(){
        swordAddButton.setText(String.valueOf((player.getDamageLvl() + 1) *1000));
        armorAddButton.setText(String.valueOf((player.getHpLvl() + 1) *1000));
        critAddButton.setText(String.valueOf((player.getCritChanceLvl() + 1) *1000));
        critChanceAddButton.setText(String.valueOf((player.getCritChanceLvl() + 1) *1000));
        heavenShielAddButton.setText(String.valueOf((player.getHeavenShieldLvl() + 1) * 2000));
        healingPotionAddButton.setText(String.valueOf((player.getHealingPotionLvl() + 1) * 2000));
        fireSwordAddButton.setText(String.valueOf((player.getFireSwordLvl() + 1) * 2000));
    }
}
