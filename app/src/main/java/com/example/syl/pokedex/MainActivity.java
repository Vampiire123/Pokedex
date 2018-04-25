package com.example.syl.pokedex;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.syl.pokedex.datasource.PokemonService;
import com.example.syl.pokedex.global.model.Pokemon;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/pokemon/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        PokemonService service = retrofit.create(PokemonService.class);
        Call<Pokemon> callSync = service.getRandomPokemon();

        callSync.enqueue(new Callback<Pokemon>() {
            @Override
            public void onResponse(Call<Pokemon> call, Response<Pokemon> response) {
                Pokemon pokemon = response.body();
                System.out.println(pokemon.toString());
            }

            @Override
            public void onFailure(Call<Pokemon> call, Throwable t) {
                System.out.println(t);
            }
        });
    }
}