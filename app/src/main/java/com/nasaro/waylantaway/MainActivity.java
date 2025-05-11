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

import com.nasaro.waylantaway.Models.Car;
import com.nasaro.waylantaway.Models.User;
import com.nasaro.waylantaway.db.SqlHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText editText_name, editText_email, editText_id;
    EditText editText_carMake, editText_carModel, editText_carYear, editText_carID, editText_carUser_ID;
    Button btn_addCar, btn_deleteCar, btn_showAllUser2, btn_updateCar;
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

        editText_carMake = findViewById(R.id.editText_carMake);
        editText_carModel = findViewById(R.id.editText_carModel);
        editText_carYear = findViewById(R.id.editText_carYear);
        editText_carID = findViewById(R.id.editText_carID);
        editText_carUser_ID = findViewById(R.id.editText_carUser_ID);

        btn_addCar = findViewById(R.id.btn_addCar);
        btn_deleteCar = findViewById(R.id.btn_deleteCar);
        btn_showAllUser2 = findViewById(R.id.btn_showAllUser2);
        btn_updateCar = findViewById(R.id.btn_updateCar);


        btn_addUser = findViewById(R.id.btn_addUser);
        btn_deleteUser = findViewById(R.id.btn_deleteUser);
        btn_showAllUser = findViewById(R.id.btn_showAllUser);
        btn_updateUser = findViewById(R.id.btn_updateUser);



        btn_addUser.setOnClickListener(v -> AddUser());
        btn_updateUser.setOnClickListener(v -> updateUser());
        btn_deleteUser.setOnClickListener(v -> deleteUser());
        btn_showAllUser.setOnClickListener(v -> showAllUser());

        btn_addCar.setOnClickListener(v -> AddCar());
        btn_deleteCar.setOnClickListener(v -> DeleteCar());


    }
    public void UpdateCar() {
        String make = editText_carMake.getText().toString().trim();
        String model = editText_carModel.getText().toString().trim();
        String year = editText_carYear.getText().toString().trim();
        String userID = editText_carUser_ID.getText().toString().trim();
        String id = editText_carID.getText().toString().trim();

        if ((make.isEmpty() && model.isEmpty() && year.isEmpty() && userID.isEmpty()) || id.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            int carId = Integer.parseInt(id);
            Car car = new Car();
            if(!make.isEmpty())
                car.make = make;
            if(!model.isEmpty())
                car.model = model;
            if(!year.isEmpty())
                car.year = Integer.parseInt(year);
            if(!userID.isEmpty())
                car.user_id = Long.parseLong(userID);

            String whereClause = "id = ?";
            String[] whereArgs = {String.valueOf(carId)};
            int rowsAffected = db.update(Car.tableName, car.toContentValues(), whereClause, whereArgs);

            if (rowsAffected > 0) {
                Toast.makeText(this, "Car updated successfully", Toast.LENGTH_SHORT).show();
                editText_carMake.setText("");
                editText_carModel.setText("");
                editText_carYear.setText("");
                editText_carID.setText("");
                editText_carUser_ID.setText("");
                showAllUser();
                }
            else
            {
                Toast.makeText(this, "Failed to update car", Toast.LENGTH_SHORT).show();
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid ID", Toast.LENGTH_SHORT).show();
        }

    }
    public void DeleteCar()
    {
        String id = editText_carID.getText().toString().trim();
        if(id.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            int carId = Integer.parseInt(id);
            String whereClause = "id = ?";
            String[] whereArgs = {String.valueOf(carId)};
            int rowsAffected = db.delete(Car.tableName, whereClause, whereArgs);
            if(rowsAffected > 0)
            {
                Toast.makeText(this, "Car deleted successfully", Toast.LENGTH_SHORT).show();
                editText_carID.setText("");
                showAllUser();
            }
            else
            {
                Toast.makeText(this, "Failed to delete car", Toast.LENGTH_SHORT).show();
            }
        }
        catch(NumberFormatException e)
        {
            Toast.makeText(this, "Invalid ID", Toast.LENGTH_SHORT).show();
        }
        editText_id.setText("");
        showAllUser();
    }
    public void AddCar()
    {
        String make = editText_carMake.getText().toString().trim();
        String model = editText_carModel.getText().toString().trim();
        String year = editText_carYear.getText().toString().trim();
        String userID = editText_carUser_ID.getText().toString().trim();

        if(make.isEmpty() || model.isEmpty() || year.isEmpty())
        {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }
        Car car = new Car(make, model, Integer.parseInt(year), Long.parseLong(userID));

        long id = db.insert(Car.tableName, car.toContentValues());

        if(id > 0)
        {
            Toast.makeText(this, "Car added successfully", Toast.LENGTH_SHORT).show();
            editText_carMake.setText("");
            editText_carModel.setText("");
            editText_carYear.setText("");
        }
        else
        {
            Toast.makeText(this, "Failed to add car", Toast.LENGTH_SHORT).show();
        }
        showAllUser();
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
        if((name.isEmpty() && email.isEmpty()) || id.isEmpty())
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
    public List<Car> getAllCarsbyUserID(long userID)
    {
        List<Car> cars = new ArrayList<>();
        Cursor cursor = db.query(Car.tableName, "user_id = ?", new String[]{String.valueOf(userID)});
        while(cursor.moveToNext())
        {
            Car car = Car.fromCursor(cursor);
            cars.add(car);
        }
        return cars;
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
                List<Car> cars = getAllCarsbyUserID(user.id);
                if(!cars.isEmpty())
                {
                    stringBuilder.append("Cars: ");
                    for(Car car : cars)
                    {
                        stringBuilder.append("Make: ").append(car.make).append(", Model: ").append(car.model).append(", Year: ").append(car.year).append('\n');
                    }
                }
                else
                {
                    stringBuilder.append("No cars found ");
                }
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