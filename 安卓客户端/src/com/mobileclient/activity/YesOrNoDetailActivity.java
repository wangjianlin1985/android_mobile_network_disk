package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.YesOrNo;
import com.mobileclient.service.YesOrNoService;
import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import android.widget.Toast;
public class YesOrNoDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明记录id控件
	private TextView TV_yesNoId;
	// 声明记录名称控件
	private TextView TV_yesNoName;
	/* 要保存的是否信息信息 */
	YesOrNo yesOrNo = new YesOrNo(); 
	/* 是否信息管理业务逻辑层 */
	private YesOrNoService yesOrNoService = new YesOrNoService();
	private int yesNoId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.yesorno_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看是否信息详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_yesNoId = (TextView) findViewById(R.id.TV_yesNoId);
		TV_yesNoName = (TextView) findViewById(R.id.TV_yesNoName);
		Bundle extras = this.getIntent().getExtras();
		yesNoId = extras.getInt("yesNoId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				YesOrNoDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    yesOrNo = yesOrNoService.GetYesOrNo(yesNoId); 
		this.TV_yesNoId.setText(yesOrNo.getYesNoId() + "");
		this.TV_yesNoName.setText(yesOrNo.getYesNoName());
	} 
}
