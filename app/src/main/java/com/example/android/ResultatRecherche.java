package com.example.android;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
/*
public class ResultatRecherche extends AppCompatActivity {
    RecyclerView tabResultat = this.findViewById(R.id.textViewResultatRecherche));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultat_recherche);

    }
}
*/

public class ResultatRecherche extends RecyclerView.Adapter<ResultatRecherche.ViewHolder> {
    private List<String> data;

    public ResultatRecherche(List<String> data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_resultat_recherche, parent, false);
        RecyclerView recyclerViewListChambre = view.findViewById(R.id.recyclerViewListChambre);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textView.setText(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            //textView = itemView.findViewById(R.id.text_view);
        }
    }
}