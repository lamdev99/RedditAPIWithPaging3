package com.raywenderlich.android.redditclone.database.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.raywenderlich.android.redditclone.models.RedditKeys;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@SuppressWarnings({"unchecked", "deprecation"})
public final class RedditKeysDao_Impl implements RedditKeysDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<RedditKeys> __insertionAdapterOfRedditKeys;

  public RedditKeysDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfRedditKeys = new EntityInsertionAdapter<RedditKeys>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `redditKeys` (`id`,`after`,`before`) VALUES (nullif(?, 0),?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, RedditKeys value) {
        stmt.bindLong(1, value.getId());
        if (value.getAfter() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getAfter());
        }
        if (value.getBefore() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getBefore());
        }
      }
    };
  }

  @Override
  public Object saveRedditKeys(final RedditKeys redditKey, final Continuation<? super Unit> p1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfRedditKeys.insert(redditKey);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, p1);
  }

  @Override
  public Object getRedditKeys(final Continuation<? super List<RedditKeys>> p0) {
    final String _sql = "select * from redditKeys order by id desc";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<RedditKeys>>() {
      @Override
      public List<RedditKeys> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfAfter = CursorUtil.getColumnIndexOrThrow(_cursor, "after");
          final int _cursorIndexOfBefore = CursorUtil.getColumnIndexOrThrow(_cursor, "before");
          final List<RedditKeys> _result = new ArrayList<RedditKeys>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final RedditKeys _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpAfter;
            _tmpAfter = _cursor.getString(_cursorIndexOfAfter);
            final String _tmpBefore;
            _tmpBefore = _cursor.getString(_cursorIndexOfBefore);
            _item = new RedditKeys(_tmpId,_tmpAfter,_tmpBefore);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, p0);
  }
}
