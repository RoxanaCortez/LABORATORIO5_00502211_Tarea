package com.roxyapps.roxana.clase.models

import android.os.Parcelable
import android.os.Parcel

data class Data_pokemon(
    val id: Int = 0,
    val pokemon: String = "N/A",
    val url: String = "N/A",
    val name: String = "N/A",
    val type: String = "N/A"
) : Parcelable {
    constructor(parcel: Parcel) : this(
        id=parcel.readInt(),
        pokemon=parcel.readString(),
        url=parcel.readString(),
        name=parcel.readString(),
        type=parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(pokemon)
        parcel.writeString(url)
        parcel.writeString(name)
        parcel.writeString(type)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Data_pokemon> {
        override fun createFromParcel(parcel: Parcel): Data_pokemon {
            return Data_pokemon(parcel)
        }

        override fun newArray(size: Int): Array<Data_pokemon?> {
            return arrayOfNulls(size)
        }
    }
}