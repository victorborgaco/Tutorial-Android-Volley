package suemar.com.br.volley;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class MyActivity extends Activity {

    private TextView tvCotacao;
    private static final String URL = "http://rate-exchange.appspot.com/currency?from=USD&to=BRL";

    public void obterCotacao(View v) {

        JsonObjectRequest requisicao =
                new JsonObjectRequest(
                        Request.Method.GET,
                        URL,
                        null,
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                 if(response.has("rate")) {
                                     try {
                                         double rate = response.getDouble("rate");
                                         tvCotacao.setText(String.format("1 USD = %.2f BRL", rate));

                                     } catch (JSONException e) {
                                         tvCotacao.setText("Não foi possível obter a cotação!");
                                         e.printStackTrace();
                                     }
                                 }
                                else {
                                     tvCotacao.setText("Não foi possível obter a cotação!");
                                 }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                tvCotacao.setText("Não foi possível obter a cotação!");
                            }
                        }

                );

        RequestQueue fila = Volley.newRequestQueue(this);
        fila.add(requisicao);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        tvCotacao = (TextView)findViewById(R.id.tvCotacao);
    }

}
