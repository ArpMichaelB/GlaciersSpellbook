package com.glacier;

import com.gluonhq.charm.down.Platform;
import com.gluonhq.charm.down.Services;
import com.gluonhq.charm.down.plugins.LifecycleService;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.application.ViewStackPolicy;
import com.gluonhq.charm.glisten.control.Avatar;
import com.gluonhq.charm.glisten.control.NavigationDrawer;
import com.gluonhq.charm.glisten.control.NavigationDrawer.Item;
import com.gluonhq.charm.glisten.control.NavigationDrawer.ViewItem;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import static com.glacier.MultiViewTest.PICK_CHARACTER;
import static com.glacier.MultiViewTest.SPELL_LIST;
import static com.glacier.MultiViewTest.SPELL_SEARCH;
import static com.glacier.MultiViewTest.DICE_ROLLER;
import static com.glacier.MultiViewTest.CUSTOM_SPELL;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class DrawerManager {

    public static void buildDrawer(MobileApplication app) {
        NavigationDrawer drawer = app.getDrawer();
        NavigationDrawer.Header header = new NavigationDrawer.Header("DnD Spellbook","Glacier Nester");
        ImageView icon = new ImageView(new Image(DrawerManager.class.getResourceAsStream("/icon.png")));
        icon.setFitHeight(25);
        icon.setFitWidth(25);
        header.setGraphic(icon);
        
        drawer.setHeader(header);
        
        final Item pickCharacter = new ViewItem("Pick a Character", MaterialDesignIcon.ACCOUNT_BOX.graphic("-fx-text-fill:-text-dark"), PICK_CHARACTER, ViewStackPolicy.SKIP);
        final Item spellList = new ViewItem("Spell List", MaterialDesignIcon.BOOK.graphic("-fx-text-fill:-text-dark"), SPELL_LIST);
        final Item spellSearch = new ViewItem("Search for a Spell", MaterialDesignIcon.SEARCH.graphic("-fx-text-fill:-text-dark"), SPELL_SEARCH);
        final Item diceRoller = new ViewItem("Roll Dice", MaterialDesignIcon.CASINO.graphic("-fx-text-fill:-text-dark"),DICE_ROLLER);
        final Item customSpell = new ViewItem("Custom Spell Input",MaterialDesignIcon.IMPORT_CONTACTS.graphic("-fx-text-fill:-text-dark"),CUSTOM_SPELL);
        drawer.getItems().addAll(pickCharacter, spellList,spellSearch,diceRoller,customSpell);
        
        if (Platform.isDesktop()) {
            final Item quitItem = new Item("Quit", MaterialDesignIcon.EXIT_TO_APP.graphic());
            quitItem.selectedProperty().addListener((obs, ov, nv) -> {
                if (nv) {
                    Services.get(LifecycleService.class).ifPresent(LifecycleService::shutdown);
                }
            });
            drawer.getItems().add(quitItem);
        }
    }
}
