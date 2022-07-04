package xyz.ibnuraffi.asthmacontrol.utils;

import android.content.Context;
import android.content.SharedPreferences;
import cn.pedant.SweetAlert.BuildConfig;

public class Session {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context _context;
    private int PRIVATE_MODE = 0;
    private static final String PREF_NAME = BuildConfig.APPLICATION_ID;
    private static final String EMAIL = "email";
    private static final String HASH = "hash";
    private static final String GAMBAR_WIDTH = "gambar_width";
    private static final String GAMBAR_HEIGHT = "gambar_height";
    private static final String GAMBAR_QUALITY = "gambar_quality";

    public Session(Context context){
        this._context = context;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }
    public void setEmail(String email){
        editor.putString(EMAIL, email);
        editor.commit();
    }
    public void setHash(String hash){
        editor.putString(HASH, hash);
        editor.commit();
    }
    public void setGambarWidth(int data){
        editor.putInt(GAMBAR_WIDTH, data);
        editor.commit();
    }
    public void setGambarHeight(int data){
        editor.putInt(GAMBAR_HEIGHT, data);
        editor.commit();
    }
    public void setGambarQuality(int data){
        editor.putInt(GAMBAR_QUALITY, data);
        editor.commit();
    }

    public String getEmail(){
        return pref.getString(EMAIL, "");
    }
    public String getHash(){
        return pref.getString(HASH, "");
    }
    public int getGambarWidth(){
        return pref.getInt(GAMBAR_WIDTH, 512);
    }
    public int getGambarHeight(){
        return pref.getInt(GAMBAR_HEIGHT, 512);
    }
    public int getGambarQuality(){
        return pref.getInt(GAMBAR_QUALITY, 100);
    }

}
