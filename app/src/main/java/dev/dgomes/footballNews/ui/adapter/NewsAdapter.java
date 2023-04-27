package dev.dgomes.footballNews.ui.adapter;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import dev.dgomes.footballNews.databinding.NewsItemBinding;
import dev.dgomes.footballNews.domain.NewsData;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private List<NewsData> newsList;

    public NewsAdapter(List<NewsData> newsList) {
        this.newsList = newsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        NewsItemBinding binding = NewsItemBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NewsData news = this.newsList.get(position);
        holder.binding.newsTitle.setText(news.getNewsTitle());
        holder.binding.newsText.setText(news.getNewsText());
        Picasso.get().load(news.getImage()).into(holder.binding.newsImage);
        holder.binding.openLinkButton.setOnClickListener(view -> {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(news.getUrl()));
                holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return this.newsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final NewsItemBinding binding;

        public ViewHolder(NewsItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }

}
