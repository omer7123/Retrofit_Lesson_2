package com.ripalay.retrofitlesson2.ui.posts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ripalay.retrofitlesson2.data.models.Post;
import com.ripalay.retrofitlesson2.databinding.ItemPostBinding;

import java.util.ArrayList;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    private List<Post> list = new ArrayList<>();
    private onClick onLongClick;



    public void setOnLongClick(onClick onLongClick) {
        this.onLongClick = onLongClick;
    }

    public Post getList(int position) {
        return list.get(position);
    }

    public void setList(List<Post> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPostBinding binding = ItemPostBinding.inflate(LayoutInflater.from(parent.getContext()), parent,false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.onBind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemPostBinding binding;
        public ViewHolder(@NonNull ItemPostBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onLongClick.onLongClick(getAdapterPosition());
                    return false;
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onLongClick.onItemClick(getAdapterPosition());
                }
            });
        }

        public void onBind(Post post) {
            binding.userIdTv.setText(String.valueOf(post.getId()));
            binding.titleTv.setText(post.getTitle());
            binding.descriptionTv.setText(post.getContent());
        }
    }
    public interface onClick{
        void onLongClick(int position);
        void onItemClick(int position);
    }
}
