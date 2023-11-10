package com.example.mad_app090_p11

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "persons_db"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        if (db != null) {
            db.execSQL(PersonDbTableData.CREATE_TABLE)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (db != null) {
            db.execSQL("DROP TABLE IF EXISTS " + PersonDbTableData.TABLE_NAME)
        }
        onCreate(db)
    }

    fun insertPerson(person: PersonListCardModel): Long {
        val db = this.writableDatabase
        val id = db.insert(PersonDbTableData.TABLE_NAME, null, getValues(person))
        db.close()
        return id
    }

    private fun getValues(person: PersonListCardModel): ContentValues {
        val values = ContentValues()
        values.put(PersonDbTableData.COLUMN_ID, person.id)
        values.put(PersonDbTableData.COLUMN_PERSON_NAME, person.name)
        values.put(PersonDbTableData.COLUMN_PERSON_EMAIL_ID, person.emailld)
        values.put(PersonDbTableData.COLUMN_PERSON_PHONE_NO, person.phoneNo)
        values.put(PersonDbTableData.COLUMN_PERSON_GPS_LAT, person.latitude)
        values.put(PersonDbTableData.COLUMN_PERSON_GPS_LONG, person.longitude)

        return values
    }

    fun getPerson(id: String): PersonListCardModel? {
        val db = this.readableDatabase
        val cursor = db.query(
            PersonDbTableData.TABLE_NAME,
            arrayOf(
                PersonDbTableData.COLUMN_ID,
                PersonDbTableData.TABLE_NAME,
                PersonDbTableData.COLUMN_PERSON_EMAIL_ID,
                PersonDbTableData.COLUMN_PERSON_PHONE_NO,
                PersonDbTableData.COLUMN_PERSON_ADDRESS,
                PersonDbTableData.COLUMN_PERSON_GPS_LAT,
                PersonDbTableData.COLUMN_PERSON_GPS_LONG
            ),
            PersonDbTableData.COLUMN_ID + "=?",
            arrayOf(id),
            null,
            null,
            null,
            null
        ) ?: return null
        cursor.moveToFirst()
        if (cursor.count == 0) {
            return null
        }
        val person = getPerson(cursor)
        cursor.close()
        return person
    }

    private fun getPerson(cursor: Cursor): PersonListCardModel {
        return PersonListCardModel(
            cursor.getString(cursor.getColumnIndexOrThrow(PersonDbTableData.COLUMN_ID)),
            cursor.getString(cursor.getColumnIndexOrThrow(PersonDbTableData.TABLE_NAME)),
            cursor.getString(cursor.getColumnIndexOrThrow(PersonDbTableData.COLUMN_PERSON_EMAIL_ID)),
            cursor.getString(cursor.getColumnIndexOrThrow(PersonDbTableData.COLUMN_PERSON_PHONE_NO)),
            cursor.getString(cursor.getColumnIndexOrThrow(PersonDbTableData.COLUMN_PERSON_ADDRESS)),
            cursor.getDouble(cursor.getColumnIndexOrThrow(PersonDbTableData.COLUMN_PERSON_GPS_LAT)),
            cursor.getDouble(cursor.getColumnIndexOrThrow(PersonDbTableData.COLUMN_PERSON_GPS_LONG))
        )
    }

    val allPersons: ArrayList<PersonListCardModel>
        get() {
            val persons = ArrayList<PersonListCardModel>()
            val selectQuery =
                " SELECT * FROM " + PersonDbTableData.TABLE_NAME + " ORDER BY " + PersonDbTableData.COLUMN_PERSON_NAME + " DESC"
            val db = this.writableDatabase
            val cursor = db.rawQuery(selectQuery, null)
            if (cursor.moveToFirst()) {
                do {
                    persons.add(getPerson(cursor))
                } while (cursor.moveToFirst())
            }
            db.close()
            return persons
        }
    val personsCount: Int
        get() {
            val countQuery = "SELECT * FROM " + PersonDbTableData.TABLE_NAME
            val db = this.readableDatabase
            val cursor = db.rawQuery(countQuery, null)
            val count = cursor.count
            cursor.close()
            return count
        }

    fun updatePerson(person: PersonListCardModel): Int {
        val db = this.writableDatabase
        return db.update(
            PersonDbTableData.TABLE_NAME,
            getValues(person),
            PersonDbTableData.COLUMN_ID + " =?",
            arrayOf(person.id)
        )
    }

    fun deletePerson(person: PersonListCardModel) {
        val db = this.writableDatabase
        db.delete(
            PersonDbTableData.TABLE_NAME,
            PersonDbTableData.COLUMN_ID + " =?",
            arrayOf(person.id)
        )
        db.close()
    }
}