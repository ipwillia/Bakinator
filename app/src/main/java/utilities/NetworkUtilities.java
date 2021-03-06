package utilities;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * This method taken from Udacity exercise S04.02. Link here: https://github.com/udacity/ud851-Sunshine/blob/student/S04.02-Exercise-DisplayDayForecast/app/src/main/java/com/example/android/sunshine/utilities/NetworkUtils.java
 */

public class NetworkUtilities {

    private static final String LOG_TAG = NetworkUtilities.class.getSimpleName();

    private static final String REDIRECT_LOCATION = "Location";

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        Log.d(LOG_TAG, "Opening connection");
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        try {

            while(urlConnection.getResponseCode() == HttpURLConnection.HTTP_MOVED_PERM ||
                    urlConnection.getResponseCode() == HttpURLConnection.HTTP_MOVED_TEMP) {
                String redirectUrl = urlConnection.getHeaderField(REDIRECT_LOCATION);
                Log.d(LOG_TAG, "Redirecting to " + redirectUrl);
                urlConnection = (HttpURLConnection) new URL(redirectUrl).openConnection();
            }
            Log.d(LOG_TAG, "Response: " + urlConnection.getResponseCode() + ": " + urlConnection.getResponseMessage());

            Log.d(LOG_TAG, "Getting input stream");
            InputStream in = urlConnection.getInputStream();

            Log.d(LOG_TAG, "Scanning");
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");



            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                Log.d(LOG_TAG, "Has input");
                return scanner.next();
            } else {
                Log.d(LOG_TAG, "No input");
                return null;
            }
        } finally {
            Log.d(LOG_TAG, "Disconnecting");
            urlConnection.disconnect();
        }
    }
}
