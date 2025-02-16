package app.ij.mlwithtensorflowlite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;

public class ActivityResult extends AppCompatActivity {

    FrameLayout frameLayout;
    TabLayout tabLayout;
    MushroomData[] mushroomDataArray; // Declare mushroomDataArray here to make it accessible throughout the class


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        ImageView backBtn = findViewById(R.id.backBtn);
        Intent intent = getIntent();
        Parcelable[] parcelables = intent.getParcelableArrayExtra("mushroomData");
        if (parcelables != null) {
            mushroomDataArray = new MushroomData[parcelables.length];
            for (int i = 0; i < parcelables.length; i++) {
                mushroomDataArray[i] = (MushroomData) parcelables[i];
            }
            // Now you have properly cast MushroomData[] array
        } else {
            // Handle case where the parcelable array is null
        }
        byte[] image = intent.getByteArrayExtra("image");

        frameLayout = findViewById(R.id.frameLayout);
        tabLayout = findViewById(R.id.tabLayout);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Bundle bundle1 = new Bundle();

        bundle1.putParcelable("mushroomData", mushroomDataArray[0]);
        bundle1.putByteArray("image", image);
        Fragment fragment1 = null;
        fragment1 = new details_mushroom();
        fragment1.setArguments(bundle1);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, fragment1)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();



        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment fragment = null;
                Bundle bundle = new Bundle(); // Create a new Bundle instance for each tab

                switch (tab.getPosition()) {
                    case 0:
                        bundle.putParcelable("mushroomData", mushroomDataArray[0]);
                        bundle.putByteArray("image", image);
                        fragment = new details_mushroom();
                        break;
                    case 1:
                        bundle.putParcelable("mushroomData", mushroomDataArray[1]);
                        bundle.putByteArray("image", image);
                        fragment = new details_mushroom();
                        break;
                    case 2:
                        bundle.putParcelable("mushroomData", mushroomDataArray[2]);
                        bundle.putByteArray("image", image);
                        fragment = new details_mushroom();
                        break;
                }

                if (fragment != null) {
                    fragment.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frameLayout, fragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .commit();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }
}
