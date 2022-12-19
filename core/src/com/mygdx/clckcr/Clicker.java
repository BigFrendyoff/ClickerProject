package com.mygdx.clckcr;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.clckcr.gameplay.player.Player;
import com.mygdx.clckcr.screens.BattleScreen;
import com.mygdx.clckcr.screens.MainScreen;

public class Clicker extends Game {

	MainScreen mainScreen;

	
	@Override
	public void create () {
		mainScreen = new MainScreen(this, new Player());
		setScreen(mainScreen);
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
	}
}
