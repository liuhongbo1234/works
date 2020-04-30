package com.xiaomi.mi_deepclean.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xiaomi.mi_deepclean.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProgressDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProgressDialogFragment extends DialogFragment {
    int mIndeterminateDrawble;
    String mMessage;
    static View mContentView;

    public static ProgressDialogFragment newInstance(int indeterminateDrawble, String message) {
        ProgressDialogFragment f = new ProgressDialogFragment();
        Bundle args = new Bundle();
        args.putInt("indeterminateDrawble", indeterminateDrawble);
        args.putString("message", message);
        f.setArguments(args);
        return f;
    }
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        mIndeterminateDrawble = getArguments().getInt("indeterminateDrawble");
        mMessage = getArguments().getString("message");
        ProgressDialog mProcessDialog = new ProgressDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT);
        if (mIndeterminateDrawble > 0) {
            mProcessDialog.setIndeterminateDrawable(getActivity().getResources().getDrawable(mIndeterminateDrawble));
        }
        if (mMessage != null) {
            mProcessDialog.setMessage(mMessage);
        }
        return mProcessDialog;
    }

    public void setMessage(String mMessage) {
        if (mMessage != null) {
            setMessage(mMessage);
        }
    }
}
