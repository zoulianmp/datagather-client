package research.ufu.datagather.utils;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import research.ufu.datagather.HTTPSingleton;
import research.ufu.datagather.model.ResponseHelper;

/**
 * Created by fynn on 02/02/15.
 */
public class Utils {
    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }


    public static ResponseHelper POSTJson(String url, String message) {
        ResponseHelper result = new ResponseHelper();
        InputStream inputStream = null;
        String result_text = "";
        try {

            HttpClient httpclient = HTTPSingleton.getHttpClient();
            HttpPost httpPost = new HttpPost(url);
            StringEntity se = new StringEntity(message);
            httpPost.setEntity(se);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            HttpResponse httpResponse = httpclient.execute(httpPost);
            result.setResultCode(httpResponse.getStatusLine().getStatusCode());
            inputStream = httpResponse.getEntity().getContent();
            if(inputStream != null)
                result_text = convertInputStreamToString(inputStream);
            else
                result_text = null;
            result.setResultText(result_text);

        } catch (Exception e) {
            result.setResultCode(410);
            e.printStackTrace();
            Log.d("InputStream", e.getLocalizedMessage());
        }

        return result;
    }

    public static ResponseHelper GET(String url) {
        ResponseHelper result = new ResponseHelper();
        InputStream inputStream = null;
        String result_text = "";
        try {

            HttpClient httpclient = HTTPSingleton.getHttpClient();
            HttpGet httpGet = new HttpGet(url);
            HttpResponse httpResponse = httpclient.execute(httpGet);
            result.setResultCode(httpResponse.getStatusLine().getStatusCode());
            inputStream = httpResponse.getEntity().getContent();
            if(inputStream != null)
                result_text = convertInputStreamToString(inputStream);
            else
                result_text = null;
            result.setResultText(result_text);

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("InputStream", e.getLocalizedMessage());
            result = null;
        }
        return result;
    }

}
