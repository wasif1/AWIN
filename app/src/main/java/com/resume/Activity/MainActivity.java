package com.resume.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.obsez.android.lib.filechooser.ChooserDialog;
import com.resume.Interface.MainInterface;
import com.resume.Presenter.MainPresenter;
import com.resume.R;
import com.resume.ViewHolder.MainView;
import com.resume.utills.Utils;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, MainInterface {

    private MainView viewHolder;
    private MainPresenter presenter;
    private File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        setListeners();
    }

    private void init() {
        viewHolder = new MainView(this);
        presenter = new MainPresenter(this, this);
    }

    private void setListeners() {
        viewHolder.getFile().setOnClickListener(this);
        viewHolder.getUpload().setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.file:
                new ChooserDialog(MainActivity.this)
                        .withChosenListener((path, pathFile) -> {
                            file = pathFile;
                            if (file != null) {
                                viewHolder.getFile().setText(file.getName());
                            }
                        })
                        .withOnCancelListener(DialogInterface::cancel)
                        .build()
                        .show();
                break;
            case R.id.upload:
                checkParams();
                break;
            default:
        }
    }

    private void checkParams() {
        if (file == null) {
            failure(getString(R.string.document_not_selected));
            viewHolder.getFile().setError(getString(R.string.document_not_selected));
            return;
        }
        if (TextUtils.isEmpty(viewHolder.getFirstname().toString())) {
            failure(getString(R.string.firstname));
            viewHolder.getFirstname().setError(getString(R.string.firstname));
            return;
        }
        if (TextUtils.isEmpty(viewHolder.getLastname().toString())) {
            failure(getString(R.string.lastname));
            viewHolder.getLastname().setError(getString(R.string.lastname));
            return;
        }
        if (TextUtils.isEmpty(viewHolder.getEmail().toString())) {
            failure(getString(R.string.email));
            viewHolder.getEmail().setError(getString(R.string.email));
            return;
        }
        if (TextUtils.isEmpty(viewHolder.getJobTitle().toString())) {
            failure(getString(R.string.jobTitle));
            viewHolder.getJobTitle().setError(getString(R.string.jobTitle));
            return;
        }
        if (TextUtils.isEmpty(viewHolder.getSource().toString())) {
            failure(getString(R.string.source));
            viewHolder.getSource().setError(getString(R.string.source));
            return;
        }

        if (presenter != null) {
            viewHolder.getUpload().setEnabled(false);
            Utils.hideKeyboard(this, viewHolder.getUpload());
            presenter.uploadResume(file,
                    viewHolder.getFirstname().toString(),
                    viewHolder.getLastname().toString(),
                    viewHolder.getEmail().toString(),
                    viewHolder.getJobTitle().toString(),
                    viewHolder.getSource().toString());
        }
    }

    private void clearFields() {
        viewHolder.getUpload().setEnabled(true);
        file = null;
        viewHolder.getFile().setText("");
        viewHolder.getFirstname().setText("");
        viewHolder.getLastname().setText("");
        viewHolder.getEmail().setText("");
        viewHolder.getJobTitle().setText("");
        viewHolder.getSource().setText("");
    }

    @Override
    public void success(String reason) {
        clearFields();
        Toast.makeText(this, getString(R.string.success_message), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void failure(String reason) {
        viewHolder.getUpload().setEnabled(true);
        Toast.makeText(this, reason, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loader(boolean flag) {
        if (flag) {
            viewHolder.getProgress().setVisibility(View.VISIBLE);
        } else {
            viewHolder.getProgress().setVisibility(View.GONE);
        }
    }
}