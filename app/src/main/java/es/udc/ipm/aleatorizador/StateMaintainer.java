package es.udc.ipm.aleatorizador;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.HashMap;

/**
 * Created by AguilaSamsung on 08/06/2017.
 */

public class StateMaintainer {

    protected final String TAG = getClass().getSimpleName();

    private final String mStateMaintainerTag;
    private final WeakReference<FragmentManager> mFragmentManager;
    private StateMngFragment mStateMaintainerFrag;
    private boolean mIsRecreating;

    public StateMaintainer(FragmentManager fragmentManager, String stateMaintainerTAG) {
        mFragmentManager = new WeakReference<>(fragmentManager);
        mStateMaintainerTag = stateMaintainerTAG;
    }

    public boolean firstTimeIn() {
        try {

            mStateMaintainerFrag = (StateMngFragment)
                    mFragmentManager.get().findFragmentByTag(mStateMaintainerTag);

            /* New maintainer */
            if (mStateMaintainerFrag == null) {
                Log.d(TAG, "Creating new RetainedFragment " + mStateMaintainerTag);
                mStateMaintainerFrag = new StateMngFragment();
                mFragmentManager.get().beginTransaction()
                        .add(mStateMaintainerFrag, mStateMaintainerTag).commit();
                mIsRecreating = false;
                return true;
            } else {
                Log.d(TAG, "Returning existent fragment " + mStateMaintainerTag);
                mIsRecreating = true;
                return false;
            }
        } catch (NullPointerException e) {
            Log.w(TAG, "Error firstTimeIn()");
            return false;
        }
    }

    public boolean wasRecreated() { return mIsRecreating; }


    public void put(String key, Object obj) {
        mStateMaintainerFrag.put(key, obj);
    }

    public void put(Object obj) {
        put(obj.getClass().getName(), obj);
    }

    public <T> T get(String key)  {
        return mStateMaintainerFrag.get(key);

    }

    public boolean hasKey(String key) {
        return mStateMaintainerFrag.get(key) != null;
    }

    public static class StateMngFragment extends Fragment {
        private HashMap<String, Object> mData = new HashMap<>();

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            /* Preserve the fragment */
            setRetainInstance(true);
        }

        public void put(String key, Object obj) {
            mData.put(key, obj);
        }

        public void put(Object object) {
            put(object.getClass().getName(), object);
        }

        public <T> T get(String key) {
            return (T) mData.get(key);
        }
    }

}

