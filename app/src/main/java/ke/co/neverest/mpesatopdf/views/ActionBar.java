package ke.co.neverest.mpesatopdf.views;

import android.view.*;
import android.widget.*;
import android.content.Context;
import android.util.AttributeSet;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.Color;

import ke.co.neverest.mpesatopdf.R;
import ke.co.neverest.mpesatopdf.utils.MaterialDrawableBuilder;


public class ActionBar extends LinearLayout
{
	public Drawable icon;
	public String titletext,subtitle;
	public int height;
	private LinearLayout extra;
	private ImageView a,up;
	private TextView title;
	private ActionItem menu,menu1,menu2;
	public ActionBar(Context context, AttributeSet attrs){
		super(context, attrs);
		//paint object for drawing in onDraw


		//at this point try fetching custom attributes
		TypedArray a = context.getTheme().obtainStyledAttributes
		(attrs,
		 R.styleable.myActionBar, 0, 0);


		try {
			//get the text and colors specified using thenames in attrs.xml
			titletext = a.getString(R.styleable.myActionBar_actiontitle);
		    icon=a.getDrawable(R.styleable.myActionBar_icone);
			} finally {
			a.recycle();
		}
				if(icon==null){
					icon=context.getResources().getDrawable(R.drawable.ico);
				}
				if(titletext==null){
					titletext=context.getResources().getString(R.string.app_name);
				}
				
				height= (int)context.getResources
					().getDimension(R.dimen.action_bar_height);
				configure(context);
		}
		
		public void setIconOnClickListenet(OnClickListener t){
			title.setOnClickListener(t);
			up.setOnClickListener(t);
			a.setOnClickListener(t);
			menu.setOnClickListener(t);
			menu1.setOnClickListener(t);
		}
		
		
		public void configure(Context context){
			//some operations on the Linear Layout
	
			this.setOrientation(LinearLayout.HORIZONTAL);
			this.setGravity(Gravity.CENTER_VERTICAL);
			this.setPadding(0,0,0,0);
			Drawable upd=new MaterialDrawableBuilder(context)
				.setIcon(MaterialDrawableBuilder.IconValue.MENU)
				.setColor(Color.WHITE)
				.build();
			up=new ImageView(context);
			LayoutParams uppar=new LayoutParams(height,height);
			up.setLayoutParams(uppar);
			up.setImageDrawable(upd);
			up.setPadding(0,5,0,5);
			up.setTag(0);
			//this.addView(up);
			
			 a=new ImageView(context);
			LayoutParams ipar=new LayoutParams(height,height);
			a.setLayoutParams(ipar);
			a.setImageDrawable(icon);
			a.setPadding(0,5,5,5);
			a.setTag(1);
			this.addView(a);
			
			
			
			
			 title=new TextView(context);
			LayoutParams titlepars=new LayoutParams(LayoutParams.WRAP_CONTENT,height);
			title.setTextSize(16);
			title.setTextColor(Color.WHITE);
			title.setLayoutParams(titlepars);
			title.setText(titletext);
			title.setGravity(Gravity.CENTER_VERTICAL);
			title.setTag(2);
			this.addView(title);
			
			 extra=new LinearLayout(context);
			LayoutParams extrapars=new LayoutParams(LayoutParams.FILL_PARENT,height);
			extra.setLayoutParams(extrapars);
			
			Drawable dir=new MaterialDrawableBuilder(context)
				.setIcon(MaterialDrawableBuilder.IconValue.SHARE)
				.setColor(Color.WHITE)
				.build();
			Drawable dr=new MaterialDrawableBuilder(context)
			.setIcon(MaterialDrawableBuilder.IconValue.FILTER)
			.setColor(Color.WHITE)
			.build();
			menu=new ActionItem(context,dr);
			menu.setTag(3);
			extra.addView(menu);
			
			
			menu1=new ActionItem(context,dir);
			menu1.setTag(4);
			extra.addView(menu1);
			
			extra.setGravity(Gravity.RIGHT |Gravity.CENTER_VERTICAL);
			this.addView(extra);
		}
		
		public void setAppIco(Drawable d){
			icon=d;
			configure(getContext());
		}
		
		
		private class ActionItem extends ImageView{
		private int iheight=(int)height/5*3;
		
			public ActionItem(Context context,Drawable icon){
				super(context);
				
				this.setImageDrawable(icon);
				LayoutParams ipar=new LayoutParams(iheight,iheight);
				ipar.setMargins(5,0,15,0);
				this.setLayoutParams(ipar);
				
				}
				
			public void setIco(Drawable d){
				this.setImageDrawable(d);
			}
			
		}
		
		public int getCountChild(){ 
			return this.getChildCount()+1;
		}
		
		public void updateSecondIcon(int page){
			
			switch(page){
				
				case 1:
					Drawable dr=new MaterialDrawableBuilder(getContext())
						.setIcon(MaterialDrawableBuilder.IconValue.REFRESH)
						.setColor(Color.WHITE)
						.build();
						menu1.setIco(dr);
					break;
				case 2:
					Drawable drr=new MaterialDrawableBuilder(getContext())
						.setIcon(MaterialDrawableBuilder.IconValue.MESSAGE)
						.setColor(Color.WHITE)
						.build();
					menu1.setIco(drr);
					break;
				case 3:
					Drawable ddr=new MaterialDrawableBuilder(getContext())
						.setIcon(MaterialDrawableBuilder.IconValue.MAGNIFY)
						.setColor(Color.WHITE)
						.build();
					menu1.setIco(ddr);
					break;
				
			}
			
			
			
			
		}
		
	public void hideMany(){
		extra.setVisibility(View.GONE);
	}
	
	public void setUp(){
		Drawable dr=new MaterialDrawableBuilder(getContext())
			.setIcon(MaterialDrawableBuilder.IconValue.ARROW_LEFT_BOLD_CIRCLE_OUTLINE)
			.setColor(Color.WHITE)
			.build();
		a.setImageDrawable(dr);
	}
}
