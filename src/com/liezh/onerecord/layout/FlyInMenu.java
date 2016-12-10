package com.liezh.onerecord.layout;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;

import com.liezh.onerecord.R;


/**
 * 自定义一个FrameLayout，实现抽屉菜单效果
 * 其实就是监听触碰事件，确定手指位移是否超过屏幕一半
 * 如果是则打开或关闭，否则恢复原来
 * @author chen
 * 
 */
public class FlyInMenu extends FrameLayout {

	private static final int SNAP_VELOCITY = 400;
	private static final int FINAL_DP = 50;

	private int menuId, rightLayoutId;

	private View menu;
	private View rightLayout;

	private int touchSlop;

	private float lastMotionX;
	private Context myContext;

	private int testRight = 240;
	private float startX;
	private boolean sudu = false;

	private boolean isOpened = true;
	private VelocityTracker velocityTracker;
	private int velocityX;

	public int duration = 500;
	public boolean linearFlying = true;
	private int finalDis;

	private Handler myHandler;
	private int everyMSpd;

	private enum State {
		ANIMATING, READY, TRACKING,
	};

	private State mState;

	public FlyInMenu(Context context, AttributeSet attrs) {
		super(context, attrs);
		myContext = context;

		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.FlyInMenu);
		duration = a.getInteger(R.styleable.FlyInMenu_animationDuration, 500);
		RuntimeException e = null;
		menuId = a.getResourceId(R.styleable.FlyInMenu_menu, 0);
		if (menuId == 0) {
			e = new IllegalArgumentException(
					a.getPositionDescription()
							+ ": The handle attribute is required and must refer to a valid child.");
		}
		rightLayoutId = a.getResourceId(R.styleable.FlyInMenu_content, 0);
		if (rightLayoutId == 0) {
			e = new IllegalArgumentException(
					a.getPositionDescription()
							+ ": The content attribute is required and must refer to a valid child.");
		}
		a.recycle();

		if (e != null) {
			throw e;
		}

		myHandler = new Handler();
		mState = State.READY;
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		menu = findViewById(menuId);
		if (menu == null) {
			throw new RuntimeException();
		}
		rightLayout = findViewById(rightLayoutId);
		if (rightLayout == null) {
			throw new RuntimeException();
		}

		removeView(menu);
		removeView(rightLayout);

		addView(menu);
		addView(rightLayout);

		touchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		System.out.println("onLayout");
		super.onLayout(changed, left, top, right, bottom);
		final float scale = myContext.getResources().getDisplayMetrics().density;
		testRight = this.getWidth() - (int) (FINAL_DP * scale + 0.5f);
		everyMSpd = (testRight * 16) / duration;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		int action = ev.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			Log.d(VIEW_LOG_TAG, "viewgroup intercept down");
			break;
		case MotionEvent.ACTION_MOVE:
			Log.d(VIEW_LOG_TAG, "viewgroup intercept move");
			break;
		case MotionEvent.ACTION_UP:
			Log.d(VIEW_LOG_TAG, "viewgroup intercept up");
			break;
		}

		return false;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		// Log.d(VIEW_LOG_TAG, "mState="+mState);
		if (mState == State.ANIMATING) {
			return false;
		}

		if (velocityTracker == null) {
			velocityTracker = VelocityTracker.obtain();
		}
		velocityTracker.addMovement(event);

		final int action = event.getAction();
		float x = event.getX();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			Log.d(VIEW_LOG_TAG, "down");

			if (!isOpened) {
				if (event.getX() < rightLayout.getLeft())
					return false;
			}

			if (isOpened) {
				menu.setVisibility(VISIBLE);
			}

			lastMotionX = x;
			startX = x;
			break;
		case MotionEvent.ACTION_MOVE:
			// Log.d(VIEW_LOG_TAG, "move");

			mState = State.TRACKING;

			int deltaX = (int) (lastMotionX - x);
			lastMotionX = x;
			// 鍚戝彸婊戯紝濡傛灉宸茬粡鍒版渶鍙宠竟灏卞仠姝㈡粦鍔�
			if (deltaX < 0) {
				if (rightLayout.getLeft() >= testRight)
					break;
				if (deltaX < (rightLayout.getLeft() - testRight))
					deltaX = (int) rightLayout.getLeft() - testRight;

			}
			// 鍚戝乏婊戯紝 鍒版渶宸﹁竟灏卞仠姝㈡粦鍔�
			else {
				if (rightLayout.getLeft() < 0)
					break;
				if (deltaX > rightLayout.getLeft())
					deltaX = rightLayout.getLeft();
			}

			// rightLayout.scrollBy(deltaX, 0);
			rightLayout.offsetLeftAndRight(-deltaX);
			// rightLayout.scrollBy(-deltaX, 0);
			invalidate();
			break;
		case MotionEvent.ACTION_UP:
			Log.d(VIEW_LOG_TAG, "up");

			final VelocityTracker tempVelocityTracker = velocityTracker;
			tempVelocityTracker.computeCurrentVelocity(1000);
			velocityX = (int) tempVelocityTracker.getXVelocity();
			System.out.println("velocityX=" + velocityX);
			if (velocityX > SNAP_VELOCITY || velocityX < -SNAP_VELOCITY) {
				sudu = true;
			} else {
				sudu = false;
			}

			if (velocityTracker != null) {
				velocityTracker.recycle();
				velocityTracker = null;
			}

			// 褰撹繖鏄竴娆＄偣鍑诲姩浣�
			if (Math.abs(x - startX) < touchSlop) {
				if (isOpened) {
					setToOpen();
				} else {
					setToClose();
				}
			}
			// 褰撹繖鏄竴娆℃粦鍔ㄤ簨浠�
			else {
				// 鏄竴娆℃粦鍔紝涓斾箣鍓嶆槸鎵撳紑鐨勭姸鎬�
				if (isOpened) {
					// 鏄竴娆″悜宸︾殑婊戝姩涓嶅仛澶勭悊
					if (x - startX < 0)
						break;

					// 濡傛灉鏄悜鍙崇殑婊戝姩锛屽鏋滈�搴︽弧瓒冲�

					if (sudu) {
						setToClose();
					}
					// 濡傛灉閫熷害涓嶆弧瓒冲�
					else {
						// 鍒ゆ柇鏄惁婊戣繃涓�崐璺濈浠ヤ笂
						if (rightLayout.getLeft() > (testRight / 2)) {
							setToClose();
						} else {
							setToOpen();
						}
					}

				}
				// 濡傛灉涔嬪墠鏄叧闂殑鐘舵�
				else {
					// 濡傛灉鏄悜鍙崇殑婊戝姩涓嶅仛澶勭悊
					if (x - startX > 0)
						break;

					if (sudu) {
						setToOpen();
					} else {
						if (rightLayout.getLeft() < (testRight / 2)) {
							setToOpen();
						} else {
							setToClose();
						}
					}
				}
			}

			break;
		case MotionEvent.ACTION_CANCEL:
			mState = State.READY;
			break;
		}
		return true;
	}

	private void setToClose() {
		isOpened = false;
		finalDis = rightLayout.getLeft() - testRight;
		if (finalDis == 0) {
			mState = State.READY;
		} else {
			updateConUI(-finalDis, false);
		}
	}

	private void setToOpen() {
		isOpened = true;
		finalDis = rightLayout.getLeft();
		if (finalDis == 0) {
			mState = State.READY;
		} else {
			updateConUI(finalDis, true);
		}
	}

	private void updateConUI(int length, final boolean minus) {
		mState = State.ANIMATING;
		Log.d(VIEW_LOG_TAG, "mState=" + mState);
		int i = 0;
		while (length > 0) {
			if (length < everyMSpd) {
				if (minus)
					updatePer(-length, i);
				else
					updatePer(length, i);
				length = 0;
			} else {
				length -= everyMSpd;
				if (minus)
					updatePer(-everyMSpd, i);
				else
					updatePer(everyMSpd, i);
			}
			i++;
		}

	}

	private void updatePer(final int length, final int number) {

		myHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				rightLayout.offsetLeftAndRight(length);
				invalidate();
				if (rightLayout.getLeft() == 0
						|| rightLayout.getLeft() == testRight) {
					mState = State.READY;
					if (rightLayout.getLeft() == 0)
						menu.setVisibility(GONE);
					System.out.println("end update state=" + mState);
				}

			}
		}, 16 * number);
	}

}
