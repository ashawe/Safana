package com.ashstudios.safana.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.provider.SyncStateContract;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ashstudios.safana.R;
import com.ashstudios.safana.others.Msg;
import com.ashstudios.safana.others.SharedPref;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import io.grpc.internal.FailingClientStream;

public class EditProfileActivity extends AppCompatActivity {
    ImageView imageView;
    private final int SELECT_PHOTO = 1;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private Uri imageUri;
    private String uploadedUrl;
    private SharedPref sharedPref;
    private String filename;
    private EditText etName, etRole, etEmail, etPhoneNumber, etGender, etBirthDate;
    private Button btnSave;
    private FirebaseFirestore db;
    private TextView tvEmpId;
    private AlertDialog alertDialog;
    private ProgressBar progressBar;
    private TextView tv_alert;
    private boolean isFirst = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        if(getIntent().getStringExtra("which").equals("first"))
            isFirst = true;

        initialize();
        db = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        sharedPref = new SharedPref(EditProfileActivity.this);
        RetriveEmployeeData();
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Safana");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, SELECT_PHOTO);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateFields()) {
                    //if all valid then update the profile
                    if(filename != null)
                        uploadImageToFirebaseStorage();
                    else
                        updateProfile();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(isFirst) {
            //if this activity is open by the first time user then on back pressed load his dashboard
            Intent intent;
            if(sharedPref.getEMP_ID().contains("EMP"))
                 intent = new Intent(EditProfileActivity.this,WorkerDashboardActivity.class);
            else
                 intent= new Intent(EditProfileActivity.this,SupervisorDashboard.class);
            finish();
            startActivity(intent);
        }
        else
            super.onBackPressed();
    }

    private void RetriveEmployeeData() {
        tv_alert.setText("Retriving Data...");
        alertDialog.show();
        tvEmpId.setText(sharedPref.getEMP_ID());
        etName.setText(sharedPref.getNAME());
        etEmail.setText(sharedPref.getEMAIL());
        db.collection("Employees").document(sharedPref.getEMP_ID().trim()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot documentSnapshot = task.getResult();
                etRole.setText(documentSnapshot.getString("role"));
                etBirthDate.setText(documentSnapshot.getString("birth_date"));
                etPhoneNumber.setText(documentSnapshot.getString("mobile"));
                etGender.setText(documentSnapshot.getString("sex"));
                Picasso.get().load(documentSnapshot.getString("profile_image")).placeholder(R.drawable.ic_user).error(R.drawable.ic_user).into(imageView);
                alertDialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(EditProfileActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateProfile() {
        if(!alertDialog.isShowing()) {
            tv_alert.setText("Updating Profile...");
            alertDialog.show();
        }
        //update profile
        Map<String,String> data = new HashMap<>();
        if(filename != null)
            data.put("profile_image",uploadedUrl);
        data.put("name",etName.getText().toString().trim());
        data.put("mail",etEmail.getText().toString().trim());
        data.put("mobile",etPhoneNumber.getText().toString().trim());
        data.put("birth_date",etBirthDate.getText().toString().trim());
        data.put("role",etRole.getText().toString().trim());
        data.put("sex",etGender.getText().toString().trim());

        db.collection("Employees").document(sharedPref.getEMP_ID()).set(data, SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        alertDialog.dismiss();
                        Toast.makeText(EditProfileActivity.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
                        //if profile is updated then update the shared pref
                        sharedPref.setName(etName.getText().toString());
                        sharedPref.setEmail(etEmail.getText().toString());
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(EditProfileActivity.this, "Something went wrong while updating profile", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    private boolean validateFields() {
        boolean isValid = true;
        if(etName.getText().length() <= 0) {
            etName.setError("Enter name");
            isValid = false;
        }
        else if(!isValidEmail(etEmail.getText().toString())) {
            etEmail.setError("Enter valid email");
            isValid = false;
        }
        else if(etRole.getText().toString().length() <= 0) {
            etRole.setError("Enter role");
            isValid = false;
        }
        else if(etPhoneNumber.getText().toString().length() != 10) {
            etPhoneNumber.setError("Enter valid mobile number");
            isValid = false;
        }
        else if(etBirthDate.getText().toString().length() <=0 ) {
            etBirthDate.setError("Enter birth date");
            isValid = false;
        }
        return isValid;
    }

    private void initialize() {
        imageView = findViewById(R.id.img_profile_edit);
        etName = findViewById(R.id.emp_name);
        etEmail = findViewById(R.id.et_email_text);
        etBirthDate = findViewById(R.id.et_date_of_birth);
        etPhoneNumber = findViewById(R.id.et_mobile_number);
        etGender = findViewById(R.id.et_gender);
        etRole = findViewById(R.id.emp_role);
        btnSave = findViewById(R.id.btn_save);
        tvEmpId = findViewById(R.id.tv_emp_id);
        sharedPref = new SharedPref(EditProfileActivity.this);
        initializeProgressBarDialog();
    }

    private void initializeProgressBarDialog() {
        View v = getLayoutInflater().inflate(R.layout.alert_progress,null);
        progressBar = v.findViewById(R.id.progressBar2);
        tv_alert = v.findViewById(R.id.alert_tv);
        ProgressBar progressBar = new ProgressBar(EditProfileActivity.this);
        progressBar.setPadding(10,30,10,30);

        final AlertDialog.Builder alertDialogbuilder = new AlertDialog.Builder(EditProfileActivity.this);
        alertDialog = alertDialogbuilder.create();
        alertDialog.setCancelable(false);
        alertDialog.setView(v);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {
            case SELECT_PHOTO:
                if(resultCode == RESULT_OK){
                    try {
                        imageUri = data.getData();
                        Cursor c = getContentResolver().query(imageUri,null,null,null,null);
                        int n = 0;
                        if (c != null) {
                            n = c.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                        }
                        c.moveToFirst();
                        filename = c.getString(n);
                        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        imageView.setImageBitmap(selectedImage);

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                }
        }
    }

    private void uploadImageToFirebaseStorage() {
        tv_alert.setText("Updating Profile...");
        alertDialog.show();
        final StorageReference reference = storageReference.child(sharedPref.getEMP_ID()+"_"+filename);
        UploadTask uploadTask = reference.putFile(imageUri);
        uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if(!task.isSuccessful())
                    throw Objects.requireNonNull(task.getException());
                return reference.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if(task.isSuccessful()) {
                    Uri uri = task.getResult();
                    uploadedUrl = uri.toString();
                    updateProfile();
                }
                else {
                    Toast.makeText(EditProfileActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if(isFirst) {
                    //if this activity is open by the first time user then on back pressed load his dashboard
                    Intent intent;
                    if(sharedPref.getEMP_ID().contains("EMP"))
                        intent = new Intent(EditProfileActivity.this,WorkerDashboardActivity.class);
                    else
                        intent= new Intent(EditProfileActivity.this,SupervisorDashboard.class);
                    finish();
                    startActivity(intent);
                }
                else
                    super.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
