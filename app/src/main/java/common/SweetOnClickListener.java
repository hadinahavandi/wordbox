package common;

import android.view.View;
import android.view.View.OnClickListener;

public abstract class SweetOnClickListener implements OnClickListener {
	int[] DataInt;
	public int[] getDataInt() {
		return DataInt;
	}
	public void setDataInt(int[] dataInt) {
		DataInt = dataInt;
	}
	public SweetOnClickListener(int Data[]) {
		this.DataInt = Data;
	}
	@Override
	public abstract void onClick(View arg0);

}
