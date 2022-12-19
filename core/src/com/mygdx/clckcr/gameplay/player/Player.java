package com.mygdx.clckcr.gameplay.player;

import java.util.ArrayList;

public class Player {
    private int hpLvl = 1;
    private double hp;
    private int critDmgLvl = 1;
    private double critDamage;
    private int critChanceLvl = 1;
    private double critChance;
    private int coins;
    private int damageLvl = 1;
    private double totalDamage;
    private int easyQuestStage = 1;
    private int mediumQuestStage = 1;
    private int hardQuestStage = 1;
    private int heavenShieldLvl = 1;
    private int healingPotionLvl = 1;
    private int fireSwordLvl = 1;

    public Player() {
        hp = 100;
        critDamage = 5;
        critChance = 1.25;
        coins = 10000;
        totalDamage = 10;
    }

    public void calculateDamage(){

    }

    public double getTotalDamage() {
        return totalDamage;
    }

    public double getHp() {
        return hp;
    }

    public void setHp(int characteristicLvl) {
        hpLvl = characteristicLvl;
        this.hp = 100 * characteristicLvl;
    }


    public double getCritDamage() {
        return critDamage;
    }

    public void setCritDamage(int characteristicLvl) {
        critDmgLvl = characteristicLvl;
        this.critDamage = 5 * characteristicLvl;
    }

    public double getCritChance() {
        return critChance;
    }

    public void setCritChance(int characteristicLvl) {
        critChanceLvl = characteristicLvl;
        this.critChance = 1.25 * characteristicLvl;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public void setTotalDamage(int characteristicLvl) {
        damageLvl = characteristicLvl;
        this.totalDamage = 10 * characteristicLvl;
    }

    public Integer getHpLvl() {
        return hpLvl;
    }

    public Integer getCritDmgLvl() {
        return critDmgLvl;
    }

    public Integer getCritChanceLvl() {
        return critChanceLvl;
    }

    public Integer getDamageLvl() {
        return damageLvl;
    }

    public Integer getEasyQuestStage() {
        return easyQuestStage;
    }

    public void setEasyQuestStage(int easyQuestStage) {
        this.easyQuestStage = easyQuestStage;
    }

    public Integer getMediumQuestStage() {
        return mediumQuestStage;
    }

    public void setMediumQuestStage(int mediumQuestStage) {
        this.mediumQuestStage = mediumQuestStage;
    }

    public Integer getHardQuestStage() {
        return hardQuestStage;
    }

    public void setHardQuestStage(int hardQuestStage) {
        this.hardQuestStage = hardQuestStage;
    }

    public void takeDamage(double incomingDamage){
        hp -= incomingDamage;
    }
    public void giveCoins(int coins){
        this.coins += coins;
    }

    public void takeFromCoins(int coins){
        this.coins -= coins;
    }

    public int getHeavenShieldLvl() {
        return heavenShieldLvl;
    }

    public void setHeavenShieldLvl(int heavenShieldLvl) {
        this.heavenShieldLvl = heavenShieldLvl;
    }

    public int getHealingPotionLvl() {
        return healingPotionLvl;
    }

    public void setHealingPotionLvl(int healingPotionLvl) {
        this.healingPotionLvl = healingPotionLvl;
    }

    public int getFireSwordLvl() {
        return fireSwordLvl;
    }

    public void setFireSwordLvl(int fireSwordLvl) {
        this.fireSwordLvl = fireSwordLvl;
    }

    public void heal(int amount){
        hp += amount;
    }

    public void giveCritChance(double amount){
        critChance += amount;
    }


}
