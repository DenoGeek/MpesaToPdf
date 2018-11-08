package ke.co.neverest.mpesatopdf.fragments;

import android.support.v4.app.Fragment;
import android.content.*;
import android.util.Log;
import android.view.*;
import android.os.*;
import java.util.*;
import android.widget.*;
import java.util.*;
import android.net.Uri;
import android.database.Cursor;
import android.graphics.Color;
import java.text.DateFormat;
import java.util.Calendar;
import java.text.SimpleDateFormat;

import ke.co.neverest.mpesatopdf.R;
import ke.co.neverest.mpesatopdf.models.MessageModel;


public class MessagesFragment extends Fragment
{
	private List<MessageModel> all;
	private ListView mslist;
	private MessageAdapter ad;
	private Long minDate,maxDate;
	private boolean filter;
	public static Fragment newInstance(Context context) {
		MessagesFragment f = new MessagesFragment();
		
		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		ViewGroup root = (ViewGroup) inflater.inflate(R.layout.messages_fragment, null);
		mslist=(ListView)root.findViewById(R.id.messagesfragmentListView1);
		
		filter=false;
		Bundle bundle = this.getArguments();
		if (bundle != null) {
			int i = bundle.getInt("state",0);
			if(i==1){
				filter=true;
				maxDate=bundle.getLong("sdate",0);
				minDate=bundle.getLong("adate",0);
			}
		}
		
		all=LoadSms();
		ad=new MessageAdapter(getActivity(),all);
		mslist.setAdapter(ad);
		
		return root;
		
		
		}
		
	public List<MessageModel> LoadSms(){

		List<MessageModel> g=new ArrayList<MessageModel> ();
		Uri message = Uri.parse("content://sms/");
		ContentResolver cr = getActivity().getContentResolver();
		Cursor c = cr.query(message, null, null, null, null);
		getActivity().startManagingCursor(c);
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

							case "is":
								msg.type=7;
								break;
							case "Loan":
								msg.type=8;
								break;

							case "do":
								msg.type=9;
								break;
							case "Your":
								msg.type=10;
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
					c.moveToNext();
				}catch (Exception e){
					Log.e("Sms",e.getMessage());
				}
			}
		}
		// else {
		// throw new RuntimeException("You have no SMS");
		// }
		c.close();
		
		return g;
	}
	

	
	private class MessageAdapter extends ArrayAdapter<String> {
		private final Context context;
		private final List<MessageModel> values;
		public MessageAdapter(Context context, List<MessageModel> values) {
			super (context, R.layout.messages_fragment_row);
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
			View rowView = inflater.inflate(R.layout.messages_fragment_row, parent, false);
			TextView bar=(TextView)rowView.findViewById(R.id.messagesfragmentrowTextView1);
			TextView msg=(TextView)rowView.findViewById(R.id.messagesfragmentrowTextView2);
			TextView determiner=(TextView)rowView.findViewById(R.id.messagesfragmentrowTextView3);
			TextView amt=(TextView)rowView.findViewById(R.id.messagesfragmentrowTextView4);
			TextView date=(TextView)rowView.findViewById(R.id.messagesfragmentrowTextView5);
			
			bar.setBackgroundColor(cols()[values.get(position).type-1]);
			msg.setText(values.get(position).message);
			amt.setText(values.get(position).amount);
			//date.setText(values.get(position).date);
			date.setText(getDate(Long.parseLong(values.get(position).date),"dd/MM/yyyy"));
			determiner.setText(typeCast()[values.get(position).type-1]);
			return rowView;
			}
		}
		
		private int[] cols(){
			int[] c={
				Color.parseColor("#99FF00"),
				Color.parseColor("#336600"),
				Color.parseColor("#CC0033"),
				Color.parseColor("#CC0033"),
				Color.parseColor("#9900CC"),
				Color.parseColor("#3399Ff"),
				Color.parseColor("#258152"),
				Color.parseColor("#524518"),
				Color.parseColor("#9900CC"),
				Color.parseColor("#626725"),
			};
			return c;
		}
		
		
		public String[] typeCast(){
			String[] a={
				"Receive","Airtime","WrongPin","Error","Sent","Withdrawal","Deficit","Loan","Deficit","Pending"
				
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
		
		
}

