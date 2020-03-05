package com.anjad.flickrphotoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.anjad.flickrphotoapp.models.AllPhotos;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;


public class MainActivity extends AppCompatActivity implements PhotoAdapter.ListItemClickListener {

    //Use the following Flickr API link to allow users to search Flickr photos (add the search term into tags parameter)
    // https://www.flickr.com/services/feeds/docs/photos_public/ - read this for all options
    // https://api.flickr.com/services/feeds/photos_public.gne?format=json&nojsoncallback=1&tags=Vienna

    @BindView(R.id.et_search_tag)
    EditText mEtSearchTag;

    @BindView(R.id.rv_result)
    RecyclerView mRvResult;

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    private static final String TAG = "MainActivity";
    private AllPhotos mAllPhotos;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private PhotoAdapter mPhotoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initRecyclerView();
    }

    private void initRecyclerView() {
        int orientation = this.getResources().getConfiguration().orientation;
        int columns;
        if(orientation == Configuration.ORIENTATION_PORTRAIT){
            columns =  3;
        }
        else{
            columns = 5;
        }
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, columns);
        mRvResult.setLayoutManager(gridLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRvResult.getContext(),
                gridLayoutManager.getOrientation());
        mRvResult.addItemDecoration(dividerItemDecoration);

        mPhotoAdapter = new PhotoAdapter(this, mAllPhotos, this);
        mRvResult.setAdapter(mPhotoAdapter);
    }

    @OnClick(R.id.btn_search)
    void onSearchClick(View view){
        if(mEtSearchTag.getText() != null && !TextUtils.isEmpty(mEtSearchTag.getText().toString())){
            showProgressBar();
            searchFlickr(mEtSearchTag.getText().toString().trim());
        }
        else {
            hideProgressBar();
            Toast.makeText(this, "You must enter some search params", Toast.LENGTH_SHORT).show();
        }
    }

    private void showProgressBar(){
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar(){
        mProgressBar.setVisibility(View.GONE);
    }

    private void searchFlickr(String searchTag) {

        Map<String, String> filters = new HashMap<>();
        filters.put("format", "json");
        filters.put("nojsoncallback", "1");
        filters.put("tags", searchTag);

        Retrofit retrofit = RetrofitClient.getRetrofitClient();
        FlickrApi flickrApi = retrofit.create(FlickrApi.class);
        Single<AllPhotos> allPhotosSingle = flickrApi.getFlickrPhotos(filters);

        allPhotosSingle.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<AllPhotos>() {
            @Override
            public void onSubscribe(Disposable d) {
                mCompositeDisposable.add(d);
            }

            @Override
            public void onSuccess(AllPhotos allPhotos) {
                mAllPhotos = allPhotos;
                loadPhotos();
                hideProgressBar();
            }

            @Override
            public void onError(Throwable e) {
                hideProgressBar();
                Log.e(TAG, e.getMessage(), e);
                Toast.makeText(MainActivity.this, "Error getting data from Flickr", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void loadPhotos() {
        mPhotoAdapter = new PhotoAdapter(this, mAllPhotos, this);
        mRvResult.setAdapter(mPhotoAdapter);
    }

    @Override
    protected void onDestroy() {
        if(!mCompositeDisposable.isDisposed()){
            mCompositeDisposable.dispose();
        }
        super.onDestroy();
    }

    @Override
    public void onListItemClick(int position) {
        Intent intent = new Intent(MainActivity.this, FullScreenActivity.class);
        intent.putExtra("clickedPhoto", mAllPhotos.getPhotos().get(position).getLargePhotoLink());
        Log.d(TAG, "onListItemClick: " + mAllPhotos.getPhotos().get(position).getLargePhotoLink());
        startActivity(intent);
    }
}
