package io.github.droidkaigi.confsched2018.data.db

import android.arch.persistence.room.RoomDatabase
import io.github.droidkaigi.confsched2018.data.api.response.Response
import io.github.droidkaigi.confsched2018.data.api.response.mapper.toSessionEntities
import io.github.droidkaigi.confsched2018.data.api.response.mapper.toSessionSpeakerJoinEntities
import io.github.droidkaigi.confsched2018.data.api.response.mapper.toSpeakerEntities
import io.github.droidkaigi.confsched2018.data.db.dao.SessionDao
import io.github.droidkaigi.confsched2018.data.db.dao.SessionSpeakerJoinDao
import io.github.droidkaigi.confsched2018.data.db.dao.SpeakerDao
import io.github.droidkaigi.confsched2018.data.db.entity.RoomEntity
import io.github.droidkaigi.confsched2018.data.db.entity.SessionWithSpeakers
import io.github.droidkaigi.confsched2018.data.db.entity.SpeakerEntity
import io.reactivex.Flowable
import javax.inject.Inject

class SessionRoomDatabase @Inject constructor(
        private val database: RoomDatabase,
        private val sessionDao: SessionDao,
        private val speakerDao: SpeakerDao,
        private val sessionSpeakerJoinDao: SessionSpeakerJoinDao
) : SessionDatabase {
    override fun getAllSessions(): Flowable<List<SessionWithSpeakers>> {
        return sessionSpeakerJoinDao.getAllSessions()
    }

    override fun getAllSpeaker(): Flowable<List<SpeakerEntity>> {
        return speakerDao.getAllSpeaker()
    }

    override fun getAllRoom(): Flowable<List<RoomEntity>> {
        return sessionDao.getAllRoom()
    }

    override fun save(response: Response) {
        database.runInTransaction {
            speakerDao.clearAndInsert(response.speakers.toSpeakerEntities())
            val sessions = response.sessions
            val sessionEntities = sessions.toSessionEntities(response.categories, response.rooms)
            sessionDao.clearAndInsert(sessionEntities)
            sessionSpeakerJoinDao.insert(sessions.toSessionSpeakerJoinEntities())
        }
    }
}
