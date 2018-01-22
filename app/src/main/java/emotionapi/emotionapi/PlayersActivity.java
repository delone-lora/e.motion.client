package emotionapi.emotionapi;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.util.HashMap;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.client.utils.URIBuilder;
import cz.msebera.android.httpclient.entity.ByteArrayEntity;
import cz.msebera.android.httpclient.impl.client.HttpClients;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import cz.msebera.android.httpclient.util.EntityUtils;

public class PlayersActivity extends AppCompatActivity
        implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener
{
    SliderLayout sliderShow;
    private String start = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players);

        sliderShow = (SliderLayout) findViewById(R.id.slider);

        HashMap<String,Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("Arjen Robben",R.drawable.arjen_robben);
        file_maps.put("Arturo Vidal",R.drawable.arturo_vidal);
        file_maps.put("Franck Ribery",R.drawable.franck_ribery);
        file_maps.put("James Rodriguez",R.drawable.james_rodriguez);
        file_maps.put("Jerome Boateng",R.drawable.jerome_boateng);
        file_maps.put("Joshua Kimmich",R.drawable.joshua_kimmich);
        file_maps.put("Jupp Heynckes",R.drawable.jupp_heynckes);
        file_maps.put("Manuel Neuer",R.drawable.manuel_neuer);
        file_maps.put("Mats Hummels",R.drawable.mats_hummels);
        file_maps.put("Robert Lewandowski",R.drawable.robert_lewandowski);
        file_maps.put("Thomas Mueller",R.drawable.thomas_mueller);

        for(String name : file_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);

            sliderShow.addSlider(textSliderView);
        }
        //sliderShow.setCustomIndicator((PagerIndicator) findViewById(R.id.custom_indicator));

        sliderShow.setPresetTransformer(SliderLayout.Transformer.RotateUp);
        sliderShow.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderShow.setCustomAnimation(new DescriptionAnimation());
        //sliderShow.setDuration(4000);
        sliderShow.addOnPageChangeListener(this);

    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        Toast.makeText(this,slider.getBundle().get("extra") + "", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        sliderShow.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void choosePlayer(View view)
    {
        PlayersActivity.ConnectionStarter connectionStarter = new ConnectionStarter();
        connectionStarter.execute();

        if(start.equals("200"))
        {

            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("abcd", "dasd");
            startActivity(intent);
        }
    }


    // asynchronous class which makes the api call in the background
    private class ConnectionStarter extends AsyncTask<Void, Void, String> {

        // this function is called before the api call is made
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            resultText.setText("Getting results...");
        }


        // this function is called when the api call is made
        @Override
        protected String doInBackground(Void... params) {
            HttpClient httpclient = HttpClients.createDefault();
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            try {
                URIBuilder builder = new URIBuilder("https://fcbemotion.azurewebsites.net/api/Emotion/start");

                URI uri = builder.build();
                HttpPost request = new HttpPost(uri);
                request.setHeader("Content-Type", "application/json");
                // Request body.The parameter of setEntity converts the image to base64
                //BasicNameValuePair entity1 = new BasicNameValuePair("player_name", "Robben");
                //request.setEntity(new UrlEncodedFormEntity((Iterable<? extends NameValuePair>) entity1));

                // getting a response and assigning it to the string res
                HttpResponse response = httpclient.execute(request);
                HttpEntity entity = response.getEntity();
                String res = EntityUtils.toString(entity);
                res = String.valueOf(response.getStatusLine().getStatusCode());
                Log.d("esss",res);
                return res;

            }
            catch (Exception e){
                return "null";
            }

        }

        // this function is called when we get a result from the API call
        @Override
        protected void onPostExecute(String result) {
            JSONArray jsonArray = null;
            try {
                // convert the string to JSONArray
//                jsonArray = new JSONArray(result);
//                String result = "";
//                // get the scores object from the results
//                for(int i = 0;i<jsonArray.length();i++) {
//                    JSONObject jsonObject = new JSONObject(jsonArray.get(i).toString());
//                    JSONObject scores = jsonObject.getJSONObject("scores");
//                    double max = 0;
//                    String emotion = "";
//                    for (int j = 0; j < scores.names().length(); j++) {
//                        if (scores.getDouble(scores.names().getString(j)) > max) {
//                            max = scores.getDouble(scores.names().getString(j));
//                            emotion = scores.names().getString(j);
//                        }
//                    }
//                    result += emotion + "\n";
//                }
//                resultText.setText(emotions);
                 start= result;

            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Could not establish connection with the monitor. Try afain", Toast.LENGTH_LONG);
            }
        }
    }
}
