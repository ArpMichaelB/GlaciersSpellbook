package com.glacier.views;

import static com.glacier.MultiViewTest.SPELL_LIST;
import static com.glacier.MultiViewTest.CUSTOM_SPELL;

import org.json.simple.JSONObject;

import com.glacier.MultiViewTest;
import com.glacier.backend.CharacterBackend;
import com.glacier.backend.SearchBackend;
import com.glacier.enums.BadMessages;
import com.gluonhq.charm.glisten.animation.BounceInUpTransition;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.TextField;
import com.gluonhq.charm.glisten.layout.layer.PopupView;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import com.gluonhq.charm.glisten.visual.Swatch;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SpellSearchView extends View {
	public SpellSearchView() 
	{
		VBox things = new VBox(5);
		TextField searchTerm = new TextField();
		searchTerm.setFloatText("Search Term");
		Button btnSearch = new Button("Search for spell");
		HBox hold = new HBox();
		ScrollPane scrolly = new ScrollPane();
        scrolly.setContent(things);
        scrolly.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
        scrolly.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
        scrolly.setFitToWidth(true);
		hold.getChildren().addAll(searchTerm,btnSearch);
		things.getChildren().addAll(hold);
		btnSearch.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event)
			{
				JSONObject data = SearchBackend.getSpell(searchTerm.getText());
				if(((String) data.get("dice")).equals(BadMessages.BAD_DICE.toString()))
				{
					Label temp = new Label((String) data.get("description"));
					temp.setWrapText(true);
					things.getChildren().add(temp);
				}
				else
				{
					Button btnAdd = new Button("Add me");
					PopupView popup = new PopupView(getApplication().getGlassPane().getChildren().get(1));
					popup.setVerticalOffset(getApplication().getView().getHeight()/2);
					Button writeSpell = new Button("Write this please!");
		        	Button noThanks = new Button("I'll pass.");
		        	noThanks.setOnAction(e -> 
		        	{
		        		popup.hide();
		        	});
		        	writeSpell.setOnAction(e -> 
		        	{
		        		CharacterBackend.writeSpell((String)data.get("name"), (String)data.get("description"));
		        		popup.hide();
	        		});
		        	popup.setContent(new VBox(5,new Label("You've Chosen " + (String) data.get("name")),writeSpell,noThanks));
					btnAdd.setOnAction(e ->
					{
						popup.show();
					});
					Label description = new Label((String) data.get("description"));
					description.setWrapText(true);
					Label title = new Label((String) data.get("name") + ":");
					title.setId("SpellName");
					things.getChildren().addAll(title,description,btnAdd);
				}
			}
			
		});
		searchTerm.setOnKeyPressed(key ->{
			if(key.getCode().equals(KeyCode.ENTER))
				btnSearch.fire();
		});
		things.setAlignment(Pos.CENTER);
		things.setPrefWidth(getApplication().getGlassPane().getWidth()-15);
		setCenter(scrolly);
		setShowTransitionFactory(v->new BounceInUpTransition(v));
	}

	@Override
	protected void updateAppBar(AppBar appBar) 
	{
		appBar.setNavIcon(MaterialDesignIcon.MENU.button(e -> getApplication().getDrawer().open()));
		appBar.setTitleText("Search");
		appBar.getActionItems().addAll(
				MaterialDesignIcon.BOOK.button(e -> getApplication().switchView(SPELL_LIST)),
				MaterialDesignIcon.IMPORT_CONTACTS.button(e-> getApplication().switchView(CUSTOM_SPELL))
		);
		MultiViewTest.getInstance().setSwatch(Swatch.AMBER);
	}
}
