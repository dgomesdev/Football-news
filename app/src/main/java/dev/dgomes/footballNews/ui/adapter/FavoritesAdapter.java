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

public class FavoritesAdapter extends ListAdapter<NewsModel, FavoritesAdapter.FavoritesViewHolder> {

    private final FavoritesListener favoriteListener;

    public FavoritesAdapter(FavoritesListener favoritesListener) {
        super(DIFF_CALLBACK);
        this.favoriteListener = favoritesListener;
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

    public static class FavoritesViewHolder extends RecyclerView.ViewHolder {

        private final NewsItemBinding binding;

        public FavoritesViewHolder(NewsItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @NonNull
    @Override
    public FavoritesViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        NewsItemBinding binding = NewsItemBinding.inflate(
                layoutInflater,
                viewGroup,
                false
        );
        return new FavoritesViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(FavoritesViewHolder holder, int position) {
        NewsModel news = getItem(position);
        holder.binding.newsTitle.setText(news.title);
        holder.binding.newsText.setText(news.text);
        Picasso.get().load(news.image).into(holder.binding.newsImage);

        holder.binding.openLinkButton.setOnClickListener(view ->
                view.getContext()
                        .startActivity(
                                new Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse(news.url)
                                )
                        )
        );

        holder.binding.shareIcon.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TITLE, news.title);
            intent.putExtra(Intent.EXTRA_TEXT, news.text);
            intent.setType("text/plain");
            view.getContext().startActivity(Intent.createChooser(intent, "Share"));
        });

        holder.binding.favoriteIcon.setOnClickListener(view -> this.favoriteListener.onClick(news));

        int isFavorite = news.isFavorite ? R.drawable.ic_favorite : R.drawable.ic_not_favorite;
        holder.binding.favoriteIcon.setImageResource(isFavorite);
    }

        public interface FavoritesListener {
            void onClick(NewsModel news);
        }

}
