package ifsc.testoni.savingdatausingroom;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

public class NoteListAdapter extends ListAdapter<Note, NoteViewHolder> {
    public interface NoteOnClickListener {
        public void onClickNote(View view);
    }

    public NoteListAdapter(@NonNull DiffUtil.ItemCallback<Note> diffCallback) {
        super(diffCallback);
    }

    @Override
    public NoteViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        return NoteViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        Note note = getItem(position);
        holder.bind(note);
    }

    static class NoteDiff extends DiffUtil.ItemCallback<Note> {
        @Override
        public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.title.equals(newItem.title);
        }
    }
}
