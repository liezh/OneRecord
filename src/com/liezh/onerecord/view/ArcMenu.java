package com.liezh.onerecord.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

import com.liezh.onerecord.R;

/**
 * �Զ���һ��viewGroup
 * @author Administrator
 *
 */
public class ArcMenu extends ViewGroup implements OnClickListener
{
	private static final int POS_LEFT_TOP = 0;
	private static final int POS_LEFT_BOTTOM = 1;
	private static final int POS_RIGHT_TOP = 2;
	private static final int POS_RIGHT_BOTTOM = 3;
	
	

	private Position mPosition = Position.RIGHT_BOTTOM;
	private int mRadius;
	private int mOffset;
	/**
	 * �˵���ǰ�Ŀ���״̬��Ĭ��Ϊ�ر�
	 */
	private Status mCurrentStatus = Status.CLOSE;
	/**
	 * ����ť
	 */
	private View mCButton;

	private OnMenuItemClickListener mMenuItemClickListener;

	public enum Status
	{
		OPEN, CLOSE
	}

	/**
	 * 菜单的位置枚举类
	 */
	public enum Position
	{
		LEFT_TOP, LEFT_BOTTOM, RIGHT_TOP, RIGHT_BOTTOM
	}

	/**
	 * �Ӱ�ť�ļ����¼���interface
	 */
	public interface OnMenuItemClickListener
	{
		void onClick(View view, int pos);
	}

	public void setOnMenuItemClickListener(
			OnMenuItemClickListener mMenuItemClickListener)
	{
		this.mMenuItemClickListener = mMenuItemClickListener;
	}

	public ArcMenu(Context context)
	{
		this(context, null);
	}

	public ArcMenu(Context context, AttributeSet attrs)
	{
		this(context, attrs, 0);
	}

	public ArcMenu(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);

		// ��ʼ���˵��뾶��ƫ����������Ĭ��ֵ�͵�λ
		mRadius = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				100, getResources().getDisplayMetrics());
		mOffset = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				0, getResources().getDisplayMetrics());
		

		// ��ȡ�˵�����������
		TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
				R.styleable.ArcMenu, defStyle, 0);

		int pos = a.getInt(R.styleable.ArcMenu_position, POS_RIGHT_BOTTOM);
		switch (pos)
		{
		case POS_LEFT_TOP:
			mPosition = Position.LEFT_TOP;
			break;
		case POS_LEFT_BOTTOM:
			mPosition = Position.LEFT_BOTTOM;
			break;
		case POS_RIGHT_TOP:
			mPosition = Position.RIGHT_TOP;
			break;
		case POS_RIGHT_BOTTOM:
			mPosition = Position.RIGHT_BOTTOM;
			break;
		}
		mRadius = (int) a.getDimension(R.styleable.ArcMenu_radius, TypedValue
				.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100,
						getResources().getDisplayMetrics()));
		mOffset = (int) a.getDimension(R.styleable.ArcMenu_offset, TypedValue
				.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0,
						getResources().getDisplayMetrics()));

		Log.e("TAG", "position = " + mPosition + " , radius =  " + mRadius);

		a.recycle();

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		int count = getChildCount();
		for (int i = 0; i < count; i++)
		{
			measureChild(getChildAt(i), widthMeasureSpec, heightMeasureSpec);
		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b)
	{
		if (changed)
		{
			layoutCButton();

			int count = getChildCount();

			for (int i = 0; i < count - 1; i++)
			{
				View child = getChildAt(i + 1);

				child.setVisibility(View.GONE);

				int cl = (int) (mRadius * Math.sin(Math.PI / 2 / (count - 2)
						* i));
				int ct = (int) (mRadius * Math.cos(Math.PI / 2 / (count - 2)
						* i));

				int cWidth = child.getMeasuredWidth();
				int cHeight = child.getMeasuredHeight();

				// �����Ӱ�ť��λ��
				if (mPosition == Position.LEFT_BOTTOM
						|| mPosition == Position.RIGHT_BOTTOM)
				{
					ct = getMeasuredHeight() - cHeight - ct - mOffset;
				}
				// 右上，右�?
				if (mPosition == Position.RIGHT_TOP
						|| mPosition == Position.RIGHT_BOTTOM)
				{
					cl = getMeasuredWidth() - cWidth - cl - mOffset;
				}
				child.layout(cl, ct, cl + cWidth, ct + cHeight);

			}

		}

	}

	/**
	 * ��������ť��λ��
	 */
	private void layoutCButton()
	{
		mCButton = getChildAt(0);
		mCButton.setOnClickListener(this);

		int l = 0;
		int t = 0;

		int width = mCButton.getMeasuredWidth();
		int height = mCButton.getMeasuredHeight();

		switch (mPosition)
		{
		case LEFT_TOP:
			l = 0 + mOffset;
			t = 0 + mOffset;
			break;
		case LEFT_BOTTOM:
			l = 0 + mOffset;
			t = getMeasuredHeight() - height - mOffset;
			break;
		case RIGHT_TOP:
			l = getMeasuredWidth() - width - mOffset;
			t = 0;
			break;
		case RIGHT_BOTTOM:
			l = getMeasuredWidth() - width - mOffset;
			t = getMeasuredHeight() - height - mOffset;
			break;
		}
		mCButton.layout(l, t, l + width, t + width);
	}

	@Override
	public void onClick(View v)
	{
		// mCButton = findViewById(R.id.id_button);
		// if(mCButton == null)
		// {
		// mCButton = getChildAt(0);
		// }

		rotateCButton(v, 0f, 360f, 300);

		toggleMenu(300);

	}

	/**
	 * ���ز˵�ʱ�Ķ���
	 */
	public void toggleMenu(int duration)
	{
		int count = getChildCount();

		for (int i = 0; i < count - 1; i++)
		{
			final View childView = getChildAt(i + 1);
			childView.setVisibility(View.VISIBLE);

			// end 0 , 0
			// start
			int cl = (int) (mRadius * Math.sin(Math.PI / 2 / (count - 2) * i));
			int ct = (int) (mRadius * Math.cos(Math.PI / 2 / (count - 2) * i));

			int xflag = 1;
			int yflag = 1;

			if (mPosition == Position.LEFT_TOP
					|| mPosition == Position.LEFT_BOTTOM)
			{
				xflag = -1;
			}

			if (mPosition == Position.LEFT_TOP
					|| mPosition == Position.RIGHT_TOP)
			{
				yflag = -1;
			}

			AnimationSet animset = new AnimationSet(true);
			Animation tranAnim = null;

			// to open
			if (mCurrentStatus == Status.CLOSE)
			{
				tranAnim = new TranslateAnimation(xflag * cl, 0, yflag * ct, 0);
				childView.setClickable(true);
				childView.setFocusable(true);

			} else
			// to close
			{
				tranAnim = new TranslateAnimation(0, xflag * cl, 0, yflag * ct);
				childView.setClickable(false);
				childView.setFocusable(false);
			}
			tranAnim.setFillAfter(true);
			tranAnim.setDuration(duration);
			// ʹÿ���Ӱ�ť������ʼʱ�䲻ͬ
			tranAnim.setStartOffset((i * 100) / count);

			tranAnim.setAnimationListener(new AnimationListener()
			{

				@Override
				public void onAnimationStart(Animation animation)
				{

				}

				@Override
				public void onAnimationRepeat(Animation animation)
				{

				}

				@Override
				public void onAnimationEnd(Animation animation)
				{
					if (mCurrentStatus == Status.CLOSE)
					{
						childView.setVisibility(View.GONE);
					}
				}
			});
			// �����Ӱ�ť�ر�ʱ����ת������ת��Ȧ
			RotateAnimation rotateAnim = new RotateAnimation(0, 720,
					Animation.RELATIVE_TO_SELF, 0.5f,
					Animation.RELATIVE_TO_SELF, 0.5f);
			rotateAnim.setDuration(duration);
			rotateAnim.setFillAfter(true);

			animset.addAnimation(rotateAnim);
			animset.addAnimation(tranAnim);
			childView.startAnimation(animset);

			final int pos = i + 1;
			childView.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					if (mMenuItemClickListener != null)
						mMenuItemClickListener.onClick(childView, pos);

					menuItemAnim(pos - 1);
					changeStatus();

				}
			});
		}
		// �ı�˵�״̬
		changeStatus();
	}

	/**
	 * �������Ӱ�ť�Ķ���Ч��
	 * 
	 * @param i
	 */
	private void menuItemAnim(int pos)
	{
		for (int i = 0; i < getChildCount() - 1; i++)
		{

			View childView = getChildAt(i + 1);
			if (i == pos)
			{
				childView.startAnimation(scaleBigAnim(300));
			} else
			{
				childView.startAnimation(scaleSmallAnim(300));
			}
			//����ť���غ����ò��ɵ��������ȡ���۽�
			childView.setClickable(false);
			childView.setFocusable(false);

		}

	}

	/**
	 * û�а����İ�ť������С����ʧ
	 * @param duration
	 * @return
	 */
	private Animation scaleSmallAnim(int duration)
	{

		AnimationSet animationSet = new AnimationSet(true);
		// ��С�������ã��䵽û�У�����ʱ��Ϊduration
		ScaleAnimation scaleAnim = new ScaleAnimation(1.0f, 0.0f, 1.0f, 0.0f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		AlphaAnimation alphaAnim = new AlphaAnimation(1f, 0.0f);
		animationSet.addAnimation(scaleAnim);
		animationSet.addAnimation(alphaAnim);
		animationSet.setDuration(duration);
		animationSet.setFillAfter(true);
		return animationSet;

	}

	/**
	 * �����˵��Ӱ�ť����󣬲���ʧ
	 * 
	 * @param duration
	 * @return
	 */
	private Animation scaleBigAnim(int duration)
	{
		AnimationSet animationSet = new AnimationSet(true);
		// ��󶯻����ã����4��������ʱ��Ϊduration
		ScaleAnimation scaleAnim = new ScaleAnimation(1.0f, 4.0f, 1.0f, 4.0f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		// ͸���ȶ��������е���
		AlphaAnimation alphaAnim = new AlphaAnimation(1f, 0.0f);

		animationSet.addAnimation(scaleAnim);
		animationSet.addAnimation(alphaAnim);

		animationSet.setDuration(duration);
		animationSet.setFillAfter(true);
		return animationSet;

	}

	/**
	 * �˵�״̬�ı�
	 */
	private void changeStatus()
	{
		mCurrentStatus = (mCurrentStatus == Status.CLOSE ? Status.OPEN
				: Status.CLOSE);
	}
	
	/**
	 * ȷ���˵��Ƿ���
	 * @return
	 */
	public boolean isOpen()
	{
		return mCurrentStatus == Status.OPEN;
	}
	
	/**
	 * ����ť����ת����
	 * @param v
	 * @param start   ��ʼ�ĽǶ�
	 * @param end     ����ʱ�ĽǶ�
	 * @param duration   ����ʱ��
	 */
	private void rotateCButton(View v, float start, float end, int duration)
	{

		RotateAnimation anim = new RotateAnimation(start, end,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		anim.setDuration(duration);
		anim.setFillAfter(true);
		v.startAnimation(anim);
	}

}
