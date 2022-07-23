package xyz.ibnuraffi.asthmacontrol.utils;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.text.Html;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Date;
import cn.pedant.SweetAlert.SweetAlertDialog;
import xyz.ibnuraffi.asthmacontrol.auth.AuthLogin;

public class Funct {

    private Session session;
    private Context context;
    private ProgressDialog loading;
    private SweetAlertDialog sweetAlertDialog;
    private Snackbar alert;
    private AlertDialog.Builder builder;
    private AlertDialog notifikasi;

    public Funct(final Context context){
        this.context = context;
        session = new Session(context);

        //loading
        loading = new ProgressDialog(context);
        loading.setMessage("Harap tunggu...");
        loading.setCancelable(false);

        //notifikasi
        builder = new AlertDialog.Builder(context);
        notifikasi = builder.create();

    }

    public void logout(){
        session.setHash("hash");
        session.setEmail("email");
        Intent intent = new Intent(context, AuthLogin.class);
        context.startActivity(intent);
        ((Activity) context).finish();
    }

    public void loadingShow() {
        if (!loading.isShowing()) {
            loading.show();
        }
    }

    public void loadingHide() {
        if (loading.isShowing()) {
            loading.dismiss();
        }
    }

    public void notifikasiToastShow(String notifikasi) {
        Toast.makeText(context, notifikasi, Toast.LENGTH_SHORT).show();
    }

    public void notifikasiShow(String text, final String link){
        if (!notifikasi.isShowing()) {
            notifikasi.setMessage(text);
            notifikasi.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    notifikasi.dismiss();
                    if(!TextUtils.isEmpty(link)) {
                        Uri uri = Uri.parse(link); // missing 'http://' will cause crashed
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        context.startActivity(intent);
                    }
                }
            });
            notifikasi.show();
        }
    }

    public void notifikasiDismisable(CoordinatorLayout root_layout, String message){
        alert = Snackbar.
                make(root_layout, message, Snackbar.LENGTH_LONG)
                .setAction("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alert.dismiss();
                    }
                });

        alert.show();
    }

    public void closeApp(){
        sweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                .setContentText("Keluar dari Aplikasi ?")
                .setConfirmText("Ya")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismiss();
                        ((Activity) context).moveTaskToBack(true);

                    }
                })
                .setCancelText("Tidak")
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                    }
                });

        if(!sweetAlertDialog.isShowing()) {
            sweetAlertDialog.show();
        }
    }

    public String imageToString(Bitmap bmp){
        //compres quality
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //0 = bad quality, 100 = best quality
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    public Bitmap base64ToBitmap(String base64){
        String base64Image = base64;
        byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }

    public void toHtml(TextView textview, String string){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            textview.setText(Html.fromHtml(string, Html.FROM_HTML_MODE_LEGACY));
        } else {
            textview.setText(Html.fromHtml(string));
        }
    }

    public void inputDropdownSpiner(Spinner spiner, ArrayList<SpinnerModel> array){
        ArrayAdapter<SpinnerModel> adapter = new ArrayAdapter<SpinnerModel>(context, android.R.layout.simple_spinner_dropdown_item, array){
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView tv = (TextView) super.getView(position, convertView, parent);
                toHtml(tv, tv.getText().toString());
                tv.setTextColor(Color.BLACK);
                return tv;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent){
                TextView tv = (TextView) super.getDropDownView(position,convertView,parent);
                toHtml(tv, tv.getText().toString());
                tv.setTextColor(Color.BLACK);
                return tv;
            }
        };
        spiner.setAdapter(adapter);
    }

    public void inputDropdownSpinerSelected(Spinner spiner, ArrayList<SpinnerModel> array, String selected){
        int index = 0;
        for (SpinnerModel row : array) {
            if (row.getId().equalsIgnoreCase(selected)) {
                spiner.setSelection(index);
                break;
            }
            index++;
        }
    }

    public void inputDropdownAutocomplete(MaterialAutoCompleteTextView spiner, ArrayList<SpinnerModel> array){
        ArrayAdapter<SpinnerModel> adapter = new ArrayAdapter<SpinnerModel>(context, android.R.layout.simple_spinner_dropdown_item, array){
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView tv = (TextView) super.getView(position, convertView, parent);
                toHtml(tv, tv.getText().toString());
                tv.setTextColor(Color.BLACK);
                return tv;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent){
                TextView tv = (TextView) super.getDropDownView(position,convertView,parent);
                toHtml(tv, tv.getText().toString());
                tv.setTextColor(Color.BLACK);
                return tv;
            }
        };
        spiner.setAdapter(adapter);
    }

    public void inputDropdownAutocompleteSelected(Spinner spiner, ArrayList<SpinnerModel> array, String selected){
        int index = 0;
        for (SpinnerModel row : array) {
            if (row.getId().equalsIgnoreCase(selected)) {
                spiner.setSelection(index);
                break;
            }
            index++;
        }
    }

    public static String getFormattedDateSimple(Long dateTime) {
        SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd");
        return newFormat.format(new Date(dateTime));
    }

    public static String CurrencyFormat(Integer nomor){
        NumberFormat format = NumberFormat.getCurrencyInstance();
        format.setMaximumFractionDigits(2);
        format.setCurrency(Currency.getInstance("IDR"));

        return format.format(nomor);
    }

    public static String getHostName(String url) {
        try {
            URI uri = new URI(url);
            String new_url = uri.getHost();
            if (!new_url.startsWith("www.")) new_url = "www." + new_url;
            return new_url;
        } catch (URISyntaxException e) {
            if (e.getMessage() != null) {
                Log.e("ERROR", e.getMessage());
            }
            return url;
        }
    }

    //permision
    public boolean setPermission(int request_code){
        if (Build.VERSION.SDK_INT >= 23) {
            if (
                    //ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                    //ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED &&
                    //ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(context, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED &&
                    //ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
                    //ContextCompat.checkSelfPermission(context, Manifest.permission.MODIFY_AUDIO_SETTINGS) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    //ContextCompat.checkSelfPermission(context, Manifest.permission.VIBRATE) == PackageManager.PERMISSION_GRANTED
            ) {
                return true;
            } else {
                ActivityCompat.requestPermissions(((Activity) context), new String[]{
                        //Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        //Manifest.permission.CALL_PHONE,
                        //Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_NETWORK_STATE,
                        Manifest.permission.INTERNET,
                        //Manifest.permission.CAMERA,
                        //Manifest.permission.MODIFY_AUDIO_SETTINGS,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                        //Manifest.permission.VIBRATE
                }, request_code);
                return false;
            }
        } else {
            return true;
        }
    }

    public boolean getPermissionResult(String[] permissions, int[] grantResults){
        for (int i = 0, len = permissions.length; i < len; i++) {
            if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                //ini di matikan akrena di beberapa hp jadi looping lemot
                //setPermission(requestCode);
                notifikasiShow("Dengan menolak akses Aplikasi, maka Aplikasi tidak akan berjalan normal, silahkan uninstall Aplikasi dan kemudian install kembali ", "");
                return false;
            }
        }

        return true;
    }
    //end permision

}
