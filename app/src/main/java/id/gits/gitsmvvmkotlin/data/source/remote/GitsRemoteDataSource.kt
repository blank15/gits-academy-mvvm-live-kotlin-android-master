package id.gits.gitsmvvmkotlin.data.source.remote

import id.gits.gitsmvvmkotlin.data.model.Movie
import id.gits.gitsmvvmkotlin.data.source.GitsDataSource
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by irfanirawansukirman on 26/01/18.
 */

object GitsRemoteDataSource : GitsDataSource {


    private var apiService = GitsApiService.create()


    override fun getMovies(callback: GitsDataSource.GetMoviesCallback) {
        apiService
                .getMovies()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ results ->
                    run {
                        if (results.results!!.isNotEmpty()) {
                            callback.onMoviesLoaded(results.results)
                        } else {
                            callback.onDataNotAvailable()
                        }
                    }
                }, { error ->
                    callback.onError(error.message)
                })
    }

    override fun getMoviesUpComing(callback: GitsDataSource.GetMoviesCallback) {
        apiService
                .getMoviesUpComing()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    results ->
                    run {
                        if(results.results!!.isNotEmpty()){
                            callback.onMoviesLoaded(results.results)
                        }else{
                            callback.onDataNotAvailable()
                        }
                    }
                })
    }

    override fun getMovieById(movieId: Int, callback: GitsDataSource.GetMoviesByIdCallback) {

    }

    override fun saveMovie(movie: Movie) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun remoteMovie(isRemote: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}