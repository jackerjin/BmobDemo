package com.example.tarena.bmobdemo.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tarena.bmobdemo.R;
import com.example.tarena.bmobdemo.bean.MyPost;
import com.example.tarena.bmobdemo.bean.MyUser;
import com.example.tarena.bmobdemo.ui.PostActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.listener.DeleteListener;

/**
 * Created by tarena on 2017/6/30.
 */

public class PostAdapter extends BaseAdapter {
    Context context;
    List<MyPost> datas;
    LayoutInflater inflater;
    MyUser currentUser;//当前登录用户

    public PostAdapter(Context context, List<MyPost> datas, MyUser currentUser) {
        this.context = context;
        this.datas = datas;
        this.currentUser = currentUser;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public MyPost getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view==null){
            view=inflater.inflate(R.layout.show_item_layout,parent,false);
            holder=new ViewHolder(view);
            view.setTag(holder);
        }else {
            holder= (ViewHolder) view.getTag();
        }
        final MyPost post= getItem(position);
        MyUser user=post.getUser();//帖子作者
        String avatar=user.getAvatar();
        if (TextUtils.isEmpty(avatar)){
            //用户没设置头像
            holder.ivAvatar.setImageResource(R.mipmap.ic_launcher);
        }else {
            Picasso.with(context).load(user.getAvatar()).into(holder.ivAvatar);
        }
        holder.tvName.setText(user.getUsername());
        holder.tvTitle.setText(post.getTitle());
        holder.tvTime.setText(post.getCreatedAt());

        holder.tvContent.setText(post.getContent());
        //帖子作者和当前登录用户是不是一个人
        if (currentUser.getUsername().equals(user.getUsername())){
            holder.tvDelete.setVisibility(View.VISIBLE);
            holder.tvUpdate.setVisibility(View.VISIBLE);
        }else {
            holder.tvDelete.setVisibility(View.INVISIBLE);
            holder.tvUpdate.setVisibility(View.INVISIBLE);
        }
        holder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 点击删除帖子
                //弹窗请用户确认后再删除
                final AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setTitle("确认删除");
                builder.setIcon(android.R.drawable.ic_menu_info_details);
                builder.setMessage("您确认要删除该帖子吗？");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                //从服务器上删除数据
                        post.delete(context, new DeleteListener() {
                            @Override
                            public void onSuccess() {
                                Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show();
                                //如果有本地缓存，且本地缓存中的数据是被优先加载
                                //也应该从缓存中清除掉对应的数据
                                //ListView的数据源中删除post
                                remove(post);
                            }

                            @Override
                            public void onFailure(int i, String s) {
                                Toast.makeText(context, "服务器繁忙，稍后重试", Toast.LENGTH_SHORT).show();
                            }
                        });
                        /*                        MyPost newPost = new MyPost();
                        newPost.setObjectId(post.getObjectId());
                        newPost.delete(context, new DeleteListener() {
                            @Override
                            public void onSuccess() {

                                remove(post);
                            }

                            @Override
                            public void onFailure(int i, String s) {

                            }
                        });*/

//                        MyPost newPost = new MyPost();
//                        newPost.delete(context, post.getObjectId(), new DeleteListener() {
//                            @Override
//                            public void onSuccess() {
//                                remove(post);
//                            }
//
//                            @Override
//                            public void onFailure(int i, String s) {
//
//                            }
//                        });
//
//                    }
//                });

                    }
                });
                builder.setNegativeButton("取消",null);
                builder.create().show();

            }
        });
        holder.tvUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 点击更新该帖子
                Intent intent=new Intent(context, PostActivity.class);
                intent.putExtra("from","update");
                intent.putExtra("post",post);
                context.startActivity(intent);
            }
        });

        return view;
    }
    public void addAll(List<MyPost> list, boolean isClear) {
        if (isClear) {
            datas.clear();

        }

        datas.addAll(list);
        notifyDataSetChanged();
    }

    public void remove(MyPost post) {
        datas.remove(post);
        notifyDataSetChanged();
    }

    public class ViewHolder {
        @BindView(R.id.iv_show_header)
        ImageView ivAvatar;
        @BindView(R.id.tv_show_name)
        TextView tvName;
        @BindView(R.id.tv_show_title)
        TextView tvTitle;
        @BindView(R.id.tv_show_date)
        TextView tvTime;
        @BindView(R.id.tv_show_content)
        TextView tvContent;
        @BindView(R.id.btn_show_delete)
        TextView tvDelete;
        @BindView(R.id.btn_show_revise)
        TextView tvUpdate;

        public ViewHolder(View v) {
            ButterKnife.bind(this, v);
        }
    }
}
