package fr.epsi.i4.myapplication.helper;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import fr.epsi.i4.myapplication.R;
import fr.epsi.i4.myapplication.model.Character;

/**
 * Created by tuannguyen on 14/04/16.
 */
public class InternalStorageFile {

    private static final String TAG = "InternalStorageFile";

    public ArrayList<Character> getCharactersFromCSVFormat(Context context){

        ArrayList<Character> characters = new ArrayList<>();

        InputStream is = context.getResources().openRawResource(R.raw.knowledge_database);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        try {
            String line;
            while ((line = br.readLine()) != null) {
                String[] rowData = line.split(",");
                String characterName = rowData[0];
                boolean sexeMale = Boolean.valueOf(rowData[1]);
                boolean liveInUSA = Boolean.valueOf(rowData[2]);
                boolean humanLook = Boolean.valueOf(rowData[3]);
                boolean canFly = Boolean.valueOf(rowData[4]);

                characters.add(new Character());

                Log.e(TAG,characterName+","+sexeMale+","+liveInUSA+","+humanLook+","+canFly);

            }
        }
        catch (IOException ex) {
            // handle exception
        }
        finally {
            try {
                is.close();
            }
            catch (IOException e) {
                // handle exception
            }
        }

        return characters;
    }
}
