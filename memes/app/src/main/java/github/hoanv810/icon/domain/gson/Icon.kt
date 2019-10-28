package github.hoanv810.icon.domain.gson

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.firebase.storage.FirebaseStorage
import com.google.gson.annotations.SerializedName
import github.hoanv810.icon.App
import github.hoanv810.icon.data.firestore.FirestoreModule

/**
 * @author hoanv
 * @since 8/2/18
 */
@IgnoreExtraProperties
class Icon() : Parcelable {

    lateinit var image: String

    @SerializedName("image_preview")
    lateinit var imagePreview: String

    /**
     * Absolute path to the image in internal storage
     */
    fun imagePath() = App.FILES_DIR + image

    fun previewPath() = App.FILES_DIR + imagePreview

    fun reference() = FirebaseStorage.getInstance(FirestoreModule.STORAGE_PREFIX).reference.child(image)

    // <editor-fold defaultstate="collapsed" desc="parcelable-initialization">
    constructor(parcel: Parcel) : this() {
        image = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(image)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Icon> {
        override fun createFromParcel(parcel: Parcel): Icon {
            return Icon(parcel)
        }

        override fun newArray(size: Int): Array<Icon?> {
            return arrayOfNulls(size)
        }
    }
    // </editor-fold>
}