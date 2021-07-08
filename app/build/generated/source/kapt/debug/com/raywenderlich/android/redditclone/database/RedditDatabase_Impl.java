package com.raywenderlich.android.redditclone.database;

import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.RoomOpenHelper.ValidationResult;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import com.raywenderlich.android.redditclone.database.dao.RedditKeysDao;
import com.raywenderlich.android.redditclone.database.dao.RedditKeysDao_Impl;
import com.raywenderlich.android.redditclone.database.dao.RedditPostsDao;
import com.raywenderlich.android.redditclone.database.dao.RedditPostsDao_Impl;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public final class RedditDatabase_Impl extends RedditDatabase {
  private volatile RedditPostsDao _redditPostsDao;

  private volatile RedditKeysDao _redditKeysDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `redditPosts` (`key` TEXT NOT NULL, `title` TEXT NOT NULL, `score` INTEGER NOT NULL, `author` TEXT NOT NULL, `commentCount` INTEGER NOT NULL, PRIMARY KEY(`key`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `redditKeys` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `after` TEXT, `before` TEXT)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '90f6dc30de6650c2d22f2a15c5aad81f')");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `redditPosts`");
        _db.execSQL("DROP TABLE IF EXISTS `redditKeys`");
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onDestructiveMigration(_db);
          }
        }
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      public void onPreMigrate(SupportSQLiteDatabase _db) {
        DBUtil.dropFtsSyncTriggers(_db);
      }

      @Override
      public void onPostMigrate(SupportSQLiteDatabase _db) {
      }

      @Override
      protected RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsRedditPosts = new HashMap<String, TableInfo.Column>(5);
        _columnsRedditPosts.put("key", new TableInfo.Column("key", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRedditPosts.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRedditPosts.put("score", new TableInfo.Column("score", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRedditPosts.put("author", new TableInfo.Column("author", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRedditPosts.put("commentCount", new TableInfo.Column("commentCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysRedditPosts = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesRedditPosts = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoRedditPosts = new TableInfo("redditPosts", _columnsRedditPosts, _foreignKeysRedditPosts, _indicesRedditPosts);
        final TableInfo _existingRedditPosts = TableInfo.read(_db, "redditPosts");
        if (! _infoRedditPosts.equals(_existingRedditPosts)) {
          return new RoomOpenHelper.ValidationResult(false, "redditPosts(com.raywenderlich.android.redditclone.models.RedditPost).\n"
                  + " Expected:\n" + _infoRedditPosts + "\n"
                  + " Found:\n" + _existingRedditPosts);
        }
        final HashMap<String, TableInfo.Column> _columnsRedditKeys = new HashMap<String, TableInfo.Column>(3);
        _columnsRedditKeys.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRedditKeys.put("after", new TableInfo.Column("after", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRedditKeys.put("before", new TableInfo.Column("before", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysRedditKeys = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesRedditKeys = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoRedditKeys = new TableInfo("redditKeys", _columnsRedditKeys, _foreignKeysRedditKeys, _indicesRedditKeys);
        final TableInfo _existingRedditKeys = TableInfo.read(_db, "redditKeys");
        if (! _infoRedditKeys.equals(_existingRedditKeys)) {
          return new RoomOpenHelper.ValidationResult(false, "redditKeys(com.raywenderlich.android.redditclone.models.RedditKeys).\n"
                  + " Expected:\n" + _infoRedditKeys + "\n"
                  + " Found:\n" + _existingRedditKeys);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "90f6dc30de6650c2d22f2a15c5aad81f", "b1c3eb00ea7da59045661a43dc00fc5d");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "redditPosts","redditKeys");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `redditPosts`");
      _db.execSQL("DELETE FROM `redditKeys`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  public RedditPostsDao redditPostsDao() {
    if (_redditPostsDao != null) {
      return _redditPostsDao;
    } else {
      synchronized(this) {
        if(_redditPostsDao == null) {
          _redditPostsDao = new RedditPostsDao_Impl(this);
        }
        return _redditPostsDao;
      }
    }
  }

  @Override
  public RedditKeysDao redditKeysDao() {
    if (_redditKeysDao != null) {
      return _redditKeysDao;
    } else {
      synchronized(this) {
        if(_redditKeysDao == null) {
          _redditKeysDao = new RedditKeysDao_Impl(this);
        }
        return _redditKeysDao;
      }
    }
  }
}
