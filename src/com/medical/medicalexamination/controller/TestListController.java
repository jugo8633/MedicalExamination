package com.medical.medicalexamination.controller;

import com.medical.medicalexamination.R;
import com.medical.medicalexamination.model.Type;

import android.app.Activity;
import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

public abstract class TestListController
{
	private ListView				listView	= null;
	private LayoutInflater			inflater	= null;
	private BasicTestListAdapter	listAdapter	= null;

	public TestListController(Activity activity, int nListResId)
	{
		super();
		listAdapter = new BasicTestListAdapter();
		inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		listView = (ListView) activity.findViewById(nListResId);
	}

	protected abstract void onItemSelected(int nPosition);

	protected void updateList()
	{
		if (null != listView)
		{
			listView.setAdapter(listAdapter);
			listView.setOnItemClickListener(itemClickListener);
		}
	}

	protected int addItem(int nIconResId, String strDesc)
	{
		return listAdapter.addItem(nIconResId, strDesc);
	}

	private class BasicTestListAdapter extends BaseAdapter
	{
		class ItemData
		{
			int		mnIconId	= Type.INVALID;
			String	mstrDesc	= null;

			public ItemData(int nIconId, String strDesc)
			{
				mnIconId = nIconId;
				mstrDesc = strDesc;
			}
		}

		private SparseArray<ItemData>	listItem	= null;

		public BasicTestListAdapter()
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
		public View getView(int position, View convertView, ViewGroup parent)
		{
			View view = inflater.inflate(R.layout.medical_test_item, null);
			RelativeLayout layoutIconBackgroun = (RelativeLayout) view
					.findViewById(R.id.RelativeLayoutTestIconBackgroun);
			ImageView imageIcon = (ImageView) view.findViewById(R.id.imageViewTestIcon);

			setLayoutBackground(layoutIconBackgroun, position);
			imageIcon.setImageResource(listItem.get(position).mnIconId);

			return view;
		}

		public int addItem(int nIconResId, String strDesc)
		{
			listItem.append(listItem.size(), new ItemData(nIconResId, strDesc));
			return (listItem.size() - 1);
		}

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

	private OnItemClickListener	itemClickListener	= new OnItemClickListener()
													{
														@Override
														public void onItemClick(AdapterView<?> parent, View view,
																int position, long id)
														{
															onItemSelected(position);
														}
													};
}
