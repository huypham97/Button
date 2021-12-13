package com.huypham.button;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            FileReader fin = new FileReader("D:\\Hoctap\\Android\\Android_052021\\Document\\image\\brand\\lv.png");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        EditText et = findViewById(R.id.edittext_email);

        et.setFilters(new InputFilter[]{new TagInputFilter(et), new TagLengthInputFilter(10)});

        CheckBox checkBox = (CheckBox) findViewById(R.id.checkbox_gender);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(MainActivity.this, "Thanks, you checked!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Please keep the box is checked!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ToggleButton toggleButton = (ToggleButton) findViewById(R.id.toggle_demo);
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                extractHashtag(et.getText().toString());
            }
        });

        Switch switchDemo = (Switch) findViewById(R.id.switch_demo);
        switchDemo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(MainActivity.this, "Checked!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Unchecked!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

        // Set auto check for the second radio button
        radioGroup.check(R.id.radio2);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radio1) {
                    Toast.makeText(MainActivity.this, "The first one checked", Toast.LENGTH_SHORT).show();
                } else if (checkedId == R.id.radio2) {
                    Toast.makeText(MainActivity.this, "The second one checked", Toast.LENGTH_SHORT).show();
                } else if (checkedId == R.id.radio3) {
                    Toast.makeText(MainActivity.this, "The third one checked", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Called when the user touches the button
    public void showMessage(View view) {
        Toast.makeText(this, "Your button is touched!!", Toast.LENGTH_SHORT).show();
    }

    static class TagInputFilter implements InputFilter {
        private final EditText edtContent;
        private boolean isInputFilter;

        private int tempLength = 0;

        public TagInputFilter(EditText edtContent) {
            this.edtContent = edtContent;
            this.isInputFilter = true;
        }

        @SuppressLint("SetTextI18n")
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            Log.d("EDIT", "source:" + source.toString());
            Log.d("EDIT", "start:" + start);
            Log.d("EDIT", "end:" + end);
            Log.d("EDIT", "dest.length():" + dest.length() + " dest:" + dest.toString());
            Log.d("EDIT", "dend:" + dend);
            Log.d("EDIT", "dstart:" + dstart);

            String textReturn = source.toString();
            Log.d("EDIT", "textReturn1:" + textReturn);
            Pattern ps = Pattern.compile("[\\w]+$");
            if (this.isInputFilter) {
                if (ps.matcher(textReturn).matches() == false) {
                    textReturn = textReturn.replaceAll("[^\\w ]", "");
                    Log.d("EDIT", "textReturn2:" + textReturn);
                } else {
                    Log.d("EDIT", "null:" + textReturn);
                    return null;
                }
            }
            this.isInputFilter = true;
            if (textReturn.startsWith(" ")) {
                if (this.edtContent.length() > 0) {
                    Editable editable = this.edtContent.getEditableText();
                    String textContent = editable.toString();
                    Log.d("EDIT", "textContent3:" + textContent);
                    if (textContent.endsWith("#")) {
                        textReturn = textReturn.replace(" ", "");
                        Log.d("EDIT", "textReturn4:" + textReturn);
                    } else {
                        textReturn = textReturn.replaceFirst("^ ", "#");
                        Log.d("EDIT", "textReturn5:" + textReturn);
                        if (!textContent.startsWith("#")) {
                            this.isInputFilter = false;
                            this.edtContent.setText("#" + textContent);
                            this.edtContent.setSelection(this.edtContent.length());
                        }
                    }
                } else {
                    textReturn = textReturn.replaceFirst("^ ", "#");
                    Log.d("EDIT", "textReturn6:" + textReturn);
                }
            }
            return textReturn;
        }
    }

    static class TagLengthInputFilter extends InputFilter.LengthFilter {

        private int temp;

        public TagLengthInputFilter(int max) {
            super(max);
            temp = 0;
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            Log.d("TEST", "source:" + source.toString());
            Log.d("TEST", "start:" + start);
            Log.d("TEST", "end:" + end);
            Log.d("TEST", "dest.length():" + dest.length() + " dest:" + dest.toString());
            Log.d("TEST", "dend:" + dend);
            Log.d("TEST", "dstart:" + dstart);
            Log.d("TEST", "length:" + (dest.length() - (dend - dstart)));

            int keep = getMax() - (dest.length() - (dend - dstart));
            Log.d("TEST", "keep:" + keep);

            if (source.length() > 0) {
                if (source.charAt(source.length() - 1) == '#') {
                    temp = temp + 1;
                }
            }

            if (dest.length() > 0) {
                if ((end == 0 || dstart < dend) && dest.length() != dstart) {
                    if (dest.charAt(dstart) == '#') {
                        temp--;
                    }
                }
            } else if (source.length() == 0) {
                temp = 0;
            }

            keep += temp;

            Log.d("TEST", "temp:" + temp);

            if (keep <= 0) {
                Log.d("TEST", "keep1:");
                return "";
            } else if (keep >= end - start) {
                Log.d("TEST", "keep2:");
                return null; // keep original
            } else {
                Log.d("TEST", "keep3:");
//                temp = 0;
                keep += start;
                if (Character.isHighSurrogate(source.charAt(keep - 1))) {
                    --keep;
                    if (keep == start) {
                        Log.d("TEST", "keep4:");
                        return "";
                    }
                }
                Log.d("TEST", "keep5:");
                return source.subSequence(start, keep);
            }
        }
    }

    void extractHashtag(String hashTag) {
        List<String> hashTags = Arrays.asList(hashTag.split("#"));
        for (String str : hashTags) {
            Log.d("TAG", str);
        }
    }
}