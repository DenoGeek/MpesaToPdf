package ke.co.neverest.mpesatopdf;

import android.app.*;
import android.os.*;
import android.view.*;
import android.graphics.*;
import android.widget.*;
import com.easyandroidanimations.library.*;
import android.content.Intent;
import ke.co.neverest.mpesatopdf.R;

public class SplashActivity extends Activity
{

	private TextView y,x;
	private ImageView v;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		 super.onCreate(savedInstanceState);
		 setContentView(R.layout.intro);
		Typeface bloody = Typeface.createFromAsset(getApplicationContext().getAssets(),"Bloodthirsty.ttf");	
		Typeface nice = Typeface.createFromAsset(getApplicationContext().getAssets(),"fonts/Walkway_Bold.ttf");
		y=(TextView)findViewById(R.id.introTextView1);
		
		
		v=(ImageView)findViewById(R.id.introImageView1);

		x=(TextView)findViewById(R.id.introTextView2);
		new BounceAnimation(y).setDuration(2300).animate();

		new Handler().postDelayed(new
			Runnable(){
				@Override
				public void run() {
					
					new PuffOutAnimation(v)
						.setListener(new AnimationListener(){
							@Override
							public void onAnimationEnd(Animation go){

								Intent g=new Intent(getApplicationContext(),MainActivity.class);
								g.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
								startActivity(g);


							}
						}).
						animate();
				}
			},3000);
		x.setTypeface(nice);	
		y.setTypeface(bloody);	
		
		 
	}
	
	
	
	@Override
	protected void onPause() {
		super .onPause();
		 // Sqlite.report("paused",getApplicationContext());
		//Log . d (msg , "The onPause() event" );
		finish();
	}
	

}
