package ar.com.fitlandia.fitlandia;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import ar.com.fitlandia.fitlandia.models.LogRutinaModel;
import ar.com.fitlandia.fitlandia.utils.APIService;
import ar.com.fitlandia.fitlandia.utils.ApiUtils;
import ar.com.fitlandia.fitlandia.utils.ApplicationGlobal;
import butterknife.BindView;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.ChartData;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.LineChartView;
import lecho.*;
import retrofit2.Callback;

public class HistoricoPeso extends AppCompatActivity {
    private APIService api;
    ApplicationGlobal applicationGlobal;
    @BindView(R.id.layout_peso)
    LinearLayout layout_peso;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico_peso);
        api = ApiUtils.getAPIService();
        applicationGlobal = ApplicationGlobal.getInstance();
       // api.getLogPesos(applicationGlobal.getUsername()).enqueue(new Callback<List<LogPesoModel>>() {
        List<PointValue> values = new ArrayList<PointValue>();
        values.add(new PointValue(1, 70));
        values.add(new PointValue(2, 72));
        values.add(new PointValue(3, 71));
        values.add(new PointValue(4, 69));
        values.add(new PointValue(5, 68));
        values.add(new PointValue(6, 67));
        values.add(new PointValue(7, 66));

        //In most cased you can call data model methods in builder-pattern-like manner.
        Line line = new Line(values).setColor(Color.rgb(199, 0, 57  )).setCubic(true);
        List<Line> lines = new ArrayList<Line>();
        lines.add(line);

        LineChartData data = new LineChartData();
        data.setLines(lines);

        LineChartView chart = findViewById(R.id.grafico);
        chart.setScaleX(new Float(1));
        chart.setScaleY(new Float(1));

        List<AxisValue> xAxisValues = new ArrayList<AxisValue>();

        List<Float> xAxisValues1 = new ArrayList<Float>();
        List<String> xAxisValues2 = new ArrayList<String>();
        //X axis label for value 1
        xAxisValues1.add(1.0f);
        xAxisValues2.add("UNO");
        //X axis labels for values 5,10,15,20,25,30
        for(int i = 5; i <= 30; i+=5) {
            xAxisValues1.add(Float.valueOf(i));
            xAxisValues2.add(String.valueOf(i));
        }

        Axis xAxis = Axis.generateAxisFromCollection(xAxisValues1, xAxisValues2);
        xAxis.setAutoGenerated(true);
        xAxis.setTextSize(8);
        xAxis.setHasLines(true);
        List<Float> yAxisValues1 = new ArrayList<Float>();
        List<String> yAxisValues2 = new ArrayList<String>();
        //X axis label for value 1
        yAxisValues1.add(1.0f);
        yAxisValues2.add("UNO");
        //X axis labels for values 5,10,15,20,25,30
        for(int i = 5; i <= 30; i+=5) {
            yAxisValues1.add(Float.valueOf(i));
            yAxisValues2.add(String.valueOf(i));
        }

        Axis yAxis = Axis.generateAxisFromCollection(yAxisValues1, yAxisValues2);
        yAxis.setAutoGenerated(true);
        yAxis.setTextSize(8);
        yAxis.setHasLines(true);
        data.setAxisYLeft(yAxis);
        data.setAxisXBottom(xAxis);
        chart.setValueSelectionEnabled(true);

        chart.setLineChartData(data);

    }
}
