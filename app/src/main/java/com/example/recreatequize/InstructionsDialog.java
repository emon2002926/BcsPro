package com.example.recreatequize;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;

public class InstructionsDialog extends Dialog {

    private int instrationPoints = 0;



    public InstructionsDialog(@NonNull Context context) {
        super(context);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.instrutions_dialog_layout);

        final AppCompatButton continueBtn = findViewById(R.id.continueBtn);

        final TextView instructionsTV = findViewById(R.id.instrationIconTv);


        setInstrationPoint(instructionsTV,"'Give at least 6 correct answers out of 10 questions   by 4 minutes & win the \n" +
                "'FIFA WORLD CUP TROPHY'"

        );


        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();
            }
        });

    }


    private void setInstrationPoint(TextView instationTv, String instationpoint){

        if(instrationPoints ==0){
            instationTv.setText(instationpoint);

        }else {

            instationTv.setText(instationTv.getText()+"/n/n"+instationpoint);

        }
    }
}
