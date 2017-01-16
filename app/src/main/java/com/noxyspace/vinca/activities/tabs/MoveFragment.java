//package com.noxyspace.vinca.activities.tabs;
//
//import android.app.Dialog;
//import android.content.DialogInterface;
//import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
//import android.support.v4.app.DialogFragment;
//import android.support.v4.app.ListFragment;
//import android.support.v7.app.AlertDialog;
//import android.view.KeyEvent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.ListAdapter;
//import android.widget.ListView;
//import android.widget.TextView;
//
//import com.noxyspace.vinca.R;
//import com.noxyspace.vinca.objects.ApplicationObject;
//import com.noxyspace.vinca.objects.DirectoryObject;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class MoveFragment extends ListFragment {
//    private List<DirectoryObject> directoryObjects = new ArrayList<>();
//
//    private TextView title;
//    private MoveListAdapter adapter;
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.move_list_fragment, container, false);
//
//
//
//        return view;
//    }
//
//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        adapter = new MoveListAdapter();
//
//        setListAdapter(adapter);
//        getListView().setOnItemClickListener(this);
//    }
//
//
//    public class MoveListAdapter extends BaseAdapter {
//
//        @Override
//        public int getCount() {
//            return directoryObjects.size();
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return position;
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return position;
//        }
//
//        @Override
//        public View getView(int position, View view, ViewGroup parent) {
//            if (view == null) {
//                view = getActivity().getLayoutInflater().inflate(R.layout.directory_object_item, null);
//            }
//
//
//
//            return view;
//        }
//    }
//
//}
