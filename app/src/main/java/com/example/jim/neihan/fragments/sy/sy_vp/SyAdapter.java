package com.example.jim.neihan.fragments.sy.sy_vp;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.jim.neihan.R;
import com.example.jim.neihan.fragments.sy.sybean.SyChildBean;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.AbstractDraweeController;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;

import org.w3c.dom.Text;

import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/**
 * Created by jim on 2018/1/10.
 */

public class SyAdapter extends RecyclerView.Adapter<SyAdapter.ViewHolder> {
    private List<SyChildBean.DataBeanX.DataBean> list;
    private Context context;

    public SyAdapter(List<SyChildBean.DataBeanX.DataBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public SyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.rec_item_view, parent, false);

        ViewHolder vh = new ViewHolder(inflate);
        return vh;
    }

    @Override
    public void onBindViewHolder(final SyAdapter.ViewHolder holder, int position) {
        final int[] num = {1};
        holder.name.setText(list.get(position).getGroup().getUser().getName());
        Uri uri = Uri.parse(list.get(position).getGroup().getUser().getAvatar_url());
        holder.avatar_url.setImageURI(uri);
//        holder.tv_title.setText(list.get(position).getGroup().getContent());
//        holder.redTitle.setText("#" + list.get(position).getGroup().getCategory_name() + "#");
        String theme = "#" + list.get(position).getGroup().getCategory_name() + "#";
        int lengthTheme = theme.length();
        String text = list.get(position).getGroup().getContent();
        String texts = theme + "  " + text;
        SpannableString spannableString = new SpannableString(texts);
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.RED);
        spannableString.setSpan(foregroundColorSpan, 0, lengthTheme, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        holder.tv_title.setText(spannableString);
        /**
         * -------------------------------
         */
        if (list.get(position).getGroup().get_$360p_video() != null) {
            holder.item_freImg.setVisibility(View.GONE);
            holder.videoPlayer.setVisibility(View.VISIBLE);
            //VideoPlayer
            String mp4_url = list.get(position).getGroup().getMp4_url();
            boolean setUp = holder.videoPlayer.setUp(mp4_url, JCVideoPlayer.SCREEN_LAYOUT_LIST, "");
            if (setUp) {
                String url = list.get(position).getGroup().getLarge_cover().getUrl_list().get(0).getUrl();
                holder.videoPlayer.thumbImageView.setScaleType(ImageView.ScaleType.FIT_XY);
                Glide.with(context).load(url).into(holder.videoPlayer.thumbImageView);
            }
        } else if (list.get(position).getGroup().getLarge_image() != null) {
            holder.item_freImg.setVisibility(View.VISIBLE);
            holder.videoPlayer.setVisibility(View.GONE);
            Uri parse = Uri.parse(list.get(position).getGroup().getLarge_image().getUrl_list().get(0).getUrl());
            // holder.item_freImg.setImageURI(parse);
            Log.d("WXW", "PATH" + parse);

            DraweeController mDraweeController = Fresco.newDraweeControllerBuilder()
                    .setAutoPlayAnimations(true)
                    //加载drawable里的一张gif图
                    .setUri(parse)//设置uri
                    .build();
            //设置Controller
            holder.item_freImg.setController(mDraweeController);


        } else if (list.get(position).getGroup().getLarge_image() == null && list.get(position).getGroup().get_$360p_video() == null) {
            holder.item_freImg.setVisibility(View.GONE);
            holder.videoPlayer.setVisibility(View.GONE);
        }
        holder.img_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean flag = true;
                if (true) {
                    View layout = LayoutInflater.from(context).inflate(R.layout.layout, null);
                    Toast toast = new Toast(context);
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(layout);
                    toast.show();
                    flag = false;
                    holder.img_yes.setImageResource(R.drawable.yes2);
                }
            }


        });

        holder.img_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean flag = true;
                if (true) {
                    Toast toast = Toast.makeText(context, "踩了", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    flag = false;
                    holder.img_no.setImageResource(R.drawable.no2);
                }
            }

        });

    }


    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    //自定义的ViewHolder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final SimpleDraweeView avatar_url;
        private final TextView tv_title;
        private final JCVideoPlayerStandard videoPlayer;
        private final SimpleDraweeView item_freImg;
        private final ImageView img_yes;
        private final ImageView img_no;
        //        private final TextView redTitle;
        private final TextView tv_mes_num;

        public ViewHolder(View view) {
            super(view);

            //用户名
            name = view.findViewById(R.id.name);
            //头像
            avatar_url = view.findViewById(R.id.avatar_url);
            //标题
            tv_title = view.findViewById(R.id.tv_title);
//            redTitle = view.findViewById(R.id.tv_redTitle);
            //播放器
            videoPlayer = view.findViewById(R.id.videoPlayer);
            //图片
            item_freImg = view.findViewById(R.id.item_freImg);
            //点赞
            img_yes = view.findViewById(R.id.img_yes);
            img_no = view.findViewById(R.id.img_no);
            //点赞的数量
            tv_mes_num = view.findViewById(R.id.tv_mes_num);
        }
    }


}
