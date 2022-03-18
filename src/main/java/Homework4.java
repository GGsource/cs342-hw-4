import java.io.File;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
//TODO: Header comment
//TODO: Tests
//TODO: Make new textfiles for the program
public class Homework4 extends Application {
	private Scene welcomeScene;
	private Scene guestScene = null;
	private Scene agentScene = null;
	private Scene fileDisplayScene = null;
	//Save the chosen scene to return to it and see more files
	private Scene chosenScene = null;

	private static File f = new File("./src/main/resources/textfiles");
    private static String[] directoryFiles = f.list();
	private Label fileName;
	private Label fileContents;
	private boolean isOfficial = false;

	public static void main(String[] args) {
		launch(args);
	}

	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {
		//Set Window title
		primaryStage.setTitle("SCP Foundation Info Kiosk");
		//Will use this image twice so declare it here
		Image scpFoundationImage = new Image("file:src/main/resources/images/scp_logo_int.png");
		//Give window an icon
		primaryStage.getIcons().add(scpFoundationImage);
		
		//Give a logo at welcome screen
		ImageView foundationLogo = new ImageView(scpFoundationImage);
		//Let the user know what they are using
		Label scpTitleLabel = new Label("SCP Foundation");
		scpTitleLabel.setFont(Font.loadFont("file:src/main/resources/bauhaus-bold-bt.ttf", 75));
		Label scpStatementLabel = new Label("Secure|Contain|Protect");
		scpStatementLabel.setFont(Font.loadFont("file:src/main/resources/bauhaus-bold-bt.ttf", 24));
		VBox scpVBox = new VBox(scpTitleLabel, scpStatementLabel);
		scpVBox.setAlignment(Pos.CENTER);
		Label kioskMessage = new Label("You are using Info Kiosk #07\nPlease select a login option!");
		scpTitleLabel.setTextAlignment(TextAlignment.CENTER); //Make text centered
		//Give the user 2 buttons to pick what login they need
		Button guestButton = new Button("Guest Login");
		Button agentButton = new Button("Agent Login");
		HBox loginHBox = new HBox(guestButton, agentButton);
		loginHBox.setAlignment(Pos.CENTER); //Center the horizontal button box
		loginHBox.setSpacing(15); //Give the buttons some space

		//Vbox to arrange everything vertically
		VBox welcomeVBox = new VBox(foundationLogo, scpVBox, kioskMessage, loginHBox);
		welcomeVBox.setAlignment(Pos.TOP_CENTER); //Center the vertical box
		welcomeVBox.setSpacing(30);//Give the elements some space between eachother

		//Deal with button presses
		guestButton.setOnAction(e->{
			if (guestScene == null) {
				guestScene = createFetcherScene(primaryStage);
			}
			primaryStage.setScene(guestScene);
		});
		agentButton.setOnAction(e->{
			Stage confirmationStage = new Stage();
			confirmationStage.initModality(Modality.APPLICATION_MODAL);
			confirmationStage.initOwner(primaryStage);
			Label confirmationLabel = new Label("Are you sure you're an agent? our password detector is broken so please tell us the truth.");
			Button confirmButton = new Button("YES");
			confirmButton.setOnAction(event->{isOfficial = true;confirmationStage.close();});
			Button denyButton = new Button("NO");
			denyButton.setOnAction(event->{isOfficial = false;confirmationStage.close();});
			HBox optionButtonsHBox = new HBox(confirmButton, denyButton);
			VBox confirmationVBox = new VBox(confirmationLabel, optionButtonsHBox);
			Scene confirmationScene = new Scene(confirmationVBox, 300, 200);
			confirmationStage.setScene(confirmationScene);
			confirmationStage.showAndWait();

			if (isOfficial) { //Make sure its an official!
				if (agentScene == null)
					agentScene = createFetcherScene(primaryStage);
				primaryStage.setScene(agentScene);
			}
		});

		//Create our welcome scene
		welcomeScene = new Scene(welcomeVBox, 700,600);
		//Set the welcome scene on stage and display it
		primaryStage.setScene(welcomeScene);
		primaryStage.show();
	}

	private Scene createFetcherScene(Stage givenStage) {
		FileFetcher fetcher;
		if (isOfficial)
			fetcher = new ProxyOfficialFetcher();
		else
			fetcher = new ProxyCivillianFetcher();
		
		Button returnToLoginButton = new Button("Return to Login Screen");
		returnToLoginButton.setOnAction(e->{
			isOfficial = false; //reset incase user was previously logged in as official
			givenStage.setScene(welcomeScene);
		});
		
		HBox fileButtonHBox = new HBox();
		for (String file : directoryFiles) {
			Button fileButton = new Button(file);
			fileButtonHBox.getChildren().add(fileButton);
			fileButton.setOnAction(e->{
				String fileContents;
				try {
					fileContents = fetcher.fetchFile(file);
					if (fileDisplayScene == null)
						fileDisplayScene = createFileDisplayScene(givenStage);
					displayFile(file, fileContents);
					chosenScene = givenStage.getScene(); //Save scene to switch back later
					givenStage.setScene(fileDisplayScene);
				}
				catch (Exception er) {
					Alert accessError = new Alert(AlertType.ERROR);
					accessError.initOwner(givenStage);
					accessError.getDialogPane().setContentText(er.getMessage());
					accessError.getDialogPane().setHeaderText("ACCESS ERROR!");
					accessError.showAndWait();
				}
			});
		}
		Label availableFilesLabel = new Label("Available Files:");
		VBox fetcherVBox = new VBox(returnToLoginButton, availableFilesLabel, fileButtonHBox);
		Scene fetcherScene = new Scene(fetcherVBox, 700, 600);
		return fetcherScene;
	}

	private Scene createFileDisplayScene(Stage givenStage) {
		Button returnButton = new Button("Return");
		returnButton.setOnAction(e->givenStage.setScene(chosenScene));
		fileName = new Label("Filename.");
		fileContents = new Label("File Contents.");
		VBox fileDisplayVBox = new VBox(returnButton, fileName, fileContents);
		Scene fileDisplayScene = new Scene(fileDisplayVBox, 700, 600);
		return fileDisplayScene;
	}

	private void displayFile(String givenName, String givenContents) {
		fileName.setText(givenName);
		fileContents.setText(givenContents);
		fileContents.setWrapText(true);
	}

}
