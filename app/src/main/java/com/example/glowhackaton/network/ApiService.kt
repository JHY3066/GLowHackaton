package com.example.glowhackaton.network

import android.os.Parcel
import android.os.Parcelable
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

data class DataModel(
    val name: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DataModel> {
        override fun createFromParcel(parcel: Parcel): DataModel {
            return DataModel(parcel)
        }

        override fun newArray(size: Int): Array<DataModel?> {
            return arrayOfNulls(size)
        }
    }
}
data class YourDataModel(
    val name: String,
    val telephone: String,
    val address: String,
    val workingHour: String,
    val information: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(telephone)
        parcel.writeString(address)
        parcel.writeString(workingHour)
        parcel.writeString(information)

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DataModel> {
        override fun createFromParcel(parcel: Parcel): DataModel {
            return DataModel(parcel)
        }

        override fun newArray(size: Int): Array<DataModel?> {
            return arrayOfNulls(size)
        }
    }
}

data class Shop (
    val name: String,
    val type: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeInt(type)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DataModel> {
        override fun createFromParcel(parcel: Parcel): DataModel {
            return DataModel(parcel)
        }

        override fun newArray(size: Int): Array<DataModel?> {
            return arrayOfNulls(size)
        }
    }
}

data class Summary (
    val name: String,
    val telephone: String,
    val address: String,
    val workingHour: String,
    val briefExplanation: String,
    val star: Long
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readLong()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(telephone)
        parcel.writeString(address)
        parcel.writeString(workingHour)
        parcel.writeString(briefExplanation)
        parcel.writeLong(star)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DataModel> {
        override fun createFromParcel(parcel: Parcel): DataModel {
            return DataModel(parcel)
        }

        override fun newArray(size: Int): Array<DataModel?> {
            return arrayOfNulls(size)
        }
    }
}
data class Detail(
    val name: String,
    val price: Int,
    val comment: String,
    val starPoint: Long
)


interface Main {
    @POST("/market")
    fun sendButtonClick(): Call<Void>

    @GET("/market")
    fun getDataList(): Call<List<DataModel>>
}
interface Search {
    @GET("/market/search")
    fun getDataList(): Call<List<YourDataModel>>
    @POST("/market/search")
    fun sendMarketName(@Body requestBody: RequestBody): Call<String>

}

interface Select {
    @POST("/shop/all")
    fun sendDataAll(@Body requestBody: RequestBody): Call<List<DataModel>>
    @GET("/shop/all")
    fun getDataAll(): Call<List<DataModel>>
    @POST("/shop/select")
    fun sendDataSelect(@Query("name") name: String, @Query("type") type: Int): Call<List<Shop>>
    @GET("/shop/select")
    fun getDataSelect(): Call<List<DataModel>>
}

interface Sum {
    @POST("/shop/summarized")
    fun sendDataSum(@Body requestBody: RequestBody): Call<List<DataModel>>
    @GET("/shop/summarized")
    fun getDataSum(): Call<List<Summary>>
    @POST("/shop/detail")
    fun sendDataDetail(@Body requestBody: RequestBody) : Call<List<DataModel>>
    @GET("/shop/detail")
    fun getDataDetail(): Call<List<Detail>>
}