package kelvin.link.dontborderme_app;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;


@Dao
public interface EventRoomDAO {
    @Insert
    void insert(EventRoom event);

    @Update
    void update(EventRoom event);

    @Query("DELETE FROM user_event_room WHERE uid = :uid AND event_id = :event_id")
    void delete(String uid, Integer event_id);

    @Query("DELETE FROM user_event_room")
    void deleteAllEvents();

    @Query("SELECT * FROM user_event_room ORDER BY event_id")
    LiveData<List<EventRoom>> getAllEvents();

}
