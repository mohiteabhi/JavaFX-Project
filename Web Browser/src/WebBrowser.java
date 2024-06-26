import java.util.ArrayList;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class WebBrowser extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    private ArrayList<BrowserWindow> openWindows;
    private Rectangle2D screenRect;
    private double locationX, locationY;
    private double windowWidth, windowHeight;
    private int untitledCount;


    public void start(Stage stage) {
        openWindows = new ArrayList<BrowserWindow>(); // List of
        screenRect = Screen.getPrimary().getVisualBounds();
        locationX = screenRect.getMinX() + 30;
        locationY = screenRect.getMinY() + 20;


        windowHeight = screenRect.getHeight() - 160;
        windowWidth = screenRect.getWidth() - 130;
        if (windowWidth > windowHeight * 1.6) {
            windowWidth = windowHeight * 1.6;
        }

        newBrowserWindow("https://www.anonymouscontent.com/privacy-policy/");

    }

    ArrayList<BrowserWindow> getOpenWindowList() {
        return openWindows;
    }

    int getNextUntitledCount() {
        return ++untitledCount;
    }

    void newBrowserWindow(String url) {
        BrowserWindow window = new BrowserWindow(this, url);
        openWindows.add(window);

        window.setOnHidden(e -> {
            openWindows.remove(window);
            System.out.println("Number of open windows is " + openWindows.size());
            if (openWindows.size() == 0) {
                System.out.println("Program will end because all windows have been closed");
            }
        });
        if (url == null) {
            window.setTitle("Untitled " + getNextUntitledCount());
        }
        window.setX(locationX); // set location and size of the window

        window.setY(locationY);
        window.setWidth(windowWidth);
        window.setHeight(windowHeight);
        window.show();
        locationX += 30; // set up location of NEXT window
        locationY += 20;
        if (locationX + windowWidth + 10 > screenRect.getMaxX()) {
            locationX = screenRect.getMinX() + 30;
        }
        if (locationY + windowHeight + 10 > screenRect.getMaxY()) {
            locationY = screenRect.getMinY() + 20;
        }
    }

}
