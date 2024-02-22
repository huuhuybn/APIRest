package vn.poly.mob305.apirest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.PersonHolder> {

    public List<Person> listP;

    public PersonAdapter(List<Person> listP) {
        this.listP = listP;
    }

    @NonNull
    @Override
    public PersonHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        return new PersonHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonHolder holder, int position) {
        holder.person = listP.get(position);
        holder.tvName.setText(holder.person.getName());
    }

    @Override
    public int getItemCount() {
        return listP.size();
    }

    public class PersonHolder extends RecyclerView.ViewHolder {
        public Person person;
        public final TextView tvName;
        public PersonHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
        }
    }

}
