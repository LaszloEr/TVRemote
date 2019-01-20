package hda.remotectl.remotectl3;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    private HttpRequest hr;

    private boolean isTvOn;
    private boolean zoomMain;

    private int Volume;
    private TextView VolumeText;
    private ArrayList<Channel> channels = new ArrayList<Channel>();
    private JSONObject channellist;
    Communication comm;
    String IpAddress = "192.168.178.40";
    private ChannelAdapter adapter; // adapter
    private ArrayList<Channelitem> items = new ArrayList<Channelitem>(); // model

    public MainActivity() {
        Volume = 50;
        isTvOn = false;
        comm = new Communication(IpAddress);
        zoomMain = false;



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isTvOn = false;


        adapter = new ChannelAdapter(this, items);

        ListView lstView = (ListView) findViewById(R.id.channels);
        lstView.setAdapter(adapter);

        adapter.setCommunication(comm);
    }


    public void Power(View v) {

        if (!isTvOn) {
            comm.sendCommandToTvServer("standby=0");
            isTvOn = true;
        } else {
            comm.sendCommandToTvServer("standby=1");
            isTvOn = false;

        }

    }
    public void ZoomMain(View v) {

        if (!zoomMain) {
            comm.sendCommandToTvServer("zoomMain=1");
            zoomMain = true;
        } else {
            comm.sendCommandToTvServer("zoomMain=0");
            zoomMain = false;

        }

    }

    public void VolumeDown(View v) {

        if (Volume > 0) {
            Volume--;
        }
        comm.sendCommandToTvServer("volume=" + Volume);
        VolumeText = (TextView) findViewById(R.id.lblVolume);
        VolumeText.setText(Volume + "%");
    }


    public void VolumeUp(View v) {

        if (Volume < 100) {
            Volume++;
        }
        comm.sendCommandToTvServer("volume=" + Volume);
        VolumeText = (TextView) findViewById(R.id.lblVolume);
        VolumeText.setText(Volume + "%");
    }


    public void scanChannels() {
        channellist = comm.getChannelsFromTvServer();
        createChannelListFromJSON();
        addNewItem();
    }

    public void Channelsearchbutton(View v) {

        scanChannels();

    }

    protected void createChannelListFromJSON() {
        try {

            // txtLog.setText(json.names().toString());
            JSONArray array = channellist.getJSONArray("channels");

            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                Channel c = new Channel(
                        obj.getString("frequency"),
                        obj.getString("channel"),
                        obj.getInt("quality"),
                        obj.getString("program"),
                        obj.getString("provider"));
                channels.add(c);
                Log.i("ChannelScan", "\n " + c.getProgram() + "\t\t\t\t (Channel: " + c.getChannel() + ")");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void addNewItem() {

        Iterator itr = channels.iterator();

        while (itr.hasNext()) {
            Channel element = (Channel) itr.next();
            Channelitem item = new Channelitem(element.getProgram(), element.getChannel());
            adapter.add(item);
        }

        // alternatively directly add the item to the adapter; no notification needed
//        items.add(item); // if item is added to the arraylist, notify need to be called explicitly
//        adapter.notifyDataSetChanged();
//        final View temp = view;
//        temp.animate().setDuration(500).alpha(1).withEndAction(new Runnable() {
//            @Override
//            public void run() {
//                items.add(item);
//                adapter.notifyDataSetChanged();
//                temp.setAlpha(0);
//            }
//        });
//         adapter.add(item);
        //Log.i(this.getClass().getSimpleName(), "Item generated and added to ItemsArray... " + items.size() + " Items");
    }
}
