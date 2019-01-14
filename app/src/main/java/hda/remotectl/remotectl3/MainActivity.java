package hda.remotectl.remotectl3;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private HttpRequest hr;

    private boolean isTvOn;
    private int Volume;
    private TextView VolumeText;


    public MainActivity() {
        Volume = 50;
        isTvOn = false;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

isTvOn = false;


    }


    public void Power(View v) {

        if (!isTvOn) {
            sendCommandToTvServer("standby=0");
            isTvOn = true;
        } else {
            sendCommandToTvServer("standby=1");
            isTvOn = false;

        }

    }

    public void VolumeDown(View v)
    {

        if (Volume > 0)
        {
            Volume--;
        }
        sendCommandToTvServer("volume=" + Volume);
        VolumeText = (TextView) findViewById(R.id.lblVolume);
        VolumeText.setText(Volume + "%");
    }


    public void VolumeUp(View v)
    {

        if (Volume < 100)
        {
            Volume++;
        }
        sendCommandToTvServer("volume=" + Volume);
        VolumeText = (TextView) findViewById(R.id.lblVolume);
        VolumeText.setText(Volume + "%");
    }


    public void sendCommandToTvServer(String message)  {
        HttpRequest hr  = new HttpRequest("192.168.178.40",1000,true);

        try {
            hr.execute(message);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
