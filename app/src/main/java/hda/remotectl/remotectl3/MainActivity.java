package hda.remotectl.remotectl3;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {



    private HttpRequest hr;

    private boolean isTvOn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

isTvOn = false;


    }


    public void Power(View v)
    {

        sendCommandToTvServer("standby=1");
    }



    public void sendCommandToTvServer(String message)  {
        HttpRequest hr  = new HttpRequest("172.16.205.160",1000,true);

        try {
            hr.execute(message);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
