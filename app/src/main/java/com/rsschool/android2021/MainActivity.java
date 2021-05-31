package com.rsschool.android2021;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity implements OnFirstFragmentDataListener, OnSecondFragmentDataListener{
    private int previousNumber = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openFirstFragment(previousNumber);
    }

    private void openFirstFragment(int previousNumber) {
        final Fragment firstFragment = FirstFragment.newInstance(previousNumber);
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, firstFragment);
        transaction.commit();
    }

    private void openSecondFragment(int min, int max) {
        final Fragment secondFragment = SecondFragment.newInstance(min, max);
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.addToBackStack(null);
        transaction.replace(R.id.container, secondFragment);
        transaction.commit();
    }

    @Override
    public void onSetPreviousNumber(int previousNumber) {
        this.previousNumber = previousNumber;
    }

    @Override
    public int onGetPreviousNumber() {
        return previousNumber;
    }

    @Override
    public void onOpenSecondFragment(int min, int max) {
        openSecondFragment(min, max);
    }

    @Override
    public void onOpenFirstFragment() {
        getSupportFragmentManager().popBackStack();
    }
}
