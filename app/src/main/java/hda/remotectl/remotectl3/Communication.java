package hda.remotectl.remotectl3;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class Communication {
    private String ipAddress;
    public Communication(String myIP){
        ipAddress = myIP;
    }

    public void sendCommandToTvServer(String message) {
        HttpRequest hr = new HttpRequest(ipAddress, 1000, true);

        try {
            hr.execute(message);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONObject getChannelsFromTvServer() {
        HttpRequest hr = new HttpRequest(ipAddress, 1000, true);

        try {
            JSONObject channellist = hr.execute("scanChannels");
            return channellist;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
       return null;
    }


}
