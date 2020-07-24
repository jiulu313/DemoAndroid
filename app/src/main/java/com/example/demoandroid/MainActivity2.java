package com.example.demoandroid;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.demoandroid.view.QCEmailTextArea;


public class MainActivity2 extends Activity {

	private QCEmailTextArea mQCEmailTextArea = null;
	private EditText mAllowInputStatusText = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main2);

		mAllowInputStatusText = (EditText) findViewById(R.id.allowInputStatusText);

		mQCEmailTextArea = (QCEmailTextArea) findViewById(R.id.emailTextArea);
		mQCEmailTextArea.setOnDeleteObjListener(new QCEmailTextArea.OnDeleteObjListener() {
			@Override
			public void delete(String key) {
				Log.i("My", "delete = " + key);
			}
		});

		mQCEmailTextArea.setOnAddObjListener(new QCEmailTextArea.OnAddObjListener() {

			@Override
			public void add(String objName, String outKey) {
				Toast.makeText(MainActivity2.this, objName, Toast.LENGTH_SHORT)
						.show();
			}
		});

		mAllowInputStatusText.setHint("isAllowInput = "
				+ mQCEmailTextArea.isAllowInputText());

		insertDatas();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mQCEmailTextArea.deleteDatas();
		mQCEmailTextArea.onDestroy();
	}

	public void testDrawable(View v) {
		mQCEmailTextArea.setDirtyTextBgColor(Color.rgb(0xff, 0x66, 0x66),
				Color.RED);
		mQCEmailTextArea.setValidTextBgColor(Color.rgb(0x66, 0x66, 0xff),
				Color.BLUE);
		mQCEmailTextArea.setDirtyTextFgColor(Color.GREEN, Color.WHITE);
		mQCEmailTextArea.setValidTextFgColor(Color.BLACK, Color.RED);
		// mQCEmailTextArea.setRoundRectPadding(0, 0, 0, 2);
		// mQCEmailTextArea.setRoundRectRadius(2, 2);
		mQCEmailTextArea.setTextSizeOfRoundRect(14);
	}

	public void recoverDrawable(View v) {
		mQCEmailTextArea.recoverDefaultValues();
	}

	public void testSetContent(View v) {
		String[] names = new String[] { "name1", "name2", "MZW@.com" };
		String[] keys = new String[] { "key1", "key2", "key3" };
		mQCEmailTextArea.setContent(names, keys);
	}

	public void testAppendContent(View v) {
		String[] names = new String[] { "name1", "name2", "name3" };
		String[] keys = new String[] { "key1", "key2", "key3" };
		mQCEmailTextArea.appendContent(names, keys);
	}

	public void testClearContent(View v) {
		mQCEmailTextArea.clearContent();
	}

	public void testSetAllowInputText(View v) {
		mQCEmailTextArea
				.setAllowInputText(!mQCEmailTextArea.isAllowInputText());
		mAllowInputStatusText.setHint("isAllowInput = "
				+ mQCEmailTextArea.isAllowInputText());
	}

	public void testGetOutKeys(View v) {
		String[] outKeys = mQCEmailTextArea.getOutKeys();
		for (int i = 0; outKeys != null && i < outKeys.length; i++)
			Log.i("My", "outKey = " + outKeys[i]);
	}

	// ***************************************************************************

	private void insertDatas() {
		String[] names = new String[] { "С��", "����", "������", "����ˮ��", "����", "����Ҳ",
				"������", "����" };
		String[] addresses = new String[] { "123456789", "012345678",
				"987654321", "696478844", "238974587", "85545615", "889821231",
				"927122132" };
		String[] outKeys = new String[] { "k-С��", "k-����", "k-������", "k-����ˮ��",
				"k-����", "k-����Ҳ", "k-������", "k-����" };
		mQCEmailTextArea.insertDatas(names, addresses, outKeys);
	}

}
