package com.tastegood.distribute.user;

import com.tastegood.distribute.gobal.BasePresenter;
import com.tastegood.distribute.gobal.BaseView;

/**
 * Created by surandy on 2016/10/18.
 */

public interface UserContract {

    interface UserView extends BaseView<Presenter> {

        void showLoadingView();

        void hideLoadingView();

        void showBusinessError(String businessErrorMessage);

    }

    interface LoginView extends UserView {

        void showUserNameError();

        void showAccountForbided();

        void showPasswordError();

        void goMainActivity();

    }

    interface LogoutView extends UserView {

        void goLoginActivity();

        void showLogoutSuccessToast();

        void showLogoutFailToast();

    }

    interface Presenter extends BasePresenter {

        void login(String username, String password);

        void logout(String username);

    }

}
