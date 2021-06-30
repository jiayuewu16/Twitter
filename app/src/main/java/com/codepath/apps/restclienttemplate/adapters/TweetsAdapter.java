package com.codepath.apps.restclienttemplate.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.databinding.ItemTweetBinding;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.ViewHolder>{

    Context context;
    List<Tweet> tweets;

    public TweetsAdapter(Context context, List<Tweet> tweets) {
        this.context = context;
        this.tweets = tweets;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        ItemTweetBinding binding = ItemTweetBinding.inflate(LayoutInflater.from(context));
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        Tweet tweet = tweets.get(position);
        holder.bind(tweet);
    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemTweetBinding binding;
        public ViewHolder(@NonNull ItemTweetBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Tweet tweet) {
            binding.tvBody.setText(tweet.body);
            binding.tvDisplayName.setText(tweet.user.displayName);
            binding.tvRelativeTime.setText(tweet.getRelativeTime());
            Glide.with(context).load(tweet.user.profileImageUrl).into(binding.ivProfileImage);
            if (tweet.photoUrl != null) {
                Glide.with(context).load(tweet.photoUrl).into(binding.ivMedia);
                binding.ivMedia.setVisibility(View.VISIBLE);
            }
            else {
                binding.ivMedia.setVisibility(View.GONE);
            }
        }
    }
}
