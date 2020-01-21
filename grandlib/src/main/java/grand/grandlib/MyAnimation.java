package grand.grandlib;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import java.util.Objects;

public abstract class MyAnimation {

    public static void collapse(@NonNull View view, Animator.AnimatorListener animatorListener) {
        int finalHeight = view.getHeight();
        ValueAnimator animator = slideAnimator(finalHeight, 0, view);
        if (animatorListener != null)
            animator.addListener(animatorListener);
        animator.start();
    }

    public static void expand(View view) {
        //set Visible
        view.setVisibility(View.VISIBLE);

        final int widthSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        final int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(widthSpec, heightSpec);

        ValueAnimator mAnimator = slideAnimator(0, view.getMeasuredHeight(), view);
        mAnimator.start();
    }

    private static ValueAnimator slideAnimator(int start, int end, View view) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.addUpdateListener(valueAnimator -> {
            //Update Height
            int value = (Integer) valueAnimator.getAnimatedValue();
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            layoutParams.height = value;
            view.setLayoutParams(layoutParams);
        });
        return animator;
    }


    public static void startAnimation(Integer animRes, View target) {
        Context baseContext = BaseApplication.getInstance().getApplicationContext();
        Animation anim = AnimationUtils.loadAnimation(baseContext, animRes);
        target.startAnimation(anim);
    }


    public static void startMultipleAnimation(Integer[] anims, View target) {
        Context baseContext = BaseApplication.getInstance().getApplicationContext();
        final AnimationSet s = new AnimationSet(true);
        s.setInterpolator(new AccelerateInterpolator());

        for (Integer animRes : anims) {
            Animation anim = AnimationUtils.loadAnimation(baseContext, animRes);
            s.addAnimation(anim);
        }
        target.startAnimation(s);
    }

    public static void starDialogWithAnim(FragmentActivity context, int page, View v) {
        //calculates the center of the View v you are passing
        int revealX = (int) (v.getX() + v.getWidth() / 2);
        int revealY = (int) (v.getY() + v.getHeight() / 2);

        //create an intent, that launches the second activity and pass the x and y coordinates
        Bundle bundle = new Bundle();
        bundle.putInt(RevealAnimation.EXTRA_CIRCULAR_REVEAL_X, revealX);
        bundle.putInt(RevealAnimation.EXTRA_CIRCULAR_REVEAL_Y, revealY);
        //just start the activity as an shared transition, but set the options bundle to null
        MovementManager.startDialogActivity(context, page, bundle);

        //to prevent strange behaviours override the pending transitions
        Objects.requireNonNull(context).overridePendingTransition(0, 0);
    }

    public static void starDialogWithAnim(FragmentActivity context, int page, View v, @NonNull Bundle bundle) {
        //calculates the center of the View v you are passing
        int revealX = (int) (v.getX() + v.getWidth() / 2);
        int revealY = (int) (v.getY() + v.getHeight() / 2);

        //create an intent, that launches the second activity and pass the x and y coordinates
        bundle.putInt(RevealAnimation.EXTRA_CIRCULAR_REVEAL_X, revealX);
        bundle.putInt(RevealAnimation.EXTRA_CIRCULAR_REVEAL_Y, revealY);
        //just start the activity as an shared transition, but set the options bundle to null
        MovementManager.startDialogActivity(context, page, bundle);

        //to prevent strange behaviours override the pending transitions
        Objects.requireNonNull(context).overridePendingTransition(0, 0);
    }

}
