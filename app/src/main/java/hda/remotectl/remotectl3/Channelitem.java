package hda.remotectl.remotectl3;

import java.util.Date;

/**
 * Created by zander on 29.04.17.
 */

public class Channelitem {
    private String channelname;
    private String channelnumber;
    private boolean isCurrentChannel;


    public Channelitem(String newChannelname, String newChannelnumber) {
        this.channelname = newChannelname;
        this.channelnumber = newChannelnumber;
        this.isCurrentChannel = false;
    }

    public String getChannelname() {
        return channelname;
    }

    public void setChannelname(String newChannelname) {
        this.channelname = newChannelname;
    }

    public String getChannelnumber() {
        return channelnumber;
    }

    public void setChannelnumber(String newChannelnumber) {
        this.channelnumber = newChannelnumber;
    }

    public boolean getIsCurrentChannel() {return isCurrentChannel;}

    public void setIsCurrentChannel(boolean newIsCurrentChannel){this.isCurrentChannel = newIsCurrentChannel; }

}