package com.cmlmobilesolutions.libary.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by addmn.cassio on 23/02/2017.
 */

public class RequestHttp {

    private static String URL_JSON = "";
    private static String URL_JSON2 = "";
    private static Context context;
    private static String Tabela = "";
    private static String tipowebservice = "";

    public static String getUrlJson() {
        return URL_JSON;
    }

    public static void setUrlJson(String urlJson) {
        URL_JSON = urlJson;
    }

    public static String getUrlJson2() {
        return URL_JSON2;
    }

    public static void setUrlJson2(String urlJson2) {
        URL_JSON2 = urlJson2;
    }

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        RequestHttp.context = context;
    }

    public static String getTabela() {
        return Tabela;
    }

    public static void setTabela(String tabela) {
        Tabela = tabela;
    }

    public static String getTipowebservice() {
        return tipowebservice;
    }

    public static void setTipowebservice(String tipowebservice) {
        RequestHttp.tipowebservice = tipowebservice;
    }

    public static HttpURLConnection conectar(String urlArquivo, String tipo) throws IOException {

        final int SEGUNDOS = 10000;
        URL url = new URL(urlArquivo);
        HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
        conexao.setReadTimeout(10 * SEGUNDOS);
        conexao.setRequestMethod(tipo);
        conexao.setDoInput(true);
        conexao.setDoOutput(false);
        /*if (tipo.equals("POST")) {
            conexao.setRequestProperty("Content-Type", "application/json");
            conexao.setRequestProperty("Accept", "application/json");
            OutputStream os = conexao.getOutputStream();
            os.write(json.getBytes("UTF-8"));
            os.close()
        }*/
        conexao.connect();
        return conexao;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String conectarPost(String urlArquivo, String tipo) throws IOException {

        final int SEGUNDOS = 10000;
        MediaType JSON = MediaType.get("application/json; charset=utf-8");
        URL url = new URL(urlArquivo);
        OkHttpClient client = new OkHttpClient();
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        RequestBody body = RequestBody.create(JSON, tipo);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        String ret ="";
        try (Response response = client.newCall(request).execute()) {
            ret=response.body().string();
        }

//        String post(String url, String json) throws IOException {
//
//            try (Response response = client.newCall(request).execute()) {
//               // return response.body().string();
//            }
//        }
        return ret;

    }
    public static boolean isConnected(Context ctx) {
        ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return (info != null && info.isConnected());
    }

    public static String getDadosUrl( String cGetsParam) {
        URL_JSON += cGetsParam;
          /* tipowebservice = tipo;
        Tabela = tipo.substring(0, 3).toLowerCase();*/
        try {
            String metodo = "GET";
            HttpURLConnection conexao = conectar(URL_JSON, metodo);
            int resposta = conexao.getResponseCode();
            if (resposta == HttpURLConnection.HTTP_OK) {
                InputStream is = conexao.getInputStream();
              //  JSONObject json = new JSONObject();
                return bytesParaString(is);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

 /*

    public static WebserviceLogistica lerJsondados(JSONObject json) throws JSONException {
        String lstRomaneio = "";
        String categoriaAtual;
        WebserviceLogistica wbLog = new WebserviceLogistica();
        ArrayList<HAux> lstHAux = new ArrayList<>();

        StringBuilder sb = new StringBuilder();
        JSONArray jsonNovaTec = json.getJSONArray(tipowebservice);

        JSONObject jsonCampos = jsonNovaTec.getJSONObject(0);
        JSONObject jsonTipos = jsonNovaTec.getJSONObject(1);
        JSONObject jsonTamanho = jsonNovaTec.getJSONObject(2);
        JSONObject jsonAtividade = jsonNovaTec.getJSONObject(3);
        JSONArray jsonCamposArray = jsonCampos.getJSONArray("CAMPOS");
        JSONArray jsonTiposArray = jsonTipos.getJSONArray("TIPOS");
        JSONArray jsonTamanhoArray = jsonTamanho.getJSONArray("TAMANHO");

        JSONArray jsonConteudos = jsonAtividade.getJSONArray("CONTEUDOS");
        Dao dao = new Dao(context);
        // Tratativa para ler  e criar campos no DB
        for (int i = 0; i < jsonCamposArray.length(); i++) {
            JSONObject jsonAtivi = jsonCamposArray.getJSONObject(i);
            JSONObject jsontp = jsonTiposArray.getJSONObject(i);
            JSONObject jsontm = jsonTamanhoArray.getJSONObject(i);
            ArrayList<String> campos = new ArrayList<>();
            ArrayList<String> tipos = new ArrayList<>();
            ArrayList<String> tamanhos = new ArrayList<>();


            // Tabela Contato

            sb.append("CREATE TABLE IF NOT EXISTS [" + Tabela + "] ( ");



            for (int j = 0; j < jsonAtivi.length(); j++) {
                campos.add(jsonAtivi.getString(String.valueOf(j + 1)));
                tipos.add(jsontp.getString(String.valueOf(j + 1)));
                tamanhos.add(jsontm.getString(String.valueOf(j + 1)));

                sb.append("  [" + jsonAtivi.getString(String.valueOf(j + 1)).trim() + "] ");
                if (j + 1 == jsonAtivi.length()) {
                    sb.append(" TEXT  ");
                } else {
                    sb.append(" TEXT , ");
                }


            }
            sb.append("    );");


        }
        for (int i = 0; i <= jsonConteudos.length() - 1; i++) {

            JSONObject jsonConteudosLinha = jsonConteudos.getJSONObject(i);
            HAux hmaux = new HAux();
            JSONObject jsonCamposObject = jsonCamposArray.getJSONObject(0);
            for (int j = 1; j < jsonCamposObject.length(); j++) {
                String campo = jsonCamposObject.getString(String.valueOf(j));
                hmaux.put(campo, jsonConteudosLinha.getString(campo));
            }

            lstHAux.add(hmaux);


        }
        dao.abrirBanco(sb);
        StringBuilder sb1 = new StringBuilder();
        TabelaDao tabelaDao = new TabelaDao(context);
        //TABELA CAMPOS
        //DROP
        sb1 = new StringBuilder();
        sb1.append("DROP TABLE IF EXISTS [CAMPOS] ;");
        tabelaDao.droptable(sb1);
        //CREATE
        sb1 = new StringBuilder();
        sb1.append("CREATE TABLE IF NOT EXISTS [CAMPOS] ( " +
                "  [TITULO] TEXT ,  " +
                "  [TIPO] TEXT , " +
                "  [TAMANHO] TEXT ,  " +
                "  [DECIMAL] TEXT  " +
                "    );");
        tabelaDao.criarTabela(sb1);
        tabelaDao.inserirCampos(jsonCamposArray);
        ArrayList<HAux> lstCampos = tabelaDao.obterDadosCAMPOS();
        //Tabela do webservice
        sb1.append("DROP TABLE IF EXISTS [" + Tabela + "] ;");

        tabelaDao.droptable(sb1);
        tabelaDao.criarTabela(sb);
        tabelaDao.inserir(jsonCamposArray, jsonConteudos);
        ArrayList<HAux> lstDados = tabelaDao.obterDados(Tabela, jsonCamposArray);


        wbLog.setSb(sb);
        wbLog.setaCampos(lstCampos);
        wbLog.setaConteudos(lstDados);
        return wbLog;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String gravarFoto(String json) {
        //  URL_JSON2;// = ToolBox.webServices(false) + "&ids=" + ids + "&tipo=" + tipo + "&referenca=" + referenca + "&container=" + container + "&romaneio=" + romaneio + "&imagem=" + imagem;
        String retorno="";
        try {


            String metodo = "POST";
             retorno = conectarPost(URL_JSON2, json);
            Log.d("WBSVLOG01POST",retorno);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return retorno;
    }

    public static ArrayList<HAux> carregaRomaneio(String ids, String tipo, String referenca, String container, String romaneio, String imagem) {
        URL_JSON += "&ids=" + ids + "&tipo=" + tipo + "&referenca=" + referenca + "&container=" + container + "&romaneio=" + romaneio + "&imagem=" + imagem;
        try {
            String metodo = "GET";
            HttpURLConnection conexao = conectar(URL_JSON, metodo);
            int resposta = conexao.getResponseCode();
            if (resposta == HttpURLConnection.HTTP_OK) {
                InputStream is = conexao.getInputStream();
                JSONObject json = new JSONObject(bytesParaString(is));
                return lerJsonCarregaRomaneio(json);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static ArrayList<HAux> lerJsonCarregaRomaneio(JSONObject json) throws JSONException {
        ArrayList<HAux> lstRomaneio = new ArrayList<HAux>();
        String categoriaAtual;

        JSONArray jsonNovaTec = json.getJSONArray("carregaromaneio");
        JSONObject jsonAtividade = jsonNovaTec.getJSONObject(0);
        JSONArray jsonTarefa = jsonAtividade.getJSONArray("tarefas");
        for (int i = 0; i < jsonTarefa.length(); i++) {
            JSONObject jsonAtivi = jsonTarefa.getJSONObject(i);
            String codigo = jsonAtivi.getString("filial");
            String descricao = jsonAtivi.getString("romaneio");
            String saldo = jsonAtivi.getString("tipo");
            String empenho = "0";
            HAux hmaux = new HAux();
            hmaux.put(HAux.ID, String.valueOf(i));
            hmaux.put(HAux.TEXTO_01, descricao);
            hmaux.put(HAux.TEXTO_02, saldo);
            long date = System.currentTimeMillis();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String dateString = sdf.format(date);
            //   SB2 produto = new SB2(dateString, codigo, descricao, empenho, saldo);
            lstRomaneio.add(hmaux);


        }
        return lstRomaneio;
    }

    public static List<SB2> lerJsonGravaImagem(JSONObject json) throws JSONException {
        List<SB2> listaDeProdutos = new ArrayList<SB2>();
        String categoriaAtual;

        JSONArray jsonNovaTec = json.getJSONArray("updateimagem");
        JSONObject jsonAtividade = jsonNovaTec.getJSONObject(0);
        JSONArray jsonTarefa = jsonAtividade.getJSONArray("tarefas");
        for (int i = 0; i < jsonTarefa.length(); i++) {
            JSONObject jsonAtivi = jsonTarefa.getJSONObject(i);
            String codigo = jsonAtivi.getString("produto");
            String descricao = jsonAtivi.getString("descricao");
            String saldo = jsonAtivi.getString("saldo");
            String empenho = "0";

            long date = System.currentTimeMillis();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String dateString = sdf.format(date);
            SB2 produto = new SB2(dateString, codigo, descricao, empenho, saldo);
            listaDeProdutos.add(produto);


        }
        return listaDeProdutos;
    }

    public static List<SB2> lerJsonProdutos(JSONObject json) throws JSONException {
        List<SB2> listaDeProdutos = new ArrayList<SB2>();
        String categoriaAtual;

        JSONArray jsonNovaTec = json.getJSONArray("saldoprodutos");
        JSONObject jsonAtividade = jsonNovaTec.getJSONObject(0);
        JSONArray jsonTarefa = jsonAtividade.getJSONArray("tarefas");
        for (int i = 0; i < jsonTarefa.length(); i++) {
            JSONObject jsonAtivi = jsonTarefa.getJSONObject(i);
            String codigo = jsonAtivi.getString("produto");
            String descricao = jsonAtivi.getString("descricao");
            String saldo = jsonAtivi.getString("saldo");
            String empenho = "0";

            long date = System.currentTimeMillis();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String dateString = sdf.format(date);
            SB2 produto = new SB2(dateString, codigo, descricao, empenho, saldo);
            listaDeProdutos.add(produto);


        }
        return listaDeProdutos;
    }
*/
    private static String bytesParaString(InputStream is) throws IOException {
        byte[] buffer = new byte[4096];
        ByteArrayOutputStream bufferzao = new ByteArrayOutputStream();
        int bytesLidos;

        while ((bytesLidos = is.read(buffer)) != -1) {
            bufferzao.write(buffer, 0, bytesLidos);
        }
        return new String(bufferzao.toByteArray(), "UTF-8");
    }
}
