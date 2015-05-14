package com.example.diego.myapplication.Atividades;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.diego.myapplication.Entidades.Atividade;
import com.example.diego.myapplication.Entidades.Tempo;
import com.example.diego.myapplication.Persistencia.DataBaseHelper;
import com.example.diego.myapplication.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Highlight;
import com.github.mikephil.charting.utils.PercentFormatter;

import java.util.ArrayList;
import java.util.List;

public class AcompanhamentoActivity extends Activity implements OnChartValueSelectedListener {
    private final int MINUTOS_SEMANA = 10080;

    public List<Atividade> atividades;
    private DataBaseHelper db;

    private Typeface mTf;
    private Typeface tf;
    private BarChart barChart;
    private PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acompanhamento_layout);

        db = new DataBaseHelper(this);

        atividades = db.selecinarTodasAtividades();

        pieGraph();
        barGraph();

        Tempo tempo = new Tempo();
        for (int i = 0; i < atividades.size(); i++) {
            tempo.setHora(tempo.getHora() + atividades.get(i).getTempo().getHora());
            tempo.setMinuto(tempo.getMinuto() + atividades.get(i).getTempo().getMinuto());
        }

        TextView total = (TextView) findViewById(R.id.textView);
        total.setText(tempo.toTempo() + " horas");
    }

    private void barGraph() {
        barChart = (BarChart) findViewById(R.id.barChart);
        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(true);
        barChart.setDescription("");
        barChart.setMaxVisibleValueCount(60);
        barChart.setPinchZoom(false);
        barChart.setDrawGridBackground(false);

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
        l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);

        setBarData(12, 50);
    }

    private int minutos(Atividade task) {
        Tempo t = task.getTempo();
        return (60 * t.getHora()) + t.getMinuto();
    }

    private void setBarData(int count, float range) {
        ArrayList<String> tarefas = new ArrayList<String>();
        ArrayList<BarEntry> tempos = new ArrayList<BarEntry>();
        for (int i = 0; i < atividades.size(); i++) {
            tarefas.add(atividades.get(i).getNome());
            tempos.add(new BarEntry(minutos(atividades.get(i)), i));
        }

        BarDataSet set1 = new BarDataSet(tempos, "Atividades");
        set1.setBarSpacePercent(35f);
        set1.setColors(ColorTemplate.LIBERTY_COLORS);
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
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(5f);
    }

    private void setPieData() {
        ArrayList<String> prioridade = new ArrayList<String>();
        prioridade.add("Alta");
        prioridade.add("Media");
        prioridade.add("Baixa");

        float alta = 0;
        float media = 0;
        float baixa = 0;
        for (int i = 0; i < atividades.size(); i++) {
            String p = atividades.get(i).getPrioridade();
            if (p.equals("Alta")) {
                alta += minutos(atividades.get(i));
            } else if (p.equals("Media")) {
                media += minutos(atividades.get(i));
            } else if (p.equals("Baixa")){
                baixa += minutos(atividades.get(i));
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

    public void onClick(View v){

        switch (v.getId()){

            case R.id.btn_acompanhamento_voltar:
                finish();
        }
    }

    @Override
    public void onValueSelected(Entry entry, int i, Highlight highlight) {

    }

    @Override
    public void onNothingSelected() {

    }
}
