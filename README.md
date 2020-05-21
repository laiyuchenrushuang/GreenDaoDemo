# GreenDaoDemo
# 插件化导入dao工程，有点牛掰。

### 开启过程

1.  添加依赖

   root  build.gradle  classpath 'org.greenrobot:greendao-gradle-plugin:3.3.0'
   
   app   build.gradle  
   
         apply plugin: 'com.android.application'
         apply plugin: 'org.greenrobot.greendao' // apply plugin
         dependencies {
                 implementation 'org.greenrobot:greendao:3.3.0' // add library
         }
         
        
2. 实体类的构建

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

1. 增

       //新增
       public long insert(PersonInfor personInfor) {
            return personInforDao.insert(personInfor);
      }


       //插入还是替换 
       public void insertOrReplace(PersonInfor personInfor) {
            personInforDao.insertOrReplace(personInfor);
       }
     
2. 删

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
    
    
 3.改
 
 4.查
      /**
     * 查询所有数据
     */
     
    public List<PersonInfor> searchAll() {
        List<PersonInfor> personInfors = personInforDao.queryBuilder().list();
        return personInfors;
    }
    

   
   
   
   
   
   
   
   
   
   
   
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

   
   
   
