package com.example.testclassification;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.testclassification.ml.AngleDetectionModelLite;
import com.example.testclassification.ml.BackClassificationModelLite;
import com.example.testclassification.ml.FrontClassificationModelLite;
import com.example.testclassification.ml.SideClassificationModelLite;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HashMap;

//import app.ml.*;


public class MainActivity extends AppCompatActivity {

    Button camera, gallery;
    ImageView imageView;
    TextView angle, model, make, price;
    int imageSize = 32;
    HashMap<String, String > priceList = new HashMap<String, String >();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        camera = findViewById(R.id.button);
        gallery = findViewById(R.id.button2);

        angle = findViewById(R.id.angle);
        model = findViewById(R.id.model);
        make = findViewById(R.id.make);
        price = findViewById(R.id.price);

        imageView = findViewById(R.id.imageView);


        priceList.put("Ford_Explorer_2020-2023","$36k - $38k");
        priceList.put("Ford_Mustang_2013-2014","$22k - $27k");
        priceList.put("Honda_Accord_2019-2022","$24k - $27k");
        priceList.put("Honda_Civic_2016-2021","$20k - $22k");
        priceList.put("Hyundai_Elantra_2019-2020","$19k - $22k");
        priceList.put("Hyundai_Elantra_2011-2016","$15k - $20k");


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

//    public void classifyImage(Bitmap image){
//
//    }

    public void classifyImage(Bitmap image){
        try {

            AngleDetectionModelLite angleModel = AngleDetectionModelLite.newInstance(getApplicationContext());

            // Creates inputs for reference.
            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 250, 250, 3}, DataType.FLOAT32);
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * 250 * 250 * 3);
            byteBuffer.order(ByteOrder.nativeOrder());

            int[] intValues = new int[250 * 250];
            image.getPixels(intValues, 0, image.getWidth(), 0, 0, image.getWidth(), image.getHeight());
            int pixel = 0;
            //iterate over each pixel and extract R, G, and B values. Add those values individually to the byte buffer.
            for(int i = 0; i < 250; i ++){
                for(int j = 0; j < 250; j++){
                    int val = intValues[pixel++]; // RGB
                    byteBuffer.putFloat(((val >> 16) & 0xFF) * (1.f / 1));
                    byteBuffer.putFloat(((val >> 8) & 0xFF) * (1.f / 1));
                    byteBuffer.putFloat((val & 0xFF) * (1.f / 1));
                }
            }

            inputFeature0.loadBuffer(byteBuffer);

            // Runs model inference and gets result.
            AngleDetectionModelLite.Outputs outputs = angleModel.process(inputFeature0);
            TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

            float[] confidences = outputFeature0.getFloatArray();
            // find the index of the class with the biggest confidence.
            int maxPos = 0;
            float maxConfidence = 0;
            for (int i = 0; i < confidences.length; i++) {
                if (confidences[i] > maxConfidence) {
                    maxConfidence = confidences[i];
                    maxPos = i;
                }
            }
            String[] classes = {"Back", "Front", "Side"};
            angle.setText(classes[maxPos]);

            // Releases model resources if no longer used.
            angleModel.close();

            classifyModelMake(inputFeature0,classes[maxPos]);

        } catch (IOException e) {
            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG);
        }
    }

    public void classifyModelMake(TensorBuffer inputFeature0, String model_class){

        if(model_class == "Back"){

            try {
                BackClassificationModelLite backModel = BackClassificationModelLite.newInstance(getApplicationContext());

                // Runs model inference and gets result.
                BackClassificationModelLite.Outputs outputs = backModel.process(inputFeature0);
                TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

                float[] confidences = outputFeature0.getFloatArray();
                // find the index of the class with the biggest confidence.
                int maxPos = 0;
                float maxConfidence = 0;
                for (int i = 0; i < confidences.length; i++) {
                    if (confidences[i] > maxConfidence) {
                        maxConfidence = confidences[i];
                        maxPos = i;
                    }
                }
                String[] classes = {
                        "Ford_Explorer_2020-2023",
                        "Ford_Mustang_2013-2014",
                        "Honda_Accord_2019-2022",
                        "Honda_Accord_2023",
                        "Honda_Civic_2016-2021",
                        "Hyundai_Elantra_2011-2016",
                        "Hyundai_Elantra_2019-2020",
                        "Ford_Explorer_2020-2023",
                        "Ford_Mustang_2013-2014",
                        "Honda_Accord_2019-2022",
                        "Honda_Accord_2023",
                        "Honda_Civic_2016-2021",
                        "Hyundai_Elantra_2011-2016",
                        "Hyundai_Elantra_2019-2020"
                };


                String CarMake = classes[maxPos].split("_")[0];
                String CarModel = classes[maxPos].split("_")[1];
                String CarYear = classes[maxPos].split("_")[2];

                make.setText(CarMake);
                model.setText(CarModel +" " +CarYear);
                price.setText(priceList.get(classes[maxPos]).toString());
                // Releases model resources if no longer used.
                backModel.close();
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG);
            }


        }

        if(model_class == "Front"){

            try {
                FrontClassificationModelLite frontModel = FrontClassificationModelLite.newInstance(getApplicationContext());

                // Runs model inference and gets result.
                FrontClassificationModelLite.Outputs outputs = frontModel.process(inputFeature0);
                TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

                float[] confidences = outputFeature0.getFloatArray();
                // find the index of the class with the biggest confidence.
                int maxPos = 0;
                float maxConfidence = 0;
                for (int i = 0; i < confidences.length; i++) {
                    if (confidences[i] > maxConfidence) {
                        maxConfidence = confidences[i];
                        maxPos = i;
                    }
                }
                String[] classes = {
                        "Ford_Explorer_2020-2023",
                        "Ford_Mustang_2013-2014",
                        "Honda_Accord_2019-2022",
                        "Honda_Accord_2023",
                        "Honda_Civic_2016-2021",
                        "Hyundai_Elantra_2011-2016",
                        "Hyundai_Elantra_2019-2020",
                        "Ford_Explorer_2020-2023",
                        "Ford_Mustang_2013-2014",
                        "Honda_Accord_2019-2022",
                        "Honda_Accord_2023",
                        "Honda_Civic_2016-2021",
                        "Hyundai_Elantra_2011-2016",
                        "Hyundai_Elantra_2019-2020"
                };

                String CarMake = classes[maxPos].split("_")[0];
                String CarModel = classes[maxPos].split("_")[1];
                String CarYear = classes[maxPos].split("_")[2];

                make.setText(CarMake);
                model.setText(CarModel +" " +CarYear);
                price.setText(priceList.get(classes[maxPos]).toString());
                // Releases model resources if no longer used.
                frontModel.close();
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG);
            }

        }
        if(model_class == "Side"){

            try {
                SideClassificationModelLite sideModel = SideClassificationModelLite.newInstance(getApplicationContext());

                // Runs model inference and gets result.
                SideClassificationModelLite.Outputs outputs = sideModel.process(inputFeature0);
                TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

                float[] confidences = outputFeature0.getFloatArray();
                // find the index of the class with the biggest confidence.
                int maxPos = 0;
                float maxConfidence = 0;
                for (int i = 0; i < confidences.length; i++) {
                    if (confidences[i] > maxConfidence) {
                        maxConfidence = confidences[i];
                        maxPos = i;
                    }
                }
                String[] classes = {
                        "Ford_Explorer_2020-2023",
                        "Ford_Mustang_2013-2014",
                        "Honda_Accord_2019-2022",
                        "Honda_Accord_2023",
                        "Honda_Civic_2016-2021",
                        "Hyundai_Elantra_2011-2016",
                        "Hyundai_Elantra_2019-2020"
                };

                String CarMake = classes[maxPos].split("_")[0];
                String CarModel = classes[maxPos].split("_")[1];
                String CarYear = classes[maxPos].split("_")[2];

                make.setText(CarMake);
                model.setText(CarModel +" " +CarYear);
                price.setText(priceList.get(classes[maxPos]).toString());
                // Releases model resources if no longer used.
                sideModel.close();
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG);
            }


        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == RESULT_OK){
            if(requestCode == 3){
                Bitmap image = (Bitmap) data.getExtras().get("data");
                int dimension = Math.min(image.getWidth(), image.getHeight());
                image = ThumbnailUtils.extractThumbnail(image, dimension, dimension);
                imageView.setImageBitmap(image);

                image = Bitmap.createScaledBitmap(image, 250, 250, false);
                classifyImage(image);
            }else{
                Uri dat = data.getData();
                Bitmap image = null;
                try {
                    image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), dat);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                imageView.setImageBitmap(image);

                image = Bitmap.createScaledBitmap(image, 250, 250, false);
                classifyImage(image);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}