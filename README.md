# AndroidRoomDemo

## 環境設定 (gradle)
* 請在gradle 引用
``` gradle
dependencies {
    // Room
    compile "android.arch.persistence.room:runtime:1.0.0"
    annotationProcessor "android.arch.persistence.room:compiler:1.0.0"
}
```
* 如果是Kotlin，請改成
``` gradle
apply plugin: 'kotlin-kapt' // Use Kotlin kapt

dependencies {
    // Room
    compile "android.arch.persistence.room:runtime:1.0.0"
    kapt "android.arch.persistence.room:compiler:1.0.0"
}
```

## 資料表 (Entity)
``` kotlin
@Entity(tableName = "Users")
data class User(

        @ColumnInfo(name = "first_name") var firstName: String,
        @ColumnInfo(name = "last_name") var lastName: String)
{
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0;
}
```

## 查詢邏輯 (Dao)
``` kotlin
@Dao
interface UserDao
{
    @Query("SELECT * FROM Users")
    fun getAll(): List<User>;

    @Query("SELECT * FROM Users WHERE uid IN (:userIds)")
    fun loadAllByIds(vararg userIds: Int): List<User>;

    @Query("SELECT * FROM Users " +
            "WHERE first_name LIKE :first AND last_name LIKE :last LIMIT 1")
    fun findByName(first: String, last: String): User;

    @Insert
    fun insertAll(vararg users: User);

    @Delete
    fun delete(user: User);
}
```

## 資料庫 (Database)
``` kotlin
@Database(entities = arrayOf(User::class), version = 1)
abstract class AppDatabase : RoomDatabase()
{
    //  取得Dao
    abstract fun userDao(): UserDao;

    companion object
    {
        priv取得Daoate var instance: AppDatabase? = null;

        /**
         * Kotlin 的 Singleton
         */
        fun getInstance(context: Context): AppDatabase
        {
            if (instance == null)
            {
                synchronized(AppDatabase::class) {
                    if (instance == null)
                    {
                        instance = Room.databaseBuilder(
                                context.applicationContext,
                                AppDatabase::class.java,
                                "User.db")
                                .allowMainThreadQueries() //can use main thread
                                .build();
                    }
                }
            }
            return instance!!
        }
    }
}
```

## 每一次數據更新的資料庫語法
``` groovy
android {
    defaultConfig {
        ...
       // used by Room, to test migrations
        javaCompileOptions {
            annotationProcessorOptions {
                arguments = ["room.schemaLocation": 
                                 "$projectDir/schemas".toString()]
            }
        }
    }
 
    // used by Room, to test migrations
    sourceSets {
        androidTest.assets.srcDirs += 
                           files("$projectDir/schemas".toString())
    }
}
```

* 內容如下
```json
{
  "formatVersion": 1,
  "database": {
    "version": 1,
    "identityHash": "c42797681b5b28d5658d020804100fe1",
    "entities": [
      {
        "tableName": "Users",
        "createSql": "CREATE TABLE IF NOT EXISTS `${TABLE_NAME}` (`uid` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `first_name` TEXT NOT NULL, `last_name` TEXT NOT NULL)",
        "fields": [
          {
            "fieldPath": "uid",
            "columnName": "uid",
            "affinity": "INTEGER",
            "notNull": true
          },
          {
            "fieldPath": "firstName",
            "columnName": "first_name",
            "affinity": "TEXT",
            "notNull": true
          },
          {
            "fieldPath": "lastName",
            "columnName": "last_name",
            "affinity": "TEXT",
            "notNull": true
          }
        ],
        "primaryKey": {
          "columnNames": [
            "uid"
          ],
          "autoGenerate": true
        },
        "indices": [],
        "foreignKeys": []
      }
    ],
    "setupQueries": [
      "CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)",
      "INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, \"c42797681b5b28d5658d020804100fe1\")"
    ]
  }
}
```