package com.neo.ringtunesgo.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.neo.ringtunesgo.Config.Config;
import com.neo.ringtunesgo.Model.PhoneContactModel;
import com.neo.ringtunesgo.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

import static com.neo.ringtunesgo.MainNavigationActivity.datasvina;
import static com.neo.ringtunesgo.View.ActivityContacts.option;


public class ContactListPickerAdapter extends SearchAdapter<PhoneContactModel> implements StickyListHeadersAdapter {

    private final Activity context;

    public ContactListPickerAdapter(ArrayList<PhoneContactModel> contacts, Activity context) {
        super(contacts, context);
        this.context = context;
    }

    public void markAll(boolean isMarkAll) {
        for (int i = 0; i < filteredContainer.size(); i++) {
            //  filteredContainer.get(i).setChecked(isMarkAll);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.item_contact_picker, null, true);
        final PhoneContactModel temp = filteredContainer.get(position);
        RelativeLayout relativeLayout = (RelativeLayout) rowView.findViewById(R.id.relative_contacts);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.item_contact_name);
        CircleImageView imageView = (CircleImageView) rowView.findViewById(R.id.item_contact_avata);
        TextView txtPhone = (TextView) rowView.findViewById(R.id.item_contact_phone);
        //for check box (right conner)
        final CheckBox scb = (CheckBox) rowView.findViewById(R.id.cb_contact);
        //
        scb.setChecked(temp.isChecked());
        txtTitle.setText(temp.getName());
       // imageView.setImageResource(R.drawable.avatar);
        txtPhone.setText(temp.getPhoneNumber());
        // click vào checkbox
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (option) {
                    case Config.GROUP_MEMBER:
                        temp.setChecked(!temp.isChecked());
                        scb.setChecked(temp.isChecked());
                        break;
                    case Config.ADD_GROUP:
                        temp.setChecked(!temp.isChecked());
                        scb.setChecked(temp.isChecked());
                        break;
                    case Config.ADD_PROFILES:
                        temp.setChecked(!temp.isChecked());
                        scb.setChecked(temp.isChecked());
                        if (temp.isChecked()) {
                            for (int i = 0; i < datasvina.size(); i++) {
                                if (!datasvina.get(i).getPhoneNumber().equals(temp.getPhoneNumber())) {
                                    datasvina.get(i).setChecked(false);
                                }
                            }
                            notifyDataSetChanged();
                        }
                        break;
                    case Config.GIFT:
                        temp.setChecked(!temp.isChecked());
                        scb.setChecked(temp.isChecked());
                        if (temp.isChecked()) {
                            for (int i = 0; i < datasvina.size(); i++) {
                                if (!datasvina.get(i).getPhoneNumber().equals(temp.getPhoneNumber())) {
                                    datasvina.get(i).setChecked(false);
                                }
                            }
                            notifyDataSetChanged();
                        }
                }

            }
        });

        return rowView;
    }


    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        //setup for header view (a, b ,c...)
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.item_header_listview, null, true);
        TextView label = (TextView) rowView.findViewById(R.id.label);
        String headerText = "" + filteredContainer.get(position).getName().subSequence(0, 1).charAt(0);
        label.setText(headerText);
        return rowView;
    }

    @Override
    public long getHeaderId(int position) {
        return filteredContainer.get(position).getName().subSequence(0, 1).charAt(0);
    }
}
