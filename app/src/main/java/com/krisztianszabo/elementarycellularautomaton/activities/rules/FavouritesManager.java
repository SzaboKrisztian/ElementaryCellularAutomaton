package com.krisztianszabo.elementarycellularautomaton.activities.rules;

import android.content.Context;
import android.util.Log;

import com.krisztianszabo.elementarycellularautomaton.model.Rule;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FavouritesManager {

    private static FavouritesManager instance = new FavouritesManager();

    private FavouritesManager() {}

    public static FavouritesManager getInstance() {
        return instance;
    }

    public List<Rule> loadFavourites(Context context) {
        File favFile = new File(context.getFilesDir(), "favs.dat");
        try {
            InputStream is = new FileInputStream(favFile);
            byte[] data = new byte[(int) favFile.length()];
            is.read(data);
            is.close();
            List<Rule> result = new ArrayList<>();
            for (int i = 0; i < data.length; i += 2) {
                int num = (data[i] << 4) | data[i + 1];
                Log.d("FAV", "" + num);
                result.add(new Rule(num, true));
            }
            return result;
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    public boolean saveFavourites(Context context, List<Rule> favourites) {
        File favFile = new File(context.getFilesDir(), "favs.dat");
        try {
            OutputStream os = new FileOutputStream(favFile);
            byte[] data = new byte[favourites.size() * 2];
            int i = 0;
            for (Rule rule : favourites) {
                int fav = rule.getNumber();
                data[i++] = (byte) ((fav & 0xf0) >> 4);
                data[i++] = (byte) (fav & 0xf);
            }
            os.write(data);
            os.flush();
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
