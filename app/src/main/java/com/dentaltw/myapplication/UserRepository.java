package com.dentaltw.myapplication;

import android.arch.lifecycle.LiveData;
import retrofit2.Response;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.util.concurrent.Executor;

/**
 * Created by Scott on 2017/6/2.
 */

@Singleton
public class UserRepository {
    private final Webservice webservice;
    private final UserDao userDao;
    private final Executor executor;

    @Inject
    public UserRepository(Webservice webservice, UserDao userDao, Executor executor) {
        this.webservice = webservice;
        this.userDao = userDao;
        this.executor = executor;
    }

    public LiveData<User> getUser(String userId) {
        refreshUser(userId);
        return userDao.load(userId);
    }

    private void refreshUser(final String userId) {
        executor.execute(() -> {
            //boolean userExists = userDao.hasUser(FRESH_TIMEOUT);
            boolean userExists = false;
            if (!userExists) {
                Response response = null;
                try {
                    response = webservice.getUser(userId).execute();
                    userDao.save((User)response.body());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
