package com.glacier.views;

import static com.glacier.MultiViewTest.PICK_CHARACTER;
import static com.glacier.MultiViewTest.SPELL_LIST;

import java.net.URISyntaxException;

import com.glacier.MultiViewTest;
import com.glacier.backend.DiceBackend;
import com.glacier.util.Constants;
import com.gluonhq.charm.glisten.animation.RotateInTransition;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.LifecycleEvent;
import com.gluonhq.charm.glisten.control.TextField;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import com.gluonhq.charm.glisten.visual.Swatch;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class DiceRollerView extends View {
	public DiceRollerView() 
	{
		VBox overall = new VBox();
		VBox things = new VBox();
		ScrollPane scrolly = new ScrollPane();
        TextField diceToRoll = new TextField();
        diceToRoll.setFloatText("Enter a la 2d6+1");
        Button btRoll = new Button("Roll the Dice!");
		scrolly.setContent(things);
        scrolly.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
        scrolly.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
        btRoll.setOnAction(ex-> 
        {
        	try 
        	{
				things.getChildren().add(DiceBackend.getResult(diceToRoll.getText()));
			} 
        	catch (URISyntaxException e) 
        	{
				things.getChildren().add(new Label("Error in getting to the dice images, sorry"));
				e.printStackTrace();
			}
        });
        diceToRoll.setOnKeyPressed(key -> {
        	if(key.getCode().equals(KeyCode.ENTER))
        		btRoll.fire();
        });
		overall.getChildren().addAll(new HBox(diceToRoll,btRoll),scrolly);
		setCenter(overall);
		setOnShowing(new EventHandler<LifecycleEvent>()
		{
			@Override
			public void handle(LifecycleEvent event) 
			{
				if(Constants.getHeldDice().length > 0)
				{
					String temp = "";
					for(String s : Constants.getHeldDice())
					{
						temp += s + " ";
					}
					try 
					{
						things.getChildren().add(DiceBackend.getResult(temp));
						Constants.setHeldDice(new String[0]);
					} 
					catch (URISyntaxException e) 
					{
						things.getChildren().add(new Label("Error in getting to the dice images, sorry"));
						e.printStackTrace();
					}
				}
			}
		});
		setShowTransitionFactory(v->new RotateInTransition(v,false,Duration.seconds(1)));
	}
	@Override
    protected void updateAppBar(AppBar appBar) {
        appBar.setNavIcon(MaterialDesignIcon.MENU.button(e -> getApplication().getDrawer().open()));
        appBar.setTitleText("Dice Rolling");
        appBar.getActionItems().addAll(
        		MaterialDesignIcon.ACCOUNT_BOX.button(e->getApplication().switchView(PICK_CHARACTER)),
        		MaterialDesignIcon.BOOK.button(e->getApplication().switchView(SPELL_LIST)));
        MultiViewTest.getInstance().setSwatch(Swatch.getRandom());
    }
}
