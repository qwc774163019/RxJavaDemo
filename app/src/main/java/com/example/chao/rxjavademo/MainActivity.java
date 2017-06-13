package com.example.chao.rxjavademo;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button btn;
    Button btn1;
    Button btn2;
    Button btn3;
    Button btn4;
    Button btn5;
    Button btn6;
    Button btn7;
    Button btn8;
    Button btn9;
    Button btn10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn= (Button) findViewById(R.id.btn);
        btn.setOnClickListener(this);
        btn1= (Button) findViewById(R.id.btn1);
        btn1.setOnClickListener(this);
        btn2= (Button) findViewById(R.id.btn2);
        btn2.setOnClickListener(this);
        btn3= (Button) findViewById(R.id.btn3);
        btn3.setOnClickListener(this);
        btn4= (Button) findViewById(R.id.btn4);
        btn4.setOnClickListener(this);
        btn5= (Button) findViewById(R.id.btn5);
        btn5.setOnClickListener(this);
        btn6= (Button) findViewById(R.id.btn6);
        btn6.setOnClickListener(this);
        btn7= (Button) findViewById(R.id.btn7);
        btn7.setOnClickListener(this);
        btn8= (Button) findViewById(R.id.btn8);
        btn8.setOnClickListener(this);
        btn9= (Button) findViewById(R.id.btn9);
        btn9.setOnClickListener(this);
        btn10= (Button) findViewById(R.id.btn10);
        btn10.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn:
                Flowable<String> flowable=Flowable.create(new FlowableOnSubscribe<String>() {
                    @Override
                    public void subscribe(FlowableEmitter<String> e) throws Exception {
                        e.onNext("Hello Rxjava");
                        e.onComplete();
                    }
                }, BackpressureStrategy.BUFFER);

                Subscriber subscriber=new Subscriber<String>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        Log.i("QWC","onSubscribe");
                        s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(String o) {
                        Log.i("QWC",o);
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {
                        Log.i("QWC","onComplete");
                    }
                };
                flowable.subscribe(subscriber);
                break;

            case R.id.btn1:
                Flowable<String> flowable1=Flowable.just("Hello Rxjava 2");
                Consumer consumer=new Consumer<String>(){

                    @Override
                    public void accept(String s) throws Exception {
                        Log.i("QWC",s);
                    }
                };
                flowable1.subscribe(consumer);
                break;
            case R.id.btn2:
                Flowable.just("Hello Rxjava 3").subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.i("QWC",s);
                    }
                });
                break;
            case R.id.btn3:
                Flowable.just("map").map(new Function<String, String>() {
                    @Override
                    public String apply(String s) throws Exception {
                        return s+"qiwenchao";
                    }
                }).subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.i("QWC",s);
                    }
                });
                break;
            case R.id.btn4:
                Flowable.just("map1").map(new Function<String, Integer>() {
                    @Override
                    public Integer apply(String s) throws Exception {
                        return s.hashCode();
                    }
                }).map(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer integer) throws Exception {
                        return integer.toString();
                    }
                }).subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.i("QWC",s);
                    }
                });
                break;
            case R.id.btn5:
                List<Integer> list = new ArrayList<>();
                list.add(10);
                list.add(1);
                list.add(5);
                Flowable.fromIterable(list).subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        Log.i("QWC","onSubscribe");
                        s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.i("QWC",integer+"");
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
                break;
            case R.id.btn6:
                List<Integer> list1 = new ArrayList<>();
                list1.add(10);
                list1.add(1);
                list1.add(5);
                Flowable.just(list1).flatMap(new Function<List<Integer>, Publisher<Integer>>() {
                    @Override
                    public Publisher<Integer> apply(List<Integer> integers) throws Exception {
                        return Flowable.fromIterable(integers);
                    }
                }).subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.i("QWC",String.valueOf(integer));
                    }
                });
                break;
            case R.id.btn7:
                Flowable.fromArray(1,2,3,-4,-1,8)
                        .filter(new Predicate<Integer>() {
                            @Override
                            public boolean test(Integer integer) throws Exception {
                                return integer>3;
                            }
                        })
                        .subscribe(new Consumer<Integer>() {
                            @Override
                            public void accept(Integer integer) throws Exception {
                                Log.i("QWC",String.valueOf(integer));
                            }
                        });
                break;
            case R.id.btn8:
                Flowable.fromArray(1,2,3,4)
                        .take(2)
                        .subscribe(new Consumer<Integer>() {
                            @Override
                            public void accept(Integer integer) throws Exception {
                                Log.i("QWC",String.valueOf(integer));
                            }
                        });
                break;
            case R.id.btn9:
                Flowable.just(1,2,34,4)
                        .doOnNext(new Consumer<Integer>() {
                            @Override
                            public void accept(Integer integer) throws Exception {
                                Log.i("QWC","保存："+integer);
                            }
                        })
                        .subscribe(new Consumer<Integer>() {
                            @Override
                            public void accept(Integer integer) throws Exception {
                                Log.i("QWC",String.valueOf(integer));
                            }
                        });
                break;
            case R.id.btn10:
                Flowable.create(new FlowableOnSubscribe<String>() {
                    @Override
                    public void subscribe(FlowableEmitter<String> e) throws Exception {
                        e.onNext("将会在3秒后显示");
                        SystemClock.sleep(3000);
                        e.onNext("qiwenchao");
                        e.onComplete();
                    }
                },BackpressureStrategy.BUFFER)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<String>() {
                            @Override
                            public void accept(String s) throws Exception {
                                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                            }
                        });

                break;
        }
    }
}
