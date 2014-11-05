package com.example.pointeuse.adapters;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.andak.R;
import com.example.andak.R.color;
import com.example.pointeuse.models.Employe;
import com.example.pointeuse.models.Mouvement;

public class MvmAdapter extends ArrayAdapter<Mouvement> {
	private ArrayList<Mouvement> items;
	private Context context;
	private int ressourceId;

	public MvmAdapter(Context context, int textViewResourceId, ArrayList<Mouvement> items) {

		super(context, textViewResourceId, items);
		this.ressourceId = textViewResourceId;
		this.items = new ArrayList<Mouvement>();
		this.items.addAll(items);
		this.context = context;

	}

	private class ViewHolder {
		TextView employe;
		TextView entree;
		TextView sortie;
	}

	@SuppressLint("ResourceAsColor")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;

		if (convertView == null) {
			LayoutInflater vi = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = vi.inflate(ressourceId, null);

			holder = new ViewHolder();

			holder.employe = (TextView) convertView
					.findViewById(R.id.employe);

			holder.entree = (TextView) convertView
					.findViewById(R.id.entree);

			holder.sortie = (TextView) convertView.findViewById(R.id.sortie);
			
			convertView.setTag(holder);
			
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		Mouvement selectedItem = items.get(position);
		Employe e = selectedItem.getEmploye();
		String employe = e.getId();
		if(!e.getFirstName().equals("") || !e.getLastName().equals("")){
			employe = e.getFirstName() + " " + e.getLastName();
		}
		holder.employe.setText(employe);
		holder.entree.setText(selectedItem.getInDate());
		holder.sortie.setText(selectedItem.getOffDate());
		
		return convertView;

	}

	public ArrayList<Mouvement> getItems() {
		return this.items;
	}

	public void setItems(ArrayList<Mouvement> items) {
		this.items = items;
	}
}
