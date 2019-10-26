package respositories

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.INTEGER
import org.jetbrains.anko.db.ManagedSQLiteOpenHelper
import org.jetbrains.anko.db.TEXT
import org.jetbrains.anko.db.createTable

class ArticleDatabaseOpenHelper(context: Context)
    :ManagedSQLiteOpenHelper(context,"ArticleDatabase.db",null,1){
    override fun onCreate(db: SQLiteDatabase?) {
        db?.createTable("Favourites",true,"id" to INTEGER,
            "title" to TEXT,
            "url" to TEXT,
            "thumbnailJson" to TEXT)
        db?.createTable("History",true,"id" to INTEGER,
            "title" to TEXT,
            "url" to TEXT,
            "thumbnailJson" to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //for upgrading table
   }

}