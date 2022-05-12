package hu.bme.aut.silentmap2_1_0.data.contact.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "contact")
@Parcelize
data class Contact (
    @PrimaryKey
    var id: Int,
    var name: String = "Name",
    var number: String = "+3620123456",
):Parcelable