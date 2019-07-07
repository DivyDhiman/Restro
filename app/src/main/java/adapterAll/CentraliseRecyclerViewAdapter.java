package adapterAll;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.util.ArrayList;
import java.util.HashMap;

import callBacks.ChildApiCallback;
import callBacks.OnLoadMoreListener;
import callBacks.RecyclerViewCallBack;
import controllerAll.ConfigApiParseKey;
import controllerAll.Controller;
import functionalityAll.CatchList;
import functionalityAll.CircularImageView;
import restro.bts.com.restro.FullImageView;
import restro.bts.com.restro.FullImageViewRecyclerView;
import restro.bts.com.restro.MapScreen;
import restro.bts.com.restro.R;
import restro.bts.com.restro.RestroDetailsScreen;


@SuppressWarnings("unchecked")
public class CentraliseRecyclerViewAdapter extends RecyclerView.Adapter {
    private Context context;
    private ArrayList<HashMap<String, Object>> data, imageDownload = new ArrayList<>(), selectCuisine = new ArrayList<>();
    private HashMap<String, Object> dataSub, dataGetter, dataSend, imageDownloadSub, dataGetResponse, selectCuisineSub;
    private int layout, previousPosition = 0;
    private String type, responseGet, errorMessage, completeAddress;
    private RecyclerViewCallBack recyclerViewCallBack;
    private Controller controller;
    private ChildApiCallback childApiCallback;
    private Intent intent;
    private boolean loading;
    private DataViewHolder holder = null;
    private final int VIEW_ITEM = 0;
    private final int VIEW_PROG = 1;
    private OnLoadMoreListener onLoadMoreListener;
    private RecyclerView recyclerView;
    private int lastVisibleItem, totalItemCount;
    private int visibleThreshold = 4;
    private int targetPositionOnScroll = 0;
    private int prePosition = -1;
    private double rating;
    private int convertRatingToInt;


    public CentraliseRecyclerViewAdapter(Object... args) {
        this.context = (Context) args[0];
        this.data = (ArrayList<HashMap<String, Object>>) args[1];
        this.layout = (int) args[2];
        this.type = (String) args[3];
        this.recyclerViewCallBack = (RecyclerViewCallBack) args[4];

        if (type.equals(context.getString(R.string.save_cusine_adapter_view))) {
            selectCuisine = (ArrayList<HashMap<String, Object>>) args[6];
            Log.e("selectCuisine","selectCuisine"+selectCuisine);
        }
    }

    public void updateData(ArrayList<HashMap<String, Object>> data) {
        this.data = data;
        notifyDataSetChanged();
    }


//    @Override
//    public int getItemViewType(int position) {
//        return data.get(position) != null ? VIEW_ITEM : VIEW_PROG;
//    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;

        controller = (Controller) context.getApplicationContext();

//        if (viewType == VIEW_ITEM) {
        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        vh = new DataViewHolder(v);
//        } else {
//            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.load_more_data, parent, false);
//            vh = new ProgressViewHolder(v);
//        }


        childApiCallback = new ChildApiCallback() {
            @Override
            public void Data_call_back(Object... args) {
                try {
                    getAPIResponse(args);
                } catch (Exception e) {
                    CatchList.Report(e);
                }
            }
        };

        return vh;
    }

    private void getAPIResponse(Object[] args) {
        String callingUI = (String) args[0];
        responseGet = (String) args[1];
//        if (callingUI.equals(context.getString(R.string.create_group_api))) {
//            if (responseGet.equals(context.getString(R.string.error_Http_not_found))) {
//                controller.pdStop();
//                controller.toastShow(context, context.getString(R.string.error_Http_not_found));
//            } else if (responseGet.equals(context.getString(R.string.error_Http_internal))) {
//                controller.pdStop();
//                controller.toastShow(context, context.getString(R.string.error_Http_internal));
//            } else if (responseGet.equals(context.getString(R.string.error_Http_other))) {
//                controller.pdStop();
//                controller.toastShow(context, context.getString(R.string.error_Http_other));
//            } else if (responseGet.equals(context.getString(R.string.error))) {
//                controller.pdStop();
//                controller.toastShow(context, context.getString(R.string.error));
//            } else if (responseGet.equals(context.getString(R.string.status_error))) {
//                errorMessage = (String) args[2];
//                controller.pdStop();
//                controller.toastShow(context, errorMessage);
//            } else if (responseGet.equals(context.getString(R.string.message_error))) {
//                errorMessage = (String) args[2];
//                controller.pdStop();
//                controller.toastShow(context, errorMessage);
//            } else {
//                controller.pdStop();
//                dataGetter = new HashMap<>();
//                dataGetter = (HashMap<String, Object>) args[3];
//                dataSend.put(ConfigApiParseKey.CREATE_GROUP_GROUP_ID, dataGetter.get(ConfigApiParseKey.CREATE_GROUP_GROUP_ID).toString());
//                intent = new Intent(context, CentralizeChatScreen.class);
//                intent.putExtra("dataGetter", dataSend);
//                intent.putExtra("screenType", context.getString(R.string.chat_proposal));
//                context.startActivity(intent);
//            }
//        }

//        if (callingUI.equals(context.getString(R.string.download_profile_image_api))) {
//            Log.e("ResponseSHow", "ResponseSHow" + responseGet);
//            Log.e("CallingUIIIIIIII", "CallingUIIIIIIIIII" + callingUI);
//            if (responseGet.equals(context.getString(R.string.error))) {
//                controller.pdStop();
//            } else {
//                controller.pdStop();
//                dataGetResponse = new HashMap<>();
//                dataGetResponse = (HashMap<String, Object>) args[3];
//                getDownloadProfileImage(dataGetResponse);
//            }
//        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        try {
            if (holder instanceof DataViewHolder) {
                if (type.equals(context.getString(R.string.restro_list_adapter_view))) {
                    dataSub = data.get(position);
                    callView(0, holder, position);
                } else if (type.equals(context.getString(R.string.restro_review_adapter_view))) {
                    dataSub = data.get(position);
                    callView(1, holder, position);
                } else if (type.equals(context.getString(R.string.save_cusine_adapter_view))) {
                    dataSub = data.get(position);
                    callView(2, holder, position);
                } else if (type.equals(context.getString(R.string.restro_detail_adapter_view))) {
                    dataSub = data.get(position);
                    callView(3, holder, position);
                } else if (type.equals(context.getString(R.string.full_image_adapter_view))) {
                    dataSub = data.get(position);
                    callView(4, holder, position);
                }

            } else {
                ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
            }

        } catch (Exception e) {
            CatchList.Report(e);
        }
    }

    @SuppressLint("SetTextI18n")
    private void callView(int i, final RecyclerView.ViewHolder holderGet, final int position) throws Exception {
        holder = ((DataViewHolder) holderGet);
        switch (i) {
            case 0:
                showAnimation(0, holder, position);


                if(!dataSub.get(ConfigApiParseKey.RESTRO_IMAGE).toString().equals(context.getString(R.string.no_value))) {
                    RequestOptions options = new RequestOptions();
                    options.centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL).skipMemoryCache(false).transform(new RoundedCorners(80));
                    Glide.with(context).load(dataSub.get(ConfigApiParseKey.RESTRO_IMAGE).toString()).apply(options).into(holder.restroImg);
                }else {
                    holder.restroImg.setBackgroundResource(R.drawable.round_default_image);
                }

                holder.restroName.setText(dataSub.get(ConfigApiParseKey.RESTRO_NAME).toString());

                if(!dataSub.get(ConfigApiParseKey.RESTRO_TIPS_TEXT).toString().equals(context.getString(R.string.no_value))) {
                    holder.restroTips.setVisibility(View.VISIBLE);
                    holder.restroTips.setText(dataSub.get(ConfigApiParseKey.RESTRO_TIPS_TEXT).toString());
                }else {
                    holder.restroTips.setVisibility(View.GONE);
                }


                if(!dataSub.get(ConfigApiParseKey.RESTRO_LIKE_COUNT).toString().equals(context.getString(R.string.no_value))) {
                    holder.likeImage.setVisibility(View.VISIBLE);
                    holder.likeCount.setVisibility(View.VISIBLE);
                    holder.likeCount.setText(dataSub.get(ConfigApiParseKey.RESTRO_LIKE_COUNT).toString() + " " + context.getString(R.string.likes));
                }else {
                    holder.likeImage.setVisibility(View.GONE);
                    holder.likeCount.setVisibility(View.GONE);
                }

                if(!dataSub.get(ConfigApiParseKey.RESTRO_PRICE_MESSAGE).toString().equals(context.getString(R.string.no_value))) {
                    holder.priceMessage.setVisibility(View.VISIBLE);
                    holder.priceMessage.setText(context.getString(R.string.type) + " " + dataSub.get(ConfigApiParseKey.RESTRO_PRICE_MESSAGE).toString());
                }else {
                    holder.priceMessage.setVisibility(View.GONE);
                }


                if(!dataSub.get(ConfigApiParseKey.RESTRO_CHECKIN_COUNT).toString().equals(context.getString(R.string.no_value))) {
                    holder.checkInCount.setVisibility(View.VISIBLE);
                    holder.checkInCount.setText(dataSub.get(ConfigApiParseKey.RESTRO_CHECKIN_COUNT).toString() + " " + context.getString(R.string.checkin));
                }else {
                    holder.checkInCount.setVisibility(View.GONE);
                }


                if(!dataSub.get(ConfigApiParseKey.RESTRO_ADDRESS).toString().equals(context.getString(R.string.no_value))){
                    completeAddress = dataSub.get(ConfigApiParseKey.RESTRO_ADDRESS).toString();
                }

                if(!dataSub.get(ConfigApiParseKey.RESTRO_CITY).toString().equals(context.getString(R.string.no_value))){
                    completeAddress = completeAddress + " " + dataSub.get(ConfigApiParseKey.RESTRO_CITY).toString();
                }

                if(!dataSub.get(ConfigApiParseKey.RESTRO_STATE).toString().equals(context.getString(R.string.no_value))){
                    completeAddress = completeAddress + " " + dataSub.get(ConfigApiParseKey.RESTRO_STATE).toString();
                }

                if(!dataSub.get(ConfigApiParseKey.RESTRO_COUNTRY).toString().equals(context.getString(R.string.no_value))){
                    completeAddress = completeAddress + " " + dataSub.get(ConfigApiParseKey.RESTRO_COUNTRY).toString();
                }

                holder.restroLocation.setText(completeAddress);

                double distance = Double.parseDouble(dataSub.get(ConfigApiParseKey.RESTRO_DISTANCE).toString());
                holder.restroDistance.setText(String.valueOf(distance/1000) + " "+ context.getString(R.string.kilo_meter));

                rating = Double.parseDouble(dataSub.get(ConfigApiParseKey.RESTRO_RATING).toString()) / 2;
                convertRatingToInt = (int) rating;
                setRating(holder, convertRatingToInt);

                holder.parentClick.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dataSub = data.get(position);
                        intent = new Intent(context, RestroDetailsScreen.class);
                        intent.putExtra("dataIntent", dataSub);
                        context.startActivity(intent);
                    }
                });

                holder.locationClick.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dataSub = data.get(position);

                        if(!dataSub.get(ConfigApiParseKey.RESTRO_ADDRESS).toString().equals(context.getString(R.string.no_value))){
                            completeAddress = dataSub.get(ConfigApiParseKey.RESTRO_ADDRESS).toString();
                        }

                        if(!dataSub.get(ConfigApiParseKey.RESTRO_CITY).toString().equals(context.getString(R.string.no_value))){
                            completeAddress = completeAddress + " " + dataSub.get(ConfigApiParseKey.RESTRO_CITY).toString();
                        }

                        if(!dataSub.get(ConfigApiParseKey.RESTRO_STATE).toString().equals(context.getString(R.string.no_value))){
                            completeAddress = completeAddress + " " + dataSub.get(ConfigApiParseKey.RESTRO_STATE).toString();
                        }

                        if(!dataSub.get(ConfigApiParseKey.RESTRO_COUNTRY).toString().equals(context.getString(R.string.no_value))){
                            completeAddress = completeAddress + " " + dataSub.get(ConfigApiParseKey.RESTRO_COUNTRY).toString();
                        }

                        intent = new Intent(context,MapScreen.class);
                        intent.putExtra("name",dataSub.get(ConfigApiParseKey.RESTRO_NAME).toString());
                        intent.putExtra("completeAddress",completeAddress);
                        intent.putExtra("distance",dataSub.get(ConfigApiParseKey.RESTRO_DISTANCE).toString());
                        intent.putExtra("destinationLatitude",dataSub.get(ConfigApiParseKey.RESTRO_LATITUDE).toString());
                        intent.putExtra("destinationLongitude",dataSub.get(ConfigApiParseKey.RESTRO_LONGITUDE).toString());
                        context.startActivity(intent);
                        controller.animAllForward(context);
                    }
                });

                holder.restroImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dataSub = data.get(position);
                        intent = new Intent(context, FullImageView.class);
                        intent.putExtra("imageUrl", dataSub.get(ConfigApiParseKey.RESTRO_IMAGE).toString());
                        context.startActivity(intent);
                        controller.animAllForward(context);
                    }
                });

                break;

            case 1:
                showAnimation(0, holder, position);

                RequestOptions optionsUserImage = new RequestOptions();
                optionsUserImage.centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL).skipMemoryCache(false).transform(new RoundedCorners(80));
                Glide.with(context).load(dataSub.get(ConfigApiParseKey.RESTRO_REVIEW_USER_IMAGE).toString()).apply(optionsUserImage).into(holder.userImage);

                holder.nameTxt.setText(dataSub.get(ConfigApiParseKey.RESTRO_REVIEW_NAME).toString());
                holder.commentTxt.setText(dataSub.get(ConfigApiParseKey.RESTRO_REVIEW_TEXT).toString());
                holder.timeTxt.setText(controller.getUtcConvertTime(Long.parseLong(dataSub.get(ConfigApiParseKey.RESTRO_REVIEW_CREATED_AT).toString())));

                holder.userImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dataSub = data.get(position);
                        intent = new Intent(context, FullImageView.class);
                        intent.putExtra("imageUrl", dataSub.get(ConfigApiParseKey.RESTRO_REVIEW_USER_IMAGE).toString());
                        context.startActivity(intent);
                        controller.animAllForward(context);
                    }
                });

                break;

            case 2:
                holder.cusineTxt.setText(dataSub.get(ConfigApiParseKey.RESTRO_CUISINE_CATEGORY).toString());

                if (selectCuisine.size() > 0) {
                    boolean isCuisineAdded = false;
                    for (int j = 0; j < selectCuisine.size(); j++) {
                        if (selectCuisine.get(j).get("cuisine_name").toString().equals(dataSub.get(ConfigApiParseKey.RESTRO_CUISINE_CATEGORY).toString())) {
                            isCuisineAdded = true;
                            holder.cusineRadio.setChecked(true);
                            break;
                        }
                    }

                    if (!isCuisineAdded) {
                        holder.cusineRadio.setChecked(false);
                    }
                } else {
                    holder.cusineRadio.setChecked(false);
                }

                holder.cusineRadio.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dataSub = data.get(position);
                        if (selectCuisine.size() > 0) {
                            boolean isCuisineAdded = false;
                            for (int i = 0; i < selectCuisine.size(); i++) {
                                if (selectCuisine.get(i).get("cuisine_name").toString().equals(dataSub.get(ConfigApiParseKey.RESTRO_CUISINE_CATEGORY).toString())) {
                                    selectCuisine.remove(i);
                                    isCuisineAdded = true;
                                    notifyDataSetChanged();
                                    break;
                                }
                            }

                            if (!isCuisineAdded) {
                                selectCuisineSub = new HashMap<>();
                                selectCuisineSub.put("cuisine_name", dataSub.get(ConfigApiParseKey.RESTRO_CUISINE_CATEGORY).toString());
                                selectCuisineSub.put("cuisine_id", dataSub.get(ConfigApiParseKey.RESTRO_CUISINE_CATEGORY_ID).toString());
                                selectCuisine.add(selectCuisineSub);
                                notifyDataSetChanged();
                            }
                        } else {
                            selectCuisineSub = new HashMap<>();
                            selectCuisineSub.put("cuisine_name", dataSub.get(ConfigApiParseKey.RESTRO_CUISINE_CATEGORY).toString());
                            selectCuisineSub.put("cuisine_id", dataSub.get(ConfigApiParseKey.RESTRO_CUISINE_CATEGORY_ID).toString());
                            selectCuisine.add(selectCuisineSub);
                            notifyDataSetChanged();
                        }

                        recyclerViewCallBack.dataGet(selectCuisine);
                    }
                });

                holder.parentClick.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dataSub = data.get(position);
                        if (selectCuisine.size() > 0) {
                            boolean isCuisineAdded = false;
                            for (int i = 0; i < selectCuisine.size(); i++) {
                                if (selectCuisine.get(i).get("cuisine_name").toString().equals(dataSub.get(ConfigApiParseKey.RESTRO_CUISINE_CATEGORY).toString())) {
                                    selectCuisine.remove(i);
                                    isCuisineAdded = true;
                                    notifyDataSetChanged();
                                    break;
                                }
                            }

                            if (!isCuisineAdded) {
                                selectCuisineSub = new HashMap<>();
                                selectCuisineSub.put("cuisine_name", dataSub.get(ConfigApiParseKey.RESTRO_CUISINE_CATEGORY).toString());
                                selectCuisineSub.put("cuisine_id", dataSub.get(ConfigApiParseKey.RESTRO_CUISINE_CATEGORY_ID).toString());
                                selectCuisine.add(selectCuisineSub);
                                notifyDataSetChanged();
                            }
                        } else {
                            selectCuisineSub = new HashMap<>();
                            selectCuisineSub.put("cuisine_name", dataSub.get(ConfigApiParseKey.RESTRO_CUISINE_CATEGORY).toString());
                            selectCuisineSub.put("cuisine_id", dataSub.get(ConfigApiParseKey.RESTRO_CUISINE_CATEGORY_ID).toString());
                            selectCuisine.add(selectCuisineSub);
                            notifyDataSetChanged();
                        }

                        recyclerViewCallBack.dataGet(selectCuisine);
                    }
                });
                break;

            case 3:
                showAnimation(0, holder, position);

                if(!dataSub.get(ConfigApiParseKey.RESTRO_IMAGE).toString().equals(context.getString(R.string.no_value))) {
                    RequestOptions optionsImage = new RequestOptions();
                    optionsImage.centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL).skipMemoryCache(false).transform(new RoundedCorners(10));
                    Glide.with(context).load(dataSub.get(ConfigApiParseKey.RESTRO_IMAGE).toString()).apply(optionsImage).into(holder.restroImg);
                }else {
                    holder.restroImg.setBackgroundResource(R.drawable.round_default_image);
                }

                holder.restroImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dataSub = data.get(position);

                        intent = new Intent(context, FullImageViewRecyclerView.class);
                        intent.putExtra("dataIntent", data);
                        intent.putExtra("postion", position);
                        context.startActivity(intent);
                    }
                });
                break;

            case 4:
                RequestOptions optionsFullImage = new RequestOptions();
                optionsFullImage.centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL).skipMemoryCache(false);
                Glide.with(context).load(dataSub.get(ConfigApiParseKey.RESTRO_IMAGE).toString()).apply(optionsFullImage).into(holder.imageShow);

                break;
        }
    }

    private void setRating(DataViewHolder holder, int ratingGet) {
        switch (ratingGet) {
            case 0:
                holder.ratingRestro1.setImageResource(R.drawable.smallgraystar);
                holder.ratingRestro2.setImageResource(R.drawable.smallgraystar);
                holder.ratingRestro3.setImageResource(R.drawable.smallgraystar);
                holder.ratingRestro4.setImageResource(R.drawable.smallgraystar);
                holder.ratingRestro5.setImageResource(R.drawable.smallgraystar);
                break;

            case 1:
                holder.ratingRestro1.setImageResource(R.drawable.smallredstar);
                holder.ratingRestro2.setImageResource(R.drawable.smallgraystar);
                holder.ratingRestro3.setImageResource(R.drawable.smallgraystar);
                holder.ratingRestro4.setImageResource(R.drawable.smallgraystar);
                holder.ratingRestro5.setImageResource(R.drawable.smallgraystar);
                break;

            case 2:
                holder.ratingRestro1.setImageResource(R.drawable.smallredstar);
                holder.ratingRestro2.setImageResource(R.drawable.smallredstar);
                holder.ratingRestro3.setImageResource(R.drawable.smallgraystar);
                holder.ratingRestro4.setImageResource(R.drawable.smallgraystar);
                holder.ratingRestro5.setImageResource(R.drawable.smallgraystar);
                break;

            case 3:
                holder.ratingRestro1.setImageResource(R.drawable.smallredstar);
                holder.ratingRestro2.setImageResource(R.drawable.smallredstar);
                holder.ratingRestro3.setImageResource(R.drawable.smallredstar);
                holder.ratingRestro4.setImageResource(R.drawable.smallgraystar);
                holder.ratingRestro5.setImageResource(R.drawable.smallgraystar);
                break;

            case 4:
                holder.ratingRestro1.setImageResource(R.drawable.smallredstar);
                holder.ratingRestro2.setImageResource(R.drawable.smallredstar);
                holder.ratingRestro3.setImageResource(R.drawable.smallredstar);
                holder.ratingRestro4.setImageResource(R.drawable.smallredstar);
                holder.ratingRestro5.setImageResource(R.drawable.smallgraystar);
                break;

            case 5:
                holder.ratingRestro1.setImageResource(R.drawable.smallredstar);
                holder.ratingRestro2.setImageResource(R.drawable.smallredstar);
                holder.ratingRestro3.setImageResource(R.drawable.smallredstar);
                holder.ratingRestro4.setImageResource(R.drawable.smallredstar);
                holder.ratingRestro5.setImageResource(R.drawable.smallredstar);
                break;

        }
    }


    private void getDownloadProfileImage(HashMap<String, Object> dataGetResponse) {
        for (int i = 0; i < imageDownload.size(); i++) {
            if (imageDownload.get(i).get("user_image").toString().equals(dataGetResponse.get("url").toString())) {
                imageDownload.remove(i);
                notifyDataSetChanged();
                break;
            }
        }
    }

    @Override
    public int getItemCount() {
        try {
            if (type.equals(context.getString(R.string.restro_list_adapter_view)) || type.equals(context.getString(R.string.restro_detail_adapter_view)) || type.equals(context.getString(R.string.full_image_adapter_view))
                    || type.equals(context.getString(R.string.restro_review_adapter_view)) || type.equals(context.getString(R.string.save_cusine_adapter_view))) {
                return data.size();
            } else {
                return 10;
            }
        }catch (Exception e){
            return  0;
        }
    }


    public void showAnimation(int i, DataViewHolder holder, int positionGet) {
        switch (i) {
            case 0:
                if (positionGet > previousPosition) {
                    Animation animation = AnimationUtils.loadAnimation(context, R.anim.bounce);
                    holder.itemView.startAnimation(animation);
                    previousPosition = positionGet;
                }
                break;
            case 1:
                Animation animation = AnimationUtils.loadAnimation(context, R.anim.recycle_right_in);
                holder.itemView.startAnimation(animation);
                break;

        }
    }

    @Override
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.itemView.clearAnimation();
    }


    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public void setLoaded() {
        loading = false;
    }

    public void setLoadedTrue() {
        loading = true;
    }

    private class DataViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout parentClick;
        private ImageView restroImg, ratingRestro1, ratingRestro2, ratingRestro3, ratingRestro4, ratingRestro5, imageShow, userImage,locationClick,
                likeImage;
        private TextView restroName, restroLocation, restroDistance, nameTxt, commentTxt, cusineTxt,timeTxt,likeCount,restroTips,priceMessage,
                checkInCount;
        private RadioButton cusineRadio;

        private DataViewHolder(View view) {
            super(view);

            if (type.equals(context.getString(R.string.restro_list_adapter_view))) {
                parentClick = view.findViewById(R.id.parent_click);
                restroImg = view.findViewById(R.id.restro_img);
                likeImage = view.findViewById(R.id.like_image);
                likeCount = view.findViewById(R.id.like_count);
                ratingRestro1 = view.findViewById(R.id.rating_restro1);
                ratingRestro2 = view.findViewById(R.id.rating_restro2);
                ratingRestro3 = view.findViewById(R.id.rating_restro3);
                ratingRestro4 = view.findViewById(R.id.rating_restro4);
                ratingRestro5 = view.findViewById(R.id.rating_restro5);
                restroName = view.findViewById(R.id.restro_name);
                restroLocation = view.findViewById(R.id.restro_location);
                restroDistance = view.findViewById(R.id.restro_distance);
                locationClick = view.findViewById(R.id.location_click);
                restroTips = view.findViewById(R.id.restro_tips);
                priceMessage = view.findViewById(R.id.price_message);
                checkInCount = view.findViewById(R.id.check_in_count);
            } else if (type.equals(context.getString(R.string.restro_review_adapter_view))) {
                parentClick = view.findViewById(R.id.parent_click);
                userImage = view.findViewById(R.id.user_image);
                nameTxt = view.findViewById(R.id.name_txt);
                commentTxt = view.findViewById(R.id.comment_txt);
                timeTxt = view.findViewById(R.id.time_txt);
            } else if (type.equals(context.getString(R.string.save_cusine_adapter_view))) {
                parentClick = view.findViewById(R.id.parent_click);
                cusineRadio = view.findViewById(R.id.cusine_radio);
                cusineTxt = view.findViewById(R.id.cusine_txt);
            } else if (type.equals(context.getString(R.string.restro_detail_adapter_view))) {
                restroImg = view.findViewById(R.id.restro_img);
            } else if (type.equals(context.getString(R.string.full_image_adapter_view))) {
                imageShow = view.findViewById(R.id.image_show);
            }
        }
    }

    private class ProgressViewHolder extends RecyclerView.ViewHolder {
        private ProgressBar progressBar;

        private ProgressViewHolder(View view) {
            super(view);
            progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        }
    }

}
