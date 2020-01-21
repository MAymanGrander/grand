package grand.grandlib;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import net.grand.R;
import net.grand.R2;
import org.jetbrains.annotations.NotNull;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class GrandDialogFragment extends BaseFragment {
    public GrandDialogFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_grand_dialog, container, false);
        ButterKnife.bind(this, view);
        return view;
    }
    @OnClick({R2.id.btn_call, R2.id.btn_whatsapp, R2.id.view1, R2.id.view2, R2.id.btn_close})
    public void views(View view) {
        if (view.getId() == R.id.btn_call) {
            SettingsManager.makeCall(requireActivity(), getString(R.string.lib_grand_phone));
        } else if (view.getId() == R.id.btn_whatsapp) {
            SettingsManager.whatsAppMsg(requireActivity(), getString(R.string.lib_grand_phone));
        } else {
            requireActivity().onBackPressed();
        }
    }

}
