package com.example.demo;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.media.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Main extends Application {
    private Stage window;
    private final double W = 1200.0 , H = 675.0;
    private double score = 0, fails = 0;
    private int sec, min, flag = 0;
    private Label time;
    private final String path = "C:\\Users\\user\\Documents\\demo\\src\\main\\java\\com\\example\\demo\\lobby-classic-game.mp3";

    private String[] textFiledAns = new String[10000];
    private String[] testAnswer = new String[10000];

    private ArrayList<Question> description = new ArrayList<>();
    private String[] options = new String[10000];
    private int test = 0 , fill = 0;


    public BorderPane ending(){
        BorderPane borderPane = new BorderPane();
        ImageView Ending = new ImageView("C:\\Users\\user\\Documents\\demo\\src\\main\\java\\com\\example\\demo\\Ending2.jpg");
        Ending.setFitWidth(640);
        Ending.setFitHeight(360);

        for ( int i = 0 ; i < description.size() ; ++i ){
            if ( textFiledAns[i] != null && textFiledAns[i].equals(Quiz.FillInAnswerQuiz[i])){
                score++;
            }else if ( textFiledAns[i] != null && !textFiledAns[i].equals(description.get(i).getAnswer())){
                fails++;
            }
            if ( testAnswer[i] != null && testAnswer[i].equals(description.get(i).getAnswer())){
                score++;
            }else if ( testAnswer[i] != null && !testAnswer[i].equals(description.get(i).getAnswer())){
                fails++;
            }
//            System.out.println(testAnswer[i] + " " + description.get(i).getAnswer());
//            System.out.println(textFiledAns[i] + " " + description.get(i).getAnswer());
        }

        Label Result = new Label("Your Result:");
        Result.setFont(Font.font("Times New Roman",FontWeight.BOLD, FontPosture.REGULAR,32));

        Label Percentage = new Label();
        double ans = (100*score)/(description.size());
        Percentage.setText(ans + "%");
        Percentage.setFont(Font.font(20));

        Label CorrectAns = new Label();
        CorrectAns.setText("Correct Answers: " + (int)score + "/" + (int)(description.size()));
        CorrectAns.setFont(Font.font(20));

        Label Time = new Label();
        Time.setText("Finished in: " + time.getText());

        Button ShowAns = kahootButton("Show answers","blue");
        Button CloseTest = kahootButton("Close Test", "red");
        CloseTest.setOnAction(e -> window.close());

        borderPane.setTop( new StackPane(new VBox(new StackPane(Result), new StackPane(Percentage), new StackPane(CorrectAns), new StackPane(Time) , new StackPane(ShowAns), new StackPane(CloseTest))));
        borderPane.setBottom((new StackPane(Ending)));

        return borderPane;
    }

    private String timeFormat(int sec){
        min = sec / 60;
        sec = sec % 60;
        String strMin = min + "";
        if ( min < 10 ) strMin = "0" + strMin;
        String strSec = sec + "";
        if ( sec < 10 ) strSec = "0" + strSec;

        return strMin + ":" + strSec;
    }

    public BorderPane currentQuestion(int ind){
        BorderPane borderPane = new BorderPane();
        Timeline timeline = new Timeline();
        Label lblTimer = new Label();

        Label lbl = new Label(description.get(ind).getDescription());
        lbl.setFont(Font.font("Times New Roman",FontWeight.BOLD, FontPosture.ITALIC,26));
        lbl.setWrapText(true);

        //timer
        lblTimer.setText(timeFormat(sec));
        timeline.setCycleCount(Timeline.INDEFINITE);
        if ( flag == 0 ) {
            timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), actionEvent ->
                    lblTimer.setText(timeFormat(sec++))));
            flag++;
        }
        else {
            timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), actionEvent ->
                    lblTimer.setText(timeFormat(sec))));
        }
        timeline.play();
        time = lblTimer;

        Button btnNext = new Button(">>");
        Button btnPrev = new Button("<<");

        borderPane.setTop(new VBox(new StackPane(lbl), new StackPane(lblTimer)));
        borderPane.setRight(new StackPane(btnNext));
        borderPane.setLeft(new StackPane(btnPrev));

        btnNext.setMinHeight(50); btnPrev.setMinHeight(50);
        btnNext.setMinWidth(100); btnPrev.setMinWidth(100);

        if ( ind == 0 ) btnPrev.setVisible(false);
        if ( ind == description.size()-1 ) btnNext.setVisible(false);

        String question = lbl.toString();
        if ( question.contains("________") ){
            ImageView FillInImg = new ImageView("C:\\Users\\user\\Documents\\demo\\src\\main\\java\\com\\example\\demo\\FillIn.png");
            Label typeAns = new Label("Type your answer here:");
            typeAns.setFont(Font.font("Times New Roman",FontWeight.BOLD,FontPosture.REGULAR,20));

            FillInImg.setFitWidth(800);
            FillInImg.setFitHeight(450);

            TextField textField = new TextField(textFiledAns[ind]);
            textField.setPrefColumnCount(5);

            borderPane.setCenter(new StackPane(new VBox(new StackPane(FillInImg), new StackPane(typeAns) , new StackPane(textField))));

            btnNext.setOnAction(e -> {
                window.setScene(new Scene(currentQuestion(ind+1),W,H));
                textFiledAns[ind] = textField.getText();
            });
            btnPrev.setOnAction(e -> {
                window.setScene(new Scene(currentQuestion(ind-1),W,H));
                textFiledAns[ind] = textField.getText();
            });
        }else{
            ImageView Kahoot = new ImageView("C:\\Users\\user\\Documents\\demo\\src\\main\\java\\com\\example\\demo\\Kahoot_main.png");
            borderPane.setCenter(Kahoot);

            GridPane gridPane = new GridPane();
            ToggleGroup tg = new ToggleGroup();
            RadioButton redBtn = kahootRadioButton(Quiz.options[ind][0], "red");
            RadioButton blueBtn = kahootRadioButton(Quiz.options[ind][1], "blue");
            RadioButton greenBtn = kahootRadioButton(Quiz.options[ind][2],"green");
            RadioButton yellowBtn = kahootRadioButton(Quiz.options[ind][3],"orange");

            redBtn.setToggleGroup(tg);
            blueBtn.setToggleGroup(tg);
            greenBtn.setToggleGroup(tg);
            yellowBtn.setToggleGroup(tg);

            if (Quiz.options[ind][0].equals(testAnswer[ind])){redBtn.fire();}
            if (Quiz.options[ind][1].equals(testAnswer[ind])){blueBtn.fire();}
            if (Quiz.options[ind][2].equals(testAnswer[ind])){greenBtn.fire();}
            if (Quiz.options[ind][3].equals(testAnswer[ind])){yellowBtn.fire();}

            gridPane.add(redBtn,0,0);
            gridPane.add(blueBtn,0,1);
            gridPane.add(greenBtn,1,0);
            gridPane.add(yellowBtn,1,1);

            redBtn.setOnAction(e -> testAnswer[ind] = Quiz.options[ind][0]);
            blueBtn.setOnAction(e -> testAnswer[ind] = Quiz.options[ind][1]);
            greenBtn.setOnAction(e -> testAnswer[ind] = Quiz.options[ind][2]);
            yellowBtn.setOnAction(e -> testAnswer[ind] = Quiz.options[ind][3]);

            borderPane.setBottom(gridPane);

            btnNext.setOnAction(e -> window.setScene(new Scene(currentQuestion(ind+1),W,H)));
            btnPrev.setOnAction(e -> window.setScene(new Scene(currentQuestion(ind-1),W,H)));
        }

        if ( ind == description.size()-1 ){
            Button finish = new Button("Finish!");
            finish.setMinHeight(50);
            finish.setMinWidth(100);
            finish.setOnAction(e -> {
                window.setScene(new Scene(ending(),W,H));
            });
            borderPane.setRight(new StackPane(finish));
        }
        return borderPane;
    }

    public RadioButton kahootRadioButton(String text, String color) {
        RadioButton radioButton = new RadioButton(text);
        radioButton.setMinWidth(600); radioButton.setMinHeight(100);
        radioButton.setStyle("-fx-background-color: " + color);
        radioButton.setTextFill(Color.WHITE);
        radioButton.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 18));
        return radioButton;
    }
    public Button kahootButton(String text, String color) {
        Button button = new Button(text);
        button.setMinWidth(600); button.setMinHeight(90);
        button.setStyle("-fx-background-color: " + color);
        button.setTextFill(Color.WHITE);
        button.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 18));
        return button;
    }

    public StackPane chooseFile() throws FileNotFoundException {
        StackPane stackPane = new StackPane();
        Quiz quiz = new Quiz();
        Button chooseBtn = new Button("Choose a file");
        ImageView ChooseKahoot = new ImageView("C:\\Users\\user\\Documents\\demo\\src\\main\\java\\com\\example\\demo\\Kahoot2.jpg");
        stackPane.getChildren().addAll(ChooseKahoot,chooseBtn);

        chooseBtn.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(window);
            try {
                description = (ArrayList<Question>)quiz.loadFromFile(file.getPath());
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
            window.setScene(new Scene(currentQuestion(0),W,H));
        });

        return stackPane;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Media media = new Media(new File(path).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setCycleCount(Timeline.INDEFINITE);

        window = primaryStage;
        window.setScene(new Scene(chooseFile(),W,H));
        window.setTitle("Project 2");
        window.show();
    }


    /*
    @Override
    public void start(Stage primaryStage) throws Exception {
        Button redButton = kahootButton("Red", "red");
        Button orangeButton = kahootButton("Orange", "orange");

        VBox vBox1 = new VBox(3);
        vBox1.getChildren().addAll(redButton, orangeButton);

        Button blueButton = kahootButton("Blue", "blue");
        Button greenButton = kahootButton("Green", "green");
        VBox vBox2 = new VBox(3);
        vBox2.getChildren().addAll(blueButton, greenButton);

        HBox hBox = new HBox(3);
        hBox.getChildren().addAll(vBox1, vBox2);
        hBox.setMaxWidth(500);
        hBox.setMaxHeight(100);

        Label txtLabel = new Label("Question description");
        Font font = Font.font("Times New Roman", FontWeight.BOLD,
                FontPosture.ITALIC, 18);
        txtLabel.setFont(font);
        txtLabel.setMinWidth(500);
        txtLabel.setAlignment(Pos.CENTER);

        BorderPane mainPane = new BorderPane();
        hBox.setAlignment(Pos.CENTER);
        mainPane.setTop(txtLabel);
        mainPane.setBottom(hBox);

        BorderPane.setMargin(hBox, new Insets(0, 0, 10, 0));
        primaryStage.setScene(new Scene(mainPane, 500, 400));
        primaryStage.setTitle("JavaFX");
        primaryStage.show();
    }*/
}
