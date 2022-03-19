package tests;

import org.testng.annotations.Test;

import base.BaseTest;
import pages.GoogleMaps;

public class GoogleMapsTest extends BaseTest{
GoogleMaps gMaps = new GoogleMaps();

	
	@Test
	public void openMaps() {
		gMaps.searchBoxButton();
		System.out.println("Passed");
	}
}
