package fragment;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import bit.eagzzycsl.smartable2.EditActivity;
import bit.eagzzycsl.smartable2.EnumExtra;
import bit.eagzzycsl.smartable2.ExtraFiled;
import bit.eagzzycsl.smartable2.R;

public class Fragment_main_smart extends Fragment {
    private View myView;
    private FloatingActionButton fab_add;
    private Fragment_main_smart_classify fragment_main_smart_classify;
    private Fragment_main_smart_serialize fragment_main_smart_serialize;

    public Fragment_main_smart() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.fragment_main_smart, container, false);
        myFindView();
        myCreate();
        mySetView();
        return myView;
    }

    private void myFindView() {
        fab_add = (FloatingActionButton) myView.findViewById(R.id.fab);
        fragment_main_smart_classify = (Fragment_main_smart_classify) getChildFragmentManager().findFragmentById(R.id.fragment_main_smart_classify);
        fragment_main_smart_serialize = (Fragment_main_smart_serialize) getChildFragmentManager().findFragmentById(R.id.fragment_main_smart_serialize);
    }

    private void mySetView() {
        if (true) {
            getChildFragmentManager().beginTransaction().hide(fragment_main_smart_serialize).commit();
        } else {
            getChildFragmentManager().beginTransaction().hide(fragment_main_smart_classify).commit();
        }
        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditActivity.class);
                intent.putExtra(EnumExtra.getName(), EnumExtra.addEntryWithEntryType);
                intent.putExtra(ExtraFiled.entryEnum, fragment_main_smart_classify.getCurrentPageEntry());
                startActivity(intent);
            }
        });

    }

    private void myCreate() {
    }

    public int switchView() {
        /*0表示当前是分类，1表示当前是序列化，用于设置图标*/
        if (fragment_main_smart_serialize.isHidden()) {

            getChildFragmentManager().beginTransaction().hide(fragment_main_smart_classify).show(fragment_main_smart_serialize).commit();
            return 1;
        } else {
            getChildFragmentManager().beginTransaction().hide(fragment_main_smart_serialize).show(fragment_main_smart_classify).commit();
            return 0;
        }
    }


}
