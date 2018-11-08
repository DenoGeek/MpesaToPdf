package ke.co.neverest.mpesatopdf;

import android.Manifest;
import android.app.*;
import android.content.pm.PackageManager;
import android.os.*;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.*;
import android.widget.*;
import java.util.Calendar;
import android.view.View.*;
import android.graphics.drawable.*;
import android.graphics.Color;
import android.content.*;
import android.text.util.Linkify;
import java.text.SimpleDateFormat;
import java.util.*;


import android.database.Cursor;
import java.text.DateFormat;
import android.net.Uri;
import android.widget.AdapterView.*;
import java.io.*;

import ke.co.neverest.mpesatopdf.models.MessageModel;
import ke.co.neverest.mpesatopdf.utils.MaterialDrawableBuilder;
import ke.co.neverest.mpesatopdf.views.ActionBar;


public class ShareActivity extends Activity
{
	private ListView options;
	private Long minDate,maxDate;
	private boolean filter;
	
	
	LinearLayout dialog ;
	private ProgressBar pb;
	private TextView info;
	@Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.share_activity);
		options=(ListView)findViewById(R.id.share_activityListView);
		
		filter=false;
		
		Bundle bundle=getIntent().getExtras();
		if(bundle!=null){
			int i = bundle.getInt("state",0);
			if(i==1){
				filter=true;
				maxDate=bundle.getLong("sdate",0);
				minDate=bundle.getLong("adate",0);
			}
			
		}

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                ) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE,}, 2);
        } else {
            // Permission has already been granted
        }
		
		
		
		dialog=(LinearLayout)findViewById(R.id.messages_fragmentLinearLayout);
		pb=(ProgressBar)findViewById(R.id.messagesfragmentProgressBar1);
		info=(TextView)findViewById(R.id.shareactivityTextView1);
		ActionBar h=(ActionBar)findViewById(R.id.share_activitycom_techshare_mtopdf_views_ActionBar);
		h.hideMany();
		h.setUp();
		OnClickListener gh=new OnClickListener(){
			@Override
			public void onClick(View v){
				int g= (int) v.getTag();
				switch(g){
					case 1:
					case 2:
					case 3:
						doBack();
					}
					
				}
			};
		h.setIconOnClickListenet(gh);
		
		configure();
		
		//generateText();
		
		}
	
		public void doBack(){
			Intent gj=new Intent(getBaseContext(),MainActivity.class);
			startActivity(gj);
		}
		
	public void configure(){

		//populate array Lisr
		List<userOptions> all=new ArrayList<userOptions>();
		userOptions hotels=new userOptions();
		hotels.optioncol=123456;
		hotels.option="Email";
		hotels.optionico= MaterialDrawableBuilder.IconValue.EMAIL_OPEN;

		userOptions events=new userOptions();
		events.optioncol=783456;
		events.option="File";
		events.optionico=MaterialDrawableBuilder.IconValue.FILE_DOCUMENT;


		userOptions gym=new userOptions();
		gym.optioncol=942538;
		gym.option="Pdf";
		gym.optionico=MaterialDrawableBuilder.IconValue.FILE_PDF;

		userOptions lib=new userOptions();
		lib.optioncol=646520;
		lib.option="Online";
		lib.optionico=MaterialDrawableBuilder.IconValue.INTERNET_EXPLORER;


		all.add(hotels);
		all.add(lib);
		//all.add(gym);
		all.add(events);
		
		//now call the adapter
		OptionsAdapter adapt=new OptionsAdapter(getApplicationContext(),all);
		options.setAdapter(adapt);
		options.setOnItemClickListener(new lister());
	}


	@Override
	protected void onPause()
	{
		// TODO: Implement this method
		super.onPause();
	}
	

	//Declare a model for the item list
	private class userOptions{
		public String option;
		public int optioncol;
		public MaterialDrawableBuilder.IconValue optionico;


	}


	private Drawable colouredDrawable(int c,Context co,MaterialDrawableBuilder.IconValue g){
		Drawable ddr=new MaterialDrawableBuilder(co)
			.setIcon(g)
			.setColor(Color.parseColor("#"+c))
			.build();
		return ddr;

	}
	private class OptionsAdapter extends ArrayAdapter<String> {
		private final Context context;
		private final List<userOptions> values;
		public OptionsAdapter(Context context, List<userOptions>
							  values) {
			super (context, R.layout.places_fragment_list_row);
			this .context = context;
			this .values = values;
		}

		@Override
		public int getCount()
		{
			// TODO: Implement this method
			return values.size();
		}


		@Override
		public View getView(final int position, View convertView,
							ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View rowView = inflater.inflate(R.layout.places_fragment_list_row,parent, false);

			TextView bar=(TextView)rowView.findViewById(R.id.placesfragmentlistrowTextView1);
			ImageView ico=(ImageView)rowView.findViewById(R.id.placesfragmentlistrowImageView1);
			TextView opt=(TextView)rowView.findViewById(R.id.placesfragmentlistrowTextView2);

			opt.setText(values.get(position).option);
			bar.setBackgroundColor(Color.parseColor("#"+values.get(position).optioncol));
			ico.setImageDrawable(colouredDrawable(values.get(position).optioncol,getContext(),values.get(position).optionico));
			return rowView;

		}
	}
	
	
	public String generateText(){
		List<MessageModel> sms=LoadSms(getApplicationContext());
			StringBuilder sb=new StringBuilder();
		for(int i=0;i<sms.size();i++){
				
				sb.append(sms.get(i).message);
				sb.append(" \n");
				sb.append("===============================");
				sb.append(" \n");
			}
		String all=sb.toString();
		//Toast.makeText(getApplicationContext(),all,Toast.LENGTH_SHORT).show();
		return all;
	}
	
	
	
	
	private class lister implements OnItemClickListener
	{

		@Override
		public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4)
		{
			// TODO: Implement this method
			
			switch(p3){
				case 0:
					
					String tet=generateText();
					Intent email = new Intent
					( Intent.ACTION_SEND );
					email. putExtra(Intent .EXTRA_EMAIL ,
									new String []{ "youremail@yahoo.com" });
					email. putExtra(Intent .EXTRA_SUBJECT , "subject" );
					email. putExtra(Intent .EXTRA_TEXT,
									tet );
					email. setType ("message/rfc822" );


					startActivity( Intent.createChooser
								  ( email, "Choose an Email client :" ));
					
					break;
				
				
				case 1:
					String te=generateText();
					
					BufferedWriter out;
					try {
						FileWriter fileWriter= new FileWriter
						(Environment.getExternalStorageDirectory().getPath()+"/mpesalog.txt");
						out = new BufferedWriter(fileWriter);
						out.write(te);
						out.close();
						info.setText("File written in your memorycard to share");
					}catch (FileNotFoundException e) {
						e.printStackTrace();
						report("error");
					}catch (IOException e) {
						e.printStackTrace();
						report("cant write");
					}
					dialog.setVisibility(View.VISIBLE);
//					final File fileToUpload = new File(Environment.getExternalStorageDirectory()+"/mpesalog.txt");
//					Ion.with(getBaseContext()).load("POST","http://koowa.co.uk/app/upload.php")
//					.uploadProgressHandler(new ProgressCallback()
//						{
//							@Override
//							public void onProgress(long uploaded, long total)
//							{
//
//								pb.setProgress((int)(uploaded/total*100));
//								System.out.println("uploaded " + (int)
//												   uploaded + " Total: "+total);
//							}
//						})
//						.setMultipartParameter("platform","android")
//						.setMultipartFile("file_toUpload", fileToUpload).asString()
//						.setCallback(new FutureCallback<String>()
//						{
//							@Override
//							public void onCompleted(Exception e, String result)
//							{
//								if(e==null){
//									info.setText("File uploaded click http://koowa.co.uk/app/upload/"+result);
//									Linkify.addLinks(info, Linkify.WEB_URLS);
//								dialog.setVisibility(View.GONE);
//								}else{
//									report("error");
//								}
//							}
//						});
			
									
					break;
				case 2:
					String text=generateText();
					
					
					try {
						FileWriter fileWriter= new FileWriter
						(Environment.getExternalStorageDirectory().getPath()+"/mpesalog.txt");
						out = new BufferedWriter(fileWriter);
						out.write(text);
						out.close();
						info.setText("File written in your memorycard to share");
					}catch (FileNotFoundException e) {
						e.printStackTrace();
						report("error");
					}catch (IOException e) {
						e.printStackTrace();
						report("cant write");
					}
			}
			
		}

		
	}
	
	
	public void report(String tx){
		Toast.makeText(getApplicationContext(),tx,Toast.LENGTH_SHORT).show();
	}
	
	
	public void createPdf(String text){

	}


	//Messages hamdlers_========
	
	public List<MessageModel> LoadSms(Context cc){

		List<MessageModel> g=new ArrayList<MessageModel> ();
		Uri message = Uri.parse("content://sms/");
		ContentResolver cr = cc.getContentResolver();
		Cursor c = cr.query(message, null, null, null, null);
		startManagingCursor(c);
		int totalSMS = c.getCount();
		if (c.moveToFirst()) {
			for (int i = 0; i < totalSMS; i++) {
				try{
					if(c.getString(c.getColumnIndexOrThrow("address")).equals("MPESA")){
						MessageModel msg=new MessageModel();
						msg.message=(c.getString(c.getColumnIndexOrThrow("body")));

						String[] tempMsg=msg.message.split(" ");
						msg.determiner= tempMsg[2];
						switch(msg.determiner){
							case "have":
								//Recieve amount
								msg.type=1;
								msg.amount=tempMsg[4];
								break;
							case "bought":
								//Buy airtime
								msg.type=2;
								msg.amount=tempMsg[3];
								break;
							case "entered":
								//wrong pin
								msg.type=3;
								break;
							case "unable":
								//unable to process sms
								msg.type=4;
								break;
							default:
								if(tempMsg[3].equals("sent")){
									//Sent or paid bill
									msg.type=5;
									msg.amount=tempMsg[2];
								}else{
									//withdrawal of monet
									msg.type=6;
									msg.amount=tempMsg[6];
								}


						}

						msg.sender=(c.getString(c.getColumnIndexOrThrow("date")));
						msg.date=c.getString(c.getColumnIndexOrThrow("date"));

						if(filter){
							if(isInLimits(Long.parseLong(msg.date))){
								g.add(msg);}
						}else{
							g.add(msg);
						}
					}
				}catch (Exception e){
					Log.e("SMS",e.getMessage());
				}

				c.moveToNext();
			}
		}
		// else {
		// throw new RuntimeException("You have no SMS");
		// }
		c.close();

		return g;
	}
	
	private int[] cols(){
		int[] c={
			Color.parseColor("#99FF00"),
			Color.parseColor("#336600"),
			Color.parseColor("#CC0033"),
			Color.parseColor("#CC0033"),
			Color.parseColor("#9900CC"),
			Color.parseColor("#3399Ff"),

		};
		return c;
	}


	public String[] typeCast(){
		String[] a={
			"Receive","Airtime","WrongPin","Error","Sent","Withdrawal"

		};
		return a;
	}

	private String getDate(long milliSeconds, String dateFormat)
    {
        // Create a DateFormatter object for displaying date in specified format.
        DateFormat formatter = new
			SimpleDateFormat(dateFormat);
        // Create a calendar object that will convert the date and time value in milliseconds to date.     
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(milliSeconds);
		return formatter.format(calendar.getTime());
    }

	private boolean isInLimits(Long ms){
		boolean r;
		r=true;
		if(ms>maxDate){
			r=false;
		} if(ms<minDate){
			r=false;
		}


		return r;
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Intent t = new Intent(getApplicationContext(),MainActivity.class);
		t.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(t);
	}
}
