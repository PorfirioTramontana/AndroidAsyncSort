package mimecast.com.recruitment.application;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import mimecast.com.recruitment.model.EmailHeaderModel;

import java.util.List;

import mimecast.com.recruitment.R;

/**
 * Created by Michael Aubert on 27/02/2015.
 */

public class EmailHeaderArrayAdapter extends ArrayAdapter<EmailHeaderModel> {

    public EmailHeaderArrayAdapter(Context aContext, List<EmailHeaderModel> aList) {
        super(aContext, R.layout.email_header_list_tem_layout, aList);
    }

    @Override
    public View getView(int anIndex, View aRecycledView, ViewGroup aParent) {
        if(null == aRecycledView) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            aRecycledView = inflater.inflate(R.layout.email_header_list_tem_layout, aParent, false);
        }
        if(null != aRecycledView) {
            EmailHeaderModel emailHeader = getItem(anIndex);
            if(null != emailHeader) {
                TextView senderView = (TextView) aRecycledView.findViewById(R.id.senderTextView);
                if(null != senderView) {
                    senderView.setText(emailHeader.getSender());
                }
                TextView subjectView = (TextView) aRecycledView.findViewById(R.id.subjectTextView);
                if(null != subjectView) {
                    subjectView.setText(emailHeader.getSubject());
                }
                TextView receivedTimeView = (TextView) aRecycledView.findViewById(R.id.receivedTimeTextView);
                if(null != receivedTimeView) {
                    receivedTimeView.setText(Long.toString(emailHeader.getReceivedTime()));
                }
            }
        }
        return aRecycledView;
    }

}
