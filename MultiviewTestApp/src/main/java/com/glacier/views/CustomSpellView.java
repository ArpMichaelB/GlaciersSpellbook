package com.glacier.views;

import static com.glacier.MultiViewTest.PICK_CHARACTER;
import static com.glacier.MultiViewTest.SPELL_LIST;
import static com.glacier.MultiViewTest.SPELL_SEARCH;

import com.glacier.MultiViewTest;
import com.glacier.backend.CharacterBackend;
import com.gluonhq.charm.glisten.animation.HingeTransition;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.TextArea;
import com.gluonhq.charm.glisten.control.TextField;
import com.gluonhq.charm.glisten.layout.layer.PopupView;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import com.gluonhq.charm.glisten.visual.Swatch;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class CustomSpellView extends View {
	public CustomSpellView()
	{
		VBox formLines = new VBox(15.0);
		Button btnSubmit = new Button("Add this spell");
		TextField name = new TextField();
		TextArea description = new TextArea();
		name.setFloatText("Name of the Spell");
		description.setFloatText("Description of the Spell");
		name.getStyleClass().add("text-field");
		description.getStyleClass().add("text-field");
		name.setOnKeyPressed(key ->
		{
			if(key.getCode().equals(KeyCode.ENTER))
				btnSubmit.fire();
		});
		description.setOnKeyPressed(key ->
		{
			if(key.getCode().equals(KeyCode.ENTER))
				btnSubmit.fire();
		});
		formLines.getChildren().addAll(name,description,btnSubmit);
		PopupView popup = new PopupView(this);
    	Button writeSpell = new Button("Write this please!");
    	Button noThanks = new Button("I'll pass.");
    	noThanks.setOnAction(e -> 
    	{
    		popup.hide();
    	});
    	writeSpell.setOnAction(e -> 
    	{
    		CharacterBackend.writeSpell(name.getText(), description.getText());
    		popup.hide();
		});
		btnSubmit.setOnAction(e -> {
			popup.setContent(new VBox(5,new Label("You want to write " + name.getText() + "?"),writeSpell,noThanks));
			popup.setVerticalOffset(getApplication().getView().getHeight()/4);
			popup.show();
		});
		setCenter(formLines);
		setShowTransitionFactory(v->new HingeTransition(v,false,Duration.seconds(1)));
	}
	@Override
	protected void updateAppBar(AppBar appBar) 
	{
		appBar.setNavIcon(MaterialDesignIcon.MENU.button(e -> getApplication().getDrawer().open()));
		appBar.setTitleText("Add Custom Spell");
		appBar.getActionItems().addAll(
				MaterialDesignIcon.ACCOUNT_BOX.button(e->getApplication().switchView(PICK_CHARACTER)),
				MaterialDesignIcon.BOOK.button(e -> getApplication().switchView(SPELL_LIST)),
				MaterialDesignIcon.SEARCH.button(e-> getApplication().switchView(SPELL_SEARCH)));
		MultiViewTest.getInstance().setSwatch(Swatch.BLUE_GREY);
	}
}
