package com.example.mad_app090_p11

class PersonDbTableData {
    companion object{
        const val TABLE_NAME = "persons"
        const val COLUMN_ID = "id"
        const val COLUMN_PERSON_NAME = "persons_name"
        const val COLUMN_PERSON_EMAIL_ID = "persons_email_id"
        const val COLUMN_PERSON_PHONE_NO = "persons_phone_no"
        const val COLUMN_PERSON_ADDRESS = "persons_address"
        const val COLUMN_PERSON_GPS_LAT = "persons_lat"
        const val COLUMN_PERSON_GPS_LONG = "persons_long"


        val CREATE_TABLE = ("CREATE TABLE "+ TABLE_NAME + "("
                + COLUMN_ID + " TEXT PRIMARY KEY,"
                + COLUMN_PERSON_NAME + " TEXT,"
                + COLUMN_PERSON_EMAIL_ID + " TEXT,"
                + COLUMN_PERSON_PHONE_NO + " TEXT,"
                + COLUMN_PERSON_ADDRESS + " TEXT,"
                + COLUMN_PERSON_GPS_LAT + " TEXT,"
                + COLUMN_PERSON_GPS_LONG + " TEXT"
                + ")")
    }
}