package com.example.pointeuse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andak.R;
import com.example.pointeuse.adapters.MvmAdapter;
import com.example.pointeuse.dao.EmployeDAO;
import com.example.pointeuse.dao.MouvementDAO;
import com.example.pointeuse.models.Employe;
import com.example.pointeuse.models.Mouvement;
import com.example.pointeuse.util.Util;

@SuppressLint("NewApi")
public class MainActivity extends FragmentActivity {
	 
	private Context context;
    private TextView txtNowDate;
    private ListView listView;
    private MvmAdapter mvmAdapter;
    private ArrayList<Mouvement> list = new ArrayList<Mouvement>();
    private MouvementDAO mouvementDAO;
    ArrayList<Mouvement> mouvements;
    private EmployeDAO employeDAO;
    private ProgressBar loading;
     
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        
        setContentView(R.layout.activity_main);
        txtNowDate = (TextView) findViewById(R.id.txt_now_date);
        listView = (ListView) findViewById(R.id.employes_presents);
        loading = (ProgressBar) findViewById(R.id.loading);
        
        list.add(new Mouvement(new Employe("Employé", null, null), null,  "Entrée", "Sortie"));        
        mouvementDAO = new MouvementDAO(context);
        employeDAO = new EmployeDAO(context);
        mouvementDAO.open();
        
    }
    
    @Override
    protected void onResume() {
    	
    	super.onResume();
    	
    	
    	String nowDate = new SimpleDateFormat("EEEE dd MMMM yyyy", Locale.FRANCE).format(new Date());
    	txtNowDate.setText(nowDate);
    	
    	initListEmployes();
        
    }
    
    public void onPointer(View v){
    	
        Intent intent = new Intent("com.google.zxing.client.android.SCAN");
        startActivityForResult(intent, 0);
        
    }
 
    public void onClear(View v){
    	mouvementDAO.delete();
    	employeDAO.delete();
    	initListEmployes();
    }
    
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
           if (requestCode == 0) {
              if (resultCode == RESULT_OK) {
                  
                 String contents = intent.getStringExtra("SCAN_RESULT");
               
                 Mouvement mouvement = mouvementDAO.getMouvementOfEmploye(contents, new Date());
                 if(mouvement != null && mouvement.getOffDate() != null){
                	 Toast.makeText(context, "Opération de pointage términée", Toast.LENGTH_LONG).show();
                 }
                 else{
                     AddMvmDlg alertdFragment = new AddMvmDlg();
                     alertdFragment.setMouvement(mouvement);
                     alertdFragment.setEmployeId(contents);
                     alertdFragment.show(getFragmentManager(), null);                	 
                 }                	 
              }
         }
      }
    
    public void initListEmployes(){
    	list = new ArrayList<Mouvement>();
    	list.add(new Mouvement(new Employe("", "Employé", ""), null,  "Entrée", "Sortie"));
    	mouvements = mouvementDAO.getMouvements(new Date());
    	list.addAll(mouvements);
        mvmAdapter = new MvmAdapter(MainActivity.this, R.layout.mvm_item, list);
        listView.setAdapter(mvmAdapter);
    }
    
    public void onGetFile(View v){
    	boolean created = Util.generateCSVFile(mouvements);
    	String fileCreationMessage;
    	if(created){
    		fileCreationMessage = context.getString(R.string.file_created);
    	}
    	else{
    		fileCreationMessage = context.getString(R.string.file_not_created);
    	}
    	Toast.makeText(context, fileCreationMessage, Toast.LENGTH_LONG).show();
    }
}
