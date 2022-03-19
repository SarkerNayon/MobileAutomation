package pages;

import org.openqa.selenium.By;

import base.BaseUtils;
import io.appium.java_client.MobileBy;

public class GoogleMaps extends BaseUtils{

	By searchBox = MobileBy.xpath("//android.widget.EditText[@content-desc=\"Search here\"]/android.widget.TextView");
public String searchBoxButton() {
	return getText(searchBox);
	
}

}
