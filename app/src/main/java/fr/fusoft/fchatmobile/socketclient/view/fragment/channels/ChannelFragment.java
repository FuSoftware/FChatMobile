package fr.fusoft.fchatmobile.socketclient.view.fragment.channels;

import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import fr.fusoft.fchatmobile.FChatMobileApplication;
import fr.fusoft.fchatmobile.socketclient.controller.FClient;
import fr.fusoft.fchatmobile.socketclient.model.messages.FChatEntry;
import fr.fusoft.fchatmobile.socketclient.view.adapter.FChatEntryAdapter;

/**
 * Created by Florent on 05/09/2017.
 */

public class ChannelFragment extends Fragment{
    private final static String LOG_TAG = "ChannelFragment";

    protected enum ChannelType{
        DEBUG(0),
        CONSOLE(1),
        PUBLIC(2),
        PRIVATE(3);

        private int value;

        ChannelType(int i){
            this.value = i;
        }

        public int getValue(){
            return this.value;
        }
    }

    protected String channelName = "";
    protected int lastPosition = -1;
    protected String iconFile = "";
    protected String channelInfo = "";

    protected ChannelType type = ChannelType.DEBUG;
    protected FChatEntryAdapter messageAdapter;
    protected ListView lvMessages;

    protected FClient client;

    protected View root;

    @Override
    public void onCreate(Bundle savedInstanceBundle){
        super.onCreate(savedInstanceBundle);
        Log.w(LOG_TAG, this.channelName + " onCreate()");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            //Restore the fragment's state here
            this.channelName = savedInstanceState.getString("channelName");
            this.lastPosition = savedInstanceState.getInt("lastPosition");
        }

        Log.w(LOG_TAG, this.channelName + " onActivityCreated()");
    }

    @Override
    public void onStart(){
        super.onStart();
        getActivity().setTitle(this.channelName);
        Log.w(LOG_TAG, this.channelName + " onStart()");
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.w(LOG_TAG, this.channelName + " onResume()");
    }

    @Override
    public void onStop(){
        super.onStop();
        Log.w(LOG_TAG, this.channelName + " onStop()");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //Save the fragment's state here

        outState.putString("channelName", this.channelName);
        outState.putInt("lastPosition", this.lastPosition);
    }

    protected void initClient(){
        this.client = ((FChatMobileApplication)getActivity().getApplication()).getClient();
    }

    protected void initListView(final List<FChatEntry> entries){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                lvMessages.setAdapter(messageAdapter);
                setMessages(entries);

                if(ChannelFragment.this.lastPosition >= 0){
                    lvMessages.setSelection(ChannelFragment.this.lastPosition);
                }
            }
        });
    }

    protected void createMessageAdapter(){
        messageAdapter = new FChatEntryAdapter(new ArrayList<FChatEntry>(),getActivity());
    }

    public void setIconFile(String url){
        this.iconFile = url;
    }

    public void setChannelName(String name){
        this.channelName = name;
    }

    public String getIcon(){
        return this.iconFile;
    }

    public String getChannelName(){
        return this.channelName;
    }

    public String getChannelInfo(){ return this.channelInfo; }

    protected void setMessages(final List<FChatEntry> messages){
        if(this.messageAdapter != null){
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    messageAdapter.clear();
                    messageAdapter.addAll(messages);
                }
            });
        }
    }

    protected void addMessage(final FChatEntry message){
        if(messageAdapter != null){
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    messageAdapter.add(message);
                }
            });
        }
    }
}
