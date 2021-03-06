package id.gits.gitsmvvmkotlin.data.source.local

import android.support.annotation.VisibleForTesting
import id.gits.gitsmvvmkotlin.data.model.Movie
import id.gits.gitsmvvmkotlin.data.source.GitsDataSource
import id.gits.gitsmvvmkotlin.data.source.local.movie.MovieDao
import id.gits.gitsmvvmkotlin.util.dbhelper.AppExecutors

/**
 * Created by irfanirawansukirman on 26/01/18.
 */

class GitsLocalDataSource private constructor(val appExecutors: AppExecutors,
                                              val movieDao: MovieDao) : GitsDataSource {
    override fun getMoviesUpComing(callback: GitsDataSource.GetMoviesCallback) {
        appExecutors.diskIO.execute {
            val movies = movieDao.getAllMovies()

            appExecutors.mainThread.execute {
                if (movies.isEmpty()){
                    callback.onError("Data movie tidak ditemukan")
                } else {
                    callback.onMoviesLoaded(movies)
                }
            }
        }
    }

    override fun getMovies(callback: GitsDataSource.GetMoviesCallback) {
        appExecutors.diskIO.execute {
            val movies = movieDao.getAllMovies()

            appExecutors.mainThread.execute {
                if (movies.isEmpty()){
                    callback.onError("Data movie tidak ditemukan")
                } else {
                    callback.onMoviesLoaded(movies)
                }
            }
        }
    }

    override fun getMovieById(movieId: Int, callback: GitsDataSource.GetMoviesByIdCallback) {
        appExecutors.diskIO.execute {
            val movies = movieDao.getMovieById(movieId)

            appExecutors.mainThread.execute {
                if (movies == null){
                    callback.onError("Data movie tidak ditemukan")
                } else {
                    callback.onMovieLoaded(movies)
                }
            }
        }
    }

    override fun saveMovie(movie: Movie) {
        appExecutors.diskIO.execute {
            movieDao.insertMovie(movie)
        }
    }

    override fun remoteMovie(isRemote: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        private var INSTANCE: GitsLocalDataSource? = null

        @JvmStatic
        fun getInstance(appExecutors: AppExecutors, movieDao: MovieDao): GitsLocalDataSource {
            if (INSTANCE == null) {
                synchronized(GitsLocalDataSource::javaClass) {
                    INSTANCE = GitsLocalDataSource(appExecutors, movieDao)
                }
            }
            return INSTANCE!!
        }

        @VisibleForTesting
        fun clearInstance() {
            INSTANCE = null
        }
    }
}