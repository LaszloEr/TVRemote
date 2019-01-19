package hda.remotectl.remotectl3;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import hda.remotectl.remotectl3.MainActivity;

import static android.app.PendingIntent.getActivity;


/**
 * Created by zander on 30.04.17.
 */

public class ChannelAdapter extends ArrayAdapter<Channelitem> {
    Communication comm;
    final static private String TAG = "NewsItemAdapter";

    public ChannelAdapter(Context context, ArrayList<Channelitem> items) {
        super(context, 0, items);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        final Channelitem item = getItem(position);

        if (convertView == null) {
            Log.i(TAG, "convertView == NULL: Inflating new view...");
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.channel_item, parent, false);
        }

        final View temp = convertView;

        Button btnChannel = (Button) convertView.findViewById(R.id.btnChannel);

        btnChannel.setText(item.getChannelname());

        btnChannel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                comm.sendCommandToTvServer("channelMain=" + item.getChannelnumber());
                Log.i(TAG, "Switching to channel: #" + item.getChannelnumber() + ": " + item.getChannelname());

            }
        });


        return convertView;
    }

    public void setCommunication(Communication newComm) {
        this.comm = newComm;
    }


}
