package com.ufcg.activitys;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendPosition;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Highlight;
import com.github.mikephil.charting.utils.PercentFormatter;
import com.github.mikephil.charting.utils.ValueFormatter;
import com.ufcg.auxiliary.DemoBase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import com.ufcg.R;
import com.ufcg.entities.Priority;
import com.ufcg.entities.Task;
import com.ufcg.entities.Time;
import com.ufcg.entities.Week;

public class AcompanhamentoSemanal extends DemoBase implements OnChartValueSelectedListener{
    private final int MINUTOS_SEMANA = 10080;

    private Typeface mTf;
    private Typeface tf;
    private BarChart barChart;
    private PieChart pieChart;
    private Week week;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acompanhamento_semanal);
        criarWeek();

        Task t1 = new Task("oi", new Time(1,0), Priority.Alta,new Date());
        Task t2 = new Task("ei", new Time(2,15), Priority.Baixa,new Date());
        Task t3 = new Task("oe", new Time(0,25), Priority.Media,new Date());
        week.addActivity(t1);
        week.addActivity(t2);
        week.addActivity(t3);


        pieGraph();
        barGraph();

        TextView total = (TextView) findViewById(R.id.textView);
        total.setText(week.getTotalTime() + " horas");
    }

    private void barGraph() {
        barChart = (BarChart) findViewById(R.id.barChart);
        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(true);
        barChart.setDescription("");
        barChart.setMaxVisibleValueCount(60);
        barChart.setPinchZoom(false);
        barChart.setDrawGridBackground(false);
        mTf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTypeface(mTf);
        xAxis.setDrawGridLines(false);
        xAxis.setSpaceBetweenLabels(2);

        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setTypeface(mTf);
        leftAxis.setLabelCount(8);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);

        YAxis rightAxis = barChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setTypeface(mTf);
        rightAxis.setLabelCount(8);
        rightAxis.setSpaceTop(15f);

        Legend l = barChart.getLegend();
        l.setPosition(LegendPosition.BELOW_CHART_LEFT);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);

        setBarData(12, 50);
    }

    private int minutos(String task) {
        Time t = week.getActivityTime(task);
        return (60*t.getHours())+t.getMinutes();
    }

    private void setBarData(int count, float range) {
        ArrayList<String> tarefas = new ArrayList<String>();
        ArrayList<BarEntry> tempos = new ArrayList<BarEntry>();
        for (int i = 0; i < week.getAcivities().size(); i++) {
            Object[] k = week.getAcivities().keySet().toArray();
            tarefas.add((String) k[i]);
            tempos.add(new BarEntry(minutos((String) k[i]), i));
        }

        BarDataSet set1 = new BarDataSet(tempos, "DataSet");
        set1.setBarSpacePercent(35f);
        ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
        dataSets.add(set1);
        BarData data = new BarData(tarefas, dataSets);
        data.setValueTextSize(10f);
        data.setValueTypeface(mTf);
        barChart.setData(data);
    }

    private void pieGraph() {
        pieChart = (PieChart) findViewById(R.id.pieChart);
        pieChart.setUsePercentValues(true);
        pieChart.setDescription("");
        pieChart.setDragDecelerationFrictionCoef(0.95f);
        tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
        pieChart.setCenterTextTypeface(Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf"));
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColorTransparent(true);
        pieChart.setTransparentCircleColor(Color.WHITE);
        pieChart.setHoleRadius(58f);
        pieChart.setTransparentCircleRadius(61f);
        pieChart.setDrawCenterText(true);
        pieChart.setRotationAngle(0);
        pieChart.setRotationEnabled(true);
        pieChart.setOnChartValueSelectedListener(this);
        pieChart.setCenterText("Prioridade-Tarefa");
        setPieData();
        pieChart.animateY(1500, Easing.EasingOption.EaseInOutQuad);
        Legend l = pieChart.getLegend();
        l.setPosition(LegendPosition.RIGHT_OF_CHART);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(5f);
    }

    private float porcentagem(float n) {
        return (100*n)/MINUTOS_SEMANA;
    }

    private void setPieData() {
        ArrayList<String> prioridade = new ArrayList<String>();
        prioridade.add(Priority.Alta.toString());
        prioridade.add(Priority.Media.toString());
        prioridade.add(Priority.Baixa.toString());

        float alta = 0;
        float media = 0;
        float baixa = 0;
        for (int i = 0; i < week.getAcivities().size(); i++) {
            Object[] k = week.getAcivities().keySet().toArray();
            if (week.getSpecificTask((String) k[i]).getPriority().equals(Priority.Alta)) {
                alta += (week.getActivityTime((String) k[i]).getHours()*60)+week.getActivityTime((String) k[i]).getMinutes();
            } else if (week.getSpecificTask((String) k[i]).getPriority().equals(Priority.Media)) {
                media += (week.getActivityTime((String) k[i]).getHours()*60)+week.getActivityTime((String) k[i]).getMinutes();
            } else if (week.getSpecificTask((String) k[i]).getPriority().equals(Priority.Baixa)){
                baixa += (week.getActivityTime((String) k[i]).getHours()*60)+week.getActivityTime((String) k[i]).getMinutes();
            }
        }

        List<Entry> porcentagem = new ArrayList<Entry>();
        porcentagem.add(new Entry(alta,1));
        porcentagem.add(new Entry(media,2));
        porcentagem.add(new Entry(baixa,3));

        PieDataSet dataSet = new PieDataSet(porcentagem, "");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        ArrayList<Integer> colors = new ArrayList<Integer>();
        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);
        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);

        PieData data = new PieData(prioridade, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        data.setValueTypeface(tf);
        pieChart.setData(data);
        pieChart.highlightValues(null);
        pieChart.invalidate();
    }

    private void criarWeek(){
        if (Week.getAllInstance().isEmpty()) {
            week = new Week();
        } else {
            week = Week.getAllInstance().get(Week.getAllInstance().size() - 1);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionToggleValues: {
                for (DataSet<?> set : pieChart.getData().getDataSets())
                    set.setDrawValues(!set.isDrawValuesEnabled());

                pieChart.invalidate();
                break;
            }
            case R.id.actionToggleHole: {
                if (pieChart.isDrawHoleEnabled())
                    pieChart.setDrawHoleEnabled(false);
                else
                    pieChart.setDrawHoleEnabled(true);
                pieChart.invalidate();
                break;
            }
            case R.id.actionDrawCenter: {
                if (pieChart.isDrawCenterTextEnabled())
                    pieChart.setDrawCenterText(false);
                else
                    pieChart.setDrawCenterText(true);
                pieChart.invalidate();
                break;
            }
            case R.id.actionToggleXVals: {

                pieChart.setDrawSliceText(!pieChart.isDrawSliceTextEnabled());
                pieChart.invalidate();
                break;
            }
            case R.id.actionSave: {
                // mChart.saveToGallery("title"+System.currentTimeMillis());
                pieChart.saveToPath("title" + System.currentTimeMillis(), "");
                break;
            }
            case R.id.actionTogglePercent:
                pieChart.setUsePercentValues(!pieChart.isUsePercentValuesEnabled());
                pieChart.invalidate();
                break;
            case R.id.animateX: {
                pieChart.animateX(1800);
                break;
            }
            case R.id.animateY: {
                pieChart.animateY(1800);
                break;
            }
            case R.id.animateXY: {
                pieChart.animateXY(1800, 1800);
                break;
            }
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_acompanhamento_semanal, menu);
        return true;
    }


    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }
}
