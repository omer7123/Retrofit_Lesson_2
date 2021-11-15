package com.ripalay.retrofitlesson2.ui.form;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ripalay.retrofitlesson2.App;
import com.ripalay.retrofitlesson2.R;
import com.ripalay.retrofitlesson2.data.models.Post;
import com.ripalay.retrofitlesson2.databinding.FragmentFormBinding;
import com.ripalay.retrofitlesson2.ui.posts.PostAdapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormFragment extends Fragment {
    private FragmentFormBinding binding;
    private PostAdapter adapter;
    private NavController controller;
    private NavHostFragment navHostFragment;
    private int id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFormBinding.inflate(inflater);
        navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        controller = navHostFragment.getNavController();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            Post post = (Post) getArguments().getSerializable("post");
             id = post.getId();
            binding.titleEt.setText(post.getTitle());
            binding.descEt.setText(post.getContent());
            binding.userIdEt.setText(String.valueOf(post.getId()));
        }
        binding.createPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Post post = new Post(
                        binding.titleEt.getText().toString(),
                        binding.descEt.getText().toString(),
                        Integer.parseInt(binding.userIdEt.getText().toString()),
                        35
                );
                if (getArguments() == null) {

                    App.api.createPost(post).enqueue(new Callback<Post>() {
                        @Override
                        public void onResponse(Call<Post> call, Response<Post> response) {
                            if (response.isSuccessful()) {
                                controller.popBackStack();
                            }
                        }

                        @Override
                        public void onFailure(Call<Post> call, Throwable t) {

                        }
                    });
                }else{
                    App.api.updatePost(id, post).enqueue(new Callback<Post>() {
                        @Override
                        public void onResponse(Call<Post> call, Response<Post> response) {
                            if (response.isSuccessful()) {
                                controller.popBackStack();
                            }

                        }

                        @Override
                        public void onFailure(Call<Post> call, Throwable t) {
                            Log.e("ololo", t.getLocalizedMessage());
                        }
                    });
                }
            }
        });
    }
}