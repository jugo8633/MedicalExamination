package com.medici.app.controller;

import com.medici.app.R;
import com.medici.app.model.Type;

import android.app.Activity;
import android.content.Context;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public abstract class ExaminationListController
{
	private ListView				listView	= null;
	private LayoutInflater			inflater	= null;
	private ExaminationListAdapter	listAdapter	= null;

	public ExaminationListController(Activity activity, int nListResId)
	{
		super();
		listAdapter = new ExaminationListAdapter();
		inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		listView = (ListView) activity.findViewById(nListResId);
	}

	protected abstract void onItemSelected(int nPosition);

	protected void updateList()
	{
		if (null != listView)
		{
			listView.setAdapter(listAdapter);
		}
	}

	protected int addItem(int nIconResId, String strTitle, String strPatientDo, String strCaretakerDo,
			int nResIdPatientDoImage)
	{
		return listAdapter.addItem(nIconResId, strTitle, strPatientDo, strCaretakerDo, nResIdPatientDoImage);
	}

	private class ExaminationListAdapter extends BaseAdapter
	{
		class ItemData
		{
			public int		mnIconId				= Type.INVALID;
			public String	mstrTitle				= null;
			public String	mstrPatientDo			= null;
			public String	mstrCaretakerDo			= null;
			public int		mnResIdPatientDoImage	= Type.INVALID;

			public ItemData(int nIconId, String strTitle, String strPatientDo, String strCaretakerDo,
					int nResIdPatientDoImage)
			{
				mnIconId = nIconId;
				mstrTitle = strTitle;
				mstrPatientDo = strPatientDo;
				mstrCaretakerDo = strCaretakerDo;
				mnResIdPatientDoImage = nResIdPatientDoImage;
			}
		}

		private SparseArray<ItemData>	listItem	= null;

		public ExaminationListAdapter()
		{
			listItem = new SparseArray<ItemData>();
		}

		@Override
		public int getCount()
		{
			return listItem.size();
		}

		@Override
		public Object getItem(int position)
		{
			return position;
		}

		@Override
		public long getItemId(int position)
		{
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent)
		{
			ImageView imageView = null;
			TextView textView = null;

			View view = inflater.inflate(R.layout.medical_test_item, null);
			RelativeLayout layoutIconBackgroun = (RelativeLayout) view
					.findViewById(R.id.RelativeLayoutTestIconBackgroun);
			imageView = (ImageView) view.findViewById(R.id.imageViewTestIcon);

			setLayoutBackground(layoutIconBackgroun, position);
			imageView.setImageResource(listItem.get(position).mnIconId);

			textView = (TextView) view.findViewById(R.id.textViewTitle);
			setTitle(textView, listItem.get(position).mstrTitle);

			imageView = (ImageView) view.findViewById(R.id.imageViewPatient);
			if (Type.INVALID != listItem.get(position).mnResIdPatientDoImage)
			{
				imageView.setImageResource(listItem.get(position).mnResIdPatientDoImage);
			}
			else
			{
				imageView.setVisibility(View.GONE);
			}

			textView = (TextView) view.findViewById(R.id.textViewPatientOption);
			textView.setText(listItem.get(position).mstrPatientDo);

			textView = (TextView) view.findViewById(R.id.textViewCaretakerOption);
			textView.setText(listItem.get(position).mstrCaretakerDo);

			view.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					onItemSelected(position);
				}
			});

			LinearLayout linearMain = (LinearLayout) view.findViewById(R.id.LinearLayoutTextMain);
			linearMain.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View v)
				{
					onItemSelected(position);
				}
			});

			return view;
		}

		public int addItem(int nIconId, String strTitle, String strPatientDo, String strCaretakerDo,
				int nResIdPatientDoImage)
		{
			listItem.append(listItem.size(), new ItemData(nIconId, strTitle, strPatientDo, strCaretakerDo,
					nResIdPatientDoImage));
			return (listItem.size() - 1);
		}

		private void setTitle(TextView textView, String strText)
		{

			SpannableString content = new SpannableString(strText);
			content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
			textView.setText(content);
		}

		private void setLayoutBackground(ViewGroup viewGroup, int nPosition)
		{
			int nItem = nPosition % 3;
			switch (nItem)
			{
			case 0:
				viewGroup.setBackgroundResource(R.drawable.arrow_yellow);
				break;
			case 1:
				viewGroup.setBackgroundResource(R.drawable.arrow_blue);
				break;
			case 2:
				viewGroup.setBackgroundResource(R.drawable.arrow_red);
				break;
			default:
				viewGroup.setBackgroundResource(R.drawable.arrow_yellow);
				break;
			}
		}
	}

}
