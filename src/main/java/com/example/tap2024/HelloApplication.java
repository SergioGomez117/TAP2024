package com.example.tap2024;

import com.example.tap2024.vistas.Calculadora;
import com.example.tap2024.vistas.Memorama;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    private MenuBar mnbPrincipal;
    private Menu menParcial1, menParcial2, menSalir;
    private MenuItem mitCalculadora, mitMemorama, mitSalir;
    private BorderPane bdpPanel;
    private Calculadora calculadoraStage;
    private Memorama memoryGame;

    @Override
    public void start(Stage stage) {
        CrearMenu();

        bdpPanel = new BorderPane();
        bdpPanel.setTop(mnbPrincipal);
        Scene scene = new Scene(bdpPanel, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/estilos/main.css").toString());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    private void CrearMenu() {
        mitCalculadora = new MenuItem("Calculadora");
        mitCalculadora.setOnAction(actionEvent -> showCalculadora());

        mitMemorama = new MenuItem("Memorama");
        mitMemorama.setOnAction(actionEvent -> showMemorama());
        menParcial1 = new Menu("Primer Parcial");
        menParcial1.getItems().addAll(mitCalculadora);
        menParcial1.getItems().addAll(mitMemorama);

        menParcial2 = new Menu("Segundo Parcial");

        // Salir
        mitSalir = new MenuItem("Salir");
        menSalir = new Menu("Salir");
        menSalir.getItems().add(mitSalir);
        mitSalir.setOnAction(event -> System.exit(0));

        mnbPrincipal = new MenuBar();
        mnbPrincipal.getMenus().addAll(menParcial1, menParcial2, menSalir);
    }

    private void showCalculadora() {
        if (calculadoraStage == null) {
            calculadoraStage = new Calculadora(this);
            calculadoraStage.show();
            mitCalculadora.setDisable(true);
        } else {
            calculadoraStage.toFront();
        }
    }

    private void showMemorama() {
        if (memoryGame == null) {
            memoryGame = new Memorama();
            memoryGame.start(new Stage());
        } else {
            memoryGame.getPrimaryStage().toFront();
        }
    }

    public void updateMenuBar() {
        mitCalculadora.setDisable(false);
    }

    public static void main(String[] args) {
        launch();
    }
}