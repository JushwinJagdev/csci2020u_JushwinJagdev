package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.Group;

public class Main extends Application {
    private static double[] avgHousePricesByYear = {
            247381.0,264171.4,287715.3,294736.1,
            308431.4,322635.9,340253.0,363153.7
    };

    private static double[] avgCommercialPricesByYear = {
            1121585.3,1219479.5,1246354.2,1295364.8,
            1335932.6,1472362.0,1583521.9,1613246.3
    };

    public static String[] ageGroups = {
            "18-25", "26-35", "36-45", "46-55", "56-65", "65+"
    };
    public static int[] purchasesByAgeGroup = {
            648, 1021, 2453, 3173, 1868, 2247
    };
    public static Color[] pieColours = {
            Color.AQUA, Color.GOLD, Color.DARKORANGE,
            Color.DARKSALMON, Color.LAWNGREEN, Color.PLUM
    };

    private Canvas canvas;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Group root = new Group();
        Scene scene = new Scene(root, 900, 500, Color.WHITE);

        this.canvas = new Canvas();
        this.canvas.heightProperty().bind(primaryStage.heightProperty());
        this.canvas.widthProperty().bind(primaryStage.widthProperty());

        root.getChildren().add(canvas);
        primaryStage.setTitle("Lab 6 Graphs");
        primaryStage.setScene(scene);
        primaryStage.show();

        drawBarChart(new barIncrement(avgHousePricesByYear, Color.RED), new barIncrement(avgCommercialPricesByYear, Color.BLUE));
        drawPieChart(pieColours, purchasesByAgeGroup);
    }

    public static class barIncrement {
        public double[] values;
        public Color color;
        public String label;

        public barIncrement() {
            this.values = new double[0];
            this.color = Color.WHITE;
        }
        public barIncrement(double[] values, Color color) {
            this.values = values;
            this.color = color;
        }
        public barIncrement(double[] values, Color color, String label) {
            this.values = values;
            this.color = color;
            this.label = label;
        }
        public String getLabel() {
            return label;
        }
        public void setLabel(String label) {
            this.label = label;
        }
        public Color getColor() {
            return color;
        }
        public void setColor(Color color) {
            this.color = color;
        }
        public double[] getValues() {
            return values;
        }
        public void setValues(double[] values) {
            this.values = values;
        }
    }

    private void drawPieChart(Color[] color, int[] purchases) {
        GraphicsContext graphic = canvas.getGraphicsContext2D();
        int total = 0;
        double eAng = 0;
        double sAng = 0;

        for (int purchase : purchases) {
            total += purchase;
        }
        double arcs = Math.pow(total, -1) * 360;
        System.out.println(purchases.length);
        for (int i = 0; i < purchases.length; i++) {
            graphic.setFill(color[i]);
            eAng = (purchases[i] * arcs);
            graphic.fillArc(420,  50, 325, 325, sAng, eAng, ArcType.ROUND);
            sAng += eAng;
        }
    }


    private void drawBarChart(barIncrement... bar) {
        GraphicsContext graphic = canvas.getGraphicsContext2D();

        double max = Double.NEGATIVE_INFINITY;
        double min = 0.0;
        int len = 0;

        for (barIncrement b : bar) {
            if (b.values.length > len) {
                len = b.values.length;
            }
            for (double x : b.values) {
                if (x > max) {
                    max = x;
                }
                if (x < min) {
                    min = x;
                }
            }
        }

        double jump =(325 - 2 * 10)/(bar.length * (len + 2));

        for (int i = 0; i < bar.length; i++) {
            graphic.setFill(bar[i].color);
            double[] arr = bar[i].values;
            double x = 50 + jump*i + 10/2;

            for (double v : arr) {
                double height = ((v - min) / (max - min)) * 325;
                graphic.strokeRect(x, 50 + 325 - height, jump, height);
                graphic.fillRect(x, 50 + 325 - height, jump, height);
                x += jump * bar.length + 10;
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}