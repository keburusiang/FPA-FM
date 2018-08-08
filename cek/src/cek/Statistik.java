/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cek;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author hurricane
 */
public class Statistik {
    public static float HitungRataBerat(List<DataSource> list)
    {
        
        float n = list.size();

        float jumlah = 0;
        float rata2;
        for (DataSource om: list)
        {
            jumlah += om.getBeratAsli();
        }
        rata2 = jumlah/ n;
        return rata2;
    }
    public static float HitungRataHarga(List<DataSource> list)
    {
        
        float n = list.size();

        float jumlah = 0;
        float rata2;
        for (DataSource om: list)
        {
            jumlah += om.getHargaAsli();
        }
        rata2 = jumlah/ n;
        return rata2;
    }
    
    public static float medianBerat(List<DataSource> list)
    {
        float median = 0;
        List<DataSource> list2 = list;      
        BeanComparator bc = new BeanComparator(DataSource.class, "getBeratAsli");

        Collections.sort(list2, bc);
        if(list2.size()%2 == 0)
        {
            median = (list2.get(list2.size()/2).getBeratAsli() + list2.get(list2.size()/2 -1 ).getHargaAsli())/2; 
        }
        else
        {
            median = (list2.get(list2.size()/2).getBeratAsli());
        }
        return Math.round(median);
    }
    
    public static float medianHarga(List<DataSource> list)
    {
        float median = 0;

        List<DataSource> list2 = list;      
        BeanComparator bc = new BeanComparator(DataSource.class, "getHargaAsli");
        if(list2.size()%2 == 0)
        {
            median = (list2.get(list2.size()/2).getHargaAsli() + list2.get(list2.size()/2 -1 ).getHargaAsli())/2;
        }
        else
        {
            median = (list2.get(list2.size()/2).getHargaAsli());
        }
        return Math.round(median);
    }

}
