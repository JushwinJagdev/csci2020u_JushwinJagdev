package sample;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.ArrayList;

public class Controller {
    @FXML
    Canvas canvas;
    private GraphicsContext gc;
    private double max = 0.0;
    public void initialize() {
        gc = canvas.getGraphicsContext2D();
        ArrayList<Double> appleList = downloadStockPrices("AAPL");
        ArrayList<Double> googleList = downloadStockPrices("GOOG");
        drawLinePlot(appleList, googleList);
    }

    private ArrayList<Double> downloadStockPrices(String stockTicker) {
        ArrayList<Double> priceList = new ArrayList<>();
        String URL = "https://query1.finance.yahoo.com/v7/finance/download/ticker?period1=1262322000&period2=1451538000&interval=1mo&events=history&includeAdjustedClose=true";
        try {
            boolean addLine = false;
            String line;
            InputStream file = new URL(URL.replace("ticker", stockTicker)).openStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(file, StandardCharsets.UTF_8));

            while ((line = reader.readLine()) != null) {
                if (addLine == true) {
                    double price = Double.parseDouble(line.split(",")[4]);
                    priceList.add(price);
                    if (price > max) {
                        max = price;
                    }
                }
                addLine = !addLine;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return priceList;
    }
    
    private void plotLine(ArrayList<Double> ticketPrices) {
        double offsetX = (canvas.getWidth() - 100) / ticketPrices.size();
        double x1 = 0.0;
        for (int i = 0; i < ticketPrices.size() - 1; i++) {
            double firstRatio = ticketPrices.get(i) / max;
            double firstRelativePrice = firstRatio * (canvas.getHeight() - 100);
            double secondRatio = ticketPrices.get(i + 1) / max;
            double secondRelativePrice = secondRatio * (canvas.getHeight() - 100);
            double y1 = (canvas.getHeight() - 100) - firstRelativePrice + 50;
            double x2 = x1 + offsetX + 50;
            double y2 = (canvas.getHeight() - 100) - secondRelativePrice + 50;
            gc.strokeLine(x1 + 50, y1, x2, y2);
            x1 += offsetX;
        }
    }

    private void drawLinePlot(ArrayList<Double> firstTickerPrices, ArrayList<Double> secondTickerPrices) {
        gc.setStroke(Color.BLACK);
        gc.strokeLine(50, 50, 50, canvas.getHeight() - 50);
        gc.strokeLine(50, canvas.getHeight() - 50, canvas.getWidth() - 50, canvas.getHeight() - 50);
        gc.setStroke(Color.BLUE);
        plotLine(firstTickerPrices);
        gc.setStroke(Color.RED);
        plotLine(secondTickerPrices);
    }
}