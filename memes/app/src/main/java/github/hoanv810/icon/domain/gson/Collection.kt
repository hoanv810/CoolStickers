package github.hoanv810.icon.domain.gson

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.firebase.firestore.ServerTimestamp
import java.util.*

/**
 * @author hoanv
 * @since 1/23/17
 */

@IgnoreExtraProperties
class Collection() : Parcelable {

    var id: Int = 0
    lateinit var coverPageUrl: String
    @ServerTimestamp
    lateinit var createdDate: Date
    var enabled: Boolean = true
    lateinit var directory: String
    lateinit var title: String

    constructor(set: Collection) : this() {
        this.id = set.id
        this.coverPageUrl = set.coverPageUrl
        this.createdDate = set.createdDate
        this.enabled = set.enabled
        this.directory = set.directory
        this.title = set.title
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Collection

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id
    }

    // <editor-fold defaultstate="collapsed" desc="parcelable-implementation">
    constructor(parcel: Parcel) : this() {
        id = parcel.readInt()
        coverPageUrl = parcel.readString()
        createdDate = Date(parcel.readLong())
        enabled = parcel.readByte() != 0.toByte()
        directory = parcel.readString()
        title = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(coverPageUrl)
        parcel.writeLong(createdDate.time)
        parcel.writeByte(if (enabled) 1 else 0)
        parcel.writeString(directory)
        parcel.writeString(title)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Collection> {
        override fun createFromParcel(parcel: Parcel): Collection {
            return Collection(parcel)
        }

        override fun newArray(size: Int): Array<Collection?> {
            return arrayOfNulls(size)
        }
    }
    // </editor-fold>
}
