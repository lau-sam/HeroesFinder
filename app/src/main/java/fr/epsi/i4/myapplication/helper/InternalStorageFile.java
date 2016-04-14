package fr.epsi.i4.myapplication.helper;

import android.content.Context;
import android.content.res.Resources;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import fr.epsi.i4.myapplication.R;

/**
 * Created by tuannguyen on 14/04/16.
 */
public class InternalStorageFile {

    public void writeFile(String filename, String texte, Context context){

        FileOutputStream fos = null;

        try {
            fos = context.openFileOutput(filename, context.MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            fos.write(texte.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readFile(Context context, String filename){

        StringBuffer fileContent = new StringBuffer("");
        try {
            FileInputStream fis = context.openFileInput(filename);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);

            String readString = br.readLine();
            while (readString != null){
                fileContent.append(readString);
                readString = br.readLine();
            }

            isr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return String.valueOf(fileContent);
    }

    public String readRawResource(Resources res){

        StringBuilder sb = new StringBuilder();
        try {
            InputStream is = res.openRawResource(R.raw.knowledge_base) ;
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            while (br.readLine() != null) {
                sb.append(br.readLine());
            }
            is.close() ;
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }


}
