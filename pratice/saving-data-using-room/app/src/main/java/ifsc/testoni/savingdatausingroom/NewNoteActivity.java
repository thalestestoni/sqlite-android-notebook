package ifsc.testoni.savingdatausingroom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class NewNoteActivity extends AppCompatActivity {
    private NoteViewModel mNoteViewModel;
    private EditText mNoteTitleView;
    private EditText mNoteTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        mNoteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);

        mNoteTitleView = findViewById(R.id.note_title);
        mNoteTextView = findViewById(R.id.note_text);

        setOnClickListenerSaveButton();
        setOnClickListenerCancelButton();

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.main_theme)));
    }

    private void setOnClickListenerSaveButton() {
        final Button saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener(view -> {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            dialogBuilder.setMessage(R.string.confirm_action_message)
                    .setPositiveButton(R.string.confirm_action_yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String title = mNoteTitleView.getText().toString();
                            String text = mNoteTextView.getText().toString();
                            Note note = new Note(title, text);
                            mNoteViewModel.insert(note);
                            finish();
                        }
                    })
                    .setNegativeButton(R.string.confirm_action_no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //Nothing
                        }
                    });
            dialogBuilder.create().show();
        });
    }

    private void setOnClickListenerCancelButton() {
        final Button cancelButton = findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(view -> {
            finish();
        });
    }
}