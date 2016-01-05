package fengyu.cn.library.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;

import fengyu.cn.library.R;


public class FysPullToRefreshHeaderView extends View {
	private PorterDuffXfermode porterDuffXfermode;// Xfermode
	private Paint paint;// ����
	private Bitmap bitmap;// ԴͼƬ
	private int width, height;// �ؼ����
	private Path path;// ��������������Ҫ�õ�
	private Canvas mCanvas;// �ڸû����ϻ���Ŀ��ͼƬ
	private Bitmap bg;// Ŀ��ͼƬ

	private float controlX, controlY;// ���������߿��Ƶ㣬ʹ�����ױ������������ߣ���Ҫ�������Ƶ㣬�������Ƶ㶼�ڸñ�������������
	private float waveY;// �����ĸ߶�

	private boolean isIncrease;// ���ڿ��ƿ��Ƶ�ˮƽ�ƶ�

	private boolean isReflesh = false;// �Ƿ�ˢ�²��������Ч����Ĭ��Ϊtrue

	/**
	 * @return �Ƿ�ˢ��
	 */
	public boolean isReflesh() {
		return isReflesh;
	}

	/**
	 * �ṩ�ӿ�����ˢ��
	 * 
	 * @param isReflesh
	 */
	public void setReflesh(boolean isReflesh) {
		this.isReflesh = isReflesh;
		// �ػ�
		postInvalidate();
	}

	/**
	 * @param context
	 */
	public FysPullToRefreshHeaderView(Context context) {
		this(context, null);
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public FysPullToRefreshHeaderView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	/**
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public FysPullToRefreshHeaderView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	/**
	 * ��ʼ������
	 */
	private void init() {
		// ��ʼ������
		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setDither(true);
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(Color.parseColor("#ffc9394a"));
		// �����Դ�ļ�
		bitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.fys_pulltorefresh_header_img);
		// ���ÿ��ΪͼƬ�Ŀ��
		width = bitmap.getWidth();
		height = bitmap.getHeight();

		// ��ʼ״ֵ̬
		waveY = 7 / 8F * height;
		controlY = 17 / 16F * height;

		// ��ʼ��Xfermode
		porterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
		// ��ʼ��path
		path = new Path();
		// ��ʼ������
		mCanvas = new Canvas();
		// ����bitmap
		bg = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		// ���½���bitmapע�뻭��
		mCanvas.setBitmap(bg);

	}

	@Override
	protected void onDraw(Canvas canvas) {
		// ��Ŀ��ͼ������bg��
		drawTargetBitmap();
		// ��Ŀ��ͼ�����ڵ�ǰ�����ϣ����Ϊ��߾࣬�ϱ߾�Ľ���
		canvas.drawBitmap(bg, getPaddingLeft(), getPaddingTop(), null);
		if (isReflesh) {
			// �ػ棬ʹ��boolean����isReflesh���п��ƣ��������ṩ���ʵĽӿ�,Ĭ��Ϊtrue��ˢ��
			invalidate();
		}
	}

	private void drawTargetBitmap() {
		// ����path
		path.reset();
		// ��������
		bg.eraseColor(Color.parseColor("#00ffffff"));

		// �����Ƶ��x������ڻ�����յ�x����ʱ���ı�ʶֵ
		if (controlX >= width + 1 / 2 * width) {
			isIncrease = false;
		}
		// �����Ƶ��x����С�ڻ�������x����ʱ���ı�ʶֵ
		else if (controlX <= -1 / 2 * width) {
			isIncrease = true;
		}

		// ���ݱ�ʶֵ�жϵ�ǰ�Ŀ��Ƶ�x�����Ǹüӻ��Ǽ�
		controlX = isIncrease ? controlX + 10 : controlX - 10;
		if (controlY >= 0) {
			// ��������
			controlY -= 1;
			waveY -= 1;
		} else {
			// ����������λ��
			waveY = 7 / 8F * height;
			controlY = 17 / 16F * height;
		}

		// ���������ߵ�����
		path.moveTo(0, waveY);
		// �������Ƶ�ͨ��controlX��controlY����
		path.cubicTo(controlX / 2, waveY - (controlY - waveY),
				(controlX + width) / 2, controlY, width, waveY);
		// �����±߽�պ�
		path.lineTo(width, height);
		path.lineTo(0, height);
		// ���бպ�
		path.close();

		// ���ϻ����������ߴ���ο��԰��粩��
		// http://blog.csdn.net/aigestudio/article/details/41960507

		mCanvas.drawBitmap(bitmap, 0, 0, paint);// ��Ľ����logo
		paint.setXfermode(porterDuffXfermode);// ����Xfermode
		mCanvas.drawPath(path, paint);// �����ױ���������
		paint.setXfermode(null);// ����Xfermode
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// ��ÿ�߲���ģʽ�ʹ�С
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);
		// ����������
		int width, height;

		if (widthMode == MeasureSpec.EXACTLY) {
			// ���
			width = widthSize;
		} else {
			// ��ȼ������ڱ߾�
			width = this.width + getPaddingLeft() + getPaddingRight();
			;
			if (widthMode == MeasureSpec.AT_MOST) {
				// ȡС���Ǹ�
				width = Math.min(width, widthSize);
			}

		}

		if (heightMode == MeasureSpec.EXACTLY) {
			// �߶�
			height = heightSize;
		} else {
			// �߶ȼ������ڱ߾�
			height = this.height + getPaddingTop() + getPaddingBottom();
			;
			if (heightMode == MeasureSpec.AT_MOST) {
				// ȡС���Ǹ�
				height = Math.min(height, heightSize);
			}

		}
		// ���ø߶ȿ��Ϊlogo��Ⱥ͸߶�,ʵ�ʿ�����Ӧ���ж�MeasureSpec��ģʽ�����ж�Ӧ���߼�����,�������˼򵥵��жϲ���
		setMeasuredDimension(width, height);

	}

}