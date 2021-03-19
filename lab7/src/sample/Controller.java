package sample;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Collection;
import java.util.TreeMap;

public class Controller {
    @FXML Canvas mainCanvas;
    @FXML GraphicsContext graphics;
    @FXML

    private TreeMap<String, Integer> warnings = new TreeMap<>();
    private static Color[] colors = {
            Color.AQUA, Color.GOLD, Color.DARKORANGE, Color.DARKSALMON
    };
    public void initialize() {
        graphics = mainCanvas.getGraphicsContext2D();
        countWarnings("weatherwarnings-2015.csv");
        drawPieChart();
    }

    private void countWarnings(String fileName) {
        String line;
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            while ((line = br.readLine()) != null) {
                String[] columns = line.split(",");
                String warningType = columns[5];
                if (warnings.containsKey(warningType)) {
                    int oldValue = warnings.get(warningType);
                    warnings.replace(warningType, oldValue + 1);
                }
                else {
                    warnings.put(warningType, 2);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void drawPieChart() {
        int numOfWarningTypes = warnings.keySet().size();
        double[] angle = new double[numOfWarningTypes];
        int incr = 0;
        double startAngle = 0.0;
        int total = 0;
        double x = 100;
        double y = 0.0;
        for (String warningType : warnings.keySet()) {
            total += warnings.get(warningType);
        }
        for (String warningType : warnings.keySet()) {
            angle[incr] = ((double) warnings.get(warningType) / (double) total) * 360;
            incr++;
        }
        incr = 0;
        for (String warningType : warnings.keySet()) {
            graphics.setFill(colors[incr]);
            graphics.fillText(warningType, x + 60, y + 20);
            graphics.fillRect(x, y, 50, 30);
            y += 40;
            graphics.fillArc(400,0.0,250,250, startAngle, angle[incr], ArcType.ROUND);
            startAngle += angle[incr];
            incr++;
        }
    }

}