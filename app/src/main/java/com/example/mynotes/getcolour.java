package com.example.mynotes;

import java.util.ArrayList;
import java.util.Random;

public class getcolour {
    getcolour()
    {

    }

    public int getrandomcolour()
    {
        ArrayList<Integer> code = new ArrayList<>();
        code.add(R.color.color1);
        code.add(R.color.color2);
        code.add(R.color.color3);
        code.add(R.color.color4);
        code.add(R.color.color5);
        code.add(R.color.color6);
        code.add(R.color.color7);
        code.add(R.color.color8);
        code.add(R.color.color9);
        code.add(R.color.color10);
        code.add(R.color.color11);
        code.add(R.color.color12);
        code.add(R.color.color13);
        code.add(R.color.color14);
        code.add(R.color.color15);
        code.add(R.color.color16);


        Random i = new Random();
        int index = i.nextInt(code.size());
        return code.get(index);

    }
}
