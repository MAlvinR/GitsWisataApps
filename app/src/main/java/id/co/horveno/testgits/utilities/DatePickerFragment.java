package id.co.horveno.testgits.utilities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import java.util.Date;
import java.util.GregorianCalendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.co.horveno.testgits.R;

public class DatePickerFragment extends DialogFragment {

    @BindView(R.id.datepicker_dialog)
    public DatePicker datePicker;

    public interface DateDialogListener {
        void onFinishDialog(Date date);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final View dateView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_date, null);
        ButterKnife.bind(this, dateView);
        return new AlertDialog.Builder(getActivity())
                .setView(dateView)
                .setTitle("Select Date")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int year = datePicker.getYear();
                        int month = datePicker.getMonth();
                        int day = datePicker.getDayOfMonth();
                        Date date = new GregorianCalendar(year, month, day).getTime();
                        DateDialogListener activity = (DateDialogListener) getActivity();
                        activity.onFinishDialog(date);
                        dismiss();
                    }
                }).create();

    }
}
