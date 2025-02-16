/*
 * Created by ishaanjav
 * github.com/ishaanjav
 */

package app.ij.mlwithtensorflowlite;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;


import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import app.ij.mlwithtensorflowlite.ml.MobileNet;


public class MainActivity extends AppCompatActivity {

    Button camera, gallery;

    MushroomData[] mushroomDetails = new MushroomData[20];
    private ALoadingDialog aLoadingDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        camera = findViewById(R.id.button);
        gallery = findViewById(R.id.button2);
        aLoadingDialog = new ALoadingDialog(this);
        initMushroomDetails();

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, 3);
                } else {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, 100);
                }
            }
        });
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(cameraIntent, 1);
            }
        });
    }

    public void classifyImage(@NonNull Bitmap image, Bitmap ori_image) {
        aLoadingDialog.show();

        try {
            MobileNet model = MobileNet.newInstance(getApplicationContext());

            // Create a TensorBuffer object with data type FLOAT32
            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 224, 224, 3}, DataType.FLOAT32);
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4*224 * 224 * 3);
            byteBuffer.order(ByteOrder.nativeOrder());



            int[] intValues = new int[224 * 224];
            image.getPixels(intValues, 0, image.getWidth(), 0, 0, image.getWidth(), image.getHeight());
            int pixel = 0;
            for (int i = 0; i < 224; i ++) {
                for(int j = 0; j < 224; j++) {
                    int val = intValues[pixel++];
                    byteBuffer.putFloat(((val >> 16) & 0xFF) / 255.0f);
                    byteBuffer.putFloat(((val >> 8) & 0xFF) / 255.0f);
                    byteBuffer.putFloat((val & 0xFF) / 255.0f);
                }
            }

            inputFeature0.loadBuffer(byteBuffer);


            // Run model inference and get result
            MobileNet.Outputs outputs = model.process(inputFeature0);
            TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

            // Find the index of the class with the highest confidence
            float[] confidences = outputFeature0.getFloatArray();
            int[] maxPos = new int[3]; // Array to store the indices of the highest confidences
            float[] maxConfidence = new float[]{Float.MIN_VALUE, Float.MIN_VALUE, Float.MIN_VALUE}; // Array to store the highest confidences

// Loop through the confidences array to find the highest confidences
            for (int i = 0; i < confidences.length; i++) {
                // Check if the current confidence is higher than the lowest stored confidence
                if (confidences[i] > maxConfidence[0]) {
                    maxConfidence[2] = maxConfidence[1];
                    maxConfidence[1] = maxConfidence[0];
                    maxConfidence[0] = confidences[i];
                    maxPos[2] = maxPos[1];
                    maxPos[1] = maxPos[0];
                    maxPos[0] = i;
                } else if (confidences[i] > maxConfidence[1]) {
                    maxConfidence[2] = maxConfidence[1];
                    maxConfidence[1] = confidences[i];
                    maxPos[2] = maxPos[1];
                    maxPos[1] = i;
                } else if (confidences[i] > maxConfidence[2]) {
                    maxConfidence[2] = confidences[i];
                    maxPos[2] = i;
                }
            }


            // Release model resources
            model.close();
            MushroomData[] predictedMushrooms = new MushroomData[3];
            ByteArrayOutputStream bs = new ByteArrayOutputStream();
            ori_image.compress(Bitmap.CompressFormat.PNG, 50, bs);
            for (int i = 0; i < 3; i++) {
                predictedMushrooms[i] = mushroomDetails[maxPos[i]];
            }
            aLoadingDialog.cancel();
            toResult(predictedMushrooms, bs.toByteArray());


        } catch (IOException e) {
            // Handle the exception
        }
    }

    public void toResult(MushroomData[] mushroomData, byte[]image) {
        Intent intent = new Intent(this, ActivityResult.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArray("mushroomData", mushroomData);
        bundle.putByteArray("image", image);
        intent.putExtras(bundle);
        startActivity(intent);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 3) {
                Bitmap image = (Bitmap) data.getExtras().get("data");
                Bitmap ori_image = image;
                int dimension = Math.min(image.getWidth(), image.getHeight());
                image = ThumbnailUtils.extractThumbnail(image, dimension, dimension);

                image = Bitmap.createScaledBitmap(image, 224, 224, false); // Resize to 224x224
                classifyImage(image, ori_image);
            } else {
                Uri dat = data.getData();
                Bitmap image = null;
                Bitmap ori_image = null;
                try {
                    image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), dat);
                    ori_image = image;
                } catch (IOException e) {
                    e.printStackTrace();
                }

                image = Bitmap.createScaledBitmap(image, 224, 224, false); // Resize to 224x224
                classifyImage(image, ori_image);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

public void initMushroomDetails() {
        mushroomDetails[0] = new MushroomData("Amanita muscaria",
                "Also known as: Fly amanita\nScientific name: Amanita muscaria",
                "In Northern Asia and Europe, fly agaric grows under trees near the winter solstice and is collected for ritual use tied to the season. Its characteristic shape and coloring are still ubiquitous in many European fairy tale illustrations and Christmas traditions. It is highly toxic.",
                "5 - 20 cm",
                "1 - 4 cm",
                "Red, White",
                "Mycorrhizal",
                "Abies lasiocarpa, Populus tremuloides, Populus grandidentata, Picea engelmannii, Pinus strobus, Pinus radiata, Pinus rigida, Pinus resinosa",
                "White",
                "Poisonous"
                );
        mushroomDetails[1] = new MushroomData("Cortinarius anthracinus",
                "Also known as: Black webcap\nScientific name: Cortinarius anthracinus",
                "Cortinarius anthracinus is a species of fungus in the family Cortinariaceae. It is found in Europe and North America. The fruit bodies of the fungus have convex to bell-shaped caps up to 10 cm (3.9 in) in diameter, with a dark brown to blackish center and a lighter margin. The gills are initially covered by a cobweb-like veil (cortina) that leaves a distinctive ring on the stem as it collapses.",
                "3.5 -5 cm",
                "1-3 cm",
                "Brown, Black, Purple",
                "Mycorrhizal",
                "Pinus, Quercus, Corylus, Fagus",
                "Brown",
                "Poisonous"
        );
        mushroomDetails[2] = new MushroomData("Entoloma abortivum",
                "Also known as: Shrimp Russula, Shrimp mushroom\nScientific name: Entoloma abortivum",
                "Entoloma abortivum is a species of fungus in the family Entolomataceae. It produces both typical agaric fruit bodies and a distinctive form found growing on the underground nests of the wood ant. The fruit bodies are pink to reddish-brown with a bell-shaped cap up to 10 cm (3.9 in) in diameter. The gills are pink, and the stem is white with a pinkish base.",
                "10 cm",
                "5 cm",
                "Pink, Red, Brown",
                "Mycorrhizal",
                "Pinus, Quercus, Corylus, Fagus",
                "Pink",
                "Poisonous"
        );
        mushroomDetails[3] = new MushroomData("Lactarius deliciosus",
                "Also known as: Saffron milk cap, Red pine mushroom\nScientific name: Lactarius deliciosus",
                "Lactarius deliciosus, commonly known as the saffron milk cap and red pine mushroom, is one of the best known members of the large milk-cap genus, Lactarius. It is found in Europe and North America, where it grows in coniferous forests. The orange-capped fruit bodies appear in autumn in coniferous and mixed forests, as well as heathlands in late summer and autumn.",
                "8 cm",
                "13 cm",
                "Orange, Red, Yellow",
                "Mycorrhizal",
                "Abies lasiocarpa, Picea engelmannii, Pinus contorta",
                "Creamy pinkish buff",
                "Poisonous"
        );

        mushroomDetails[4] = new MushroomData("Agaricus bisporus",
                "Also known as: Common mushroom, Button mushroom, White mushroom\nScientific name: Agaricus bisporus",
                "Agaricus bisporus is an edible basidiomycete mushroom native to grasslands in Europe and North America. It has two color states while immature—white and brown—both of which have various names. When mature, it is known as portobello mushroom, often shortened to just portobello.",
                "8 cm",
                "13 cm",
                "White, Brown",
                "Saprotrophic",
                "Picea abies",
                "Brown",
                "Edible"
        );

        mushroomDetails[5] = new MushroomData("Agrocybe aegerita",
                "Also known as: Poplar mushroom, Velvet pioppino\nScientific name: Agrocybe aegerita",
                "Agrocybe aegerita is a species of mushroom in the family Strophariaceae. It is commonly known as the poplar mushroom, chestnut mushroom, or velvet pioppino. The fungus was first described scientifically in 1774 by Italian mycologist Giovanni Antonio Scopoli.",
                "6 cm",
                "3 - 10 cm",
                "Brown, Yellow, White",
                "Saprotrophic",
                "On wood",
                "Brown",
                "Edible"
        );

        mushroomDetails[6] = new MushroomData("Bolete",
                "Also known as: Boletus\nScientific name: Boletus",
                "Boletus is a genus of mushroom-producing fungi, comprising over 100 species. The genus Boletus was originally broadly defined and described by Elias Magnus Fries in 1821, essentially containing all fungi with pores. Since then, other genera have been defined gradually, such as Tylopilus by Petter Adolf Karsten in 1881, and old names such as Leccinum have been resurrected or redefined.",
                "10 cm",
                "8 - 16 cm",
                "Brown, Yellow , Red, Purple",
                "Mycorrhizal",
                "Pinus, Quercus, Pseudotsuga",
                "Brown",
                "Poisonous"
        );

        mushroomDetails[7] = new MushroomData("Coprinus comatus",
                "Also known as: Shaggy ink cap, Lawyer's wig, Shaggy mane\nScientific name: Coprinus comatus",
                "Coprinus comatus, the shaggy ink cap, lawyer's wig, or shaggy mane, is a common fungus often seen growing on lawns, along gravel roads and waste areas. The young fruit bodies first appear as white cylinders emerging from the ground, then the bell-shaped caps open out. The caps are white, and covered with scales—this is the origin of the common names of the fungus.",
                "10 cm",
                "5 cm",
                "White",
                "Saprotrophic",
                "Pinus, Quercus, Corylus, Fagus",
                "Black",
                "Poisonous"
        );

        mushroomDetails[8] = new MushroomData("Dictyophora",
                "Also known as: Dictyophora\nScientific name: Dictyophora",
                "Dictyophora is a genus of fungi in the family Phallaceae. The genus was circumscribed by French mycologist Jean Baptiste François Pierre Bulliard in 1791. The genus is widespread, and contains about 20 species.",
                "20 cm",
                "8 cm",
                "Brown, Black, White",
                "Saprotrophic",
                "Pinus, Quercus, Corylus, Fagus",
                "Black",
                "Poisonous"
        );

        mushroomDetails[9] = new MushroomData("Enoki mushroom",
                "Also known as: Enokitake, Enokidake, Enoki-dake, Golden needle mushroom\nScientific name: Flammulina velutipes",
                "Flammulina velutipes, known as enoki in North America, enokitake in Japan, and golden needle mushroom in China, is a species of edible mushroom in the family Physalacriaceae. It is well known for its role in Japanese cuisine, where it is also known as enokitake.",
                "10 cm",
                "2 cm",
                "White",
                "Saprotrophic",
                "On wood",
                "White",
                "Edible"
        );

        mushroomDetails[10] = new MushroomData("Hen-of-the-woods",
                "Also known as: Maitake, Ram's head, Sheep's head\nScientific name: Grifola frondosa",
                "Grifola frondosa is a polypore mushroom that grows in clusters at the base of trees, particularly oaks. The mushroom is commonly known among English speakers as hen of the woods, hen-of-the-woods, ram's head, and sheep's head. It is typically found in late summer to early autumn.",
                "61 cm",
                "61 cm",
                "Brown, Gray",
                "Saprotrophic",
                "On wood",
                "White",
                "Edible"
        );

        mushroomDetails[11] = new MushroomData("Hericium",
                "Also known as: Lion's mane, Monkey head, Bearded tooth\nScientific name: Hericium erinaceus",
                "Hericium erinaceus is an edible and medicinal mushroom belonging to the tooth fungus group. Native to North America, Europe and Asia it can be identified by its long spines (greater than 1 cm length), its appearance on hardwoods, and its tendency to grow a single clump of dangling spines.",
                "20 cm",
                "30 cm",
                "Yellow, White",
                "Saprotrophic",
                "Quercus agrifolia",
                "White",
                "Poisonous"
        );

        mushroomDetails[12] = new MushroomData("Hypsizigus marmoreus",
                "Also known as: Bunashimeji, White beech mushroom\nScientific name: Hypsizigus marmoreus",
                "Hypsizigus marmoreus, commonly known as Bunashimeji, is an edible mushroom native to East Asia. It is cultivated locally in temperate climates around the world. The mushroom is rich in umami tasting compounds such as guanylic and glutamic acid.",
                "20 cm",
                "8 cm",
                "Brown, White",
                "Saprotrophic",
                "On wood",
                "White",
                "Edible"
        );

        mushroomDetails[13] = new MushroomData("Matsutake",
                "Also known as: Pine mushroom\nScientific name: Tricholoma matsutake",
                "Tricholoma matsutake, the matsutake mushroom, is a highly sought-after mycorrhizal mushroom that grows in Asia, Europe, and North America. It is prized in Japanese, Korean, and Chinese cuisine for its distinct spicy-aromatic odor. The fungus grows in Asia, Europe, and North America, where it fruits in late summer and autumn.",
                "15 cm",
                "20 cm",
                "Brown",
                "Mycorrhizal",
                "Pinus, Picea, Abies, Quercus, Lithocarpus, Castanopsis",
                "White",
                "Poisonous"
        );

        mushroomDetails[14] = new MushroomData("Morel",
                "Also known as: Morchella\nScientific name: Morchella",
                "Morchella, the true morels, is a genus of edible sac fungi closely related to anatomically simpler cup fungi in the order Pezizales. These distinctive fungi have a honeycomb appearance, due to the network of ridges with pits composing their cap. Morels are prized by gourmet cooks, particularly in French cuisine.",
                "5 - 18 cm",
                "2 - 5 cm",
                "Brown, Yellow, Bronze, White",
                "Saprotrophic",
                "Pinus, Quercus, Corylus, Fagus",
                "White to cream",
                "Edible"
        );

        mushroomDetails[15] = new MushroomData("Nameko",
                "Also known as: Nameko\nScientific name: Pholiota nameko",
                "Pholiota nameko is an edible mushroom. It is commonly cultivated and sold in East Asia. The nameko has a slightly gelatinous coating and a unique, slightly nutty flavor. It is most commonly used in miso soup and nabemono.",
                "5 cm",
                "5 - 9 cm",
                "Orange, Brown, Bronze",
                "Saprotrophic",
                "On wood",
                "Cinnamon brown",
                "Edible"
        );

        mushroomDetails[16] = new MushroomData("Pleurotus eryngii",
                "Also known as: King trumpet mushroom, French horn mushroom, King oyster mushroom\nScientific name: Pleurotus eryngii",
                "Pleurotus eryngii, also known as king trumpet mushroom, French horn mushroom, king oyster mushroom, or eryngii, is an edible mushroom native to Mediterranean regions of Europe, the Middle East, and North Africa, but also grown in many parts of Asia. It is related to the similarly cultivated king oyster mushroom.",
                "10 cm",
                "15 cm",
                "Brown, White",
                "Saprotrophic",
                "Pinus, Quercus, Corylus, Fagus",
                "White",
                "Edible"
        );

        mushroomDetails[17] = new MushroomData("Russula virescens",
                "Also known as: Green-cracking russula\nScientific name: Russula virescens",
                "Russula virescens is a species of mushroom in the family Russulaceae. It is found in Europe and North America. The fruit bodies are small to medium-sized, with caps that are initially convex before flattening out in age. The cap color is variable, ranging from green to yellow-green, and the gills are white.",
                "10 cm",
                "10 cm",
                "Green, Yellow, White",
                "Mycorrhizal",
                "Pinus, Quercus, Fagus",
                "White",
                "Poisonous"
        );

        mushroomDetails[18] = new MushroomData("Shiitake",
                "Also known as: Shiitake\nScientific name: Lentinula edodes",
                "Lentinula edodes is a species of edible mushroom in the family Marasmiaceae, native to East Asia. It is cultivated and consumed in many Asian countries. It is considered a medicinal mushroom in some forms of traditional medicine.",
                "10 cm",
                "5 cm",
                "Brown",
                "Saprotrophic",
                "Pinus, Quercus, Corylus, Fagus",
                "Brown",
                "Edible"
        );

        mushroomDetails[19] = new MushroomData("Tricholoma flavovirens",
                "Also known as: Tricholoma\nScientific name: Tricholoma flavovirens",
                "Tricholoma flavovirens is a species of mushroom in the family Tricholomataceae. It is found in Europe and North America. The fruit bodies are medium-sized, with caps that are initially convex before flattening out in age. The cap color is variable, ranging from green to yellow-green, and the gills are white.",
                "6 cm",
                "4 - 8 cm",
                "Brown, Gray, White",
                "Mycorrhizal",
                "Pseudotsuga menziesii",
                "White",
                "Poisonous"
        );


}

}