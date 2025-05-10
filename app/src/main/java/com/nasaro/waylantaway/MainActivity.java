package com.nasaro.waylantaway;

import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.nasaro.waylantaway.Models.User;
import com.nasaro.waylantaway.db.SqlHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText editText_name, editText_email, editText_id;
    Button btn_addUser, btn_deleteUser, btn_showAllUser, btn_updateUser;
    SqlHelper db;
    TextView textview_showAllUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        db = new SqlHelper(this);
        textview_showAllUser = findViewById(R.id.textview_showAllUser);

        editText_name = findViewById(R.id.editText_name);
        editText_email = findViewById(R.id.editText_email);
        editText_id = findViewById(R.id.editText_id);

        btn_addUser = findViewById(R.id.btn_addUser);
        btn_deleteUser = findViewById(R.id.btn_deleteUser);
        btn_showAllUser = findViewById(R.id.btn_showAllUser);
        btn_updateUser = findViewById(R.id.btn_updateUser);

        btn_addUser.setOnClickListener(v -> AddUser());
        btn_updateUser.setOnClickListener(v -> updateUser());
        btn_deleteUser.setOnClickListener(v -> deleteUser());
        btn_showAllUser.setOnClickListener(v -> showAllUser());

    }
    public void AddUser()
    {
        String name = editText_name.getText().toString().trim();
        String email = editText_email.getText().toString().trim();

        if(name.isEmpty() || email.isEmpty())
        {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }
        User user = new User(name, email);
        long id = db.insert(User.table_name, user.toValues());
        if(id > 0)
        {
            Toast.makeText(this, "User added successfully", Toast.LENGTH_SHORT).show();
            editText_name.setText("");
            editText_email.setText("");
        }
        else
        {
            Toast.makeText(this, "Failed to add user", Toast.LENGTH_SHORT).show();
        }
        showAllUser();
    }
    public void updateUser()
    {
        String name = editText_name.getText().toString().trim();
        String email = editText_email.getText().toString().trim();
        String id = editText_id.getText().toString().trim();
        if(name.isEmpty() || (email.isEmpty() && id.isEmpty()))
        {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }
        User user = new User(name, email);

        try {
            int userId = Integer.parseInt(id);
            String whereClause = "id = ?";
            String[] whereArgs = {String.valueOf(userId)};
            int rowsAffected = db.update(User.table_name, user.toValues(), whereClause, whereArgs);
            if(rowsAffected > 0) {
                Toast.makeText(this, "User updated successfully", Toast.LENGTH_SHORT).show();
                editText_name.setText("");
                editText_email.setText("");
                editText_id.setText("");
                showAllUser();
            }
            else
            {
                Toast.makeText(this, "Failed to update user", Toast.LENGTH_SHORT).show();
            }
        }
        catch(NumberFormatException e)
        {
            Toast.makeText(this, "Invalid ID", Toast.LENGTH_SHORT).show();
        }
    }
    public void showAllUser()
    {
        List<User> users = getAllUsers();
        StringBuilder stringBuilder = new StringBuilder();

        if(!users.isEmpty())
        {
            for(User user : users)
            {
                stringBuilder.append("ID: ").append(user.id).append(", Name: ").append(user.name).append(", Email: ").append(user.email).append('\n');
            }
        }
        else
        {
            stringBuilder.append("No users found");
        }

        textview_showAllUser.setText(stringBuilder.toString());
    }
    public void deleteUser()
    {
        String id = editText_id.getText().toString().trim();
        if(id.isEmpty())
        {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            int userId = Integer.parseInt(id);
            String whereClause = "id = ?";
            String[] whereArgs = {String.valueOf(userId)};
            int rowsAffected = db.delete(User.table_name, whereClause, whereArgs);
        }
        catch(NumberFormatException e)
        {
            Toast.makeText(this, "Invalid ID", Toast.LENGTH_SHORT).show();
        }
        editText_id.setText("");
        showAllUser();
    }
    public List<User> getAllUsers()
    {
        List<User> users = new ArrayList<>();
        Cursor cursor = db.getAll(User.table_name);
        while(cursor.moveToNext())
        {
            User user = User.fromCursor(cursor);
            users.add(user);
        }
        return users;
    }


}