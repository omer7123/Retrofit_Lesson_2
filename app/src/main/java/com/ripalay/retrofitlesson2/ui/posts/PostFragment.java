package com.ripalay.retrofitlesson2.ui.posts;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ripalay.retrofitlesson2.App;
import com.ripalay.retrofitlesson2.R;
import com.ripalay.retrofitlesson2.data.models.Post;
import com.ripalay.retrofitlesson2.databinding.FragmentPostBinding;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostFragment extends Fragment {
    private FragmentPostBinding binding;
    private NavController controller;
    private NavHostFragment navHostFragment;
    private PostAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new PostAdapter();
        adapter.setOnLongClick(new PostAdapter.onClick() {
            @Override
            public void onLongClick(int position) {
                Post post = adapter.getList(position);
                AlertDialog.Builder alert = new AlertDialog.Builder(requireActivity());
                alert.setTitle("Вы точно хотите удалить?")
                        .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                App.api.deletePost(post.getId()).enqueue(new Callback<Post>() {
                                    @Override
                                    public void onResponse(Call<Post> call, Response<Post> response) {
                                        App.api.getPosts().enqueue(new Callback<List<Post>>() {
                                            @Override
                                            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                                                adapter.setList(response.body());
                                            }

                                            @Override
                                            public void onFailure(Call<List<Post>> call, Throwable t) {

                                            }
                                        });
                                    }

                                    @Override
                                    public void onFailure(Call<Post> call, Throwable t) {

                                    }
                                });

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
            }

            @Override
            public void onItemClick(int position) {
                Post post = adapter.getList(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("post", post);
                controller.navigate(R.id.formFragment, bundle);
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentPostBinding.inflate(inflater);
        navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        controller = navHostFragment.getNavController();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.navigate(R.id.formFragment);
            }
        });
        binding.postRv.setAdapter(adapter);
        App.api.getPosts().enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.isSuccessful() && response.body() != null)
                    adapter.setList(response.body());
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {

            }
        });
    }
}