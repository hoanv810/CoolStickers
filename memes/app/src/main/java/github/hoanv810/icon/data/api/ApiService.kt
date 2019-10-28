package github.hoanv810.icon.data.api

import github.hoanv810.icon.BuildConfig
import github.hoanv810.icon.domain.gson.Collection
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Url

/**
 * @author hoanv
 * @since 4/4/18.
 */
interface ApiService {

    @GET
    fun getCollections(@Url url: String = BuildConfig.COLLECTIONS_URL) : Flowable<List<Collection>>
}