package ke.co.neverest.mpesatopdf.utils;

import android.graphics.Color;
import java.util.Random;

public class ColorUtils
{
	public static  int[] getRandomColor() {
		Random random = new Random();
		int RGB = 0xff + 1;
		int[] colors = new int[2];
		int a = 256;
		int r1 = (int) Math.floor(Math.random() * RGB);
		int r2 = (int) Math.floor(Math.random() * RGB);
		int r3 = (int) Math.floor(Math.random() * RGB);
		colors[0] = Color.rgb(r1, r2, r3);
		if((r1 + r2 + r3) > 450) {
			colors[1] = Color.parseColor("#222222");
		}else{
			colors[1] = Color.parseColor("#ffffff");
		}
		return colors;
	}
	
}
