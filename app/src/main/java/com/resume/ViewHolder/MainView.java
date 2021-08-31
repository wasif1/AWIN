package com.resume.ViewHolder;

import android.app.Activity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.resume.R;

public class MainView {

    private EditText file;
    private EditText firstname;
    private EditText lastname;
    private EditText email;
    private EditText jobTitle;
    private EditText source;
    private Button upload;
    private RelativeLayout progress;

    public MainView(Activity activity) {
        file = activity.findViewById(R.id.file);
        firstname = activity.findViewById(R.id.firstname);
        lastname = activity.findViewById(R.id.lastname);
        email = activity.findViewById(R.id.email);
        jobTitle = activity.findViewById(R.id.jobTitle);
        source = activity.findViewById(R.id.source);
        upload = activity.findViewById(R.id.upload);
        progress = activity.findViewById(R.id.progress);
    }

    public RelativeLayout getProgress() {
        return progress;
    }

    public EditText getFile() {
        return file;
    }

    public EditText getFirstname() {
        return firstname;
    }

    public EditText getLastname() {
        return lastname;
    }

    public EditText getEmail() {
        return email;
    }

    public EditText getJobTitle() {
        return jobTitle;
    }

    public EditText getSource() {
        return source;
    }

    public Button getUpload() {
        return upload;
    }
}
