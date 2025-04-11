package com.amirnlz.core.database.util

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.time.LocalDate
import java.time.format.DateTimeFormatter

internal class Converters {
  private val formatter = DateTimeFormatter.ISO_LOCAL_DATE
  private val gson = Gson()

  @TypeConverter
  fun fromLocalDate(date: LocalDate?): String? {
    return date?.format(formatter)
  }

  @TypeConverter
  fun toLocalDate(dateString: String?): LocalDate? {
    return dateString?.let { LocalDate.parse(it, formatter) }
  }

  @TypeConverter
  fun fromStringList(strings: List<String>?): String? {
    return gson.toJson(strings)
  }

  @TypeConverter
  fun toStringList(string: String?): List<String>? {
    val listType = object : TypeToken<List<String>>() {}.type
    return gson.fromJson(string, listType)
  }
}
