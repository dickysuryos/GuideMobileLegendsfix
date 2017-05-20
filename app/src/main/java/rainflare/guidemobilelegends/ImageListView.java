package rainflare.guidemobilelegends;

/**
 * Created by law on 1/29/16.
 */

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

//import com.google.android.gms.ads.MobileAds;

public class ImageListView extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ListView listView;
    public String JSON_STRING;
public  String link;
   public static String aw;
    public static final String GET_IMAGE_URL = "http://gopacitan.co.id/guideMobileLegends/get_alldata.php";
    public GetAlImages getAlImages;
    ImageView like;
    public static String name_hero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-4104040526671108~6013609079");

        //listView = (ListView) findViewById(R.id.listView);
        //  listView.setOnItemClickListener(this);
        AdView mAdView = (AdView) findViewById(R.id.adView);
       // mAdView.setAdSize(AdSize.BANNER);
     //   mAdView.setAdUnitId("ca-app-pub-4104040526671108/8129470671");
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        getURLs();



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        listView = (ListView) findViewById(R.id.listView);
        //registerForContextMenu(listView);
        //listView.setOnCreateContextMenuListener(this);
        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){



            @Override
            public void onItemClick(AdapterView<?>parent, View view, int position, long id) {

                // listView.getItemAtPosition(position);
                name_hero = listView.getItemAtPosition(position).toString();
                ///  name_hero.equals(listView.getItemAtPosition(position));

                // config.hero_name = name_hero.toString();
                // Toast.makeText(ImageListView.this,name_hero, Toast.LENGTH_SHORT).show();
                //  Intent intent;
                //    intent = new Intent(ImageListView.this,Main_web.class);
                //   intent.putExtra(config.TAG_link,link);

                Intent intent;
                intent = new Intent(ImageListView.this,Main_web.class);
                intent.putExtra(config.id_hero,name_hero);
                gethero();
                intent.putExtra(aw,link);

                //  intent.putExtra(config.hero_name,name_hero);


                //     startActivity(intent);

                //based on item add info to intent


                //   Toast.makeText(ImageListView.this, config.hero_name, Toast.LENGTH_SHORT).show();

            }

        });
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_youtube) {

            try {


                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + "BhucGydL6uM"));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            } catch (ActivityNotFoundException e) {

                // youtube is not installed.Will be opened in other available apps

                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtube.com/watch?v=BhucGydL6uM" + id));
                startActivity(i);
            }

        } else if (id == R.id.nav_like) {

            Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
            String facebookUrl = getFacebookPageURL(this);
            facebookIntent.setData(Uri.parse(facebookUrl));
            startActivity(facebookIntent);

        }
        else if(id == R.id.nav_rate){
            Uri uri = Uri.parse("market://details?id="+ImageListView.this.getPackageName());
            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
            // To count with Play market backstack, After pressing back button,
            // to taken back to our application, we need to add following flags to intent.
            goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            try {
                startActivity(goToMarket);
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/details?id="+ImageListView.this.getPackageName())));
            }
        }
        else if(id == R.id.nav_more){
            Uri uri = Uri.parse("market://developer?id=Rain%20Flare&hl=in");
            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
            // To count with Play market backstack, After pressing back button,
            // to taken back to our application, we need to add following flags to intent.
            goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            try {
                startActivity(goToMarket);
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/developer?id=Rain%20Flare&hl=in")));
            }

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    public String getFacebookPageURL(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) { //newer versions of fb app
                return "fb://facewebmodal/f?href=" + "https://www.facebook.com/RAINFLAREDEV";
            } else { //older versions of fb app
                return "fb://page/" + "RAINFLAREDEV";
            }
        } catch (PackageManager.NameNotFoundException e) {
            return "https://www.facebook.com/RAINFLAREDEV"; //normal web url
        }
    }
    private void getImages() {
        class GetImages extends AsyncTask<Void, Void, Void> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ImageListView.this, "Downloading images...", "Please wait...", false, false);
            }

            @Override
            protected void onPostExecute(Void v) {
                super.onPostExecute(v);
                loading.dismiss();
                //   Toast.makeText(ImageListView.this,GetAlImages.bitmaps.toString().trim(),Toast.LENGTH_LONG).show();
                CustomList customList = new CustomList(ImageListView.this, GetAlImages.id_hero , GetAlImages.nama, GetAlImages.bitmaps);

                //     ListAdapter adapter = new SimpleAdapter(
                //             ImageListView.this,GetAlImages.list, R.layout.list_item,
                //                new Bitmap[] new String[](config.TAG_Id_nama_konveksi,config.TAG_Nama_konveksi,GetAlImages.bitmaps},
                //                 new int[]{R.id.status, R.id.name,R.id.imageView});
                listView.setAdapter(customList);
                //   listView.setAdapter();
            }

            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    getAlImages.getAllImages();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }
        GetImages getImages = new GetImages();
        getImages.execute();
    }

    private void getURLs() {
        class GetURLs extends AsyncTask<String, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ImageListView.this, "Loading...", "Please Wait...", true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                getAlImages = new GetAlImages(s);
                getImages();
            }

            @Override
            protected String doInBackground(String... strings) {
                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(strings[0]);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }

                    return sb.toString().trim();

                } catch (Exception e) {
                    return null;
                }
            }
        }
        GetURLs gu = new GetURLs();
        gu.execute(GET_IMAGE_URL);
    }


    /**
     * @Override public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
     * String ap = "a";
     * Intent intent = new Intent(this, Edit_Konveksi.class);
     * intent.putExtra(config.idget,i);
     * intent.putExtra(config.konv_ID,getAlImages.idgambar[i]);
     * intent.putExtra(config.ambilgambar,ap);
     * startActivity(intent);
     * finish();
     * }
     **/

   private void gethero() {
        class Gethero extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ImageListView.this, "Fetching...", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                showEmployee(s);

            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(config.GET_data,name_hero);
                return s;
            }
        }
        Gethero ge = new Gethero();
        ge.execute();

    }

    private void showEmployee(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(config.TAG_JSON_ARRAY);
            JSONObject c = result.getJSONObject(0);

           link = c.getString(config.TAG_link);
         //   Toast.makeText(ImageListView.this, link, Toast.LENGTH_SHORT).show();
        //    name_hero  = c.getString(config.hero_name);

            Intent intent;
            intent = new Intent(ImageListView.this,Main_web.class);
            //intent.putExtra(config.id_hero,name_hero);

            intent.putExtra("aw",link);
       //     Toast.makeText(ImageListView.this, link, Toast.LENGTH_SHORT).show();
            startActivity(intent);

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


}