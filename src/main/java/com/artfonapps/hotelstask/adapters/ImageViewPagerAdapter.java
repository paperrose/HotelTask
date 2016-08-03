package com.artfonapps.hotelstask.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.artfonapps.hotelstask.R;
import com.artfonapps.hotelstask.db.models.Image;
import com.artfonapps.hotelstask.network.Communicator;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by paperrose on 15.07.2016.
 */
public class ImageViewPagerAdapter  extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;
    List<Image> images;

    public ImageViewPagerAdapter(Context context, List<Image> images) {
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.images = images;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (FrameLayout)object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = layoutInflater.inflate(R.layout.hotel_image_item, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.img);
        Picasso.with(context)
                .load(Communicator.getImageAddress(images.get(position).url, images.get(position).hotel.id))
                .error(R.drawable.no_image)
                .fit()
                .into(imageView);
        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((FrameLayout) object);
    }
}
