package midterm2021;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.io.IOException;

public class Main extends Application {

    private int frameH = 36;
    private int frameW = 92;
    private int numFrames = 8;
    private int sourceHeightOffset = 0;
    private int sourceWidthOffset = 100;
    private int frameNum = 0;

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("CSCI2020U - Midterm");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);

//      Creating the menu buttons
        Button btApp1 = new Button("Animation");
        btApp1.setPrefWidth(200);
        Button btApp2 = new Button("2D Graphics");
        btApp2.setPrefWidth(200);
        Button btApp3 = new Button("About");
        btApp3.setPrefWidth(200);
        Button backToMain = new Button("Back to main menu");
        backToMain.setPrefWidth(200);

//        setting the Event handlers for each buttons
        btApp1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println("Clicked on Animation button");

                GridPane drawing = new GridPane();
                drawing.add(backToMain,0,0);
                Scene scene = new Scene(drawing, 800, 600);

                Canvas animate = new Canvas();
                animate.setHeight(800);
                animate.setWidth(600);

                drawing.add(animate,2,2);
                primaryStage.setTitle("Animation");
                primaryStage.setScene(scene);
                primaryStage.show();

                GraphicsContext animation = animate.getGraphicsContext2D();;

                Image image = new Image(getClass().getClassLoader().getResource("ducks.png").toString());

                Timeline timeline = new Timeline();
                timeline.setCycleCount(Timeline.INDEFINITE);
                timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(4), new EventHandler<ActionEvent>(){

                    @Override
                    public void handle(ActionEvent actionEvent) {
                        animation.setFill(Color.GREEN);
                        animation.fillRect(100,250,150,100);
                        animation.drawImage(image, sourceWidthOffset,sourceHeightOffset,frameW,frameH,100,250,150,100);
                        frameNum = (frameNum + 1) % numFrames;
                        sourceHeightOffset = frameH * frameNum;
                    }
                }));

                timeline.playFromStart();
            }
        });

        btApp2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println("Clicked on Graphics 2D button");

                GridPane drawing = new GridPane();

                drawing.add(backToMain,0,0);
                Scene scene = new Scene(drawing, 800, 600);

                Canvas initial = new Canvas();
                initial.setHeight(800);
                initial.setWidth(600);

                drawing.add(initial,1,1);
                primaryStage.setTitle("2D");
                primaryStage.setScene(scene);
                primaryStage.show();

                GraphicsContext initials = initial.getGraphicsContext2D();
                initials.setStroke(Color.BLACK);

                initials.strokeLine(0,200,150,200);
                initials.strokeLine(75,200,75,300);

                initials.strokeArc(0,260,75,75,0,-180, ArcType.OPEN);

                initials.strokeLine(200,200,350,200);
                initials.strokeLine(275,200,275,300);

                initials.strokeArc(200,260,75,75,0,-180, ArcType.OPEN);

            }
        });

        btApp3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println("Clicked on About button");
                GridPane aboutGrid = new GridPane();
                aboutGrid.add(backToMain,0,0);
                aboutGrid.setAlignment(Pos.CENTER);
                aboutGrid.setHgap(10);
                aboutGrid.setVgap(10);
                aboutGrid.setPadding(new Insets(25,25,25,25));

                Scene aboutScene = new Scene(aboutGrid,500,500);

                Label nameLabel = new Label("Name:");
                Label emailLabel = new Label("Email:");
                Label softwareDescriptionLabel = new Label("Software Desc:");

                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder = null;
                try {
                    docBuilder = dbFactory.newDocumentBuilder();
                    Document document= null;

                    document = docBuilder.parse(getClass().getClassLoader().getResource("info.xml").toString());
                    document.getDocumentElement().normalize();
                    NodeList itemNodes = document.getElementsByTagName("info");
                    for (int i = 0; i < itemNodes.getLength(); i++) {
                        Element itemElement = (Element) itemNodes.item(i);
                        String name = itemElement.getElementsByTagName("name").item(0).getTextContent();
                        String email = itemElement.getElementsByTagName("email").item(0).getTextContent();
                        String softDesc = itemElement.getElementsByTagName("software-description").item(0).getTextContent();
                        Label nameOutput = new Label(name);
                        Label emailOutput = new Label(email);
                        Label softDescOutput = new Label(softDesc);
                        aboutGrid.add(nameLabel,0,1);
                        aboutGrid.add(nameOutput,1,1);
                        aboutGrid.add(emailLabel,0,2);
                        aboutGrid.add(emailOutput,1,2);
                        aboutGrid.add(softwareDescriptionLabel, 0, 3);
                        aboutGrid.add(softDescOutput,1,3);
                    }
                    primaryStage.setScene(aboutScene);

                } catch (SAXException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }catch (ParserConfigurationException e) {
                    e.printStackTrace();
                }

            }

        });

//        Add the menu buttons to the grid
        grid.add(btApp1, 0,1);
        grid.add(btApp2, 0,2);
        grid.add(btApp3, 0,3);

        // main App Scene
        Scene mainScene = new Scene(grid, 300, 275);

        backToMain.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                primaryStage.setScene(mainScene);
            }
        });

        primaryStage.setScene(mainScene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
