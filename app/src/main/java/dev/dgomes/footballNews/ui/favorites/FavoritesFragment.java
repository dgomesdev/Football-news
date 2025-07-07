package dev.dgomes.footballNews.ui.favorites;

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
import dev.dgomes.footballNews.databinding.FragmentFavoritesBinding;
import dev.dgomes.footballNews.ui.adapter.FavoritesAdapter;

@AndroidEntryPoint
public class FavoritesFragment extends Fragment {

    private FragmentFavoritesBinding binding;
    private FavoritesViewModel favoritesViewModel;

    private FavoritesAdapter favoritesAdapter;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        favoritesViewModel =
                new ViewModelProvider(this).get(FavoritesViewModel.class);

        binding = FragmentFavoritesBinding.inflate(inflater, container, false);

        favoritesAdapter = new FavoritesAdapter(
                news -> favoritesViewModel.removeFavorite(news)
        );
        binding.favoritesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.favoritesRecyclerView.setAdapter(favoritesAdapter);

        observeNews();
        observeStates();

        return binding.getRoot();
    }

    private void observeStates() {
        favoritesViewModel.getFavoritesState().observe(getViewLifecycleOwner(), state -> {
            System.out.println("Favorites State changed: " + state.getStatus());
            switch (state.getStatus()) {
                case LOADING:
                    binding.swipeFavorites.setRefreshing(true);
                    break;
                case SUCCESS:
                    binding.swipeFavorites.setRefreshing(false);
                    break;
                case ERROR:
                    binding.swipeFavorites.setRefreshing(false);
                    Snackbar.make(
                            binding.getRoot(),
                            state.getErrorMessage(),
                            Snackbar.LENGTH_SHORT
                    ).show();
            }
        });
    }

    private void observeNews() {
        favoritesViewModel.getFavoritesState().observe(
                getViewLifecycleOwner(),
                state -> {
                    System.out.println("Favorites changed: " + state.getFavoritesList());
                    favoritesAdapter.submitList(state.getFavoritesList());
                }
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}