import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class Homework4 extends Application {
	private Scene welcomeScene;
	public static void main(String[] args) {
		launch(args);
	}

	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {
		//Set Window title
		primaryStage.setTitle("SCP Foundation Info Kiosk");
		//Will use this image twice so declare it here
		Image scpFoundationImage = new Image("file:src/main/resources/scp_logo_int.png");
		//Give window an icon
		primaryStage.getIcons().add(scpFoundationImage);
		
		//Give a logo at welcome screen
		ImageView foundationLogo = new ImageView(scpFoundationImage);
		//Let the user know what they are using
		Label welcomeMessage = new Label("Welcome to the SCP Foundation\nYou are using Info Kiosk #07\nPlease select your status");
		welcomeMessage.setTextAlignment(TextAlignment.CENTER); //Make text centered
		//Give the user 2 buttons to pick what login they need
		Button guestButton = new Button("Guest Login");
		Button agentButton = new Button("Agent Login");
		HBox loginHBox = new HBox(guestButton, agentButton);
		loginHBox.setAlignment(Pos.CENTER); //Center the horizontal button box
		loginHBox.setSpacing(30); //Give the buttons some space

		//Vbox to arrange everything vertically
		VBox welcomeVBox = new VBox(foundationLogo, welcomeMessage, loginHBox);
		welcomeVBox.setAlignment(Pos.CENTER); //Center the vertical box
		welcomeVBox.setSpacing(30);//Give the elements some space between eachother

		//Create our welcome scene
		welcomeScene = new Scene(welcomeVBox, 700,700);
		
		//Set the welcome scene on stage and display it
		primaryStage.setScene(welcomeScene);
		primaryStage.show();
	}

}
