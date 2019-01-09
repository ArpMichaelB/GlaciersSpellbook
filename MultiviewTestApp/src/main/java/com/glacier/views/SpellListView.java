package com.glacier.views;

import static com.glacier.MultiViewTest.DICE_ROLLER;
import static com.glacier.MultiViewTest.SPELL_LIST;
import static com.glacier.MultiViewTest.SPELL_SEARCH;
import static com.glacier.MultiViewTest.CUSTOM_SPELL;
import static com.glacier.MultiViewTest.PICK_CHARACTER;

import org.json.simple.JSONObject;

import com.glacier.MultiViewTest;
import com.glacier.backend.CharacterBackend;
import com.glacier.backend.SearchBackend;
import com.glacier.util.Constants;
import com.gluonhq.charm.glisten.animation.FlipInYTransition;
import com.gluonhq.charm.glisten.animation.MobileTransition;
import com.gluonhq.charm.glisten.animation.NoTransition;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.LifecycleEvent;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import com.gluonhq.charm.glisten.visual.Swatch;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.VBox;

public class SpellListView extends View {

    @SuppressWarnings("unchecked")
    public SpellListView()
    {
        VBox controls = new VBox(10.0);
        ScrollPane scrolly = new ScrollPane();
        scrolly.setContent(controls);
        scrolly.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
        scrolly.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
        scrolly.setFitToWidth(true);
        setCenter(scrolly);
        setOnShowing(new EventHandler<LifecycleEvent>(){
        	@Override
			public void handle(LifecycleEvent event) {
				controls.getChildren().removeAll(controls.getChildren());
				//regardless of whether they have spells, remove the stuff from the screen
				//so it doesn't keep piling up
				if(!CharacterBackend.getSpellList().isEmpty())
		        {
		        	CharacterBackend.getSpellList().forEach(ex ->
			        {
			        	JSONObject spell = (JSONObject) ex;
			        	spell.keySet().forEach(e -> 
						{
							//i.e. for each string in the key set
							Button btnDelete = new Button("Delete " + e);
							btnDelete.setOnAction(del ->
							{
								MobileTransition prev = getShowTransition();
								CharacterBackend.deleteSpell((String) e);
								setShowTransitionFactory(v-> new NoTransition());
								getApplication().switchView(DICE_ROLLER);
								getApplication().switchView(SPELL_LIST);
								setShowTransitionFactory(v -> prev);
							});
							Label title = new Label(e + ":");
							title.setId("SpellName");
							controls.getChildren().add(title);
							final JSONObject spellDetails = (JSONObject) spell.get(e);
							Label description = new Label((String) spellDetails.get("description"));
							description.setWrapText(true);
							controls.getChildren().add(description);
							if(!((String) spellDetails.get("dice")).isEmpty())
							{
								Button btnRoll = new Button("Roll dice");
								btnRoll.setOnAction(act ->{
									Constants.setHeldDice(SearchBackend.extractDiceFromSpellDescription((String)spellDetails.get("dice")));
									getApplication().switchView(DICE_ROLLER);
								});
								controls.getChildren().add(btnRoll);
							}
							controls.getChildren().add(btnDelete);
						});
			        });
		        }
		        else
		        {
		        	Label noSpells = new Label("No Spells Found");
		        	noSpells.setId("SpellName");
		        	controls.getChildren().add(noSpells);
		        }
			}
        	
        });
        //each time the view is about to be shown, update the spell list from the chosen character's file
        setShowTransitionFactory(v->new FlipInYTransition(v));
    }

    @Override
    protected void updateAppBar(AppBar appBar) {
        appBar.setNavIcon(MaterialDesignIcon.MENU.button(e -> getApplication().getDrawer().open()));
        appBar.setTitleText("List of Spells");
        appBar.getActionItems().addAll(
        		MaterialDesignIcon.ACCOUNT_BOX.button(e -> getApplication().switchView(PICK_CHARACTER)),
        		MaterialDesignIcon.IMPORT_CONTACTS.button(e -> getApplication().switchView(CUSTOM_SPELL)),
        		MaterialDesignIcon.SEARCH.button(e -> getApplication().switchView(SPELL_SEARCH)));
        MultiViewTest.getInstance().setSwatch(Swatch.TEAL);
    }
   
    
}
