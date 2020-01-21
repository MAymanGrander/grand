package grand.grandlib;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import net.grand.R;

import grand.grandlib.constantsutils.Params;

public class DialogActivity extends ParentActivity {
    private RevealAnimation mRevealAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        mRevealAnimation = new RevealAnimation(ApplicationUtil.getRootView(this), getIntent().getBundleExtra(Params.BUNDLE_PAGE), this);
        addFragment();
    }

    private void addFragment() {
        Fragment fragment = new GrandDialogFragment();
        MovementManager.replaceFragment(this, fragment, "");
    }

    @Override
    public void onBackPressed() {
        mRevealAnimation.unRevealActivity();
    }

}
