package com.mygdx.clckcr.gameplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.Array;

import java.util.Random;

public class CommonEnemy extends Actor implements EnemyActor{
    private double hp;
    private double initialHp;
    private double damage;
    private double timeBetweenAttack;
    private int x = 150;
    private int y = 350;

    private Array<TextureRegion> enemyConditions;

    TextureRegion enemyTexture;

    public CommonEnemy (int enemyNumber, double hp,
                        double damage, double timeBetweenAttack){
        this.hp = hp;
        initialHp = hp;
        this.damage = damage;
        this.timeBetweenAttack = timeBetweenAttack;
        enemyConditions = new Array<>();
        for (int i = 0; i < 3; i++){
            enemyConditions.add(new TextureRegion(new Texture(Gdx.files.internal("enemies/Monstr" + enemyNumber + i + ".png"))));
        }
        enemyTexture = enemyConditions.get(0);
        setBounds(x, y,
                128, 128);
//        for (int i = 1; i <= 4; i++){
//            enemyTextures.add(new TextureRegion(new Texture(Gdx.files.internal("enemies/Monstr" + i + ".png"))));
//        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(enemyTexture, getX(), getY(), getOriginX(), getOriginY(),
                getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }

    @Override
    public Actor hit(float x, float y, boolean touchable) {
        if (touchable && getTouchable() != Touchable.enabled) return null;
        return x >= 0 && x < this.getWidth() && y >= 0 && y < this.getHeight() ? this : null;
    }

    @Override
    public int getHit(double damage) {
        Random random = new Random();
        hp -= damage;
        x += random.nextInt(10);
        y += random.nextInt(10);
        x -= random.nextInt(10);
        x -= random.nextInt(10);
        if (x <= 70){
            x += 15;
        }
        if (y <= 250){
            y += 35;
        }
        if (x >= 180){
            x -= 40;
        }
        if (y >= 450){
            y -= 50;
        }

        if ((hp / initialHp) <= 0.75 && (hp/initialHp) > 0.25){
            enemyTexture = enemyConditions.get(1);
        }
        if ((hp / initialHp) <= 0.25){
            enemyTexture = enemyConditions.get(2);
        }

        setBounds(x, y, 128, 128);
        if(hp <= 0){
            return 0;
        }
        else {
            return 1;
        }
    }

    @Override
    public double calculateHit() {
        return damage;
    }

    public double getHp(){
        return hp;
    }

    public double getTimeBetweenAttack() {
        return timeBetweenAttack;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public double getDamage() {
        return damage;
    }

    //    public Actor hit(float x, float y, boolean touchable){
//        if (touchable && getTouchable())
//    }

}
