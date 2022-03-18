import java.io.File;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
//TODO: Header comment
//TODO: Tests
//TODO: Make new textfiles for the program
//TODO: PDF Explaining Proxy Design Pattern
public class Homework4 extends Application {
	//Scenes
	private Scene welcomeScene;
	private Scene guestScene = null;
	private Scene agentScene = null;
	private Scene fileDisplayScene = null;
	private Scene chosenScene = null;//Save chosen scene to return to it later
	//Data Members
	private static File f = new File("./src/main/resources/textfiles");
    private static String[] directoryFiles = f.list();
	private Label fileName;
	private Label fileContents;
	private boolean isOfficial = false;
	//Constants
	private final String BAUHAUS_FONT = "file:src/main/resources/bauhaus-bold-bt.ttf";
	private final BackgroundImage BG_IMAGE = new BackgroundImage(new Image("file:src/main/resources/images/background.png"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
	private final Background CARBON_BG = new Background(BG_IMAGE);
	private final String SET_TEXT_WHITE = "-fx-text-fill: white";
	private double standardWidth;
	private double standardHeight;

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
		scpTitleLabel.setFont(Font.loadFont(BAUHAUS_FONT, 75));
		scpTitleLabel.setStyle(SET_TEXT_WHITE);
		scpTitleLabel.setTextAlignment(TextAlignment.CENTER); //Make text centered
		Label scpStatementLabel = new Label("Secure|Contain|Protect");
		scpStatementLabel.setStyle(SET_TEXT_WHITE);
		scpStatementLabel.setFont(Font.loadFont(BAUHAUS_FONT, 24));
		VBox scpVBox = new VBox(scpTitleLabel, scpStatementLabel);
		scpVBox.setAlignment(Pos.CENTER);
		Label kioskMessage = new Label("You are using Info Kiosk #07\nPlease select a login option!");
		kioskMessage.setStyle(SET_TEXT_WHITE);
		//Give the user 2 buttons to pick what login they need
		Button guestButton = new Button("Guest Login");
		Button agentButton = new Button("Agent Login");
		HBox loginHBox = new HBox(guestButton, agentButton);
		for (Node hButton : loginHBox.getChildren()) {
			Button ourbutton = (Button)hButton;
			ourbutton.setStyle("-fx-background-color: #4b92db; -fx-background-radius: 10px; -fx-padding: 10 10 10 10; -fx-text-fill: #ffffff; -fx-border-color: #ffffff; -fx-border-width: 2; -fx-border-radius: 10px");
			ourbutton.setFont(Font.loadFont(BAUHAUS_FONT, 18));
		}
		loginHBox.setAlignment(Pos.CENTER); //Center the horizontal button box
		loginHBox.setSpacing(15); //Give the buttons some space

		//Vbox to arrange everything vertically
		VBox welcomeVBox = new VBox(foundationLogo, scpVBox, kioskMessage, loginHBox);
		welcomeVBox.setAlignment(Pos.CENTER); //Center the vertical box
		welcomeVBox.setSpacing(30);//Give the elements some space between eachother
		welcomeVBox.setBackground(CARBON_BG);
		welcomeVBox.setPadding(new Insets(30,30,30,30));

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
			confirmationStage.getIcons().addAll(primaryStage.getIcons());
			confirmationStage.setTitle("You wouldn't lie, right?");
			confirmationStage.setResizable(false);
			Label confirmationLabel = new Label("Are you sure you're an agent? Our password detector is broken so please tell us the truth.");
			confirmationLabel.setWrapText(true);
			Button confirmButton = new Button("YES");
			confirmButton.setOnAction(event->{isOfficial = true;confirmationStage.close();});
			Button denyButton = new Button("NO");
			denyButton.setOnAction(event->{isOfficial = false;confirmationStage.close();});
			HBox optionButtonsHBox = new HBox(confirmButton, denyButton);
			VBox confirmationVBox = new VBox(confirmationLabel, optionButtonsHBox);
			
			//Make it pretty
			confirmationVBox.setAlignment(Pos.CENTER);
			optionButtonsHBox.setAlignment(Pos.CENTER);
			confirmationVBox.setSpacing(10);
			optionButtonsHBox.setSpacing(10);
			confirmationLabel.setTextAlignment(TextAlignment.CENTER);
			confirmationVBox.setBackground(CARBON_BG);
			confirmationLabel.setStyle("-fx-text-fill: #ffffff; -fx-padding: 0 10 0 10");
			confirmationLabel.setFont(Font.loadFont(BAUHAUS_FONT, 14));
			for (Node n : optionButtonsHBox.getChildren()) {
				Button b = (Button)n;
				b.setStyle("-fx-background-color: #4b92db; -fx-background-radius: 10px; -fx-padding: 0 10 0 10; -fx-text-fill: #ffffff; -fx-border-color: #ffffff; -fx-border-width: 2; -fx-border-radius: 10px;");
				b.setFont(Font.loadFont(BAUHAUS_FONT, 20));
			}

			Scene confirmationScene = new Scene(confirmationVBox, 280, 100);
			confirmationStage.setScene(confirmationScene);
			confirmationStage.showAndWait();


			if (isOfficial) { //Make sure its an official!
				if (agentScene == null)
					agentScene = createFetcherScene(primaryStage);
				primaryStage.setScene(agentScene);
			}
		});

		//Create our welcome scene
		welcomeScene = new Scene(welcomeVBox);
		//Set the welcome scene on stage and display it
		primaryStage.setScene(welcomeScene);
		primaryStage.show();
		//Save Scene size to use elsewhere
		standardWidth = welcomeScene.getWidth();
		standardHeight = welcomeScene.getHeight();
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
		
		VBox fileButtonVBox = new VBox();
		for (String file : directoryFiles) {
			Button fileButton = new Button(file);
			fileButtonVBox.getChildren().add(fileButton);
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
		VBox fetcherVBox = new VBox(returnToLoginButton, availableFilesLabel, fileButtonVBox);
		//Make it look good
		fileButtonVBox.setAlignment(Pos.CENTER);
		fileButtonVBox.setSpacing(5);
		fetcherVBox.setAlignment(Pos.CENTER);
		fetcherVBox.setBackground(CARBON_BG);
		for (Node n : fileButtonVBox.getChildren()) {
			Button fButton = (Button)n;
			fButton.setPrefWidth(300);
			fButton.setStyle("-fx-background-color: #303030; -fx-background-radius: 10px; -fx-padding: 10 10 10 10; -fx-text-fill: #ffffff; -fx-border-color: #ffffff; -fx-border-width: 2; -fx-border-radius: 10px;");
			fButton.setFont(Font.loadFont(BAUHAUS_FONT, 18));
		}
		returnToLoginButton.setStyle("-fx-background-color: #4b92db; -fx-background-radius: 10px; -fx-padding: 10 10 10 10; -fx-text-fill: #ffffff; -fx-border-color: #ffffff; -fx-border-width: 2; -fx-border-radius: 10px;");
		returnToLoginButton.setFont(Font.loadFont(BAUHAUS_FONT, 18));

		Scene fetcherScene = new Scene(fetcherVBox, standardWidth, standardHeight);
		return fetcherScene;
	}

	private Scene createFileDisplayScene(Stage givenStage) {
		Button returnButton = new Button("Return");
		returnButton.setOnAction(e->givenStage.setScene(chosenScene));
		fileName = new Label("Filename.");
		fileContents = new Label("File Contents.");
		//Put file contents in a vbox to avoid it touching edges of window
		HBox fileContentsHBox = new HBox(fileContents);
		VBox fileDisplayVBox = new VBox(returnButton, fileName, fileContentsHBox);
		
		//Make it look good
		fileDisplayVBox.setAlignment(Pos.CENTER);
		fileDisplayVBox.setBackground(CARBON_BG);
		fileDisplayVBox.setSpacing(10);
		fileContents.setStyle("-fx-background-color: #303030; -fx-text-fill: #ffffff; -fx-padding: 0 10 0 10; -fx-font-size: 20;");
		fileName.setFont(Font.loadFont(BAUHAUS_FONT, 20));
		fileName.setStyle("-fx-text-fill: #ffffff");
		returnButton.setStyle("-fx-background-color: #4b92db; -fx-background-radius: 10px; -fx-padding: 10 10 10 10; -fx-text-fill: #ffffff; -fx-border-color: #ffffff; -fx-border-width: 2; -fx-border-radius: 10px;");
		returnButton.setFont(Font.loadFont(BAUHAUS_FONT, 18));
		fileContentsHBox.setPadding(new Insets(0, 10, 0, 10));
		fileContentsHBox.setAlignment(Pos.CENTER);

		Scene fileDisplayScene = new Scene(fileDisplayVBox, standardWidth, standardHeight);
		return fileDisplayScene;
	}

	private void displayFile(String givenName, String givenContents) {
		fileName.setText(givenName);
		fileContents.setText(givenContents);
		fileContents.setWrapText(true);
	}

}
