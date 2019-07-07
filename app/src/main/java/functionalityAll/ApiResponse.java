package functionalityAll;

import android.content.Context;
import android.os.Environment;
import android.util.Log;


import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

import controllerAll.Config;
import restro.bts.com.restro.R;


/**
 * Created by Abhay dhiman
 */

// All Api Request Method class
public class ApiResponse {
    String result;

    HttpURLConnection urlConnection;
    String response;
    URL url;
    private InputStream inputStream;


    //Get request method
    public String ResponseGet(Context context, String urlGet) {

        try {
            Log.e("URL","URL"+urlGet);
            url = new URL(urlGet);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(50000);
            urlConnection.setReadTimeout(50000);
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            urlConnection.connect();

            int HttpResult = urlConnection.getResponseCode();

            Log.e("HttpResult", "HttpResult" + HttpResult);
            Log.e("HttpResult", "HttpResult" + urlConnection.getInputStream());

            if (HttpResult == HttpURLConnection.HTTP_OK) {
                InputStream ins = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader br = new BufferedReader(new InputStreamReader(ins));
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                response = sb.toString();
                br.close();
                urlConnection.disconnect();
            } else if (HttpResult == HttpURLConnection.HTTP_NOT_FOUND) {
                response = context.getString(R.string.error_Http_not_found);
            } else if (HttpResult == HttpURLConnection.HTTP_INTERNAL_ERROR) {
                response = context.getString(R.string.error_Http_internal);
            } else {
                response = context.getString(R.string.error_Http_other);
            }
        } catch (MalformedURLException e) {
            urlConnection.disconnect();
            response = "Error";
            CatchList.Report(e);
        } catch (IOException e) {
            urlConnection.disconnect();
            response = "Error";
            CatchList.Report(e);
        }
        return response;
    }


    //Get request method
    public String Response_JSON(Context context, String str) {

        try {
            url = new URL(str);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(200000);
            urlConnection.setReadTimeout(200000);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.connect();

            int HttpResult = urlConnection.getResponseCode();
            if (HttpResult == HttpURLConnection.HTTP_OK) {
                InputStream ins = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader br = new BufferedReader(new InputStreamReader(ins));
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                response = sb.toString();

                br.close();
                urlConnection.disconnect();
            } else if (HttpResult == HttpURLConnection.HTTP_NOT_FOUND) {
                response = context.getString(R.string.error_Http_not_found);
            } else if (HttpResult == HttpURLConnection.HTTP_INTERNAL_ERROR) {
                response = context.getString(R.string.error_Http_internal);
            } else {
                response = context.getString(R.string.error_Http_other);
            }
        } catch (MalformedURLException e) {
            Log.e("er", "er" + e);
            response = "Error";
            CatchList.Report(e);
        } catch (IOException e) {
            Log.e("eri", "eri" + e);
            response = "Error";
            CatchList.Report(e);
        }
        return response;
    }

    public String Response_Post(Context context, String url, JSONObject data, String type) {
        StringBuilder sb = new StringBuilder();
        try {
            Log.e("url", "url" + url);
            this.url = new URL(url);
            urlConnection = (HttpURLConnection) this.url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setUseCaches(false);
            urlConnection.setConnectTimeout(10000);
            urlConnection.setReadTimeout(10000);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.connect();

            OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
            out.write(String.valueOf(data));
            out.close();

            int HttpResult = urlConnection.getResponseCode();
            if (HttpResult == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "utf-8"));
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();

                response = sb.toString();
            } else if (HttpResult == HttpURLConnection.HTTP_NOT_FOUND) {
                response = context.getString(R.string.error_Http_not_found);
            } else if (HttpResult == HttpURLConnection.HTTP_INTERNAL_ERROR) {
                response = context.getString(R.string.error_Http_internal);
            } else {
                response = context.getString(R.string.error_Http_other);
            }

            urlConnection.disconnect();
        } catch (MalformedURLException e) {
            Log.e("e", "e" + e);
            urlConnection.disconnect();
            response = context.getString(R.string.error);
            CatchList.Report(e);
        } catch (IOException e) {
            Log.e("e2", "e2" + e);
            urlConnection.disconnect();
            response = context.getString(R.string.error);
            CatchList.Report(e);
        }

        return response;
    }


    public String Response_Post(Context context, String url, HashMap<String, Object> data, String type) {
        StringBuilder sb = new StringBuilder();
        try {
            this.url = new URL(url);
            urlConnection = (HttpURLConnection) this.url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setUseCaches(false);
            urlConnection.setConnectTimeout(10000);
            urlConnection.setReadTimeout(10000);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.connect();

            OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
            out.write(data.get("data").toString());
            out.close();

            int HttpResult = urlConnection.getResponseCode();

            if (HttpResult == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "utf-8"));
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();
                response = sb.toString();
            } else if (HttpResult == HttpURLConnection.HTTP_NOT_FOUND) {
                response = context.getString(R.string.error_Http_not_found);
            } else if (HttpResult == HttpURLConnection.HTTP_INTERNAL_ERROR) {
                response = context.getString(R.string.error_Http_internal);
            } else {
                response = context.getString(R.string.error_Http_other);
            }

            urlConnection.disconnect();
        } catch (MalformedURLException e) {
            urlConnection.disconnect();
            response = context.getString(R.string.error);
            CatchList.Report(e);
        } catch (IOException e) {
            urlConnection.disconnect();
            response = context.getString(R.string.error);
            CatchList.Report(e);
        }
        return response;
    }


    public String ResponsePost(Context context, String url, String param) {

        StringBuilder sb = new StringBuilder();
        try {
            this.url = new URL(url);
            urlConnection = (HttpURLConnection) this.url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setUseCaches(false);
            urlConnection.setConnectTimeout(10000);
            urlConnection.setReadTimeout(10000);
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            urlConnection.connect();

            OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
            out.write(param);
            out.close();

            int HttpResult = urlConnection.getResponseCode();
            if (HttpResult == 200) {
                response = urlConnection.getHeaderField("Location");
            } else if (HttpResult == 201) {
                response = urlConnection.getHeaderField("Location");
            } else if (HttpResult == 204) {
                response = "204";
            } else if (HttpResult == 304) {
                response = "304";
            } else if (HttpResult == 400) {
                response = "400";
            } else if (HttpResult == 403) {
                response = "403";
            } else if (HttpResult == 410) {
                response = "410";
            } else if (HttpResult == 429) {
                response = "429";
            } else if (HttpResult == 500) {
                response = "500";
            } else {
                response = context.getString(R.string.error_Http_other);
            }

            urlConnection.disconnect();
        } catch (MalformedURLException e) {
            urlConnection.disconnect();
            response = context.getString(R.string.error);
            CatchList.Report(e);
        } catch (IOException e) {
            urlConnection.disconnect();
            response = context.getString(R.string.error);
            CatchList.Report(e);
        }
        return response;
    }

    //Get request method
    public String Response_Post_SKYSCANNER(Context context, String str) {

        try {
            url = new URL(str);
            urlConnection = (HttpURLConnection) url.openConnection();
          //  urlConnection.setConnectTimeout(200000);     
            //urlConnection.setReadTimeout(200000);      
            urlConnection.setConnectTimeout(400000);
            urlConnection.setReadTimeout(400000);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.connect();

            int HttpResult = urlConnection.getResponseCode();
            if (HttpResult == 200) {
                InputStream ins = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader br = new BufferedReader(new InputStreamReader(ins));
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                response = sb.toString();
                br.close();
                urlConnection.disconnect();

            } else if (HttpResult == 201) {
                InputStream ins = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader br = new BufferedReader(new InputStreamReader(ins));
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                response = sb.toString();
                br.close();
                urlConnection.disconnect();

            } else if (HttpResult == 204) {
                response = "204";
            } else if (HttpResult == 304) {
                response = "304";
            } else if (HttpResult == 400) {
                response = "400";
            } else if (HttpResult == 403) {
                response = "403";
            } else if (HttpResult == 410) {
                response = "410";
            } else if (HttpResult == 429) {
                response = "429";
            } else if (HttpResult == 500) {
                response = "500";
            } else {
                response = context.getString(R.string.error_Http_other);
            }

        } catch (MalformedURLException e) {
            Log.e("er", "er" + e);
            response = "Error";
            CatchList.Report(e);
        } catch (IOException e) {
            Log.e("eri", "eri" + e);
            response = "Error";
            CatchList.Report(e);
        }
        return response;
    }


    public String Response_Post_format(Context context, String url, HashMap<String, Object> data, String type) {
        StringBuilder sb = new StringBuilder();
        try {
            this.url = new URL(url);
            urlConnection = (HttpURLConnection) this.url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setUseCaches(false);
            urlConnection.setConnectTimeout(10000);
            urlConnection.setReadTimeout(10000);
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            urlConnection.connect();

            OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
            Log.e("data_new", "data_new" + data.get("data").toString());
            out.write(data.get("data").toString());
            out.close();

            int HttpResult = urlConnection.getResponseCode();
            Log.e("HttpResult_new", "HttpResult_new" + HttpResult);
            if (HttpResult == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "utf-8"));
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();
                response = sb.toString();
            } else if (HttpResult == HttpURLConnection.HTTP_NOT_FOUND) {
                response = context.getString(R.string.error_Http_not_found);
            } else if (HttpResult == HttpURLConnection.HTTP_INTERNAL_ERROR) {
                response = context.getString(R.string.error_Http_internal);
            } else {
                response = context.getString(R.string.error_Http_other);
            }

            urlConnection.disconnect();
        } catch (MalformedURLException e) {
            urlConnection.disconnect();
            response = context.getString(R.string.error);
            CatchList.Report(e);
        } catch (IOException e) {
            urlConnection.disconnect();
            response = context.getString(R.string.error);
            CatchList.Report(e);
        }
        return response;
    }

//    public String Response_Download(Context context, String url_st, String filename, String type) {
//        try {
//            url = new URL("Enter the URL to be downloaded");
//            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//            urlConnection.setRequestMethod("GET");
//            urlConnection.setDoOutput(true);
//            urlConnection.connect();
//
//            File SDCardRoot = Environment.getExternalStorageDirectory().getAbsoluteFile();
//
//            File file = new File(SDCardRoot, filename);
//            if (file.createNewFile()) {
//                file.createNewFile();
//            }
//
//            FileOutputStream fileOutput = new FileOutputStream(file);
//            InputStream inputStream = urlConnection.getInputStream();
//            int totalSize = urlConnection.getContentLength();
//            int downloadedSize = 0;
//            byte[] buffer = new byte[1024];
//            int bufferLength = 0;
//            while ((bufferLength = inputStream.read(buffer)) > 0) {
//                fileOutput.write(buffer, 0, bufferLength);
//                downloadedSize += bufferLength;
//                Log.i("Progress:", "downloadedSize:" + downloadedSize + "totalSize:" + totalSize);
//            }
//            fileOutput.close();
////            if (downloadedSize == totalSize) filepath = file.getPath();
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            filepath = null;
//            e.printStackTrace();
//        }
//        return filepath;
//    }


//    public void downloadFile(Context context,String uRl) {
//
//        URL url = new URL ("file://some/path/anImage.png");
//        InputStream input = url.openStream();
//        try {
//            File storagePath = new File(Environment.getExternalStorageDirectory(),"Trajilis");
//
//            if (!storagePath.exists()) {
//                storagePath.mkdirs();
//            }
//
//            OutputStream output = new FileOutputStream (storagePath, "myImage.png");
//            try {
//                byte[] buffer = new byte[aReasonableSize];
//                int bytesRead = 0;
//                while ((bytesRead = input.read(buffer, 0, buffer.length)) >= 0) {
//                    output.write(buffer, 0, bytesRead);
//                }
//            }
//            finally {
//                output.close();
//            }
//        }
//
//        finally {
//            input.close();
//        }


//
//        File direct = new File(Environment.getExternalStorageDirectory()
//                + "/Trajilis");
//
//        if (!direct.exists()) {
//            direct.mkdirs();
//        }
//
//        DownloadManager mgr = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
//
//        Uri downloadUri = Uri.parse(uRl);
//        DownloadManager.Request request = new DownloadManager.Request(downloadUri);
//
//        request.setAllowedNetworkTypes(
//                DownloadManager.Request.NETWORK_WIFI
//                        | DownloadManager.Request.NETWORK_MOBILE)
//                .setAllowedOverRoaming(false).setTitle("Demo")
//                .setDescription("Something useful. No, really.")
//                .setDestinationInExternalPublicDir("/AnhsirkDasarpFiles", "fileName.jpg");
//
//        mgr.enqueue(request);

//    }


    public String downloadToSdCard(Context context, String downloadUrl, String imageName, String subFolderName) {
        try {

            Log.e("downloadUrl","downloadUrl"+downloadUrl+"--"+imageName+"--"+subFolderName);
            url = new URL(downloadUrl);
            File myDir = new File(Environment.getExternalStorageDirectory(), Config.APPNAME);
            if (!myDir.exists()) {
                myDir.mkdir();
            }

            File mSubDir = new File(myDir, subFolderName);
            if (!mSubDir.exists()) {
                mSubDir.mkdir();
            }

            File file = new File(mSubDir, imageName);

            URLConnection ucon = url.openConnection();
            urlConnection = (HttpURLConnection) ucon;
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = urlConnection.getInputStream();
            }

            FileOutputStream fos = new FileOutputStream(file);
            int totalSize = urlConnection.getContentLength();
            int downloadedSize = 0;
            byte[] buffer = new byte[1024];
            int bufferLength = 0;
            while ((bufferLength = inputStream.read(buffer)) > 0) {
                fos.write(buffer, 0, bufferLength);
                downloadedSize += bufferLength;
                Log.e("Progress:", "downloadedSize:" + downloadedSize + "totalSize:" + totalSize);
            }

//            fos.close();
//            inputStream.close();
            response = String.valueOf(file);
        } catch (IOException io) {
            response = context.getString(R.string.error);
            Log.e("er", "er" + io);
            CatchList.Report(io);
            io.printStackTrace();
        } catch (Exception e) {
            response = context.getString(R.string.error);
            Log.e("erag", "erag" + e);
            CatchList.Report(e);
            e.printStackTrace();
        } finally {
//            try {
//                if(inputStream != null){
//                    inputStream.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }

        return response;
    }
}