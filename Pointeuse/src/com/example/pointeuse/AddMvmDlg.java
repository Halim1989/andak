package com.example.pointeuse;

import java.util.Date;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.andak.R;
import com.example.pointeuse.dao.EmployeDAO;
import com.example.pointeuse.dao.MouvementDAO;
import com.example.pointeuse.models.Employe;
import com.example.pointeuse.models.Mouvement;
import com.example.pointeuse.util.Util;

@SuppressLint("NewApi")
public class AddMvmDlg extends DialogFragment {

	private Mouvement mouvement;
	private String employeId;
	private String title;
	private Employe employe;
	private boolean create = false;
	private boolean isEntree = true;
	private EditText txtFirstName;
	private EditText txtLastName;
	private TextView tvFirstName;
	private TextView tvLastName;
	private CheckBox cbOperationType;
	private Context context;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		context = getActivity().getApplicationContext();
		LayoutInflater li = LayoutInflater.from(context);
		View addEmployeView = li.inflate(R.layout.add_mvm_dlg, null);
		
		initView(addEmployeView);
		
		AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
		.setTitle(title)
		.setView(addEmployeView)
		.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {

				EmployeDAO employeDAO = new EmployeDAO(getActivity().getApplicationContext());
				employe.setFirstName(txtFirstName.getText().toString());
				employe.setLastName(txtLastName.getText().toString());

				if(create){
					
					employeDAO.create(employe);

				}
				else{
					
					employeDAO.update(employe);
					
				}
								
				MouvementDAO mouvementDAO = new MouvementDAO(context);
				if(isEntree){
					
					Mouvement mouvement = new Mouvement();
					mouvement.setEmploye(employe);
					mouvement.setDate(new Date());
					mouvement.setInDate(Util.getFormatedHour(new Date()));
					mouvementDAO.create(mouvement);
					
				}
				else{
					
					mouvement.setOffDate(Util.getFormatedHour(new Date()));
					mouvementDAO.update(mouvement);
					
				}
				
				MainActivity mainActivity = (MainActivity) getActivity();
				mainActivity.initListEmployes();
				
			}
		}).create();
		
		
		return alertDialog;
		
	}
	
	private void initView(View view){
		
		if(mouvement != null){
			employe = mouvement.getEmploye();
			if(mouvement.getId() != -1){
				isEntree = false;
			}
		}
		
		if(employe == null){
			create = true;
			employe = new Employe();
			employe.setId(employeId);
		}
		TextView code = (TextView) view.findViewById(R.id.tv_id);
		code.setText(employe.getId());

		txtFirstName = (EditText) view.findViewById(R.id.txt_first_name);
		txtLastName = (EditText) view.findViewById(R.id.txt_last_name);
		tvFirstName = (TextView) view.findViewById(R.id.tv_first_name);
		tvLastName = (TextView) view.findViewById(R.id.tv_last_name);
		cbOperationType = (CheckBox) view.findViewById(R.id.operation_type);
		
		title = context.getString(R.string.pointer);
		if(!create){
			view.findViewById(R.id.txt_create_employe_message).setVisibility(View.GONE);
		}
		else{
			title = title + " ( " + context.getString(R.string.unknown_employe) + " )";
		}
		
		if(employe.getFirstName() == null || employe.getFirstName().equals("")){
			txtFirstName.setVisibility(View.VISIBLE);
			tvFirstName.setVisibility(View.GONE);
		}
		else{
			txtFirstName.setVisibility(View.GONE);
			tvFirstName.setVisibility(View.VISIBLE);
			tvFirstName.setText(employe.getFirstName());
		}
		
		if(employe.getLastName() == null || employe.getLastName().equals("")){
			txtLastName.setVisibility(View.VISIBLE);
			tvLastName.setVisibility(View.GONE);
		}
		else{
			txtLastName.setVisibility(View.GONE);
			tvLastName.setVisibility(View.VISIBLE);
			tvLastName.setText(employe.getLastName());
		}
		
		
		if(isEntree){
			cbOperationType.setText(context.getString(R.string.operation_entree));
			cbOperationType.setChecked(false);
		}
		else{
			cbOperationType.setText(context.getString(R.string.operation_sortie));
			cbOperationType.setChecked(true);
		}

	}
	
	public Mouvement getMouvement() {
		return mouvement;
	}

	public void setMouvement(Mouvement mouvement) {
		this.mouvement = mouvement;
	}

	public String getEmployeId() {
		return employeId;
	}

	public void setEmployeId(String employeId) {
		this.employeId = employeId;
	}
	
}
