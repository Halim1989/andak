package com.example.pointeuse.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang3.StringEscapeUtils;

import android.os.Environment;

import com.example.pointeuse.models.Mouvement;

public abstract class Util {

	public static final File SD_ROOT = Environment.getExternalStorageDirectory();
	public static final String DIR = "/DCIM/Pointages";

	public static String getFormatedDate(Date date) {
		return new SimpleDateFormat("dd-MM-yyyy", Locale.FRANCE).format(new Date());
	}

	public static String getFormatedHour(Date date) {
		return new SimpleDateFormat("hh:mm", Locale.FRANCE).format(new Date());
	}

	public static boolean generateCSVFile(ArrayList<Mouvement> mouvements) {
		boolean created = false;
		if(mouvements.size() > 0){
			created = true;
			String date = getFormatedDate(mouvements.get(0).getDate());
			createFolderIfNotExists(SD_ROOT.getPath() + DIR);
			File file = new File(SD_ROOT, DIR + "/" + date + ".csv");
			FileWriter writer;
			try {
				writer = new FileWriter(file);
				
				//	Entete
				writer.append(StringEscapeUtils.escapeCsv("Employe"));
				writer.append(',');
				writer.append(StringEscapeUtils.escapeCsv("Arrivee"));
				writer.append(',');
				writer.append(StringEscapeUtils.escapeCsv("Sortie"));
				writer.append('\n');

				for (Mouvement mouvement : mouvements) {
					writer.append(StringEscapeUtils.escapeCsv(mouvement.getEmploye().getId()));
					writer.append(',');
					writer.append(StringEscapeUtils.escapeCsv(mouvement.getInDate()));
					writer.append(',');
					writer.append(StringEscapeUtils.escapeCsv(mouvement.getOffDate()));
					writer.append('\n');				
				}

				writer.flush();
				writer.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return created;
	}

	public static void createFolderIfNotExists(String path) {
		File theDir = new File(path);
		// if the directory does not exist, create it
		if (!theDir.exists()) {
			System.out.println("creating directory: " + path);
			theDir.mkdir();
		}
	}

}
