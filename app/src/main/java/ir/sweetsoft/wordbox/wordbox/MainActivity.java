package ir.sweetsoft.wordbox.wordbox;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.activeandroid.query.Select;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import java.util.List;

import javax.xml.datatype.Duration;

import common.SweetDate;
import ir.sweetsoft.wordbox.wordbox.Model.Word;

public class MainActivity extends BaseWordBoxActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    private TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        try {
            if(shouldAskPermission())
                requestFileAccessPermission(Constants.PERMISSION_FILEACCESS);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }




    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        if (requestCode==Constants.PERMISSION_FILEACCESS) {{
            // If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                try {
//                    DBTools.Export(Environment.getExternalStorageDirectory().getAbsolutePath()+"/wordbox", "wb"+System.currentTimeMillis()+".db", getApplicationContext(), Constants.DBNAME);
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }

            } else {
                AlertDialog.Builder ab=new AlertDialog.Builder(this);
                ab.setMessage("Please give me access to storage in order to make backup");
                ab.setPositiveButton("OK",null);
                ab.show();
            }
            return;
        }
        }
    }
    protected boolean shouldAskPermission() {
        return (Build.VERSION.SDK_INT > 22);
    }
    protected void requestFileAccessPermission(int RequestCode)
    {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                RequestCode);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

            if (id == R.id.action_backup) {
                int wordCount=0;
                List<Word> words=new Select().from(Word.class).execute();
                if(words!=null)
                    wordCount=words.size();
                String FileName="WordBox"+ SweetDate.Time2String(System.currentTimeMillis(),true)+"- "+wordCount+"Words.db";
                DBTools.Export(Environment.getExternalStorageDirectory().getAbsolutePath()+"/wordbox", FileName, getApplicationContext(), Constants.DBNAME);
                new LovelyStandardDialog(this, LovelyStandardDialog.ButtonLayout.VERTICAL)
                        .setTopColorRes(R.color.colorOrange)
                        .setButtonsColorRes(R.color.colorBlue)
                        .setIcon(R.drawable.backup)
                        .setTitle(R.string.backupsuccessfulltitle)
                        .setMessage(R.string.backupsuccessfull)
                        .setPositiveButton(R.string.ok, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                            }
                        })
                        .show();
                return true;
                }
            if (id == R.id.action_restore) {
                Intent intent = new Intent()
                        .setType("*/*")
                        .setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select the Backup File"), Constants.REQUEST_OPENFILE);
                return true;
            }


        if (id == R.id.action_about) {
            new LovelyStandardDialog(this, LovelyStandardDialog.ButtonLayout.VERTICAL)
                    .setTopColorRes(R.color.colorGreen)
                    .setButtonsColorRes(R.color.colorBlue)
                    .setIcon(R.drawable.code)
                    .setTitle(R.string.abouttitle)
                    .setMessage(R.string.about)
                    .setPositiveButton(R.string.ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        }
                    })
                    .show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }





    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Manage";
                case 1:
                    return "Review";
            }
            return "Manage";
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_ADDWORD || requestCode==Constants.REQUEST_EDITWORD || requestCode==Constants.REQUEST_REVIEW) {
            this.recreate();
            Log.d("recreating","--");
        }
        else if(requestCode == Constants.REQUEST_OPENFILE && resultCode==RESULT_OK) {
            Uri selectedfile = data.getData();
                DBTools.Import(selectedfile.getPath(), getApplicationContext(), Constants.DBNAME);
            new LovelyStandardDialog(this, LovelyStandardDialog.ButtonLayout.VERTICAL)
                    .setTopColorRes(R.color.colorOrange)
                    .setButtonsColorRes(R.color.colorBlue)
                    .setIcon(R.drawable.restore)
                    .setTitle(R.string.restoresuccessfulltitle)
                    .setMessage(R.string.restoresuccessfull)
                    .setPositiveButton(R.string.ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent mStartActivity = new Intent(getApplicationContext(), MainActivity.class);
                            int mPendingIntentId = 123456;
                            PendingIntent mPendingIntent = PendingIntent.getActivity(getApplicationContext(), mPendingIntentId,    mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
                            AlarmManager mgr = (AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                            mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
                            System.exit(0);
                        }
                    })
                    .show();
        }
    }
}
