package com.glacier.views;

import static com.glacier.MultiViewTest.SPELL_SEARCH;
import static com.glacier.MultiViewTest.PICK_CHARACTER;
import static com.glacier.MultiViewTest.SPELL_LIST;
import static com.glacier.MultiViewTest.DICE_ROLLER;

import com.glacier.MultiViewTest;
import com.glacier.backend.CharacterBackend;
import com.gluonhq.charm.glisten.animation.FlipInXTransition;
import com.gluonhq.charm.glisten.animation.MobileTransition;
import com.gluonhq.charm.glisten.animation.NoTransition;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.LifecycleEvent;
import com.gluonhq.charm.glisten.control.TextField;
import com.gluonhq.charm.glisten.layout.layer.PopupView;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import com.gluonhq.charm.glisten.visual.Swatch;

import javafx.animation.PauseTransition;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.util.Duration;


public class PickCharacterView extends View 
{	
    public PickCharacterView() 
    {
        VBox controls = new VBox(15.0);
        ScrollPane scrolly = new ScrollPane();
        scrolly.setContent(controls);
        scrolly.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
        scrolly.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
        scrolly.setFitToWidth(true);
        setOnShowing(new EventHandler<LifecycleEvent>(){

			@Override
			public void handle(LifecycleEvent event) 
			{
				
				controls.getChildren().removeAll(controls.getChildren());
		        for(String s : CharacterBackend.getCharacterNames())
		        {
		        	Button chooseCharacter = new Button(s);
		        	controls.getChildren().add(chooseCharacter);
		        	PopupView popup = new PopupView(getApplication().getGlassPane().getChildren().get(1));
		        	popup.setVerticalOffset(getApplication().getView().getHeight()/4);
		        	Button closepop = new Button("Okay!");
		        	Button deleteThis = new Button("Delete " + s);
		        	deleteThis.setOnAction(e->
		        	{
		        		MobileTransition prev = getShowTransition();
		        		CharacterBackend.deleteCharacter(s);
		        		popup.hide();
		        		setShowTransitionFactory(v-> new NoTransition());
		        		getApplication().switchView(SPELL_SEARCH);
		        		PauseTransition pause = new PauseTransition(Duration.millis(1));
			    		//this is a THOUSAND percent easier than thread based waiting golly
			    		pause.setOnFinished(ex->getApplication().switchView(PICK_CHARACTER));
			    		pause.play();
		        		getApplication().switchView(PICK_CHARACTER);
		        		setShowTransitionFactory(v->prev);
		        		//to update the screen so there's not a button for a deleted character onscreen anymore
		        		
	        		});
		        	closepop.setOnAction(e -> popup.hide());
		        	popup.setContent(new VBox(5,new Label("You've Chosen " + s),closepop,deleteThis));
		        	chooseCharacter.setOnAction(e -> 
		        	{
		        		CharacterBackend.setCharacter(s);
		        		popup.show();
	        		});
		        }
		        TextField newName = new TextField();
		        newName.setFloatText("Character Name");
		        
		        Button btAdd = new Button("Add a new character");
		    	btAdd.setOnAction(e ->
		    	{
		    		MobileTransition prev = getShowTransition();
		    		CharacterBackend.makeNewCharacter(newName.getText());
		    		setShowTransitionFactory(v-> new NoTransition());
		    		getApplication().switchView(SPELL_SEARCH);
		    		PauseTransition pause = new PauseTransition(Duration.millis(1));
		    		//this is a THOUSAND percent easier than thread based waiting golly
		    		pause.setOnFinished(ex->getApplication().switchView(PICK_CHARACTER));
		    		pause.play();
	        		setShowTransitionFactory(v->prev);
	        		//to update the screen so we can see the new character we've just made
	    		});
		    	newName.setOnKeyPressed(ex -> {
		        	if(ex.getCode().equals(KeyCode.ENTER))
		        	{
		        		btAdd.fire();
		        	}
		        });
		        controls.setAlignment(Pos.CENTER);
		        controls.getChildren().addAll(newName,btAdd);
			}
        });
        setCenter(scrolly);
        //TODO: CSS/Styling
        setShowTransitionFactory(v->new FlipInXTransition(v));
    }

    @Override
    protected void updateAppBar(AppBar appBar) {
        appBar.setNavIcon(MaterialDesignIcon.MENU.button(e -> getApplication().getDrawer().open()));
        appBar.setTitleText("Pick your character");
        appBar.getActionItems().addAll(
        		MaterialDesignIcon.BOOK.button(e-> getApplication().switchView(SPELL_LIST)),
        		MaterialDesignIcon.SEARCH.button(e -> getApplication().switchView(SPELL_SEARCH)),
        		MaterialDesignIcon.CASINO.button(e-> getApplication().switchView(DICE_ROLLER))
		);
        
        MultiViewTest.getInstance().setSwatch(Swatch.DEEP_PURPLE);
    }
    
}
