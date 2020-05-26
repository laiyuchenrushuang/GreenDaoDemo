# GreenDaoDemo
# 插件化导入dao工程，有点牛掰。

### 开启过程

####.  添加依赖

   root  build.gradle  classpath 'org.greenrobot:greendao-gradle-plugin:3.3.0'
   
   app   build.gradle  
   
         apply plugin: 'com.android.application'
         apply plugin: 'org.greenrobot.greendao' // apply plugin
         dependencies {
                 implementation 'org.greenrobot:greendao:3.3.0' // add library
         }
         
        
####. 实体类的构建

   写好变量：
          
    @Id(autoincrement = true)//设置自增长  
    private Long id;  

    @Index(unique = true)//设置唯一性  
    private String perNo;//人员编号  

    private String name;//人员姓名  

    private String sex;//人员姓名  
    
   类名加@Enity：
   
   @Entity  
   public class PersonInfor  
   
   然后Make project  自动生成其他代码

### 逻辑代码

####. 增

       //新增
       public long insert(PersonInfor personInfor) {
            return personInforDao.insert(personInfor);
      }


       //插入还是替换 
       public void insertOrReplace(PersonInfor personInfor) {
            personInforDao.insertOrReplace(personInfor);
       }
     
####. 删

   //删除一条数据
   
        public void delete(String wherecluse) {
               personInforDao.queryBuilder()
                         .where(PersonInforDao.Properties.PerNo.eq(wherecluse))
                         .buildDelete()
                         .executeDeleteWithoutDetachingEntities();
           }
   
       //删除多条数据
       public void delete(PersonInfor personinfor) {

            QueryBuilder<PersonInfor> builder = personInforDao.queryBuilder();

        //可以利用反射 获取成员变量的值 然后遍历加进去
            if (!TextUtils.isEmpty(personinfor.getPerNo()) && personinfor.getPerNo() != null && !"/".equals(personinfor.getPerNo())) {  //编号
                  builder.where(PersonInforDao.Properties.PerNo.eq(personinfor.getPerNo()));
            }   

            if (!TextUtils.isEmpty(personinfor.getName()) && personinfor.getName() != null && !"/".equals(personinfor.getName())) {  //名字
                builder.where(PersonInforDao.Properties.Name.eq(personinfor.getName()));
            }

            if (!TextUtils.isEmpty(personinfor.getSex()) && personinfor.getSex() != null && !"/".equals(personinfor.getSex())) {  //性别
               builder.where(PersonInforDao.Properties.Sex.eq(personinfor.getSex()));
            }

               builder.buildDelete().executeDeleteWithoutDetachingEntities();

         }
      /**
     * 删除所有数据
     */
    public void deleteAll(){
        personInforDao.deleteAll();
    }
    
 ####.改
 
     
        /**
          * 更新数据
          *
          * @param personInfor
          */
            public void update(PersonInfor personInfor) {
               PersonInfor mOldPersonInfor = personInforDao.queryBuilder().where(PersonInforDao.Properties.PerNo.eq(personInfor.getPerNo())).build().unique();//拿到之前的记录
               if (mOldPersonInfor != null) {
                     mOldPersonInfor.setPerNo(personInfor.getPerNo());
                     mOldPersonInfor.setName(personInfor.getName());
                     mOldPersonInfor.setSex(personInfor.getSex());
                     personInforDao.update(mOldPersonInfor);
               }
            }
    
            //这里主要重点  unique()  这个unique 看是标记的哪个唯一变量
 
 
 ####.查
 
      /**
     * 查询所有数据
     */
     
    public List<PersonInfor> searchAll() {
        List<PersonInfor> personInfors = personInforDao.queryBuilder().list();
        return personInfors;
    }
    

        /**
     * 查询条件数据
     */
    public List<PersonInfor> query(PersonInfor personinfor) {
        QueryBuilder<PersonInfor> builder = personInforDao.queryBuilder();

        //可以利用反射 获取成员变量的值 然后遍历加进去
        if (!TextUtils.isEmpty(personinfor.getPerNo()) && personinfor.getPerNo() != null && !"/".equals(personinfor.getPerNo())) {  //编号
            builder.where(PersonInforDao.Properties.PerNo.eq(personinfor.getPerNo()));
        }

        if (!TextUtils.isEmpty(personinfor.getName()) && personinfor.getName() != null && !"/".equals(personinfor.getName())) {  //名字
            builder.where(PersonInforDao.Properties.Name.eq(personinfor.getName()));
        }

        if (!TextUtils.isEmpty(personinfor.getSex()) && personinfor.getSex() != null && !"/".equals(personinfor.getSex())) {  //性别
            builder.where(PersonInforDao.Properties.Sex.eq(personinfor.getSex()));
        }

        return builder.list();
    }
   
   
  #### 补充
  
（1）上面代码介绍的是查询所有数据，查询数据数量方式如下：

  int count = mUserDao.count();   
（2）精确（where）条件查询

//查询电影名为“肖申克的救赎”的电影  
MovieCollect movieCollect = 
mMovieCollectDao.queryBuilder().where(MovieCollectDao.Properties.Title.eq("肖申克的救赎")).unique(); 
 
//查询电影年份为2017的电影  
List<MovieCollect> movieCollect =
mMovieCollectDao.queryBuilder().where(MovieCollectDao.Properties.Year.eq(2017)).list();  
（3）模糊查询（like）

//查询电影名含有“传奇”的电影  
List<MovieCollect> movieCollect = mMovieCollectDao.queryBuilder().where(MovieCollectDao.Properties.Title.like("传奇")).list();
 
//查询电影名以“我的”开头的电影  
List<MovieCollect> movieCollect = mMovieCollectDao.queryBuilder().where(MovieCollectDao.Properties.Title.like("我的%")).list();  
（4）区间查询

//大于
//查询电影年份大于2012年的电影  
List<MovieCollect> movieCollect = mMovieCollectDao.queryBuilder().where(MovieCollectDao.Properties.Year.gt(2012)).list();
 
//大于等于  
//查询电影年份大于等于2012年的电影  
List<MovieCollect> movieCollect = mMovieCollectDao.queryBuilder().where(MovieCollectDao.Properties.Year.ge(2012)).list();
 
//小于  
//查询电影年份小于2012年的电影  
List<MovieCollect> movieCollect = mMovieCollectDao.queryBuilder().where(MovieCollectDao.Properties.Year.lt(2012)).list();
 
//小于等于  
//查询电影年份小于等于2012年的电影  
List<MovieCollect> movieCollect = mMovieCollectDao.queryBuilder().where(MovieCollectDao.Properties.Year.le(2012)).list();
 
//介于中间  
//查询电影年份在2012-2017之间的电影  
List<MovieCollect> movieCollect = mMovieCollectDao.queryBuilder().where(MovieCollectDao.Properties.Year.between(2012,2017)).list();  
（5）升序降序

//查询电影年份大于2012年的电影，并按年份升序排序  
List<MovieCollect> movieCollect = mMovieCollectDao.queryBuilder().where(MovieCollectDao.Properties.Year.gt(2012)).orderAsc(MovieCollectDao.Properties.Year).list();
 
//查询电影年份大于2012年的电影，并按年份降序排序  
List<MovieCollect> movieCollect = mMovieCollectDao.queryBuilder().where(MovieCollectDao.Properties.Year.gt(2012)).orderDesc(MovieCollectDao.Properties.Year).list();  
（6）and/or

//and  
//查询电影年份大于2012年且电影名以“我的”开头的电影  
List<MovieCollect> movieCollect = mMovieCollectDao.queryBuilder().and(MovieCollectDao.Properties.Year.gt(2012), MovieCollectDao.Properties.Title.like("我的%")).list();  
 
//or  
//查询电影年份小于2012年或者大于2015年的电影  
List<MovieCollect> movieCollect = mMovieCollectDao.queryBuilder().or(MovieCollectDao.Properties.Year.lt(2012), MovieCollectDao.Properties.Year.gt(2015)).list()
   
### 缓存处理

  由于GreenDao默认开启了缓存，所以当你调用A查询语句取得X实体，然后对X实体进行修改并更新到数据库，接着再调用A查询语句取得X实体，会发现X实体的内容依旧是修改前的。其实你的修改已经更新到数据库中，只是查询采用了缓存，所以直接返回了第一次查询的实体。  
解决方法：查询前先清空缓存，清空方法如下

//清空所有数据表的缓存数据  
DaoSession daoSession = MyApplication.getInstances().getDaoSession();  
daoSession .clear();
 
//清空某个数据表的缓存数据  
UserDao userDao = MyApplication.getInstances().getDaoSession().getUserDao();  
userDao.detachAll();

### 数据库加密

添加依赖  

compile 'net.zetetic:android-database-sqlcipher:3.5.7'//使用加密数据库时需要添加  
获取操作的数据库对象  

mSQLiteOpenHelper = new MySQLiteOpenHelper(MyApplication.getInstance(), DB_NAME, null);//建库  
mDaoMaster = new DaoMaster(mSQLiteOpenHelper.getEncryptedWritableDb("你的密码"));//加密  
//mDaoMaster = new DaoMaster(mSQLiteOpenHelper.getWritableDatabase());  
mDaoSession = mDaoMaster.newSession();  
温馨提示：  
（1）使用上面步骤得到的DaoSession进行具体的数据表操作。  
（2）如果运行后报无法加载有关so库的异常，请对项目进行clean和rebuild。

### 数据库版本升级

  在版本迭代时，我们经常需要对数据库进行升级，而GreenDAO默认的DaoMaster.DevOpenHelper在进行数据升级时，会把旧表删除，然后创建新表，并没有迁移旧数据到新表中，从而造成数据丢失。  
  这在实际中是不可取的，因此我们需要作出调整。下面介绍数据库升级的步骤与要点。  
第一步  
  新建一个类，继承DaoMaster.DevOpenHelper，重写onUpgrade(Database db, int oldVersion, int newVersion)方法，在该方法中使用MigrationHelper进行数据库升级以及数据迁移。  
网上有不少MigrationHelper的源码，这里采用的是https://github.com/yuweiguocn/GreenDaoUpgradeHelper中的MigrationHelper，它主要是通过创建一个临时表，将旧表的数据迁移到新表中，大家可以去看下源码。  

public class MyOpenHelper extends DaoMaster.OpenHelper {
    public MyOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }
 
    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
 
        //把需要管理的数据库表DAO作为最后一个参数传入到方法中
        MigrationHelper.migrate(db, new MigrationHelper.ReCreateAllTableListener() {
 
            @Override
            public void onCreateAllTables(Database db, boolean ifNotExists) {
                DaoMaster.createAllTables(db, ifNotExists);
            }
 
            @Override
            public void onDropAllTables(Database db, boolean ifExists) {
                DaoMaster.dropAllTables(db, ifExists);
            }
        },  MovieCollectDao.class);
    }
}  
  然后使用MyOpenHelper替代DaoMaster.DevOpenHelper来进行创建数据库等操作  

mSQLiteOpenHelper = new MyOpenHelper(MyApplication.getInstance(), DB_NAME, null);//建库  
mDaoMaster = new DaoMaster(mSQLiteOpenHelper.getWritableDatabase());  
mDaoSession = mDaoMaster.newSession();  
第二步  
  在表实体中，调整其中的变量（表字段），一般就是新增/删除/修改字段。注意：  
  （1）新增的字段或修改的字段，其变量类型应使用基础数据类型的包装类，如使用Integer而不是int，避免升级过程中报错。  
  （2）根据MigrationHelper中的代码，升级后，新增的字段和修改的字段，都会默认被赋予null值。  

第三步  
  将原本自动生成的构造方法以及getter/setter方法删除，重新Build—>Make Project进行生成。  

第四步  
  修改Module下build.gradle中数据库的版本号schemaVersion ，递增加1即可，最后运行app  

    greendao{
    //数据库版本号，升级时进行修改
        schemaVersion 2
        //dao的包名，包名默认是entity所在的包；
        daoPackage 'com.greendao.gen'
        //生成数据库文件的目录;
        targetGenDir 'src/main/java'
    }
###  代码混淆  

在proguard-rules.pro文件中添加以下内容进行混淆配置 
'# greenDAO开始  
-keepclassmembers class * extends org.greenrobot.greendao.AbstractDao {  
public static java.lang.String TABLENAME;  
}  
-keep class **$Properties  
'# If you do not use SQLCipher:  
-dontwarn org.greenrobot.greendao.database.**  
'# If you do not use RxJava:  
-dontwarn rx.**  
'# greenDAO结束 '
 
 
 如果按照上面介绍的加入了数据库加密功能，则需添加一下配置
'#sqlcipher数据库加密开始  
-keep  class net.sqlcipher.** {*;}  
-keep  class net.sqlcipher.database.** {*;}  
'#sqlcipher数据库加密结束  
   
   
   
   
   
   
###  实体类的代码

/**
 * Created by ly on 2020/5/21 14:37
 */
 
  @Entity
  
  public class PersonInfor {
  
    @Id(autoincrement = true)//设置自增长
    private Long id;

    @Index(unique = true)//设置唯一性
    private String perNo;//人员编号

    private String name;//人员姓名

    private String sex;//人员姓名

    private String time; //修改时间

    @Generated(hash = 1200744094)
    public PersonInfor(Long id, String perNo, String name, String sex,
            String time) {
        this.id = id;
        this.perNo = perNo;
        this.name = name;
        this.sex = sex;
        this.time = time;
    }

    @Generated(hash = 1362534400)
    public PersonInfor() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPerNo() {
        return this.perNo;
    }

    public void setPerNo(String perNo) {
        this.perNo = perNo;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return this.sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}


### 其他资料

![image](https://github.com/laiyuchenrushuang/GreenDaoDemo/blob/master/GreenDao/greendao.png)

代码地址：https://github.com/laiyuchenrushuang/Allinspection

   
   
   
