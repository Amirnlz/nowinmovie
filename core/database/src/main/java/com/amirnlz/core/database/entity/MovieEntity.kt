package com.amirnlz.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "movie")
data class MovieEntity(
  @PrimaryKey val id: Long,
  val title: String,
  val voteAverage: Double,
  val releaseDate: LocalDate?,
  val posterPath: String,
)
