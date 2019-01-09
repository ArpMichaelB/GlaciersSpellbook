package com.glacier;

import com.glacier.views.CustomSpellView;
import com.glacier.views.DiceRollerView;
import com.glacier.views.PickCharacterView;
import com.glacier.views.SpellListView;
import com.glacier.views.SpellSearchView;
import com.gluonhq.charm.down.Platform;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.visual.Swatch;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MultiViewTest extends MobileApplication {
	
    public static final String PICK_CHARACTER = HOME_VIEW;
    public static final String SPELL_LIST = "Spell List View";
    public static final String SPELL_SEARCH = "Spell Search";
    public static final String DICE_ROLLER = "Dice Roller";
    public static final String CUSTOM_SPELL = "Custom Spell Input";
    
    @Override
    public void init() {
        addViewFactory(PICK_CHARACTER, PickCharacterView::new);
        addViewFactory(SPELL_LIST, SpellListView::new);
        addViewFactory(SPELL_SEARCH,SpellSearchView::new);
        addViewFactory(DICE_ROLLER,DiceRollerView::new);
        addViewFactory(CUSTOM_SPELL,CustomSpellView::new);
        DrawerManager.buildDrawer(this);
    }

    @Override
    public void postInit(Scene scene) {
        Swatch.DEEP_PURPLE.assignTo(scene);

        scene.getStylesheets().add(MultiViewTest.class.getResource("style.css").toExternalForm());
        ((Stage) scene.getWindow()).getIcons().add(new Image(MultiViewTest.class.getResourceAsStream("/icon.png")));
        if(Platform.isDesktop())
    	{
        	scene.getWindow().setWidth(500);
        	scene.getWindow().setHeight(500);
    	}
    }
}
