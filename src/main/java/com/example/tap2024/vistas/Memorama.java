package com.example.tap2024.vistas;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Memorama extends Application {
    private Stage primaryStage;
    private static final int ROWS = 4;
    private static final int COLUMNS = 5;

    private GridPane gridPane = new GridPane();
    private int[][] cards;
    private List<Integer> cardValues = new ArrayList<>();
    private List<ImageView> cardImageViews = new ArrayList<>();
    private int flips = 0;
    private int firstCardRow = -1;
    private int firstCardColumn = -1;
    private int matches = 0;
    private Timeline timeline;

    @Override
    public void start(Stage primaryStage) {
        initializeCards();
        initializeGridPane();

        Scene scene = new Scene(gridPane);
        primaryStage.setTitle("Memorama");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void initializeCards() {
        for (int i = 1; i <= (ROWS * COLUMNS) / 2; i++) {
            cardValues.add(i);
            cardValues.add(i);
        }

        Collections.shuffle(cardValues);
        cards = new int[ROWS][COLUMNS];
    }

    private void initializeGridPane() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                ImageView cardImageView = createCardImageView(i, j);
                gridPane.add(cardImageView, j, i);
                cardImageViews.add(cardImageView);
            }
        }
    }

    private ImageView createCardImageView(int row, int column) {
        ImageView imageView = new ImageView(new Image("/imagenes/back.jpg"));
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);

        imageView.setOnMouseClicked(event -> handleCardClick(row, column));

        return imageView;
    }

    private void handleCardClick(int row, int column) {
        if (timeline != null && timeline.getStatus() == Timeline.Status.RUNNING) {
            return; // No permitir clics durante las animaciones
        }

        flips++;

        int cardValue = cardValues.get(row * COLUMNS + column);
        cards[row][column] = cardValue;

        ImageView cardImageView = cardImageViews.get(row * COLUMNS + column);
        cardImageView.setImage(new Image("/imagenes/" + cardValue + ".JPG"));

        if (flips % 2 == 1) {
            // Primer clic
            firstCardRow = row;
            firstCardColumn = column;
        } else {
            // Segundo clic
            if (cards[row][column] == cards[firstCardRow][firstCardColumn]) {
                // Coinciden, se quedan destapadas
                matches++;
                if (matches == ROWS * COLUMNS / 2) {
                    showGameWonAlert();
                }
            } else {
                // No coinciden, se voltean nuevamente
                timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
                    hideCard(row, column);
                    hideCard(firstCardRow, firstCardColumn);
                }));
                timeline.play();
            }
        }
    }

    private void hideCard(int row, int column) {
        cards[row][column] = 0;
        ImageView cardImageView = cardImageViews.get(row * COLUMNS + column);
        cardImageView.setImage(new Image("/imagenes/back.jpg"));
    }

    private void showGameWonAlert() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("¡Felicidades!");
        alert.setHeaderText(null);
        alert.setContentText("Has ganado el juego.");

        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public Stage getPrimaryStage() {
        return primaryStage;

    }
}