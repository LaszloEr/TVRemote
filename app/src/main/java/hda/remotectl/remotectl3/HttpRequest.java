

/**
 * This class encapsulates the communication with the TV Simulator via HTTP protocol.
 * Its methods can be called for the first step directly from the user interface thread.
 * However, in this case the user interface might freeze depending on delay in the network.
 * Moreover the command "scanChannels" takes about 4 seconds to be processed...
 *
 * So for the production case the methods of this class must be called from
 * method "doInBackground" of a subclass of "AsyncTask".
 *
 * This class needs the following permissions inside <manifest></manifest> in AndroidManifest.xml:
 *    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
 *    <uses-permission android:name="android.permission.INTERNET" />
 *
 * @author      Bernhard Kreling, <bernhard.kreling@h-da.de>
 * @version     1.3, 2015-12-03
 */

package hda.remotectl.remotectl3;

        import android.os.NetworkOnMainThreadException;
        import android.os.StrictMode;
        import org.json.JSONException;
        import org.json.JSONObject;
        import java.io.BufferedReader;
        import java.io.IOException;
        import java.io.InputStream;
        import java.io.InputStreamReader;
        import java.net.HttpURLConnection;
        import java.net.URL;

public class HttpRequest {

    protected String ipAddress;
    protected int timeoutMillis;

    /**
     * Constructor to be used if instantiated in a subclass of AsyncTask.
     *
     * @param ipAddress        the HTTP request will be sent to this IP address, e.g. "141.100.0.34"
     *
     * @param timeoutMillis    after that time (given in milliseconds) method sentHttp will
     *                         stop waiting for a response
     *
     */
    public HttpRequest(String ipAddress, int timeoutMillis) {
        this.ipAddress = ipAddress;
        this.timeoutMillis = timeoutMillis;
    }

    /**
     * Constructor to be used if instantiated in the user interface thread.
     *
     * @param ipAddress        the HTTP request will be sent to this IP address, e.g. "141.100.0.34"
     *
     * @param timeoutMillis    after that time (given in milliseconds) method sentHttp will
     *                         stop waiting for a response
     *
     */
    public HttpRequest(String ipAddress, int timeoutMillis, boolean onMainThread) {
        this.ipAddress = ipAddress;
        this.timeoutMillis = timeoutMillis;

        // This is a dirty trick to avoid the NetworkOnMainThreadException.
        // It should be used for the first step in the lab only; later on HttpRequest should be embedded into AsyncTask!
        if (onMainThread) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
    }

    /**
     * This function should be used if called from the UI thread.
     * If AsyncTask is used, call AsyncTask.execute instead.
     * The function sends one or more command(s) to the TV Simulator, waits for the response
     * and evaluates it.
     *
     * @param parameters    a list of command=value pairs separated by &
     *                      see TestTVInterface.htm for valid commands and their values
     *
     * @return              see the response in TestTVInterface.htm for the command "scanChannels".
     *                      For all other commands just {"status":"ok"}
     */
    public JSONObject execute(String parameters)
            throws IOException, JSONException, IllegalArgumentException {
        try {
            return sendHttp(parameters);
        }
        catch (NetworkOnMainThreadException e) {
            // unfortunately this exception does not carry a message and
            // therefore must be explained this way:
            throw new IOException("network should not be accessed on main thread");
        }
    }

    /**
     * The function sends one or more command(s) to the TV Simulator, waits for the response
     * and evaluates it.
     * It can be called from method "doInBackground" of a subclass of "AsyncTask".
     *
     * @param parameters    a list of command=value pairs separated by &
     *                      see TestTVInterface.htm for valid commands and their values
     *
     * @return              see the response in TestTVInterface.htm for the command "scanChannels".
     *                      For all other commands just {"status":"ok"}
     */
    public JSONObject sendHttp(String parameters)
            throws IOException, JSONException, IllegalArgumentException {
        if (ipAddress == null || ipAddress.isEmpty())
            throw new IllegalArgumentException("IP address is undefined " + parameters);
        URL url = new URL("http", ipAddress, 80, "tv?" + parameters);

        // to be done:
        // uncomment the following test output if required and watch window "6: Android"
        //
        // Log.i("TV Remote Control", getClass().getSimpleName() + " " + url.toExternalForm());

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(timeoutMillis);
        if (conn.getResponseCode() != 200)
            throw new IOException(conn.getResponseMessage());
        return readHttpResponseBody(conn.getInputStream());
    }

    /**
     * The function reads and returns the response from the TV Simulator.
     *
     * @param in    the input stream obtained from an open HttpURLConnection
     *
     * @return      see the response in TestTVInterface.htm for the command "scanChannels".
     *              For all other commands just {"status":"ok"}
     * @throws IOException      if the response status is not "ok"
     */
    private JSONObject readHttpResponseBody(InputStream in)
            throws IOException, JSONException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String result = "";
        String line;
        while ((line = reader.readLine()) != null) {
            result += line;
        }
        JSONObject httpResponse = new JSONObject(result);
        String httpStatus = httpResponse.getString("status");
        if (!httpStatus.equals("ok"))
            throw new IOException("TV returns status=" + httpStatus);
        return httpResponse;
    }
}
