package com.lcc.msdq.area;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.lcc.msdq.R;
import com.lcc.view.CityPicker;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class AreaDialogFragment extends DialogFragment  {
	private CityPicker cityPicker;

	public interface StringListener {
		void onStringInputComplete(String message);
	}

	public interface CodeListener {
		void onCodeInputComplete(String message);
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View view = inflater.inflate(R.layout.fragment_area_dialog, null);
		cityPicker = (CityPicker) view.findViewById(R.id.citypicker);
		builder.setView(view).setPositiveButton("确定", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								StringListener listener = (StringListener) getActivity();
								listener.onStringInputComplete(cityPicker.getCity_string());

								CodeListener codelistener = (CodeListener) getActivity();
								codelistener.onCodeInputComplete(cityPicker.getCity_code_string());
							}
						}).setNegativeButton("取消", null);
		return builder.create();
	}
}
