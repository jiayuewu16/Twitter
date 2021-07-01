package com.codepath.apps.restclienttemplate.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Parcel;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.databinding.ActivityDetailsBinding;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;

public class TweetDetailsActivity extends AppCompatActivity {

    ActivityDetailsBinding binding;
    Tweet tweet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        tweet = Parcels.unwrap(getIntent().getParcelableExtra(Tweet.class.getSimpleName()));
        binding.tvName.setText(tweet.user.name);
        binding.tvScreenName.setText(tweet.user.screenName);
        binding.tvBody.setText(tweet.body);
        binding.tvCreatedAt.setText(tweet.getDateCreatedAt());
        Glide.with(this).load(tweet.user.profileImageUrl).into(binding.ivProfileImage);
        if (tweet.photoUrl != null) {
            Glide.with(this).load(tweet.photoUrl).into(binding.ivMedia);
            binding.ivMedia.setVisibility(View.VISIBLE);
        }
        else {
            binding.ivMedia.setVisibility(View.GONE);
        }
    }
}