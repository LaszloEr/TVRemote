package hda.remotectl.remotectl3;

/**
 * Created by zander on 05.12.17.
 */

public class Channel {

    private String frequency;
    private String channel;
    private int quality;
    private String program;
    private String provider;

    public Channel(String frequency, String channel, int quality, String program, String provider) {
        this.frequency = frequency;
        this.channel = channel;
        this.quality = quality;
        this.program = program;
        this.provider = provider;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }
}
