package cn.agiledata.android.sdk.sample;

import cn.agiledata.android.sdk.SocketClient;

import com.ajrd.android.util.MyUtil;
import com.ajrd.android.util.RequestUtil;
import com.example.ajrd_sdktest.R;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends BaseActivity {

	/**
	 * ǩ����ť
	 */
	private Button btn_sign_in;

	/**
	 * ǩ��16���Ʊ���
	 */
	private String request = "08002038000000C00001393930303030303030303231313331353237303630363030303030303031393939303539353533313130303031DF70918A70E8DCBE";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		btn_sign_in = (Button) findViewById(R.id.btn_sign_in);
		btn_sign_in.setOnClickListener(new BtnSignInClickListener());
	}

	private class BtnSignInClickListener implements OnClickListener {

		@Override
		public void onClick(View arg0) {
            /************************************************* 
             * 
             *  ����1����װ�Լ���������
             *  
             ************************************************/
			MyAsyTask mTask = new MyAsyTask(RequestUtil.getDataAppendLen(request));
			mTask.execute();
		}

	}

	private class MyAsyTask extends AsyncTask<Object, Object, Object> {

		private String mRequest;

		public MyAsyTask(String request) {
			mRequest = request;
			Log.e("mRequest", ""+mRequest);
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			showProgressDialog("��¼��...");
		}

		@Override
		protected Object doInBackground(Object... params) {

			return null;
		}

		@Override
		protected void onPostExecute(Object result) {
			super.onPostExecute(result);
			/************************************************* 
             * 
             *  ����2�������ķ��͵�������
             *  
             ************************************************/
			SocketClient client = new SocketClient(MyUtil.hexStringToByte(mRequest), loginHandler);
			client.start();
		}

	}

	Handler loginHandler = new Handler() {
		public void handleMessage(Message msg) {
			/************************************************* 
             * 
             *  ����3���������ܺ�ķ��ر��ģ�����Ӧ����
             *  
             ************************************************/
			switch (msg.what) {
			case RequestUtil.REQUEST_SUCCESS: {
				//
				String hex_response = (String) msg.obj;
				if (hex_response != null && hex_response.length() > 0) {
					System.out.println("hex_response:"+hex_response);
				}
				//ת��Ϊ�ֽ����鱨�Ĵ���ȫ�����Ѿ����ܣ�
				byte[] response = MyUtil.hexStringToByte(hex_response);
				
			}break;
			case RequestUtil.REQUEST_FAIELD:{
				String err_msg = (String)msg.obj;
				if(err_msg!=null&&err_msg.length()>0){
					showShortToast(err_msg);
				}
				
			}break;
			default:
				break;
			}
			if(mAlertDialog!=null&&mAlertDialog.isShowing()){
				mAlertDialog.dismiss();
			}
		};

	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
