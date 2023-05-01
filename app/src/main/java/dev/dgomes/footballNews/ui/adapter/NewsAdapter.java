package dev.dgomes.footballNews.ui.adapter;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import dev.dgomes.footballNews.R;
import dev.dgomes.footballNews.databinding.NewsItemBinding;
import dev.dgomes.footballNews.domain.NewsData;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private final List<NewsData> newsList;
    private final FavoritesListener favoriteListener;

    public NewsAdapter(List<NewsData> newsList, FavoritesListener favoriteListener) {
        this.newsList = newsList;
        this.favoriteListener = favoriteListener;
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
        holder.binding.newsTitle.setText(news.title);
        holder.binding.newsText.setText(news.text);
        Picasso.get().load(news.image).into(holder.binding.newsImage);

        holder.binding.openLinkButton.setOnClickListener(view -> {

            view.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(news.url)));
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.setData(Uri.parse(news.getUrl()));
//                holder.itemView.getContext().startActivity(intent);
        });

        holder.binding.shareIcon.setOnClickListener(view -> {

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TITLE, news.title);
            intent.putExtra(Intent.EXTRA_TEXT, news.text);
            intent.setType("text/plain");
            view.getContext().startActivity(Intent.createChooser(intent, "Share"));
        });

        holder.binding.favoriteIcon.setOnClickListener(view -> {
            news.isFavorite = !news.isFavorite;
            this.favoriteListener.onClick(news);
            notifyItemChanged(position);
        });

        int isFavorite = news.isFavorite ? R.drawable.ic_favorite : R.drawable.ic_not_favorite;
        holder.binding.favoriteIcon.setImageResource(isFavorite);

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

    public interface FavoritesListener {
        void onClick(NewsData news);
    }

}
