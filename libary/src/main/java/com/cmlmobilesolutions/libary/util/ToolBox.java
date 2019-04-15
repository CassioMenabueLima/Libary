package com.cmlmobilesolutions.libary.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;


import com.cmlmobilesolutions.libary.model.Imagens;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;


public class ToolBox {

    public static void ProgressDialogCustom(Context context, String Titulo, int icon, ArrayList lista) {
        final ProgressDialog progressDialog;

        int nMaximo;
        progressDialog = new ProgressDialog(context);
        progressDialog.setIcon(icon);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);

        if (lista.size() < 100) {
            nMaximo = 100;
        } else {
            nMaximo = lista.size();
        }

        final int nMax = nMaximo;
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle(Titulo);
        progressDialog.show();


        new Thread() {

            public void run() {

                try {

                    for (int i = 0; i <= nMax; i++) {
                        //   Thread.sleep(100);
                        progressDialog.incrementProgressBy(1);

                    }

                    progressDialog.dismiss();
                } catch (Exception e) {

                }
            }
        }.start();

    }
    //Registro do Aparelho
    public static void registerToken(String token, String imei, String tel, String usuario, String nome,String url) {

        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("token", token)
                .add("imei", imei)
                .add("usuario", usuario)
                .add("nome", nome)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        try {
            client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Notifications
    public static void notificacoes(String titulo, String mensagem, Context ctx, PendingIntent pi, String _NOTIFICATION_SERVICE, int idNotificacao, int logo) {

        NotificationManager nm = (NotificationManager)
                ctx.getSystemService(_NOTIFICATION_SERVICE);


        Notification.Builder nf = new Notification.Builder(ctx);
        nf.setSmallIcon(logo);
        nf.setContentIntent(pi);
        nf.setContentTitle(titulo);
        nf.setContentText(mensagem);
        nf.setAutoCancel(true);
        nf.setDefaults(
                Notification.DEFAULT_SOUND |
                        Notification.DEFAULT_VIBRATE
        );

        // A versao do sistema operacional no SmartPhone
        int versao = Build.VERSION.SDK_INT;

        if (versao >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            //
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                nm.notify(idNotificacao, nf.build());
            }
        } else {
            // TATATATA TA
            nm.notify(idNotificacao, nf.getNotification());
        }
       /* // espera 100ms e vibra por 250ms.
                notificacao.vibrate = new long[]{100, 250, 100, 500};
        notificacao.defaults |= Notification.DEFAULT_LIGHTS;
        notificacao.defaults |= Notification.DEFAULT_SOUND;
        notificacao.flags |= Notification.FLAG_AUTO_CANCEL;

        // id (n˙mero ˙nico) que identifica esta notificaÁ„o. Mesmo id utilizado para cancelar
        nm.notify(R.string.app_name, notificacao);*/
    }

    //Validacoes de Componentes visuais
    public static Boolean checkEditCustom(EditText campo, Boolean focus) {

        String conteudo = campo.getText().toString().trim();
        boolean cancel = true;
        View focusView = null;

        if (TextUtils.isEmpty(conteudo)) {
            campo.setError("Preenchimento Obrigatorio");
            focusView = campo;
            cancel = false;
        }
        if (!cancel && focus) {
            focusView.requestFocus();
        }


        return cancel;
    }
    public static Boolean checkAutoCompleteTextViewCustom(AutoCompleteTextView campo, Boolean focus) {
        String conteudo = campo.getText().toString().trim();
        boolean cancel = true;
        View focusView = null;

        if (TextUtils.isEmpty(conteudo)) {
            campo.setError("Preenchimento Obrigatorio");
            focusView = campo;
            cancel = false;
        }
        if (!cancel && focus) {
            focusView.requestFocus();
        }


        return cancel;
    }
    public static boolean checkSpinnerCustom(Spinner campo, int id, Boolean focus) {
        boolean cancel = true;
        View focusView = null;

        if (id == 0) {
            SpinnerAdapter adapter = campo.getAdapter();
            ((TextView) campo.getSelectedView()).setError("Preenchimento Obrigatorio");
            focusView = campo;
            cancel = false;
        }
        if (!cancel && focus) {
            focusView.requestFocus();
        }


        return cancel;
    }

    //Strings
    public static String addZeroLeft(String value, int n) {
        String s = value.trim();
        StringBuffer resp = new StringBuffer();
        int fim = n - s.length();
        for (int x = 0; x < fim; x++)
            resp.append('0');
        return resp + s;
    }
    public static String removeAllEspecialCaracters(String stringFonte)    {

        String passa = stringFonte;

        passa = passa.replaceAll("[ÂÀÁÄÃ]", "A");

        passa = passa.replaceAll("[âãàáä]", "a");

        passa = passa.replaceAll("[ÊÈÉË]", "E");

        passa = passa.replaceAll("[êèéë]", "e");

        passa = passa.replaceAll("ÎÍÌÏ", "I");

        passa = passa.replaceAll("îíìï", "i");

        passa = passa.replaceAll("[ÔÕÒÓÖ]", "O");

        passa = passa.replaceAll("[ôõòóö]", "o");

        passa = passa.replaceAll("[ÛÙÚÜ]", "U");

        passa = passa.replaceAll("[ûúùü]", "u");

        passa = passa.replaceAll("Ç", "C");

        passa = passa.replaceAll("ç", "c");

        passa = passa.replaceAll("[ýÿ]", "y");

        passa = passa.replaceAll("Ý", "Y");

        passa = passa.replaceAll("ñ", "n");

        passa = passa.replaceAll("Ñ", "N");

        passa = passa.replaceAll("[+=*&amp;%$#@!:]", "");

        passa = passa.replaceAll("['\"]", "-");

        passa = passa.replaceAll("[<>()\\{\\}]", "");

        passa = passa.replaceAll("['\\\\,()|/]", "-");

        passa = passa.replaceAll("[^!-ÿ]{1}[^ -ÿ]{0,}[^!-ÿ]{1}|[^!-ÿ]{1}", " ");

        return passa;

    }
    public static String getCurrenceDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        // OU
        SimpleDateFormat dateFormat_hora = new SimpleDateFormat("HH:mm:ss");
        Date data = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        Date data_atual = cal.getTime();
        String data_completa = dateFormat.format(data_atual);
        String hora_atual = dateFormat_hora.format(data_atual);
        return data_completa;
    }
    public static String getCurrenceHour() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy-HH:mm:ss");
        // OU
        SimpleDateFormat dateFormat_hora = new SimpleDateFormat("HH:mm:ss");
        Date data = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        Date data_atual = cal.getTime();
        String data_completa = dateFormat.format(data_atual);
        String hora_atual = dateFormat_hora.format(data_atual);
        return hora_atual;
    }

    // Arquivos
    public static void writeFile(String mensagem, String path, String fileName) {

        File diretorio = new File(path);
        if (!diretorio.exists()) {
            diretorio.mkdir();
        }
        FileWriter f;
        try {
            f = new FileWriter(
                    path + "/" + fileName,
                    true
            );
            f.append(mensagem);
            f.flush();
            f.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static String readFile(String path, String fileName) {
        StringBuilder conteudo = new StringBuilder();
        File diretorio = new File(path);

        if (!diretorio.exists()) {
            return "";
        }
        File arquivo = new File(path + "/" + fileName);

        if (!arquivo.exists()) {
            return "";
        }
        //
        try {
            BufferedReader input = new BufferedReader(new FileReader(arquivo));
            String linha = null;

            //processo de leitura e acumulacao
            while ((linha = input.readLine()) != null) {
                conteudo.append(linha);
            }
            input.close();
        } catch (Exception e) {
            conteudo.append(e.toString());
        }
        return conteudo.toString();

    }
    public static void deleteFile(String path, String fileName) {

        File diretorio = new File(path);

        if (!diretorio.exists()) {
            return;
        }
        File arquivo = new File(path + "/" + fileName);

        if (arquivo.exists()) {
            arquivo.delete();
        }

    }

    //Imagens
    public static void saveBitmap(String caminho, String imagemNome, Bitmap bitmap) {
        //String path = Environment.getExternalStorageDirectory().toString();
        String path = caminho;
        File diretorio = new File(caminho);
        if (!diretorio.exists()) {
            diretorio.mkdir();
        }
        diretorio = new File(path);
        if (!diretorio.exists()) {
            diretorio.mkdir();
        }
        OutputStream fOut = null;
        String imageName = imagemNome;
        File file = new File(path, imageName);
        try {
            fOut = new FileOutputStream(file);
            if (!bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut)) {
                Log.e("Log", "error while saving bitmap " + path + imageName);
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public static Bitmap loadBitmap(Context context, String picName) {
        Bitmap b = null;
        FileInputStream fis;
        try {
            fis = context.openFileInput(picName);

            b = BitmapFactory.decodeStream(fis);
            fis.close();

        } catch (FileNotFoundException e) {
            Log.d("IAMGEM", "file not found");
            e.printStackTrace();
        } catch (IOException e) {
            Log.d("IAMGEM", "io exception");
            e.printStackTrace();
        }
        return b;
    }
    public static Bitmap resizeBitmap(String localPath, int largura, int altura) {
        //tamanho do meu imageview

        int targetW = largura;
        int targeH = altura;

        //iv_foto.setImageBitmap(BitmapFactory.decodeFile(localPath));//comando valido desde que a imagem seja pequena
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;//nao carrega a foto ele inspenciona
        BitmapFactory.decodeFile(localPath, options);
        //tamanho da minha foto
        int photow = options.outWidth;
        int photoh = options.outHeight;

        //escala de reducao
        int fatoScala = Math.min(photow / targetW, photoh / targeH);

        options.inJustDecodeBounds = false;//carrega a imagem ja tratada
        options.inSampleSize = fatoScala;
        Bitmap bm = BitmapFactory.decodeFile(localPath, options);
        return bm;
    }
    public static String createImageJson(Imagens img) throws JSONException {
        JSONObject retJson = new JSONObject();
        Gson gson = new Gson();
        String json = "";
        try {
            retJson.put("imagem", img);
            //convert java object to JSON format
            json = gson.toJson(img);
            System.out.println(json);//   json= retJson.

        } catch (Exception e) {
            e.printStackTrace();
        }


        return json;
    }


}
