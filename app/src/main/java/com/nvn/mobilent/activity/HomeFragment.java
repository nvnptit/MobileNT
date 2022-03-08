package com.nvn.mobilent.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ViewFlipper;

import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.nvn.mobilent.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerView;
    NavigationView navigationView;
    ListView listView;
    DrawerLayout drawerLayout;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CartFragment newInstance(String param1, String param2) {
        CartFragment fragment = new CartFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private void setActionViewLipper(View view) {
        ArrayList<String> slidePictures = new ArrayList<>();
        slidePictures.add("https://cdn.tgdd.vn/2022/02/banner/830-300-830x300-20.png");
        slidePictures.add("https://cdn.tgdd.vn/2022/02/banner/reno6z-830-300-830x300.png");
        slidePictures.add("https://cdn.tgdd.vn/2022/03/banner/830-300-830x300-1.png");
        slidePictures.add("https://cdn.tgdd.vn/2022/02/banner/830-300-830x300-19.png");
        for (int i = 0; i < slidePictures.size(); i++) {
            ImageView imageView = new ImageView(view.getContext().getApplicationContext());
            Picasso.get().load(slidePictures.get(i)).into(imageView); // Ep anh vao imageview
            imageView.setScaleType(ImageView.ScaleType.FIT_XY); //Fix vua khung anh
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(4000); // Set time delay slide
        viewFlipper.setAutoStart(true); // Auto

        Animation animation_SlideIn = AnimationUtils.loadAnimation(view.getContext().getApplicationContext(), R.anim.slide_in_right);
        Animation animation_SlideOut = AnimationUtils.loadAnimation(view.getContext().getApplicationContext(), R.anim.slide_out_right);

        viewFlipper.setInAnimation(animation_SlideIn);
        viewFlipper.setOutAnimation(animation_SlideOut);
    }

    private void setControl(View view) {
        toolbar = view.findViewById(R.id.toolbar);
        viewFlipper = view.findViewById(R.id.viewlipper);
        recyclerView = view.findViewById(R.id.recyclerview);
        navigationView = view.findViewById(R.id.navigationview);
        listView = view.findViewById(R.id.listViewHome);
        drawerLayout = view.findViewById(R.id.drawerlayout);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        setControl(view);
        setActionViewLipper(view);
        // Inflate the layout for this fragment
        return view;
    }

}