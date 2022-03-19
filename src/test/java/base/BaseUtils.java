package base;

import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.connection.ConnectionStateBuilder;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static io.appium.java_client.touch.WaitOptions.waitOptions;


public class BaseUtils extends DriverManager {


    protected List<MobileElement> elementList(By by){
        return androidDriver.findElements(by);
    }

    // Android only, will not work for ios
    public void scrollAndClick(String visibleText) {
        androidDriver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\"" + visibleText + "\").instance(0))").click();
    }

    public void scrollDown() {
        TouchAction action = new TouchAction(androidDriver);
        Dimension size = androidDriver.manage().window().getSize();
        int width = size.width;
        int height = size.height;
        int middleOfX = width / 2;
        int startYCoordinate = (int) (height * .7);
        int endYCoordinate = (int) (height * .2);

        action.press(PointOption.point(middleOfX, startYCoordinate))
                .waitAction(waitOptions(Duration.ofSeconds(2)))
                .moveTo(PointOption.point(middleOfX, endYCoordinate)).release().perform();
    }

    public void scrollUp() {
        TouchAction action = new TouchAction(androidDriver);
        Dimension size = androidDriver.manage().window().getSize();
        int width = size.width;
        int height = size.height;
        int middleOfX = width / 2;
        int startYCoordinate = (int) (height * .2);
        int endYCoordinate = (int) (height * .7);

        action.press(PointOption.point(middleOfX, startYCoordinate))
                .waitAction(waitOptions(Duration.ofSeconds(2)))
                .moveTo(PointOption.point(middleOfX, endYCoordinate)).release().perform();
    }

    public static void swipeLeft() {
        Dimension dimensions = androidDriver.manage().window().getSize();
        int Anchor = androidDriver.manage().window().getSize().getHeight() / 2;
        Double screenWidthStart = dimensions.getWidth() * 0.8;
        int scrollStart = screenWidthStart.intValue();
        Double screenWidthEnd = dimensions.getWidth() * 0.2;
        int scrollEnd = screenWidthEnd.intValue();

        new TouchAction(androidDriver)
                .press(PointOption.point(scrollStart, Anchor))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
                .moveTo(PointOption.point(scrollEnd, Anchor))
                .release().perform();
    }

    public static void swipeRight() {
        Dimension dimensions = androidDriver.manage().window().getSize();
        int Anchor = androidDriver.manage().window().getSize().getHeight() / 2;
        Double screenWidthStart = dimensions.getWidth() * 0.2;
        int scrollStart = screenWidthStart.intValue();
        Double screenWidthEnd = dimensions.getWidth() * 0.8;
        int scrollEnd = screenWidthEnd.intValue();

        new TouchAction(androidDriver)
                .press(PointOption.point(scrollStart, Anchor))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
                .moveTo(PointOption.point(scrollEnd, Anchor))
                .release().perform();
    }

    public void seekBar(By by, double percentile){
        MobileElement seek_bar=androidDriver.findElement(by);
        int startX=seek_bar.getLocation().getX();
        int y=seek_bar.getLocation().getY();
        int endX=seek_bar.getSize().getWidth();

        TouchAction action=new TouchAction(androidDriver);
        int moveTo=(int)(endX * percentile);
        action.press(PointOption.point(startX, y)).moveTo(PointOption.point(moveTo,y)).release().perform();
    }

    protected void tapWithText(String text, By by) {
        List<MobileElement> elements = androidDriver.findElements(by);
        for (MobileElement s : elements) {
            if (s.getText().equals(text)) {
                s.click();
            }
        }
    }

    protected void tapWithCoordinates(int x, int y){
        TouchAction touchAction = new TouchAction(androidDriver);
        touchAction.tap(PointOption.point(x, y))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
                .perform();

    }

    protected String getAllElementText(By by) {
        String s = "";
        List<MobileElement> elements = androidDriver.findElements(by);
        for (MobileElement el : elements) {
            s = el.getText();
            System.out.println(s);
        }
        return s;
    }

    protected List<String> getAllElementTextInList(By by) {
        List<String> list = new ArrayList<>();
        List<MobileElement> elements = androidDriver.findElements(by);
        for (MobileElement el : elements) {
            list.add(el.getText());
        }
        System.out.println(list);
        return list;
    }

    public void setWifiOff() {
        if (androidDriver.getConnection().isWiFiEnabled()) {
            androidDriver.setConnection(new ConnectionStateBuilder().withWiFiDisabled().build());
        }
    }

    public void setWifiOn() {
        if (!androidDriver.getConnection().isWiFiEnabled()) {
            androidDriver.setConnection(new ConnectionStateBuilder().withWiFiEnabled().build());
        }
    }

    //revisit
    protected void checkBatteryStatus() {
       androidDriver.executeScript("mobile:batteryInfo");
    }

    protected void openNotification() {
        androidDriver.openNotifications();
    }

    protected String getText(By by) {
        MobileElement element = androidDriver.findElement(by);
        return element.getText();
    }

    //revisit
    protected void tapJs(By by){
        MobileElement element = androidDriver.findElement(by);
        HashMap<String, Integer> points = new HashMap<>();
        points.put("x", element.getLocation().getX());
        points.put("y", element.getLocation().getY());
        androidDriver.executeScript("mobile: tap", points);
    }

    protected void tapOnElement(By by){
        MobileElement  element = androidDriver.findElement(by);
        element.click();
    }

    //revisit
    public void scrollToElement( String elementName, boolean scrollDown) {
        String listID = (androidDriver.findElementByAndroidUIAutomator("new UiSelector().className(\"android.widget.ListView\")")).getId();
        String direction;
        if (scrollDown) {
            direction = "down";
        } else {
            direction = "up";
        }
        HashMap<String, String> scrollObject = new HashMap<>();
        scrollObject.put("direction", direction);
        scrollObject.put("element", listID);
        scrollObject.put("text", elementName);
        androidDriver.executeScript("mobile: scrollTo", scrollObject);
    }

    protected MobileElement scrollToElementByName(String elementName, String listId) {
        return androidDriver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()" +
                ".resourceId(\"" + listId + "\"))" +
                ".scrollIntoView(new UiSelector().text(\"" + elementName + "\"));");
    }


    //*** Wait actions *****//
    public void staticWait(int second){
        try {
            Thread.sleep(second * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    protected void waitForElement(By element) {
        WebDriverWait wait = new WebDriverWait(androidDriver, 10);
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(element));
    }

}
