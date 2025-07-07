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

import dagger.hilt.android.AndroidEntryPoint;
import dev.dgomes.footballNews.databinding.FragmentNewsBinding;
import dev.dgomes.footballNews.ui.adapter.NewsAdapter;

@AndroidEntryPoint
public class NewsFragment extends Fragment {

    private FragmentNewsBinding binding;
    private NewsViewModel newsViewModel;
    private NewsAdapter newsAdapter;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        newsViewModel = new ViewModelProvider(this).get(NewsViewModel.class);

        binding = FragmentNewsBinding.inflate(inflater, container, false);

        // Initialize adapter and set it once
        newsAdapter = new NewsAdapter(news -> newsViewModel.toggleFavorite(news));
        binding.newsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.newsRecyclerView.setAdapter(newsAdapter);

        observeNews();
        observeStates();

        binding.swipeNews.setOnRefreshListener(newsViewModel::fetchNews);

        return binding.getRoot();
    }

    private void observeStates() {
        newsViewModel.getNewsState().observe(getViewLifecycleOwner(), state -> {
            System.out.println("News State changed: " + state.getStatus());
            switch (state.getStatus()) {
                case LOADING:
                    binding.swipeNews.setRefreshing(true);
                    break;
                case SUCCESS:
                    binding.swipeNews.setRefreshing(false);
                    break;
                case ERROR:
                    binding.swipeNews.setRefreshing(false);
                    Snackbar.make(
                            binding.getRoot(),
                            state.getErrorMessage(),
                            Snackbar.LENGTH_SHORT
                    ).show();
            }
        });
    }

    private void observeNews() {
        newsViewModel.getNewsState().observe(
                getViewLifecycleOwner(),
                state -> {
                    System.out.println("News changed: " + state.getAllNewsList());
                    newsAdapter.submitList(state.getAllNewsList());
                }
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}