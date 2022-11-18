package ifsc.testoni.savingdatausingroom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

public class NewNoteActivity extends AppCompatActivity {
    private EditText mEditNoteView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);
        mEditNoteView = findViewById(R.id.edit_note);

        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(view -> {
            Intent replyIntent = new Intent();
            if (TextUtils.isEmpty(mEditNoteView.getText())) {
                setResult(RESULT_CANCELED, replyIntent);
            } else {
                String title = mEditNoteView.getText().toString();
                replyIntent.putExtra("title", title);
                setResult(RESULT_OK, replyIntent);
            }
            finish();
        });
    }
}