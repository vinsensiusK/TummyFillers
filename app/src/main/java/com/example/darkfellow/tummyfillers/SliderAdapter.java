package com.example.darkfellow.tummyfillers;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by darkfellow on 4/5/18.
 */

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context){

        this.context = context;
    }

    public int[] slide_images = {
            R.drawable.group_10,
            R.drawable.group_10,
            R.drawable.group_10
    };

    public String[] slide_headings = {
            "EAT",
            "EAT",
            "EAT"
    };

    public String[] slide_descs = {
            "Makanan itu itu aja? Makanan pada mahal? Atau banyak yang gasehat? Mau kenyang? Tummy Fillers rajanya!",
            "Makanan itu itu aja? Makanan pada mahal? Atau banyak yang gasehat? Mau kenyang? Tummy Fillers rajanya!",
            "Makanan itu itu aja? Makanan pada mahal? Atau banyak yang gasehat? Mau kenyang? Tummy Fillers rajanya!"
    };

    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (RelativeLayout) object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout, container , false);

        ImageView slideImageView = (ImageView) view.findViewById(R.id.slide_image);
        TextView slideHeading = (TextView) view.findViewById(R.id.slide_heading);
        TextView slideDescription = (TextView) view.findViewById(R.id.slide_desc);

        slideImageView.setImageResource(slide_images[position]);
        slideHeading.setText(slide_headings[position]);
        slideDescription.setText(slide_descs[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView((RelativeLayout)object);
    }
}
