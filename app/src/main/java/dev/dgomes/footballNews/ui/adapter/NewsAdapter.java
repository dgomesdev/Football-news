package dev.dgomes.footballNews.ui.adapter;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import dev.dgomes.footballNews.R;
import dev.dgomes.footballNews.databinding.NewsItemBinding;
import dev.dgomes.footballNews.domain.NewsModel;

public class NewsAdapter extends ListAdapter<NewsModel, NewsAdapter.NewsViewHolder> {

    private final NewsListener newsListener;

    public NewsAdapter(NewsListener newsListener) {
        super(DIFF_CALLBACK);
        this.newsListener = newsListener;
    }

    private static final DiffUtil.ItemCallback<NewsModel> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<>() {
                @Override
                public boolean areItemsTheSame(@NonNull NewsModel oldItem, @NonNull NewsModel newItem) {
                    return oldItem.id == newItem.id;
                }

                @Override
                public boolean areContentsTheSame(@NonNull NewsModel oldItem, @NonNull NewsModel newItem) {
                    return oldItem.title.equals(newItem.title)
                            && oldItem.text.equals(newItem.text)
                            && oldItem.url.equals(newItem.url)
                            && oldItem.image.equals(newItem.image)
                            && oldItem.isFavorite == newItem.isFavorite;
                }
            };

    public static class NewsViewHolder extends RecyclerView.ViewHolder {
        private final NewsItemBinding binding;

        public NewsViewHolder(NewsItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        NewsItemBinding binding = NewsItemBinding.inflate(inflater, parent, false);
        return new NewsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        NewsModel news = getItem(position);

        holder.binding.newsTitle.setText(news.title);
        holder.binding.newsText.setText(news.text);
        Picasso.get().load(news.image).into(holder.binding.newsImage);

        holder.binding.openLinkButton.setOnClickListener(view ->
                view.getContext().startActivity(
                        new Intent(Intent.ACTION_VIEW, Uri.parse(news.url))
                )
        );

        holder.binding.shareIcon.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TITLE, news.title);
            intent.putExtra(Intent.EXTRA_TEXT, news.text);
            intent.setType("text/plain");
            view.getContext().startActivity(Intent.createChooser(intent, "Share"));
        });

        holder.binding.favoriteIcon.setOnClickListener(view -> newsListener.onClick(news));

        int favoriteIcon = news.isFavorite ? R.drawable.ic_favorite : R.drawable.ic_not_favorite;
        holder.binding.favoriteIcon.setImageResource(favoriteIcon);
    }

    public interface NewsListener {
        void onClick(NewsModel news);
    }
}