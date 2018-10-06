package com.teige.tim.randomnamenorsk.utils;

import android.Manifest;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.teige.tim.randomnamenorsk.BuildConfig;
import com.teige.tim.randomnamenorsk.R;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DownloadHelper extends AppCompatActivity {
    private DownloadManager manager = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        assert manager != null;
    }

    public void downloadFile(int type) {
        String dlDescription = "";
        String dlTitle = "";
        String uri = "";
        String dlFilename = getString(type);

        switch (type) {
            case R.string.female_names:
                dlDescription = getString(R.string.female_download_description);
                dlTitle = getString(R.string.female_download_title);
                uri = getString(R.string.female_download_uri);
                dlFilename = getString(type);
                break;
            case R.string.male_names:
                dlDescription = getString(R.string.male_download_description);
                dlTitle = getString(R.string.male_download_title);
                uri = getString(R.string.male_download_uri);
                dlFilename = getString(type);
                break;
            case R.string.last_names:
                dlDescription = getString(R.string.lastname_download_description);
                dlTitle = getString(R.string.lastname_download_title);
                uri = getString(R.string.lastname_download_uri);
                dlFilename = getString(type);
                break;
        }

        if (isExternalStorageWritable() && isExternalStorageReadable()) {

            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(uri));
            request.setDescription(dlDescription);
            request.setTitle(dlTitle);
            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOCUMENTS, dlFilename);
            manager.enqueue(request);
            registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.i("PERMISSIONS", "Request for permission with code: " + requestCode + "was granted");

                } else {
                    Log.i("PERMISSIONS", "Permission with code: " + requestCode + " was not granted");
                    this.finishAndRemoveTask();
                }
                return;
            }
            case 2: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.i("PERMISSIONS", "Request for permission with code: " + requestCode + "was granted");

                } else {
                    Log.i("PERMISSIONS", "Permission with code: " + requestCode + " was not granted");
                    this.finishAndRemoveTask();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    private boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    /* Checks if external storage is available to at least read */
    private boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }

    private BroadcastReceiver onComplete = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            Bundle extras = intent.getExtras();
            assert extras != null;
            DownloadManager.Query query = new DownloadManager.Query();
            query.setFilterById(extras.getLong(DownloadManager.EXTRA_DOWNLOAD_ID));
            Cursor c = manager.query(query);
            if (c.moveToFirst()) {
                int status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
                if (status == DownloadManager.STATUS_SUCCESSFUL) {
                    String filePath = c.getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                    parseNameFile(filePath);
                }
            }

        }
    };

    private void parseNameFile(String filePath) {
        String filename = filePath.substring(filePath.lastIndexOf('/') + 1);

        try {
            mapCountObject obj = createCountMapFromDownload(filePath);
            Map<String, Integer> nameCountMap = obj.map;
            Integer totalCount = obj.count;
            try {
                createLocalFrequencyFile(filename, nameCountMap, totalCount);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
            if (BuildConfig.DEBUG) {
                Log.d("FILEMANAGER", getFilesDir().toString());
            }
        }
    }

    private mapCountObject createCountMapFromDownload(String filePath) throws IOException {

        File file = new File(filePath);
        FileInputStream data = new FileInputStream(file);
        BufferedReader bData = new BufferedReader(new InputStreamReader(data));
        Map<String, Integer> nameCountMap = new HashMap<>();
        int totalCount = 0;
        //Ignore the header
        String bufferedLine = bData.readLine();
        while ((bufferedLine = bData.readLine()) != null) {
            String[] splitLine = bufferedLine.split(";");
            List<Integer> counts = new ArrayList<Integer>();
            for (int i = 1; i < splitLine.length; i++) {
                try {
                    counts.add(Integer.parseInt(splitLine[i]));
                } catch (NumberFormatException ignored) {
                }
            }
            Integer sum = 0;
            for (Integer i : counts) {
                sum += i;
            }
            nameCountMap.put(splitLine[0], sum);
            totalCount += sum;
        }
        return new mapCountObject(nameCountMap, totalCount);
    }

    private void createLocalFrequencyFile(String filename, Map<String, Integer> nameCountMap, Integer totalCount) throws IOException {
        FileOutputStream out = openFileOutput(filename, Context.MODE_PRIVATE);
        for (Map.Entry<String, Integer> entry : nameCountMap.entrySet()) {
            float frequency = ((float)entry.getValue() / totalCount);
            out.write((entry.getKey() + "," + Float.toString(frequency) +
                    System.getProperty("line.separator")).getBytes());
        }
        out.close();
    }

    private void handlePermissions(String permission) {
        if (ContextCompat.checkSelfPermission(this, permission)
                != PackageManager.PERMISSION_GRANTED) {
            switch (permission) {
                case Manifest.permission.WRITE_EXTERNAL_STORAGE:
                    ActivityCompat.requestPermissions(this,
                            new String[]{permission},
                            1);
                    return;
                case Manifest.permission.INTERNET:
                    ActivityCompat.requestPermissions(this,
                            new String[]{permission},
                            2);
                    return;
                default:
                    Log.w("PERMISSIONS", "Trying to handle permission: " + permission + ". No handle found");
                    return;
            }
        }
    }
    private class mapCountObject {
        public Map<String, Integer> map;
        public Integer count;

        public mapCountObject(Map<String, Integer> map, Integer count) {
            this.map = map;
            this.count = count;
        }
    }
}
