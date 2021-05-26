package com.example.intervention_plombiers

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable
data class Intervention(
    var nom: String? ="",
    var date: String? ="",
    var filename: String? =""):Parcelable, Serializable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nom)
        parcel.writeString(date)
        parcel.writeString(filename)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Intervention> {
        private val serialVersionUid: Long=424242
        override fun createFromParcel(parcel: Parcel): Intervention {
            return Intervention(parcel)
        }

        override fun newArray(size: Int): Array<Intervention?> {
            return arrayOfNulls(size)
        }
    }

}