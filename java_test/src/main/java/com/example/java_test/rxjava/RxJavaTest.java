package com.example.java_test.rxjava;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.logging.Logger;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 类描述:
 * 创建人: macroz
 * 创建时间: 2019/5/1 7:26 PM
 * 修改人: macroz
 * 修改时间: 2019/5/1 7:26 PM
 * 修改备注:
 */
public class RxJavaTest {
    public static void main(String[] args) {
        String[] strings = {"aaa", "bbb", "ccc"};


        Flowable.just("aaa")
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .blockingSubscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        System.out.println(s);
                        System.out.println("接收 Thread :" + Thread.currentThread().getName());
                    }
                });

//        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
//                emitter.onNext(1);
//                emitter.onNext(2);
//                emitter.onNext(3);
//                System.out.println("发送 Thread :" + Thread.currentThread().getName());
//            }
//        });
//        observable
//                .subscribeOn(Schedulers.computation())
//                .observeOn(Schedulers.single());
//        observable.subscribe(new Consumer<Integer>() {
//            @Override
//            public void accept(Integer integer) throws Exception {
//                System.out.println(integer);
//                System.out.println("接收 Thread :" + Thread.currentThread().getName());
//            }
//        });

//        Flowable.create(new FlowableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
//                System.out.println("requested: " + emitter.requested());
//                System.out.println("cur Thread :" + Thread.currentThread().getName());
//                for (int i = 0; i < 129; i++) {
//                    System.out.println("emitter: " + i);
//                    emitter.onNext(i);
//                }
//            }
//        }, BackpressureStrategy.ERROR)
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(Schedulers.newThread())
//                .subscribe(new Subscriber<Integer>() {
//                    @Override
//                    public void onSubscribe(Subscription s) {
////                s.request(1);
//                        System.out.println("onSubscribe");
//                        System.out.println("cur Thread :" + Thread.currentThread().getName());
//                        System.out.println("订阅线程 :" + Thread.currentThread().getName());
//                    }
//
//                    @Override
//                    public void onNext(Integer integer) {
//                        System.out.println("接收线程 :" + Thread.currentThread().getName());
//                        System.out.println(integer);
//                    }
//
//                    @Override
//                    public void onError(Throwable t) {
//                        System.out.println("onError:" + t.toString());
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        System.out.println("onComplete");
//                    }
//                });
    }
}
