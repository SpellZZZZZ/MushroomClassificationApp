package app.ij.mlwithtensorflowlite;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link details_mushroom#newInstance} factory method to
 * create an instance of this fragment.
 */
public class details_mushroom extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private MushroomData mushroomData;
    private byte[] image;

    public details_mushroom() {
        // Required empty public constructor
    }

    public static details_mushroom newInstance(MushroomData mushroomData) {
        details_mushroom fragment = new details_mushroom();
        Bundle args = new Bundle();
        args.putParcelable("mushroomData", (Parcelable) mushroomData);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public void initImages(ImageView imageView1, ImageView imageView2, ImageView imageView3, ImageView imageView4){
        switch (mushroomData.getName()) {
            case "Amanita daucipes":
                imageView1.setImageResource(R.drawable.a__1_);
                imageView2.setImageResource(R.drawable.a__2_);
                imageView3.setImageResource(R.drawable.a__3_);
                imageView4.setImageResource(R.drawable.a__4_);
                break;
            case "Cortinarius anthracinus":
                imageView1.setImageResource(R.drawable.c__1_);
                imageView2.setImageResource(R.drawable.c__2_);
                imageView3.setImageResource(R.drawable.c__3_);
                imageView4.setImageResource(R.drawable.c__4_);
                break;
            case "Entoloma abortivum":
                imageView1.setImageResource(R.drawable.e__1_);
                imageView2.setImageResource(R.drawable.e__2_);
                imageView3.setImageResource(R.drawable.e__3_);
                imageView4.setImageResource(R.drawable.e__4_);
                break;
            case "Lactarius deliciosus":
                imageView1.setImageResource(R.drawable.l__1_);
                imageView2.setImageResource(R.drawable.l__2_);
                imageView3.setImageResource(R.drawable.l__3_);
                imageView4.setImageResource(R.drawable.l__4_);
                break;
            case "Agaricus bisporus":
                imageView1.setImageResource(R.drawable.ab__1_);
                imageView2.setImageResource(R.drawable.ab__2_);
                imageView3.setImageResource(R.drawable.ab__3_);
                imageView4.setImageResource(R.drawable.ab__4_);
                break;
            case "Agrocybe aegerita":
                imageView1.setImageResource(R.drawable.aa__1_);
                imageView2.setImageResource(R.drawable.aa__2_);
                imageView3.setImageResource(R.drawable.aa__3_);
                imageView4.setImageResource(R.drawable.aa__4_);
                break;
            case "Bolete":
                imageView1.setImageResource(R.drawable.b__1_);
                imageView2.setImageResource(R.drawable.b__2_);
                imageView3.setImageResource(R.drawable.b__3_);
                imageView4.setImageResource(R.drawable.b__4_);
                break;
            case "Coprinus comatus":
                imageView1.setImageResource(R.drawable.cc__1_);
                imageView2.setImageResource(R.drawable.cc__2_);
                imageView3.setImageResource(R.drawable.cc__3_);
                imageView4.setImageResource(R.drawable.cc__4_);
                break;
            case "Dictyophora":
                imageView1.setImageResource(R.drawable.d__1_);
                imageView2.setImageResource(R.drawable.d__2_);
                imageView3.setImageResource(R.drawable.d__3_);
                imageView4.setImageResource(R.drawable.d__4_);
                break;
            case "Enoki mushroom":
                imageView1.setImageResource(R.drawable.enoki__1_);
                imageView2.setImageResource(R.drawable.enoki__2_);
                imageView3.setImageResource(R.drawable.enoki__3_);
                imageView4.setImageResource(R.drawable.enoki__4_);
                break;
            case "Hen-of-the-woods":
                imageView1.setImageResource(R.drawable.hotw__1_);
                imageView2.setImageResource(R.drawable.hotw__2_);
                imageView3.setImageResource(R.drawable.hotw__3_);
                imageView4.setImageResource(R.drawable.hotw__4_);
                break;
            case "Hericium":
                imageView1.setImageResource(R.drawable.h__1_);
                imageView2.setImageResource(R.drawable.h__2_);
                imageView3.setImageResource(R.drawable.h__3_);
                imageView4.setImageResource(R.drawable.h__4_);
                break;
            case "Hypsizigus marmoreus":
                imageView1.setImageResource(R.drawable.hm__1_);
                imageView2.setImageResource(R.drawable.hm__2_);
                imageView3.setImageResource(R.drawable.hm__3_);
                imageView4.setImageResource(R.drawable.hm__4_);
                break;
            case "Matsutake":
                imageView1.setImageResource(R.drawable.mat__1_);
                imageView2.setImageResource(R.drawable.mat__2_);
                imageView3.setImageResource(R.drawable.mat__3_);
                imageView4.setImageResource(R.drawable.mat__4_);
                break;
            case "Morel":
                imageView1.setImageResource(R.drawable.m__1_);
                imageView2.setImageResource(R.drawable.m__2_);
                imageView3.setImageResource(R.drawable.m__3_);
                imageView4.setImageResource(R.drawable.m__4_);
                break;
            case "Nameko":
                imageView1.setImageResource(R.drawable.n__1_);
                imageView2.setImageResource(R.drawable.n__2_);
                imageView3.setImageResource(R.drawable.n__3_);
                imageView4.setImageResource(R.drawable.n__4_);
                break;
            case "Pleurotus eryngii":
                imageView1.setImageResource(R.drawable.pe__1_);
                imageView2.setImageResource(R.drawable.pe__2_);
                imageView3.setImageResource(R.drawable.pe__3_);
                imageView4.setImageResource(R.drawable.pe__4_);
                break;
            case "Russula virescens":
                imageView1.setImageResource(R.drawable.rv__1_);
                imageView2.setImageResource(R.drawable.rv__2_);
                imageView3.setImageResource(R.drawable.rv__3_);
                imageView4.setImageResource(R.drawable.rv__4_);
                break;
            case "Shiitake":
                imageView1.setImageResource(R.drawable.s__1_);
                imageView2.setImageResource(R.drawable.s__2_);
                imageView3.setImageResource(R.drawable.s__3_);
                imageView4.setImageResource(R.drawable.s__4_);
                break;
            case "Tricholoma flavovirens":
                imageView1.setImageResource(R.drawable.tf__1_);
                imageView2.setImageResource(R.drawable.tf__2_);
                imageView3.setImageResource(R.drawable.tf__3_);
                imageView4.setImageResource(R.drawable.tf__4_);
                break;


        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details_mushroom, container, false);

        if(getArguments() != null){
            mushroomData = getArguments().getParcelable("mushroomData");
            image = getArguments().getByteArray("image");
        }

        ImageView uploadedImage = view.findViewById(R.id.uploadedImage);
        TextView name = view.findViewById(R.id.mushroomNameTextView);
        TextView otherName = view.findViewById(R.id.otherNames);
        ImageView poisonousImage = view.findViewById(R.id.poisonousIcon);
        TextView poisonousText = view.findViewById(R.id.poisonousStatement);
        TextView description = view.findViewById(R.id.description);
        TextView height = view.findViewById(R.id.heightValue);
        TextView diameter = view.findViewById(R.id.diameterValue);
        TextView colours = view.findViewById(R.id.coloursValue);
        TextView habit = view.findViewById(R.id.habitValue);
        TextView nearbyTrees = view.findViewById(R.id.nearbyTreesValue);
        TextView sporePrint = view.findViewById(R.id.sporePrintValue);
        TextView poisonous = view.findViewById(R.id.poisonousValue);

        ImageView imageView1 = view.findViewById(R.id.imageView5);
        ImageView imageView2 = view.findViewById(R.id.imageView2);
        ImageView imageView3 = view.findViewById(R.id.imageView3);
        ImageView imageView4 = view.findViewById(R.id.imageView4);

        initImages(imageView1, imageView2, imageView3, imageView4);

        name.setText(mushroomData.getName());
        otherName.setText(mushroomData.getOtherName());
        description.setText(mushroomData.getDescription());
        height.setText(mushroomData.getHeight());
        diameter.setText(mushroomData.getDiameter());
        colours.setText(mushroomData.getColours());
        habit.setText(mushroomData.getHabit());
        nearbyTrees.setText(mushroomData.getNearbyTrees());
        sporePrint.setText(mushroomData.getSporePrint());
        poisonous.setText(mushroomData.getPoisonous());
        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
        bitmap = Bitmap.createScaledBitmap(bitmap, 500, 500, true);
        uploadedImage.setImageBitmap(bitmap);
        uploadedImage.setVisibility(View.VISIBLE);

        if(mushroomData.getPoisonous().equals("Poisonous")){
            poisonousImage.setImageResource(R.drawable.poisonicon);
            poisonousText.setText("This mushroom is poisonous");
            poisonousText.setTextColor(getResources().getColor(R.color.red));
        } else {
            poisonousImage.setImageResource(R.drawable.edibleicon);
            poisonousText.setText("This mushroom is edible");
            poisonousText.setTextColor(getResources().getColor(R.color.green));
        }

        // Inflate the layout for this fragment
        return view;
    }
}