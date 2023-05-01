package dev.dgomes.footballNews.ui.news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.snackbar.Snackbar;

import dev.dgomes.footballNews.databinding.FragmentNewsBinding;
import dev.dgomes.footballNews.ui.adapter.NewsAdapter;

public class NewsFragment extends Fragment {

    private FragmentNewsBinding binding;
    private NewsViewModel newsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        newsViewModel =
                new ViewModelProvider(this).get(NewsViewModel.class);

        binding = FragmentNewsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.newsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        observeNews();
        observeStates();

        binding.swipeNews.setOnRefreshListener(newsViewModel::loadNews);
        return root;
    }

    private void observeStates() {
        newsViewModel.getState().observe(getViewLifecycleOwner(), state -> {
            switch (state) {
                case LOADING:
                    binding.swipeNews.setRefreshing(true);
                    break;
                case SUCCESS:
                    binding.swipeNews.setRefreshing(false);
                    break;
                case ERROR:
                    binding.swipeNews.setRefreshing(false);
                    Snackbar.make(binding.getRoot(), "Failure", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void observeNews() {
        newsViewModel.getNews().observe(getViewLifecycleOwner(), news -> binding.newsRecyclerView.setAdapter(new NewsAdapter(news, newsViewModel::saveNews)));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}