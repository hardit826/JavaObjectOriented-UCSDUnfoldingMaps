package demos;
import java.applet.Applet;
import java.awt.Canvas;

import de.fhpotsdam.unfolding.texture.PAppletFactory;
import  processing.core.*;

public class Tester12 extends PApplet
{
	private String URL = "https://www.lonelyplanet.com/travel-blog/tip-article/wordpress_uploads/2015/05/Ksamil-beach-Albania_cs.jpg";
	private PImage background;
	public void setup()
	{
		
		size(500,500);
		background = loadImage(URL, "jpg");
	}
	public void draw()
	{
		background.resize(width, height);
		image(background, 0,0);
		//fill(255,209,0);
		int[] color = sunColorSec(second());
		fill(color[0],color[1],color[2]);
		ellipse(110,110,width/20,height/20);
		ellipse(140,110,width/20,height/20);
		ellipse(width/4,height/4,width/5,height/5);
		arc(125, 140, 50, 50, 0, PI);
	}
	public int[] sunColorSec (float seconds)
	{
		int[] rgb = new int[3];
		float diffFrom30 = Math.abs(30-seconds);
		float ratio = diffFrom30/30;
		rgb[0]= (int)(255*ratio);
		rgb[1]= (int)(255*ratio);
		rgb[2]= 0;
		return rgb;
	}
	
}
