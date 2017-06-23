package omnia.downloadimage;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Lenovo-pc on 22/06/2017.
 */

public class DownloadImage extends Fragment implements Runnable{
    View v;
    EditText url;
    String urlStr;
    ImageView imageView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.downloadimage,container,false);
        url=(EditText)v.findViewById(R.id.url);
        Button download=(Button)v.findViewById(R.id.download);
        imageView=(ImageView)v.findViewById(R.id.imageView);
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread=new Thread(DownloadImage.this);
                thread.start();
            }
        });
        return v;
    }

    @Override
    public void run() {
        urlStr=url.getText().toString();
        if(!urlStr.isEmpty()){
            try {
                URL imageUrl=new URL(urlStr);
                HttpURLConnection httpURLConnection=(HttpURLConnection) imageUrl.openConnection();
                httpURLConnection.connect();
                InputStream inputStream=httpURLConnection.getInputStream();
                final Bitmap image= BitmapFactory.decodeStream(inputStream);
                inputStream.close();
                httpURLConnection.disconnect();

                imageView.post(new Runnable() {
                    @Override
                    public void run() {
                        imageView.setImageBitmap(image);

                    }
                });

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
