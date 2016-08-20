package demos;
import processing.core.PApplet;
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.utils.MapUtils;
import parsing.ParseFeed;
import de.fhpotsdam.unfolding.providers.*;
import de.fhpotsdam.unfolding.providers.Google.*;

import java.util.List;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.GeoJSONReader;

import java.util.HashMap;


import de.fhpotsdam.unfolding.marker.Marker;

public class EarthquakeCityMapInfo extends PApplet
{
	private UnfoldingMap map;
	HashMap<String, Float> lifeExpMap;
	List<Feature>countries;
	List<Marker>countryMarkers;
	
	private static HashMap<String,Float>loadLifeExpectancyFromCSV( PApplet p,String fileName)
	{
		HashMap<String,Float>lifeExpMap = new HashMap<String, Float>();
		//Get lines of csv
		String[] rows = p.loadStrings(fileName);
		for(String row: rows)
		{
			String[] columns = row.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
			for(int i=columns.length-1;i>3;i--)
			{
				if(!columns.equals(".."))
				{
					Float column = Float.parseFloat(columns[i]);
					lifeExpMap.put(columns[3], column);
					break;
				}
			}
			
		}
		 
		return lifeExpMap;
	}
	private void shadeCountries() {
		for (Marker marker : countryMarkers) {
			// Find data for country of the current marker
			String countryId = marker.getId();
			System.out.println(lifeExpMap.containsKey(countryId));
			if (lifeExpMap.containsKey(countryId)) {
				float lifeExp = lifeExpMap.get(countryId);
				// Encode value as brightness (values range: 40-90)
				int colorLevel = (int) map(lifeExp, 40, 90, 10, 255);
				marker.setColor(color(255-colorLevel, 100, colorLevel));
			}
			else {
				marker.setColor(color(150,150,150));
			}
		}
	}
	public void setup()
	{
		size(1000,800,OPENGL);
		map = new UnfoldingMap(this, 50, 50, width, 700, new Google.GoogleMapProvider());
		MapUtils.createDefaultEventDispatcher(this, map);
		//Load loadLifeExpectancyFromCSV data
		lifeExpMap = ParseFeed.loadLifeExpectancyFromCSV(this, "LifeExpectancyWorldBank.csv");
		
		// Load country polygons and adds them as markers
		countries = GeoJSONReader.loadData(this, "countries.geo.json");
		countryMarkers = MapUtils.createSimpleMarkers(countries);
		map.addMarkers(countryMarkers);
		shadeCountries();
	}
	public void draw()
	{
		background(0,0,0);
		map.draw();
		
	}	
}
