package daodemo.hc.com.greendao;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import daodemo.hc.com.greendao.dao.DaoMaster;
import daodemo.hc.com.greendao.dao.DaoSession;
import daodemo.hc.com.greendao.dao.UserDao;
import daodemo.hc.com.greendao.user.User;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "lylog";
    EditText editUserName;
    EditText editId;

    Button addBt;
    Button deleteBt;
    Button queryBt;
    Button updateBt;
    TextView result;

    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private UserDao mUserDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initDataBase();
        initEvent();

    }

    private void initEvent() {
        addBt.setOnClickListener(this);
        deleteBt.setOnClickListener(this);
        queryBt.setOnClickListener(this);
        updateBt.setOnClickListener(this);
    }

    private void initDataBase() {
        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        mHelper = new DaoMaster.DevOpenHelper(this, "notes-db1", null);
        db = mHelper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();

        mUserDao = this.getDaoSession().getUserDao();
    }

    private void initView() {
        editUserName =findViewById(R.id.etName);
        editId =findViewById(R.id.etId);
        addBt = findViewById(R.id.btnAdd);
        deleteBt = findViewById(R.id.btnDelete);
        queryBt = findViewById(R.id.btnQuery);
        updateBt = findViewById(R.id.btnUpdate);

        result = findViewById(R.id.result);
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }
    public SQLiteDatabase getDb() {
        return db;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnAdd:
                insertData();
                break;
            case R.id.btnDelete:
                deleteData();
                break;
            case R.id.btnQuery:
                queryData();
                break;
            case  R.id.btnUpdate:
               updateData();
                break;
        }
    }

    private void updateData() {
        if (editId.getText() != null &&editUserName.getText() != null&&  editId.getText().toString() != null && editUserName.getText().toString() != null) {
            User TextData = new User(Long.valueOf(editId.getText().toString()),editUserName.getText().toString());
            mUserDao.update(TextData);
        }
    }

    private void queryData() {
        long id  = -1;
        String name = null;
        List<User> users = mUserDao.loadAll();
        if (!editId.getText().toString().isEmpty() ) {
            Log.d(TAG, "queryData: "+editId.getText().toString());
            id = Long.valueOf(editId.getText().toString());
            Log.d(TAG, "queryData: id :"+id);

        }else if(editUserName.getText() != null ) {
            name = editUserName.getText().toString();
            Log.d(TAG, "queryData: name :"+name);
        }

        for (User s: users) {
            if (id != -1  && id == s.getId()) {
                result.setText("id :"+id +"\n"+"name :"+s.getName());
            }else if(name != null && s.getName()!= null && s.getName().equals(name)) {
                result.setText("id :"+id +"\n"+"name :"+s.getName());
            }else{
                showToast("请确认数据查询字段是否正确");
            }
        }
    }

    private void deleteData() {
        if (editId.getText() != null &&editUserName.getText() != null) {
            Long id = Long.valueOf(editId.getText().toString());
            mUserDao.deleteByKey(id);
        }else {
            showToast("数据输入不对，id");
        }
    }

    private void insertData() {
        if (editId.getText() != null &&editUserName.getText() != null&&  editId.getText().toString() != null && editUserName.getText().toString() != null) {
            User TextData = new User(Long.valueOf(editId.getText().toString()),editUserName.getText().toString());
            mUserDao.insert(TextData);
        }else {
            showToast("数据输入不对，id，name");
        }
    }

    private void showToast(String s) {
        Toast.makeText(this,s,Toast.LENGTH_LONG).show();
    }
}


