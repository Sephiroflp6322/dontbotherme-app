package kelvin.link.dontborderme_app;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = {EventRoom.class}, version = 1)
public abstract class EventRoomDatabase extends RoomDatabase {
    private static EventRoomDatabase instance;

    public abstract EventRoomDAO eventRoomDAO();

    public static synchronized EventRoomDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    EventRoomDatabase.class, "dontborderme_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void>{
        private EventRoomDAO eventDAO;

        private PopulateDbAsyncTask(EventRoomDatabase db){
            eventDAO = db.eventRoomDAO();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //TODO Should fetch data from webservice
            eventDAO.insert(new EventRoom("kelvin@gmail.com", 1, "Go shopping",
                    "Hongkong", "Description","s", null));
            eventDAO.insert(new EventRoom("kelvin@gmail.com", 2, "Go shopping",
                    "Macao", "Description","r", null));
            eventDAO.insert(new EventRoom("kelvin@gmail.com", 3, "Go shopping",
                    "Taiwan", "Description","r", null));

            return null;
        }
    }
}
