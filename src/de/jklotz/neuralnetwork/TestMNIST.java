package de.jklotz.neuralnetwork;

import de.jklotz.neuralnetwork.network.Layer;
import de.jklotz.neuralnetwork.network.Neuron;
import de.jklotz.neuralnetwork.network.NeuronalNetwork;
import de.jklotz.neuralnetwork.utils.Utils;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.text.Text;
import javafx.scene.transform.Transform;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Logger;

public class TestMNIST extends Application {

    private static double[] input;
    private static NeuronalNetwork neuralNetwork;
    private static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public static void main(String[] args) {
        logger.info("Loading Neuronal Network...");

        try {
            neuralNetwork = Utils.deserialize("result/NeuronalNetwork_E18.dat");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Künstliche Intelligenz | MNIST Test");

        Canvas canvas = new Canvas(28, 28);

        final GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        initDraw(graphicsContext);

        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED,
                event -> {
                    graphicsContext.beginPath();
                    graphicsContext.moveTo(event.getX(), event.getY());
                    graphicsContext.stroke();
                });

        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED,
                event -> {
                    graphicsContext.lineTo(event.getX(), event.getY());
                    graphicsContext.stroke();
                });

        FlowPane left = new FlowPane();
        left.getChildren().add(canvas);

        GridPane right = new GridPane();
        right.setVgap(5);
        right.setHgap(5);

        Text text = new Text("Los gehts.");

        Button button = new Button("Aktualisieren");
        button.setOnAction(actionEvent -> {
            File file = new File("temp/drawnImage.png");
            File predicted = new File("temp/predictedImage.png");

            try {
                WritableImage writableImage = new WritableImage((int) canvas.getWidth(),
                        (int) canvas.getHeight());

                SnapshotParameters spa = new SnapshotParameters();
                spa.setFill(Color.WHITE);
                canvas.snapshot(spa, writableImage);

                RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                ImageIO.write(renderedImage, "png", file);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            input = new double[28 * 28];

            try {
                BufferedImage image = ImageIO.read(file);

                int w = image.getWidth();
                int h = image.getHeight();


                int a = 0;
                for (int y = 0; y < h; y++) {
                    for (int x = 0; x < w; x++) {

                        java.awt.Color pixelColor = new java.awt.Color(image.getRGB(x, y), false);
                        pixelColor = new java.awt.Color(255 - pixelColor.getRed(), 255 - pixelColor.getGreen(),
                                255 - pixelColor.getBlue());
                        image.setRGB(x, y, pixelColor.getRGB());

                        int pixelValue = new java.awt.Color(image.getRGB(x, y)).getRed();

                        input[a] = pixelValue / 255.0D;
                        a++;
                    }
                }

                ImageIO.write(image, "png", predicted);

                neuralNetwork.input(input);
                neuralNetwork.fireOutput();

                int highestIndex = -1;
                double highestOutput = -1.0;
                double[] outputs = neuralNetwork.output();
                for (int k = 0; k < outputs.length; k++) {
                    if (highestOutput < outputs[k]) {
                        highestIndex = k;
                        highestOutput = outputs[k];
                    }
                }

                logger.info(String.valueOf(highestIndex));
                text.setText("Ist deine Zahl etwa die " + highestIndex);

            } catch (IOException e) {
                e.printStackTrace();
            }

            graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            initDraw(graphicsContext);

            right.getChildren().clear();

            for (int i = 0; i < neuralNetwork.output().length; i++) {
                Text name = new Text("" + i);
                right.add(name, 0, i);

                ProgressBar bar = new ProgressBar(neuralNetwork.output()[i]);
                right.add(bar, 1, i);
            }

        });

        FlowPane middle = new FlowPane(Orientation.VERTICAL);
        middle.getChildren().add(button);

        middle.getChildren().add(text);


        right.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
        left.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
        middle.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));

        BorderPane pane = new BorderPane();
        pane.setLeft(left);
        pane.setCenter(middle);
        pane.setRight(right);

        Scene scene = new Scene(pane, 1440, 810);
        stage.setScene(scene);
        stage.show();
    }

    private void initDraw(GraphicsContext gc) {
        double canvasWidth = gc.getCanvas().getWidth();
        double canvasHeight = gc.getCanvas().getHeight();

        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, canvasWidth, canvasHeight);

        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);

        gc.setLineCap(StrokeLineCap.ROUND);
        gc.setLineJoin(StrokeLineJoin.ROUND);


    }


}
