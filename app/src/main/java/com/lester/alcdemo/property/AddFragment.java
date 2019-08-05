package com.lester.alcdemo.property;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.lester.alcdemo.R;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;


public class AddFragment extends AppCompatActivity {

    private Context mContext;


    private ImageView profilep;
    private TextView txtLanguages, txtDepartments;
    private Uri filePath;

    private final int PICK_IMAGE_REQUEST = 71;

    //DECLARE THE FIELDS
    EditText userNameEditText, userStautsEditText;
    // ImageView userImageProfileView;
    LinearLayout saveProfileBtn;


    //FIREBASE DATABASE FIELDS


    //IMAGE HOLD URI
    Uri imageHoldUri = null;

    //PROGRESS DIALOG
    ProgressDialog mProgress;
    FirebaseStorage storage;
    StorageReference storageReference;


    private static final int REQUEST_CAMERA = 3;
    private static final int SELECT_FILE = 2;
    private FirebaseFirestore db;

    private Button btnChoose, btnUpload;
    private ImageView imageView;
    private String Img;

    public AddFragment() {

        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.fragment_property);
        btnChoose = findViewById(R.id.btnChoose);
        btnUpload = findViewById(R.id.btnUpload);
        imageView = findViewById(R.id.imgView);


        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddFragment.this, "lest", Toast.LENGTH_SHORT).show();

                //LOGIC FOR PROFILE PICTURE
                PicSelection();

            }

            private void profilePicSelection() {


                mProgress.setTitle("Saving Profile");
                mProgress.setMessage("Please wait....");
                mProgress.show();

              //  String profilePicUrl = imageHoldUri.getLastPathSegment();




                mProgress.dismiss();

                //  finish();
//                        Intent moveToHome = new Intent(Use, InstagramHome.class);
//                        moveToHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                        startActivity(moveToHome);

            }
        });


    }

    private void saveUserProfile() {

        mProgress = new ProgressDialog(this);


        saveProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //LOGIC FOR SAVING USER PROFILE
                saveUserProfile();

            }
        });

    }
    private void PicSelection() {


        //DISPLAY DIALOG TO CHOOSE CAMERA OR GALLERY

        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Photo!");

        //SET ITEMS AND THERE LISTENERS
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("Take Photo")) {
                    cameraIntent();
                } else if (items[item].equals("Choose from Library")) {
                    galleryIntent();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();

    }

    private void cameraIntent() {

        //CHOOSE CAMERA
        Log.d("gola", "entered here");
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    private void galleryIntent() {

        //CHOOSE IMAGE FROM GALLERY
        Log.d("gola", "entered here");
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, SELECT_FILE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        //SAVE URI FROM GALLERY
        if(requestCode == SELECT_FILE && resultCode == RESULT_OK)
        {
            Uri imageUri = data.getData();

            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1)
                    .start(this);

        }else if ( requestCode == REQUEST_CAMERA && resultCode == RESULT_OK ){
            //SAVE URI FROM CAMERA

            Uri imageUri = data.getData();

            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1)
                    .start(this);

        }


        //image crop library code
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                imageHoldUri = result.getUri();

                imageView.setImageURI(imageHoldUri);
                uploadImage(imageHoldUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }

    }





    private void uploadImage(Uri img) {

        if (filePath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            storage = FirebaseStorage.getInstance();
            storageReference = storage.getReference();
            StorageReference ref = storageReference.child("images/" + UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Img = Objects.requireNonNull(taskSnapshot.getUploadSessionUri()).toString();
                            AddProperty(Img);


                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(AddFragment.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded " + (int) progress + "%");
                        }
                    });
        }

    }

        private void AddProperty(String img) {

            final EditText mEditTextBody = (EditText) findViewById(R.id.editText2);
            final EditText mEditTextTitle = (EditText) findViewById(R.id.editText);
            final EditText mEditTextPrice = (EditText) findViewById(R.id.editText3);






            Map<String, Object> prop = new HashMap<>();
            prop.put("title", mEditTextTitle.getText().toString());

            prop.put("desc", mEditTextBody.getText().toString());
            prop.put("price",mEditTextPrice.getText().toString());
            prop.put("image",img);



            this.db = FirebaseFirestore.getInstance();
            db.collection("pp").add(prop).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Log.d("added property", documentReference.getId());
                    Toast.makeText(AddFragment.this, "Property Added", Toast.LENGTH_SHORT).show();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.w("failed", e);
                }
            });
        }


}




