package com.example.tedyi2005.appiatechnicaltest;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;



public class AdListActivity extends Activity {

    private static final String TAG = "AdListActivity";

    static final String URL = "http://ads.appia.com/getAds?id=236&password=OVUJ1DJN&siteId=4288&deviceId=4230&" +
            "sessionId=techtestsession&totalCampaignsRequested=10&lname=Kidanne";

    // XML node names?
    static final String APP_ID = "appId";
    static final String AVERAGERATINGIMAGEURL = "averageRatingImageURL";
    static final String BIDRATE = "bidRate";
    static final String CALLTOACTION = "callToAction";
    static final String CAMPAIGNDISPLAYORDER = "campaignDisplayOrder";
    static final String CAMPAIGNID = "campaignId";
    static final String CAMPAIGNTYPEID = "campaignId";
    static final String CATEGORYNAME = "categoryName";
    static final String CLICKPROXYURL = "clickProxyURL";
    static final String CREATIVEID = "creativeId";
    static final String HOMESCREEN = "homeScreen";
    static final String IMPRESSIONTRACKINGURL = "impressionTrackingURL";
    static final String ISRANDOMPICK = "isRandomPick";
    static final String MINOSVERSION = "minOSVersion";
    static final String NUMBEROFRATINGS = "numberOfRatings";
    static final String PRODUCTDESCRIPTION = "productDescription";
    static final String PRODUCTID = "productId";
    static final String PRODUCTNAME = "productName";
    static final String PRODUCTTHUMBNAIL = "productThumbnail";
    static final String RATING = "rating";
    private List<AdItem> adItemList = new ArrayList<AdItem>();
    private RecyclerView mRecyclerView;
    private AdRecyclerAdapter adapter;
    ProgressBar webservicePG;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Allow activity to show indeterminate progressbar */
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

        setContentView(R.layout.content_ad_list);

        /* Initialize recyclerview */
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        webservicePG = (ProgressBar) findViewById(R.id.progressBar1);
        new AsyncHttpTask().execute();
    }

    public class AsyncHttpTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            setProgressBarIndeterminateVisibility(true);
            webservicePG.setVisibility(View.VISIBLE);
        }

        @Override
        protected Integer doInBackground(String... params) {
            InputStream inputStream = null;
            Integer result = 0;
            HttpURLConnection urlConnection = null;

            try {

                if (null == adItemList) {
                    adItemList = new ArrayList<AdItem>();
                }
                URL xmlurl = new URL(URL);
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();

                Document doc = db.parse(new InputSource(xmlurl.openStream()));
                NodeList nodeList = doc.getElementsByTagName("ad");

                Element docEle = doc.getDocumentElement();

                NodeList nl = docEle.getChildNodes();
                if (nl != null && nl.getLength() > 0) {
                    for (int i = 0; i < nl.getLength(); i++) {
                        if (nl.item(i).getNodeType() == Node.ELEMENT_NODE) {
                            Element el = (Element) nl.item(i);
                            if (el.getNodeName().contains("ad")) {

                                AdItem item = new AdItem();
                                item.setAppId(el.getElementsByTagName(APP_ID).item(0).getTextContent());
                                item.setAverageRatingImageURL(el.getElementsByTagName(AVERAGERATINGIMAGEURL).item(0).getTextContent());
                                item.setBidRate(el.getElementsByTagName(BIDRATE).item(0).getTextContent());
                                item.setCallToAction(el.getElementsByTagName(CALLTOACTION).item(0).getTextContent());
                                item.setCampaignDisplayOrder(el.getElementsByTagName(CAMPAIGNDISPLAYORDER).item(0).getTextContent());
                               /* item.setCampaignId(el.getElementsByTagName("campaignId").item(0).getTextContent());
                                item.setCategoryName(el.getElementsByTagName("categoryName").item(0).getTextContent());
                                item.setClickProxyURL(el.getElementsByTagName("clickProxyURL").item(0).getTextContent());
                                item.setCreativeId(el.getElementsByTagName("creativeId").item(0).getTextContent());
                                item.setHomeScreen(el.getElementsByTagName("homeScreen").item(0).getTextContent());
                                item.setImpressionTrackingURL(el.getElementsByTagName("impressionTrackingURL").item(0).getTextContent());
                                item.setIsRandomPick(el.getElementsByTagName("isRandomPick").item(0).getTextContent());
                                item.setMinOSVersion(el.getElementsByTagName("minOSVersion").item(0).getTextContent());
                                item.setNumberOfRatings(el.getElementsByTagName("numberOfRatings").item(0).getTextContent());*/
                                item.setProductDescription(el.getElementsByTagName(PRODUCTDESCRIPTION).item(0).getTextContent());
                                item.setProductId(el.getElementsByTagName(PRODUCTID).item(0).getTextContent());
                                item.setProductName(el.getElementsByTagName(PRODUCTNAME).item(0).getTextContent());
                                item.setProductThumbnail(el.getElementsByTagName(PRODUCTTHUMBNAIL).item(0).getTextContent());
                                item.setRating(el.getElementsByTagName(RATING).item(0).getTextContent());

                                Log.e("ITEM ", item.toString());
                                adItemList.add(item);
                            }
                        }
                    }

                }
                result = 1;
            } catch (Exception e) {
                result = 0;
                Log.d(TAG, e.getLocalizedMessage());
            }

            return result; //"Failed to fetch data!";
        }

        @Override
        protected void onPostExecute(Integer result) {

            setProgressBarIndeterminateVisibility(false);
            // Make Progress Bar invisible
            webservicePG.setVisibility(View.INVISIBLE);
            /* Download complete. Lets update UI */
            if (result == 1) {
                adapter = new AdRecyclerAdapter(AdListActivity.this, adItemList);
                mRecyclerView.setAdapter(adapter);
            } else {
                Log.e(TAG, "Failed to fetch data!");
            }

        }
    }
}


