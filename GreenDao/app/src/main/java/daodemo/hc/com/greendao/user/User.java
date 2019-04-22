package daodemo.hc.com.greendao.user;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by ly on 2019/4/22.
 */
@Entity(indexes = {
        @Index(value = "name", unique = true)
})
public class User {

    @Id
    private Long id;

    @NotNull
    private String name;

    @Keep
    public User(long id, @NotNull String name) {
        this.id = id;
        this.name = name;
    }



    @Keep
    public User() {
    }



    @Generated(hash = 1709734220)
    public User(Long id, @NotNull String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public void setId(Long id) {
        this.id = id;
    }


}
