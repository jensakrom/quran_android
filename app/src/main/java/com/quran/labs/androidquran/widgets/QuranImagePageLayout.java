package com.quran.labs.androidquran.widgets;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.quran.labs.androidquran.ui.helpers.AyahSelectedListener;
import com.quran.labs.androidquran.ui.util.PageController;

public class QuranImagePageLayout extends QuranPageLayout {
  private HighlightingImageView imageView;

  public QuranImagePageLayout(Context context) {
    super(context);
  }

  @Override
  protected View generateContentView(Context context, boolean isLandscape) {
    imageView = new HighlightingImageView(context);
    imageView.setAdjustViewBounds(true);
    imageView.setIsScrollable(isLandscape && shouldWrapWithScrollView());
    return imageView;
  }

  @Override
  protected void setContentNightMode(boolean nightMode, int textBrightness) {
    imageView.setNightMode(nightMode, textBrightness);
  }

  public HighlightingImageView getImageView() {
    return imageView;
  }

  @Override
  public void setPageController(PageController controller, int pageNumber) {
    super.setPageController(controller, pageNumber);
    final GestureDetector gestureDetector = new GestureDetector(context,
        new PageGestureDetector());
    OnTouchListener gestureListener = new OnTouchListener() {
      @Override
      public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
      }
    };
    imageView.setOnTouchListener(gestureListener);
    imageView.setClickable(true);
    imageView.setLongClickable(true);
  }

  private class PageGestureDetector extends
      GestureDetector.SimpleOnGestureListener {

    @Override
    public boolean onDown(MotionEvent e) {
      return true;
    }

    @Override
    public boolean onSingleTapUp(MotionEvent event) {
      return pageController.handleTouchEvent(event,
          AyahSelectedListener.EventType.SINGLE_TAP, pageNumber);
    }

    @Override
    public boolean onDoubleTap(MotionEvent event) {
      return pageController.handleTouchEvent(event,
          AyahSelectedListener.EventType.DOUBLE_TAP, pageNumber);
    }

    @Override
    public void onLongPress(MotionEvent event) {
      pageController.handleTouchEvent(event,
          AyahSelectedListener.EventType.LONG_PRESS, pageNumber);
    }
  }
}
