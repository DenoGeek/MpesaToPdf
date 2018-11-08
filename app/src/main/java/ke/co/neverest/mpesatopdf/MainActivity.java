package ke.co.neverest.mpesatopdf;

import android.Manifest;
import android.app.*;
import android.content.pm.PackageManager;
import android.os.*;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.*;
import android.widget.*;
import java.util.Calendar;
import android.content.Intent;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View.*;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;

import ke.co.neverest.mpesatopdf.fragments.MessagesFragment;
import ke.co.neverest.mpesatopdf.views.ActionBar;


public class MainActivity extends FragmentActivity
{
    /** Called when the activity is first created. */
	private LinearLayout bottomsheet;
	private Button Start,Stop;
	int year,month,day,tv;
	private int fstate;
	private long adate,sdate;
    @Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		bottomsheet=(LinearLayout)findViewById(R.id.mainLinearLayout);
		Start=(Button)findViewById(R.id.mainButton);
		Stop=(Button)findViewById(R.id.mainButton1);
		
		Start.setOnClickListener(new botcli());
		Stop.setOnClickListener(new botcli());
		Calendar calendar = Calendar. getInstance();
		 year = calendar .get (Calendar. YEAR);
		 month = calendar .get (Calendar .MONTH);
		 day = calendar .get( Calendar.DAY_OF_MONTH );
		fstate=0;
		adate=0;
		sdate=0;
		
		//Set onclick to actionbar;
		ActionBar g=(ActionBar) findViewById(R.id.maincom_techshare_jkufy_views_ActionBar);
		OnClickListener gh=new OnClickListener(){
			@Override
			public void onClick(View v){
				int g= (int) v.getTag();
				switch(g){
					case 3:
						if(bottomsheet.getVisibility()==View.VISIBLE){
							bottomsheet.setVisibility(View.GONE);
						}else{
							bottomsheet.setVisibility(View.VISIBLE);
						}
						
						break;
					case 4:
					Intent gj=new Intent(getBaseContext(),ShareActivity.class);
					gj.putExtra("state",fstate);
					gj.putExtra("sdate",sdate);
					gj.putExtra("adate",adate);
					gj.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(gj);
				}
			}
		};
		g.setIconOnClickListenet(gh);


        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS}, 2);
        } else {
            // Permission has already been granted
            setPage(0);
        }
		

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 2: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    setPage(0);
                } else {
                    Toast.makeText(getApplicationContext(),"Sms permission is required so give it",Toast.LENGTH_SHORT);
                }
                return;
            }

        }
    }
	
	
	public void setPage(int page){
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		switch(page){
			case 0:
				MessagesFragment  msfrag = new MessagesFragment();
				Bundle bundle = new Bundle();
				bundle.putInt("state", fstate);
				msfrag.setArguments(bundle);
				
				ft.setCustomAnimations(R.anim.slideleft, R.anim.slideright);
				ft.replace(R.id.mainFrameLayout, msfrag,"fragment");
				// Start the animated transition.
				ft.commit();
				
				break;
			
		}
		
	}
	
	@Override
	protected void onResume() {
		super .onResume();
		//Log . d (msg , "The onResume() event" );
	}

	@Override
	protected void onPause()
	{
		// TODO: Implement this method
		super.onPause();
	}
	@Override
	protected Dialog onCreateDialog (int id ) {
// TODO Auto -generated method stub
		if (id == 999) {
			return new DatePickerDialog ( this, myDateListener , year , month, day );
		}
		return null ;
	}
	
		
	private class botcli implements OnClickListener
	{
	
		@Override
		public void onClick(View p1)
		{
			//showDate ( year , month+ 1, day );
			
			// TODO: Implement this method
			switch(p1.getId()){
				case R.id.mainButton:
					tv=0;
					break;
				
				case R.id.mainButton1:
					tv=1;
					break;
			}
			
			showDialog(999);
		}

		
	
	}
	
	private DatePickerDialog .OnDateSetListener myDateListener = new
	DatePickerDialog .OnDateSetListener () {
		@Override
		public void onDateSet (DatePicker arg0 , int arg1 , int arg2 , int arg3 ) {
			// TODO Auto - generated method stub
			// arg1 = year
			// arg2 = month
			// arg3 = day
			showDate (arg1 , arg2 +1, arg3 );
		}
	};
	
	
	private void showDate(int c, int b, int a){
		
		if(tv==0){
			Start.setText(""+a+"/"+b+"/"+c);
			adate=milliseconds(""+c+"-"+b+"-"+a);
		}
		else{
			Stop.setText(""+a+"/"+b+"/"+c);
			sdate=milliseconds(""+c+"-"+b+"-"+a);
		}
		performFilter();
		
		
	}
	
	private void performFilter(){
		if(adate!=0){
			if(sdate!=0){
				fstate=1;
				FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
				//Toast.makeText(getApplicationContext(),""+adate+sdate,Toast.LENGTH_SHORT).show();
				MessagesFragment  msfrag = new MessagesFragment();
				Bundle bundle = new Bundle();
				bundle.putInt("state", fstate);
				bundle.putLong("adate",adate);
				bundle.putLong("sdate",sdate);
				msfrag.setArguments(bundle);

				ft.setCustomAnimations(R.anim.slideleft, R.anim.slideright);
				ft.replace(R.id.mainFrameLayout, msfrag,"fragment");
				// Start the animated transition.
				ft.commit();
				
				
			}
		}
		
		
	}
	
	public long milliseconds(String date)     {
        //String date_ = date;
        SimpleDateFormat sdf = new
			SimpleDateFormat("yyyy-MM-dd");
        try
        {
            Date mDate = sdf.parse(date);
            long timeInMilliseconds = mDate.getTime();
            System.out.println("Date in milli :: " + timeInMilliseconds);
            return timeInMilliseconds;
        }
        catch (ParseException e)         {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;
    }
	
}
