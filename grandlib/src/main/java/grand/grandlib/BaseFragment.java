package grand.grandlib;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import org.jetbrains.annotations.NotNull;

import timber.log.Timber;

public class BaseFragment extends Fragment {
    private FragmentActivity mContext;

    @Override
    public void onDestroyView() {
        mContext = null;
        super.onDestroyView();
    }

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        try {
            mContext = (FragmentActivity) context;
        } catch (Exception e) {
            Timber.e(e);
        }
    }

    @NonNull
    @Override
    public Context getContext() {
        return mContext == null ? requireActivity() : mContext;
    }
}
