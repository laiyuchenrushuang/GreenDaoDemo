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
   
