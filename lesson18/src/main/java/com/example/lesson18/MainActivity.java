package com.example.lesson18;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        RetrofitHelper retrofitHelper = new RetrofitHelper();
//        List<String> uris = new ArrayList<>();
//        try {
//            Response<Image> imageResponse = retrofitHelper.getService().getImages().execute();
//            Image image = imageResponse.body();
//            List<Image.Images> images = image.getImages();
//            for (Image.Images img : images) {
//                String uri = img.getDisplaySizes().get(0).getUri();
//                uris.add(uri);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        List<String> uris = init();

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        MyListAdapter adapter = new MyListAdapter(uris);
        recyclerView.setAdapter(adapter);
    }

    private List<String> init() {
        List<String> uris = new ArrayList<>();
        uris.add("https://media.gettyimages.com/photos/s-kevin-kisner-on-the-18th-green-during-day-one-of-the-open-2018-at-picture-id1001891272?b=1&k=6&m=1001891272&s=170x170&h=K4XNjkvitpwqwHsqNogWo7ZyHxQtcsQQsuK_bKsaduQ=");
        uris.add("https://media.gettyimages.com/photos/s-justin-thomas-tees-off-the-17th-during-day-one-of-the-open-2018-at-picture-id1001891268?b=1&k=6&m=1001891268&s=170x170&h=41HfT7xfaPyXwr-SPgB8r1926716ScMxN_fvdZ7tnUc=");
        uris.add("https://media.gettyimages.com/photos/gemma-arterton-attends-a-special-screening-of-the-escape-at-the-on-picture-id1001891260?b=1&k=6&m=1001891260&s=170x170&h=1U-vfSfgS6DMqY0PaHuDmE-k5zoygjE5YDHFnJMZpMA=");
        uris.add("https://media.gettyimages.com/photos/gemma-arterton-attends-a-special-screening-of-the-escape-at-the-on-picture-id1001891252?b=1&k=6&m=1001891252&s=170x170&h=n7SD0lOHNYWeIshsJUrV9cRCGG6TxGdwG3hwPQAUV-Y=");
        uris.add("https://media.gettyimages.com/photos/gemma-arterton-attends-a-special-screening-of-the-escape-at-the-on-picture-id1001891256?b=1&k=6&m=1001891256&s=170x170&h=v3Tfl4LHZgxyxXEQO9tZ0a7at0s7VrOZ4J29ot1UxNU=");
        uris.add("https://media.gettyimages.com/photos/gemma-arterton-attends-a-special-screening-of-the-escape-at-the-on-picture-id1001891248?b=1&k=6&m=1001891248&s=170x170&h=ZOI41ol3a6TJE6rj3beEvhoLHK7JxHshsxr-Y01FFBY=");
        uris.add("https://media.gettyimages.com/photos/gemma-arterton-attends-a-special-screening-of-the-escape-at-the-on-picture-id1001891246?b=1&k=6&m=1001891246&s=170x170&h=kpFRHQzuH6vl9AvuAHEanTBGd2tnL7IeInWZt7bdenw=");
        uris.add("https://media.gettyimages.com/photos/gemma-arterton-attends-a-special-screening-of-the-escape-at-the-on-picture-id1001891244?b=1&k=6&m=1001891244&s=170x170&h=7C58-EBWMSHvuiRXbz6845bErK4uPcTS00YYX8Gr2po=");
        uris.add("https://media.gettyimages.com/photos/daley-blind-of-ajax-during-the-club-friendly-match-between-wanderers-picture-id1001891232?b=1&k=6&m=1001891232&s=170x170&h=_fgE3crupsmhUfp5iMv1nWdCDLDHaUl454vBFO2URL0=");
        uris.add("https://media.gettyimages.com/photos/ezio-greggio-attends-2018-ischia-global-film-music-fest-on-july-19-picture-id1001891236?b=1&k=6&m=1001891236&s=170x170&h=l-s-2zlQfTQZmP55sXDyJSxn20uvUV6ZL38OhblRVX4=");
        uris.add("https://media.gettyimages.com/photos/ezio-greggio-attends-2018-ischia-global-film-music-fest-on-july-19-picture-id1001891228?b=1&k=6&m=1001891228&s=170x170&h=3hDH7zvXvafyLm1CxvGlmGxXz60AY5B52UvJOM65lkU=");
        uris.add("https://media.gettyimages.com/photos/ezio-greggio-attends-2018-ischia-global-film-music-fest-on-july-19-picture-id1001891226?b=1&k=6&m=1001891226&s=170x170&h=hUiyVRa5fHXqvC4bjXx2tinEw3V3Bj2TYtTrp7e68Zw=");
        uris.add("https://media.gettyimages.com/photos/ezio-greggio-attends-2018-ischia-global-film-music-fest-on-july-19-picture-id1001891222?b=1&k=6&m=1001891222&s=170x170&h=isf3IttIfmSvMFVIsX1EuavCKa8xiG7tLMIPikj6tzM=");
        uris.add("https://media.gettyimages.com/photos/daley-blind-of-ajax-during-the-club-friendly-match-between-wanderers-picture-id1001891216?b=1&k=6&m=1001891216&s=170x170&h=KW-BRh_4eN6vSa9KjolV_Z33sOrs5ujnUwAPazyp-zg=");
        uris.add("https://media.gettyimages.com/photos/noa-lang-of-ajax-during-the-club-friendly-match-between-wolverhampton-picture-id1001891204?b=1&k=6&m=1001891204&s=170x170&h=Nedt1kRYeTeWHL4QJCW4bqTXKU9NDwsQ_zxfuTb3INs=");
        uris.add("https://media.gettyimages.com/photos/andre-onana-of-ajax-during-the-club-friendly-match-between-wanderers-picture-id1001891196?b=1&k=6&m=1001891196&s=170x170&h=N9hzLRTHycPxz1kpHfJ_DHneh6F2uRqsLr-GF1t2Lko=");
        uris.add("https://media.gettyimages.com/photos/poet-kenneth-goldsmith-is-photographed-on-february-4-2014-in-new-york-picture-id1001891182?b=1&k=6&m=1001891182&s=170x170&h=ajVjyIKy3lcu2NatpfFtreDORw_sm_U1enH6-cSMi3M=");
        uris.add("https://media.gettyimages.com/photos/poet-kenneth-goldsmith-is-photographed-on-february-4-2014-in-new-york-picture-id1001891180?b=1&k=6&m=1001891180&s=170x170&h=nSYh7JtFpn9ApkcbI-tJIuJ20adNbKBsWlQQUVJwjek=");
        uris.add("https://media.gettyimages.com/photos/matthijs-de-ligt-of-ajax-david-neres-of-ajax-donny-van-de-beek-of-picture-id1001891176?b=1&k=6&m=1001891176&s=170x170&h=i5gSkMeDj-YF7X7ZFSzjBibBPcyzQAImHNETnYSC5FM=");
        return uris;
    }
}
